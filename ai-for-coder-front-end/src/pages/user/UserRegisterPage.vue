<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { userRegister } from '@/api/userController.ts'

const router = useRouter()

// 表单数据
const formState = reactive<API.UserRegisterRequest>({
  username: '',
  password: '',
  checkPassword: '',
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, message: '用户名至少3个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 8, message: '密码不能小于8位', trigger: 'blur' },
  ],
  checkPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    {
      validator: (rule: any, value: string, callback: any) => {
        if (value && value !== formState.password) {
          callback('两次输入的密码不一致')
        } else {
          callback()
        }
      },
      trigger: 'blur'
    },
  ],
}

// 加载状态
const loading = ref(false)

// 注册处理
const handleRegister = async (values: API.UserRegisterRequest) => {
  try {
    loading.value = true
    const res = await userRegister(values)
    if (res.data.code === 200 && res.data.data) {
      message.success('注册成功，请登录')
      // 注册成功后跳转到登录页面
      router.push({
        path: '/user/login',
        replace: true,
      })
    } else {
      message.error('注册失败，' + res.data.message)
    }
  } catch (error: any) {
    message.error('注册失败，' + (error.message || '未知错误'))
  } finally {
    loading.value = false
  }
}

// 跳转到登录页面
const goToLogin = () => {
  router.push('/user/login')
}
</script>

<template>
  <div id="userRegisterPage">
    <h2 class="title">木子AIForCoder - 用户注册</h2>
    <div class="desc">不写一行代码，生成完整应用</div>
    <a-form
      :model="formState"
      name="register"
      autocomplete="off"
      @finish="handleRegister"
      :loading="loading"
    >
      <a-form-item name="username" :rules=rules.username>
        <a-input v-model:value="formState.username" placeholder="请输入用户名" />
      </a-form-item>
      <a-form-item
        name="password"
        :rules=rules.password
      >
        <a-input-password v-model:value="formState.password" placeholder="请输入密码" />
      </a-form-item>
      <a-form-item
        name="checkPassword"
        :rules=rules.checkPassword
      >
        <a-input-password v-model:value="formState.checkPassword" placeholder="请再次输入密码" />
      </a-form-item>
      <div class="tips">
        已有账号？
        <RouterLink to="/user/login">去登录</RouterLink>
      </div>
      <a-form-item>
        <a-button
          type="primary"
          html-type="submit"
          style="width: 100%"
          :loading="loading"
        >
          注册
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped>
#userRegisterPage {
  max-width: 360px;
  margin: 0 auto;
}

.title {
  text-align: center;
  margin-bottom: 16px;
}

.desc {
  text-align: center;
  color: #bbb;
  margin-bottom: 16px;
}

.tips {
  margin-bottom: 16px;
  color: #bbb;
  font-size: 13px;
  text-align: right;
}
</style>
