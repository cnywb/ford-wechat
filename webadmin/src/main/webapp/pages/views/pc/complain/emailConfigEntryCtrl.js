angular.module('DBApp').controller('emailConfigEntryCtrl',
    ['$scope', 'dbUtils', function ($scope, dbUtils) {
        //页面添加时的对象
        $scope.data = {};
        //表单提交
        $scope.submit = function (valid) {
            $scope.submited = true;
            if (!valid) {
                return;
            }
            dbUtils.confirm("确定添加吗？", function () {
                var data = angular.copy($scope.data);
                dbUtils.post("emailConfigHandle", data, function () {
                    dbUtils.success("数据提交成功!");
                    $scope.submited = false;
                    loadData();
                })
            });
        };
        function loadData(){
            dbUtils.post("emailConfigGet", {}, function (data) {
                $scope.data = data;
            });
        }

        loadData();
    }]);
