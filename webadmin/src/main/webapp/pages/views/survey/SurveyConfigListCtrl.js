/**
 * Created by maojiawei on 16/3/30.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("SurveyConfigListCtrl", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
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
                transCode: "surveyConfigPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "短项目ID", width: "20%", field: "shortId"},
                {name: "答题链接", width: "20%", field: "link"},
                {name: "答题完成跳转地址", width: "20%", field: "redirectUrl"},
                {name: "是否需要回调", width: "20%", field: "needCallback"},
                {name: "回调接口地址", width: "20%", field: "callbackUrl"},
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
            controller: 'SurveyConfigEditorCtrl',
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
            dbUtils.post('surveyConfigDelete', {'ids': ids}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }

}]);
