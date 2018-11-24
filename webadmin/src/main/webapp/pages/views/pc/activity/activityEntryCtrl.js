angular.module('DBApp').controller('activityEntryCtrl',
    ['$scope', '$modal', '$state', '$stateParams', 'FileUploader', 'dbUtils', 'dbImService', function ($scope, modal, $state, $stateParams, FileUploader, dbUtils, dbImService) {

        var id = $stateParams.id;
        //页面添加时的对象
        $scope.data = {};

        function isEmpty(value) {
            return angular.isUndefined(value) || value == "" || value == "null" || value == "undefined";
        }

        //表单提交
        $scope.submit = function (valid) {
            $scope.submited = true;
            if (!valid) {
                return;
            }
            if (isEmpty($scope.data.imageUrl)) {
                dbUtils.error("请上传图片!");
                return
            }
            var data = angular.copy($scope.data);
            data['id'] = id;
            dbUtils.confirm("确定添加吗？", function () {
                dbUtils.post("activityHandle", data, function () {
                    dbUtils.success("数据提交成功!");
                    $scope.submited = false;
                    $state.go("activityManage");
                })
            });
        };

        //文件上传
        var uploader = $scope.uploader = new FileUploader({
            url: '../resourceStore/resourceStoreHandle.ctl',
            autoUpload: true,//自动上传
            removeAfterUpload: true,
        });

        // FILTERS
        uploader.filters.push({
            name: 'imageFilter',
            fn: function (item /*{File|FileLikeObject}*/, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        });

        uploader.onCompleteItem = function (fileItem, response, status, headers) {
            console.info('onCompleteItem', fileItem, response, status, headers);
            if (status != 200) {
                dbUtils.error("上传异常!" + response.errorMsg);
                fileItem.cancel();
                uploader.clearQueue();
            } else {
                $scope.data['imageUrl'] = response.url;
                $scope.$apply();
                dbUtils.success("上传成功!");
            }
        };
        if ($stateParams['id']) {
            dbUtils.post("activityGet", {id: $stateParams['id']}, function (data) {
                $scope.data = data;
            });
        }
    }]);
