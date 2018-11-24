<style scoped>
  @import "../resources/css/style.css";
</style>
<template>
  <div class="flex-v container">

    <!-- 1.header begin-->
    <div class="flex-v header">
      <div class="flex-h flex-v-center title">
        <h1 class="flex-1">车主认证</h1>
        <img src="../../static/images/icon_right.png">
      </div>
      <div class="bottom flex-v-end"></div>
    </div>
    <!-- 1.header end-->

    <!--2.content begin-->
    <div class="flex-v flex-1 content">

      <!-- 2.1 Float Content Begin -->
      <div class="float-info flex-1 flex-v warp">

        <div class="flex-h flex-v-center title padder-v car-info">
          <img src="../../static/images/icon_edit.png" alt="">
          <h1>认证车辆信息</h1>
        </div>

        <div class="flex-v">

          <div class="car-info-div-h4 flex-v-center">
            <h4 class="car-info-h4" v-html="label"></h4>
          </div>

          <div class="car-info-overflow">
            <div class="car-info-overflow-pad" v-for="item in cars">
              <img class="car-info-overflow-img" src="../../static/images/icon_car.png">
              <ul>
                <li class="car-info-overflow-li">vin码：{{item.vin}}</li>
                <hr/>
                <li class="car-info-overflow-li">车 型：{{item.model ? item.model : item.brand + item.type}}</li>
              </ul>
            </div>

          </div>
        </div>

        <div class="car-info-group">
          <div class="flex-1 flex-v flex-h-center car-info-pad">
            <div class=" flex-v flex-v-center">
              <input type="button" class="btn btn-primary width-80 mg-xl" @click="bindCar()" value="我要添加车辆">
              <div class="choice-space"></div>
              <input type="button" class="btn btn-primary width-80" @click="completed()" value="完成">
              <div class="choice-space"></div>
            </div>
          </div>
        </div>

      </div>
    </div>
    <!--2.content end-->

  </div>

</template>

<script>
  import Resources from '../common/Resources';
  import Toast from '../common/Toast';
  import Session from '../common/Session';
  import wx from 'weixin-js-sdk';


  export default {
    mounted () {
      console.log("GA");
      this.$ga.page(this.$router);
      this.initData();
      this.initWeixin();
    },
    data () {
      return {
        params: {
          vin: '',
          imageUrl: '',
        },
        status: this.$route.params.status,
        label: '尊敬的车主，恭喜您已认证以下车辆：',
        cars: []
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
      initData: function () {
        var channelType = Session.get(Session.Key.CHANNEL_TYPE);
        var dealer = Session.get(Session.Key.DEALER);
        console.log("channelType: " + channelType + "  dealer: " + dealer);

        if (this.status == 1 || this.status == "1") {//已经完成认证操作
          if (channelType == "0" || channelType == 0) { //经销商
            this.label = "尊敬的车主，恭喜您已经在经销商" + dealer + "处进行了认证<br/>当前认证了以下车辆：";
          } else {
            this.label = "尊敬的车主，恭喜您已认证以下车辆：";
          }
        } else {
          this.label = "尊敬的车主，您已认证过，无需重复认证。<br/>您认证的车辆为：";
        }

        Resources.get("/i/bind/vin/list", null).then((response) => {
          let data = response.body;
          console.log(data);
          this.cars = data;
        }, error => {

        });
      },
      bindCar: function () {
        this.$router.push("/license");
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
