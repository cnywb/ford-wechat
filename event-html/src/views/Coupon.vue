<style scoped>
  @import '../resources/css/style.css';
  @import '../resources/css/base.css';
  @import '../resources/css/flex.css';
</style>

<template>
  <div class="flex-v rule container">
    <div class="flex-h" style="height: 100px;">
      <div class="flex-2"></div>
      <img style="margin:1rem;width: 55px;height: 35px;" src="../../static/images/logo-2.png"/>
    </div>

    <div class="warp flex-1">
      <div style="font-family:Impact;">
        <div class="flex-v coupon-img">
          <div class="flex-12 flex-h flex-v-end">
            <img style="margin-bottom:-0.9rem;margin-left: 0.5rem;" src="../../static/images/money.png" height="40px;"
                 width="30px;"/>
            <span style="margin-bottom:-1rem;margin-left:-0.5rem;color:#703A16;font-size: 40px; padding-right: 1rem;">{{amount}}</span>
            <span style="margin-left:-0.8rem;color:#703A16;font-size: 14px; font-weight: bold">服务代金券</span>
          </div>
          <div class="flex-7 "></div>
        </div>
      </div>

      <div class="flex-h" style="margin-top: 2rem;">
        <div class="flex-0-1">
          <img src="../../static/images/coupon-round.png" style=" width:8px;height:8px;margin-top:-0.1rem;margin-right: 0.4rem;"></div>
        <div class="flex-12">生效时间：代金券自领取后即时生效，生效时将收到短信</div>
      </div>

      <div class="flex-h">
        <div class="flex-0-1">
          <img src="../../static/images/coupon-round.png" style=" width:8px;height:8px;margin-top:-0.1rem;margin-right: 0.4rem;"></div>
        <div class="flex-12">有效期：代金券有效期自生效之日起30天内</div>
      </div>

      <!--<div class="flex-h">-->
        <!--<div class="flex-1">-->
          <!--<img src="../../static/images/coupon-round.png" style=" width:8px;height:8px;margin-top:-0.1rem;margin-right: 0.4rem;"></div>-->
        <!--<div>有效期：代金券有效期自生效之日起30天内</div>-->
      <!--</div>-->

      <div class="flex-h">
        <div class="flex-0-1">
          <img src="../../static/images/coupon-round.png" style=" width:8px;height:8px;margin-top:-0.1rem;margin-right: 0.4rem;"></div>
        <div class="flex-12">使用范围：代金券可在全国长安福特授权经销商处，抵扣维修保养费用；代金券不找零不兑换现金</div>
      </div>

      <div class="flex-h">
        <div class="flex-0-1">
          <img src="../../static/images/coupon-round.png" style=" width:8px;height:8px;margin-top:-0.1rem;margin-right: 0.4rem;"></div>
        <div class="flex-12"> 查询：该代金券生效后可在个人中心——电子账户查询</div>
      </div>

    </div>

    <div class="text-center" style=" height: 80px; ">
      <input type="button" class="button width-40" @click="testClick" value="返回首页"/>
    </div>
  </div>
</template>

<script>
  import Vue from "vue";
  import axios from 'axios'

  export default {
    mounted () {
      console.log("GA");
      this.$ga.page(this.$router);
      //调用页面加载前的方法
      this.catAmountClick();
    },
    data() {
      return {
        amount:''
      }
    },
    methods: {
        catAmountClick:function () {
          axios.get('/e/coupon/detail/latest/',{}).then(response => {
            console.log(response);
            if(response.code == 'OK'){
             this.amount = response.body.amount;
            }

          }).catch(response => {

          });
        },
      testClick: function () {
        this.$router.replace("/");
      }
    }

  }
</script>
