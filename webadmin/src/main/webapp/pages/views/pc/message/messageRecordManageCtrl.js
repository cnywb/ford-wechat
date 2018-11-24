/**
 * Created by ziv.hung.
 * 备注：消息记录
 * v1.0.0 17/5/16
 */
var DBApp = angular.module('DBApp');

DBApp.controller("messageRecordManageCtrl", ['$scope', '$modal', 'dbUtils', function ($scope, $modal, dbUtils) {

    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 4//查询条件列
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
                transCode: "messageRecordPage",
                autoLoad: true,//页面初始化自动加载数据
                showCheckBox: true,//是否显示复选框,
                lineFeed: true//强制单行显示
            },
            header: [
                /*{"name": "VIN码", "width": "10%", "field": "vin"},
                 {"name": "手机号", "width": "10%", "field": "mobile"},*/
                {"name": "公告标题", "width": "13%", "field": "title"},
                {"name": "消息种类", "width": "16%", "field": "msgClass"},
                /*{"name": "消息类型", "width": "16%", "field": "msgType"},*/
                {"name": "推送渠道", "width": "16%", "field": "pushChannel"},
                {"name": "公告发布时间", "width": "13%", "field": "publishTime"},
                {"name": "消息失效时间", "width": "16%", "field": "invalidTime"}
            ],
            rowOperation: {show: true}//是否显示最后操作列
        }
    };
    //!!formGridOptions-END!!
    var formGridEvents = {
        grid: {
            rowEvents: [//最后的操作列
                {
                    "name": "查看", "class": "btn-warning", icon: "glyphicon-list", "click": function (row) {
                    detailRow(row)
                }
                }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //操作单行记录 修改字段，以及transCode
    function detailRow(row) {
        dbUtils.post("messageRecordGet", {id: row['id']}, function (data) {
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
                            fields: [{name: "title", label: "标题", type: "text", disabled: true, labelCols: 1, cols: 12, rows: 8},
                                {name: "msgClass", label: "公告类型", type: "text", disabled: true, labelCols: 2},
                                {name: "pushChannel", label: "发布渠道", type: "text", disabled: true, labelCols: 2},
                                {name: "publishTime", label: "生发时间", type: "text", disabled: true, labelCols: 2},
                                {name: "invalidTime", label: "失效时间", type: "text", disabled: true, labelCols: 2},
                                {name: "content", label: "公告内容", type: "textarea", disabled: true, labelCols: 1, cols: 12, rows: 8}
                            ]
                        }],
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
}]);