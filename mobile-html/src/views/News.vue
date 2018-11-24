<template>
    <div>
        <div style="position: fixed;width: 100%;">
            <div class="index-header information-title">
                <h1><span>爱车宝典</span> <img src="../../static/img/icon-yangche-bai.png" style="height: 3rem;margin-top: -0.5rem;"></h1>
            </div>
            <div style="padding: 0.5rem 1.5rem;background: white;">
                <input type="text" class="upkeep-seek-input" placeholder="搜索" v-model="page.keyWord">
                <a class="upkeep-seek-icon" v-on:click="keyWordSearch()"></a>
            </div>
        </div>
        <div class="news-content" style="padding-top: 12rem;padding-bottom: 1rem;">
            <div class="news-tag">
                <p>今日热搜</p>
                <span v-for="key in hotKeyWords" v-on:click="clickHotKey(key)">{{key}}</span>
            </div>
            <div class="news-content-list">
                <template v-if="infoItems.length>0">
                    <template v-for="item in infoItems">
                        <div class="news-item" v-on:click="viewDetail(item.sourceHref)">
                            <div>
                                <div class="fl" style="width: 30%;height:7rem;line-height: 7rem;">
                                    <img :src="item.iconUrl" alt="" style="width: 100%;">
                                </div>
                                <div class="fl" style="width: 70%;padding-left: 0.8rem;">
                                    <h4 v-if="item.highLightTitle" v-html="item.highLightTitle" class="news_title"></h4>
                                    <h4 v-else v-html="item.title" class="news_title"></h4>
                                    <p><span style="color:#a1a1a1;">{{item.createDate}}</span></p>
                                    <p style="font-size: 12px;" v-if="item.highLightAnswer_"
                                       v-html="item.highLightAnswer_"
                                       class="news_digest">
                                    </p>
                                    <p style="font-size: 12px;" v-else v-html="item.digest_" class="news_digest">
                                    </p>
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
    </div>

</template>
<style scoped>
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
        -webkit-line-clamp: 2;
    }

    .news_digest p {
        display: none;
    }
</style>
<script>
    import Http from '../utils/http';

    export default {
        data () {
            return {
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
            //查询资讯信息
            queryInfo: function () {
                new Http("/api/public/info/search", {page: this.page}, (data) => {
                    for (let i = 0; i < data.content.length; i++) {
                        let o = data.content[i];
                        let digest_ = o['digest'];
                        digest_ = digest_.replace(/<\/p>/gi, '').replace(/<p>/gi, "").replace(/<br>/gi, "");
                        o['digest_'] = digest_;

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
            viewDetail: function (href) {
                window.location.href = href;
            },
            //热门关键字
            queryHotKeyWord: function () {
                new Http("/api/public/info/hot", {}, (data) => {
                    this.hotKeyWords = data;
                })
            },
            clickHotKey: function (key) {
                this.page.keyWord = key;
                this.keyWordSearch();
            }

        },
        mounted () {
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
    ga('send', 'event', '爱车宝典', '爱车宝典', '爱车宝典');
</script>