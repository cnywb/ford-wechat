/**
 * Created by zhaoliang on 2017/5/24.
 */


angular.module('DBApp').controller('carOwnerAuthenStatusEditorCtrl',
    ['$scope', '$modal', '$state', '$stateParams',  'dbUtils', 'source','$modalInstance','dbImService', function ($scope, modal, $state, $stateParams, dbUtils,source,$modalInstance, dbImService) {

        if (angular.isUndefined(source)) {

        } else {
            $scope.data= angular.copy(source);
            console.log($scope.data);
            $scope.imgData = $scope.data.imgData;
            $scope.rotate = 0;
            //获取行驶证图片
        /*    dbUtils.post("imageUrlEntityGet", dataImg, function (data) {
                console.log(data);
                $scope.imgData=data;
            })*/
        }
        $scope.imgUrlId=null;
        $scope.sub=function (value) {

            console.log(value);
            $scope.imgUrlId=value;
            //alert(img.id);
        }

        //取消Modal
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.rotate1={
            right:400,
            top:-25
        }
        $scope.rotateClose={
            height:40,
            width:40,
            right:-30,
            top:-13
        }
        $scope.rotateClick=function () {
            $scope.rotate=$scope.rotate+90;

            var x= (($scope.rotate/90)%2 ==0) ? 2 : 1 ;

            if(x==1){
                $scope.rotate1={
                    right:400,
                    top:-175
                }
                $scope.rotateClose={
                    height:40,
                    width:40,
                    right:120,
                    top:-162
                }
            }else {
                $scope.rotate1={
                    right:400,
                    top:-25
                }
                $scope.rotateClose={
                    height:40,
                    width:40,
                    right:-30,
                    top:-13
                }
            }

        }


        //点击图片时放大显示图片
        $scope.changePic=function($event){
            var img=$event.srcElement || $event.target;
            angular.element("#big-image")[0].src=img.src;
            angular.element("#js-img-view")[0].style.display="block";
        }

        //点击图片时放小显示图片
        $scope.closePic =function(){
            angular.element("#js-img-view")[0].style.display="none";
        }

        //表单提交
        $scope.submit = function (valid) {
            var data = angular.copy($scope.data);
    /*        if($scope.imgUrlId==null){
                dbUtils.warning("请选择一个清晰的行驶证照片！")
                return false;
            }*/
            data["imgUrlId"]=$scope.imgUrlId;
            data["notPass"]=1; // 0 审核不通过
            dbUtils.confirm("请确认是否通过审核？", function () {
                dbUtils.post("carOwnerAuthenStatusHandle", data, function (value) {
                    console.log(value);
                    if(value=="success"){
                        dbUtils.success("审核完成!");
                    }else {
                        dbUtils.error(value);
                    }
                    $scope.submited = false;
                    $modalInstance.close();
                })
            });
        };

       $scope.notPass=function () {
           var data = angular.copy($scope.data);
           if($scope.data.memo==null){
               dbUtils.warning("请在备注中填写未通过原因！")
               return false;
           }
           data["notPass"]=0; //0 审核不通过
           dbUtils.confirm("请确认是否不通过审核？", function () {
               dbUtils.post("carOwnerAuthenStatusHandle", data, function () {
                   dbUtils.success("审核不通过完成！");
                   $scope.submited = false;
                   $modalInstance.close();
               })
           });
       };




    }]);
