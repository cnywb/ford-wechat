/**
 * Created by maojiawei on 16/3/30.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("SurveyCallbackListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "shortId", label: "短项目ID", type: "text", placeholder: "请输入问卷网短项目ID",  labelCols: "4"},
                {name: "openId", label: "微信OpenId", type: "text", placeholder: "请输入微信OpenId",  labelCols: "4"},
            ]
        },
        grid: {
            settings: {
                transCode: "surveyCallbackPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "短项目ID", width: "20%", field: "shortId"},
                {name: "微信OpenId", width: "20%", field: "openId"},
                {name: "答题状态", width: "20%", field: "status"},
                {name: "回调时间戳", width: "20%", field: "timestamp"},
                {name: "回调接口地址", width: "20%", field: "callbackUrl"},
                {name: "问卷网回调签名", width: "20%", field: "signature"}
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {

            },
            operationEvents: []
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

}]);
