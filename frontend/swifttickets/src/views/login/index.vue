<script setup>
import {
  Form,
  FormItem,
  Input,
  InputPassword,
  Button,
  message,
  Select,
  Modal
} from 'ant-design-vue'
import { reactive, ref, unref } from 'vue'
import { fetchLogin, fetchRegister, fetchSendCode } from '../../services'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { useRouter } from 'vue-router'
import Cookies from 'js-cookie'
const useForm = Form.useForm

const formState = reactive({
  phone: '17343478777',
  code: ''
})

const state = reactive({
  open: false
})

const rulesRef = reactive({
  usernameOrMailOrPhone: [
    {
      required: true,
      message: '请输入用户名或者邮件或者手机号'
    }
  ],
  password: [
    {
      required: true,
      message: '请输入密码'
    }
  ]
})

const { validate, validateInfos } = useForm(formState, rulesRef)

const registerForm = reactive({
  username: '',
  password: '',
  realName: '',
  idType: 0,
  idCard: '',
  phone: '',
  mail: ''
})

const registerRules = reactive({
  username: [
    {
      required: true,
      message: '请输入用户名'
    }
  ],
  password: [
    {
      required: true,
      message: '请输入密码'
    }
  ],
  realName: [
    {
      required: true,
      message: '请输入姓名'
    }
  ],
  idType: [
    {
      required: true,
      message: '请选择证件类型'
    }
  ],
  idCard: [
    {
      required: true,
      message: '请输入证件号码'
    }
  ],
  phone: [
    {
      required: true,
      message: '请输入电话号码'
    }
  ],
  mail: [
    {
      required: true,
      message: '请输入邮箱'
    }
  ]
})

const { validate: registerValidate, validateInfos: registerValidateInfos } =
  useForm(registerForm, registerRules)

let currentAction = ref('login')

const router = useRouter()

let usePasswd = ref(false)

const sendCode = () => {
  console.log("sendCode")
  fetchSendCode(formState.phone)
  .then((res) => {
      if (!res) {
        message.error(res.message)
      }
    })
}


const handleLogin = () => {
  if (!formState.code) return message.error('请输入验证码')
  validate()
    .then(() => {
      fetchLogin({
        ...formState
      }).then((res) => {
        console.log(res.data?.token)
        console.log(res.data?.username)
        console.log(res.data?.phone)
        if (res.success) {
          Cookies.set('token', res.data?.token)
          Cookies.set('username', res.data?.username)
          Cookies.set('phone', res.data?.phone)
          router.push('/ticketSearch')
        } else {
          message.error(res.message)
        }
      })
  })
}


const registerSubmit = () => {
  registerValidate()
    .then(() => {
      fetchRegister(registerForm).then((res) => {
        if (res.success) {
          message.success('注册成功')
          currentAction.value = 'login'
          formState.phone = res.data?.username
          // formState.password = ''
        } else {
          message.error(res.message)
        }
      })
    })
    .catch((err) => console.log(err))
}
</script>
<template>
  <div class="login-wrapper">
    <div class="title-wrapper"></div>
    <div class="login-reg-panel">
      <div class="login-info-box">
        <h2>已有账号？</h2>
        <h3>欢迎登录账号！</h3>
        <button @click="() => (currentAction = 'login')">去登录</button>
      </div>
      <div class="register-info-box">
        <h2>没有账号？</h2>
        <h3>欢迎注册账号！</h3>
        <button @click="() => (currentAction = 'register')">去注册</button>
      </div>
      <div
        class="white-panel"
        :class="{ 'white-panel-left': currentAction === 'register' }"
      >
        <div class="login-show" v-if="currentAction === 'login'">
          <h1 class="title">登录</h1>
          <Form name="basic" autocomplete="off">
            <FormItem v-bind="validateInfos.phone">
              <Input
                size="large"
                v-model:value="formState.phone"
                placeholder="用户名"
              >
                <template #prefix
                  ><UserOutlined style="color: rgba(0, 0, 0, 0.25)" /></template
              ></Input>
            </FormItem>

            <FormItem v-if="usePasswd" v-bind="validateInfos.password">
              <InputPassword
                size="large"
                placeholder="密码"
              >
                <template #prefix
                  ><LockOutlined style="color: rgba(0, 0, 0, 0.25)"
                /></template>
              </InputPassword>
            </FormItem>
            <FormItem v-else v-bind="validateInfos.code">
              <Input
                size="large"
                style="width: 75%"
                v-model:value="formState.code"
                placeholder="验证码"
              >
                <template #prefix
                  ><LockOutlined style="color: rgba(0, 0, 0, 0.25)"
                /></template>
                
              </Input>
              <Button style="width: 25%" @click="sendCode" type="success" :disabled="disabled">发送</Button>
            </FormItem>
            <FormItem>
              <div class="action-btn">
                <Button 
                  type="primary"
                  :style="{ backgroundColor: '#123456', border: 'none' }"
                  @click="message.info('暂不支持使用密码登录')">
                  <div v-show="usePasswd">使用验证码登录</div>
                  <div v-show="!usePasswd">使用密码登录</div>
                </Button>
                <Button
                  type="primary"
                  :style="{ backgroundColor: '#202020', border: 'none' }"
                  @click="handleLogin">
                  登录
                </Button>
              </div>
            </FormItem>
          </Form>
        </div>
        <div class="register-show" v-else>
          <h1 class="title">注册</h1>
          <Form name="basic" autocomplete="off" :label-col="{ span: 6 }">
            <FormItem label="用户名" v-bind="registerValidateInfos.username">
              <Input
                v-model:value="registerForm.username"
                placeholder="请输入用户名"
              >
              </Input>
            </FormItem>
            <FormItem label="密码" v-bind="registerValidateInfos.password">
              <InputPassword
                v-model:value="registerForm.password"
                placeholder="密码"
              >
              </InputPassword>
            </FormItem>

            <FormItem label="姓名" v-bind="registerValidateInfos.realName">
              <Input
                v-model:value="registerForm.realName"
                placeholder="请输入姓名"
              >
              </Input>
            </FormItem>
            <FormItem label="证件类型" v-bind="registerValidateInfos.idType">
              <Select
                :options="[{ value: 0, label: '中国居民身份证' }]"
                v-model:value="registerForm.idType"
                placeholder="请选择证件类型"
              ></Select>
            </FormItem>
            <FormItem label="证件号码" v-bind="registerValidateInfos.idCard">
              <Input
                v-model:value="registerForm.idCard"
                placeholder="请输入证件号码"
              >
              </Input>
            </FormItem>
            <FormItem label="手机号码" v-bind="registerValidateInfos.phone">
              <Input
                v-model:value="registerForm.phone"
                placeholder="请输入手机号码"
              >
              </Input>
            </FormItem>
            <FormItem label="邮件" v-bind="registerValidateInfos.mail">
              <Input
                v-model:value="registerForm.mail"
                placeholder="请输入邮箱账号"
              >
              </Input>
            </FormItem>
            <FormItem>
              <div class="action-btn">
                <a></a>
                <Button
                  type="primary"
                  @click="registerSubmit"
                  :style="{ backgroundColor: '#202020', border: 'none' }"
                  >注册</Button
                >
              </div>
            </FormItem>
          </Form>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-wrapper {
  width: 100%;
  height: 100%;
  // background-color: #fff;
  background: url('src/assets/black_dot.png');
  background-clip: border-box;
  .login-reg-panel {
    position: relative;
    top: 50%;
    transform: translateY(-50%);
    text-align: center;
    width: 40%;
    right: 0;
    left: 20%;
    margin: auto;
    min-width: 800px;
    height: 600px;
    background-color: rgba(30, 30, 30, 0.9);
    .white-panel {
      background-color: rgba(255, 255, 255, 1);
      height: 600px;
      position: absolute;
      width: 50%;
      right: calc(50% - 50px);
      transition: 0.3s ease-in-out;
      z-index: 0;
      box-sizing: border-box;
      .login-show,
      .register-show {
        height: 100%;
        display: flex;
        flex-direction: column;
        // justify-content: space-around;
        transition: 0.3s ease-in-out;
        color: #242424;
        text-align: left;
        padding: 30px;
        .title {
          font-size: 24px;
          font-weight: bolder;
          padding: 20px 0;
          color: #202020;
        }
        .action-btn {
          display: flex;
          width: 100%;
          a {
            display: block;
            line-height: 32px;
          }
          justify-content: space-between;
        }
      }
    }
    .white-panel-left {
      transition: 0.3s ease-in-out;
      right: calc(0px + 50px);
    }
    .login-info-box {
      display: flex;
      flex-direction: column;
      width: 30%;
      padding: 0 50px;
      top: 20%;
      left: 0;
      position: absolute;
      text-align: left;
      justify-content: center;
      font-family: 'Mukta', sans-serif;
      color: #b8b8b8;
      h2 {
        font-size: 24px;
        color: #b8b8b8;
        font-weight: bolder;
        font-weight: bolder;
        margin-bottom: 40px;
      }
      h3 {
        font-size: 20px;
        color: #b8b8b8;
        margin-bottom: 40px;
      }
      button {
        cursor: pointer;
        width: 100%;
        background-color: transparent;
        box-shadow: none;
        border: 1px solid #b8b8b8;
        border-radius: 2px;
        height: 25px;
      }
    }
    .register-info-box {
      width: 30%;
      padding: 0 50px;
      top: 20%;
      right: 0;
      position: absolute;
      text-align: left;
      font-family: 'Mukta', sans-serif;
      color: #b8b8b8;
      h2 {
        font-size: 24px;
        color: #b8b8b8;
        font-weight: bolder;
        font-weight: bolder;
        margin-bottom: 40px;
      }
      h3 {
        font-size: 20px;
        color: #b8b8b8;
        margin-bottom: 40px;
      }
      button {
        cursor: pointer;
        width: 100%;
        background-color: transparent;
        box-shadow: none;
        border: 1px solid #b8b8b8;
        border-radius: 2px;
        height: 25px;
      }
    }
  }
}
.code-modal {
  .wrapper {
    text-align: center;
    .tip-text {
      width: 100%;
      text-align: center;
      font-size: 14px;
      color: red;
    }
    .code-input {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      .code-label {
        margin: 10px;
        &::before {
          content: '*';
          color: red;
        }
      }
    }
  }
}
:deep(.ant-modal-header) {
  background-color: #3b3b3b !important;
}
:deep(.ant-form-item-label) {
  label {
    color: #202020;
  }
}
</style>
