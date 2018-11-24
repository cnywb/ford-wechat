<style scoped>
  @import '../resources/css/style.css';
  @import '../resources/css/base.css';
  @import '../resources/css/flex.css';
</style>

<template>
  <div class="flex-v receive container">
    <div class="flex-h flex-1">
      <div class="flex-1"></div>
      <img class="img-style" src="../../static/images/logo-2.png"/>
    </div>

    <div class="flex-h flex-v-center flex-h-center height-rule">
      <input type="button" class="button-rule width-30" @click="ruleClick" value="活动规则"/>

      <div v-if="show ==='A'" type="button" class="main-red text-center button width-40" @click="receiveClick()">
        <p class="main-receive">领取红包</p>
        <p class="main-surplus">剩余{{surplus}}份</p>
      </div>

      <div v-else-if="show === 'D'"  type="button" class="main-red text-center button width-40" @click="receive1Click()">
        <p class="main-receive">领取红包</p>
        <p class="main-surplus">剩余{{surplus}}份</p>
      </div>

      <div v-else-if="show === 'B'" type="button" class="main-red text-center button width-40" @click="catClick()">
        <p class="main-receive">查看红包</p>
        <p style="font-size: 8px;">({{nextDate}}还可以领取哦)</p>
      </div>

      <div v-else-if="show === 'C'" type="button" class="main-red text-center button1 width-40" @click="catClick()">
        <p class="main-receive1">查看红包</p>
      </div>

      <div v-else id="btn" style="margin: -2.4rem; z-index: 2" type="button" class="text-center button width-40" :disabled="disabled || time > 0">
        <p class="main-count">倒计时</p>
        <p class="main-time">{{text}}</p>
      </div>

      <!--<input style="margin: -2.4rem; z-index: 300" type="button" class="button width-40"  @click= "testClick"  value="领取红包" />-->
      <input type="button" style="width: 30%;z-index: 1" class="button-red" @click="winnersClick" value="红包名单"/>
    </div>

    <div class=" text-center receive-warp" style="padding-bottom: 2rem;">
      <div class="main-receive-marquee text-center" style="text-align: center">
        <div>
          <marquee align="middle" direction="up" truespeed="truespeed" height="20px"  width="150" scrolldelay="500" behavior="scroll">
            <p>
              {{recentNews}}
            </p>
          </marquee>
        </div>
      </div>
    </div>

    <!--弹框 begin-->
    <div class="bomb-box" v-if="showUnAuth">
      <!-- <div class="bomb-box-div"></div>-->
      <div class="bomb-box-table">
        <div class="bomb-box-table-cell">
          <div style="margin-top: 2rem;">
            <p style=" font-size: 20px; font-weight: bold;"> 活动还没开始哦~</p>
            <p style="font-size: 9px;font-weight: bold;">(红包领取时间：每日10:00-22:00)</p>
          </div>
          <button class="box-bomb" @click="closeShowUnAuth()">确定</button>
        </div>
      </div>
    </div>
    <!-- 弹框 end-->

    <!--弹框 begin-->
    <div class="bomb-box" v-if="showNoAuth">
      <!-- <div class="bomb-box-div"></div>-->
      <div class="bomb-box-table">
        <div class="bomb-box-table-cell">
          <div style="margin-top: 2rem;">
            <p style=" font-size: 20px; font-weight: bold;"> 你还不是认证车主哦~</p>
            <p style="font-size: 9px;font-weight: bold;">点击确认即刻成为认证车主领千万红包大礼</p>
          </div>
          <button class="box-bomb" @click="closeShowNoAuth()">确定</button>
        </div>
      </div>
    </div>
    <!-- 弹框 end-->

  </div>
</template>

<script>
  import Vue from "vue";
  import axios from 'axios'

  export default {
    mounted () {
      console.log("GA");
      this.$ga.page(this.$router);
      console.info("加载前执行");
      this.initMain(true);
      this.initLoop();

    },
    data() {
      return {
        coupon:'',
        begun:false,
        recentNews:'',
        nextDate:'',
        surplus:0,
        show:'A',
        showUnAuth:false,
        showNoAuth:false,
        noEvent:false,
        bindUrl:'',
        time: 0,
        surplusTime:300,
        minutes: 0,
        second: 0,
        disabled: false,
        timeout: null,
        stop:null,
        ended:false,
        submitting: false
      }
    },
    methods: {
      initLoop: function() {
         this.stop = setInterval(()=>this.initMain(false), 5000); //5秒向后台发送一次请求
      },
      initMain: function (loading) {
        axios.get('/e/state/1', {showLoading: loading}).then(response => {
          console.log(response);

          //剩余份数
          this.surplus = response.body.surplus;
          //最新消息
          this.recentNews = response.body.recentNews;
          //判断是否认证
          if(response.body.bind == false){
            this.showNoAuth = true; //未认证
          }
          //有奖vin未绑定
          if(response.body.needCouponBind === true){
            this.show = 'D';
            this.begun = response.body.begun;
            this.ended = response.body.ended;
          } else if (response.body.involved == true) {//判断是否中奖

              if(response.body.nextDate!=null && response.body.nextDate != undefined){
                this.show = 'B';
                this.nextDate = response.body.nextDate;
              }else{
                this.show = 'C';
              }

          }else {
            //判断是否开奖5分钟前
            if (response.body.prepare == true) {

              //活动开始时间
              var startTime = response.body.startTime;
              //服务器当前时间
              var currentTime = response.updateTime;
              //字符串转换时间
              startTime = startTime.replace(/-/g,"/");
              currentTime = currentTime.replace(/-/g,"/");
              startTime = new Date(startTime);
              currentTime = new Date(currentTime);
              //获取活动开始前的剩余时间
              var surplusTime = Math.floor((startTime.getTime() - currentTime.getTime())/1000);
              this.surplusTime = surplusTime;
              console.log("剩余秒"+surplusTime)
              this.show = 'E'
              this.timerStart();

            } else {
              this.show = 'A'
              this.begun = response.body.begun;
              this.ended = response.body.ended;
            }
          }
        }).catch(response => {
          console.log(response);
          if (response.code == 'NO_EVENT'||response.code == 'NO_EVENT_TODAY') {
            clearInterval(this.stop);
            this.stop =null;
            this.show = 'A'
            this.noEvent = true;
            return;
          }
        });
      },
      receiveClick:function () {
        if(window.submitting) {
            return;
        }
        if(this.noEvent === true){
            this.showUnAuth = true;
        } else if (this.begun === false) {
          this.showUnAuth = true;
        } else if(this.ended === true){
          this.showUnAuth = true;
        }else {
          //领取红包接口
          window.submitting = true;
          axios.post('/e/coupon/draw', {}).then(response => {
            window.submitting = false;
            console.log(response);
            //成功领取
            if(response.code =='OK'){
              window.clearInterval(this.stop);
              this.stop =null;
              this.coupon = response.body;
              this.$router.push("/success");
            }

          }).catch(response => {
            window.submitting = false;
            console.log(response);

            //已领取完
            if(response.code =="100004"){
              clearInterval(this.stop);
              this.stop =null;
              this.$router.push("/not");
            }
          });
        }

      },
      receive1Click:function () {
        if(this.ended === true){
          this.showUnAuth = true;
        } else if (this.begun === false) {
          this.showUnAuth = true;
        } else {
          //绑定vin接口
          window.clearInterval(this.stop);
          this.stop =null;
          this.$router.push("/success");
        }
      },
      catClick:function () {
        clearInterval(this.stop);
        this.stop =null;
        this.$router.push("/coupon");
      },
      ruleClick: function () {
        clearInterval(this.stop);
        this.stop =null;
        this.$router.push("/rule");
      },
      winnersClick: function () {
        clearInterval(this.stop);
        this.stop =null;
        this.$router.push("/winners");
      },
      closeShowUnAuth: function () {
        this.showUnAuth = false;
      },
      closeShowNoAuth: function () {
          //跳转未认证页面
//        window.location.href = this.bindUrl;
        window.location.href = process.env.BIND_URL;
      },
      timerStart: function () {
        this.time = this.surplusTime;

        if (this.timeout == null) {
          this.timeout = setInterval(() => this.timer(), 1000);
        }

      },
      timer: function () {
        if (this.time > 0) {
          var min = Math.floor(this.time/60);
          var mins = Number(min);
          var minute = mins<10? '0'+mins : mins;
          this.minutes = minute;
          var sec = this.time%60;
          var num = Number(sec);
          var secs = num<10? '0'+num : num;
          this.second = secs;
          this.time--;
        } else {
          clearInterval(this.timeout);
          this.timeout = null;
          this.show = 'A';
        }
      },
    },
    computed: {
      text: function () {
        return this.time > 0 ? "00:"+ this.minutes +":"+ this.second : '';
      }
    },

  }
</script>



