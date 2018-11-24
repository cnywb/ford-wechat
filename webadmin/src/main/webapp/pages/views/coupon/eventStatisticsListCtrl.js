/**
 * Created by zhaoliang on 2017/8/27.
 */

var DBApp = angular.module('DBApp');

DBApp.controller("eventStatisticsListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "projectCode", label: "活动代码", type: "text", placeholder: "请输入活动代码",  labelCols: "3"},
                {label: "跑批时间", type: "dateRange", name: "create", labelCols: "3"}
            ]
        },
        grid: {
            settings: {
                transCode: "eventStatisticsPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                // {name: "批次号", width: "8%", field: "batchNo",lineFeed:true},
                {name: "时间批次", width: "8%", field: "dateNo",lineFeed:true},
                {name: "活动代码", width: "8%", field: "projectCode",lineFeed:true},
                {name: "活动名称", width: "8%", field: "projectName",lineFeed:true},
                {name: "每日访问人数", width: "8%", field: "visitCount",lineFeed:true},
                {name: "每次人抽奖人数", width: "8%", field: "drawCount",lineFeed:true},
                {name: "短信发送数", width: "8%", field: "smsSendCount",lineFeed:true},
                {name: "短信发送失败数量", width: "8%", field: "smsFailedCount",lineFeed:true},
                {name: "每日认证人数", width: "8%", field: "authenCount",lineFeed:true},
                {name: "因活动认证人数", width: "8%", field: "authenCountWithEvent",lineFeed:true},
                {name: "抽中人数", width: "8%", field: "winnerCount",lineFeed:true},
                {name: "老认证用户获券人数", width: "9%", field: "originalWinnerCount",lineFeed:true}
            ],
            rowOperation: {show: false}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                // "type_Format":function(val,row){
                //     if(row['type']===1){
                //         return '意见领袖';
                //     }else if(row['type']===2){
                //         return '车主手册';
                //     }else if(row['type']===3){
                //         return  '活动';
                //     }else{
                //         return '经销商'
                //     }
                // }
            },
            // operationEvents: [{
            //     name: "新增", class: "btn-success", icon: "tianjia", click: function () {
            //         openModal();
            //     }
            // }],
            rowEvents: [{
                name: '编辑', class: 'btn-primary', icon: 'shanchu', click: function (row) {
                    openModal(row);
                }
            }, {
                name: "删除", class: "btn-danger", icon: "guanbi", click: function (row) {
                    quit(row);
                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    /** 打开modal */
    // function openModal(source) {
    //
    //     var instance = $modal.open({
    //         animation: true,
    //         templateUrl: 'db/db-form.html',
    //         controller: 'dealerEditorCtrl',
    //         size: "md",
    //         backdrop: "static",
    //         resolve: {
    //             source: function () {
    //                 return source;
    //
    //             }
    //         }
    //     });
    //     instance.result.then(function () {
    //         $scope.dbFormGrid.reLoadData();
    //     });
    // }

    /**
     * 删除操作
     */
    // function quit(source) {
    //     var ids = [source.id];
    //
    //     dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
    //         dbUtils.post('scanDealerRemove', {'ids': ids}, function (data) {
    //             dbUtils.success("删除成功！!");
    //             $scope.dbFormGrid.reLoadData();
    //         }, function (error) {
    //             dbUtils.error("删除处理异常!" + error);
    //         });
    //     });
    // }

}]);
