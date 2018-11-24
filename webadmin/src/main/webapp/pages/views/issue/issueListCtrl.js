/**
 * Created by huwanqiang on 2017/3/3.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("issueListCtr", ['$scope', '$modal', 'dbUtils','$http','$window', function($scope, $modal, dbUtils,$http,$window){
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
                {name: "提问时间", width: "10%", field: "createdDate"},
                {name: "答案内容", width: "15%", field: "answerContent"},
                {name: "答案时间", width: "10%", field: "answerDate"}
            ],
            rowOperation: {show: true}
        }
    };

    var formGridEvents = {
        grid: {
            fieldEvents: {

            },
            operationEvents: [{
                name: '导出Excel', class: 'btn-primary', icon: 'x-xlsx', click: function () {
                    exportExcel();
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


    //导出excel
    function exportExcel() {
        var totalRecords = $scope.dbFormGrid.page.totalRecords;
        if (!totalRecords) {
            dbUtils.warning("无查询数据记录不导出！", "温馨提示");
            return;
        }
        dbUtils.confirm("确定要以当前查询条件下的结果数据执行导出操作?", function () {
            var params = $scope.dbFormGrid.getQueryParams(1);

            var openId = params["openId"] || "";
            var title = params['title'] || "";
            var type = params['type'] || "";

            var createEndDate = params['createEndDate'] || "";
            var createStartDate = params['createStartDate'] || "";


            var url = '../issueListExport.ctl?'
                + 'openId='+openId
                + '&title='+title
                + '&type=' + type
                + '&createEndDate=' + createEndDate
                + '&createStartDate=' + createStartDate;

            $window.open(url);
        });
    }

}]);

