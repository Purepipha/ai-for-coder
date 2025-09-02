<script setup lang="ts">
// 主页组件
</script>

<template>
  <div class="home">
    <div class="hero-section">
      <h1 class="hero-title">AI for coder</h1>
      <p class="hero-description">智能编程助手，让编程更高效</p>
      <div class="hero-actions">
        <a-button type="primary" size="large">开始使用</a-button>
        <a-button size="large" style="margin-left: 16px;">了解更多</a-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home {
  min-height: calc(100vh - 128px);
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.hero-section {
  text-align: center;
  max-width: 800px;
  padding: 60px 20px;
}

.hero-title {
  font-size: 3.5rem;
  font-weight: 700;
  margin-bottom: 24px;
  background: linear-gradient(135deg, #fff 0%, #f0f8ff 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.hero-description {
  font-size: 1.25rem;
  margin-bottom: 40px;
  opacity: 0.9;
  line-height: 1.6;
}

.hero-actions {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  gap: 16px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hero-title {
    font-size: 2.5rem;
  }

  .hero-description {
    font-size: 1.1rem;
  }

  .hero-actions {
    flex-direction: column;
  }

  .hero-actions :deep(.ant-btn) {
    width: 200px;
    margin-left: 0 !important;
  }
}

@media (max-width: 480px) {
  .hero-section {
    padding: 40px 16px;
  }

  .hero-title {
    font-size: 2rem;
  }

  .hero-description {
    font-size: 1rem;
  }
}
</style>
