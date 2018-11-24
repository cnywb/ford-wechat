// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import 'mint-ui/lib/style.css'
import {Toast, Indicator} from 'mint-ui';
import Axios from './base/Axios'
import Raven from 'raven-js';
import RavenVue from 'raven-js/plugins/vue';
import VueAnalytics from 'vue-analytics'
import Utils from './base/Utils'

if (!Utils.isWeixin()) {
  window.location.href = "./static/error.html";
}

Axios.init();
Raven.config(process.env.RAVEN_CDN).addPlugin(RavenVue, Vue).install();
Vue.use(VueAnalytics, { id: 'UA-106291039-1', router });

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
})
