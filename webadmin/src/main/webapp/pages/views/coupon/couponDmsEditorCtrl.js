/**
 * Created by zhaoliang on 2017/9/25.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('couponDmsEditorCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,transCode:"couponDmsHandle",cols:1},
        title: {label: "DMS下发管理"},
        sections: [{
            fields: [
                {name: "vin", label: "VIN码", type: "text",labelCols: "4", editable:true,required:true},
                {name: "targetDealer", label: "责任经销商", type: "text",labelCols: "4", editable:true,required:true},
                {name: "sendCount", label: "发送次数", type: "text",labelCols: "4", editable:true , required: true},
                {name: "sendDmsStatus", label: "是否发送DMS", type: "select",   dropDownItemType: "json", dropDownItem: "sendDmsStatusSelect", labelCols: "4", editable:true , required: true},
                {name: "cancel", label: "是否取消", type: "select",   dropDownItemType: "json", dropDownItem: "cancelStatusSelect", labelCols: "4", editable:true , required: true}
            ]
        }],
        events:{
            modalClose:function(){
                $modalInstance.dismiss("cancel");
            },
            beforeSubmit:function(reqBody){},
            afterSubmit:function(){
                if(angular.isUndefined(source)){
                    dbUtils.success("添加成功", "提示");
                }else{
                    dbUtils.success("修改成功", "提示");
                }
                $modalInstance.close();
            }
        }
    };
}]);