import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
import VueResouce from "vue-resource";
import VueLocalStorage from 'vue-localstorage'
import routes from './routes'
import MintUI from 'mint-ui'
import 'mint-ui/lib/style.css'

/*错误捕获*/
import Raven from 'raven-js';
import RavenVue from 'raven-js/plugins/vue';

Raven
    .config('https://5bf79ea2ef484af68f84169fa9c333dc@sentry.io/206232')
    .addPlugin(RavenVue, Vue)
    .install();

Vue.use(MintUI)
Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(VueResouce);
Vue.use(VueLocalStorage);

const router = new VueRouter({
    routes
});

router.beforeEach((to, from, next) => {

    //校验需要用户登录才能访问的页面
    let user = sessionStorage.getItem('openId');
    if (!user && to.path != '/login'&& to.path != '/register') {
       window.location.href="/pc/static/loading.html?openId=oypfds5GCtlJf5QoDqShGYGjAyz4";
    } else {
        next()
    }
});

//router.afterEach(transition => {
//NProgress.done();
//});

new Vue({
    //el: '#app',
    //template: '<App/>',
    router,
    store,
    //components: { App }
    render: h => h(App)
}).$mount('#app')

