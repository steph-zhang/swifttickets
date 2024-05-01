<script setup>
import { ref, watch, onMounted, reactive } from 'vue'
import { Layout, ConfigProvider, Space } from 'ant-design-vue'
import { useRoute } from 'vue-router'

import Header from './components/header/index.vue'
import BreadHeader from '@/components/bread-header/index.vue'
import Sider from './components/sider/index.vue'
import zhCN from 'ant-design-vue/es/locale/zh_CN'
import jsCookie from 'js-cookie'
import dayjs from 'dayjs'
import duration from 'dayjs/plugin/duration'
import axios from './services/axios'


dayjs.extend(duration)
const state = reactive()
const { Content } = Layout

const isLogin = ref(false)
const route = useRoute()

onMounted(() => {
  const token = jsCookie.get('token')
  if (token) {
    axios.defaults.headers.common['Authorization'] = token
  }
  window.addEventListener('hashchange', () => {
    console.log(location.pathname, 'change')
  })
})

watch(
  () => location.pathname,
  (newValue) => {
    console.log('newValue:::', newValue)
  }
)

watch(
  () => route.path,
  (newRoute) => {
    if (newRoute === '/login') {
      isLogin.value = true
    } else {
      isLogin.value = false
    }
  }
)

</script>

<template>
  <ConfigProvider :locale="zhCN">
    <Layout>
      <Header :isLogin="isLogin" />
      <Layout class="page-wrapper" :class="{ isLogin }">
        <Sider v-if="!isLogin" />
        <Content class="app-wrapper" :class="{ isLogin }">
          <ConfigProvider :locale="zhCN">
            <BreadHeader v-if="!isLogin" />
            <router-view /> 
          </ConfigProvider>
        </Content>
      </Layout>
    </Layout>
  </ConfigProvider>
</template>

<style lang="scss" scoped>
.page-wrapper {
  box-sizing: border-box;
  padding-top: 64px;
  /* min-width: 1230px; */
  .app-wrapper {
    box-sizing: border-box;
    margin: 20px;
    transition: all 0.2s;
  }
}

.isLogin.page-wrapper {
  padding: 0;
}
.ant-layout-content {
  min-height: calc(100vh - 64px);
}
.isLogin.ant-layout-content {
  height: 100vh;
  margin: 0;
}
:deep(.ant-layout-content){
  transition: all 0.2s;
}
</style>
