/**
 * Created by maojiawei on 16/3/30.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("dealerListCtr", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "code", label: "销售代码", type: "text", placeholder: "请输入销售代码",  labelCols: "3"},
                {name: "sstCode", label: "服务代码", type: "text", placeholder: "请输入服务代码",  labelCols: "3"},
                {name: "name", label: "经销商名称", type: "text", placeholder: "请输入经销商名称",  labelCols: "3"},
                {name: "type", label: "类型", type: "select",   dropDownItemType: "json", dropDownItem: "dealerTypeSelect", labelCols: "3", editable:true , required: true}
            ]
        },
        grid: {
            settings: {
                transCode: "scanDealerPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "销售代码", width: "5%", field: "code",lineFeed:true},
                {name: "服务代码", width: "5%", field: "sstCode",lineFeed:true},
                {name: "经销商名称", width: "10%", field: "name",lineFeed:true},
                {name: "小区", width: "5%", field: "smallArea",lineFeed:true},
                {name: "大区", width: "5%", field: "bigArea",lineFeed:true},
                {name: "省", width: "5%", field: "province",lineFeed:true},
                {name: "市", width: "5%", field: "city",lineFeed:true},
                {name: "地址", width: "20%", field: "address",lineFeed:true},
                {name: "邮编", width: "5%", field: "zipcode",lineFeed:true},
                {name: "类型", width: "10%", field: "type_",lineFeed:true}
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {
                "type_Format":function(val,row){
                    if(row['type']===1){
                        return '意见领袖';
                    }else if(row['type']===2){
                        return '车主手册';
                    }else if(row['type']===3){
                        return  '活动';
                    }else{
                        return '经销商'
                    }
                }
            },
            operationEvents: [{
                name: "新增", class: "btn-success", icon: "tianjia", click: function () {
                    openModal();
                }
            }],
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

    //打开modal
    function openModal(source) {

        var instance = $modal.open({
            animation: true,
            templateUrl: 'db/db-form.html',
            controller: 'dealerEditorCtrl',
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
            dbUtils.post('scanDealerRemove', {'ids': ids}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }

}]);
