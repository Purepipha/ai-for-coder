<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import logoUrl from '@/assets/logo.svg'
import  { type MenuProps, message } from 'ant-design-vue'
import { useLoginUserStore } from '@/stores/loginUser.ts'
import { userLogout } from '@/api/userController.ts'

// 获取登录用户状态
const logUserStore = useLoginUserStore();

// 定义菜单项类型
interface MenuItem {
  key: string
  label: string
  path: string
}

// 组件属性
const props = withDefaults(defineProps<{
  menuItems?: MenuItem[]
  title?: string
}>(), {
  menuItems: () => [],
  title: 'AI for coder'
})

const route = useRoute()
const router = useRouter()

// 当前选中的菜单项
const selectedKeys = computed(() => {
  const currentItem = props.menuItems.find(item => item.path === route.path)
  return currentItem ? [currentItem.key] : []
})

// 菜单配置
const menuConfig = computed<MenuProps['items']>(() =>
  props.menuItems.map(item => ({
    key: item.key,
    label: item.label,
  }))
)

// 菜单点击处理
const handleMenuClick: MenuProps['onClick'] = ({ key }) => {
  const targetItem = props.menuItems.find(item => item.key === key)
  if (targetItem) {
    router.push(targetItem.path)
  }
}

// 登录按钮点击处理
const handleLogin = () => {
  console.log('点击登录')
  router.push('/user/login')
}

const doLogout = async () => {
  const res = await userLogout()
  if (res.data.code === 200) {
    logUserStore.setLoginUser({
      userName: '未登录',
    })
    message.success('退出登录成功')
    await router.push('/user/login')
  } else {
    message.error('退出登录失败，' + res.data.message)
  }
}
</script>

<template>
  <a-layout-header class="global-header">
    <div class="header-content">
      <!-- 左侧区域 -->
      <div class="header-left">
        <!-- Logo 和标题 -->
        <div class="logo-section">
          <img :src="logoUrl" alt="logo" class="logo" />
          <span class="title">{{ title }}</span>
        </div>

        <!-- 菜单 -->
        <div class="menu-section">
          <a-menu
            v-if="menuItems.length > 0"
            mode="horizontal"
            :selectedKeys="selectedKeys"
            :items="menuConfig"
            @click="handleMenuClick"
            class="header-menu"
          />
        </div>
      </div>

      <!-- 右侧区域 -->
      <a-col>
        <div class="header-right">
          <div v-if="logUserStore.loginUser.id">
            <a-dropdown>
              <a-space align="center" class="user-info">
                <a-avatar :src="logUserStore.loginUser.userAvatar"/>
                <span class="user-name">{{logUserStore.loginUser.userName ?? '无名'}}</span>
              </a-space>
              <template #overlay>
                <a-menu>
                  <a-menu-item key="profile" @click="doLogout">
                    <LogoutOutlined />
                    退出登录
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
          <div v-else>
            <a-button type="primary" @click="handleLogin">
              登录
            </a-button>
          </div>
        </div>
      </a-col>

    </div>
  </a-layout-header>
</template>

<style scoped>
.global-header {
  position: sticky;
  top: 0;
  z-index: 100;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  padding: 0;
  height: 64px;
  line-height: 64px;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}

.header-left {
  display: flex;
  align-items: center;
  flex: 1;
  min-width: 0;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-right: 32px;
}

.logo {
  width: 32px;
  height: 32px;
  vertical-align: middle;
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: #1890ff;
  white-space: nowrap;
}

.menu-section {
  flex: 1;
  min-width: 0;
}

:deep(.header-menu) {
  border-bottom: none !important;
  line-height: 64px;
}

:deep(.header-menu .ant-menu-item) {
  border-bottom: none !important;
  position: relative;
}

:deep(.header-menu .ant-menu-item::after) {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: 2px;
  background-color: #1890ff;
  transition: width 0.3s ease;
}

:deep(.header-menu .ant-menu-item-selected) {
  color: #1890ff !important;
  border-bottom: none !important;
}

:deep(.header-menu .ant-menu-item-selected::after) {
  width: calc(100% - 32px);
}

:deep(.header-menu .ant-menu-item:hover) {
  color: #1890ff !important;
}

:deep(.header-menu .ant-menu-item:hover::after) {
  width: calc(100% - 32px);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-name {
  display: inline-flex;
  align-items: center;
  line-height: 1;
}

/* 响应式设计 */
@media (max-width: 992px) {
  .header-content {
    padding: 0 16px;
  }

  .logo-section {
    margin-right: 16px;
  }
}

@media (max-width: 768px) {
  .title {
    display: none;
  }

  .logo-section {
    margin-right: 12px;
  }

  :deep(.header-menu .ant-menu-item) {
    padding: 0 8px !important;
  }
}

@media (max-width: 576px) {
  .header-content {
    padding: 0 12px;
  }
}
</style>

