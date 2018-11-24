angular.module('DBApp').controller('activityManageCtrl', ['$scope', '$modal', '$state', 'dbUtils', function ($scope, modal, $state, dbUtils) {

    var formGridOptions = {
        form: {
            settings: {
                cols: 3
            },
            fields: [
                {name: "keyWord", label: "关键字", type: "text", placeholder: "关键字查询"}
            ]
        },
        grid: {
            settings: {
                transCode: "activityPage",
                autoLoad: true,
                lineFeed: true
            },
            header: [
                {"name": "排序", "width": "10%", "field": "sortNo"},
                {"name": "活动名称", "width": "25%", "field": "name"},
                {"name": "活动图片", "width": "25%", "field": "imageUrl", type: 'img'},
                {"name": "活动地址", "width": "25%", "field": "href"}
            ],
            rowOperation: {show: true}
        }
    };
    //!!formGridOptions-END!
    var formGridEvents = {
        grid: {
            //字段渲染时相关事件
            rowEvents: [{
                name: "修改", class: "btn-primary", icon: "glyphicon-edit", click: function (currentRecord) {
                    $state.go("activityEntry", {id: currentRecord['id']});
                },

            },
                {
                    name: "删除", class: "btn-danger", icon: "glyphicon-remove", click: function (currentRecord) {
                    dbUtils.confirm("确定重建索引吗？", function () {
                        dbUtils.post('activityDelete', {'ids': [currentRecord['id']]}, function () {
                            dbUtils.success("重建索引成功!");
                            $scope.dbFormGrid.reLoadData();
                        }, function (error) {
                            dbUtils.error("创建索引失败!" + error);
                        });
                    });
                }
                }
            ],
            operationEvents: [{
                name: "一键缓存", class: "btn-primary", icon: "", click: function () {
                    dbUtils.confirm("确定要一键缓存？确认后立刻生效！", function () {
                        dbUtils.post('activityCache', {}, function () {
                            dbUtils.success("一键缓存成功！!");
                        }, function (error) {
                            dbUtils.error("一键缓存异常!" + error);
                        });
                    });
                }
            }, {
                name: "新增", class: "btn-primary", icon: "glyphicon-plus", click: function () {
                    $state.go("activityEntry");
                }
            }]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};
}]);
