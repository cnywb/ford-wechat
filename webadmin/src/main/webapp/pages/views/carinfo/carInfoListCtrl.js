/**
 * Created by maojiawei on 16/3/30.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("carInfoListCtr", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "openId", label: "微信openId", type: "text", placeholder: "请输入微信openId",  labelCols: "3"},
                {name: "buyDealerName", label: "购车经销商", type: "text", placeholder: "请输入购车经销商",  labelCols: "3"},
                {name: "repairDealerName", label: "维修经销商", type: "text", placeholder: "请输入维修经销商",  labelCols: "3"},
                {name: "model", label: "车型", type: "text", placeholder: "请输入车型",  labelCols: "3"},
                {name: "style", label: "车款", type: "text", placeholder: "请输入车款",  labelCols: "3"},
                {name: "color", label: "颜色", type: "text", placeholder: "请输入颜色",  labelCols: "3"},
                {'label': '购车时间', 'type': 'dateRange', name:'buy','labelCols': '3'}
            ]
        },
        grid: {
            settings: {
                transCode: "carInfoPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "微信openId", width: "20%", field: "openId"},
                {name: "购车日期", width: "20%", field: "buyDate"},
                {name: "购车经销商代码", width: "20%", field: "buyDealerCode"},
                {name: "购车经销商", width: "20%", field: "buyDealerName"},
                {name: "维修经销商代码", width: "20%", field: "repairDealerCode"},
                {name: "维修经销商", width: "20%", field: "repairDealerName"},
                {name: "车型", width: "20%", field: "model"},
                {name: "车款", width: "20%", field: "style"},
                {name: "颜色", width: "20%", field: "color"}
            ],
            rowOperation: {show: false}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {

            },
            operationEvents: []
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //打开modal
    function openModal(source) {
        var entity;
        if (source != undefined && source != null && source.length != 0) {
            entity = source[0];
        }

        var instance = $modal.open({
            animation: true,
            templateUrl: 'db/db-form.html',
            controller: 'appLinkEditorCtrl',
            size: "md",
            backdrop: "static",
            resolve: {
                source: function () {
                    return entity;

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
        var selectRows = $scope.dbFormGrid.getAllSelectRows();
        if (selectRows.length === 0) {
            dbUtils.info('请选择需要删除的行');
            return;
        }
        var ids = dbUtils.getFieldArray(selectRows, "id");

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
