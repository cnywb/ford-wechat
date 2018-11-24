<template>

    <div>
        <div class="index-header information-title" style="position: fixed;width: 100%;">
            <h1>长安福特原厂延长质保 <img src="../../static/img/icon-zhibao-bai.png" style="height: 2.8rem;margin-top: -0.5rem;"></h1>
        </div>
        <div style="width: 100%; overflow-y: scroll; padding-bottom: 4rem;background: #f4f4f3;padding-top: 6rem">
            <img src="../../static/image/quality.jpg" alt="" style="width: 100%;">
        </div>
        <div class="upkeep-footer" v-on:click="searchCombo()">
            <img src="../../static/img/icon-chaxun.png" style="height: 1.1rem;margin-right: 0.5rem;">长安福特原厂-延长质保查询
            <!--<input type="checkbox" class="upkeep-footer-checkbox"> TODO 暂未开放时注释掉-->
            <img src="../../static/img/icon-more.png" class="fr" style="height: 1.2rem;margin-top: 1.4rem;">
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
                <template v-for="quality in buyPlanList">
                    <table class="font-s text-center upkeep-table" style="width: 100%">
                        <tr>
                            <td class="border-right" style="width: 30%;">车架号</td>
                            <td>{{quality.vin}}的福特{{quality.model}}</td>
                        </tr>
                        <tr>
                            <td class="border-right">套餐</td>
                            <td>已经在 北京{{quality.dealerName}}购买 {{quality.product}}方案</td>
                        </tr>
                    </table>
                </template>
                <div class="text-center padder-t" style="width:100%;">
                    <p class="font-s" style=" margin: 10px;">
                        输入购车日期，已行驶里程数以及<br>
                        月平均行驶里程数，查询您延长质保方案使用情况
                    </p>
                    <p class="quality-xinshi">
                        <span style="width: 19%;" class="b-t-l-r b-b-l-r">
                                    <select v-model="planQuery.year" style="text-align: center">
                                        <template v-for="item in yearData">
                                            <option v-bind:value="item">{{item}}年</option>
                                        </template>
                                    </select>
                                </span>
                        <span style="width: 14%;">
                                    <select v-model="planQuery.month" style="text-align: center">
                                        <template v-for="item in monthData">
                                            <option v-bind:value="item">{{item}}月</option>
                                        </template>
                                    </select>
                                </span>
                        <span style="width: 29%;"><input type="number" placeholder="已行驶里程" style="width: 80%;" v-model="planQuery.travelledDistance"></span>
                        <span style="width: 34%;" class="b-t-r-r b-b-r-r"><input type="number" placeholder="月平均行驶里程" style="width: 80%;" v-model="planQuery.monthDistance"></span>
                    </p>
                </div>
                <div class="text-center padder" style="padding-top: 0;padding-bottom: 0;">
                    <button class="upkeep-seek-btn mg-xxxl" v-on:click="searchUseResultPlan">使用情况</button>
                </div>
                <div class="text-center padder" style="width: 100%;  float: right; padding-top: 0;">
                    <img src="../../static/img/icon-back.png" alt="" class="padder-t" style="width: 32px;" v-on:click="showSearchResult('search')">
                </div>
            </div>

            <div class="upkeep-model" v-if="searchPage == 'effect'">
                <div class="display-table">
                    <div class="display-table-cell">
                        <div class="text-center padder font-xl margin-t-xl">
                            延长质保生效信息<br>
                            此方案仅作参考，请咨询当地长安福特授权经销商。
                        </div>
                        <div class="font-s text-center">
                            {{planUseResult}}
                        </div>
                        <div class="text-center padder" style="width: 100%;  float: right;">
                            <img src="../../static/img/icon-back.png" alt="" class="padder-t" style="width: 32px;" v-on:click="showSearchResult('result')">
                        </div>
                    </div>
                </div>
            </div>

            <div class="upkeep-model" v-if="searchPage == 'noResult'">
                <div class="display-table">
                    <div class="display-table-cell">
                        <div class="text-center padder font-xl margin-t-xl">
                            查询结果
                        </div>
                        <div class="font-s text-center" style="margin-bottom: 10px">
                            您尚未购买长安福特原厂延长质保，输入购车日期，<br>
                            已行驶里程数，为您推荐延长质保方案
                        </div>
                        <div class="text-center padder-t">
                            <p class="quality-xinshi">
                                <span style="width: 29%;" class="b-t-l-r b-b-l-r">
                                    <select v-model="planQuery.year" style="text-align: center">
                                        <template v-for="item in yearData">
                                            <option v-bind:value="item">{{item}}年</option>
                                        </template>
                                    </select>
                                </span>
                                <span style="width: 19%;">
                                    <select v-model="planQuery.month" style="text-align: center">
                                        <template v-for="item in monthData">
                                            <option v-bind:value="item">{{item}}月</option>
                                        </template>
                                    </select>
                                </span>
                                <span style="width: 49%;" class="b-t-r-r b-b-r-r"><input type="number" placeholder="已行驶里程" style="width: 80%;" v-model="planQuery.travelledDistance"></span>
                            </p>
                        </div>
                        <div class="text-center padder" style="padding-top: 0px;padding-bottom: 0px">
                            <button class="upkeep-seek-btn mg-xxxl" v-on:click="searchNoResultPlan">延保方案</button>
                        </div>
                        <div class="text-center padder" style="width: 100%;  float: right; padding-top: 0;">
                            <img src="../../static/img/icon-back.png" alt="" class="padder-t" style="width: 32px;" v-on:click="showSearchResult('search')">
                        </div>
                    </div>
                </div>
            </div>

            <div class="upkeep-model" v-if="searchPage == 'planTable'" style="overflow-y: scroll;">
                <div class="text-center padder font-xl">
                    您的车型为{{carModel}}的车辆可购买以下延保方案
                </div>
                <div v-if="planData.length == 0" style="width: 100%;text-align: center;color: red;">无可购买延保方案</div>
                <div v-else>
                    <table class="font-s text-center upkeep-table" style="width: 100%">
                        <tr>
                            <th>方案</th>
                            <th>名称</th>
                            <th>价格(RMB)</th>
                        </tr>

                        <template v-for="detail in planData">
                            <tr>
                                <td class="border-right">{{detail.type}}</td>
                                <td class="border-right">{{detail.name}}</td>
                                <td>{{detail.price}}</td>
                            </tr>
                        </template>
                    </table>
                </div>
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
                    <div class="fl" style="width: 50%;padding-right: 0.5rem">
                        1.使用机动车行驶证的方法查询，如图：
                        <img src="../../static/image/xingshizheng.jpg" alt="" class="width-whole">
                    </div>
                    <div class="fr" style="width: 50%;padding-left: 0.5rem">
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
                <div class="overflow-h" style="color: rgb(45, 150, 205);padding-left: 3%;font-size: 16px">
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
        z-index: 100;
        bottom: 4rem;
        width: 100%;
        color: #1b394e;
        background: #ffffff;
        padding: 0.5rem 1.5rem 1rem 1.5rem;
        height: 290px;
    }

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

    .b-b-l-r {
        border-bottom-left-radius: 15px;
    }

    .b-b-r-r {
        border-bottom-right-radius: 15px;
    }

    .b-t-l-r {
        border-top-left-radius: 15px;
    }

    .b-t-r-r {
        border-top-right-radius: 15px;
    }

    .quality-xinshi {
        overflow: hidden;
    }

    .quality-xinshi span {
        display: inline-block;
        height: 30px;
        line-height: 28px;
        border: 1px solid #91c1c7;
        text-align: center;
        float: left;
        margin-left: 1%;
    }

    .quality-xinshi span input {
        border: none;
        height: 24px;
        width: 50%;
        position: relative;
        top: -2px;
        text-align: center;
        margin-left: 5%;
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
        data () {
            return {
                vin: "",
                flagOpen: false,
                pickerOpen: false,
                carModel: "",
                showSearch: false,
                searchPage: null,
                yearData: ['2017', '2016', '2015', '2014', '2013'],
                monthData: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
                planQuery: {
                    year: "",
                    month: "",
                    travelledDistance: "",
                    monthDistance: ""
                },//Search的页面
                buyPlanList: [{
                    vin: "",
                    model: "",
                    dealerName: "",
                    product: ""
                }],//已购买的方案数据
                planUseResult: "",//延保使用情况
                planData: [],//方案数据
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
            closeFlagOpen: function () { //计划查询弹出框
                this.flagOpen = false;
            },
            searchResult: function () {
                var vin = this.vin;
                if (!vin) {
                    Toast('请输入VIN码');
                    return;
                }
                if (!this.validateVin(vin) && !this.getModelFromVin(vin)) {
                    Toast('VIN码格式不正确');
                    return;
                }
                new Http("/api/private/combo/quality", {vin: vin}, (data) => {
                    this.buyPlanList = [];
                    if (data.length > 0) {
                        let willEffectData = [];
                        data.forEach(function (item) {
                            if (item['fileName'] == '2015-55') {
                                willEffectData.push(item);
                            }
                        });
                        this.buyPlanList = willEffectData;
                        this.searchPage = "result";
                    }

                    if (!this.buyPlanList || this.buyPlanList.length == 0) {
                        this.searchPage = "noResult";
                    }
                });
            },
            /**
             * 查询存在延保的使用情况
             */
            searchUseResultPlan: function () {
                let planQuery = this.planQuery;
                let buyMonths = this.getMonthAmount(planQuery.year, planQuery.month);//计算购买月数
                let travelledDistance = planQuery.travelledDistance;
                let monthDistance = planQuery.monthDistance;
                let packageOrder = this.buyPlanList[0];
                if (!travelledDistance || travelledDistance <= 0) {
                    Toast('已行驶里程必须为大于0的整数!');
                    return;
                }
                if (!monthDistance || monthDistance <= 0) {
                    Toast('月平均行驶里程必须为大于0的整数!');
                    return;
                }
                this.planUseResult = this.getEffectiveInfo(buyMonths, packageOrder['termMonths'], packageOrder['termOdometer'], travelledDistance, monthDistance, packageOrder['contractStartDate'], packageOrder['contractEndDate']);//获取使用记录
                this.searchPage = "effect";
            },
            searchNoResultPlan: function () {
                let year = this.planQuery.year;
                let month = this.planQuery.month;
                let travelledDistance = this.planQuery.travelledDistance;
                if (!travelledDistance || travelledDistance <= 0) {
                    Toast('已行驶里程必须为大于0的整数!');
                    return;
                }

                var buyMonths = this.getMonthAmount(year, month);
                var buyChance = null;
                if (buyMonths < 6) {
                    if (travelledDistance < 5000) {
                        buyChance = "新购车";
                    }
                } else if (buyMonths >= 6 && buyMonths < 30) {
                    if (travelledDistance >= 5000 && travelledDistance < 80000) {
                        buyChance = "在保车";
                    }
                }

                if (!buyChance) {
                    Toast("暂无合适的延保产品供您选择!");
                    return;
                }
                this.queryPackage(buyChance);
            },
            /**
             * 得到月份数
             */
            getMonthAmount: function (yearStr, monthStr) {
                let endDate = new Date();
                let startDate = yearStr + "-" + monthStr + "-" + "1";
                startDate = new Date(startDate.replace(/-/g, '/'));
                let num = 0;
                let year = endDate.getFullYear() - startDate.getFullYear();
                num += year * 12;
                let month = endDate.getMonth() - startDate.getMonth();
                num += month;
                let day = endDate.getDate() - startDate.getDate();
                if (day > 0) {
                    num += 1;
                }
                return num;
            },
            /**
             * 计算生效日期
             * @param buyMonths  购车月份
             * @param hasOdometer 已行驶里程
             * @param termMonths  延保月份
             * @param termOdometer 延保里程
             * @param monthAverageOdometer 月平均行驶公里数
             */
            getEffectiveInfo: function (buyMonths, termMonths, termOdometer, hasOdometer, monthAverageOdometer, contractStartDate, contractEndDate) {
                hasOdometer = hasOdometer * 1;
                monthAverageOdometer = monthAverageOdometer * 1;
                if ((48 - buyMonths) <= 0) {
                    return "根据您的驾驶习惯，您购买的延保将由于超过覆盖的公里数而失效";
                }
                if (termMonths == 12 && termOdometer == 100000) {
                    var result = hasOdometer + (48 - buyMonths) * monthAverageOdometer;
                    result = result * 1;
                    if (result > 0 && result < 100000) {
                        return "根据您的驾驶习惯，您购买的延保将在" + contractStartDate + "至" + contractEndDate + "生效";
                    } else {
                        return "根据您的驾驶习惯，您购买的延保将由于超过覆盖的公里数而失效";
                    }
                }
                if (termMonths == 24 && termOdometer == 100000) {
                    var result = hasOdometer + (60 - buyMonths) * monthAverageOdometer;
                    result = result * 1;
                    if (result > 0 && result < 100000) {
                        return "根据您的驾驶习惯，您购买的延保将在" + contractStartDate + "至" + contractEndDate + "生效";
                    } else {
                        return "根据您的驾驶习惯，您购买的延保将由于超过覆盖的公里数而失效";
                    }
                }
                if (termMonths == 12 && termOdometer == 20000) {
                    var result = hasOdometer + (48 - buyMonths) * monthAverageOdometer;
                    result = result * 1;

                    if (result > 0 && result <= 120000) {
                        return "根据您的驾驶习惯，您购买的延保将在" + contractStartDate + "至" + contractEndDate + "生效";
                    } else {
                        return "根据您的驾驶习惯，您购买的延保将由于超过覆盖的公里数而失效";
                    }
                }
                if (termMonths == 24 && termOdometer == 40000) {
                    var result = hasOdometer + (60 - buyMonths) * monthAverageOdometer;
                    result = result * 1;
                    if (result > 0 && result < 140000) {
                        return "根据您的驾驶习惯，您购买的延保将在" + contractStartDate + "至" + contractEndDate + "生效";
                    } else {
                        return "根据您的驾驶习惯，您购买的延保将由于超过覆盖的公里数而失效";
                    }
                }
            },
            showSearchResult: function (step) {
                this.searchPage = step;
            },
            validateVin: function (vin) {
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
                }
                var middlestr = vin.substring(10, 11);
                if (!middlestr.match(reg2)) {
                    return false;
                }
                return true;
            },
            queryPackage: function (buyChance) {
                var vin = this.vin;
                let carModel = this.getModelFromVin(vin);
                this.carModel = carModel;
                new Http("/api/private/combo/list", {model: carModel, packageType: "EW"}, (data) => {
                    var chancePlans = [];
                    data.forEach(function (item) {
                        if (item['buyChance'] == buyChance) {
                            chancePlans.push(item);
                        }
                        item['price'] = item['price'].replace('.0000');
                    });
                    this.planData = data;
                    this.searchPage = "planTable";
                });
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
                /*if (vin5 == "F" && vin11 == "N") {
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
        mounted () {
            let vin = window.sessionStorage.getItem("vin");
            this.vin = vin || "";
            this.planQuery.year = new Date().getFullYear();
            this.planQuery.month = new Date().getMonth() + 1;
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
    ga('send', 'event', '原厂质保', '原厂质保', '原厂质保');
</script>