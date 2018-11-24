<template>
    <div>
        <div class="index-header information-title" style="position: fixed;width: 100%;">
            <h1>长安福特原厂保养套餐 <img src="../../static/img/icon-baoyang-bai.png" style="height: 3rem;margin-top: -0.5rem;"></h1>
        </div>
        <div style="width: 100%; overflow-y: scroll; padding-bottom: 4rem;background: #f4f4f3;padding-top: 6rem">
            <img src="../../static/image/baoyang.jpg" alt="" style="width: 100%;">
        </div>
        <div class="upkeep-footer" v-on:click="searchCombo()">
            <img src="../../static/img/icon-chaxun.png" style="height: 1.1rem;margin-right: 0.5rem;">长安福特原厂-保养套餐查询
            <!--<input type="checkbox" class="upkeep-footer-checkbox"> TODO 暂未开放时注释掉 -->
            <img src="../../static/img/icon-more.png" class="fr" style="height: 1.6rem;margin-top: 1.2rem;">
        </div>
        <div class="shade" v-if="showSearch" style="position: fixed;">
            <div class="upkeep-model" v-if="searchPage == 'search'">
                <div class="index-news-header">
                    <div class="fl">
                        <img src="../../static/img/icon-bianji.png" style="height: 2.62rem;">
                    </div>
                    <div class="fl padder-l">
                        <p style="font-size: 16px;line-height: 36px;">信息输入</p>
                    </div>
                </div>
                <div>
                    <input type="text" class="upkeep-seek-input" placeholder="请输入vin码" v-model="vin">
                    <a class="upkeep-seek-icon"></a>
                </div>
                <div class="upkeep-seek-hint">
                    <p style="white-space: nowrap;">*请正确输入您的VIN码，以查询您的保养套餐使用情况;</p>
                    <p style="white-space: nowrap;">&nbsp;&nbsp;您购买的长安福特原厂保养套餐需满1月后，方可查询其使用情况。</p>
                </div>
                <div class="text-center padder">
                    <button class="upkeep-seek-btn mg-xxxl" v-on:click="searchResult">查询</button>
                    <button class="upkeep-seek-btn mg-xxxl" v-on:click="showSearchResult('help')">如何查询VIN码?</button>
                </div>
            </div>

            <div class="upkeep-model" v-if="searchPage == 'result'" style="overflow-y: scroll">
                <div class="text-center padder font-xl">
                    查询结果
                </div>
                <template v-for="result in resultList">
                    <table class="font-s text-center upkeep-table" style="margin-bottom: 10px;width: 100%;">
                        <tr>
                            <td class="border-right" style="width: 30%">车架号</td>
                            <td>{{result.vin}}</td>
                        </tr>
                        <tr>
                            <td class="border-right">套餐</td>
                            <td>已经在{{result.dealerName}}购买{{result.combo}}</td>
                        </tr>
                        <tr>
                            <td class="border-right">合同有效期</td>
                            <td>{{result.contractStartDate}}至{{result.contractEndDate}}</td>
                        </tr>
                        <tr>
                            <td class="border-right">套餐次数</td>
                            <td>可使用{{result.times}}次</td>
                        </tr>
                    </table>
                </template>
                <div class="text-center padder">
                    <img src="../../static/img/icon-back.png" alt="" class="padder-t" style="width: 32px;" v-on:click="showSearchResult('search')">
                </div>
            </div>


            <div class="upkeep-model" v-if="searchPage == 'noResult'">
                <div class="display-table">
                    <div class="display-table-cell">
                        <div class="text-center padder font-xl margin-t-xl">
                            查询结果
                        </div>
                        <div class="font-s text-center padder-b">
                            您尚未购买长安福特原厂保养套餐，请选择相应车型，<br>
                            点击按钮查询长安福特原厂保养套餐价格。
                        </div>
                        <div style="text-align: center;">
                            <select v-model="carModel" style="border: 1px solid #333;background: none;margin: 1rem 0;padding: 0.6rem 0.6rem;width: 80%;">
                                <template v-for="item in carModelData">
                                    <option v-bind:value="item">{{item}}</option>
                                </template>
                            </select>
                        </div>
                        <!--<input type="text" class="upkeep-seek-input" placeholder="全新福克斯">-->
                        <div class="text-center padder" style="width: 100%; padding: 0;">
                            <button class="upkeep-seek-btn mg-xxxl" v-on:click="showComboTable()">了解套餐详情</button>
                        </div>
                        <div class="text-center padder" style="width: 100%; padding: 0;">
                            <img src="../../static/img/icon-back.png" alt="" class="padder-t" style="width: 32px;" v-on:click="showSearchResult('search')">
                        </div>
                    </div>
                </div>
            </div>


            <div class="upkeep-model" v-if="searchPage == 'comboDetail'" style="overflow-y: scroll">
                <div class="text-center padder font-xl">
                    查询结果
                </div>
                <table class="font-s text-center upkeep-table" style="width: 100%;">
                    <tr>
                        <th>类型</th>
                        <th>名称</th>
                        <th>价格(RMB)</th>
                    </tr>
                    <template v-for="detail in comboDetailData">
                        <tr>
                            <td class="border-right">{{detail.type}}</td>
                            <td class="border-right">{{detail.name}}</td>
                            <td>{{detail.price}}</td>
                        </tr>
                    </template>
                </table>
                <div class="text-center padder">
                    <img src="../../static/img/icon-back.png" alt="" class="padder-t" style="width: 32px;" v-on:click="showSearchResult('noResult')">
                </div>
            </div>


            <div class="upkeep-model" v-if="searchPage == 'help'">
                <div class="index-news-header">
                    <div class="fl">
                        <img src="../../static/img/icon-vin-l.png" style="height: 2.62rem;">
                    </div>
                    <div class="fl padder-l">
                        <p style="font-size: 16px;line-height: 36px;">如何查询VIN码</p>
                    </div>
                </div>
                <div class="overflow-h">
                    <div class="fl" style="width: 50%; padding-right: 0.5rem">
                        1.使用机动车行驶证的方法查询，如图：
                        <img src="../../static/image/xingshizheng.jpg" alt="" class="width-whole">
                    </div>
                    <div class="fr" style="width: 50%;; padding-left: 0.5rem">
                        2.在车的前挡风玻璃左侧，如图：
                        <img src="../../static/image/chejiahao.jpg" alt="" class="width-whole">
                    </div>
                </div>
                <div class="text-center padder" style="width: 100%;  float: right;">
                    <img src="../../static/img/icon-back.png" alt="" class="padder-t" style="width: 32px;" v-on:click="showSearchResult('search')">
                </div>
            </div>
        </div>
        <div class="shade" v-if="flagOpen" style="position: fixed;">
            <div class="upkeep-model-open" v-if="flagOpen">
                <div class="overflow-h" style="color: rgb(45, 150, 205);padding-left: 3%; font-size: 16px">
                    该功能即将上线，敬请期待！
                </div>
                <div class="text-center">
                    <button class="upkeep-seek-btn mg-xxxl" v-on:click="closeFlagOpen">确认</button>
                </div>
            </div>
        </div>
    </div>
</template>
<style>

    .upkeep-model-open {
        position: absolute;
        left: 10%;
        right: 10%;
        top: 40%;
        width: 80%;
        border-radius: 10px;
        color: #1b394e;
        background: #ffffff;
        padding: 5%;
        height: 100px;
        text-align: center;
    }

    .upkeep-content img {
        width: 100%;
        padding: 0.5rem;
    }

    .upkeep-footer {
        width: 100%;
        height: 4rem;
        line-height: 4rem;
        background: #1b394e;
        position: fixed;
        bottom: 0;
        padding: 0 6%;
        font-size: 18px;
        z-index: 10000;
    }

    .upkeep-footer-checkbox {
        position: absolute;
        z-index: 1;
        width: 100%;
        height: 100%;
        left: 0;
        opacity: 0;
    }

    .upkeep-footer-checkbox:checked + img {
        transform: rotate(180deg);
        -ms-transform: rotate(180deg); /* IE 9 */
        -moz-transform: rotate(180deg); /* Firefox */
        -webkit-transform: rotate(180deg); /* Safari 和 Chrome */
        -o-transform: rotate(180deg);
    }

    .upkeep-model {
        position: fixed;
        bottom: 4rem;
        width: 100%;
        color: #1b394e;
        background: #ffffff;
        padding: 0.5rem 1.5rem 1rem 1.5rem;
        height: 290px;
    }

    .upkeep-seek-input {
        border: 2px solid #bcd9dd;
        width: 100%;
        margin: 0.5rem 0;
        height: 2.4rem;
        border-radius: 1.2rem;
        padding: 0 1rem;
    }

    .upkeep-seek-input::-webkit-input-placeholder {
        color: #c6c6c6;
    }

    .upkeep-seek-input::-moz-placeholder {
        color: #c6c6c6;
    }

    .upkeep-seek-input:-ms-input-placeholder {
        color: #c6c6c6;
    }

    .upkeep-seek-icon {
        background: url(../../static/img/icon-sousuo.png) 0 0 no-repeat;
        background-size: 100% 100%;
        height: 1.3rem;
        width: 1.3rem;
        float: right;
        position: relative;
        top: -2.4rem;
        right: 0.8rem;
    }

    .upkeep-seek-hint {
        font-size: 10px;
        transform: scale(0.9);
        -ms-transform: scale(0.9);
        -moz-transform: scale(0.9);
        -webkit-transform: scale(0.9);
        -o-transform: scale(0.9);
        margin: 0 -6%;
    }

    .upkeep-seek-btn {
        background: #2d96cd;
        width: 45%;
        color: #fff;
        padding: 0.2rem 0;
        font-size: 16px;
        border-radius: 2rem;
    }

    .upkeep-seek-a {
        font-size: 10px;
        transform: scale(0.9);
        -ms-transform: scale(0.9);
        -moz-transform: scale(0.9);
        -webkit-transform: scale(0.9);
        -o-transform: scale(0.9);
        text-decoration: underline;
        color: #1b394e;
    }

    @media (max-width: 330px) {
        .upkeep-seek-hint {
            font-size: 10px;
            transform: scale(0.8);
            -ms-transform: scale(0.8);
            -moz-transform: scale(0.8);
            -webkit-transform: scale(0.8);
            -o-transform: scale(0.8);
            margin: 0 -12%;
        }
    }

    .upkeep-table {
        border: 1px solid #1b394e;
        border-spacing: 0;
        border-collapse: collapse;
        border-right: 0;
        border-left: 0;
    }

    .upkeep-table td {
        border: 1px solid #1b394e;
        border-right: 0;
        border-left: 0;
        padding: 10px;
    }

    .border-right {
        border-right: 1px solid #333 !important;
    }

    .font-s {
        font-size: 10px;
    }

    .padder {
        padding: 1rem;
    }

    .font-xl {
        font-size: 15px;
    }

    .text-center {
        text-align: center;
    }
</style>
<script>
    import Http from '../utils/http';
    import {Toast} from 'mint-ui';
    export default {
        data() {
            return {
                dict: {
                    "新基础保养套餐 24个月5次 第二次购买": 5,
                    "长安福特基础保养套餐 32个月7次 第二次购买": 7,
                    "长安福特基础保养套餐 24个月5次 第二次购买": 5,
                    "新基础保养套餐 32个月7次 第二次购买": 7,
                    "新高级保养套餐 16个月3次 第二次购买": 3,
                    "新基础保养套餐 24个月5次 第一次购买": 5,
                    "新高级保养套餐 16个月3次 第一次购买": 3,
                    "长安福特高级保养套餐 24个月5次 第二次购买": 5,
                    "长安福特高级保养套餐 16个月3次": 3,
                    "新基础保养套餐 16个月3次 第二次购买": 3,
                    "新基础保养套餐 32个月7次 第一次购买": 7,
                    "长安福特基础保养套餐 16个月3次": 3,
                    "新高级保养套餐 24个月5次 第二次购买": 5,
                    "长安福特基础保养套餐 32个月7次": 7,
                    "基础保养套餐 赠送套餐 第一次购买": 5,
                    "新基础保养套餐 16个月3次 第一次购买": 3,
                    "长安福特高级保养套餐 32个月7次": 7,
                    "长安福特高级保养套餐 32个月7次 第二次购买": 7,
                    "长安福特基础保养套餐 16个月3次 第二次购买": 3,
                    "长安福特高级保养套餐 16个月3次 第二次购买": 3,
                    "高级保养套餐 赠送套餐 买7赠2 40个月": 9,
                    "高级保养套餐 赠送套餐 第一次购买": 5,
                    "基础保养套餐 赠送套餐 买4赠1 24个月": 5,
                    "基础保养套餐 赠送套餐 买7赠2 40个月": 9,
                    "长安福特高级保养套餐 24个月5次": 5,
                    "长安福特基础保养套餐 24个月5次": 5,
                    "新高级保养套餐 24个月5次 第一次购买": 5,
                    "新高级保养套餐 32个月7次 第一次购买": 7,
                    "高级保养套餐 赠送套餐 买4赠1 24个月": 5,
                    "新高级保养套餐 32个月7次 第二次购买": 7
                },
                allModelList: [],//所有SSP车型列表数据
                carModelData: [],//根据Vin从所有车型中过滤出相对应的车型列表。
                vin: "",
                flagOpen: false,
                showSearch: false,
                searchPage: null,
                carModel: null,
                resultList: [{
                    vin: "",
                    dealerName: "",
                    combo: "",
                    contractStartDate: "",
                    contractEndDate: "",
                    times: "",
                }],
                comboDetailData: [],//套餐明细数据

                //车型选择源
                modelCacheData: [
                    {"name": "ABF", "value": "嘉年华"},
                    {"name": "FNN", "value": "新嘉年华1.0T"},
                    {"name": "FLN", "value": "新嘉年华1.5L"},
                    {"name": "KNF", "value": "翼搏1.0T"},
                    {"name": "KLF", "value": "翼搏1.5L"},
                    {"name": "CEF", "value": "经典福克斯"},
                    {"name": "CBE", "value": "2012款新福克斯1.6L"},
                    {"name": "CCE", "value": "2012款新福克斯2.0L"},
                    {"name": "CNE", "value": "2015款全新福克斯1.0T"},
                    {"name": "CLE", "value": "2015款全新福克斯1.5T"},
                    {"name": "CBE", "value": "2015款全新福克斯1.6L"},
                    {"name": "FLS", "value": "福睿斯"},
                    {"name": "JLE", "value": "2013款翼虎1.5T"},
                    {"name": "JBE", "value": "2013款翼虎1.6T"},
                    {"name": "JCE", "value": "2013款翼虎2.0L"},
                    {"name": "JLE", "value": "2016款全新翼虎1.5T"},
                    {"name": "JCE", "value": "2016款全新翼虎2.0L"},
                    {"name": "FFF", "value": "麦柯斯"},
                    {"name": "BFF", "value": "蒙迪欧-致胜2.3L"},
                    {"name": "BCF", "value": "蒙迪欧Gtdi"},
                    {"name": "FLF", "value": "2013款新蒙迪欧1.5T"},
                    {"name": "FCF", "value": "2013款新蒙迪欧2.0L"},
                    {"name": "FLF", "value": "2016款全新蒙迪欧1.5T"},
                    {"name": "FCF", "value": "2016款全新蒙迪欧2.0L"},
                    {"name": "FCH", "value": "锐界2.0L"},
                    {"name": "FHH", "value": "锐界2.7L"},
                    {"name": "MLH", "value": "金牛座1.5T"},
                    {"name": "MCH", "value": "金牛座2.0L"},
                    {"name": "MHH", "value": "金牛座2.7L"}],
                //车型选择源
                modelCacheSpecialData: [
                    {"name": "AF", "value": "嘉年华"},
                    {"name": "CF", "value": "经典福克斯"},
                    {"name": "FS", "value": "福睿斯"},
                    {"name": "FF", "value": "麦柯斯"}]
            }
        },
        methods: {
            initAllModel: function () {
                new Http("/api/private/combo/upkeep/model", {}, (data) => {
                    this.allModelList = data;
                });
            },
            searchCombo: function () { //计划查询弹出框

                // TODO 暂停开放
                this.flagOpen = true;
                /*this.showSearch = !this.showSearch;
                 if (this.showSearch) {
                 this.searchPage = "search";
                 } else {
                 this.searchPage = null;
                 }*/
            },
            closeFlagOpen: function () {
                this.flagOpen = false;
            },
            searchResult: function () {
                let vin = this.vin;
                if (!vin) {
                    Toast('请输入VIN码');
                    return;
                }

                if (!this.validateVin(vin)) {
                    Toast('VIN码格式不正确');
                    return;
                }
                new Http("/api/private/combo/upkeep", {vin: vin}, (data) => {
                    if (data.length > 0) {
                        this.resultList = data;
                        let this_ = this;
                        this.resultList.forEach(function (item) {
                            item['combo'] = getProductName(item['product']);
                            item['times'] = this_.dict[item['product']];

                        });
                        this.searchPage = "result";
                    } else {
                        this.filterModel(vin);
                        this.carModel = this.carModelData[0];
                        this.searchPage = "noResult";
                    }
                });

                function getProductName(product) {
                    var index = product.indexOf(" ");
                    return product.substring(0, index);
                }
            },
            showSearchResult: function (step) {
                this.searchPage = step;
            },
            showComboTable: function () {
                if (!this.carModel) {
                    Toast("请选择车型!");
                    return;
                }
                new Http("/api/private/combo/list", {model: this.carModel, packageType: "SSP"}, (data) => {

                    data.forEach(function (item) {
                        item['price'] = item['price'].replace('.0000');
                    });

                    this.comboDetailData = data;
                    this.searchPage = "comboDetail";
                });
            },
            validateVin: function (vin) {//校验VIN是否正确
                if (vin.length != 17) {
                    return false;
                }
                var prefix = vin.substring(0, 3).toUpperCase();
                if (prefix != "LVS" && prefix != "LVR") {
                    return false;
                }

                var endstr = vin.substring(11, 17);
                var reg1 = /^\d+$/;
                var reg2 = /^[A-Za-z]+$/;
                if (!endstr.match(reg1)) {
                    return false;
                    ``
                }
                var middlestr = vin.substring(10, 11);
                if (!middlestr.match(reg2)) {
                    return false;
                }
                return true;
            },
            filterModel: function (vin) {//根据VIN过滤其对应所有的车型数据
                var filterModelList = [];
                let carModel = this.getModelFromVin(vin);
                this.allModelList.forEach(function (item) {
                    if (item.indexOf(carModel) != -1) {
                        filterModelList.push(item);
                    }
                });
                this.carModelData = filterModelList;
                if (filterModelList.length > 0) {
                    this.carModel = filterModelList[0];
                }
            },
            getModelFromVin: function (vin) {//根据VIN规则获取车型名称。
                vin = vin.toUpperCase();
                var vin5 = vin.substring(4, 5);
                var vin11 = vin.substring(10, 11);
                var vin8 = vin.substring(7, 8);

                let key = vin5 + vin8 + vin11;
                this.modelCacheData.forEach((item) => {
                    if (item.name == key) {
                        return item.value;
                    }
                });

                this.modelCacheSpecialData.forEach((item) => {
                    if (item.name == key) {
                        return item.value;
                    }
                });
                /*
                 if (vin5 == "F" && vin11 == "N") {
                 return "嘉年华";
                 }
                 if (vin5 == "K" && vin11 == "F") {
                 return "翼博";
                 }
                 if (vin5 == "C" && vin11 == "F") {
                 return "福克斯";
                 }
                 if (vin5 == "C" && vin11 == "E") {
                 return "新福克斯";
                 }
                 if (vin5 == "F" && (vin11 == "E" || vin11 == "S")) {
                 return "福睿斯";
                 }
                 if (vin5 == "B" && vin11 == "F") {
                 return "致胜";
                 }
                 if (vin5 == "F" && vin11 == "H") {
                 return "锐界";
                 }
                 if (vin5 == "J" && vin11 == "E") {
                 return "翼虎";
                 }
                 if (vin5 == "F" && vin11 == "F" && (vin8 == "C" || vin8 == "L")) {
                 return "新蒙迪欧";
                 }
                 if (vin5 == "F" && vin11 == "F" && vin8 == "F") {
                 return "麦柯斯";
                 }
                 if (vin5 == "M" && vin11 == "H") {
                 return "金牛座";
                 }*/
                return "";
            }
        },
        mounted() {
            let vin = window.sessionStorage.getItem("vin");
            this.vin = vin || "";
            this.initAllModel();//加载所有SSP的车型。
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
    ga('send', 'event', '保养套餐', '保养套餐', '保养套餐');
</script>