/**
 * Created by zhaoliang on 2017/5/24.
 */

var DBApp = angular.module('DBApp');

DBApp.controller("carOwnerAuthenStatusListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "userVin", label: "VIN码", type: "text", placeholder: "请输入VIN码",  labelCols: "3"},
                {name: "openId", label: "微信唯一编号", type: "text", placeholder: "请输入微信编号",  labelCols: "3"},
                {name: "userMobile", label: "认证手机", type: "text", placeholder: "请输入认证手机",  labelCols: "3"},
                {name: "authState", label: "状态", type: "select",   dropDownItemType: "json", dropDownItem: "statusAuthenSelect", labelCols: "3", editable:true , required: true}
            ]
        },
        grid: {
            settings: {
                transCode: "carOwnerAuthenStatusPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "VIN码", width: "10%", field: "userVin",lineFeed:true},
                {name: "微信唯一编号", width: "20%", field: "openId",lineFeed:true},
                {name: "认证姓名", width: "10%", field: "userName",lineFeed:true},
                {name: "认证手机", width: "10%", field: "userMobile",lineFeed:true},
                {name: "申请时间", width: "10%", field: "createDate",lineFeed:true},
                {name: "认证状态", width: "10%", field: "type_",lineFeed:true,class:"type_"},
                {name: "审核次数", width: "10%", field: "times",lineFeed:true},
                {name: "审核结果", width: "5%", field: "result_",lineFeed:true},
                {name: "审核方式", width: "10%", field: "authWay",lineFeed:true},
                {name: "审核时间", width: "10%", field: "authDate",lineFeed:true}
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                "type_Format":function(val,row){
                    if(row['authState']===1){
                        return '已审核';
                    } else{
                        return '未审核'
                    }
                },
                "type_Color":function(val,row){
                    if(row['authState']===1) {
                        return 'green';
                    }else{
                        return 'red';
                    }
                },
                "result_Format":function(val,row){
                    if(row['authResult']===1){
                        return '通过';
                    }else if(row['authResult']===0){
                        return ' ';
                    }else{
                        return '未通过'
                    }
                },
            },
      /*      operationEvents: [{
                name: "新增", class: "btn-success", icon: "tianjia", click: function () {
                    openModal();
                }
            }],*/
            rowEvents: [{
                name: '审核',  class: 'btn-primary', icon: 'shanchu', isDisabled:function (row) {
                    var status=row['authState'];
                    if(status===1){  //1 是已审核
                        return true;
                    }
                    return false;
                }, click: function (row) {
                    dbUtils.post("imageUrlEntityGet", row, function (data) {
                        console.log(data);
                        $scope.imgData=data;
                        row['imgData']=$scope.imgData;
                        openModal(row);
                    })
                }
            },
                {
                    name: "删除", class: "btn-danger", icon: "guanbi",isDisabled:function (row) {
                    var status=row['authState'];
                    if(status===1){  //1 是已审核
                        return true;
                    }
                    return false;
                },  click: function (row) {
                        quit(row);
                    }
                }
            ]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //打开modal
    function openModal(source) {

        var instance = $modal.open({
            animation: true,
            templateUrl: 'views/userinfo/carOwnerAuthenStatusEditor.html',
            controller: 'carOwnerAuthenStatusEditorCtrl',
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
    function quit(source) {
        var userVin = source.userVin;
        var openId = source.openId;
        var id = source.id;
        dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
            dbUtils.post('carOwnerAuthenStatusRemove', {'userVin': userVin,'openId': openId,'id':id}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }

}]);