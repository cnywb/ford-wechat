/**
 * Created by jovi on 5/23/16.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('SurveyLinkCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    $scope.dbForm = {
        originData: source,
        settings:{showClose:true,cols:1, isDetail: true,},
        title: {label: "生成问卷链接"},
        sections: [{
            fields: [
                {name: "link", label: "链接", type: "text",labelCols: "2", editable:true},
            ]
        }],
        events:{
            modalClose:function(){
                $modalInstance.dismiss("cancel");
            },
            beforeSubmit:function(reqBody){},
            afterSubmit:function(){
            }
        }
    };
}]);
