/**
 * Created by zhaoliang on 2017/8/31.
 */

var DBApp = angular.module('DBApp');

DBApp.controller("couponDmsListCtrl", ['$scope', '$modal', 'dbUtils','$http','$window', function($scope, $modal, dbUtils,$http,$window){
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {label: "开始时间范围", type: "dateRange", name: "create", labelCols: "3"},
                {name: "targetDealer", label: "责任经销商", type: "text", placeholder: "请输入责任经销商代码",  labelCols: "3"},
                {name: "batchNo", label: "批次号", type: "text", placeholder: "请输入批次号",  labelCols: "3"},
                {name: "vin", label: "VIN码", type: "text", placeholder: "请输入VIN码",  labelCols: "3"},
                {name: "customerMobile", label: "手机号", type: "text", placeholder: "请输入手机号",  labelCols: "3"},
                {name: "sendDmsStatus", label: "DMS发送状态", type: "select",   dropDownItemType: "json", dropDownItem: "sendDmsStatusSelect", labelCols: "3", editable:true },
                {name: "sendSms", label: "短信发送", type: "select",   dropDownItemType: "json", dropDownItem: "sendSmsSelect", labelCols: "3", editable:true }

            ]
        },
        grid: {
            settings: {
                transCode: "couponDmsPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "批次号", width: "5%", field: "batchNo",lineFeed:true},
                {name: "代金券编号", width: "5%", field: "seqNo",lineFeed:true},
                {name: "活动名称", width: "5%", field: "name",lineFeed:true},
                {name: "VIN码", width: "5%", field: "vin",lineFeed:true},
                {name: "活动编号", width: "5%", field: "campaignCode",lineFeed:true},
                {name: "总金额", width: "5%", field: "amount",lineFeed:true},
                {name: "已用金额", width: "5%", field: "useAmount",lineFeed:true},
                {name: "开始日期", width: "5%", field: "validBeginDate",lineFeed:true,class:"status_"},
                {name: "结束日期", width: "5%", field: "validEndDate",lineFeed:true},
                {name: "责任经销商", width: "10%", field: "targetDealer",lineFeed:true},
                {name: "使用经销商", width: "5%", field: "useDealer",lineFeed:true,class:"batchStatus_"},
                {name: "门槛金额", width: "5%", field: "lowestAmount",lineFeed:true},
                {name: "抵扣限额", width: "5%", field: "limitDeduct",lineFeed:true},
                {name: "是否发送DMS", width: "5%", field: "sendDmsStatus",lineFeed:true},
                {name: "发送次数", width: "5%", field: "sendCount",lineFeed:true},
                {name: "DMS发送结果", width: "5%", field: "dmsResult",lineFeed:true},
                {name: "是否取消", width: "5%", field: "cancel_",lineFeed:true},
                {name: "历史记录", width: "5%", field: "sendHistory",lineFeed:true},
                {name: "短信发送", width: "5%", field: "sendSms",lineFeed:true},
                {name: "手机号", width: "5%", field: "customerMobile",lineFeed:true},
                {name: "活动类型", width: "5%", field: "periodType",lineFeed:true},
                {name: "份数", width: "5%", field: "copies",lineFeed:true},
                {name: "标签", width: "5%", field: "TAG",lineFeed:true}
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                "cancel_Format":function(val,row){
                    if(row['cancel']==0){
                        return '否';
                    }else{
                        return '是'
                    }
                },
                // "status_Color":function(val,row){
                //     if(row['status']===1){
                //         return 'green';
                //     }else{
                //         return 'red';
                //     }
                // },
                // "batchStatus_Format":function(val,row){
                //     if(row['batchStatus']===1){
                //         return '已处理';
                //     }else{
                //         return '未处理'
                //     }
                // },
                // "batchStatus_Color":function(val,row){
                //     if(row['batchStatus']===1){
                //         return 'green';
                //     }else{
                //         return 'red';
                //     }
                // }
            },
            // operationEvents: [{
            //     name: '导出Excel', class: 'btn-primary', icon: 'x-xlsx', click: function () {
            //         exportExcel();
            //     }
            // }],
            rowEvents: [{
                name: '修改', class: 'btn-primary', icon: 'shanchu', click: function (row) {
                    openModal(row);
                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    /** 打开modal */
    function openModal(source) {

        var instance = $modal.open({
            animation: true,
            templateUrl: 'db/db-form.html',
            controller: 'couponDmsEditorCtrl',
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

    /** 导出excel */
    // function exportExcel() {
    //     var totalRecords = $scope.dbFormGrid.page.totalRecords;
    //     if (!totalRecords) {
    //         dbUtils.warning("无查询数据记录不导出！", "温馨提示");
    //         return;
    //     }
    //     dbUtils.confirm("确定要以当前查询条件下的结果数据执行导出操作?", function () {
    //         var params = $scope.dbFormGrid.getQueryParams(1);
    //
    //         var batchStatus = params["batchStatus"] || "";
    //         var openId = params['openId'] || "";
    //         var vin = params['vin'] || "";
    //         var projectCode = params['projectCode'] || "";
    //         var status = params['status'] || "";
    //         var createEndDate = params['createEndDate'] || "";
    //         var createStartDate = params['createStartDate'] || "";
    //
    //
    //         var url = '../couponListExport.ctl?'
    //             + 'batchStatus='+batchStatus
    //             + '&openId='+openId
    //             + '&vin=' + vin
    //             + '&projectCode='+projectCode
    //             + '&status='+status
    //             + '&createEndDate=' + createEndDate
    //             + '&createStartDate=' + createStartDate;
    //
    //         $window.open(url);
    //     });
    // }


}]);
