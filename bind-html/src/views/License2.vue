<style scoped>
  @import '../resources/css/style.css';
  @import '../resources/css/base.css';
  @import '../resources/css/flex.css';
</style>
<template>
  <div class="flex-v container">

    <!-- 1. Header Begin -->
    <div class="header flex-v">
      <div class="flex-h flex-v-center title">
        <h1 class="flex-1">车主认证</h1>
        <img class="" src="../../static/images/icon_right.png" alt="">
      </div>
    </div>
    <!-- 1. Header End -->

    <!-- 2. Content Begin -->
    <div class="flex-v flex-1 content white">

      <!-- 2.1 Title Content Begin -->
      <div class="board flex-v warp padder-v">
        <div class="flex-h flex-v-center title padder-v">
          <img src="../../static/images/icon_upload.png" alt="">
          <h1>上传行驶证</h1>
        </div>
      </div>
      <div class="divider"></div>
      <!-- 2.1 Title Content End -->
      <div class="board flex-v flex-1 padder-v-xl title-h5">
        <h5>您只差一步就能成为认证车主，尊享专属福礼！</h5>

        <div class="flex-v frame padder-b"  style="overflow:auto;">
          <!-- <div class="flex-v flex-v-center flex-h-center frame-content" @click="camera()">
           <img style="width:100%;" src="../../static/images/choose_image.png">
          <p class="frame-content-add">+</p>
           <p class="frame-content-font">上传行驶证照片</p>
         </div>-->
          <div class="flex-h-center frame-content" @click="camera()">
            <img class="full-width license-img" v-if="!params.imageUrl" src="../../static/images/choose_image.png" />
            <img class="full-width license-img" v-else :src="params.imageUrl" />
          </div>

          <div class="flex-h license-desc-row padder-v">
            <div class="flex-v flex-1 padder-b padder-h-xl">
              <h4>参考出现方式</h4>
              <p class="frame-content-p">1.请按照示例图提交行驶证</p>
              <p class="frame-content-p">2.照片要四角对齐，如有模糊太暗遮挡，否则不予认证</p>
            </div>
            <div class="padder-b" style="padding-right: 1rem;">
              <img class="frame-content-img-size" src="../../static/images/image_card.png">
            </div>
          </div>

        </div>

        <div class="flex-v flex-v-center">
          <input type="text" class="text full-width" v-model="params.vin" :readonly="vinReadonly"
                 placeholder="请手动输入VIN码"/>
          <input type="button" @click="submit()" class="btn btn-primary width-80 mg-xl " value="提交">
        </div>

      </div>

    </div>
    <!-- 2. Content End -->

    <!--弹框 begin-->
    <div class="bomb-box" v-if="error.show">
      <div class="bomb-box-div"></div>
      <div class="bomb-box-table">
        <div class="bomb-box-table-cell">
          <h1>温馨提示</h1>
          <h4 class="bomb-box-table-h4">{{error.message}}</h4>
          <img class="bomb-box-table-close" src="../../static/images/icon_close.png" alt="关闭" @click="hideErrorInfo()">
        </div>
      </div>
    </div>
    <!--弹框 end-->

  </div>
</template>

<script>
  import wx from 'weixin-js-sdk';
  import Resources from '../common/Resources';
  import Session from '../common/Session';
  import Test from '../common/Test';
  import Utils from '../common/Utils';
  import {Toast, Indicator} from 'mint-ui';

  export default {

    mounted() {
      this.initWeixin();
      console.log(Session.get(Session.Key.AUTHED));
    },
    data () {
      return {
        params: {
          vin: '',
          imageUrl: '',
          imageId: null
        },
        vinReadonly: Session.get(Session.Key.AUTHED),
        times: 0,
        error: {
          show: false,
          message: '请调整姿势再试一次或尝试手动输入哦'
        },
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
            jsApiList: ['chooseImage', 'getLocalImgData'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
          });
        }, error => {
          console.log("Weixin config error");
        });

        wx.error(function(res){
            console.log("weixin is error");
          // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
        });

        /*
        wx.ready(function () {
          wx.checkJsApi({
            jsApiList: [
              'chooseImage', 'getLocalImgData'
            ],
            success: function (res) {
              //alert(JSON.stringify(res));
              console.log(JSON.stringify(res));
            }
          });
        });*/

      },
      camera: function () {
        console.log("camera clicker");

        wx.chooseImage({
          count: 1, // 默认9
          sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
          sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
          success: (res) => {
            console.log("camera success");
            let localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
            if (localIds && localIds.length > 0) {
              this.imageData(localIds[0]);
            }
          }
        });
      },
      imageData: function (localId) {
        console.log("-> imageData");
        console.log(!Utils.isWeixin() || Utils.isDev());

        if (!Utils.isWeixin() || Utils.isDev()) {
          //模拟环境
          this.readVin(Test.base64());
          return;
        }
        //真机环境
        wx.getLocalImgData({
          localId: localId, // 图片的localID
          success: res =>  {
            var localData = res.localData; // localData是图片的base64数据，可以用img标签显示
//            console.log(localData);
            this.readVin(localData);
          }
        });
      },
      readVin: function (localData) {
        Resources.put("/i/bind/license/put", {base64Image: localData}).then(response => {
          let data = response.body;
          console.log(data);
          this.params.vin = data.vin;
          this.params.imageUrl = data.imageUrl;
          this.params.imageId = data.id;
          Session.set(Session.Key.VIN, data.vin);
          Session.set(Session.Key.PHOTO_URL, data.imageUrl);

        }, error => {
          //行驶证识别失败3次后
          this.times++;
          if (this.times >= 3) {//强制提交
            this.submitAuthApply(localData);
            return;
          }
          if (this.vinReadonly) {
            this.showErrorInfo("建议您再试一次哦~");
          } else {
            this.showErrorInfo("建议您再试一次或尝试手动输入哦~");
          }
        });
      },
      clear: function () {
        this.params.vin = null;
        this.params.imageId = null;
        this.params.imageUrl = null;
        Session.remove(Session.Key.VIN);
        Session.remove(Session.Key.PHOTO_URL);
      },
      submitAuthApply: function (localData) {
        let mobile = Session.get(Session.Key.MOBILE);
        Resources.post("/i/bind/apply", {
          mobile: mobile,
          base64Image: localData
        }).then(response => {
          let data = response.body;
          this.showErrorInfo("您的信息已提交，目前正在审核中，预计3个工作日内核实完成，谢谢！");
        }, error => {
          Toast("认证申请提交失败");
        });
      },

      submit: function () {
        if (!this.params.vin) {
          Toast("VIN不能为空");
        }

        let mobile = Session.get(Session.Key.MOBILE);

        let params = {
          mobile: mobile,
          vin: this.params.vin,
          imageUrl: this.params.imageUrl,
          imageId: this.params.imageId,
          times: this.times
        }
        console.log("mobile: " + mobile);
        Resources.post("/i/bind/save", params).then(response => {
          let data = response.body;
          let code = response.code;
          if (code == "OK") {//认证成功
            Toast("恭喜您，认证成功啦");
            this.$router.push("cars");
            return;
          } else if (code == "WAITING") {//认证审核
            this.showErrorInfo("您的信息已提交，目前正在审核中，预计3个工作日内核实完成，谢谢！");
          } else if (code == "NEED_LICENSE") {//需要行驶证
            Toast("还差一点点哦！请上传行驶证");
          } else if (code == "MAX") {//超过认证次数
            this.showErrorInfo("您已经解绑过2次，不能再次提交申请哦");
          } else if (code == "ERROR") {//认证失败
            this.showErrorInfo("网络通信失败");
          } else {
            this.showErrorInfo("网络通信失败");
          }
        }, error => {
          console.log(error);
//          Toast(error.message);
          this.showErrorInfo("网络通信失败");
        });
      },

      showErrorInfo: function (message) {
        this.error = {
          show: true,
          message: message ? message : '请调整姿势再试一次或尝试手动输入哦~'
        }
      },
      hideErrorInfo: function () {
        this.error = {
            show: false,
        }
        if (this.times >= 3) {//强制提交

        }
      },
    }
  }
</script>

