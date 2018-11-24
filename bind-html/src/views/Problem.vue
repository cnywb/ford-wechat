<style scoped>
  @import "../resources/css/style.css";
  @import '../resources/css/base.css';
  @import '../resources/css/flex.css';
</style>
<template>

  <div class="flex-v container">


    <!-- 1.header begin-->
    <div class="flex-v header">
      <div class="flex-h flex-v-center title">
        <h1 class="flex-1">认证问题</h1>
        <img src="../../static/images/icon_question.png">
      </div>
      <div class="bottom1 flex-v-end"></div>
    </div>
    <!-- 1.header end-->

    <!--2.content begin-->
    <div class="flex-v flex-1 content">

       <div class="flex-v flex-1 warp white">
      <div class="scroll-v">
      <!-- 2.1 Float Content Begin -->
      <div class=" flex-v  gray divider"><!--gray-->
        <div class="flex-v padder-h-xxl" style="height: 6rem">
          <h4 class="padder-b">认证信息</h4>
          <div class="flex-h flex-v-center padder-h-xl row" @click="sheet.vins.show = true">
            <span class="problem-red">*</span>VIN 码：<span class="flex-1">{{car.vin}}</span>
            <span class="new-arrow">&nbsp; > </span>
          </div>
       <!--   <div class="flex-h flex-v-center padder-h-xl divider row">
            <div class="problem-label">&lt;!&ndash;<span class="font-red">*</span>&ndash;&gt;姓 名：{{car.name}}</div>
          &lt;!&ndash;  <div class="flex-1"><span class="font-red">*</span>联系电话：{{car.mobile}}</div>&ndash;&gt;
          </div>
          <div class="flex-h flex-v-center padder-h-xl row">
            <div class="flex-1">&lt;!&ndash;<span class="font-red">*</span>&ndash;&gt;联系电话：{{car.mobile}}</div>
          &lt;!&ndash;  <div class="problem-label flex-1">联系地址：{{car.address}}</div>&ndash;&gt;
            &lt;!&ndash;<span class="new-arrow">&nbsp; > </span>&ndash;&gt;
          </div>-->
        </div>
      </div>
      <div class=" hr-div divider"></div>
      <div class="flex-v flex-1  white">
        <div class="flex-1 flex-v">
          <div>
            <div class="flex-v padder-h-xxl">

              <div class="flex-h flex-v-center padder-h-xl divider row">
                <div class="problem-label"><span class="font-red">*</span>联系电话</div>
                <input style="text-transform: uppercase;" class="describe-input flex-1" v-model="params.mobile" placeholder="请输入手机号">
              </div>

              <div class="flex-h flex-v-center padder-h-xl divider row" @click="sheet.questions.show = true">
                <div class="flex-1 problem-label"><span class="font-red">*</span>问题选择</div>
                <div class="font-right">{{params.question}}</div>
                <span class="new-arrow">&nbsp; > </span>
              </div>
              <div class="flex-h flex-v-center padder-h-xl divider row" @click="sheet.options.show = true">
                <div class="problem-label"><span class="font-red">*</span>问题描述</div>
                <div class="flex-1 font-right">{{params.option}}</div>
                <span class="new-arrow">&nbsp; > </span>
              </div>
          <!--    <div class="flex-h flex-v-center padder-h-xl divider row">
                <div class="problem-label">涉事经销商</div>
                <input class="describe-input flex-1" v-model="params.dealer" placeholder="如有请填写">
              </div>-->
              <div class="flex-h flex-v-center padder-h-xl divider row">
                <div class="problem-label"><span class="font-red">*</span>{{params.numLabel}}</div>
                <input style="text-transform: uppercase;" class="describe-input flex-1" v-model="params.value" :placeholder="params.placeholder">
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
               <!--   <img v-else :src="params.imageUrl" @click="camera()" class="describe-img">-->
                </div>
              </div>
            </div>
          </div>

        </div>
        <div align="center" class="mg-xxxl">
          <input type="button" class="btn btn-primary mg-xl width-80" @click="submit()" value="提交申请"/>
        </div>
      </div>
      </div>
      </div>
    </div>
    <!--2.content end-->
    <mt-actionsheet :actions="sheet.vins.actions" v-model="sheet.vins.show"></mt-actionsheet>
    <mt-actionsheet :actions="sheet.questions.actions" v-model="sheet.questions.show"></mt-actionsheet>
    <mt-actionsheet :actions="sheet.options.actions" v-model="sheet.options.show"></mt-actionsheet>
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
        vins: [],
        car: {},
        count: '0',
        params: {
          question: '',
          option: '',
          mobile: '',
          dealer: '',
          vin: '',
          value: '',
          numLabel: '输入VIN',
          imageId: null,
          imageUrl: '',
          placeholder:'请输入VIN'
        },
        desc: '',
        sheet: {
          vins: {
            actions: [],
            show: false
          },
          questions: {
            actions: [
          /*    {
                name: "修改手机",
                method: this.questionSelected
              },*/
              {
                name: "解除VIN码绑定",
                method: this.questionSelected
              },
              {
                name: "无法认证",
                method: this.questionSelected
              },
              {
                name: "其他",
                method: this.questionSelected
              }
            ],
            show: false
          },
          options: {
            actions: [],
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
          success: res => {
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
          //行驶证识别失败3次后
//          this.times++;
//          if (this.times >= 3) {//强制提交
//            this.submitAuthApply(localData);
//            return;
//          }
          Toast("建议您再试一次哦~")
        });
      },
      initData: function () {
        Resources.get("/i/bind/vin/", {url: encodeURIComponent(window.location.href)}).then((response) => {
          let data = response.body;
          console.log(data);
          if (data.length > 0) {
            this.car = data[0];
            this.params.mobile=this.car.mobile;
          }
          let actions = [];
          data.forEach(value => {
            actions.push({
              name: value.vin,
              method: this.vinSelected
            });
          })
          this.cars = data;
          console.log(actions);
          this.sheet.vins.actions = actions;
        }, error => {
          if (error.code == "500001") {//未认证
//            Toast("未认证");
            this.$router.replace("/service");
          }
        });
      },


      submit: function () {
        if (!this.params.question) {
          Toast("请选择问题");
          return;
        }
        if (!this.params.option) {
          Toast("请选择处理原因");
          return;
        }
        if (!this.params.value) {
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

        let params = {
          question: this.params.question,
          mobile:this.params.mobile,
          option: this.params.option,
          dealer: this.params.dealer,
          imageUrl: this.params.imageUrl,
          imageId: this.params.imageId,
          desc: this.desc
        }

        if (this.params.question == '修改手机') {//修改手机
          params['mobile'] = this.params.value;
          params['vin'] = this.car.vin;
        } else {//解绑VIN
//          params['vin'] = this.car.vin;
          params['vin'] = this.params.value ? this.params.value : this.car.vin;
        }

        Resources.post("/i/service/save", params).then(response => {
          let data = response.body;
          this.$router.replace("hint/unbind/waiting");
        }, error => {
          console.log(error);
//          Toast(error.message);
          if (error.code == '500001') {//未认证
            Toast("未完成认证不能提交工单哦");
          } else {
            Toast(error.message);
          }
        });
      },
      questionSelected: function (data) {
        let name = data.name;
        this.params.question = name;
        this.params.option = null;
        this.sheet.questions.show = false;
        if (name == "修改手机") {
          this.params.numLabel = "更新手机号";
          this.sheet.options.actions = [
            {
              name: "手机号更改",
              method: this.optionSelected
            }
          ];
        }else if(name == "解除VIN码绑定"){
          this.params.numLabel = "输入VIN";
          this.params.placeholder = "输入需要解除绑定VIN码";
          this.sheet.options.actions = [
            {
              name: "二手车认证",
              method: this.optionSelected
            }, {
              name: "VIN码有误",
              method: this.optionSelected
            }, {
              name: "更换微信号",
              method: this.optionSelected
            }, {
              name: "更换车辆",
              method: this.optionSelected
            }
          ];
        }else if(name == "无法认证"){
          this.params.numLabel = "输入VIN";
          this.params.placeholder = "输入需要认证的VIN码";
          this.sheet.options.actions = [
            {
              name: "认证不成功",
              method: this.optionSelected
            }
          ];
        }else {
          this.params.numLabel = "输入VIN";
          this.params.placeholder = "请输入VIN";
          this.sheet.options.actions = [
            {
              name: "其他",
              method: this.optionSelected
            }
          ];
        }
      },
      optionSelected: function (data) {
        let name = data.name;
        this.params.option = name;
        this.sheet.options.show = false;
      },
      vinSelected: function (data) {
        console.log(data);
        let vin = data.name;
        this.cars.forEach(item => {
          if (item.vin == vin) {
            this.car = item;
          }
        });
        this.sheet.vins.show = false;
      }
    },
    computed: {
      text: function () {
        alert("test")
      }
    },

  }
</script>
