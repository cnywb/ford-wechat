<style scoped>
  @import '../resources/css/style.css';
</style>

<template>
   <div class="flex-v not container">
     <div class="flex-h flex-1" >
       <div class="flex-1"></div>
       <img style="margin:1rem;width: 55px;height: 35px;" src="../../static/images/logo-2.png"/>
     </div>
     <div class=" text-center" style="padding-bottom: 2rem;">
       <input type="button" class="button width-40"  @click= "testClick"  value="确定" />
     </div>
  </div>
</template>

<script>
  import Vue from "vue";

  export default {
    mounted () {
      console.log("GA");
      this.$ga.page(this.$router);
      console.log("test");
    },
    data() {
      return {
        value:'test'
      }
    },
    methods: {
      testClick: function () {
        this.$router.replace("/subscribe");
      }
    }

  }
</script>



