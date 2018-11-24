<template>
    <div>
        <div style="position: fixed;width: 100%;z-index: 1000;">
            <div class="index-header information-title">
                <h1><span>车主活动</span> <img src="../../static/img/icon-huodong.png" style="height: 3rem;margin-top: -0.5rem;"></h1>
            </div>
        </div>
        <div style="height: 100%; padding-top: 6.5rem;" class="activity">
            <template v-for="type in iconMenus">
                <div class="all-item">
                    <div class="flex-container">
                        <template v-for="menu in type.menus">
                            <div class="flex-item " v-if="(menu.href && menu.href.indexOf('tel:')!=0 )">
                                <img :src="menu.imageUrl">
                                <span class="item-menu-name">{{menu.name}}</span>
                                <span class="item-details" v-on:click="menuClick(menu)">了解详情 > </span>
                            </div>
                            <div v-else>
                                <a :href="menu.href">
                                    <img :src="menu.imageUrl">
                                    <span>{{menu.name}}</span>
                                </a>
                            </div>
                        </template>
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
    </div>
</template>
<!--该页面的样式不加scoped，目的是为了替换默认样式-->
<style>

    .flex-container {
        display: flex;
        width: 100%;
        justify-content: flex-start;
        flex-direction: row;
        flex-wrap: wrap;
        align-content: space-around;
        align-items: stretch;
        padding-bottom: 0.5rem;
    }

    .flex-item {
        border: 1px solid #eaeaea;
        margin: 0.5rem 0.5rem 0rem 0.5rem;
        padding: 0.5rem 0.5rem;
        height: 13rem;
        text-align: center;
        width: 100%;
        vertical-align: bottom;
    }

    .flex-item img {
        display: block;
        width: 100%;
        height: 100%;
        margin: auto;
    }

    .flex-item .item-menu-name {
        color: #ffffff;
        font-size: 14px;
        align-content: center;
        position: absolute;
        margin-top: -2rem;
        left: 1.5rem;
    }

    .flex-item .item-details {
        background-color: rgb(45, 150, 205);
        color: rgb(255, 255, 255);
        border-radius: 1rem;
        width: 80px;
        float: right;
        font-size: 12px;
        margin-top: -2rem;
        position: absolute;
        right: 1.5rem;
    }

    .all-item h4 {
        color: #2d96cd;
        font-size: 14px;
        font-weight: 400;
    }

    #app {
        height: 100%;
        background-color: white;
    }

    a {
        color: black;
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
                iconMenus: [{
                    typeName: "车主活动", menus: []
                }],
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
                        to += "&openId=" + this.openId;
                    } else {
                        to += "?openId=" + this.openId;
                    }
                    window.location.href = to;
                } else {
                    this.$router.push(to);
                }
            },
            loadActivity: function () {
                new Http("/api/public/activity/findAll", {}, (data) => {
                    console.log(data);
                    this.iconMenus[0]['menus'] = data;
                    console.log(this.iconMenus[0]['menus']);
                });
            },
            //菜单点击次数记录通用方法
            recordMenu: function (id, name, sort) {
                new Http("/api/public/browse/menu", {id: id, name: name, sort: sort});
            }
        },
        mounted () {
            this.init();
            this.loadActivity();
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