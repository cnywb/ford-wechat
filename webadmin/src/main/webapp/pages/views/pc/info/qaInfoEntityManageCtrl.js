/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * QAInfoEntityListCtrl.js 2017-05-16 19:35:16
 */
angular.module('DBApp').controller('qaInfoEntityManageCtrl', ['$scope', '$modal', '$state', 'dbUtils', function ($scope, $modal, $state, dbUtils) {

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
                transCode: "qaInfoEntityPage",
                autoLoad: true,
                showCheckBox: true
            },
            header: [
                {"name": "问题", "width": "20%", "field": "question"},
                {"name": "标签", "width": "20%", "field": "tags"},
                {"name": "是否已索引", "width": "20%", "field": "indexed"},
                {"name": "ES索引ID", "width": "20%", "field": "esId"}
            ],
            rowOperation: {show: true}
        }
    };
    //!!formGridOptions-END!
    var formGridEvents = {
        grid: {
            fieldEvents: {
                "indexedFormat": function (value) {
                    return value == true ? "已索引" : "未索引";
                },
                "indexedColor": function (value) {
                    if (value =='已索引') {
                        return "green";
                    }
                    return "red";
                }
            },
            rowEvents: [{
                name: "修改", class: "btn-primary", icon: "glyphicon-edit", click: function (currentRecord) {
                    $state.go("qaInfoEntityEntry", {id: currentRecord['id']});
                }
            }, {
                name: "重建索引", class: "btn-primary", icon: "glyphicon-edit", click: function (currentRecord) {
                    dbUtils.post('qaInfoEntityEs', {'id': currentRecord['id']}, function () {
                        dbUtils.success("重建索引成功!");
                        $scope.dbFormGrid.reLoadData();
                    }, function (error) {
                        dbUtils.error("创建索引失败!" + error);
                    });
                }
            }],
            operationEvents: [{
                name: "新增", class: "btn-primary", icon: "glyphicon-plus", click: function () {
                    $state.go("qaInfoEntityEntry");
                }
            }, {
                name: "删除", class: "btn-danger", icon: "glyphicon-floppy-remove", click: function (rows) {
                    if (rows.length == 0) {
                        dbUtils.error("请选择要删除的行！");
                        return;
                    }
                    var ids = dbUtils.getFieldArray(rows, "id");
                    dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
                        dbUtils.post('qaInfoEntityDelete', {'ids': ids}, function (data) {
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
        var instance = $modal.open({
            animation: true,
            templateUrl: 'db/db-form.html',
            size: "lg",
            controller: ['$scope', '$modalInstance', '$timeout', 'dbUtils', 'source', function ($scope, $modalInstance, $timeout, dbUtils, source) {
                //!!FORM--START!!
                $scope.dbForm = {
                    settings: {showClose: true, transCode: "qaInfoEntityHandle", cols: 1},
                    title: {icon: "glyphicon-edit", label: "新增或者编辑"},
                    sections: [
                        {
                            sectionTitle: {show: false, icon: "glyphicon-list", label: ""},
                            fields: [
                                {"name": "question", "label": "问题", type: "text", required: true, placeholder: "问题", labelCols: 2},
                                {"name": "tags", "label": "标签", type: "text", placeholder: "标签：标签1,标签2", labelCols: 2},
                                {"id": "answerEditor", "label": "答案", type: "wangEditor", required: true, placeholder: "答案", labelCols: 2}
                            ]
                        }],
                    defaultData: {},
                    originData: source,
                    events: {
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

        instance.rendered.then(function () {
            //必须在页面渲染完成后才能初始化富文本编辑器。在controller当中通过全局函数获取实例
            window.setTimeout(function () {
                window.editorDiscoveryContentEditor = null;
                window.editorDiscoveryContentEditor = new wangEditor('answerEditor');
                window.editorDiscoveryContentEditor.config.menus = [
                    'source',
                    '|',
                    'bold',
                    'underline',
                    'italic',
                    'strikethrough',
                    'eraser',
                    'forecolor',
                    'bgcolor',
                    '|',
                    'quote',
                    'fontfamily',
                    'fontsize',
                    'head',
                    'unorderlist',
                    'orderlist',
                    'alignleft',
                    'aligncenter',
                    'alignright',
                    '|',
                    'link',
                    'unlink',
                    'table',
                    'emotion',
                    '|',
                    'img',
                    '|',
                    'fullscreen'
                ];
                window.editorDiscoveryContentEditor.config.uploadImgUrl = '../resourceStore/resourceStoreEditor.ctl';
                window.editorDiscoveryContentEditor.create();
                window.editorDiscoveryContentEditor.$txt.html(angular.isUndefined(source) ? "" : source['answer']);
            }, 300);
        });
        instance.result.then(function () {
            $scope.dbFormGrid.reLoadData();
        });
    }
}]);
