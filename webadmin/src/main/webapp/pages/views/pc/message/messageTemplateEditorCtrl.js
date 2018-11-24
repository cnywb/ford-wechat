/**
 * Created by ziv.hung on 16/1/14.
 */

var DBApp = angular.module("DBApp");

DBApp.controller('messageTemplateEditorCtrl', ['$scope', '$modalInstance', '$modal', '$window', 'dbUtils', 'source', function ($scope, $modalInstance, $modal, $window, dbUtils, source) {
    //!!FORM--START!!
    $scope.dbForm = {
        settings: {cols: 2},
        sections: [{
            sectionTitle: {show: true, icon: "jigou", label: "模板基础信息"},
            fields: [
                {name: "code", label: "模板代码", type: "text", labelCols: "2", required: true, placeholder: "模板代码"},
                {name: "name", label: "模板名称", type: "text", labelCols: "2", required: true, placeholder: "模板名称"},
                {name: "templateType", label: "模板分类", labelCols: "2", type: "select", dropDownItemType: "json", required: true, dropDownItem: "templateType"},
                {name: "useChannel", label: "使用渠道", labelCols: "2", type: "multiple", dropDownItemType: "json", required: true, dropDownItem: "useChannel"}
            ]
        }, {
            sectionTitle: {show: true, icon: "jigou", label: "模板内容（注：参数约定必须包含${}大小写敏感，例如：${orderNo}）"},
            fields: [
                {name: "content", label: "模板内容", cols: 12, labelCols: "1", required: true, type: "textarea", rows: 10}
            ]
        }],
        originData: angular.copy(source),
        defaultData: {code: "TEMPLATE_NOTICE_2017513_0000", name: "消息模板公告2017513版", templateType: {name: "公告消息", value: "公告消息"}, useChannel: [{name: "微信平台", value: "微信平台"}, {name: "个人中心", value: "个人中心"}], content: "尊敬的 ${customerName} 您购买的东西已支付成功。\n 购买商品：${productName}\n支付订单：${orderNo}\n 支付金额：${amount}\n 支付时间：${payTime}\n 感谢您的光临，祝您生活愉快。"},
        resetFormDataCallBack: function () {
            if (source) {
                $scope.dbDataTable.rows = angular.copy(source['params']);
                $scope.setOtherTabInfo("params", angular.copy(source['params']));
            }
        }
    };

    if (source) {
        $scope.dbForm.isEditView = true;
        $scope.dbForm.showEditBtn = true;
        $scope.dbForm.globalFieldEditor = true;
    }

    /**
     * 提交数据信息
     *
     * @param isValid 页面校验是否通过
     */
    $scope.submitForm = function (isValid) {
        $scope.dbForm.submited = true;

        var reqBody = angular.copy($scope.dbForm.getFormData());
        var content = reqBody['content'];
        if (!content) {
            dbUtils.error("模板内容不能为空!");
            return;
        }

        if (isValid) {
            var useChannels = reqBody['useChannel'];
            if (!useChannels || useChannels.length == 0) {
                dbUtils.error("使用渠道不能为空！");
                return;
            }
            var containsParams = content.indexOf("${") + 1;
            if (containsParams > 0) {
                if (!$scope.params || $scope.params.length < 1) {
                    dbUtils.error("模板参数不能为空！");
                    return;
                } else if (containsParams != $scope.params.length) {
                    dbUtils.error("模板参数数量不等于内容参数数量！");
                    return;
                }
            }
            reqBody["content"] = content;
            reqBody["params"] = $scope.params;
            console.log(reqBody);
            dbUtils.confirm("确认信息正确,执行提交操作?", function () {
                dbUtils.post("messageTemplateHandle", reqBody, function (data) {
                    if (data) {
                        dbUtils.error(data);
                    } else {
                        dbUtils.success("数据提交成功");
                        $modalInstance.close();
                    }
                });
            });
        } else {
            console.log("校验不通过");
        }
    };
    /* 汇总其他人员信息数据 */
    $scope.setOtherTabInfo = function (field, rows) {
        $scope.params = rows;
    };

    /* 页面关闭按钮事件 */
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
    /**---------------------------------------模板参数配置---------------------------------------*/
    var dbDataTable = {
        title: {icon: "jigou", label: "模板参数定义"},
        tableHeaders: [
            {label: "参数key", field: "paramKey", dataType: "text", required: true},
            {label: "key描述", field: "keyDesc", dataType: "text", required: true}
        ],
        distinct: {
            msg: "参数Key不能重复!",
            fields: ["paramKey"]
        },
        requiredMsg: "配置参数信息均必填!",
        rows: [],
        afterAdd: function (rows) {
            $scope.setOtherTabInfo("params", rows);

        }
    };
    if (!source) {
        var params = [
            {
                keyDesc: "客户名称",
                paramKey: "customerName"
            },
            {
                keyDesc: "购买商品",
                paramKey: "productName"
            },
            {
                keyDesc: "订单编号",
                paramKey: "orderNo"
            },
            {
                keyDesc: "支付金额",
                paramKey: "amount"
            },
            {
                keyDesc: "支付时间",
                paramKey: "payTime"
            }
        ];
        $scope.setOtherTabInfo("params", params);
    }

    $scope.dbDataTable = dbDataTable;

}]);