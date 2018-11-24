/**
 * Created by zhaoliang on 2017/5/5.
 */

var DBApp = angular.module('DBApp');

DBApp.controller("weFordClubMemberListCtr", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "userId", label: "用户ID", type: "text", placeholder: "请输入用户ID",  labelCols: "3"},
                {name: "mobile", label: "车主电话", type: "text", placeholder: "请输入车主电话",  labelCols: "3"},
                {name: "vvin", label: "VIN码", type: "text", placeholder: "请输入VIN码",  labelCols: "3"},
                {name: "channelType", label: "渠道类型", type: "select",   dropDownItemType: "json", dropDownItem: "dealerTypeSelect", labelCols: "3", editable:true , required: true}
            ]
        },
        grid: {
            settings: {
                transCode: "weFordClubMemberPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: false
            },
            header: [
                {name: "客户ID", width: "5%", field: "userId",lineFeed:true},
                {name: "姓名", width: "10%", field: "name",lineFeed:true},
                {name: "电话", width: "10%", field: "mobile",lineFeed:true},
                {name: "VIN码", width: "10%", field: "vvin",lineFeed:true},
                {name: "微信标识", width: "10%", field: "openId",lineFeed:true},
                {name: "认证时间", width: "10%", field: "dcrtDate",lineFeed:true},
                {name: "车辆编号", width: "5%", field: "vcarId",lineFeed:true},
                {name: "会员卡号", width: "5%", field: "memberNo",lineFeed:true},
                {name: "会员卡状态", width: "5%", field: "vcardStatus",lineFeed:true},
                {name: "批处理标识", width: "5%", field: "jobStatus",lineFeed:true},
                {name: "渠道类型", width: "5%", field: "type_",lineFeed:true},
                {name: "渠道代码", width: "5%", field: "channelCode",lineFeed:true},
                {name: "认证方式", width: "5%", field: "authWay",lineFeed:true},
                {name: "认证来源", width: "5%", field: "source",lineFeed:true},
                {name: "车主地址", width: "10%", field: "address",lineFeed:true}
            ],
            rowOperation: {show: false}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                "type_Format":function(val,row){
                    if(row['channelType']===1){
                        return '意见领袖';
                    }else if(row['channelType']===2){
                        return '车主手册';
                    }else if(row['channelType']===3){
                        return  '活动';
                    }else{
                        return '经销商'
                    }
                }
            }
 /*           operationEvents: [{
                name: "新增", class: "btn-success", icon: "tianjia", click: function () {
                    openModal();
                }
            }],*/
       /*     rowEvents: [{
                name: '编辑', class: 'btn-primary', icon: 'shanchu', click: function (row) {
                    openModal(row);
                }
            }*/
                //{
            //     name: "删除", class: "btn-danger", icon: "guanbi", click: function (row) {
            //         quit(row);
            //     }
            // }
             //]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //打开modal
/*    function openModal(source) {

        var instance = $modal.open({
            animation: true,
            templateUrl: 'db/db-form.html',
            controller: 'weFordClubMemberEditorCtrl',
            size: "md",
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
    }*/

    /**
     * 删除操作
     */
/*    function quit(source) {
        var ids = [source.id];

        dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
            dbUtils.post('weFordClubMemberRemove', {'ids': ids}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }*/

}]);
