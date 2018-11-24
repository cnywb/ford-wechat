import Index from './views/Index.vue'
import Information from './views/Information.vue'
import News from './views/News.vue'
import Quality from './views/Quality.vue'
import Rescue from './views/Rescue.vue'
import Upkeep from './views/Upkeep.vue'
import NotFound from './views/404.vue'
import MessageDetail from './views/MessageDetail.vue'
import FAQ from './views/FAQ.vue';
import AllMenu from './views/AllMenu.vue';
import MessageList from './views/MessageList.vue';
import Activity from './views/Activity.vue';
import Complain from './views/Complain.vue';

export default [
    {
        path: '/information',
        component: Information,
        name: '车主个人信息完善'
    },
    {
        path: '/news',
        component: News,
        name: '爱车宝典资讯'
    },
    {
        path: '/quality',
        component: Quality,
        name: '质保查询'
    },
    {
        path: '/rescue',
        component: Rescue,
        name: '道路紧急救援'
    },
    {
        path: '/upkeep',
        component: Upkeep,
        name: '车辆保养'
    },

    {
        path: '/',
        component: Index,
        name: '首页',
        iconCls: 'el-icon-message',//图标样式class
        children: []
    },
    {
        path: '/messageDetail/:id',
        name: "公告消息",
        component: MessageDetail
    },
    {
        path: '/faq',
        name: "互动问答",
        component: FAQ
    },
    {
        path: '/all',
        name: "所有菜单",
        component: AllMenu
    },
    {
        path: '/messageList',
        name: "消息列表",
        component: MessageList
    },
    {
        path: '/activity',
        name: "车主活动",
        component: Activity
    },
    {
        path: '/complain',
        name: "投诉建议",
        component: Complain
    },
    {
        path: '/*',
        name: "404",
        component: NotFound
    }
];
