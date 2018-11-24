<template>
    <div style="background-color: white">
        <div class="index-header information-title">
            <h1>消息详情 <img src="../../static/img/message.png" style="height: 3rem;margin-top: -0.5rem;"></h1>
        </div>
        <div class="msg-content">
            <div style="padding: 0.5rem 1.5rem;">
                {{detail.title}}
            </div>
            <div class="news-tag" style="color: #232a2d;">
                {{detail.content}}
            </div>
            <div class="news-tag" style="text-align: right; padding-right: 2.5rem;color: #9c9c9c;">
                {{detail.publishTime}}
            </div>
            <div class="news-tag" style="text-align: center">
                <a style="color: #424242;" v-on:click="backIndex()">返回</a>
            </div>
        </div>
    </div>
</template>
<style>
    .msg-content {
        background: #fff;
        position: absolute;
        width: 100%;
        height: 83%;
        color: #1b394e;
    }
</style>
<script>
    import Http from '../utils/http';
    export default {
        data() {
            return {
                vin: "",
                openId: "",
                mobile: "",
                detail: {}
            }
        },
        methods: {
            init: function () {
                let openId = window.sessionStorage.getItem("openId");
                this.openId = openId || "";

                let vin = window.sessionStorage.getItem("vin");
                this.vin = vin || "";

                let mobile = window.sessionStorage.getItem("mobile");
                this.mobile = mobile || "";
            },
            //查询公告
            queryMessage: function () {
                new Http("/api/public/msg/detail", {openId: this.openId, vin: this.vin, id: this.$route.params['id']}, (data) => {
                    this.detail = data;
                })
            },
            //返回
            backIndex: function () {
                //返回上一页
                this.$router.go(-1);
            }
        },
        mounted() {
            this.queryMessage();
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
    ga('send', 'event', '公告明细', '公告明细', '公告明细');
</script>