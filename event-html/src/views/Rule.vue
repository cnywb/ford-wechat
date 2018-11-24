<style scoped>
  @import '../resources/css/style.css';
</style>

<template>
  <div class="flex-v rule container">
    <div class="scroll-v">
    <div class="flex-h">
      <div class="flex-2"></div>
      <img style="margin:1rem;width: 55px;height: 35px;" src="../../static/images/logo-2.png"/>
    </div>

    <div class="warp" style="height: 100%;margin-top: -2rem;">
        <div class="text-center">
        <img style="margin:1rem;width: 100px;height: 30px;" src="../../static/images/rule-title.png" />
        </div>

      <p><span style="font-size: 15px; font-weight: bold;">一.活动起始时间</span> 即日起</p>
      <p><span style="font-size: 15px; font-weight: bold;">二.活动对象</span> 全体长安福特认证车主</p>
      <p><span style="font-size: 15px; font-weight: bold;">三.奖项设置</span>  代金券红包奖励(红包使用有效期，自领取日起30天内)</p>
      <p><span style="font-size: 15px; font-weight: bold;"> 四.活动细则</span> </p>
      <p>1.长安福特车主，只需关注长安福特微客服公众号，并完成车主认证，即可参与领红包活动。</p>
      <p> 2.车主领取到红包中的代金券后，可于30天后再次参与活动,领取红包。</p>
      <p> 3.活动每日10:00开启，22:00截止，红包中代金券金额随机，每天平均3000个名额，先到先得。红包领取后，使用详情可点击“查看红包”进行了解。</p>

      <p><span style="font-size: 15px; font-weight: bold;"> 五.免责声明</span> </p>
      <p> 1.用户在进行活动中，如使用非常规参与方式，长安福特将有权视其获得的红包无效。</p>
      <p>  2.用户领取红包成功与否，请以活动页面提示为准。如因网络系统异常等问题，导致无法及时领取红包，长安福特微客服将不会给予相关补偿。</p>
      <p>  3.如您对本次活动有相关异议及问题，可及时与微客服进行联系，或拨打热线400-887-7766</p>
      </div>

    <div class=" text-center" style="padding-bottom: 2.4rem;margin-top: 1rem;">
      <input type="button" class="button width-40"  @click= "testClick"  value="返回首页" />
    </div>
    </div>
  </div>
</template>

<script>
  import Vue from "vue";

  export default {
    mounted () {
      console.log("GA");
      this.$ga.page(this.$router);
//      console.log("test");
    },
    data() {
      return {
        //value:test
      }
    },
    methods: {
      testClick: function () {
        this.$router.replace("/");
      }
    }

  }
</script>



