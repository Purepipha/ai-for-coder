<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalFooter from '@/components/GlobalFooter.vue'
import { RouterView } from 'vue-router'

// 菜单配置
const menuItems = [
  { key: 'home', label: '主页', path: '/' },
  { key: 'about', label: '关于', path: '/about' },
]
</script>

<template>
  <a-layout class="basic-layout">
    <!-- 头部导航 -->
    <GlobalHeader :menu-items="menuItems" />

    <!-- 内容区域 -->
    <a-layout-content class="main-content">
      <div class="content-wrapper">
        <RouterView />
      </div>
    </a-layout-content>

    <!-- 底部版权 -->
    <GlobalFooter />
  </a-layout>
</template>

<style scoped>
.basic-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1;
  background: #f0f2f5;
  padding-bottom: 60px; /* 为固定底部留出空间 */
}

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .content-wrapper {
    padding: 16px;
  }
}
</style>

