/**
 * Created by zhaoliang on 2017/5/5.
 */

var DBApp = angular.module('DBApp');

DBApp.controller("SurveyResultsListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "shortId", label: "项目ID", type: "text", placeholder: "请输入项目ID",  labelCols: "3", require: true},
            ]
        },
        grid: {
            settings: {
                transCode: "surveyResultPage",
                autoLoad: false,
                page: {pageSize: 10},
                showCheckBox: false
            },
            header: [
                {name: "答卷序号", width: "5%", field: "seq",lineFeed:true},
                {name: "微信标识", width: "10%", field: "source",lineFeed:true},
                {name: "答题时长", width: "10%", field: "timeUsed",lineFeed:true},
                {name: "答题ip", width: "10%", field: "ip",lineFeed:true},
                {name: "开始时间", width: "10%", field: "start",lineFeed:true},
                {name: "结束时间", width: "10%", field: "finish",lineFeed:true},
                {name: "答题状态", width: "10%", field: "rspdStatus",lineFeed:true},
            ],
            rowOperation: {show: false}
        }
    };

    var formGridEvents = {
        grid: {

        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

}]);
