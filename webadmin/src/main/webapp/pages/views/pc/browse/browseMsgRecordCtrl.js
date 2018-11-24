/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * browseMsgRecordCtrl.js 2017-05-16 19:35:16
 */
angular.module('DBApp').controller('browseMsgRecordCtrl', ['$scope', '$modal', '$state', '$window', 'dbUtils', function ($scope, $modal, $state, $window, dbUtils) {

    var formGridOptions = {
        form: {
            settings: {
                cols: 3
            },
            fields: [
                {name: "type", label: "统计方式", type: "select", dropDownItem: [{name: "天", value: "天"}, {name: "时", value: "时"}], defaultValue: {name: "天", value: "天"}, labelCols: 3},
                {label: "统计区间", type: "dateRange", labelCols: 3}
            ]
        },
        grid: {
            settings: {
                transCode: "msgRecordPage",
                autoLoad: true,
                pageSize: 10,
                showCheckBox: false
            },
            header: [
                {"name": "公告ID", "width": "20%", "field": "id"},
                {"name": "公告标题", "width": "20%", "field": "name"},
                {"name": "阅读日期", "width": "20%", "field": "readTime"},
                {"name": "阅读量", "width": "20%", "field": "times"}
            ],
            rowOperation: {show: false}
        }
    };
    //!!formGridOptions-END!
    var formGridEvents = {
        grid: {
            operationEvents: [//最后的操作列
                {
                    name: "导出", class: "btn-primary", icon: "glyphicon-export", click: function () {
                    excelExport();
                }
                }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};
    function excelExport() {
        var totalRecords = $scope.dbFormGrid.page.totalRecords;
        if (!totalRecords) {
            dbUtils.warning("无查询数据记录不导出！", "温馨提示");
            return;
        }
        dbUtils.confirm("确定要以当前查询条件下的结果数据执行导出操作?", function () {
            var params = $scope.dbFormGrid.getQueryParams(1);
            var type = params['type'] || "";
            var startDate = params['startDate'] || "";
            var endDate = params['endDate'] || "";
            var url = '../msgRecordExport.ctl?type=' + type + '&startDate=' + startDate + '&endDate=' + endDate;
            $window.open(url);
        });
    }
}]);
