<style scoped>
  @import '../resources/css/style.css';
</style>
<template>
  <div class="flex-v container">

    <!-- 1. Header Begin -->
    <div class="header flex-v">
      <div class="flex-h flex-v-center title">
        <h1 class="flex-1">{{hint.title}}</h1>
        <img class="" src="../../static/images/icon_right.png" alt="">
      </div>
    </div>
    <!-- 1. Header End -->

    <!-- 2. Content Begin -->
    <div class="flex-v flex-1 content white cue-content">
      <div class="flex-2"></div>
      <div class=" flex-v flex-6 flex-v-center">
        <h1>温馨提示</h1>
        <h4 class="cue-content-h4">{{hint.message}}</h4>
        <h4 class="cue-bottem-h4">{{hint.secondMessage}}</h4>

        <div align="center" class="full-width">
          <input type="button" class="btn btn-primary mg-xl cue-content-button" @click="completed()" value="确认">
        </div>
      </div>


    </div>
    <!-- 2. Content End -->

  </div>
</template>

<script>
  import wx from 'weixin-js-sdk';
  import Resources from '../common/Resources';
  import Session from '../common/Session';

  export default {
    name: 'mobile',
    mounted() {
      console.log("GA");
      this.$ga.page(this.$router);
      this.initWeixin();

      if (this.$route.params.business == "bind" && this.$route.params.status == "waiting") {

      }
      if (this.$route.params.business == "unbind" && this.$route.params.status == "waiting") {
        this.hint.title = "认证问题";
        this.hint.message = "您的反馈已提交，我们会尽快解决，";
        this.hint.secondMessage = "谢谢您的支持";
      }
    },
    data () {
      return {
        params: {
          business: this.$route.params.business,
          status: this.$route.params.status
        },
        hint: {
          title: "车主认证",
          message: "您的信息已提交，目前正在审核中，",
          secondMessage: "预计3个工作日审核完成，谢谢！"
        }
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
      completed: function () {
        let url = Session.get(Session.Key.CHANNEL_URL);
        if (url) {
          window.location.href = url;
          return;
        }
        this.$router.push("/subscribe");
//        window.location.href = process.env.WECHAT_REDIRECT;
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
