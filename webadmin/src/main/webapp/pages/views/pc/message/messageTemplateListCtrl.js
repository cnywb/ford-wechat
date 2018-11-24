/**
 * Created by ziv.hung.
 * 备注：模板信息
 * v1.0.0 17/5/11
 */
var DBApp = angular.module('DBApp');

DBApp.controller("messageTemplateListCtrl", ['$scope', '$modal', 'dbUtils', function ($scope, $modal, dbUtils) {

    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 4//查询条件列
            },
            fields: [
                {name: "code", label: "模板代码", type: "text", placeholder: "模板代码"},
                {name: "name", label: "模板名称", type: "text", placeholder: "模板名称"},
                {name: "templateType", label: "模板分类", type: "select", dropDownItemType: "json", dropDownItem: "templateType"},
                {name: "useChannel", label: "使用渠道", type: "select", dropDownItemType: "json", dropDownItem: "useChannel"}
            ]
        },
        grid: {
            settings: {
                transCode: "messageTemplatePage",
                autoLoad: true,//页面初始化自动加载数据
                showCheckBox: false,//是否显示复选框,
                lineFeed: true//强制单行显示
            },
            header: [
                {"name": "模板代码", "width": "15%", "field": "code"},
                {"name": "模板名称", "width": "15%", "field": "name"},
                {"name": "模板分类", "width": "15%", "field": "templateType"},
                {"name": "使用渠道", "width": "15%", "field": "useChannel"},
                {"name": "模板内容", "width": "25%", "field": "content_",omit:true}
            ],
            rowOperation: {show: true, lineFeed: true}//是否显示最后操作列
        }
    };
    //!!formGridOptions-END!!
    var formGridEvents = {
        grid: {
            fieldEvents: {
                "content_Format": function (value, row) {
                    row["content_"] = row["content"];
                    if (value.length > 30) {
                        row["content_"] = value.substr(0, 30);
                    }
                    return row["content_"];
                }
            },
            rowEvents: [//最后的操作列
                {
                    "name": "修改", "class": "btn-primary", icon: "glyphicon-list", "click": function (row) {
                    operationRow(row);
                }
                }],
            operationEvents: [{
                name: "新增", class: "btn-primary", icon: "glyphicon-plus", click: function () {
                    operationRow();
                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    function operationRow(source) {
        var instance = null;
        if (source) {
            dbUtils.post("messageTemplateParamGet", {templateId: source['id']}, function (dataParams) {
                instance = $modal.open({
                    animation: true,
                    templateUrl: "views/pc/message/messageTemplateEditorView.html",
                    controller: "messageTemplateEditorCtrl",
                    size: "lg",
                    backdrop: "static",
                    resolve: {
                        source: function () {//弹出页面传参函数
                            source['params'] = dataParams;
                            var originData = angular.copy(source);
                            var useChannel = [];
                            angular.forEach(source['useChannel'].split(','), function (item) {
                                useChannel.push({name: item, value: item});
                            });
                            originData['useChannel'] = useChannel;
                            return originData;
                        }
                    }
                });

                instance.result.then(function () {//close弹出框 回调函数
                    $scope.dbFormGrid.reLoadData();
                });
            });
        } else {
            instance = $modal.open({
                animation: true,
                templateUrl: "views/pc/message/messageTemplateEditorView.html",
                controller: "messageTemplateEditorCtrl",
                size: "lg",
                backdrop: "static",
                resolve: {
                    source: function () {//弹出页面传参函数
                        return source;
                    }
                }
            });


            instance.result.then(function () {//close弹出框 回调函数
                $scope.dbFormGrid.reLoadData();
            });
        }
    }
}]);