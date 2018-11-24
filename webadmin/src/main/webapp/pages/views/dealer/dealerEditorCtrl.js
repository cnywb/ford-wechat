/**
 * Created by jovi on 5/23/16.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('dealerEditorCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,transCode:"scanDealerHandle",cols:1},
        title: {label: "经销商录入"},
        sections: [{
            fields: [
                {name: "code", label: "销售代码", type: "text",labelCols: "4", editable:true},
                {name: "sstCode", label: "服务代码", type: "text",labelCols: "4", editable:true , required: true},
                {name: "name", label: "经销商名称", type: "text",labelCols: "4", editable:true , required: true},
                {name: "smallArea", label: "小区", type: "text",labelCols: "4", editable:true },
                {name: "bigArea", label: "大区", type: "text",labelCols: "4", editable:true },
                {name: "province", label: "省", type: "text",     labelCols: "4", editable:true },
                {name: "city", label: "市", type: "text",labelCols: "4", editable:true },
                {name: "address", label: "地址", type: "text",labelCols: "4", editable:true},
                {name: "zipcode", label: "邮编", type: "text",labelCols: "4", editable:true },
                {name: "type", label: "类型", type: "select",   dropDownItemType: "json", dropDownItem: "dealerTypeSelect", labelCols: "4", editable:true , required: true}
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
