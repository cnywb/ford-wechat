/**
 * Created by maojiawei on 16/3/30.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("groupListCtr", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "openId", label: "微信openId", type: "text", placeholder: "请输入微信openId",  labelCols: "3"},
                {name: "name", label: "用户姓名", type: "text", placeholder: "请输入用户姓名",  labelCols: "3"},
                {name: "mobile", label: "用户手机", type: "text", placeholder: "请输入用户手机",  labelCols: "3"},
                {name: "favourCarName", label: "意向车型名称", type: "text", placeholder: "请输入意向车型名称",  labelCols: "3"},
                {name: "dealer", label: "经销商", type: "text", placeholder: "请输入经销商",  labelCols: "3"},
                {name: "appLinkName", label: "app名称", type: "text", placeholder: "请输入app名称",  labelCols: "3"},
                {'label': '购车时间', 'type': 'dateRange', name:'buy','labelCols': '3'}
            ]
        },
        grid: {
            settings: {
                transCode: "groupPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "微信openId", width: "10%", field: "openId"},
                {name: "用户姓名", width: "10%", field: "name"},
                {name: "用户手机", width: "10%", field: "mobile"},
                {name: "意向车型编号", width: "10%", field: "favourCarCode"},
                {name: "意向车型名称", width: "10%", field: "favourCarName"},
                {name: "购车时间", width: "10%", field: "buyDate"},
                {name: "省", width: "5%", field: "province"},
                {name: "市", width: "5%", field: "city"},
                {name: "经销商编号", width: "10%", field: "dealerNo"},
                {name: "经销商", width: "10%", field: "dealer"}
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {

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
            controller: 'groupEditorCtrl',
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
            dbUtils.post('groupRemove', {'ids': ids}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }

}]);
