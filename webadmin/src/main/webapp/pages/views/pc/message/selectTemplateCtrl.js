/**
 * Created by ziv.hung on 17/5/22.
 */

var DBApp = angular.module('DBApp');

DBApp.controller("selectTemplateCtrl", ['$scope', '$modal', '$modalInstance', 'templateType', function ($scope, $modal, $modalInstance, templateType) {

    //!!formGridOptions-START!!
    var formGridOptions = {
        form: {
            settings: {
                cols: 4,//查询条件列
                showClose: true
            },
            fields: [
                {name: "code", label: "模板代码", type: "text", placeholder: "模板代码"},
                {name: "name", label: "模板名称", type: "text", placeholder: "模板名称"},
                {name: "useChannel", label: "使用渠道", type: "select", dropDownItemType: "json", dropDownItem: "useChannel"}
            ],
            hiddenParams: {templateType: templateType}
        },
        grid: {
            settings: {
                transCode: "messageTemplatePage",
                autoLoad: true,//页面初始化自动加载数据
                showCheckBox: false,//是否显示复选框,
                lineFeed: true//强制单行显示
            },
            header: [
                {"name": "模板代码", "width": "18%", "field": "code"},
                {"name": "模板名称", "width": "18%", "field": "name"},
                {"name": "模板分类", "width": "18%", "field": "templateType"},
                {"name": "使用渠道", "width": "18%", "field": "useChannel"},
                {"name": "模板内容", "width": "18%", "field": "content_", omit: true}
            ],
            rowOperation: {show: true, lineFeed: true}//是否显示最后操作列
        }
    };
    //!!formGridOptions-END!!
    var formGridEvents = {
        form: {
            modalClose: function () {
                $modalInstance.dismiss("cancer");
            }
        },
        grid: {
            rowEvents: [//最后的操作列
                {
                    "name": "确定", "class": "btn-primary", icon: "glyphicon-edit", "click": function (row) {
                    $modalInstance.close(row);
                }
                }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};
}]);