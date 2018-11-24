/**
 * Created by zhaoliang on 2017/9/8.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('eventDetailAddCtrl', ['$scope', '$modalInstance', '$modal', 'dbUtils', 'source', function($scope, $modalInstance, $modal, dbUtils, source){
    $scope.data={}
    if (angular.isUndefined(source)) {
        $scope.data1={}
    } else {
        $scope.data1= angular.copy(source);
        console.log($scope.data1)
    }

    //取消Modal
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    /* 多活动条件*/
    $scope.selectedChange = function (v) {
        console.log('selectedChange: ', v);
        var value=$scope.data1;
        //标准的for循环
        for(var i=0;i<value.length;i++){
            console.log(value[i].projectName)
            if(v==value[i].projectName){
                $scope.data.projectCode=value[i].projectCode
                $scope.data.wishing = value[i].wishing
            }
        }
    }


    //时间控件
    $scope.opts ={
        pikaday: {
            // firstDay: 1,    //start on Monday
            showTime: false,    //show timepicker as well
            showSeconds:false,   //showSeconds
            language: 'cn',     //中文
            use24hour: false   //24小时
        }
    };

    //分钟数
    var minutes = new Array(60);
    for(var i=0; i<minutes.length; i++) {
        minutes[i] = i;
    }
    $scope.minute = minutes

    //小时数
    var hour = new Array(24);
    for(var i=0; i<hour.length; i++) {
        hour[i] = i;
    }
    $scope.hours = hour



    //表单提交
    $scope.submit = function (valid) {
        var data = angular.copy($scope.data);

        if(data.dateNo ==null){
            dbUtils.warning("时间批次不能为空！")
            return false;
        }
        if(data.max ==null){
            dbUtils.warning("最大额度不能为空！")
            return false;
        }
        if(data.min ==null){
            dbUtils.warning("最小额度不能为空！")
            return false;
        }
        if(data.totalAmount ==null){
            dbUtils.warning("总额度不能为空！")
            return false;
        }
        if(data.count ==null){
            dbUtils.warning("份数不能为空！")
            return false;
        }
        if(data.lowestAmount ==null){
            dbUtils.warning("门槛金额不能为空！")
            return false;
        }
        if(data.validTimes ==null){
            dbUtils.warning("代金券有效天数不能为空！")
            return false;
        }

        var x= data.dateNo;
        if(x.length==8){
            x = x.replace(/(.{4})/, "$1-");
            x = x.replace(/(.{7})/, "$1-")
        }
        var startTime =  x +" "+ data.startHours +":" + data.startMinute+":"+"00";
        startTime = startTime.replace(/-/g,'/');
        var timestamp1 = new Date(startTime).getTime();
        data["startTime"]= timestamp1

        var endTime = x + " "+data.endHours +":" + data.endMinute+":"+"00";
        endTime = endTime.replace(/-/g,'/');
        var timestamp2 = new Date(endTime).getTime();
        data["endTime"]=timestamp2


        var str = data.dateNo;
        str = str.replace(/-/g,'');
        data["dateNo"] = str;

        console.log(data);
        dbUtils.confirm("请确认是否添加活动？", function () {
            dbUtils.post("eventDetailHandle", data, function () {
                dbUtils.success("保存成功！");
                $scope.submited = false;
                $modalInstance.close();
            })
        });
    };


}]);

