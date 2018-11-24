/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * QAInfoEntityListCtrl.js 2017-05-16 19:35:16
 */
angular.module('DBApp').controller('esSearchInfoCtrl', ['$scope', '$modal', '$state', 'dbUtils', function ($scope, $modal, $state, dbUtils) {

    var formGridOptions = {
        form: {
            settings: {
                cols: 3
            },
            fields: [
                {name: "keyWord", label: "关键字", type: "text", placeholder: "关键字查询"},
                {name: "type", label: "搜索内容", type: "select", dropDownItem: [{name: "行车养车", value: "info"}, {name: "互问互答", value: "asdfasd"}], defaultValue: {name: "互问互答", value: "asdfasd"}},
                {name: "infoType", label: "所属类型", type: "select", dropDownItemType: "json", dropDownItem: "infoType"}
            ]
        },
        grid: {
            settings: {
                transCode: "esSearchInfoPage",
                autoLoad: false,
                pageSize: 10,
                showCheckBox: false
            },
            header: [
                {"name": "结果内容", "width": "30%", "field": "content"},
                {"name": "标题", "width": "30%", "field": "highLightTitle", class: "highLight"},
                {"name": "摘要", "width": "30%", "field": "highLightDigest", class: "highLight"}
            ],
            rowOperation: {show: true}
        }
    };
    //!!formGridOptions-END!
    var formGridEvents = {
        grid: {
            rowEvents: [//最后的操作列
                {
                    "name": "删除", "class": "btn-danger", icon: "glyphicon-remove", "click": function (row) {
                        dbUtils.confirm("确定删除吗？",function () {
                            dbUtils.post("esSearchInfoDelete", {type: row['type'], esId: row['esId']}, function () {
                                dbUtils.success("删除成功");
                                $scope.dbFormGrid.reLoadData();
                            });
                        });

                }
                }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};
}]);
