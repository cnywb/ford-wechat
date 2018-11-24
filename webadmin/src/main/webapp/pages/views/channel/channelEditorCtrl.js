/**
 * Created by zhaoliang on 2017/5/4.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('channelEditorCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,transCode:"weChannelHandle",cols:1},
        title: {label: "渠道商录入"},
        sections: [{
            fields: [
                {name: "dealerCode", label: "销售代码", type: "text",labelCols: "4", editable:true},
                {name: "dealerServiceCode", label: "经销商码", type: "text",labelCols: "4", editable:true},
                {name: "code", label: "渠道代码", type: "text",labelCols: "4", editable:true , required: true},
                {name: "name", label: "渠道名称", type: "text",labelCols: "4", editable:true , required: true},
                {name: "smallArea", label: "小区", type: "text",labelCols: "4", editable:true },
                {name: "bigArea", label: "大区", type: "text",labelCols: "4", editable:true },
                {name: "province", label: "省", type: "text",     labelCols: "4", editable:true },
                {name: "city", label: "市", type: "text",labelCols: "4", editable:true },
                {name: "address", label: "地址", type: "text",labelCols: "4", editable:true},
                {name: "zipCode", label: "邮编", type: "text",labelCols: "4", editable:true },
                {name: "url", label: "URL", type: "text",labelCols: "4", editable:true },
                {name: "type", label: "类型", type: "select",   dropDownItemType: "json", dropDownItem: "dealerTypeSelect", labelCols: "4", editable:true , required: true},
                {name: "remark", label: "备注", type: "text",labelCols: "4", editable:true },
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