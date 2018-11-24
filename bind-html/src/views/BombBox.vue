<template>

  <!--弹框 begin-->
  <div class="bomb-box" v-if="showUnAuth">
    <div class="bomb-box-div"></div>
    <div class="bomb-box-table">
      <div class="bomb-box-table-cell">
        <h1>温馨提示</h1>
        <h4 class="bomb-box-table-h4">请调整姿势再试一次或尝试手动输入哦</h4>
        <img class="bomb-box-table-close" src="../../static/images/icon_close.png" alt="关闭" @click="closeShowUnAuth">
      </div>
    </div>
  </div>
  <!--弹框 end-->

</template>
<script>
  import wx from 'weixin-js-sdk';
  import Resources from '../common/Resources';


  export default {
    mounted () {
      console.log("GA");
      this.$ga.page(this.$router);
      this.initWeixin();
    },
    data() {
      return {
        //当前用户是否认证
        isAuth: false,
        //弹出未认证提示界面
        showUnAuth: true,
      }
    },
    methods: {
      closeShowUnAuth: function () {
        this.showUnAuth = false;
      },
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
