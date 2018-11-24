/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntityListCtrl.js 2017-05-05 15:37:27
 */
angular.module('DBApp').controller('iconMenuEntityCtrl', ['$scope', '$modal', 'dbUtils', function ($scope, modal, dbUtils) {

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
                transCode: "iconMenuEntityPage",
                autoLoad: true,
                showCheckBox: true,
                lineFeed: true
            },
            header: [
                {"name": "名称", "width": "10%", "field": "name"},
                {"name": "所属分类", "width": "10%", "field": "typeName"},
                {"name": "链接地址", "width": "10%", "field": "href"},
                {"name": "图标", "width": "10%", "field": "imageUrl", type: 'img'},
                {"name": "排序", "width": "10%", "field": "order"},
                {"name": "推荐到首页", "width": "10%", "field": "recommend_"},
                {"name": "首页截止时间", "width": "10%", "field": "recommendEndTime"},
                {"name": "标红", "width": "10%", "field": "redTip"},
                {"name": "是否需要认证", "width": "10%", "field": "requiredAuth"},
            ],
            rowOperation: {show: true}
        }
    };
    //!!formGridOptions-END!
    var formGridEvents = {
        grid: {
            /*字段渲染时相关事件*/
            fieldEvents: {
                "recommend_Format": function (value, row) {
                    row["recommend_"] = value;
                    if (row["recommend"]) {
                        row["recommend_"] = "是";
                    } else {
                        row["recommend_"] = "否";
                    }
                    return row["recommend_"];
                },
                "recommend_Color": function (value, row) {
                    return value == "是" ? "red" : '';
                },
                "redTipColor": function (value, row) {
                    return value ? "red" : '';
                },
                "requiredAuthColor": function (value, row) {
                    return value ? "red" : '';
                },
            },
            rowEvents: [{
                name: "修改", class: "btn-primary", icon: "glyphicon-edit", click: function (currentRecord) {
                    openModal(currentRecord);
                }, isDisabled: function (currentRecord) {
                    return false;
                }
            }],
            operationEvents: [{
                name: "一键缓存", class: "btn-primary", icon: "", click: function () {
                    dbUtils.confirm("确定要一键缓存？确认后立刻生效！", function () {
                        dbUtils.post('iconMenuCache', {}, function () {
                            dbUtils.success("一键缓存成功！!");
                        }, function (error) {
                            dbUtils.error("一键缓存异常!" + error);
                        });
                    });
                }
            }, {
                name: "删除", class: "btn-danger", icon: "", click: function (rows) {
                    if (rows.length == 0) {
                        dbUtils.error("请选择要删除的行！");
                        return;
                    }
                    var ids = [];
                    angular.forEach(rows, function (row) {
                        ids.push(row.id);
                    });
                    dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
                        dbUtils.post('iconMenuEntityDelete', {'ids': ids}, function (data) {
                            dbUtils.success("删除成功！!");
                            $scope.dbFormGrid.reLoadData();
                        }, function (error) {
                            dbUtils.error("删除处理异常!" + error);
                        });
                    });
                }
            }, {
                name: "新增", class: "btn-primary", icon: "", click: function (rows) {
                    openModal();
                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //新增或修改
    function openModal(source) {
        var instance = modal.open({
            animation: true,
            templateUrl: 'db/db-form.html',
            size: "lg",
            controller: ['$scope', '$modalInstance', '$timeout', 'dbUtils', 'source', function ($scope, $modalInstance, $timeout, dbUtils, source) {
                //!!FORM--START!!
                $scope.dbForm = {
                    settings: {showClose: true, transCode: "iconMenuEntityHandle", cols: 2},
                    title: {icon: "glyphicon-edit", label: "新增或者编辑"},
                    sections: [
                        {
                            sectionTitle: {show: false, icon: "glyphicon-list", label: ""},
                            fields: [
                                {"name": "name", "label": "名称", type: "text", disabled: false, required: true, placeholder: "如：工单查询"},
                                {"name": "href", "label": "链接地址", type: "text", disabled: false, required: true, placeholder: "可以使内部路由地址或http绝对地址"},
                                {"name": "imageUrl", "label": "图片地址", type: "text", disabled: false, required: true, placeholder: "绝对路径或根路径如：/static/img/icon-zhangdan.png"},
                                {"name": "order", "label": "排序", type: "text", disabled: false, required: true, placeholder: "数字越大越在前面"},
                                {
                                    "name": "recommend", "label": "推荐到首页", type: "select", disabled: false, required: true, placeholder: "",
                                    dropDownItemType: "json", dropDownItem: [{"name": "是", "value": true}, {"name": "否", "value": false}]
                                },
                                {
                                    "name": "redTip", "label": "红标提示", type: "select", disabled: false, required: true, placeholder: "",
                                    dropDownItemType: "json", dropDownItem: [{"name": "是", "value": true}, {"name": "否", "value": false}]
                                },
                                {
                                    "name": "requiredAuth", "label": "需要认证", type: "select", disabled: false, required: true, placeholder: "",
                                    dropDownItemType: "json", dropDownItem: [{"name": "是", "value": true}, {"name": "否", "value": false}]
                                },
                            ]
                        }],
                    defaultData: {recommend: "false", redTip: "false", requiredAuth: "false",},
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


        instance.result.then(function () {
            $scope.dbFormGrid.reLoadData();
        });
    }
}]);
