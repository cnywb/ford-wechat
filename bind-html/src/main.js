// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Vuex from 'vuex'
import Resouce from "vue-resource";
import routers from './router'
import 'mint-ui/lib/style.css'
import VueAnalytics from 'vue-analytics'

import Raven from 'raven-js';
import RavenVue from 'raven-js/plugins/vue';

Raven
  .config(process.env.RAVEN_KEY)
  .addPlugin(RavenVue, Vue)
  .install();

// console.log(wx)

Vue.use(Vuex);
Vue.use(Resouce);

Vue.use(VueAnalytics, {
  id: 'UA-100393481-1',
  routers
});

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router:routers,
  template: '<App/>',
  components: { App }
})
