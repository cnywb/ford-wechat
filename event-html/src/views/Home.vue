<template>
  <div>
    <div>/event/open/demo/list: {{message.list}}</div>
    <div>/event/open/demo/error: {{message.error}}</div>
  </div>
</template>

<script>
  import Vue from "vue";
  import axios from 'axios'

  export default {
    name: 'hello',
    mounted () {
      console.log("GA");
      this.$ga.page(this.$router);
      this.login();
    },
    data() {
      return {
        message: {
          list: "",
          error: ""
        }
      }
    },
    methods: {
      login: function () {
        axios.get('/event/open/1/login', {}).then(response => {
          console.log(response);
        }).catch(response => {
          console.log(response);
        });
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
