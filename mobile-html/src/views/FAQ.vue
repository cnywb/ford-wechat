<template>
    <div>
        <div style="position: fixed;width: 100%;">
            <div class="index-header information-title">
                <h1><span>常见问题</span> <img src="../../static/img/fqa_bai.png" style="height: 2.11rem;"></h1>
            </div>
            <div style="padding: 0.5rem 1.5rem;background: white;">
                <input type="text" class="upkeep-seek-input" placeholder="搜索" v-model="page.keyWord">
                <a class="upkeep-seek-icon" v-on:click="keyWordSearch()"></a>
            </div>
        </div>
        <div class="news-content" style="background: #f4f4f3;padding-top: 12rem;padding-bottom:5rem;">
            <div class="news-tag">
                <p>今日热搜</p>
                <span v-for="key in hotKeyWords" v-on:click="clickHotKey(key)">{{key}}</span>
            </div>
            <div class="news-content-list">
                <template v-if="infoItems.length>0">
                    <template v-for="item in infoItems">
                        <div class="news-item" v-on:click="viewDetail(item)">

                            <div style="width: 100%;">
                                <h4 v-if="item.highLightQuestion" v-html="item.highLightQuestion"
                                    class="news_title"></h4>
                                <h4 v-else v-html="item.question" class="news_title"></h4>
                                <p><span style="color:#a1a1a1;">{{item.createDate}}</span></p>
                                <div style="font-size: 12px;" v-if="item.highLightAnswer_"
                                     v-html="item.highLightAnswer_"
                                     class="news_digest">
                                </div>
                                <div style="font-size: 12px;" v-else v-html="item.answer_" class="news_digest">
                                </div>
                            </div>
                            <div class="news-item-details">
                                <a>查看详情</a>
                            </div>
                        </div>
                    </template>
                    <div style="text-align: center" v-on:click="queryInfo()" v-if="!queryEnd">加载更多</div>
                    <div style="text-align: center" v-else="">已经没有更多数据了！</div>
                </template>

                <template v-else>
                    <div style="text-align: center">
                        <h4 v-if="!isLoading || queryEnd">非常抱歉，未找到相关内容！</h4>
                        <h4 v-else>努力加载中，请稍候!</h4>
                    </div>
                </template>
            </div>

        </div>

        <div class="information-item-content" v-if="showContent" v-cloak>
            <div class="index-news-header">
                <div class="fl padder-l">
                    <p style="font-size: 1.2em;">问题详情</p>
                </div>
                <img src="../../static/img/icon-close.png" alt="关闭" v-on:click="closeMsg()"
                     class="index-header-Unautherized-shade-close" style="margin: 10px;">
            </div>
            <div v-html="currentQuestion" style="margin-bottom: 1.6rem;font-weight: bold">

            </div>
            <div style="overflow-y: scroll;height:73%;color:#3e3b3b">
                <div v-html="currentAnswer">

                </div>
            </div>
            <div class="information-item-footer" style="width: 93%;">
                <button class="information-item-btn" v-on:click="closeMsg()">关闭</button>
            </div>
        </div>
        <div class="rescue-dial rescue-dial-checked" style="background: #2d96cd;position: fixed">
            <a href="tel:4008877766" style="color:white">
                <img src="../../static/img/service_bai.png" alt="" style="width: 20.6%;">
                <span><br>客服中心</span></a>
        </div>
    </div>
</template>
<style>
    /**
    标题只显示一行
    */
    .news_title {
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
    }

    /**
        多出行数自动隐藏
     */
    .news_digest {
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
    }

    .news-content-list {
        height: 66%;
    }

    .information-item-content {
        color: #2d96cd;
        height: 79%;
    }

    .information-item-footer {
        position: absolute;
        bottom: 3%;
    }
</style>
<script>
    import Http from '../utils/http';
    export default {
        data() {
            return {
                currentQuestion: "",
                currentAnswer: "",
                //显示详细内容
                showContent: false,
                infoItems: [],
                isLoading: true,
                queryEnd: false,//加载是否完成,
                page: {
                    keyWord: "",
                    pageNumber: 1,
                    pageSize: 5
                },
                hotKeyWords: []

            }
        },
        methods: {
            keyWordSearch: function () {
                this.infoItems = [];
                this.queryEnd = false;
                this.page.pageNumber = 1;
                this.queryInfo();
            },
            //查询问答信息
            queryInfo: function () {
                new Http("/api/public/qainfo/search", {page: this.page}, (data) => {
                    for (let i = 0; i < data.content.length; i++) {
                        let o = data.content[i];
                        let answer_ = o['answer'];
                        answer_ = answer_.replace(/<\/p>/gi, '').replace(/<p>/gi, "").replace(/<br>/gi, "");
                        o['answer_'] = answer_;

                        let highLightAnswer_ = o['highLightAnswer'] || "";
                        highLightAnswer_ = highLightAnswer_.replace(/<\/p>/gi, '').replace(/<p>/gi, "").replace(/<br>/gi, "");
                        console.log(highLightAnswer_);
                        o['highLightAnswer_'] = highLightAnswer_;
                        this.infoItems.push(o);
                    }
                    this.isLoading = true;
                    if (data.content.length < this.page.pageSize) {
                        this.queryEnd = true;
                    }
                    this.page.pageNumber++;
                })
            },
            /*
             查看文章详情
             */
            viewDetail: function (item) {
                this.currentQuestion = item.question;
                this.currentAnswer = item.answer;
                this.showContent = true;
            },
            //热门关键字
            queryHotKeyWord: function () {
                new Http("/api/public/qainfo/hot", {}, (data) => {
                    this.hotKeyWords = data;
                })
            },
            clickHotKey: function (key) {
                this.page.keyWord = key;
                this.keyWordSearch();
            },
            closeMsg: function () {
                this.showContent = !this.showContent;
            }

        },
        mounted() {
            this.queryInfo();
            this.queryHotKeyWord();
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
    ga('send', 'event', '互问互答', '互问互答', '互问互答');
</script>