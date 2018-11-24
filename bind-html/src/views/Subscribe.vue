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
          <h1 class="authentication-title-text">长安福特</h1>
        </div>
        <br/>
        <div class="flex-v-center text-center">
          <h2 class="padder-v">长按下方二维码3-5秒</h2>
          <h6>关注长安福特微客服，精彩活动享不停</h6>
        </div>
        <div class="text-center">
          <img src="../../static/images/scan.jpg" class="scan"/>
        </div>

      </div>
      <!-- 2.1 Float Content End -->

    </div>
    <!-- 2. Content End -->

  </div>
</template>

<script>
  import Resources from '../common/Resources';
  import Session from '../common/Session';
  import {Toast, Indicator} from 'mint-ui';
  import wx from 'weixin-js-sdk';


  export default {
    name: 'Subscribe',
    mounted() {
      console.log("GA");
      this.$ga.page(this.$router);
      this.initWeixin();
    },
    data () {
      return {}
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
    },
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
