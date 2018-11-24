<style scoped>
  @import '../resources/css/style.css';
  @import '../resources/css/base.css';
  @import '../resources/css/flex.css';
</style>

<template>
  <div class="flex-v success container">
    <div class="flex-h flex-1">
      <div class="flex-1"></div>
      <img class="img-style" src="../../static/images/logo-2.png"/>
    </div>
    <div class="text-center receive1-warp div-padding-bottom">
      <div class="success-bg">
        <div class="flex-v">
          <div class="flex-h flex-v-center success-height">
            <div class="divider flex-1 divider-left"></div>
            <div class="success-receive">恭喜您获得了</div>
            <div class="divider flex-1 divider-right"></div>
          </div>

          <div class="flex-v flex-v-center">
            <div class="flex-h flex-v-center flex-h-center button-success width-70">{{amount}}元电子代金券</div>
          </div>

          <div class="success-top" >

            <div v-if="vinShow" class="flex-v flex-v-center" @click="sheet.vin.show = show">
              <div class="width-65 flex-h flex-v-center flex-h-center coupon-vin">{{vin}}</div>
            </div>

            <div v-else id="vin" class="flex-h flex-v-center button-select success-margin-left" style="width: 65%"
                 @click="sheet.vin.show = true">
              <div class=" flex-1"></div>
              <div class="success-vin">请选择VIN</div>
              <div class=" flex-1 success-img-margin"><img src="../../static/images/arrow.png"/></div>
            </div>

          </div>

        </div>
        <div class="success-top1">
          <input type="button" class="button-coupon" @click="saveVinClick()" value="确定"/>
        </div>
        <div class="success-bom">
          <p><img class="success-bom-valid" src="../../static/images/coupon-round.png" width="3%">您可在本页面查看所获代金券 &nbsp;
            <img class="success-bom-valid" src="../../static/images/coupon-round.png" width="3%">{{validTimes}}天后可再次领取
          </p>
        </div>
      </div>
    </div>

    <mt-actionsheet :actions="sheet.vin.actions" v-model="sheet.vin.show"></mt-actionsheet>

  </div>
</template>

<script>
  import Vue from "vue";
  import axios from 'axios'
  import {Toast, Indicator, Actionsheet} from 'mint-ui';
  Vue.component(Actionsheet.name, Actionsheet);

  export default {
    mounted () {
      console.log("GA");
      this.$ga.page(this.$router);
      //调用页面加载前的方法
      this.sendVin();
    },
    data() {
      return {
        show:true,
        vinShow:true,
        vin:'',
        amount:'',
        validTimes:'',
        couponNo: '',
        sheet: {
          vin: {
            actions: [],
            show: false
          }
        }
      }
    },
    methods: {
      sendVin: function () {
        //查询vin和代金券金额

       // console.log(this.couponNo)
        axios.get('/e/coupon/detail/latest/receiving/',{}).then(response => {
          console.log(response);

          if (response.code == 'OK') {
            this.validTimes = response.body.validTimes;
            this.amount = response.body.amount;
            this.couponNo = response.body.couponNo;
            if (response.body.vinList.length == 1) {
              this.vin = response.body.vinList[0];
              this.show = false;
            } else {
              this.vinShow = false;
              for (var x of response.body.vinList) {
                console.log(x);
                let vin = {name: x, method: this.questionSelected};
                this.sheet.vin.actions.push(vin);
              }
            }
          }
        }).catch(response => {
          if (response.code == '100001') {
            Toast('不存在！');
            return;
          }
          if (response.code == '100002') {
            Toast('已领取！');
            return;
          }
        });
      },
      questionSelected: function (data) {
        this.vin = data.name;
        this.vinShow = true;
      },
      saveVinClick: function () {

        if (this.vin == null||this.vin =='') {
          Toast('请选择VIN码');
          return;
        }

        let params = {
          vin: this.vin,
          couponNo: this.couponNo
        };

        axios.post('/e/coupon/draw/vin/', null, {params: params}).then(response => {
            if(response.code== 'OK'){
              this.$router.replace("/subscribe");
            }

        }).catch(response => {
          console.log(response);
          if (response.code == '100006') {
            Toast('未认证！');
            return;
          }
        });

      },
    },
    computed: {
      test: function () {
       console.log('test');
      }
    },

  }
</script>



