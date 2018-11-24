/**
 * Created by zhaoliang on 2017/5/24.
 */

var DBApp = angular.module('DBApp');

DBApp.controller("weUnauthApplyListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "vin", label: "VIN码", type: "text", placeholder: "请输入VIN码",  labelCols: "3"},
                {name: "openId", label: "微信唯一编号", type: "text", placeholder: "请输入微信编号",  labelCols: "3"},
                {name: "mobile", label: "认证手机", type: "text", placeholder: "请输入认证手机",  labelCols: "3"},
                {name: "assessStatus", label: "状态", type: "select",   dropDownItemType: "json", dropDownItem: "assessStatusSelect", labelCols: "3", editable:true , required: true}
            ]
        },
        grid: {
            settings: {
                transCode: "weUnauthApplyPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "问题标题", width: "20%", field: "title",lineFeed:true},
                {name: "姓名", width: "10%", field: "name",lineFeed:true},
                {name: "vin码", width: "20%", field: "vin",lineFeed:true},
                {name: "认证手机", width: "10%", field: "mobile",lineFeed:true},
                {name: "微信唯一标识", width: "10%", field: "openId",lineFeed:true},
                {name: "审核状态", width: "10%", field: "assessStatus_",lineFeed:true,class:"assessStatus_"},
                {name: "审核结果", width: "10%", field: "type_",lineFeed:true},
                {name: "申请时间", width: "10%", field: "applyDate",lineFeed:true}
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                "type_Format":function(val,row){
                    if(row['assessResult']===1){
                        return '通过';
                    }else if(row['assessResult']===0){
                        return ' ';
                    }else if(row['assessResult']===3){
                        return '关闭';
                    }
                    else{
                        return '未通过'
                    }
                },
                "assessStatus_Format":function(val,row){
                    if(row['assessStatus']===0){
                        return '未审核';
                    }else if(row['assessStatus']===1){
                        return '审核中';
                    }else{
                        return '已审核'
                    }
                },
                "assessStatus_Color":function(val,row){
                    if(row['assessStatus']===0){
                        return 'red';
                    }else if(row['assessStatus']===1){
                        return '#004FCC';
                    }else{
                        return 'green';
                    }
                },
            },
            rowEvents: [{
                name: '审核',  class: 'btn-primary', icon: 'shenhe', isDisabled:function (row) {
                    var status=row['assessStatus'];
                    var result=row['assessResult'];
                    if(status===2){
                        return true;
                    }
                    if(status===1){
                        return true;
                    }
                    if(result===3){
                        return true;
                    }
                    return false;
                }, click: function (row) {

                  //  var dataImg= angular.copy(source);
                    row['userVin']=row['vin']
                   // dataImg['userVin'] = dataImg.vin;
                    dbUtils.post("imageUrlEntityGet", row, function (data) {
                        console.log(data);
                     //   $scope.imgData=data;
                        row['imgData']=data;
                        openModal(row);
                    })

                }
            },
                {
                    name: "关闭", class: "btn-danger", icon: "guanbi",isDisabled:function (row) {
                    var result=row['assessResult'];
                    if(result===3){
                        return true;
                    }
                    return false;
                }, click: function (row) {
                    quit(row);
                }
                }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //打开modal
    function openModal(source) {

        var instance = $modal.open({
            animation: true,
            templateUrl: 'views/members/weUnauthApplyEditor.html',
            controller: 'weUnauthApplyEditorCtrl',
            size: "lg",
            backdrop: "static",
            resolve: {
                source: function () {
                    return source;

                }
            }
        });
        instance.result.then(function () {
            $scope.dbFormGrid.reLoadData();
        });
    }

    /**
     * 关闭工单操作
     */
    function quit(source) {
        var id = source.id;

        dbUtils.confirm("确定要对所选行进行<span style='color: red'>关闭工单</span>操作?关闭后会影响发送DMS数据，请谨慎关闭", function () {
            dbUtils.post('weUnauthApplyRemove', {'id': id}, function (data) {
                dbUtils.success("工单关闭成功！");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("工单关闭异常!" + error);
            });
        });
    }

}]);