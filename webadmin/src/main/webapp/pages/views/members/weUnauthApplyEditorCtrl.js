/**
 * Created by zhaoliang on 2017/5/24.
 */


angular.module('DBApp').controller('weUnauthApplyEditorCtrl',
    ['$scope', '$modal', '$state', '$stateParams',  'dbUtils', 'source','$modalInstance','dbImService', function ($scope, modal, $state, $stateParams, dbUtils,source,$modalInstance, dbImService) {

        if (angular.isUndefined(source)) {
            $scope.data={}
        } else {
            $scope.data= angular.copy(source);
            $scope.applyDate={
                unBundling:"解绑审核",
                editMobile:"修改手机"
            }
            $scope.vinShow=true;
            $scope.mobileShow=false;
            $scope.data.applyType=0;
            $scope.rotate = 0;

       /*     if($scope.data.applyType===0){
                $scope.vinShow=true;
                $scope.titleData="解绑审核";
                $scope.mobileShow=false;
            }else {
                $scope.vinShow=false;
                $scope.titleData="修改手机";
                $scope.mobileShow=true;
            }*/
            $scope.imgData= $scope.data.imgData;
            // console.log($scope.data);
            // var dataImg= angular.copy(source);
            // dataImg['userVin'] = dataImg.vin;
            // dbUtils.post("imageUrlEntityGet", dataImg, function (data) {
            //     console.log(data);
            //     $scope.imgData=data;
            // })
        }

        $scope.imgUrlId=null;
        $scope.sub=function (value) {

            console.log(value);
            $scope.imgUrlId=value;
            //alert(img.id);
        }

        $scope.orMobile=function (value) {
            //alert(value)
            if(value=="解绑审核"){
                $scope.vinShow=true;
                $scope.mobileShow=false;
                $scope.data.applyType=0;
            }else {
                $scope.vinShow=false;
                $scope.mobileShow=true;
                $scope.data.applyType=1;
            }
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

            var image = new Image();
            image.src = img.src;
            image.onload =function(){
                var width = image.width;
                var height = image.height;
                var fileSize = image.fileSize;
                console.log(1+"------"+height+"---"+width);
            }
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
            // if($scope.imgUrlId==null){
            //     dbUtils.warning("请选择一个清晰的行驶证照片！")
            //     return false;
            // }
            data["imgUrlId"]=$scope.imgUrlId;
            data["notPass"]=1; //0 审核不通过
            dbUtils.confirm("请确认是否通过审核？", function () {
                dbUtils.post("weUnauthApplyHandle", data, function () {
                    dbUtils.success("审核成功！");
                    $scope.submited = false;
                  //  $modalInstance.dismiss('cancel');
                    $modalInstance.close();
                })
            });
        };

        $scope.notPass=function () {
            var data = angular.copy($scope.data);
            if($scope.data.assessMemo==null){
                dbUtils.warning("请在审核理由中填写未通过原因！")
                return false;
            }
            data["notPass"]=0; //0 审核不通过
            dbUtils.confirm("请确认是否不通过审核？", function () {
                dbUtils.post("weUnauthApplyHandle", data, function () {
                    dbUtils.success("审核不通过成功！");
                    $scope.submited = false;
                    $modalInstance.close();
                    //$modalInstance.dismiss('cancel');
                })
            });
        };


    }]);
