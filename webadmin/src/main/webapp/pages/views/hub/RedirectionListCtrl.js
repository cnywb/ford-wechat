/**
 * Created by maojiawei on 16/3/30.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("RedirectionListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "channel", label: "短项目ID", type: "text", placeholder: "请输入问卷网短项目ID",  labelCols: "4"},
            ]
        },
        grid: {
            settings: {
                transCode: "redirectionPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "标识", width: "20%", field: "state"},
                {name: "重定向URL", width: "20%", field: "url"},
                {name: "备注说明", width: "20%", field: "remark"}
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
            controller: 'RedirectionEditorCtrl',
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
            dbUtils.post('redirectionDelete', {'ids': ids}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }

}]);
