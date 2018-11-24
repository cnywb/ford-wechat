/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * InfoEntityListCtrl.js 2017-05-16 19:31:48
 */
angular.module('DBApp').controller('infoEntityManageCtrl', ['$scope', '$modal', '$state', 'dbUtils', function ($scope, modal, $state, dbUtils) {

    var formGridOptions = {
        form: {
            settings: {
                cols: 3
            },
            fields: [
                {name: "keyWord", label: "关键字", type: "text", placeholder: "关键字查询"}
            ]
        },
        grid: {
            settings: {
                transCode: "infoEntityPage",
                autoLoad: true,
                showCheckBox: true,
                lineFeed: true
            },
            header: [
                {"name": "标题", "width": "10%", "field": "title"},
                {"name": "图片地址", "width": "10%", "field": "iconUrl", type: 'img'},
                {"name": "摘要", "width": "10%", "field": "digest"},
                {"name": "标签", "width": "10%", "field": "tags"},
                {"name": "是否已索引", "width": "20%", "field": "indexed"},
                {"name": "ES索引ID", "width": "20%", "field": "esId"},
                {"name": "原文地址", "width": "10%", "field": "sourceHref_"}
            ],
            rowOperation: {show: true}
        }
    };
    //!!formGridOptions-END!
    var formGridEvents = {
        grid: {
            //字段渲染时相关事件
            fieldEvents: {
                "titleFormat": function (value) {
                    if (value && value.length > 20) {
                        return value.substr(0, 20);
                    }
                    return value;
                },
                "sourceHref_Format": function (value, row) {
                    if (row['sourceHref']) {
                        return "查看原文";
                    }
                    return "";
                },
                "digestFormat": function (value) {
                    if (value && value.length > 20) {
                        return value.substr(0, 20) + "...";
                    }
                },
                "indexedFormat": function (indexed) {
                    if (indexed == true) {
                        return "已索引";
                    }
                    return "未索引";
                },
                "sourceHref_Click": function (row) {
                    if (row['sourceHref']) {
                        window.open(row['sourceHref'], '_blank');
                    }
                },
                "indexedColor": function (value,row) {
                    if (value =='已索引') {
                        return "green";
                    }
                    return "red";
                }
            },
            rowEvents: [{
                name: "修改", class: "btn-primary", icon: "glyphicon-edit", click: function (currentRecord) {
                    $state.go("infoEntityEntry", {id: currentRecord['id']});
                },

            },
                {
                    name: "重建索引", class: "btn-primary", icon: "glyphicon-edit", click: function (currentRecord) {
                    dbUtils.confirm("确定重建索引吗？", function () {
                        dbUtils.post('infoEntityEs', {'id': currentRecord['id']}, function () {
                            dbUtils.success("重建索引成功!");
                            $scope.dbFormGrid.reLoadData();
                        }, function (error) {
                            dbUtils.error("创建索引失败!" + error);
                        });
                    });
                }
                }
            ],
            operationEvents: [{
                name: "新增", class: "btn-primary", icon: "glyphicon-plus", click: function () {
                    $state.go("infoEntityEntry");
                }
            }, {
                name: "删除", class: "btn-danger", icon: "glyphicon-floppy-remove", click: function (rows) {
                    if (rows.length == 0) {
                        dbUtils.error("请选择要删除的行！");
                        return;
                    }
                    var ids = dbUtils.getFieldArray(rows, 'id');
                    dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
                        dbUtils.post('infoEntityDelete', {'ids': ids}, function (data) {
                            dbUtils.success("删除成功！!");
                            $scope.dbFormGrid.reLoadData();
                        }, function (error) {
                            dbUtils.error("删除处理异常!" + error);
                        });
                    });

                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //新增或修改
    function openModal(source) {
        var labelCols = 3;
        var instance = modal.open({
            animation: true,
            templateUrl: 'db/db-form.html',
            size: "lg",
            controller: ['$scope', '$modalInstance', '$timeout', 'dbUtils', 'source', function ($scope, $modalInstance, $timeout, dbUtils, source) {
                //!!FORM--START!!
                $scope.dbForm = {
                    settings: {showClose: true, transCode: "infoEntityHandle", cols: 1},
                    title: {icon: "glyphicon-edit", label: "新增或者编辑"},
                    sections: [
                        {
                            sectionTitle: {show: false, icon: "glyphicon-list", label: ""},
                            fields: [
                                {
                                    "name": "tags",
                                    "label": "标签",
                                    type: "text",
                                    disabled: false,
                                    required: true,
                                    placeholder: "标签",
                                    labelCols: labelCols,
                                    cols: 10
                                },
                                {
                                    "name": "title",
                                    "label": "标题",
                                    type: "text",
                                    disabled: false,
                                    required: true,
                                    placeholder: "标题",
                                    labelCols: labelCols,
                                    cols: 10
                                },
                                {
                                    "name": "iconUrl",
                                    "label": "图片地址",
                                    type: "text",
                                    disabled: false,
                                    required: true,
                                    placeholder: "图片地址",
                                    labelCols: labelCols,
                                    cols: 10
                                },
                                {
                                    "name": "digest",
                                    "label": "摘要",
                                    type: "text",
                                    disabled: false,
                                    required: true,
                                    placeholder: "摘要",
                                    rows: 4,
                                    labelCols: labelCols,
                                    cols: 10
                                },
                                {
                                    "name": "content",
                                    "label": "内容",
                                    type: "textarea",
                                    placeholder: "内容",
                                    rows: 5,
                                    labelCols: labelCols,
                                    cols: 10
                                },
                                {
                                    "name": "sourceHref",
                                    "label": "原文地址",
                                    type: "text",
                                    placeholder: "原文地址",
                                    labelCols: labelCols,
                                    cols: 10
                                },
                            ]
                        }],
                    defaultData: {},
                    originData: source,
                    events: {
                        beforeSubmit: function (reqBody) {
                            if (!reqBody['content'] && !reqBody['sourceHref']) {
                                dbUtils.error("原文地址或内容必须有一个存在!");
                                return false;
                            }
                        },
                        afterSubmit: function () {
                            dbUtils.success("提交成功!");
                            $modalInstance.close();
                        },
                        modalClose: function () {
                            $modalInstance.dismiss("cancel");
                        }
                    }
                };
                //!!FORM-END!!
            }],
            backdrop: "static",
            resolve: {
                source: function () {
                    return source;
                }
            }
        });


        instance.result.then(function () {
            $scope.dbFormGrid.reLoadData();
        });
    }
}]);
