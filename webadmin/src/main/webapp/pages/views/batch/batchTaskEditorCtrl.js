/**
 * Created by zhaoliang on 2017/5/4.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('batchTaskEditorCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,transCode:"batchTaskHandle",cols:1},
        title: {label: "批处理任务书"},
        sections: [{
            fields: [
                {name: "batchType", label: "任务类型", type: "select",   dropDownItemType: "json", dropDownItem: "batchTypeSelect", labelCols: "4", required: true, placeholder: "任务类型"},
                {name: "batchNo", label: "日期批次",placeholder:"日期批次", type: "text",labelCols: "4", editable:true},
                {name: "params", label: "业务参数",placeholder:"业务参数{'dateNo':'20160614'}", type: "text",labelCols: "4", editable:true , required: true},
                {name: "businessId", label: "业务编号", placeholder:"业务编号",type: "text",labelCols: "4", editable:true },
                {name: "memo", label: "描述", placeholder:"描述",type: "text",labelCols: "4", editable:true },
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