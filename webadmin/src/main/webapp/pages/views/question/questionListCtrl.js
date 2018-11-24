/**
 * Created by maojiawei on 16/3/30.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("questionListCtr", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "openId", label: "微信openId", type: "text", placeholder: "请输入微信openId",  labelCols: "3"},
                {name: "title", label: "问题主题", type: "text", placeholder: "请输入问题主题",  labelCols: "3"},
                {name: "type", label: "问题类别", type: "select",   dropDownItemType: "json", dropDownItem: "questionTypeSelect",labelCols: "3"},
                {label: "提问时间", type: "dateRange", name: "create", labelCols: "3"}
            ],
            hiddenParams: {status:1}
        },
        grid: {
            settings: {
                transCode: "questionPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "微信openId", width: "10%", field: "openId"},
                {name: "问题主题", width: "15%", field: "title"},
                {name: "内容", width: "15%", field: "content"},
                {name: "问题类别", width: "10%", field: "type"},
                {name: "提问时间", width: "10%", field: "createdDate"}
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {

            },
            operationEvents: [],
            rowEvents: [{
                name: '编辑', class: 'btn-primary', icon: 'shanchu', click: function (row) {
                    openModal(row);
                }
            },{
                name: '回答', class: 'btn-primary', icon: 'shanchu', click: function (row) {
                    answerModal(row);
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
            controller: 'questionEditorCtrl',
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

    //打开modal
    function answerModal(source) {
        var instance = $modal.open({
            animation: true,
            templateUrl: 'db/db-form.html',
            controller: 'questionAnswerCtrl',
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
        dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
            dbUtils.post('questionRemove', {'id': source.id}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }

}]);
