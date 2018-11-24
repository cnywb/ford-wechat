/**
 * Created by zhaoliang on 2017/8/27.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("eventOperationRecordListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {label: "跑批时间", type: "dateRange", name: "create", labelCols: "3"},
                {name: "projectCode", label: "活动代码", type: "text", placeholder: "请输入活动代码",  labelCols: "3"},
                {name: "openId", label: "唯一标识", type: "text", placeholder: "请输入唯一标识",  labelCols: "3"},
                {name: "operationType", label: "操作类型", type: "select", dropDownItemType: "json", dropDownItem: "operationTypeSelect", labelCols: "3", editable:true}
            ]
        },
        grid: {
            settings: {
                transCode: "eventOperationRecordPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "时间批次", width: "15%", field: "dateNo",lineFeed:true},
                {name: "活动代码", width: "15%", field: "projectCode",lineFeed:true},
                {name: "活动名称", width: "15%", field: "projectName",lineFeed:true},
                {name: "唯一标识", width: "20%", field: "openId",lineFeed:true},
                {name: "操作类型", width: "15%", field: "type_",lineFeed:true,class:"type_"},
                {name: "最后记录时间", width: "20%", field: "lastOperationTime",lineFeed:true}
            ],
            rowOperation: {show: false}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                "type_Format":function(val,row){
                    if(row['operationType']===1){
                        return '点击抽奖';
                    }else{
                        return '访问页面'
                    }
                },
            "type_Color":function(val,row){
                if(row['operationType']===1){
                    return 'green';
                }else{
                    return 'red';
                }
            }
            },
            // operationEvents: [{
            //     name: "新增", class: "btn-success", icon: "tianjia", click: function () {
            //         openModal();
            //     }
            // }],
            rowEvents: [{
                name: '编辑', class: 'btn-primary', icon: 'shanchu', click: function (row) {
                    openModal(row);
                }
            }, {
                name: "删除", class: "btn-danger", icon: "guanbi", click: function (row) {
                    quit(row);
                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

   /** 打开modal*/
    // function openModal(source) {
    //
    //     var instance = $modal.open({
    //         animation: true,
    //         templateUrl: 'db/db-form.html',
    //         controller: 'dealerEditorCtrl',
    //         size: "md",
    //         backdrop: "static",
    //         resolve: {
    //             source: function () {
    //                 return source;
    //
    //             }
    //         }
    //     });
    //     instance.result.then(function () {
    //         $scope.dbFormGrid.reLoadData();
    //     });
    // }

    /**
     * 删除操作
     */
    // function quit(source) {
    //     var ids = [source.id];
    //
    //     dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
    //         dbUtils.post('scanDealerRemove', {'ids': ids}, function (data) {
    //             dbUtils.success("删除成功！!");
    //             $scope.dbFormGrid.reLoadData();
    //         }, function (error) {
    //             dbUtils.error("删除处理异常!" + error);
    //         });
    //     });
    // }

}]);
