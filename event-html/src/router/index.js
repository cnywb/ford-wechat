import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Coupon from '@/views/Coupon'
import NotRed from '@/views/NotRed'
import Rule from '@/views/Rule'
import Main from '@/views/Main'
import Winners from '@/views/Winners'
import SuccessRed from '@/views/SuccessRed'
import Subscribe from '@/views/Subscribe'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: '首页',
      component: Main
    },
    {
      path: '/home/:openId',
      name: 'Home',
      component: Home
    },
    {
      path: '/not',
      name: '未领到红包',
      component: NotRed
    },
    {
      path: '/rule',
      name: '活动规则',
      component: Rule
    },
    {
      path: '/winners',
      name: '获奖者',
      component: Winners
    },
    {
      path: '/coupon',
      name: '代金券',
      component: Coupon
    },
    {
      path: '/success',
      name: '成功领取',
      component: SuccessRed
    },
    {
      path: '/subscribe',
      component: Subscribe,
      name: '关注二维码'
    },
  ]
})
