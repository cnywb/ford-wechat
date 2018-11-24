<template>
    <div>
        <div class="index-header information-title">
            <h1>车主个人信息完善 <img src="../../static/img/icon-xinxi.png" alt=""></h1>
        </div>
        <div class="information-item" v-on:click="showMsg('userInfo')">
            <img src="../../static/img/icon-owner-xinxi.png" class="icon">
            <span class="font-xl">车主基本信息</span>
            <img src="../../static/img/icon-right.png" class="fr more" alt="展开">
        </div>
        <div class="information-item" v-on:click="showMsg('carInfo')">
            <img src="../../static/img/car-xinxi.png" class="icon">
            <span class="font-xl">车辆基本信息</span>
            <img src="../../static/img/icon-right.png" class="fr more" alt="展开">
        </div>
        <div class="information-item" v-on:click="go2Bind('carInfo')">
            <img src="../../static/img/icon-car-add.png" class="icon">
            <span class="font-xl">添加车辆</span>
            <img src="../../static/img/icon-right.png" class="fr more" alt="展开">
        </div>
        <div class="information-footer">*点击按钮，进入页面，填写相关信息</div>
        <div class="information-item-content" style="bottom:auto" v-if="dialog == 'userInfo'">
            <div class="index-news-header">
                <div class="fl">
                    <img src="../../static/img/icon-owner-xinxi-active.png" style="height: 3rem;">
                </div>
                <div class="fl padder-l">
                    <p style="font-size: 1.2em;">车主</p>
                    <p style="font-size: 1.2em;">基本信息</p>
                </div>
                <img src="../../static/img/icon-close.png" alt="关闭" v-on:click="showMsg"
                     class="index-header-Unautherized-shade-close" style="margin: 10px;">
            </div>
            <div class="information-item-content-scroll">
                <div class="information-item-list">
                    姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;<input type="text" v-model="userInfoName" placeholder="请输入您的姓名">
                </div>
                <div class="information-item-list">
                    <div class="fl information-item-list-sex">
                        性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别&nbsp;&nbsp;&nbsp;&nbsp;
                        <input v-model="userInfoGender" type="radio" name="sex" value="男"><span>男</span>/
                        <input v-model="userInfoGender" type="radio" name="sex" value="女" style="margin-left: 0.2rem;">&nbsp;<span style="margin-left: 0.2rem;">女</span>
                    </div>
                </div>
                <div class="information-item-list">
                    生&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;<input type="date" style="width: 60%;" v-model="userInfoBirthday">
                </div>
                <div class="information-item-list">
                    联系电话&nbsp;&nbsp;<input type="text" v-model="userInfoMobile" placeholder="请输入联系电话">
                </div>
                <div class="information-item-list">
                    电子邮箱&nbsp;&nbsp;<input type="text" v-model="userInfoEmail" placeholder="请输入电子邮箱">
                </div>
                <div class="information-item-list" v-on:click="selectAddressShow">
                    地址区域&nbsp;&nbsp;<input type="text" style="padding-left: 0.5rem;" v-model="userInfoAddressArea" disabled placeholder="点击选择地址区域"><i class="icon-vin-more"></i>
                </div>
                <div class="information-item-list">
                    详细地址&nbsp;&nbsp;<input type="text" v-model="userInfoAddressDetail" placeholder="请输入详细地址">
                </div>
                <div class="information-item-footer" style="width: 90%">
                    <button class="information-item-btn" v-on:click="submitUserInfo">确认提交</button>
                </div>
            </div>
        </div>
        <div class="information-item-content" v-if="dialog == 'carInfo'" v-cloak style="bottom:auto;top: 80px;">
            <div class="index-news-header">
                <div class="fl">
                    <img src="../../static/img/icon-car-xinxi-active.png" style="height: 3rem;">
                </div>
                <div class="fl padder-l">
                    <p style="font-size: 1.2em;">车辆</p>
                    <p style="font-size: 1.2em;">基本信息</p>
                </div>
                <img src="../../static/img/icon-close.png" alt="关闭" v-on:click="showMsg" class="index-header-Unautherized-shade-close" style="margin: 10px;">
            </div>
            <div>
                <div class="information-item-list">
                    购&nbsp;车&nbsp;时&nbsp;间<input type="date" v-model="carInfoBuyDate" style="margin-left: 0.5rem">
                </div>
                <div class="information-item-list" v-on:click="selectDealerShow('buy')">
                    购车经销商<input type="text" style="padding-left: 0.5rem" v-model="carInfoBuyDealerName" disabled placeholder="点击选择购车经销商"><i class="icon-vin-more"></i>
                </div>
                <div class="information-item-list" v-on:click="selectDealerShow('repair')">
                    维修经销商<input type="text" style="padding-left: 0.5rem" v-model="carInfoRepairDealerName" disabled placeholder="点击选择维修经销商"><i class="icon-vin-more"></i>
                </div>
                <div class="information-item-list">
                    车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型<input type="text" style="padding-left: 0.5rem" v-model="carInfoModel" v-if="modelList.length==1" disabled>
                    <select class="b-b" v-model="carInfoModel" style="background: transparent !important;width: 68.8%;text-align: center;border: 0" v-else>
                        <template v-for="item in modelList">
                            <option v-bind:value="item" style="text-align: center">{{item}}</option>
                        </template>
                    </select><i class="icon-vin-more"></i>
                </div>
                <div class="information-item-list">
                    车&nbsp;牌&nbsp;号&nbsp;码<input type="text" v-model="carInfoLicense" placeholder="请输入您的车牌号">
                </div>
            </div>
            <div class="information-item-footer" style="width: 90%">
                <button class="information-item-btn" v-on:click="submitCarInfo">确认提交</button>
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
                    <v-distpicker type="mobile" v-on:selected="onSelected"></v-distpicker>
                </div>
                <div class="text-center padder">
                    <button class="upkeep-seek-btn mg-xxxl" v-on:click="confirmSelected">确认</button>
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
<style>

    .address-container ul {
        height: 13rem !important;
        overflow: scroll !important;;
    }

    .address-container ul li {
        text-align: center;
    }

    .icon-vin-more {
        background: url(../../static/img/icon-xiala.png) 0 0 no-repeat;
        background-size: 50% 50%;
        display: inline-block;
        width: 24px;
        height: 24px;
        position: relative;
        top: 13px;
    }

    .upkeep-city-model {
        position: absolute;
        z-index: 100;
        bottom: 0rem;
        width: 100%;
        color: #1b394e;
        background: #ffffff;
        padding: 0.5rem 1.5rem 1rem 1.5rem;
    }

    .information-item-list input {
        padding-left: 0.5rem;
        width: 70%;
        border-style: none;
        border: none;
        font-size: 14px;
    }

    .information-item-content {
        position: absolute !important;
    }

    .information-item-content input[type='text'][disabled] {
        -webkit-text-fill-color: #0d0d0d;
        -webkit-opacity: 1;
        color: #0d0d0d;
    }

    .information-item-content input[type='date'] {
        -webkit-appearance: none;
        -webkit-tap-highlight-color: rgba(0,0,0,0);;
        outline: none;
        padding-left: 0.2rem;
    }

    .information-item-content-scroll {
        margin-top: 1%;
        height: 80%;
        overflow: scroll;
    }

    @media (max-width: 330px) {
        .information-item-content-scroll {
            height: 80%;
            overflow: scroll;
        }
    }
</style>
<script>
    import Dealerpicker from '../components/Dealerpicker.vue'
    import VDistpicker from 'v-distpicker'

    import Http from '../utils/http';
    import Vue from 'vue';
    import VueLocalStorage from 'vue-localstorage';
    import {Toast} from 'mint-ui';
    Vue.use(VueLocalStorage);
    Vue.component('v-distpicker', VDistpicker)

    export default {
        components: {
            "Dealerpicker": Dealerpicker
        },
        data() {
            return {
                sysName: 'VUEADMIN',
                dialog: null,
                collapsed: false,
                sysUserName: '',
                sysUserAvatar: '',
                openId: '',
                userInfoName: "", userInfoGender: "男", userInfoBirthday: "", userInfoMobile: "", userInfoAddress: "", userInfoEmail: "",
                carInfoBuyDate: "", carInfoBuyDealerName: "", carInfoRepairDealerName: "", carInfoLicense: "", carInfoModel: "", carInfoStyle: "", carInfoColor: "",
                modelList: [],
                modelCacheData: [{"name": "ABF", "value": "嘉年华"},
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
                selectAddressShowFlag: false,//是否显示地址弹出框
                selectedAddress: "",//选择地区
                userInfoAddressArea: "",//地区
                userInfoAddressDetail: "",//详细地址
                selectDealerShowFlag: false,//经销商信息
                carInfoBuyDealerName_: "",//购买经销商
                carInfoRepairDealerName_: "",//维修经销商
                selectDealerFlag: ""//所选择那个经销商
            }
        },
        methods: {
            selectAddressShow() {
                this.selectAddressShowFlag = true;
            },
            selectDealerShow(flag) {
                this.selectDealerFlag = flag;
                this.selectDealerShowFlag = true;
            },
            onSelected(data) {
                this.selectedAddress = data.province.value + data.city.value + data.area.value;
            },
            onDealerSelected(data) {
                if (this.selectDealerFlag == "buy") {
                    this.carInfoBuyDealerName_ = data.dealer.value;
                } else {
                    this.carInfoRepairDealerName_ = data.dealer.value;
                }
            },
            confirmDealerSelected() {
                this.selectDealerShowFlag = false;
                if (this.selectDealerFlag == "buy") {
                    this.carInfoBuyDealerName = this.carInfoBuyDealerName_;
                } else {
                    this.carInfoRepairDealerName = this.carInfoRepairDealerName_;
                }
            },
            confirmSelected() {
                this.selectAddressShowFlag = false;
                this.userInfoAddressArea = this.selectedAddress;
            },
            init: function () {
                let vin = window.sessionStorage.getItem("vin");
                vin = vin.toUpperCase();
                let vin5 = vin.substring(4, 5);
                let vin11 = vin.substring(10, 11);
                let vin8 = vin.substring(7, 8);

                let key = vin5 + vin8 + vin11;
                this.modelList = [];
                let this_ = this;
                this.modelCacheData.forEach(function (item) {
                    if (item.name == key) {
                        this_.modelList.push(item.value);
                    }
                });
                if (this.carInfoModel) {
                    return;
                }
                if (this.modelList.length > 0) {
                    this.carInfoModel = this.modelList[0];
                    console.log("this.carInfoModel   " + this.carInfoModel);
                }
            },
            showMsg: function (flag) {
                this.dialog = flag;
            },
            queryUserInfo: function () {
                new Http("/api/private/info/user/query", {}, (data) => {
                    this.userInfo = data;
                    this.userInfoName = data['userName'];
                    this.userInfoGender = data['gender'];
                    this.userInfoBirthday = data['birthday'];
                    this.userInfoMobile = data['mobile'];
                    this.userInfoAddress = data['address'];
                    if (data['address']) {
                        let address = data['address'].split("`");
                        this.userInfoAddressArea = address[0];
                        this.userInfoAddressDetail = address[1];
                    }
                    this.userInfoEmail = data['email'];
                })
            },
            queryCarInfo: function () {
                new Http("/api/private/info/car/query", {}, (data) => {
                    this.carInfo = data;
                    this.carInfoBuyDate = data['buyDate'];
                    this.carInfoBuyDealerName = data['buyDealerName'];
                    this.carInfoRepairDealerName = data['repairDealerName'];
                    this.carInfoModel = data['model'] ? data['model'] : this.carInfoModel;
                    this.carInfoStyle = data['style'];
                    this.carInfoColor = data['color'];
                    this.carInfoLicense = data['license'];
                })
            },
            submitUserInfo: function () {
                let userName = this.userInfoName;
                let gender = this.userInfoGender;
                let birthday = this.userInfoBirthday;
                let mobile = this.userInfoMobile;
                let address = this.userInfoAddressArea + "`" + this.userInfoAddressDetail;
                let email = this.userInfoEmail;
                if (!userName && !gender && !birthday && !mobile && !address && !email) {
                    Toast("内容不能为空!");
                    return;
                }
                let reg = /^1(3|4|5|7|8)\d{9}$/;
                if (mobile && !reg.test(mobile)) {
                    Toast("手机号格式不正确!");
                    return;
                }
                new Http("/api/private/info/user", {userName: userName, gender: gender, birthday: birthday, mobile: mobile, address: address, email: email}, () => {
                    Toast({
                        message: '操作成功',
                        iconClass: 'icon icon-success'
                    });
                    this.showMsg(null);
                });
            },
            submitCarInfo: function () {
                let buyDate = this.carInfoBuyDate;
                let buyDealerName = this.carInfoBuyDealerName;
                let repairDealerName = this.carInfoRepairDealerName;
                let model = this.carInfoModel;
                let style = this.carInfoStyle;
                let license = this.carInfoLicense;
                if (!buyDate && !buyDealerName && !repairDealerName && !license && !model && !style) {
                    Toast("内容不能为空!");
                    return;
                }
                let reg = /^[冀豫云辽黑湘皖鲁苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼渝京津沪新京军空海北沈兰济南广成使领A-Z]{1}[a-zA-Z0-9]{5}[a-zA-Z0-9挂学警港澳]{1}$/;
                if (license && !reg.test(license)) {
                    Toast("车牌号格式不正确!");
                    return;
                }
                new Http("/api/private/info/car", {buyDate: buyDate, buyDealerName: buyDealerName, repairDealerName: repairDealerName, license: license, model: model, style: style}, () => {
                    Toast({
                        message: '操作成功',
                        iconClass: 'icon icon-success'
                    });
                    this.showMsg(null);
                });
            },
            go2Bind: function () {
                window.location.href = window.location.origin + "/bind/static/init.html?state=31&channelCode=10001&openId=" + this.openId;
            }
        },
        mounted() {
            let openId = window.sessionStorage.getItem("openId");
            this.openId = openId || "";
            this.queryUserInfo();
            this.queryCarInfo();
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
    ga('send', 'event', '我的车辆', '我的车辆', '我的车辆');
</script>