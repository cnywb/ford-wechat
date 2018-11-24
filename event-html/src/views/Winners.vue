<style scoped>
  @import '../resources/css/style.css';
  @import '../resources/css/base.css';
  @import '../resources/css/flex.css';
</style>

<template>
  <div class="flex-v rule container">
      <div class="flex-h" style="height: 61px; ">
        <div class="flex-1"></div>
        <img style="margin:1rem 1rem 0rem 0rem; width: 55px;height: 35px;" src="../../static/images/logo-2.png"/>
      </div>

        <div class="flex-h flex-h-center " style="height: 40px;">
          <img style="width: 100px;height: 30px;" src="../../static/images/red-list.png" />
        </div>

    <div style=" height: 60px;" class="flex-h flex-v-center flex-space-around-h flex-h-center warp" >
      <input type="button" class="button-mobile width-40"   value="手机号" />
      <input type="button" class="button-mobile width-40"   value="红包金额" />
    </div>

    <div class="flex-1 text-center warp flex-v" style="padding-bottom: 2rem;" >
      <div class="flex-1 flex-v" style="  background-color: rgba(0, 0, 0, 0.2);   border-radius: 0.5rem; border: 1px solid rgba(220,220,220,0.5);">

        <div ref="list" style="margin-top: 1.5rem;" class="scroll-y flex-1 flex-h flex-space-around-h flex-h-center" >

          <ul>
            <li style="color: #FED233; font-size: 16px;" v-for="x in value">
             {{x.mobile}}
              &nbsp;&nbsp;&nbsp;&nbsp;
             {{x.amount}}元服务代金券
            </li>
          </ul>

        </div>

        <div class=" ">
          <img  @click = "scrollClick()" src="../../static/images/arrow.png"/>
        </div>

      </div>
    </div>
  <div class=" text-center" style=" height: 80px; ">
    <input type="button" class="button width-40"  @click= "testClick"  value="返回首页" />
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
      this.catWinners();
    },
    data() {
      return {
        value:''
      }
    },
    methods: {
      catWinners:function () {

        axios.get('/e/coupon/list/',{}).then(response => {
          console.log(response);
          this.value = response.body

        }).catch(response => {

            console.log()
        });

      },
      testClick: function () {
        this.$router.replace("/");
      },
      scrollClick:function () {
       // this.$refs.list.style.backgroundColor = 'red';
        this.$refs.list.scrollTop = this.$refs.list.scrollTop+20;
      }
    }

  }
</script>
