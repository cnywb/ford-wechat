/**
 * Created by zhaoliang on 2017/5/4.
 */
var DBApp = angular.module('DBApp');

DBApp.controller("channelListCtr", ['$scope', '$modal', 'dbUtils','$http', function($scope, $modal, dbUtils,$http){
    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 2
            },
            fields: [
                {name: "dealerCode", label: "销售码", type: "text", placeholder: "请输入销售代码",  labelCols: "3"},
                {name: "code", label: "渠道代码", type: "text", placeholder: "请输入渠道代码",  labelCols: "3"},
                {name: "name", label: "渠道名称", type: "text", placeholder: "请输入渠道名称",  labelCols: "3"},
                {name: "type", label: "类型", type: "select",   dropDownItemType: "json", dropDownItem: "dealerTypeSelect", labelCols: "3", editable:true , required: true}
            ]
        },
        grid: {
            settings: {
                transCode: "weChannelPage",
                autoLoad: true,
                page: {pageSize: 10},
                showCheckBox: true
            },
            header: [
                {name: "销售码", width: "5%", field: "dealerCode",lineFeed:true},
                {name: "经销商码", width: "5%", field: "dealerServiceCode",lineFeed:true},
                {name: "渠道代码", width: "5%", field: "code",lineFeed:true},
                {name: "渠道名称", width: "10%", field: "name",lineFeed:true},
                {name: "小区", width: "8%", field: "smallArea",lineFeed:true},
                {name: "大区", width: "5%", field: "bigArea",lineFeed:true},
                {name: "省", width: "5%", field: "province",lineFeed:true},
                {name: "市", width: "8%", field: "city",lineFeed:true},
                {name: "地址", width: "5%", field: "address",lineFeed:true},
                {name: "邮编", width: "5%", field: "zipCode",lineFeed:true},
                {name: "类型", width: "8%", field: "type_",lineFeed:true},
                {name: "备注", width: "10%", field: "remark",lineFeed:true}
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
            controller: 'channelEditorCtrl',
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
            dbUtils.post('weChannelRemove', {'ids': ids}, function (data) {
                dbUtils.success("删除成功！!");
                $scope.dbFormGrid.reLoadData();
            }, function (error) {
                dbUtils.error("删除处理异常!" + error);
            });
        });
    }

}]);