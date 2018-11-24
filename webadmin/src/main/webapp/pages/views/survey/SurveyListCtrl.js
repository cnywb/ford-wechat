/**
 * Created by zhaoliang on 2017/5/5.
 */

var DBApp = angular.module('DBApp');

DBApp.controller("SurveyListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "status", label: "项目状态", type: "select",   dropDownItemType: "json", dropDownItem: "surveyStatus", labelCols: "3", editable:true , required: false}
                // {name: "status", label: "项目状态", type: "select", placeholder: "请输入项目ID",  labelCols: "3"},
            ]
        },
        grid: {
            settings: {
                transCode: "surveyPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: false
            },
            header: [
                {name: "用户编号", width: "5%", field: "user",lineFeed:true},
                {name: "项目短id", width: "10%", field: "short_id",lineFeed:true},
                {name: "项目id", width: "5%", field: "project_id",lineFeed:true},
                {name: "项目标题", width: "10%", field: "title",lineFeed:true},
                {name: "项目状态", width: "10%", field: "status",lineFeed:true},
                {name: "创建时间", width: "10%", field: "create_time",lineFeed:true},
                {name: "更新时间", width: "10%", field: "update_time",lineFeed:true},
                {name: "答题数量", width: "10%", field: "respondent_count",lineFeed:true},
                {name: "项目类型", width: "10%", field: "ptype",lineFeed:true},
            ],
            rowOperation: {show: false}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                "statusFormat":function(val,row){
                    if(row['status'] == 0){
                        return '未发布';
                    }else if(row['status']== 1){
                        return '收集中';
                    }else if(row['status']== 2){
                        return '已结束';
                    }else if(row['status']== 3){
                        return '暂停中';
                    }else if(row['status']== -2){
                        return '已删除';
                    }else if(row['status']== -1){
                        return '永久删除';
                    }else{
                        return row['status']
                    }
                }
            },
            rowEvents: [{
                name: '生成链接', class: 'btn-primary', icon: 'guanbi', click: function (row) {
                    dbUtils.post("surveyLink", {shortId: row['short_id']}, function(data) {
                        openModal(data);
                    })

                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //打开modal
    function openModal(source) {

        var instance = $modal.open({
            animation: true,
            templateUrl: 'db/db-form.html',
            controller: 'SurveyLinkCtrl',
            size: "md",
            backdrop: "static",
            resolve: {
                source: function () {
                    return source;
                }
            }
        });
        instance.result.then(function () {

        });
    }
}]);
