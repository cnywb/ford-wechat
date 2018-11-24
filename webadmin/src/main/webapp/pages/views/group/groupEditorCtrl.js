/**
 * Created by jovi on 5/23/16.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('groupEditorCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,transCode:"groupHandle",cols:1},
        title: {label: "微信用户组录入"},
        sections: [{
            fields: [
                {name: "openId", label: "微信openId", type: "text",labelCols: "4", editable:true , required: true},
                {name: "name", label: "用户姓名", type: "text",labelCols: "4", editable:true , required: true},
                {name: "mobile", label: "用户手机", type: "text",labelCols: "4", editable:true , required: true},
                {name: "favourCarCode", label: "意向车型编号", type: "text",labelCols: "4", editable:true , required: true},
                {name: "favourCarName", label: "意向车型名称", type: "text",labelCols: "4", editable:true , required: true},
                {name: "buyDate", label: "购车时间", type: "date",     labelCols: "4", editable:true , required: true},
                {name: "province", label: "省", type: "text",labelCols: "4", editable:true , required: true},
                {name: "city", label: "市", type: "text",labelCols: "4", editable:true , required: true},
                {name: "dealerNo", label: "经销商编号", type: "text",labelCols: "4", editable:true , required: true},
                {name: "dealer", label: "经销商", type: "text",labelCols: "4", editable:true , required: true}
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
