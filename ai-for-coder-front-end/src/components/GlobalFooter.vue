<script setup lang="ts">
// 版权信息配置
const copyright = 'AI for coder by 木子'
</script>

<template>
  <a-layout-footer class="global-footer">
    <div class="footer-content">
      <span class="copyright">{{ copyright }}</span>
    </div>
  </a-layout-footer>
</template>

<style scoped>
.global-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 99;
  background: #fff;
  border-top: 1px solid #f0f0f0;
  padding: 16px 0;
  text-align: center;
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.06);
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
}

.copyright {
  color: rgba(0, 0, 0, 0.65);
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .footer-content {
    padding: 0 16px;
  }

  .copyright {
    font-size: 12px;
  }
}
</style>
