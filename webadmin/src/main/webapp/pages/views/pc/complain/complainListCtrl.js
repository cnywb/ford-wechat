/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * browseMsgRecordCtrl.js 2017-05-16 19:35:16
 */
angular.module('DBApp').controller('complainListCtrl', ['$scope', '$modal', '$state', '$window', 'dbUtils', function ($scope, $modal, $state, $window, dbUtils) {

    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {label: "投诉建议时间", type: "dateRange", labelCols: 3}
            ]
        },
        grid: {
            settings: {
                transCode: "complainPage",
                autoLoad: true,
                pageSize: 10,
                showCheckBox: false,
                lineFeed: true
            },
            header: [
                {"name": "投诉时间", "width": "9%", "field": "firstInsert"},
                {"name": "姓名", "width": "9%", "field": "customerName"},
                {"name": "联系电话", "width": "9%", "field": "customerMobile"},
                {"name": "VIN码", "width": "9%", "field": "customerVin"},
                {"name": "车型", "width": "9%", "field": "carModel"},
                {"name": "车牌号", "width": "9%", "field": "license"},
                {"name": "行驶里程", "width": "9%", "field": "exerciseMileage"},
                {"name": "投诉类型", "width": "9%", "field": "complainReason"},
                {"name": "投诉经销商", "width": "9%", "field": "involveDealer"},
                {"name": "投诉问题", "width": "9%", "field": "incidentDesc"}
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
            var startDate = params['startDate'] || "";
            var endDate = params['endDate'] || "";
            var url = '../complainExport.ctl?startDate=' + startDate + '&endDate=' + endDate;
            $window.open(url);
        });
    }
}]);
