/**
 * Created by zhaoliang on 17/5/27.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("authDmsListCtr", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "batchNo", label: "批次号", type: "text", placeholder: "请输入批次号",  labelCols: "3"},
                {name: "vin", label: "VIN码", type: "text", placeholder: "请输入vin码",  labelCols: "3"},
                {name: "channelType", label: "渠道类型", type: "select",   dropDownItemType: "json", dropDownItem: "dealerTypeSelect", labelCols: "3", editable:true , required: false},
                {name: "verify", label: "解绑/认证", type: "select",   dropDownItemType: "json", dropDownItem: "verifyTypeSelect", labelCols: "3", editable:true , required: false},
                {name: "openId", label: "微信唯一标识", type: "text", placeholder: "请输入微信唯一标识",  labelCols: "3"},
                {name: "sendDmsStatus", label: "发送DMS状态", type: "select",   dropDownItemType: "json", dropDownItem: "sendDmsStatusTypeSelect", labelCols: "3", editable:true , required: false},
                {label: "发送DMS时间", type: "dateRange", name: "create", labelCols: "3"},
            ]
        },
        grid: {
            settings: {
                transCode: "authToDmsPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "批次号", width: "5%", field: "batchNo",lineFeed:true},
                {name: "用户ID", width: "5%", field: "userId",lineFeed:true},
                {name: "姓名", width: "5%", field: "name",lineFeed:true},
                {name: "电话", width: "10%", field: "mobile",lineFeed:true},
                {name: "VIN码", width: "5%", field: "vin",lineFeed:true},
                {name: "微信唯一标识", width: "10%", field: "openId",lineFeed:true},
                {name: "渠道类型", width: "5%", field: "channelType",lineFeed:true},
                {name: "渠道代码", width: "5%", field: "channelCode",lineFeed:true},
                {name: "渠道名称", width: "5%", field: "channelCode",lineFeed:true},
                {name: "日期批次", width: "5%", field: "dateNo",lineFeed:true},
                {name: "认证时间", width: "5%", field: "dcrtDate",lineFeed:true},
                {name: "解绑/认证", width: "5%", field: "verify_",lineFeed:true},
                {name: "渠道类型", width: "5%", field: "type_",lineFeed:true},
                {name: "经销商名称", width: "20%", field: "dealerName",lineFeed:true},
                {name: "大区", width: "5%", field: "bigArea",lineFeed:true},
                {name: "小区", width: "10%", field: "smallArea",lineFeed:true},
                {name: "发送DMS时间", width: "5%", field: "sendDmsDate",lineFeed:true},
                {name: "发送DMS状态", width: "20%", field: "sendDmsStatus_",lineFeed:true},
                {name: "发送DMS结果", width: "20%", field: "sendDmsResult",lineFeed:true},
                {name: "解绑时间", width: "5%", field: "unauthDate",lineFeed:true}
            ],
            rowOperation: {show: false}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                "verify_Format":function(val,row){
                    if(row['verify']===1){
                        return '认证';
                    }else{
                        return '解绑'
                    }
                },
                "type_Format":function(val,row){
                    if(row['channelType']===1){
                        return '意见领袖';
                    }else if(row['channelType']===2){
                        return '车主手册';
                    }else if(row['channelType']===3){
                        return  '活动';
                    }else{
                        return '经销商'
                    }
                },
                "sendDmsStatus_Format":function(val,row){
                    if(row['sendDmsStatus']===0){
                        return '不发送';
                    }else if(row['sendDmsStatus']===1){
                        return '待发送';
                    }else if(row['sendDmsStatus']===2){
                        return  '失败';
                    }else{
                        return '成功'
                    }
                }
            }
       /*     operationEvents: [{
                name: "新增", class: "btn-success", icon: "tianjia", click: function () {
                    openModal();
                }
            }],
            rowEvents: [{
                name: '编辑', class: 'btn-primary', icon: 'shanchu', click: function (row) {
                    openModal(row);
                }
            }, {
                name: "删除", class: "btn-danger", icon: "guanbi", click: function (row) {
                    quit(row);
                }
            }]*/
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //打开modal
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
