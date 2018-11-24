/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * MessageRecordEntityListCtrl.js 2017-05-19 15:16:27
 */
angular.module('DBApp').controller('messageRecordEntityCtrl', ['$scope', '$modal', 'dbUtils', function ($scope, modal, dbUtils) {

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
                transCode: "messageRecordEntityPage",
                autoLoad: true,
                showCheckBox: true
            },
            header: [
                            { "name": "id", "width": "10%", "field": "id" },
                            { "name": "openId", "width": "10%", "field": "openId" },
                            { "name": "vin", "width": "10%", "field": "vin" },
                            { "name": "mobile", "width": "10%", "field": "mobile" },
                            { "name": "msgClass", "width": "10%", "field": "msgClass" },
                            { "name": "msgType", "width": "10%", "field": "msgType" },
                            { "name": "content", "width": "10%", "field": "content" },
                            { "name": "invalidTime", "width": "10%", "field": "invalidTime" },
                            { "name": "pushChannel", "width": "10%", "field": "pushChannel" },
                            { "name": "sendStatus", "width": "10%", "field": "sendStatus" },
                            { "name": "sendResult", "width": "10%", "field": "sendResult" },
                            { "name": "msgSource", "width": "10%", "field": "msgSource" },
                            { "name": "sourceChannel", "width": "10%", "field": "sourceChannel" },
                        ],
            rowOperation: {show: true}
        }
    };
    //!!formGridOptions-END!
    var formGridEvents = {
        grid: {
            /*字段渲染时相关事件
            fieldEvents: {
                "contentFormat": function (value, row) {
                    row["content_"] = value;
                    if (value.length > 30) {
                        row["content_"] = value.substr(0, 30);
                    }
                    return row["content_"];
                }
            },*/
            rowEvents: [{
                name: "修改", class: "btn-primary", icon: "glyphicon-edit", click: function (currentRecord) {
                    openModal(currentRecord);
                }, isDisabled: function (currentRecord) {
                    return false;
                }
            }],
            operationEvents:[{name:"删除",class:"btn-danger",icon:"",click:function(rows){
                if(rows.length==0){
                    dbUtils.error("请选择要删除的行！");
                    return;
                }
                var ids = [];
                angular.forEach(rows,function (row) {
                    ids.push(row.id);
                });
                dbUtils.confirm("确定要对所选行进行<span style='color: red'>删除</span>操作?", function () {
                    dbUtils.post('messageRecordEntityDelete', {'ids': ids}, function (data) {
                        dbUtils.success("删除成功！!");
                            $scope.dbFormGrid.reLoadData();
                    }, function (error) {
                        dbUtils.error("删除处理异常!" + error);
                    });
                });

            }},{name:"新增",class:"btn-primary",icon:"",click:function(rows){
                openModal();
            }}]
        }
    };

    $scope.dbFormGrid = {options: formGridOptions, events: formGridEvents};

    //新增或修改
    function openModal(source) {
        var instance = modal.open({
            animation: true,
            templateUrl: 'db/db-form.html',
            size: "lg",
            controller: ['$scope', '$modalInstance', '$timeout', 'dbUtils', 'source',function($scope, $modalInstance, $timeout, dbUtils, source) {
                //!!FORM--START!!
                $scope.dbForm = {
                    settings: {showClose: true, transCode: "messageRecordEntityHandle", cols: 2},
                    title: {icon: "glyphicon-edit", label: "新增或者编辑"},
                    sections: [
                        {
                            sectionTitle: {show: false, icon: "glyphicon-list", label: ""},
                            fields: [
                                                    { "name": "id", "label": "id" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "openId", "label": "openId" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "vin", "label": "vin" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "mobile", "label": "mobile" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "msgClass", "label": "msgClass" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "msgType", "label": "msgType" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "content", "label": "content" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "invalidTime", "label": "invalidTime" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "pushChannel", "label": "pushChannel" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "sendStatus", "label": "sendStatus" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "sendResult", "label": "sendResult" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "msgSource", "label": "msgSource" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    { "name": "sourceChannel", "label": "sourceChannel" ,type: "text", disabled: false,required: true,placeholder: ""},
                                                    ]
                        }],
                    defaultData: {},
                    originData:source,
                    events: {
                        beforeSubmit: function (reqBody) {
                            reqBody['id'] = source['id'];
                            var formData = angular.copy($scope.dbForm.formData);
                            //reqBody["auditCaOrgName"] = formData['auditCaOrgCode'].name;
                            return true;
                        },
                        afterSubmit: function () {
                            dbUtils.success("提交成功!");
                                $modalInstance.close();
                        },
                        modalClose: function () {
                                $modalInstance.dismiss("cancel");
                        }
                    }
                };
                //!!FORM-END!!
            }],
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
}]);
