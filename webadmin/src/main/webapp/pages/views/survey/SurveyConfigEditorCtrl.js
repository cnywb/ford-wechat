/**
 * Created by jovi on 5/23/16.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('SurveyConfigEditorCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,transCode:"surveyConfigHandle",cols:1},
        title: {label: "回调录入"},
        sections: [{
            fields: [
                {name: "shortId", label: "短项目ID", type: "text",labelCols: "3", editable:true , required: true},
                {name: "link", label: "答题链接", type: "text",labelCols: "3", editable:false , required: false, placeholder: "保存后自动生成"},
                {name: "redirectUrl", label: "答题完成跳转地址", type: "text",labelCols: "3", editable:true , required: false},
                {name: "needCallback", label: "是否需要回调", type: "checkbox",labelCols: "3", editable:true , required: false},
                {name: "callbackUrl", label: "回调接口地址", type: "text",labelCols: "3", editable:true , required: false},
                {name: "remark", label: "备注说明", type: "text",labelCols: "3", editable:true , required: false}
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
