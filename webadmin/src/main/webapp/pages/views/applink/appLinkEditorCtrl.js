/**
 * Created by jovi on 5/23/16.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('appLinkEditorCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,transCode:"appLinkInfoHandle",cols:1},
        title: {label: "app录入"},
        sections: [{
            fields: [
                {name: "plantForm", label: "平台", type: "select",   dropDownItemType: "json", dropDownItem: "plantFormSelect", labelCols: "4", editable:true , required: true},
                {name: "idCat", label: "Must Have标识", type: "text",labelCols: "4", editable:true , required: true},
                {name: "idApp", label: "app的ID", type: "text",labelCols: "4", editable:true , required: true},
                {name: "appLinkName", label: "app名称", type: "text",labelCols: "4", editable:true , required: true},
                {name: "downloadName", label: "下载公司名称", type: "text",labelCols: "4", editable:true , required: true},
                {name: "appLinkUrl", label: "APP下载链接", type: "text",labelCols: "4", editable:true , required: true},
                {name: "appLinkImg", label: "APP图标", type: "text",labelCols: "4", editable:true , required: true}
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
