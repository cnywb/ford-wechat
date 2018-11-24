<template>
    <div>
        <template v-for="type in iconMenus">
            <div class="all-item">
                <h4><span></span>{{type.typeName}}</h4>
                <div class="index-content">
                    <ul>
                        <template v-for="menu in type.menus">
                            <li v-on:click="menuClick(menu)" v-if="(menu.href && menu.href.indexOf('tel:') != 0)">
                                <img :src="menu.imageUrl">
                                <span :class="{'redTip':menu.redTip}">{{menu.name}}</span>
                            </li>
                            <li v-else>
                                <a :href="menu.href"><img :src="menu.imageUrl">
                                    <span :class="{'redTip':menu.redTip}">{{menu.name}}</span></a>
                            </li>
                        </template>
                    </ul>
                </div>
            </div>
        </template>
        <div class="index-header-Unautherized-shade" v-cloak v-if="showUnAuth">
            <div class="index-header-Unautherized-shade-triangle"></div>
            <div class="display-table">
                <div class="display-table-cell">
                    <h1>温馨提示</h1>
                    <p style="padding: 1rem 0;">您还未完成认证，<br>请进行认证后再开始查询。</p>
                    <img src="../../static/img/icon-close.png" alt="关闭" v-on:click="closeShowUnAuth"
                         class="index-header-Unautherized-shade-close">
                    <button class="buttonBg autherized" v-on:click="go2Bind()">即刻认证  ></button>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>
    a {
        color: black;
    }

    .redTip {
        color: red;
    }

    .index-content ul li {
        float: left;
    }

    .index-header-Unautherized-shade {
        top: 0;
    }

    .buttonBg {
        background: linear-gradient(to right, #3cc4c2, #2d97ce);
        margin-top: 1.0rem;
    }
</style>
<script>
    import Http from '../utils/http';

    export default {
        data () {
            return {
                //当前用户是否认证
                isAuth: false,
                openId: '',
                vin: '',
                //弹出未认证提示界面
                showUnAuth: false,
                //icon图标列表，默认排序,通过两层循环解决ul动态生成的问题
                //默认先从本地缓存当中获取
                iconMenus: [],
            }
        },
        methods: {
            init: function () {
                let isAuth = window.sessionStorage.getItem("isAuth");
                if (isAuth == "false") {
                    this.isAuth = false;
                } else {
                    this.isAuth = (isAuth || isAuth === "true");
                }

                let openId = window.sessionStorage.getItem("openId");
                this.openId = openId || "";

                let vin = window.sessionStorage.getItem("vin");
                this.vin = vin || "";
            },
            //查询图标
            queryIconMenus: function () {
                let middleData = [];

                new Http("/api/public/menu/findAll", {}, (data) => {
                    //将服务端返回平铺格式的数据转换成按分类分组的数据
                    data.forEach(function (item, index) {
                        let typeData = findTypeData(item.typeId);
                        if (typeData == null) {
                            //分类不存在时，先创建分类，再往分类里面存放菜单
                            typeData = {typeId: item.typeId, typeName: item.typeName, order: item.typeOrder, menus: []};
                            middleData.push(typeData);
                        }
                        //只有存在name的数据，才当做一个菜单，否则只是一个分类数据
                        if (item.name) {
                            typeData.menus.push(item);
                        }

                    });
                    //先对分类排序
                    middleData.sort(function (a, b) {
                        return b.order - a.order;
                    });
                    //格式转换
                    middleData.forEach(function (item, index) {
                        if (item.menus.length == 0) {
                            middleData.splice(index, 1);
                        }
                        //对每个分类下的菜单进行排序
                        item.menus.sort(function (a, b) {
                            return b.order - a.order
                        });
                    });

                    this.iconMenus = middleData;

                });

                function findTypeData (typeId) {
                    let typeData = null;
                    middleData.forEach(function (item) {
                        if (item.typeId == typeId) {
                            typeData = item;
                            return;
                        }
                    });
                    return typeData;
                }
            },
            closeShowUnAuth: function () {
                this.showUnAuth = false;
            },
            //图标菜单点击事件
            menuClick: function (menu) {
                let to = menu['href'];
                if (!to) {
                    return;
                }
                //需要弹出提示的
                let requiredAuth = menu['requiredAuth'];
                this.recordMenu(menu.id, menu.name, menu.order);
                if (!this.isAuth && requiredAuth) {
                    this.showUnAuth = true;
                    return;
                } else if (to.indexOf("http") == 0 || to.indexOf("tel:") == 0) {
                    if (to.indexOf("?") > -1) {
                        to += "&openid=" + this.openId;
                    } else {
                        to += "?openid=" + this.openId;
                    }
                    window.location.href = to;
                } else {
                    this.$router.push(to);
                }
            },
            go2Bind: function () {
                window.location.href = window.location.origin + "/bind/static/init.html?state=31&channelCode=10001&openId=" + this.openId;
            },
            //菜单点击次数记录通用方法
            recordMenu: function (id, name, sort) {
                new Http("/api/public/browse/menu", {id: id, name: name, sort: sort});
            }
        },
        mounted () {
            this.init();
            this.queryIconMenus();
        }
    };

    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o),
            m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', 'https://www.google-analytics.com/analytics.js?v=201706051448', 'ga');

    ga('create', 'UA-100492557-1', 'auto');
    ga('send', 'pageview');
    ga('send', 'event', '全部菜单', '全部菜单', '全部菜单');
</script>