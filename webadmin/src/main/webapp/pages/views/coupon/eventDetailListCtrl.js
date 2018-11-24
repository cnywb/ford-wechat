/**
 * Created by zhaoliang on 2017/8/27.
 */

var DBApp = angular.module('DBApp');
DBApp.controller("eventDetailListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "projectCode", label: "活动代码", type: "text", placeholder: "请输入活动代码",  labelCols: "3"},
                {label: "跑批时间", type: "dateRange", name: "create", labelCols: "3"}
            ]
        },
        grid: {
            settings: {
                transCode: "eventDetailPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "时间批次", width: "8%", field: "dateNo",lineFeed:true},
                {name: "活动代码", width: "8%", field: "projectCode",lineFeed:true},
                {name: "活动名称", width: "8%", field: "projectName",lineFeed:true},
                {name: "最大额度", width: "8%", field: "max",lineFeed:true},
                {name: "最小额度", width: "8%", field: "min",lineFeed:true},
                {name: "总金额", width: "8%", field: "totalAmount",lineFeed:true},
                {name: "份数", width: "8%", field: "count",lineFeed:true},
                {name: "每日开始时间", width: "10%", field: "startTime",lineFeed:true},
                {name: "每日结束时间", width: "10%", field: "endTime",lineFeed:true},
                {name: "代金券有效天数", width: "8%", field: "validTimes",lineFeed:true},
                {name: "祝福语", width: "16%", field: "wishing",lineFeed:true}
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                // "type_Format":function(val,row){
                //     if(row['type']===1){
                //         return '意见领袖';
                //     }else if(row['type']===2){
                //         return '车主手册';
                //     }else if(row['type']===3){
                //         return  '活动';
                //     }else{
                //         return '经销商'
                //     }
                // }
            },
            operationEvents: [{
                name: "新增", class: "btn-success", icon: "tianjia", click: function () {
                    dbUtils.post("eventList", {}, function (data) {
                        //openModal(data[0]);
                        addModal(data);
                    })
                }
            }],
            rowEvents: [{
                name: '编辑', class: 'btn-primary', icon: 'shanchu', click: function (row) {
                    console.log(row)
                    editModal(row);
                }
            }, {
                name: "删除", class: "btn-danger", icon: "guanbi", click: function (row) {
                    quit(row);
                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //添加活动配置
    function addModal(source) {

        var instance = $modal.open({
            animation: true,
            templateUrl: 'views/coupon/eventDetailAddCtrl.html',
            controller: 'eventDetailAddCtrl',
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
    }


    //编辑活动配置
    function editModal(source) {

        var instance = $modal.open({
            animation: true,
            templateUrl: 'views/coupon/eventDetailEditorCtrl.html',
            controller: 'eventDetailEditorCtrl',
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
    }

    /**
     * 删除操作
     */
    function quit(source) {
        var ids = [source.id];

        dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
            dbUtils.post('eventDetailRemove', {'ids': ids}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }

}]);
