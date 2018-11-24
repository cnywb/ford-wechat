/**
 * Created by maojiawei on 16/3/30.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("userInfoListCtr", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "openId", label: "微信openId", type: "text", placeholder: "请输入微信openId",  labelCols: "3"},
                {name: "userName", label: "用户名", type: "text", placeholder: "请输入用户名",  labelCols: "3"},
                {name: "license", label: "驾照号码", type: "text", placeholder: "请输入驾照号码",  labelCols: "3"},
                {name: "mobile", label: "联系电话", type: "text", placeholder: "请输入联系电话",  labelCols: "3"},
                {name: "email", label: "联系email", type: "text", placeholder: "请输入联系email",  labelCols: "3"}
            ]
        },
        grid: {
            settings: {
                transCode: "userInfoPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "微信openId", width: "20%", field: "openId"},
                {name: "用户名", width: "20%", field: "userName"},
                {name: "性别", width: "20%", field: "gender"},
                {name: "用户生日", width: "20%", field: "birthday"},
                {name: "驾照号码", width: "20%", field: "license"},
                {name: "联系电话", width: "20%", field: "mobile"},
                {name: "联系地址", width: "20%", field: "address"},
                {name: "联系email", width: "20%", field: "email"}
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
