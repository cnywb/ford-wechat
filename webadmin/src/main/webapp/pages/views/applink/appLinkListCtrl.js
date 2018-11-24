/**
 * Created by maojiawei on 16/3/30.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("appLinkListCtr", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "appLinkName", label: "app名称", type: "text", placeholder: "请输入app名称",  labelCols: "4"},
                {name: "downloadName", label: "下载公司名称", type: "text", placeholder: "请输入下载公司名称",  labelCols: "4"},
                {name: "plantForm", label: "平台", type: "select",   dropDownItemType: "json", dropDownItem: "plantFormSelect",labelCols: "4"}
            ]
        },
        grid: {
            settings: {
                transCode: "appLinkInfoPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "平台", width: "10%", field: "plantForm"},
                {name: "Must Have标识", width: "10%", field: "idCat"},
                {name: "app的ID", width: "10%", field: "idApp"},
                {name: "app名称", width: "10%", field: "appLinkName"},
                {name: "下载公司名称", width: "10%", field: "downloadName"},
                {name: "APP下载链接", width: "10%", field: "appLinkUrl"},
                {name: "APP图标", width: "10%", field: "appLinkImg"}
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
            controller: 'appLinkEditorCtrl',
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
    function quit() {
        var ids = [source.id];

        dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
            dbUtils.post('appLinkInfoRemove', {'ids': ids}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }

}]);
