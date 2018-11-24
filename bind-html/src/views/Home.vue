<template>
  <div class="hello">
    {{msg}}
  </div>
</template>

<script>
  import wx from 'weixin-js-sdk';
  import Resources from '../common/Resources';
  import Session from '../common/Session';
  export default {
    name: 'home',
    mounted() {
      console.log("GA");
      this.$ga.page(this.$router);
      this.init();
//      Resources.redirectInit();
    },
    data () {
      return {
        msg: '加载中，请稍后...'
      }
    },
    methods: {

      init: function () {

        let authed = Session.getBool(Session.Key.AUTHED);
        let binding = Session.getBool(Session.Key.BINDING);
        let unbinding = Session.getBool(Session.Key.UNBINDING);
        var routerUrl = window.localStorage.getItem("router");


        if (binding) {//绑定中
          this.$router.replace("/hint/bind/waiting")
        } else if (unbinding) {//解绑中
          this.$router.replace("/hint/unbind/waiting")
        } else if (routerUrl) {//
          this.$router.replace(routerUrl)
        } else if (!authed) {//首次绑定
          this.$router.replace("mobile")
        } else if (authed) {//已绑定过车辆
          this.$router.replace("/cars/0")
        } else {
          this.msg = window.location.href;
        }
      }

    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .hello {
    color: #000;
  }
</style>
