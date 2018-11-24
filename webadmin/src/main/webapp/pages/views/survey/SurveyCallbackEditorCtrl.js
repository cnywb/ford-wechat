/**
 * Created by jovi on 5/23/16.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('redirectConfigEditorCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,transCode:"redirectConfigHandle",cols:1},
        title: {label: "重定向录入"},
        sections: [{
            fields: [
                {name: "state", label: "微信入口状态参数", type: "text",labelCols: "4", editable:true , required: true},
                {name: "url", label: "授权跳转的URL", type: "text",labelCols: "4", editable:true , required: true},
                {name: "remark", label: "备注说明", type: "text",labelCols: "4", editable:true , required: true}
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
