<style scoped>
  @import '../resources/css/style.css';
</style>
<template>
  <div class="flex-v container">
    <!-- 1. Header Begin -->
    <div class="header flex-v">
      <div class="flex-h flex-v-center title">
        <h1 class="flex-1">车主认证</h1>
        <img class="" src="../../static/images/icon-xinxi.png" alt="">
      </div>
      <div class="bottom flex-v-end"></div>
    </div>
    <!-- 1. Header End -->

    <!-- 2. Content Begin -->
    <div class="flex-v flex-1 content">

      <!-- 2.1 Float Content Begin -->
      <div class="float flex-1 flex-v warp">
        <div class="flex-h flex-v-center title padder-v">
          <img src="../../static/images/icon-bianji.png" alt="" class="authentication-title-img">
          <h1 class="authentication-title-text">信息验证</h1>
        </div>

        <div class="padder-v">
          <input type="text" class="text full-width" v-model="params.mobile" placeholder="请输入您的常用手机号"/>
          <!-- <a class="upkeep-seek-icon"></a>-->
        </div>
        <div class="padder-v flex-h flex-v-center">
          <input type="text" class="text text-width flex-1" v-model="params.code" placeholder="请输入验证码"/>
          <!-- <input type="button" class="button dark mg-l" @click="sendCode" value="获取验证码">-->
          <button id="btn" @click="sendCode" :disabled="disabled || time > 0" :class="class1">
            {{text}}
          </button>
        </div>
        <div>
          <router-link class="padder-h" to="/question">认证教程</router-link>
        </div>


        <div class="flex-1 flex-v">
          <div class=" flex-v flex-v-center">
            <!--  <h2 class="color-primary padder-v">请选择认证方式</h2>-->
            <!--  <input type="button" class="button primary width-80 mg-xl" @click="submit" value="上传行驶证">-->
            <div class="choice-space"></div>
            <!--<input type="button" class="button primary width-80" value="扫描VIN码">-->
            <div class="choice-space"></div>
            <input type="button" class="btn btn-primary width-80 mg-xl" @click="submit" value="确定">
          </div>
        </div>

        <div>
          <router-link class="padder-h" to="/service">认证问题反馈</router-link>
        </div>

      </div>
      <!-- 2.1 Float Content End -->

    </div>
    <!-- 2. Content End -->

  </div>
</template>

<script>
  import wx from 'weixin-js-sdk';
  import Resources from '../common/Resources';
  import Session from '../common/Session';
  import {Toast, Indicator} from 'mint-ui';

  export default {
    name: 'mobile',
    mounted () {
      Toast('即刻成为认证车主，尊享更多身份特权');
      console.log("GA");
      this.$ga.page(this.$router);
      this.initWeixin();
    },
    data () {
      return {
        params: {
          mobile: '',
          code: ''
        },
        time: 0,
        second: 60,
        class1: 'button dark mg-l',
        disabled: false
      }
    },
    methods: {
      initWeixin: function () {
        console.log(window.location.origin);
        console.log(window.location.href);
        Resources.post("/i/wechat/info", {url: encodeURIComponent(window.location.href.split("#")[0])}).then((response) => {
          let data = response.body;
          console.log(data);
          wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: data.appId, // 必填，公众号的唯一标识
            timestamp: data.timestamp, // 必填，生成签名的时间戳
            nonceStr: data.nonceStr, // 必填，生成签名的随机串
            signature: data.signature,// 必填，签名，见附录1
            jsApiList: ['hideMenuItems', 'hideAllNonBaseMenuItem'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
          });
        }, error => {
          console.log("Weixin config error");
        });

        wx.error(function (res) {
          console.log("weixin is error");
          // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
        });

        wx.ready(function () {
          wx.hideAllNonBaseMenuItem();
        });
      },
      sendCode: function () {

       // var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //正则表达式验证手机号

     //   var myreg =  /^1\d{10}$/
        var reg = /^1[0-9]{10}$/;

        console.log(this.params.mobile);
        this.params.mobile = this.params.mobile.trim();
        console.log(this.params.mobile);
        if (this.params.mobile == null || this.params.mobile == "") {
          Toast('手机号输入不能为空');
          return;
        }
        console.log(this.params.mobile.length)
//        console.log(this.params.mobile.startsWith("1")) ;
        /*if(this.params.mobile.indexOf("1") == 0){
          Toast('您的手机号输入错误');
          return;
        }
        if(this.params.mobile.length != 11){
          Toast('您的手机号输入错误');
          return;
        }*/
        if (!reg.test(this.params.mobile)) {
          Toast('您的手机号输入错误');
          return;
        }


        console.log("mobile: " + this.params.mobile);
        Resources.post("/i/code/send", {mobile: this.params.mobile}).then(response => {
          let data = response.body;
          console.log(data);
          this.timerStart();
          Toast('验证码发送成功');
        }, error => {
          Toast(error.message);
        });
      },
      submit: function () {

        if (this.params.mobile == null || this.params.mobile == "") {
          Toast('手机号输入不能为空');
          return;
        }
        if (this.params.code == null || this.params.code == "") {
          Toast('验证码输入不能为空');
          return;
        }

        console.log("mobile: " + this.params.mobile + "  code: " + this.params.code);
        Resources.post("/i/code/check", {mobile: this.params.mobile, code: this.params.code}).then(response => {
          let data = response.body;
          console.log(data);
          Session.set(Session.Key.MOBILE, this.params.mobile);
          this.$router.push("/license");
        }, error => {
          Toast(error.message);
        });
      },
      timerStart: function () {
        setTimeout(() => {
          this.time = this.second;
          this.timer();
        }, 500);
        this.class1 = "button dark1 mg-l"
        this.disabled = true
      },
      timer: function () {
        if (this.time > 0) {
          this.time--;
          setTimeout(this.timer, 1000);
        } else {
          this.class1 = "button dark mg-l"
          this.disabled = false
        }
      }
    },
    computed: {
      text: function () {
        return this.time > 0 ? this.time + '秒后重发' : '获取验证码';
      }
    },

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
