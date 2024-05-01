<script setup>
import {
  Card,
  Form,
  FormItem,
  TypographyTitle,
  Input,
  Button,
  Divider,
  Row,
  Col,
  Select,
  SelectOption,
  message
} from 'ant-design-vue'
import { reactive, onMounted } from 'vue'
import jsCookie from 'js-cookie'
import { fetchUserInfo } from '@/services'
import { REGIN_MAP, CHECK_STATUS, DISCOUNTS_TYPE } from '@/constants'
const useForm = Form.useForm

const state = reactive({
  userInfoMap: [
    { label: '姓名', value: '--', key: 'username' },
    { label: '证件号码', value: '--', key: 'id' },
    { label: '手机号', value: '--', key: 'phone'},
  ],
})

const phone = jsCookie.get('phone')

onMounted(() => {
  fetchUserInfo({ phone }).then((res) => {
    const { userInfoMap } = state
    if (res.success) {
      state.userInfoMap = userInfoMap.map((item) => ({
        ...item,
        value: res.data[item.key]
      }))
      state.userType = res.data?.userType
    }
  })
})

</script>

<template>
  <Card>
    <TypographyTitle :level="5">基本信息</TypographyTitle>
    <Row v-for="info in state.userInfoMap" class="info-wrapper">
      <Col :span="8" :style="{ textAlign: 'end' }">
        <label class="info-label">
          {{ info.label }}：
        </label>
      </Col>
      <Col span="4" :style="{ textAlign: 'start' }">
        <span class="info-value" :style="{ color: info.color }">
        {{ info?.render ? info.render(info.value) : info.value ?? '--' }}
        </span>
      </Col>
    </Row>
  </Card>
</template>

<style lang="scss" scoped>
.info-wrapper {
  width: 100%;
  text-align: center;
  /* // padding: 10px; */
  margin-bottom: 24px;
  height: 32px;
  line-height: 32px;
  .info-label {
    display: inline-block;
    color: #787878;
  }
  .info-require::before {
    content: '* ';
    color: red;
  }
  .info-value {
    display: inline-block;
    padding: 0 10px;
    font-weight: bolder;
  }
}

.title-wrapper {
  display: flex;
  justify-content: space-between;
}
::v-deep {
  .ant-form-item-label {
    label {
      color: #787878;
    }
  }
}
</style>
