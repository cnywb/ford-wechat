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
                {name: "msgClass", label: "消息种类", type: "select", dropDownItemType: "json", dropDownItem: "templateType"},
                {name: "msgType", label: "消息类型", type: "select", dropDownItemType: "json", dropDownItem: "msgType"},
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
                {"name": "消息种类", "width": "13%", "field": "msgClass"},
                {"name": "消息类型", "width": "13%", "field": "msgType"},
                {"name": "推送渠道", "width": "13%", "field": "pushChannel"},
                {"name": "消息失效时间", "width": "13%", "field": "invalidTime"},
                {"name": "发送状态", "width": "13%", "field": "sendStatus"},
                {"name": "消息内容", "width": "13%", "field": "content", omit: true},
                {"name": "消息阅读总计", "width": "13%", "field": "readCount"}
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
                    sections: [/*{
                     sectionTitle: {show: true, icon: "jigou", label: "认证车主信息"},
                     fields: [
                     {name: "vin", label: "VIN码", type: "text", readonly: true, placeholder: "点击选择", required: true, labelCols: 2},
                     {name: "mobile", label: "手机号码", type: "text", disabled: true, placeholder: "请点击VIN码", required: true, labelCols: 2}
                     ]
                     }, */{
                        sectionTitle: {show: true, icon: "jigou", label: "消息基础信息"},
                        fields: [
                            {name: "msgClass", label: "消息种类", type: "text", disabled: true, labelCols: 2},
                            {name: "msgType", label: "消息类型", type: "select", dropDownItemType: "json", dropDownItem: "msgType", required: true, labelCols: 2},
                            {name: "invalidTime", label: "失效时间", type: "date", required: true, placeholder: "日期年月日", labelCols: 2}
                        ]
                    }, {
                        sectionTitle: {show: true, icon: "jigou", label: "采用消息模板"},
                        fields: [
                            {name: "templateName", label: "模板名称", type: "text", required: true, readonly: true, labelCols: 2},
                            {name: "templateCode", label: "模板代码", type: "text", disabled: true, placeholder: "点击选择模板", labelCols: 2},
                            {name: "templateType", label: "模板种类", type: "text", disabled: true, labelCols: 2},
                            {name: "pushChannel", label: "使用渠道", type: "text", disabled: true, labelCols: 2},
                            {name: "content", label: "消息内容", type: "textarea", required: true, placeholder: "文本域框", disabled: true, labelCols: 1, cols: 12, rows: 8}
                        ]
                    }, {}],
                    originData: originData,//数据源（新增修改共用）
                    defaultData: {msgClass: "公告消息"},
                    events: {
                        beforeSubmit: function (reqBody) {//提交返回结果正常处理
                            var tmpParamData = [];
                            angular.forEach($scope.tmpList, function (tmp) {
                                var itemData = JSON.parse("{\"" + tmp + "\": \"" + reqBody[tmp] + "\"}");
                                tmpParamData.push(itemData);
                            });
                            reqBody['tmpParamData'] = tmpParamData;
                            console.log(reqBody);
                        },
                        afterSubmit: function () {//提交返回结果正常处理
                            dbUtils.success("数据提交成功!");
                            $modalInstance.close();
                        },
                        modalClose: function () {//关闭弹出框
                            $modalInstance.dismiss("cancel");
                        },
                        "templateNameClick": function () {
                            getTemplateName();
                        }
                    }
                };
                //!!FORM-END!!
                /** 获取模板信息 */
                function getTemplateName() {
                    var instance = $modal.open({
                        animation: true,
                        templateUrl: 'db/db-form-grid.html',
                        controller: "selectTemplateCtrl",//js在app.js中配置添加与当前js一起。
                        size: "lg",
                        backdrop: "static",
                        resolve: {
                            templateType: function () {//弹出页面传参函数
                                return "公告消息";
                            }
                        }
                    });
                    instance.result.then(function (rowBack) {//所选择记录回传
                        $scope.dbForm.setFormDataField("templateId", rowBack["id"]);
                        $scope.dbForm.setFormDataField("templateName", rowBack["name"]);
                        $scope.dbForm.setFormDataField("templateCode", rowBack["code"]);
                        $scope.dbForm.setFormDataField("templateType", rowBack["templateType"]);
                        $scope.dbForm.setFormDataField("pushChannel", rowBack["useChannel"]);
                        $scope.dbForm.setFormDataField("content", rowBack["content"]);

                        dbUtils.post("messageTemplateParamGet", {templateId: rowBack['id']}, function (data) {
                            $scope.tmpList = [];
                            var addSections = {
                                sectionTitle: {show: true, icon: "jigou", label: "模板消息必填参数"},
                                fields: []
                            };
                            angular.forEach(data, function (item) {
                                var field = {name: item['paramKey'], label: item['keyDesc'], type: "text", required: true, labelCols: 2};
                                addSections.fields.push(field);
                                $scope.tmpList.push(item["paramKey"]);
                            });

                            $scope.dbForm.sections[4] = addSections;
                        })
                    });
                }
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
                            sectionTitle: {show: true, icon: "jigou", label: "消息基础信息"},
                            fields: [
                                {name: "msgClass", label: "公告类型", type: "text", disabled: true, labelCols: 2},
                                {name: "msgType", label: "消息类型", type: "text", disabled: true, labelCols: 2},
                                {name: "invalidTime", label: "失效时间", type: "text", disabled: true, labelCols: 2}
                            ]
                        }, {
                            sectionTitle: {show: true, icon: "jigou", label: "内容信息"},
                            fields: [
                                {name: "templateName", label: "模板名称", type: "text", disabled: true, labelCols: 2},
                                {name: "templateCode", label: "模板代码", type: "text", disabled: true, labelCols: 2},
                                {name: "pushChannel", label: "使用渠道", type: "text", disabled: true, labelCols: 2},
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
            dbUtils.confirm("确定要执行<span style='color: red'>当前</span><br>操作吗？", function () {//要求操作人员确认
                // TODO 调用后台业务处理方法。
                dbUtils.post("", {ids: ids}, function (data) {
                    dbUtils.dbFormGrid.reLoadData();
                })
            });
        });
    }
}]);