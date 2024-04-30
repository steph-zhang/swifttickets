import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Antd from 'ant-design-vue'
import dayjs from 'dayjs'
import 'ant-design-vue/dist/antd.less'
import './global.less'
import 'dayjs/locale/zh-cn'
dayjs.locale('zh-cn')

const app = createApp(App)
app.use(router).use(Antd).mount('#app')