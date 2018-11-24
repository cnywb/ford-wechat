/**
 * Created by zhaoliang on 2017/8/27.
 */

var DBApp = angular.module('DBApp');
DBApp.controller("messageSendListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {label: "发送时间", type: "dateRange", name: "create", labelCols: "3"},
                // {name: "openId", label: "微信唯一标识", type: "text", placeholder: "请输入openId",  labelCols: "3"},
                {name: "mobile", label: "手机号", type: "text", placeholder: "请输入手机号",  labelCols: "3"},
                {name: "projectCode", label: "活动代码", type: "text", placeholder: "请输入活动代码",  labelCols: "3"},
                {name: "vin", label: "VIN码", type: "text", placeholder: "请输入VIN码",  labelCols: "3"},
                {name: "sendResult", label: "发送结果", type: "select",   dropDownItemType: "json", dropDownItem: "sendResultSelect", labelCols: "3", editable:true}
            ]
        },
        grid: {
            settings: {
                transCode: "messageSendPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "时间批次", width: "5%", field: "dateNo",lineFeed:true},
                {name: "活动代码", width: "5%", field: "projectCode",lineFeed:true},
                {name: "活动名称", width: "5%", field: "projectName",lineFeed:true},
                {name: "触发方式", width: "5%", field: "sendChannel",lineFeed:true},
                {name: "VIN", width: "10%", field: "vin",lineFeed:true},
                // {name: "OPENID", width: "10%", field: "openId",lineFeed:true},
                // {name: "会员编号", width: "5%", field: "memberNo",lineFeed:true},
                {name: "手机号", width: "10%", field: "mobile",lineFeed:true},
                {name: "发送状态", width: "5%", field: "statusCode",lineFeed:true},
                {name: "发送结果", width: "5%", field: "sendResult",lineFeed:true,class:"sendResult_"},
                {name: "发送时间", width: "10%", field: "sendTime",lineFeed:true},
                {name: "短信内容", width: "15%", field: "content",lineFeed:true}
            ],
            rowOperation: {show: false}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                // "sendResult_Color":function(val,row){
                //     if(row['sendResult']=="成功"){
                //         return 'green';
                //     }else{
                //         return 'red';
                //     }
                // },
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

    /** 删除操作 */
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
