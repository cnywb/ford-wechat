/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntityListCtrl.js 2017-05-05 15:37:27
 */
angular.module('DBApp').controller('iconMenuPublishCtrl',
    ['$scope', '$modal', 'FileUploader', 'dbUtils', 'dbImService', function ($scope, modal, FileUploader, dbUtils, dbImService) {
        //分类下拉框
        dbImService.bindImCode($scope, null, "iconMenuType");
        //页面添加时的对象
        $scope.iconMenu = {};

        //属于首页的图标数据
        $scope.indexIconMenus = [];
        //转换后用于显示的首页图标数据
        $scope.indexIconMenusViewData = [];

        //所有icon列表，页面渲染需要的格式
        $scope.iconMenusViewData = [];

        /**
         * 从服务端返回的数据格式，平铺的方式。
         * 示例：
         * [
         * {typeId:1,typeName:"活动",name: "工单查询", imgUrl: "/static/img/icon-gongdan.png", href: "",order:999},
         * {typeId:1,typeName:"活动",name: "工单查询", imgUrl: "/static/img/icon-gongdan.png", href: "",order:998},
         * ]

         服务端转换后的中间状态数据，根据分类可以直接获取该分类下的所有数据
         示例:
         [{ typeId:1,typeName:"活动",order:999,menus:[
            {name: "工单查询", imgUrl: "/static/img/icon-gongdan.png", href: "",order:999},
            {name: "工单查询", imgUrl: "/static/img/icon-gongdan.png", href: "",order:999}]},
         { typeId:2,typeName:"售前",order:999,menus:[
                {name: "工单查询", imgUrl: "/static/img/icon-gongdan.png", href: "",order:999},
                {name: "工单查询", imgUrl: "/static/img/icon-gongdan.png", href: "",order:999}]}

         ]
         */
        $scope.iconMenusMiddleData = [];
        //查询所有icon列表，并将值转换成页面显示所需要的格式
        function findAllIconMenus() {
            $scope.iconMenu = {};
            dbUtils.post("findAllIconMenus", {}, function (data) {
                conventQueryData2MiddleData(data);
                convertMiddleData2ViewData();
            })
        }
        //查询所有图标
        findAllIconMenus();

        //将服务端返回的平铺的数据格式转换为中间状态格式（按type分类）
        function conventQueryData2MiddleData(queryData) {
            $scope.iconMenusMiddleData = [];
            $scope.indexIconMenus = [];
            $scope.indexIconMenusViewData = [];
            //后台返回的数据，要求所有分类都要有一个值
            angular.forEach(queryData, function (data) {
                var typeData = findMiddleTypeDataByType(data.typeId);
                if (typeData == null) {
                    //分类不存在时，先创建分类，再往分类里面存放菜单

                    typeData = {typeId: data.typeId, typeName: data.typeName, order: data.typeOrder, menus: []};
                    //只有存在name的数据，才当做一个菜单，否则只是一个分类
                    if (data.name) {
                        typeData.menus.push(data);
                        if (data.recommend) {
                            $scope.indexIconMenus.push(data);
                        }
                    }
                    $scope.iconMenusMiddleData.push(typeData);
                } else {
                    //只有存在name的数据，才当做一个菜单，否则只是一个分类
                    if (data.name) {
                        typeData.menus.push(data);
                        if (data.recommend) {
                            $scope.indexIconMenus.push(data);
                        }
                    }
                }
            });
        }

        function findMiddleTypeDataByType(typeId) {
            var typeData = null;
            angular.forEach($scope.iconMenusMiddleData, function (item) {
                if (item.typeId == typeId) {
                    typeData = item;
                    return;
                }
            });
            return typeData;
        }

        //将中间状态数据转换为页面渲染要用的数据
        function convertMiddleData2ViewData() {
            //先对分类进行排序
            $scope.iconMenusMiddleData.sort(function (a, b) {
                return b.order - a.order;
            });
            $scope.iconMenusViewData = [];
            //数据格式转换
            angular.forEach($scope.iconMenusMiddleData, function (iconMenus) {
                var menus = convertMenus2View(iconMenus.menus);
                var type = {typeName: iconMenus.typeName, typeId: iconMenus.typeId, menus: menus};
                $scope.iconMenusViewData.push(type);
            });

            //首页显示的数据格式转换
            $scope.indexIconMenusViewData = convertMenus2View($scope.indexIconMenus);
        }

        /**
         * 按页面显示的方式转换格式，页面每行只显示4个。
         * 需要将所有数据转化成4个为一组的对象。
         */
        function convertMenus2View(data) {
            //先进行排序，再进行分组显示
            data.sort(function (a, b) {
                return b.order - a.order
            });
            var menus = [];
            var tmp = [];
            angular.forEach(data, function (item, index) {
                if (index != 0 && index % 4 == 0) {
                    menus.push({"menus": tmp});
                    tmp = [];
                }
                tmp.push(item);
            });
            //不满足4个时，补充空位，避免页面显示错位
            if (tmp.length < 4) {
                var j = tmp.length;
                for (var i = 4; i > j; i--) {
                    tmp.push({});
                }
            }
            if (tmp.length > 0) {
                menus.push({"menus": tmp});
            }
            return menus;
        }


        function isEmpty(value) {
            return angular.isUndefined(value) || value == "" || value == "null" || value == "undefined";
        }

        //对添加的内容进行预览
        $scope.preview = function () {
            if (isEmpty($scope.iconMenu.type)) {
                dbUtils.error("请选择所属分类!");
                return
            }
            if (isEmpty($scope.iconMenu.name)) {
                dbUtils.error("请填写名字!");
                return
            }

            if (isEmpty($scope.iconMenu.imageUrl)) {
                dbUtils.error("请上传图片!");
                return
            }

            if (isEmpty($scope.iconMenu.href)) {
                dbUtils.error("请填写链接地址!");
                return
            }
            if (isEmpty($scope.iconMenu.order)) {
                dbUtils.error("请填写排序值!");
                return
            }

            //将新的图标加入到右侧显示当中
            //先加入到中间状态值，再转换为显示格式数据
            var newMenu = angular.copy($scope.iconMenu);
            newMenu["typeName"] = newMenu["type"]["value"];
            newMenu["new"] = true;
            joinNewMenu($scope.iconMenusMiddleData, newMenu);
                joinNewIndexMenu($scope.indexIconMenus, newMenu);
            //重新渲染
            convertMiddleData2ViewData();
        };

        /**
         * 加入一个新的菜单
         * @param array 要加入的数据组
         * @param newMenu 新菜单
         */
        function joinNewMenu(array, newMenu) {
            var hasJoin = false;
            angular.forEach(array, function (data) {
                //删除原有值
                angular.forEach(data.menus, function (menu, index) {
                    if (menu["new"]) {
                        data.menus.splice(index, 1);
                    }
                });
                if ((data.typeName != newMenu.typeName) || hasJoin) {
                    return;
                }
                data.menus.push(newMenu);
                hasJoin = true;
                return;
            });
        }

        function joinNewIndexMenu(array,newMenu) {
            angular.forEach(array, function (menu, index) {
                if (menu["new"]) {
                    array.splice(index, 1);
                }
            });
            if(newMenu.recommend) {
                array.push(newMenu);
            }
        }

        //表单提交
        $scope.submit = function (valid) {
            $scope.submited = true;
            if (!valid) {
                return;
            }
            if (isEmpty($scope.iconMenu.imageUrl)) {
                dbUtils.error("请上传图片!");
                return
            }
            var newMenu = angular.copy($scope.iconMenu);
            newMenu["typeName"] = newMenu["type"]["value"];
            newMenu["typeCode"] = newMenu["type"]["value"];
            dbUtils.confirm("确定添加吗？添加后实时发布到系统上！", function () {
                dbUtils.post("iconMenuEntityHandle", newMenu, function (data) {
                    dbUtils.success("添加成功!");
                    $scope.submited=false;
                    findAllIconMenus();
                })
            });
        };
        //菜单删除
        $scope.deleteMenu = function (menuId) {
            dbUtils.confirm("确定删除吗?", function () {
                dbUtils.post("iconMenuEntityDelete", {ids: [menuId]}, function (data) {
                    dbUtils.success("删除成功!");
                    findAllIconMenus();
                })
            });

        };

        //文件上传
        var uploader = $scope.uploader = new FileUploader({
            url: '../resourceStore/resourceStoreHandle.ctl',
            autoUpload: true,//自动上传
            removeAfterUpload: true,
        });

        // FILTERS
        uploader.filters.push({
            name: 'imageFilter',
            fn: function (item /*{File|FileLikeObject}*/, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        });

        uploader.onCompleteItem = function (fileItem, response, status, headers) {
            console.info('onCompleteItem', fileItem, response, status, headers);
            if (status != 200) {
                dbUtils.error("上传异常!" + response.errorMsg);
                fileItem.cancel();
                uploader.clearQueue();
            } else {
                $scope.iconMenu.imageUrl = response.url;
                $scope.$apply();
                dbUtils.success("上传成功!");
            }
        };


        window.setTimeout(function () {
            if ($().datepicker) {
                $('.date-picker').datepicker({
                    rtl: Metronic.isRTL(),
                    orientation: "left",
                    autoclose: true,
                    language: 'zh-CN'
                });
            }
        }, 1000)

    }]);
