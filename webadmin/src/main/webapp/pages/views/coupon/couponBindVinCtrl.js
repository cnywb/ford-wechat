/**
 * Created by zhaoliang on 2017/9/12.
 */

var DBApp = angular.module("DBApp");
DBApp.controller('couponBindVinCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){

    if (angular.isUndefined(source)) {
        $scope.data={}
    } else {
        $scope.data= angular.copy(source);
       console.log($scope.data)
    }

    //取消Modal
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    //表单提交
    $scope.submit = function (valid) {
        var data = angular.copy($scope.data);

        if(data.vin ==null){
            dbUtils.warning("VIN码不能为空！")
            return false;
        }

        console.log(data);
        dbUtils.confirm("请确认是否绑定VIN码？", function () {
            dbUtils.post("drawVinHandle", data, function () {
                dbUtils.success("VIN绑定成功！");
                $scope.submited = false;
                $modalInstance.close();
            })
        });
    };


}]);

