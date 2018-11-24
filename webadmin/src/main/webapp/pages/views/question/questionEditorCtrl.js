/**
 * Created by jovi on 5/23/16.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('questionEditorCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function ($scope,
                                                                                                             $modalInstance, $modal, dbUtils, source) {

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,transCode:"questionHandle",cols:1},
        title: {label: "问题列表"},
        sections: [{
            fields: [
                {name: "openId", label: "微信openId", type: "text",labelCols: "4", editable:false , required: true},
                {name: "type", label: "问题类别", type: "select",   dropDownItemType: "json", dropDownItem: "questionTypeSelect",labelCols: "3",editable:true,required:true},
                {name: "title", label: "问题主题", type: "text",labelCols: "4", editable:false , required: true},
                {name: "content", label: "内容", type: "text",labelCols: "4", editable:false , required: true}
            ]
        }],
        events:{
            modalClose:function(){
                $modalInstance.dismiss("cancel");
            },
            beforeSubmit:function(reqBody){

            },
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
