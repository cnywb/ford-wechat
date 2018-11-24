/**
 * Created by zhaoliang on 2017/8/27.
 */

var DBApp = angular.module('DBApp');

DBApp.controller("couponListCtrl", ['$scope', '$modal', 'dbUtils','$http','$window', function($scope, $modal, dbUtils,$http,$window){
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {label: "跑批时间", type: "dateRange", name: "create", labelCols: "3"},
                {name: "batchStatus", label: "批处理状态", type: "select",   dropDownItemType: "json", dropDownItem: "batchStatusSelect", labelCols: "3", editable:true },
                {name: "openId", label: "唯一标识", type: "text", placeholder: "请输入微信OpenId",  labelCols: "3"},
                {name: "vin", label: "VIN码", type: "text", placeholder: "请输入VIN码",  labelCols: "3"},
                {name: "projectCode", label: "活动代码", type: "text", placeholder: "请输入活动代码",  labelCols: "3"},
                {name: "status", label: "状态", type: "select",   dropDownItemType: "json", dropDownItem: "statusAuthSelect", labelCols: "3", editable:true }
            ]
        },
        grid: {
            settings: {
                transCode: "couponPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "时间批次", width: "5%", field: "dateNo",lineFeed:true},
                {name: "活动代码", width: "5%", field: "projectCode",lineFeed:true},
                {name: "活动名称", width: "5%", field: "projectName",lineFeed:true},
                {name: "代金券编号", width: "10%", field: "couponNo",lineFeed:true},
                {name: "金额", width: "5%", field: "amount",lineFeed:true},
                {name: "门槛金额", width: "5%", field: "lowestAmount",lineFeed:true},
                {name: "状态", width: "10%", field: "status_",lineFeed:true,class:"status_"},
                {name: "代金券开始日期", width: "10%", field: "startTime",lineFeed:true},
                {name: "代金券失效日期", width: "10%", field: "expiredTime",lineFeed:true},
                {name: "批处理状态", width: "5%", field: "batchStatus_",lineFeed:true,class:"batchStatus_"},
                {name: "经销商服务代码", width: "5%", field: "dealerCode",lineFeed:true},
                {name: "VIN", width: "5%", field: "vin",lineFeed:true},
                {name: "OPENID", width: "10%", field: "openId",lineFeed:true},
                {name: "会员编号", width: "5%", field: "memberNo",lineFeed:true},
                {name: "会员手机号", width: "10%", field: "mobile",lineFeed:true},
                {name: "祝福语", width: "10%", field: "wishing",lineFeed:true}
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                "status_Format":function(val,row){
                    if(row['status']===1){
                        return '领用中';
                    }else if(row['status']===2){
                        return '已领用';
                    }else{
                        return '未领用'
                    }
                },
                "status_Color":function(val,row){
                    if(row['status']===1){
                        return '#004FCC';
                    }else if(row['status']===2){
                        return 'green';
                    }else{
                        return 'red';
                    }

                },
                "batchStatus_Format":function(val,row){
                    if(row['batchStatus']===1){
                        return '已处理';
                    }else{
                        return '未处理'
                    }
                },
                "batchStatus_Color":function(val,row){
                    if(row['batchStatus']===1){
                        return 'green';
                    }else{
                        return 'red';
                    }
                }
            },
            operationEvents: [{
                name: '批量绑定VIN', class: 'btn-primary', icon: 'x-xlsx', click: function () {
                    fastBind();
                }},
                { name: '导出Excel', class: 'btn-primary', icon: 'x-xlsx', click: function () {
                    exportExcel();
                }
            }],
            rowEvents: [{
                name: '绑定', class: 'btn-primary', icon: 'shanchu', isDisabled:function (row) {
                    var status=row['status'];
                    if(status===1){
                        return false;
                    }
                    return true;
                },  click: function (row) {
                    dbUtils.post("vinEntityGet", row, function (value) {
                        $scope.data={};
                        $scope.data.openId = row.openId;
                        $scope.data.couponNo = row.couponNo;
                        $scope.data.projectCode = row.projectCode;
                        $scope.data.projectName = row.projectName;
                        $scope.data.vinList = value;
                        openModal($scope.data);
                    })
                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    /** 打开modal */
    function openModal(source) {

        var instance = $modal.open({
            animation: true,
            templateUrl: 'views/coupon/couponBindVinCtrl.html',
            controller: 'couponBindVinCtrl',
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

    function fastBind() {
        var selectRows = $scope.dbFormGrid.getAllSelectRows();
        if (selectRows.length === 0) {
            dbUtils.info('请选择需要绑定的行');
            return;
        }
        var nos = dbUtils.getFieldArray(selectRows, "couponNo");

        dbUtils.post('fastBindCouponVin', {'nos': nos}, function (data) {
            dbUtils.success("绑定成功"+data.success+"条，失败"+data.failed+"条");
            $scope.dbFormGrid.reLoadData();
        }, function (error) {
            dbUtils.error("绑定处理异常!" + error);
        });
    }


    /** 导出excel */
    function exportExcel() {
        var totalRecords = $scope.dbFormGrid.page.totalRecords;
        if (!totalRecords) {
            dbUtils.warning("无查询数据记录不导出！", "温馨提示");
            return;
        }
        dbUtils.confirm("确定要以当前查询条件下的结果数据执行导出操作?", function () {
            var params = $scope.dbFormGrid.getQueryParams(1);

            var batchStatus = params["batchStatus"] || "";
            var openId = params['openId'] || "";
            var vin = params['vin'] || "";
            var projectCode = params['projectCode'] || "";
            var status = params['status'] || "";
            var createEndDate = params['createEndDate'] || "";
            var createStartDate = params['createStartDate'] || "";


            var url = '../couponListExport.ctl?'
                + 'batchStatus='+batchStatus
                + '&openId='+openId
                + '&vin=' + vin
                + '&projectCode='+projectCode
                + '&status='+status
                + '&createEndDate=' + createEndDate
                + '&createStartDate=' + createStartDate;

            $window.open(url);
        });
    }


}]);
