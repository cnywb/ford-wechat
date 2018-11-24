<template>
    <div>
        <div style="position: fixed;width: 100%;">
            <div class="index-header information-title">
                <h1><span>消息列表</span></h1>
            </div>
        </div>
        <div style="padding-top: 7rem;">
            <template v-for="msg in msgRecord" v-if="msgRecord.length>0">
                <div class="index-news" v-on:click="msgDetail(msg)">
                    <h4 class="change-line">{{msg.title}}</h4>
                    <div class="date">{{msg.publishTime}}</div>
                    <p>{{msg.content}}</p>
                </div>
            </template>
            <div style="text-align: center;color: #39768e;margin-top: 1rem" v-on:click="queryMessage()" v-if="!queryEnd">加载更多</div>
            <div style="text-align: center;color: #39768e;margin-top: 1rem" v-else="">没有更多新的消息了！</div>
            <template v-else>
                <div style="text-align: center">
                    <h4 v-if="!isLoading || queryEnd">非常抱歉，未找到相关内容！</h4>
                    <h4 v-else>努力加载中，请稍候!</h4>
                </div>
            </template>
        </div>
    </div>
</template>
<style scoped="">
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

    .buttonBg {
        background: linear-gradient(to right, #3cc4c2, #2d97ce);
        margin-top: 1.0rem;
    }
</style>
<script>
    import Http from '../utils/http';
    export default {
        data() {
            return {
                //当前用户是否认证
                openId: '',
                msgRecord: [],
                isLoading: true,
                queryEnd: false,//加载是否完成,
                page: {
                    keyWord: "",
                    pageNumber: 1,
                    pageSize: 10
                }
            }
        },
        methods: {
            init: function () {
                let openId = window.sessionStorage.getItem("openId");
                this.openId = openId || "";
            },
            //查询公告
            queryMessage: function () {
                new Http("/api/public/msg/list", {openId:this.openId,page: this.page}, (data) => {
                    for (let i = 0; i < data.content.length; i++) {
                        let o = data.content[i];
                        this.msgRecord.push(o);
                    }
                    this.isLoading = true;
                    if (data.content.length < this.page.pageSize) {
                        this.queryEnd = true;
                    }
                    this.page.pageNumber++;
                });
            },
            //消息点击事件
            msgDetail: function (msg) {
                this.recordMsg(msg.id,msg.title);
                this.$router.push({name: "公告消息", params: {id: msg.id}});
            },
            //公告点击次数记录通用方法
            recordMsg: function (id, name) {
                new Http("/api/public/browse/announcement", {id: id, name: name});
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
    ga('send', 'event', '公告列表', '公告列表', '公告列表');
</script>