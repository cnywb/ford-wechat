<style scoped>
    @import "../resources/css/complain/style.css";
    @import '../resources/css/complain/base.css';
    @import '../resources/css/complain/flex.css';

    .customer {
        background: transparent !important;
        text-align: center;
        border: 0;
        width: 82%;
    }

    .buttonBg {
        background: linear-gradient(to right, #3cc4c2, #2d97ce);
        margin-top: 1.0rem;
    }
</style>
<template>
    <div class="flex-v container">
        <!-- 1.header begin-->
        <div class="flex-v header">
            <div class="flex-h flex-v-center title">
                <h1 class="flex-1">投诉建议</h1>
                <img src="../../static/img/icon-complain.png">
            </div>
            <div class="bottom1 flex-v-end"></div>
        </div>
        <!-- 1.header end-->
        <div class="index-header-Unautherized-shade" v-cloak v-if="showSubmit">
            <div class="index-header-Unautherized-shade-triangle"></div>
            <div class="display-table">
                <div class="display-table-cell">
                    <h1>温馨提示</h1>
                    <p style="padding: 1rem 0;">您好，您的投诉建议已成功提交，会由专人马上处理。<br/>请您保持手机畅通，我们会在三个工作日内与您取得联系。<br/>感谢您的理解和耐心等待！</p>
                    <button class="buttonBg autherized" v-on:click="goHome" style="width: 30%">确定</button>
                </div>
            </div>
        </div>
        <!--2.content begin-->
        <div class="flex-v flex-1 content">
            <div class="flex-v flex-1 warp white">
                <div class="scroll-v">
                    <div class="flex-v flex-1 white">
                        <div class="flex-1 flex-v">
                            <div class="flex-v padder-h-xxl">
                                <h4 class="padder-b divider" v-if="isAuth" style="color:#b5b5b5">投诉人信息</h4>
                                <h4 class="padder-b divider" v-else>即刻成为<span v-on:click="goAuth" style="color: #2d96cd;border-bottom: 0.1rem solid #2d96cd">认证车主</span>，可以帮助您更及时地反馈问题。</h4>
                                <div class="flex-h flex-v-center padder-h-xl divider row">
                                    <div class="problem-label"><span class="font-red">*</span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</div>
                                    <input style="text-transform: uppercase;" class="describe-input flex-1" v-model="formData.customerName" placeholder="请输入姓名">
                                </div>
                                <div class="flex-h flex-v-center padder-h-xl divider row">
                                    <div class="problem-label"><span class="font-red">*</span>联系电话</div>
                                    <input style="text-transform: uppercase;" class="describe-input flex-1" type="number" v-model="formData.customerMobile" placeholder="请输入联系电话">
                                </div>
                                <div class="flex-h flex-v-center padder-h-xl divider row">
                                    <div class="problem-label"><span v-if="isAuth" class="font-red">*</span><span v-else>&nbsp;</span>VIN&nbsp;&nbsp;&nbsp;&nbsp;码</div>
                                    <input style="text-transform: uppercase;" class="describe-input flex-1" v-model="formData.customerVin" placeholder="请输入VIN码" v-if="!isAuth">
                                    <select class="b-b customer" v-model="formData.customerVin" style="font-size: 12px" v-else>
                                        <option value="" style="text-align: center">请选择</option>
                                        <template v-for="item in vinList">
                                            <option v-bind:value="item" style="text-align: center">{{item}}</option>
                                        </template>
                                    </select>
                                    <img src="../../static/img/icon-xiala.png" style="height: 10px" v-if="isAuth">
                                </div>
                                <div class="flex-h flex-v-center padder-h-xl divider row">
                                    <div class="problem-label"><span class="font-red">*</span>车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型</div>
                                    <select class="b-b customer" v-model="formData.carModel" style="font-size: 12px">
                                        <option value="" style="text-align: center">请选择</option>
                                        <template v-for="item in carModelList">
                                            <option v-bind:value="item" style="text-align: center">{{item}}</option>
                                        </template>
                                    </select>
                                    <img src="../../static/img/icon-xiala.png" style="height: 10px">
                                </div>
                                <div class="flex-h flex-v-center padder-h-xl divider row">
                                    <div class="problem-label"><span v-if="isAuth" class="font-red">*</span><span v-else>&nbsp;</span>车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌</div>
                                    <input style="text-transform: uppercase;" class="describe-input flex-1" v-model="formData.license" placeholder="请输入车牌号码">
                                </div>
                                <div class="flex-h flex-v-center padder-h-xl divider row">
                                    <div class="problem-label"><span v-if="isAuth" class="font-red">*</span><span v-else>&nbsp;</span>行驶里程</div>
                                    <input style="text-transform: uppercase;" class="describe-input flex-1" v-model="formData.exerciseMileage" type="number" placeholder="请输入行驶里程">
                                </div>
                                <div class="flex-h flex-v-center padder-h-xl divider row">
                                    <div class="problem-label"><span class="font-red">*</span>投诉类型</div>
                                    <select class="b-b customer" v-model="formData.complainReason" style="font-size: 12px">
                                        <option value="" style="text-align: center">请选择</option>
                                        <template v-for="item in complainReasons">
                                            <option v-bind:value="item.value" style="text-align: center">{{item.name}}</option>
                                        </template>
                                    </select>
                                    <img src="../../static/img/icon-xiala.png" style="height: 10px">
                                </div>
                                <div class="flex-h flex-v-center padder-h-xl divider row" v-on:click="selectDealerShow()">
                                    <div class="problem-label"><span class="font-red">*</span>投诉经销商</div>
                                    <input class="describe-input flex-1" v-model="formData.involveDealer">
                                    <img src="../../static/img/icon-xiala.png" style="height: 10px">
                                </div>
                                <div class="flex-h flex-v-center problem-desc-row">
                                    <div class="flex-v flex-1">
                                        <div class="problem-count-title">
                                            <div style="float: left;padding-left: 3%;"><span class="font-red">*</span>投诉问题</div>
                                            <div class="font-right" style="padding-right: 2%;"> {{count}}/200</div>
                                        </div>
                                        <textarea maxlength="200" v-model="formData.incidentDesc" rows="5" class="describe-textarea" placeholder="为了更快速的帮助到您，请详细描述您的疑问，我们会马上受理。"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div align="center">
                            <input type="button" class="btn btn-primary mg-xl width-80" @click="submitFormData()" value="提交"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="shade" v-cloak style="position: absolute; z-index: 10;" v-if="selectAddressShowFlag">
            <div class="upkeep-city-model">
                <div class="index-news-header">
                    <div class="fl">
                        <img src="../../static/img/icon-vin-l.png" style="height: 2.62rem;">
                    </div>
                    <div class="fl padder-l">
                        <p style="font-size: 16px;line-height: 36px;">请选择所在地区</p>
                    </div>
                </div>
                <div class="overflow-h" style="height: 15rem">
                    <v-distpicker type="mobile" v-on:selected="onAddressSelected"></v-distpicker>
                </div>
                <div class="text-center padder">
                    <button class="upkeep-seek-btn mg-xxxl" v-on:click="confirmAddressSelected">确认</button>
                </div>
            </div>
        </div>

        <div class="shade" style="position: absolute; z-index: 10;" v-if="selectDealerShowFlag">
            <div class="upkeep-city-model">
                <div class="index-news-header">
                    <div class="fl">
                        <img src="../../static/img/icon-vin-l.png" style="height: 2.62rem;">
                    </div>
                    <div class="fl padder-l">
                        <p style="font-size: 16px;line-height: 36px;">请选择经销商</p>
                    </div>
                </div>
                <div class="overflow-h">
                    <dealerpicker type="mobile" v-on:selected="onDealerSelected"></dealerpicker>
                </div>
                <div class="text-center padder">
                    <button class="upkeep-seek-btn mg-xxxl" v-on:click="confirmDealerSelected">确认</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Dealerpicker from '../components/Dealerpicker.vue'
    import VDistpicker from 'v-distpicker'
    import Http from '../utils/http';
    import Vue from "vue";
    import VueLocalStorage from 'vue-localstorage';
    import {Toast} from 'mint-ui';

    Vue.use(VueLocalStorage);
    Vue.component('v-distpicker', VDistpicker);

    export default {
        components: {
            "Dealerpicker": Dealerpicker
        },
        data () {
            return {
                regMobile: /^1(3|4|5|7|8)\d{9}$/,
                regLicense: /^[冀豫云辽黑湘皖鲁苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼渝京津沪新京军空海北沈兰济南广成使领A-Z]{1}[a-zA-Z0-9]{5}[a-zA-Z0-9挂学警港澳]{1}$/,
                isAuth: false,
                showSubmit: false,
                selectedAddress: false,
                count: '0',
                selectAddressShowFlag: false,
                selectDealerShowFlag: false,
                vinList: [],
                carModelList: ["经典福克斯", "新福克斯", "全新福克斯", "福睿斯", "翼博", "全新福特翼博", "国产锐界", "金牛座", "麦柯斯", "蒙迪欧", "蒙迪欧致胜", "新蒙迪欧", "嘉年华", "新嘉年华", "翼虎", "新福特翼虎"],
                carModeCacheList: [
                    {"name": "CF", "value": "经典福克斯"},
                    {"name": "CE", "value": "新福克斯"},
                    {"name": "CE", "value": "全新福克斯"},
                    {"name": "FS", "value": "福睿斯"},
                    {"name": "KF", "value": "翼博"},
                    {"name": "FH", "value": "国产锐界"},
                    {"name": "MH", "value": "金牛座"},
                    {"name": "FF", "value": "麦柯斯"},
                    {"name": "BF", "value": "蒙迪欧"},
                    {"name": "FF", "value": "新蒙迪欧"},
                    {"name": "AF", "value": "嘉年华"},
                    {"name": "FN", "value": "新嘉年华"},
                    {"name": "JE", "value": "翼虎"}],
                formData: {
                    id: null,
                    openId: null,
                    customerName: null,
                    customerMobile: null,
                    customerVin: "",
                    carModel: "",
                    license: null,
                    exerciseMileage: null,
                    addressArea: null,
                    addressDetail: null,
                    address: null,
                    complainObject: "",
                    complainReason: "",
                    involveDealer: null,
                    incidentDesc: null
                },
                complainObjects: [//投诉对象
                    {"name": "厂家活动", "value": "厂家活动"},
                    {"name": "经销商", "value": "经销商"},
                    {"name": "产品", "value": "产品"},
                    {"name": "客服", "value": "客服"},
                    {"name": "其他", "value": "其他"}],
                complainReasons: [//问题类型
                    {"name": "销售", "value": "销售"},
                    {"name": "产品", "value": "产品"},
                    {"name": "服务", "value": "服务"},
                    {"name": "零件", "value": "零件"}]
            }
        },
        methods: {
            init: function () {
                let isAuth = window.sessionStorage.getItem("isAuth");
                let openId = window.sessionStorage.getItem("openId");
                if (openId == null || openId == '' || this.openId == "undefind") { //openId 不存在
                    this.goAuth();
                }
                let vin = window.sessionStorage.getItem("vin");
                this.isAuth = isAuth == "true";
                if (this.isAuth) {
                    let vinList = window.sessionStorage.getItem("vinList");
                    if (vinList) {
                        if (vinList.indexOf(",") > -1) {
                            this.vinList = window.sessionStorage.getItem("vinList").split(",");
                        } else {
                            this.vinList = [vin];
                        }
                    }
                }
                let name = window.sessionStorage.getItem("name");
                let mobile = window.sessionStorage.getItem("mobile");
                this.formData.customerVin = !vin ? "" : vin;
                this.formData.customerName = !name ? "" : name;
                this.formData.customerMobile = !mobile ? "" : mobile;
                this.formData.openId = openId;
            },
            goAuth: function () {
                window.location.href = window.location.origin + "/bind/static/init.html?state=31&channelCode=10001&openId=" + window.sessionStorage.getItem("openId");
            },
            loadCarModelList: function (val) {
                this.carModelList = [];
                if (val) {
                    let vin5 = val.substring(4, 5);
                    let vin11 = val.substring(10, 11);
                    let key = vin5 + vin11;
                    this.carModeCacheList.forEach((item) => {
                        if (item.name == key) {
                            this.carModelList.push(item.value);
                        }
                    });
                }
                if (this.carModelList.length == 1) {
                    this.formData.carModel = this.carModelList[0];
                }
            },

            selectDealerShow () {
                this.selectDealerShowFlag = true;
            },
            onDealerSelected (data) {
                this.addressDealerName = data.dealer.value;
            },
            confirmDealerSelected () {
                this.selectDealerShowFlag = false;
                this.formData.involveDealer = this.addressDealerName;
            },
            confirmAddressSelected () {
                this.selectAddressShowFlag = false;
                this.formData.addressArea = this.selectedAddress;
            },
            selectAddressShow: function () {
                this.selectAddressShowFlag = true;
            },
            onAddressSelected (data) {
                this.selectedAddress = data.province.value + data.city.value + data.area.value;
            },
            vinCheck (vin) {//校验VIN是否正确
                if (vin.length != 17) {
                    return false;
                }
                let prefix = vin.substring(0, 3).toUpperCase();
                if (prefix != "LVS" && prefix != "LVR") {
                    return false;
                }
                let endstr = vin.substring(11, 17);
                let reg1 = /^\d+$/;
                let reg2 = /^[A-Za-z]+$/;
                if (!endstr.match(reg1)) {
                    return false;
                }
                let middlestr = vin.substring(10, 11);
                if (!middlestr.match(reg2)) {
                    return false;
                }
                return true;
            },
            submitFormData () {
                let customerName = this.formData.customerName;
                let customerMobile = this.formData.customerMobile;
                let customerVin = this.formData.customerVin;
                let carModel = this.formData.carModel;
                let license = this.formData.license;
                let exerciseMileage = this.formData.exerciseMileage;
                let complainReason = this.formData.complainReason;
                let incidentDesc = this.formData.incidentDesc;
                let involveDealer = this.formData.involveDealer;

                if (this.isAuth) {
                    if (!customerName || !customerMobile || !customerVin || !carModel || !license || !exerciseMileage || exerciseMileage < 0 || !complainReason || !incidentDesc || !involveDealer) {
                        Toast("请填写完整");
                        return;
                    }
                } else {
                    if (!customerName || !customerMobile || !carModel || !complainReason || !incidentDesc || !involveDealer) {
                        Toast("请填写完整");
                        return;
                    }
                }

                if (customerMobile && !this.regMobile.test(customerMobile)) {
                    Toast("手机号格式不正确!");

                    return;
                }

                if (customerVin && !this.vinCheck(customerVin)) {
                    Toast("VIN码格式不正确!");
                    return;
                }

                if (license && !this.regLicense.test(license)) {
                    Toast("车牌号格式不正确!");
                    return;
                }

                new Http("/api/public/complain/apply", this.formData, () => {
                    this.showSubmit = true;
                }, (error) => {
                    console.log(error);
                    if (error) {
                        Toast(error.message);
                    }
                })
            },
            goHome () {
                this.$router.push('/');
            }
        },
        watch: {
            "formData.incidentDesc": function (val) {
                this.count = val.length;
            }
            /* "formData.customerVin": function (val) {
                 this.loadCarModelList(val);
             }*/
        },
        mounted () {
            this.init();
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
    ga('send', 'event', '投诉建议', '投诉建议', '投诉建议');
</script>