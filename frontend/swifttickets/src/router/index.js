import { createRouter, createWebHistory } from 'vue-router'
import { message } from 'ant-design-vue'
import Login from '../views/login/index.vue'
import TicketSearch from '../views/ticketsearch/index.vue'
import Userinfo from '../views/user-info/index.vue'
import PersonalTicket from '../views/personalTicket/index.vue'
import BuyTicket from '../views/buy-ticket/index.vue'

import Cookies from 'js-cookie'
const routes = [
  {
    path: '/',
    name: 'index',
    meta: { requiresAuth: false },
    redirect: (to) => {
      // 该函数接收目标路由作为参数
      // 相对位置不以`/`开头
      // 或 { path: 'profile'}
      return '/ticketSearch'
    }
  },
  {
    label: '车票查询',
    path: '/ticketSearch',
    name: 'ticketSearch',
    component: TicketSearch,
    icon: 'icon-chaxun',
    meta: { requiresAuth: false }
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
    meta: { requiresAuth: false }
  },
  {
    label: '用户信息',
    path: '/userInfo',
    name: 'userInfo',
    component: Userinfo,
    icon: 'icon-ic_account',
    meta: { requiresAuth: true }
  },
  {
    label: '本人车票',
    path: '/personalTicket',
    name: 'personalTicket',
    component: PersonalTicket,
    icon: 'icon-dingdan',
    meta: { requiresAuth: false }
  },
  {
    label: '购买车票',
    path: '/buyTicket',
    name: 'buyTicket',
    component: BuyTicket,
    icon: 'icon-goumai',
    meta: { requiresAuth: true }
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to, from) => {
  // console.log(Cookies.get('username'))
  // console.log(to, 'to')
  // console.log(from, 'from')
  if (
    to.meta?.requiresAuth &&
    to.name !== 'login' &&
    (!Cookies.get('username') || !Cookies.get('token'))
  ) {
    message.error('用户未登录或已过期！')
    return {
      name: 'login'
    }
  }
})

export { routes }

export default router
