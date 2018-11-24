/**
 * Created by zhaoliang on 2017/5/5.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('weFordClubMemberEditorCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,transCode:"weFordClubMemberHandle",cols:1},
        title: {label: "车主录入"},
        sections: [{
            fields: [
                {name: "userId", label: "用户ID", type: "text",labelCols: "4", editable:true},
                {name: "name", label: "车主姓名", type: "text",labelCols: "4", editable:true , required: true},
                {name: "mobile", label: "车主电话", type: "text",labelCols: "4", editable:true , required: true},
                {name: "vin", label: "VIN码", type: "text",labelCols: "4", editable:true },
                {name: "openId", label: "微信标识", type: "text",labelCols: "4", editable:true },
                {name: "dcrtDate", label: "认证时间", type: "text",     labelCols: "4", editable:true },
                {name: "bigArea", label: "大区", type: "text",labelCols: "4", editable:true },
                {name: "smallArea", label: "小区", type: "text",labelCols: "4", editable:true},
                {name: "channelType", label: "渠道类型", type: "select",   dropDownItemType: "json", dropDownItem: "dealerTypeSelect", labelCols: "4", editable:true , required: true},
                //{name: "channelType", label: "渠道类型", type: "text",labelCols: "4", editable:true },
                {name: "channelCode", label: "渠道代码", type: "text",     labelCols: "4", editable:true },
                {name: "channelName", label: "渠道名称", type: "text",labelCols: "4", editable:true },
                {name: "dealerServiceCode", label: "经销商代码", type: "text",labelCols: "4", editable:true},
                {name: "dealerName", label: "经销商名称", type: "text",labelCols: "4", editable:true },
                {name: "dateNo", label: "日期批次", type: "text",labelCols: "4", editable:true }

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
