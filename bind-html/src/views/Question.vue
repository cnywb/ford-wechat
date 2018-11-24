<style scoped>
  @import '../resources/css/style.css';
</style>
<template>
  <div class="flex-v container">
    <!-- 1. Header Begin -->
    <div class="header flex-v">
      <div class="flex-h flex-v-center title">
        <h1 class="flex-1">认证教程</h1>
        <img class="" src="../../static/images/icon-xinxi.png" alt="">
      </div>
      <div class="bottom flex-v-end"></div>
    </div>
    <!-- 1. Header End -->

    <!-- 2. Content Begin -->
    <div class="flex-v flex-1 content">

      <!-- 2.1 Float Content Begin -->
      <div class="float flex-1 flex-v warp scroll-v">
        <div>

          <div class="flex-h flex-v-center title padder-v">
            <h2 class="question-color">长安福特车主认证教程</h2>
          </div>

          <div class="">
            <div class="flex-v flex-1 flex-v-center">
              <div class="padder-v">

                <div style="padding-bottom: 1rem;">欲成为微客服认证车主，您只需要完成以下2个步骤，同时关注我们，即可享受更多福利。</div>


                <h4 class="question-color" > <img class="question-width"  src="../../static/images/sequence1.png">进入认证页面</h4>
                <div class="flex-h margin-top"><div class="news-div1-name"><img src="../../static/images/sign.png"></div><div>关注下方微信公众号，成为微客服粉丝。点击“我是车主”，直接进入认证页面。</div></div>
                <img class="padder-v full-width" src="../../static/images/question_1.png">


                <div class="flex-h"><div class="news-div1-name"><img src="../../static/images/sign.png"></div><div>或点击个人中心-即刻认证，进入认证页面。</div></div>
                <img class="padder-v full-width" src="../../static/images/question_2.png">


                <h4 class="padder-v question-color" ><img class="question-width"  src="../../static/images/sequence2.png">提交认证信息</h4>
                <span>根据以下步骤填写信息，即可完成认证。第一步，输入常用手机号获取短信验证码；第二步，输入VIN码验证。您可以选择手动输入VIN码或上传行驶证由系统自动识别VIN码两种方式。</span>
                <img class="padder-v full-width" src="../../static/images/question_3.png">


                <h4 class=" question-top question-color" ><img class="question-width" style="padding-top: 0.1rem" src="../../static/images/mark.png">填写过程中，请注意：</h4>
                <!--<p><img class="question-bottom" src="../../static/images/sign.png">	确保手机号为常用号码；保证输入的验证码和您收到的短信一致</p>
                <p><img class="question-bottom" src="../../static/images/sign.png">保证上传的行驶证完整而清晰</p>-->
                <div class="flex-h"><div class="news-div1-name"><img src="../../static/images/sign.png"></div><div>确保您输入的手机号可以收到短信验证码</div></div>
                <div class="flex-h"><div class="news-div1-name"><img src="../../static/images/sign.png"></div><div>行驶证请使用裸证拍摄，去除证套，保证提交的照片清晰、完整</div></div>
                <div class="flex-h"><div class="news-div1-name"><img src="../../static/images/sign.png"></div><div>VIN码需要核对无误后再提交，以免影响认证结果</div></div>
                <!--<div class="text-center"><img class="padder-v" src="../../static/images/question_4.png"></div>-->


                <h4 class="padder-v question-color">当您有多个车辆需要认证...</h4>
                <p>请确保您已关注长安福特微客服，如您拥有2辆或以上车辆需要认证，可以：</p>
                <div class="flex-h"><div class="news-div1-name"><img src="../../static/images/sign.png"></div><div>在个人中心的“我的车辆”，选择“添加车辆”</div></div>
               <!-- <p> <img class="question-bottom" src="../../static/images/sign.png">在个人中心的“我的车辆”，选择“添加车辆”</p>-->

                <img class="padder-v full-width" src="../../static/images/question_5.png">
                <div class="flex-h"><div class="news-div1-name"><img src="../../static/images/sign.png"></div><div>也可以在认证完成时，选择“我要添加车辆”</div></div>
               <!-- <p><img class="question-bottom"  src="../../static/images/sign.png">也可以在认证完成时，选择“我要添加车辆”</p>-->
                <div class="text-center">
                  <img class="padder-v full-width" src="../../static/images/question_6.png">
                </div>

             <!--   <h4 class="padder-v">如在认证中遇到任何问题，您可以在个人中心查找“认证问题反馈”功能，点击填写问题并提交，我们将尽快回复您。 </h4>
                <div class="text-center">
                  <img class="padder-v full-width" src="../../static/images/question_7.png">
                </div>-->


                <h4 class="padder-v"></h4>

                <div class="flex-h divider flex-v-center padder-v" @click="qaShow = !qaShow">
                  <h4 class="flex-1 question-color">如果您在认证中遇到问题，可以点击这里查看：</h4>
                  <div class="caret"></div>
                </div>

                <div v-show="qaShow">
                  <h4 class="padder-v">为什么提交信息后，显示“需要审核”？</h4>
                  <div>答：您好，如果提交的信息存在需要后台人工判断的情况，那么需要一定时间进行审核，请耐心等待，三天内微客服将告知您审核结果。如三天后还未收到反馈信息，请及时与在线客服联系。
                  </div>

                  <h4 class="padder-v">为什么我认证不了？</h4>
                  <div>答：您好，认证成功需要提供准确的VIN码和常用联系方式，如果仍然无法认证成功，建议您在个人中心进入“认证问题反馈”，我们将尽快寻找原因并予以答复。</div>
                  <div class="text-center">
                    <img class="padder-v full-width" src="../../static/images/question_7.png">
                  </div>

                  <h4 class="padder-v">认证后，我需要修改个人信息或解除/变更车辆绑定该怎么做？</h4>
                  <div>答：您好，您可以在个人中心进入“认证问题反馈”，我们会尽快为您解决。</div>
                </div>

                <h4 class="padder-v-xxxl">
                  欢迎您成为长安福特微客服认证车主！ 我们将不定期放送多种车主专享福利，让您能尽情享受参与秒杀、免费领取代金券等活动的超值体验。您还可以在个人中心及时了解到与您爱车密切相关的信息，包括工单查询、保养记录、养护状态等；还有最专业的行车养车建议将为您的爱车轻松护航。
                </h4>

                  <h4>长安福特只为您的爱车提供更优质的服务！</h4><br/><br/><br/>
                  <img style="width: 40px; float:right;" src="../../static/images/question-logo.png">


              </div>
            </div>
          </div>
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
    mounted () {
      console.log("GA");
      this.$ga.page(this.$router);
      this.initWeixin();
    },
    data () {
      return {
        qaShow: false,
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
    },


  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
