<style scoped>
  @import "../resources/css/style.css";
</style>
<template>
  <div class="flex-v container">

    <!-- 1.header begin-->
    <div class="flex-v header">
      <div class="flex-h flex-v-center title">
        <h1 class="flex-1">认证问题</h1>
        <img src="../../static/images/icon_question.png">
      </div>
      <div class="bottom flex-v-end"></div>
    </div>
    <!-- 1.header end-->

    <!--2.content begin-->
    <div class="flex-v flex-1 content">

      <!-- 2.1 Float Content Begin -->
      <div class="flex-1 flex-v warp white"><!--gray-->
        <div class="scroll-v">

          <div class="flex-v padder-h-xxl">
          <h4 class="padder-b">认证信息</h4>
          <div class="flex-h flex-v-center padder-h-xl divider row" @click="sheet.questions.show = true">
            <div class="flex-1 problem-label"><span class="font-red">*</span>问题选择</div>
            <div class="font-right">{{params.question}}</div>
            <span class="new-arrow">&nbsp; > </span>
          </div>
       <!--   <div class="flex-h flex-v-center padder-h-xl divider row">
            <div class="problem-label">涉事经销商</div>
            <input class="describe-input flex-1" v-model="params.dealer" placeholder="如有请填写">
          </div>-->
          <div class="flex-h flex-v-center padder-h-xl divider row">
            <div class="problem-label"><span class="font-red">*</span>联系手机</div>
            <input class="describe-input flex-1" v-model="params.mobile" placeholder="请输入手机号">
          </div>
          <div class="flex-h flex-v-center padder-h-xl divider row">
            <div class="problem-label"><span class="font-red">*</span>输入VIN</div>
            <input class="describe-input flex-1" style="text-transform: uppercase;" v-model="params.vin" placeholder="请输入VIN">
          </div>
          <div class="flex-h flex-v-center padder-t">
            <h4><span class="problem-red">*</span>问题补充(上传行驶证)</h4>
          </div>
          <div class="flex-h flex-v-center problem-desc-row">
            <div class="flex-v flex-1 padder-b">
              <div class="problem-count-title font-right">{{count}}/200</div>
              <textarea maxlength="200" v-model="desc" rows="5" class="describe-textarea"
                        placeholder="在此描述您遇到的问题，将有助客服人员更快地处理您的申请"/>
            </div>
            <div class="flex-h flex-v-center padder-b" style="padding-top: 1.4rem;">
              <img src="../../static/images/icon_link.png" class="photo-img padder-h">
              <div @click="camera()">
              <img v-if="!params.imageUrl" src="../../static/images/bg_upload2.png" class="describe-img">
              <img v-else src="../../static/images/bg_upload1.png" class="describe-img">
              </div>
            <!--  <img v-else :src="params.imageUrl" @click="camera()" class="describe-img">-->
            </div>
          </div>

        </div>
          <div align="center" class="mg-xxxl">
          <input type="button" class="btn btn-primary mg-xl width-80" @click="submit()" value="提交申请"/>
        </div>

        </div>
      </div>
    </div>
    <!--2.content end-->
    <mt-actionsheet :actions="sheet.questions.actions" v-model="sheet.questions.show"></mt-actionsheet>
  </div>


</template>

<script>
  import Vue from "vue";
  import wx from 'weixin-js-sdk';
  import Resources from '../common/Resources';
  import Session from '../common/Session';
  import Test from '../common/Test';
  import Utils from '../common/Utils';
  import {Toast, Indicator, Actionsheet} from 'mint-ui';

  Vue.component(Actionsheet.name, Actionsheet);

  export default {
    mounted() {
      console.log("GA");
      this.$ga.page(this.$router);

      this.initWeixin();
      this.initData();
    },
    data () {
      return {
        count: '0',
        params: {
          question: '',
          mobile: '',
          dealer: '',
          vin: '',
          imageId: null,
          imageUrl: ''
        },
        desc: '',
        sheet: {
          questions: {
            actions: [
              {
                name: "无法认证",
                method: this.questionSelected
              }, {
                name: "审核问题",
                method: this.questionSelected
              }, {
                name: "其他",
                method: this.questionSelected
              }
            ],
            show: false
          }
        }

      }
    },
    watch: {
      desc: function (val) {
//        console.log(val)
//        console.log(val.length)
        this.count = val.length;
      }
    },
    methods: {
      initWeixin: function () {
        console.log(window.location.origin);
        console.log(window.location.href);
        Resources.post("/i/wechat/info", {url: encodeURIComponent(window.location.href)}).then((response) => {
          let data = response.body;
          console.log(data);
          wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: data.appId, // 必填，公众号的唯一标识
            timestamp: data.timestamp, // 必填，生成签名的时间戳
            nonceStr: data.nonceStr, // 必填，生成签名的随机串
            signature: data.signature,// 必填，签名，见附录1
            jsApiList: ['chooseImage', 'getLocalImgData', 'hideMenuItems', 'hideAllNonBaseMenuItem'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
          });
        }, error => {

        });
        wx.ready(function () {
          wx.hideAllNonBaseMenuItem();
        });
      },
      camera: function () {
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
          success: res =>{

            var localData = res.localData; // localData是图片的base64数据，可以用img标签显示
            console.log(localData);
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
          Toast("建议您再试一次哦~")
//          Toast(error.message);
        });
      },
      initData: function () {

      },


      submit: function () {
        if (!this.params.question) {
          Toast("请选择问题");
          return;
        }
        if (!this.params.mobile) {
          Toast("请输入手机号");
          return;
        }
        if (!this.params.vin) {
          Toast("请输入VIN");
          return;
        }
        if (!this.desc) {
          Toast("请填写事件描述");
          return;
        }
        if (!this.params.imageId) {
          Toast("请上传行驶证照片");
          return;
        }

        /*if (this.params.question == '修改手机' && !this.params.mobile) {
          Toast("请填写手机号");
          return;
        }
        if (this.params.question == '修改手机' && !this.params.vin) {
          Toast("请填写涉事VIN");
          return;
        }*/

        let params = {
          question: this.params.question,
          dealer: this.params.dealer,
          mobile: this.params.mobile,
          vin: this.params.vin,
          imageUrl: this.params.imageUrl,
          imageId: this.params.imageId,
          desc: this.desc
        }

        Resources.post("/i/service/save", params).then(response => {
          let data = response.body;
          this.$router.replace("hint/unbind/waiting");
        }, error => {
          console.log(error);
          Toast(error.message);
        });
      },
      questionSelected: function (data) {
        let name = data.name;
        this.params.question = name;
        this.sheet.questions.show = false;

      }
    },
    computed: {
      text: function () {
        alert("test")
      }
    },

  }
</script>
