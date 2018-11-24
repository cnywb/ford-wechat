<style scoped>
  @import '../resources/css/style.css';
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

        <div class="flex-v frame">
          <!-- <div class="flex-v flex-v-center flex-h-center frame-content" @click="camera()">
           <img style="width:100%;" src="../../static/images/choose_image.png">
          <p class="frame-content-add">+</p>
           <p class="frame-content-font">上传行驶证照片</p>
         </div>-->
          <div class="frame-content" @click="camera()">
            <img style="width:100%;" src="../../static/images/choose_image.png">
          </div>

          <div class="flex-h">
            <div class="flex-h-warp">
              <h4 class="frame-content-h4">参考出现方式</h4>
              <p class="frame-content-p1">1.请按照示例图提交行驶证</p>
              <p class="frame-content-p2">2.照片要四角对齐，如有模糊太暗遮挡，否则不予认证</p>
            </div>
            <div class="flex-h-warp frame-content-img">
              <img class="frame-content-img-size" src="../../static/images/image_card.png">
            </div>
          </div>

        </div>

        <div  align="center" class="input-margin">
          <input type="text" class="text full-width" placeholder="请手动输入VIN码" />
          <input type="button" @click="openShow" class="btn btn-primary width-80 mg-xl " value="提交">
        </div>

      </div>


    </div>
    <!-- 2. Content End -->

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

  </div>
</template>

<script>
  import wx from 'weixin-js-sdk';
  import Resources from '../common/Resources';
  import Toast from '../common/Toast';

  export default {

    mounted() {
      this.initWeixin();
    },
    data () {
      return {
        msg: 'Welcome to Your Vue.js App',
        showUnAuth:false
      }
    },
    methods: {
      initWeixin: function() {
        Resources.post("/i/wechat/info", {url: encodeURIComponent(window.location.href)}).then(data => {
          wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: data.appId, // 必填，公众号的唯一标识
            timestamp: data.timestamp, // 必填，生成签名的时间戳
            nonceStr: data.nonceStr, // 必填，生成签名的随机串
            signature: data.signature,// 必填，签名，见附录1
            jsApiList: ['chooseImage', 'getLocalImgData'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
          });
        }, error => {

        });


        wx.ready(function(){
          wx.checkJsApi({
            jsApiList: [
              'chooseImage', 'getLocalImgData'
            ],
            success: function (res) {
              //alert(JSON.stringify(res));
              console.log(JSON.stringify(res));
            }
          });
        });

      },
      camera: function () {
        wx.chooseImage({
          count: 1, // 默认9
          sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
          sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
          success: (res) => {
            let localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
            if (localIds && localIds.length > 0) {
              this.imageData(localIds[0]);
            }
          }
        });
      },
      imageData: function(localId) {
        console.log("-> imageData");
        wx.getLocalImgData({
          localId: localId, // 图片的localID
          success: function (res) {
            var localData = res.localData; // localData是图片的base64数据，可以用img标签显示
            console.log(localData);

          }
        });
      },
      putImage: function() {

      },

      openShow: function () {
        this.showUnAuth=true;
      },
      closeShowUnAuth: function () {
        this.showUnAuth = false;
      },
    }
  }
</script>

