import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Mobile from '@/views/Mobile'
import License from '@/views/License'
import Hint from '@/views/Hint'
import Subscribe from '@/views/Subscribe'
import Question from '@/views/Question'
import Service from '@/views/Service'
import Cars from '@/views/Cars'
import Problem from '@/views/Problem'
import NotFound from '@/views/404'


Vue.use(Router)

export default new Router({
  // mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/cars/:status',
      component: Cars,
      name: '绑定车辆列表'
    },
    {
      path: '/mobile',
      name: '验证手机号',
      component: Mobile
    },
    {
      path: '/license',
      name: 'VIN校验',
      component: License
    },
    {
      path: '/hint/:business/:status',
      component: Hint,
      name: '结果提示'
    },
    {
      path: '/problem',
      component: Problem,
      name: '认证用户-认证问题'
    },
    {
      path: '/service',
      component: Service,
      name: '非认证用户-认证问题'
    },
    {
      path: '/subscribe',
      component: Subscribe,
      name: '关注二维码'
    },
    {
      path: '/question',
      component: Question,
      name: '认证教程'
    },

    {
      path: '/*',
      name:"404",
      component: NotFound
    }
  ]
})
