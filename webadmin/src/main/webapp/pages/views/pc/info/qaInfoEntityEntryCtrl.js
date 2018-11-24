/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntityListCtrl.js 2017-05-05 15:37:27
 */
angular.module('DBApp').controller('qaInfoEntityEntryCtrl', ['$scope', '$modal', 'dbUtils', '$state', '$stateParams', function ($scope, modal, dbUtils, $state, $stateParams) {

    $scope.data = {};
    var id = $stateParams.id;
    //表单提交
    $scope.submit = function (valid) {
        $scope.submited = true;
        if (!valid) {
            return;
        }
        var content = window.editorDiscoveryContentEditor.$txt.html();
        if (!content) {
            dbUtils.error("问题答案不得为空！", "温馨提醒");
            return;
        }

        var data = angular.copy($scope.data);
        data['id'] = id;
        data['answer'] = content;
        dbUtils.confirm("确定添加吗？添加后实时发布到系统上！", function () {
            dbUtils.post("qaInfoEntityHandle", data, function (str) {
                if(str === "200"){
                    dbUtils.success("数据提交成功!");
                    $scope.submited = false;
                    $state.go("qaInfoEntityManage");
                }else {
                    dbUtils.error("问题答案不得含有特殊字符！", "温馨提醒");
                }

            })
        });
    };

//必须在页面渲染完成后才能初始化富文本编辑器。在controller当中通过全局函数获取实例
    window.editorDiscoveryContentEditor = null;
    window.editorDiscoveryContentEditor = new wangEditor('answerEditor');
    window.editorDiscoveryContentEditor.config.menus = [
        'bold',
        'underline',
        'italic',
        'strikethrough',
        'eraser',
        'forecolor',
        'bgcolor',
        '|',
        'quote',
        'fontfamily',
        'fontsize',
        'head',
        'unorderlist',
        'orderlist',
        'alignleft',
        'aligncenter',
        'alignright',
        '|',
        'link',
        'unlink',
        'table',
        'emotion',
        '|',
        'img',
        '|',
        'fullscreen'
    ];
    window.editorDiscoveryContentEditor.config.uploadImgUrl = '../resourceStore/resourceStoreEditor.ctl';
    window.editorDiscoveryContentEditor.create();
    if ($stateParams['id']) {
        dbUtils.post("qaInfoEntityGet", {id: $stateParams['id']}, function (data) {
            $scope.data = data;
            window.editorDiscoveryContentEditor.$txt.html(data['answer']);
        });
    }
}]);