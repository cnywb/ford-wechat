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
      <div class="board flex-v warp padder-v-xxl">
        <div class="flex-h flex-v-center title padder-v">
          <img src="../../static/images/icon_scan.png" alt="">
          <h1>扫描VIN码</h1>
        </div>
      </div>
      <div class="divider"></div>
      <!-- 2.1 Title Content End -->
      <div class="board board-color flex-v flex-1 padder-v-xxl">
        <h5>您只差一步就能成为认证车主，尊享专属福礼！</h5>

        <div class="flex-v case">
          <div class="case-height">
            <div class="case-height-one"></div>
            <div class="case-height-two"></div>
            <div align="center" class="case-height-center">
              <p class="case-height-font">扫描VIN码</p>
            </div>
            <div class="case-height-three"></div>
            <div class="case-height-four"></div>
          </div>
        </div>

        <div  align="center" class="padder-v input-margin">
          <input type="text" class="text full-width" placeholder="请手动输入VIN码" />
          <input type="button" @click="openShow" class="btn btn-primary width-80 mg-xl input-margin" value="提交">
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
  export default {
    data () {
      return {
        msg: 'Welcome to Your Vue.js App',
        showUnAuth:false
      }
    },
    methods: {
      openShow: function () {
        this.showUnAuth=true;
      },
      closeShowUnAuth: function () {
        this.showUnAuth = false;
      },
    }
  }
</script>

