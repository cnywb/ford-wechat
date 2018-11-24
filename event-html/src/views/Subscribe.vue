<style scoped>
  @import '../resources/css/style.css';
  @import '../resources/css/base.css';
  @import '../resources/css/flex.css';
</style>

<template>
  <div class="flex-v rule container">
      <div class="flex-h">
        <div class="flex-2"></div>
        <img style="margin:1rem;width: 55px;height: 35px;" src="../../static/images/logo-2.png"/>
      </div>

    <!-- 2. Content Begin -->
    <div class="flex-v flex-1 content">

      <!-- 2.1 Float Content Begin -->
      <div class="flex-1 flex-v warp">
        <div class="flex-h flex-v-center flex-h-center title padder-v">
          <h1 class="authentication-title-text">长安福特</h1>
        </div>
        <br/>
        <div class="flex-v-center text-center">
          <h2 class="padder-v">长按下方二维码3-5秒</h2>
          <h6>关注长安福特微客服，可在“个人中心”-“我的红包”中查看自己的红包哦！</h6>
        </div>
        <div class="text-center" style="margin-top: 0.5rem;">
          <img src="../../static/images/scan.jpg" style=" width: 80%;"/>
        </div>

      </div>
      <!-- 2.1 Float Content End -->

    </div>
    <!-- 2. Content End -->

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



