/**
 * Created by ziv.hung.
 * 备注：TODO
 * v1.0.0 17/5/16
 */
angular.module('DBApp').controller("messageContentManageCtrl", ['$scope', '$modal', 'dbUtils', function ($scope, $modal, dbUtils) {

    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 3//查询条件列
            },
            fields: [
                /*{name: "vin", label: "VIN码", type: "text", placeholder: "VIN码"},
                 {name: "mobile", label: "手机号", type: "text", placeholder: "手机号码"},*/
                {name: "msgClass", label: "公告种类", type: "select", dropDownItemType: "json", dropDownItem: "templateType"},
                {name: "msgType", label: "公告类型", type: "select", dropDownItemType: "json", dropDownItem: "msgType"},
                {name: "pushChannel", label: "推送渠道", type: "select", dropDownItemType: "json", dropDownItem: "useChannel"}
            ]
        },
        grid: {
            settings: {
                transCode: "messageContentPage",
                autoLoad: true,//页面初始化自动加载数据
                showCheckBox: true,//是否显示复选框,
                lineFeed: true//强制单行显示
            },
            header: [
                /*{"name": "VIN码", "width": "10%", "field": "vin"},
                 {"name": "手机号", "width": "10%", "field": "mobile"},*/
                {"name": "公告标题", "width": "13%", "field": "title"},
                {"name": "公告种类", "width": "13%", "field": "msgClass"},
                /*{"name": "消息类型", "width": "13%", "field": "msgType"},*/
                {"name": "推送渠道", "width": "13%", "field": "pushChannel"},
                {"name": "公告发布时间", "width": "13%", "field": "publishTime"},
                {"name": "公告失效时间", "width": "13%", "field": "invalidTime"},
                {"name": "公告内容", "width": "13%", "field": "content", omit: true}
            ],
            rowOperation: {show: true, lineFeed: true}//是否显示最后操作列
        }
    };
    //!!formGridOptions-END!!
    var formGridEvents = {
        grid: {
            rowEvents: [//最后的操作列
                {
                    "name": "查看", "class": "btn-warning", icon: "glyphicon-list", "click": function (row) {
                    detailRow(row);
                }
                }],
            operationEvents: [{//多数据处理配置
                name: "删除", class: "btn-danger", icon: "glyphicon-trash", click: function () {
                    operationSelectedRows();
                }
            }, {
                name: "新增", class: "btn-primary", icon: "glyphicon-plus", click: function () {
                    operationRow();
                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //操作单行记录 修改字段，以及transCode
    function operationRow(row) {
        var instance = $modal.open({
            animation: true,
            templateUrl: "db/db-form.html",
            controller: ["$scope", "$modalInstance", "dbUtils", "originData", function ($scope, $modalInstance, dbUtils, originData) {
                //!!FORM--START!!
                $scope.dbForm = {
                    settings: {showClose: true, transCode: "messageContentHandle", cols: 2},
                    title: {icon: "luru", label: "发布公告消息"},
                    sections: [{
                        sectionTitle: {show: true, icon: "jigou", label: "发布公告消息"},
                        fields: [
                            {name: "title", label: "标题", type: "text", required: true, labelCols: 1, cols: 12, rows: 8},
                            {name: "publishTime", label: "发布时间", type: "dateTime", required: true, placeholder: "点击请选择", labelCols: 2},
                            {name: "invalidTime", label: "失效时间", type: "dateTime", required: true, placeholder: "点击请选择", labelCols: 2},
                            {name: "content", label: "消息内容", type: "textarea", required: true, placeholder: "消息内容", labelCols: 1, cols: 12, rows: 8}
                        ]
                    }, {}],
                    originData: originData,//数据源（新增修改共用）
                    defaultData: {},
                    events: {
                        beforeSubmit: function (reqBody) {
                            reqBody['msgClass'] = "公告消息";
                            reqBody['pushChannel'] = "个人中心";
                            reqBody['publishTime'] = reqBody['publishTime'] + ":00";
                            reqBody['invalidTime'] = reqBody['invalidTime'] + ":00";
                        },
                        afterSubmit: function () {//提交返回结果正常处理
                            dbUtils.success("数据提交成功!");
                            $modalInstance.close();
                        },
                        modalClose: function () {//关闭弹出框
                            $modalInstance.dismiss("cancel");
                        }
                    }
                };
            }],
            size: "lg",
            backdrop: "static",
            resolve: {
                originData: function () {//弹出页面传参函数
                    return row;
                }
            }
        });
        instance.result.then(function () {//close弹出框 回调函数
            $scope.dbFormGrid.reLoadData();
        });
    }

    //操作单行记录 修改字段，以及transCode
    function detailRow(row) {
        dbUtils.post("messageContentGet", {id: row['id']}, function (data) {
            $modal.open({
                animation: true,
                templateUrl: "db/db-form.html",
                controller: ["$scope", "$modalInstance", "dbUtils", "originData", function ($scope, $modalInstance, dbUtils, originData) {
                    //!!FORM--START!!
                    $scope.dbForm = {
                        settings: {showClose: true, cols: 2, isDetail: true},
                        title: {icon: "luru", label: "发布公告消息"},
                        sections: [{
                            sectionTitle: {show: true, icon: "jigou", label: "发布公告消息"},
                            fields: [
                                {name: "title", label: "标题", type: "text", disabled: true, labelCols: 1, cols: 12, rows: 8},
                                {name: "msgClass", label: "公告类型", type: "text", disabled: true, labelCols: 2},
                                {name: "pushChannel", label: "发布渠道", type: "text", disabled: true, labelCols: 2},
                                {name: "publishTime", label: "生发时间", type: "text", disabled: true, labelCols: 2},
                                {name: "invalidTime", label: "失效时间", type: "text", disabled: true, labelCols: 2},
                                {name: "content", label: "消息内容", type: "textarea", disabled: true, labelCols: 1, cols: 12, rows: 8}
                            ]
                        }, {}],
                        originData: originData,//数据源（新增修改共用）
                        defaultData: {},
                        events: {
                            modalClose: function () {//关闭弹出框
                                $modalInstance.dismiss("cancel");
                            }
                        }
                    };
                    //!!FORM-END!!
                }],
                size: "lg",
                backdrop: "static",
                resolve: {
                    originData: function () {//弹出页面传参函数
                        return data;
                    }
                }
            });
        });
    }

    //批量操作
    function operationSelectedRows() {
        $scope.dbFormGrid.operationButtonClick(function (selectRows) {//多数据操作函数
            if (selectRows.length == 0) {
                return;
            }
            var ids = dbUtils.getFieldArray(selectRows, 'id');//获取指定字段值。
            dbUtils.confirm("确定要执行<span style='color: red'>当前</span>操作吗？同时会清理掉记录数据！", function () {//要求操作人员确认
                dbUtils.post("messageContentDelete", {ids: ids}, function () {
                    dbUtils.success("删除成功");
                    $scope.dbFormGrid.reLoadData();
                })
            });
        });
    }
}]);