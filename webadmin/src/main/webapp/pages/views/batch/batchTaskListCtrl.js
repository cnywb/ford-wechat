/**
 * Created by zhaoliang on 17/5/27.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("batchTaskListCtr", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {label: "创建时间", type: "dateRange", name: "create", labelCols: "3"},
                {name: "batchNo", label: "批次号", type: "text", placeholder: "请输入批次号",  labelCols: "3"},
                {name: "runTimes", label: "处理次数", type: "text", placeholder: "请输入处理次数",  labelCols: "3"},
                {name: "status", label: "状态", type: "select",   dropDownItemType: "json", dropDownItem: "statusTypeSelect", labelCols: "3", editable:true , required: true}
            ]
        },
        grid: {
            settings: {
                transCode: "batchTaskPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "批次号", width: "5%", field: "batchNo",lineFeed:true},
                {name: "数据类型", width: "5%", field: "dataTypeName",lineFeed:true},
                {name: "任务状态", width: "20%", field: "status_",lineFeed:true,class:"status_"},
                {name: "执行时间", width: "5%", field: "startDate",lineFeed:true},
                {name: "结束时间", width: "10%", field: "endDate",lineFeed:true},
                {name: "处理次数", width: "5%", field: "runTimes",lineFeed:true},
                {name: "处理结果", width: "5%", field: "batchResult",lineFeed:true},
                {name: "业务参数", width: "5%", field: "params",lineFeed:true},
                {name: "业务编号", width: "10%", field: "businessId",lineFeed:true},
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                "status_Format":function(val,row){
                    if(row['status']===1){
                        return '待处理';
                    }else if(row['status']===2){
                        return '处理中';
                    }else if(row['status']===3){
                        return  '已处理';
                    }else{
                        return '失败';
                    }
                },
                "status_Color":function(val,row){
                    if(row['status']===1){
                        return '#F89406';
                    }else if(row['status']===2){
                        return '#004FCC';
                    }else if(row['status']===3){
                        return  'green';
                    }else{
                        return 'red';
                    }
                }
            },
            operationEvents: [{
                name: "新增", class: "btn-success", icon: "tianjia", click: function () {
                    openModal();
                }
            }],
            rowEvents: [{
                name: '再次执行', class: 'btn-primary', icon: 'shanchu',isDisabled:function (row) {
                    var status=row['status'];
                    if(status===4){
                        return false;
                    }
                    return true;
                },  click: function (row) {
                    dbUtils.confirm("确认是否再次执行", function () {
                        dbUtils.post('batchTaskHandle',row, function (data) {
                            dbUtils.success("执行成功！!");
                            $scope.dbFormGrid.reLoadData();
                        }, function (error) {
                            dbUtils.error("处理异常!" + error);
                        });
                    });
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
            controller: 'batchTaskEditorCtrl',
            size: "md",
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
    // 删除操作
/*    function quit(source) {
        var ids = [source.id];

        dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
            dbUtils.post('scanDealerRemove', {'ids': ids}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }*/

}]);
