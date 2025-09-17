<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { userLogin } from '@/api/userController.ts'
import { useLoginUserStore } from '@/stores/loginUser.ts'

const router = useRouter()
const loginUserStore = useLoginUserStore()

// 表单数据
const formState = reactive<API.UserLoginRequest>({
  userAccount: '',
  userPassword: '',
})


// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名或邮箱', trigger: 'blur' },
    { min: 3, message: '用户名至少3个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 8, message: '密码至少8个字符', trigger: 'blur' },
  ],
}

// 加载状态
const loading = ref(false)

// 登录处理
const handleLogin = async (values: any) => {
  try {
    const res = await userLogin(values)
    if (res.data.code === 200 && res.data.data) {
      await loginUserStore.fetchLoginUser()
      message.success("登录成功")
      router.push({
        path: '/',
        replace: true,
      })
    } else {
      message.error('登录失败，' + res.data.message)
    }
  } catch (error : any) {
    message.error('登录失败，' + (error.message || '未知错误'))
  }

}

// 跳转到注册页面
const goToRegister = () => {
  // TODO: 添加注册页面路由
  message.info('注册功能开发中')
}
</script>
<template>
  <div id="userLoginPage">
    <h2 class="title">木子AIForCoder - 用户登录</h2>
    <div class="desc">不写一行代码，生成完整应用</div>
    <a-form :model="formState" name="basic" autocomplete="off" @finish="handleLogin">
      <a-form-item name="userAccount" :rules=rules.username>
        <a-input v-model:value="formState.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item
        name="userPassword"
        :rules=rules.password
      >
        <a-input-password v-model:value="formState.userPassword" placeholder="请输入密码" />
      </a-form-item>
      <div class="tips">
        没有账号？
        <RouterLink to="/user/register">去注册</RouterLink>
      </div>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%">登录</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped>
#userLoginPage {
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
