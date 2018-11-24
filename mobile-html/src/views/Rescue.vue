<template>
    <div>
        <div class="index-header information-title">
            <h1>道路救援 <img src="../../static/img/icon-jiuyuan-bai.png" alt=""></h1>
        </div>
        <div class="information-item" v-on:click="showItem1">
            <input type="checkbox" class="rescue-checkbox" id="rescue-checkbox-1">
            <img src="../../static/img/icon-jinjijiuyuan.png" class="icon">
            <span class="font-xl">道路紧急救援</span>
            <img src="../../static/img/icon-xia.png" class="fr more" alt="展开" style="height: 0.8rem;margin-top: 0.5rem;">
        </div>
        <div class="rescue-item-content rescue-item-content-1" v-cloak v-if="item1" style="font-size: 14px;">
            <p>福特道路救援 — 当您在驾驶途中遭遇困境时, 无论是由电子或机械故障，
                还是驾驶失误所导致；无论是在全年<span style="font-size: 1.2em;">365</span>天中的任何一个时间, 也无论
                当时您身在何处，
            </p>
            <p>
                只需拨打<span style="font-size: 1.2em;">24</span>小时全国统一免费救援服务热线：<span style="font-size: 1.3em;">400-650-1668</span>，
                就能在第一时间获得长安福特道路救援团队最迅捷、最专业、最妥善的
                救援服务。
            </p>
            <table>
                <tr>
                    <td>
                        <img src="../../static/img/icon-xianchang.png" alt="">
                    </td>
                    <td>
                        <h3>现场救援服务</h3>
                        对于较简单故障尽量于家中或路边修理解决。
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="../../static/img/icon-tuoche.png" style="height: 1.7rem;">
                    </td>
                    <td>
                        <h3>拖车服务</h3>
                        将故障车辆拖送至附近授权经销商处修理解决。
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="../../static/img/icon-buji.png" alt="" style="height: 1.6rem;">
                    </td>
                    <td>
                        <h3>补给服务</h3>
                        为抛锚车辆补充燃料（＊燃料费须由车主自行承担）、水，
                        提供搭电服务，提供备用钥匙递送或其他解决方案。
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="../../static/img/icon-taxi.png" alt="">
                    </td>
                    <td>
                        <h3>免费出租车服务</h3>
                        在车辆拖运送修时为用户报销最长距离不超过150公里内
                        出租车费。
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="../../static/img/icon-zhusu.png" alt="" style="height: 1.25rem;">
                    </td>
                    <td>
                        <h3>免费住宿</h3>
                        为在异地抛锚并需长时间等待维修的车主安排最多3晚4星级的
                        免费酒店住宿 。
                    </td>
                </tr>
            </table>
        </div>
        <div class="information-item" v-on:click="showItem2">
            <input type="checkbox" class="rescue-checkbox" id="rescue-checkbox-2">
            <img src="../../static/img/icon-yanchang.png" class="icon">
            <span class="font-xl">延长计划</span>
            <img src="../../static/img/icon-xia.png" class="fr more" alt="展开" style="height: 0.8rem;margin-top: 0.5rem;">
        </div>
        <div class="rescue-item-content" v-cloak v-if="item2" style="font-size: 14px;">
            <p>如果您的免费道路救援服务即将或已经到期，我们诚挚地邀请您为您的
                爱车续写一份保障 – “长安福特道路救援延长计划”保证专业，快捷和全
                方位的关怀与呵护将时刻与您一路相伴！
            </p>
            <p style="margin-top: 0.5rem;">
                如果您的爱车自购车之日起不超过5年，您仅需支付很少的年费就可尊享
                超值的“长安福特道路救援延长计划”。
            </p>
            <table>
                <tr>
                    <td style="border-left: none;width: 40%;">
                        <img src="../../static/img/icon-neirong.png" alt="">
                        <br>服务内容
                    </td>
                    <td style="border-right: none;">
                        服务内容与长安福特
                        提供的新车2年免费道路
                        救援服务一致。
                    </td>
                </tr>
                <tr>
                    <td style="border-left: none;">
                        <img src="../../static/img/icon-zhonglei.png" style="height: 1.5rem;">
                        <br>产品种类
                    </td>
                    <td style="border-right: none;">
                        充分考虑客户的需求，“长安福特
                        道路救援延长计划”特设延长1年和延长
                        2年两种计划方案供车主选择。
                    </td>
                </tr>
                <tr>
                    <td rowspan="3" style="border-left: none;">
                        <img src="../../static/img/icon-fangshi.png" alt="">
                        <br>咨询和购买方式
                    </td>
                    <td style="border-right: none;">
                        车主可前往当地最近福特授权
                        经销商4S店咨询和购买。
                    </td>
                </tr>
                <tr>
                    <td style="border-right: none;">
                        或点击页面下方“一键拨打”按钮拨打4006501668进行在线人工客服咨询及购买。
                    </td>
                </tr>
            </table>
        </div>
        <div class="information-item" v-on:click="showItem3">
            <input type="checkbox" class="rescue-checkbox" id="rescue-checkbox-3">
            <img src="../../static/img/icon-xiaoteishi.png" class="icon">
            <span class="font-xl">温馨小贴士</span>
            <img src="../../static/img/icon-xia.png" class="fr more" alt="展开" style="height: 0.8rem;margin-top: 0.5rem;">
        </div>
        <div class="rescue-item-content" v-cloak v-if="item3">
            <p class="text-center" style="font-size: 14px;">当您需要救援时，请准备好下列信息：</p>
            <div class="rescue-hit-board">
                <ul>
                    <li>
                        <i></i>您的姓名
                    </li>
                    <li>
                        <i></i>您的确切位置
                    </li>
                    <li>
                        <i></i>车型、车牌号、<br>&nbsp;&nbsp;&nbsp;&nbsp;底盘号和车身颜色
                    </li>
                    <li>
                        <i></i>您的联系方式
                    </li>
                    <li>
                        <i></i>故障描述
                    </li>
                </ul>
            </div>
        </div>
        <!--<div class="rescue-dial"> 没展开显示-->
        <div class="rescue-dial rescue-dial-checked" style="position: fixed">
            <a href="tel:4006501668" style="color:white">
                <img src="../../static/img/icon-boda.png" alt="">
                <span><br>一键拨打</span></a>
        </div>
    </div>
</template>
<style scoped>
    .rescue-hit-board {
        background: url(../../static/img/hint-board.png) 0 0 no-repeat;
        background-size: 100% 100%;
        height: 24rem;
        width: 18rem;
        margin: auto;
        margin-top: 1.5rem;
        position: relative;
        left: 0.8rem;
    }
</style>
<script>
    export default {
        data() {
            return {
                item1: false,
                item2: false,
                item3: false,
            }
        },
        methods: {
            showItem1: function () {
                this.item1 = !this.item1;
            },
            showItem2: function () {
                this.item2 = !this.item2;
            },
            showItem3: function () {
                this.item3 = !this.item3;
            }
        },
        mounted() {


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
    ga('send', 'event', '道路救援', '道路救援', '道路救援');
</script>