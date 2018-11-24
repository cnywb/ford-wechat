<template>
    <div>
        <div class="index-header">
            <div v-cloak v-if="isAuth">
                <h4 class="padder-v"><i class="icon-vin"></i> {{vin}}<i v-if="vinList.length > 1" class="redTip icon-vin-more" v-on:click="showMoreVin"></i></h4>
                <p class="padder-l-xxl f-s">会员号 <span class="owner-serial">{{userNum}}</span></p>
                <img class="owner-heade" :src="headImg">
            </div>
            <!--未认证部分开始-->
            <div class="index-header-Unautherized" v-else>
                即刻成为长安福特认证车主，尊享更多身份特权
                <button class="autherized" v-on:click="go2Bind()">即刻认证  ></button>
            </div>
            <!--未认证部分开始-->
        </div>
        <!--未认证部分开始-->
        <div class="index-header-Unautherized-shade" v-cloak v-if="showUnAuth">
            <div class="index-header-Unautherized-shade-triangle"></div>
            <div class="display-table">
                <div class="display-table-cell">
                    <h1>温馨提示</h1>
                    <p style="padding: 1rem 0;">您还未完成认证，<br>请进行认证后再开始查询。</p>
                    <img src="../../static/img/icon-close.png" alt="关闭" v-on:click="closeShowUnAuth" class="index-header-Unautherized-shade-close">
                    <button class="buttonBg autherized" v-on:click="go2Bind()">即刻认证  ></button>
                </div>
            </div>
        </div>
        <!--未认证部分结束-->
        <div class="index-content">

            <template v-for="menus in iconMenus">
                <ul>
                    <template v-for="menu in menus.menus">
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
            </template>
        </div>
        <!--<div class="index-news">
            <div class="index-news-header">
                <div class="fl">
                    <img src="..../../static/img/icon-zhangdan-active.png" style="height: 2.8rem;">
                </div>
                <div class="fl padder-l">
                    <p>电子账单</p>
                    <p>11:32</p>
                </div>
            </div>
            <div class="index-news-content">
                <p style="font-size: 30px;letter-spacing: 4px;">******</p>
                <p class="index-news-content-more">查看详情</p>
            </div>
        </div>-->

        <template v-for="msg in msgRecord">
            <div class="index-news" v-on:click="msgDetail(msg)">
                <h4 class="change-line">{{msg.title}}</h4>
                <div class="date">{{msg.publishTime}}</div>
                <p>{{msg.content}}</p>
            </div>
        </template>
        <div style="text-align: center;height: 2rem;color: #39768e;margin-top: 1rem;" v-on:click="showMoreMsg()">
            查看全部消息
        </div>


        <div class="shade center-flex" v-if="showVins">
            <div class="upkeep-model">
                <div class="text-center padder font-xl margin-t-xl" style="color: #2d96cd;">
                    请选择绑定vin码
                </div>
                <div>
                    <select class="b-b" v-model="vin">
                        <template v-for="item in vinList">
                            <option v-bind:value="item">{{item}}</option>
                        </template>
                    </select>
                </div>
                <div class="text-center padder">
                    <button class="upkeep-seek-btn mg-xxxl" v-on:click="confirmChangeVin">确认</button>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>

    .b-b {
        border: 1px solid #2d96cd;
        background: none;
        padding: 0.5rem 1rem;
        width: 80%;
        text-align: center;
        color: #2d96cd
    }

    .center-flex {
        position: absolute;
        align-items: center;
        flex-direction: column;
    }

    .upkeep-model {
        position: absolute;
        left: 10%;
        right: 10%;
        top: 30%;
        width: 80%;
        border-radius: 10px;
        color: #1b394e;
        background: #ffffff;
        padding: 0.5rem 1.5rem 1rem 1.5rem;
        height: 180px;
        text-align: center;
    }

    .padder {
        padding: 1rem;
    }

    .text-center {
        text-align: center;
    }

    .upkeep-seek-btn {
        background: #2d96cd;
        width: 65%;
        color: #fff;
        padding: 0.2rem 0;
        font-size: 16px;
        border-radius: 2rem;
    }

    .icon-vin {
        background: url(../../static/img/icon-vin.png) 0 0 no-repeat;
        background-size: 100% 100%;
        display: inline-block;
        width: 60px;
        height: 24px;
        position: relative;
        top: 4px;
        margin-right: 0.5rem;
    }

    .icon-vin-more {
        background: url(../../static/img/icon-more.png) 0 0 no-repeat;
        background-size: 50% 50%;
        display: inline-block;
        width: 26px;
        height: 24px;
        position: relative;
        top: 13px;
        margin: 0 0.5rem;
    }

    h4.change-line {
        width: 55%;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
        float: left;
    }

    .index-news {
        line-height: 1.5rem;
        padding: 1rem 1.2rem;
    }

    div.date {
        width: 45%;
        text-align: right;
        padding-right: 5px;
        float: left;
        color: #747474;
    }

    a {
        color: black;
    }

    .redTip {
        color: red;
    }

    .buttonBg {
        background: linear-gradient(to right, #3cc4c2, #2d97ce);
        margin-top: 1.0rem;
    }
</style>
<script>
    import Http from '../utils/http';
    import Vue from 'vue';
    import VueLocalStorage from 'vue-localstorage';
    Vue.use(VueLocalStorage);

    export default {
        //定义需要存入localStorage的对象
        localStorage: {
            menus: {
                type: Object
            }
        },
        data() {
            return {
                headImg: '/pc/static/img/header.png',
                //当前用户是否认证
                isAuth: false,
                openId: '',
                userNum: "",//用户编号
                showVins: false,//用户编号
                vin: '',
                selectVin: '',
                vinList: [],
                //弹出未认证提示界面
                showUnAuth: false,
                //icon图标列表，默认排序,通过两层循环解决ul动态生成的问题
                //默认先从本地缓存当中获取
                iconMenus: this.$localStorage.get("menus", []),
                msgRecord: []
            }
        },
        methods: {
            init: function () {
                let openId = window.sessionStorage.getItem("openId");
                if (openId == null || openId == '' || this.openId == "undefind") { //openId 不存在
                    window.location.href = "http://www.changanfordwechat.com/fordwechat/control?state=17261";
                    return;
                }
                this.openId = openId || "";
                let isAuth = window.sessionStorage.getItem("isAuth");
                if (isAuth == "false") {
                    this.isAuth = false;
                } else if (isAuth == 'true') {
                    this.isAuth = (isAuth || isAuth === "true");
                    let vin = window.sessionStorage.getItem("vin");
                    this.vin = vin || "";
                    let userNum = window.sessionStorage.getItem("userNum");
                    this.userNum = userNum || "";
                    let vinListStr = window.sessionStorage.getItem("vinList") || [];
                    if (vinListStr && vinListStr != 'undefind' && vinListStr.indexOf(",") > -1) {
                        let vinList = vinListStr.split(",");
                        this.vinList = vinList || [];
                    }
                }
            },
            getHeadImg: function () {
                let headImg = window.sessionStorage.getItem("headImg") || "";
                if (!headImg) {
                    new Http("/api/public/weixin/headImg", {}, (data) => {
                        if (data) {
                            this.headImg = data;
                            window.sessionStorage.setItem("headImg", data);
                        }
                    });
                } else {
                    this.headImg = headImg;
                }
            },
            showMoreVin: function () {
                this.showVins = true;
            },
            confirmChangeVin: function () {
                window.sessionStorage.setItem("vin", this.vin);
                this.showVins = false;
            },
            //查询公告
            queryMessage: function () {
                new Http("/api/public/msg/list", {openId: this.openId, page: {pageNumber: 1, pageSize: 5}}, (data) => {
                    this.msgRecord = data.content;
                });
            },
            //查询图标
            queryIconMenus: function () {
                let pathName = window.location.pathname;
                let projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
                if (projectName === "/") {
                    projectName = "";
                }
                new Http("/api/public/menu/findIndexMenu", {openId: this.openId}, (data) => {
                    let menus = [];
                    let tmp = [];
                    data.forEach(function (item, index) {
                        if (index != 0 && index % 4 == 0) {
                            menus.push({"menus": tmp});
                            tmp = [];
                        }
                        tmp.push(item);
                    });
                    let length = tmp.length;
                    if (length < 4) {
                        tmp.push({name: "全部", imageUrl: window.location.origin + projectName + "/static/img/icon-all.png", href: "/all", id: 0});
                        //补充几个空的
                        for (let i = 3; i > length; i--) {
                            tmp.push({name: "全部", id: 0, order: 0});
                        }
                    } else {
                        //刚好等于4个时，先push原有值，再新建一个数组
                        menus.push({"menus": tmp});
                        tmp = [{name: "全部", imageUrl: window.location.origin + projectName + "/static/img/icon-all.png", href: "/all", id: 0}];
                        for (let i = 3; i > 0; i--) {
                            tmp.push({name: "全部", id: 0, order: 0});
                        }
                    }
                    menus.push({"menus": tmp});
                    this.iconMenus = menus;
                    //存入localStorage当中
                    this.$localStorage.set("menus", menus);
                })
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
            //消息点击事件
            msgDetail: function (msg) {
                this.recordMsg(msg.id, msg.title);
                this.$router.push({name: "公告消息", params: {id: msg.id}});
            },
            //显示全部消息
            showMoreMsg: function () {
                this.recordMsg(0, '消息列表');
                this.$router.push({name: "消息列表"});
            },
            //菜单点击次数记录通用方法
            recordMenu: function (id, name, sort) {
                new Http("/api/public/browse/menu", {id: id, name: name, sort: sort});
            },
            //公告点击次数记录通用方法
            recordMsg: function (id, name) {
                new Http("/api/public/browse/announcement", {id: id, name: name});
            },
            go2Bind: function () {
                window.location.href = window.location.origin + "/bind/static/init.html?state=31&channelCode=10001&openId=" + this.openId;
            }
        },
        mounted() {
            this.init();
            this.queryMessage();
            this.getHeadImg();
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
    ga('send', 'event', '个人中心首页', '个人中心首页', '个人中心首页');
</script>