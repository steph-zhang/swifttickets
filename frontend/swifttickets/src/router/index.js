import { createRouter, createWebHistory } from 'vue-router'
import { message } from 'ant-design-vue'

import TicketSearch from '../views/ticketsearch/index.vue'
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
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// router.beforeEach(async (to, from) => {
//   // console.log(Cookies.get('username'))
//   // console.log(to, 'to')
//   // console.log(from, 'from')
//   if (
//     to.meta?.requiresAuth &&
//     to.name !== 'login' &&
//     (!Cookies.get('username') || !Cookies.get('token'))
//   ) {
//     message.error('用户未登录或已过期！')
//     return {
//       name: 'login'
//     }
//   }
// })

export { routes }

export default router
