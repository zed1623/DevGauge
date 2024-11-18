<template>
  <div>
    <!-- 在这里添加你的模板内容 -->
    <h1>{{ message }}</h1>
  </div>
</template>

<script setup>
import { ref } from "vue";

// 定义响应式数据
const message = ref("用户排行页面");

// 在这里编写你的 Composition API 代码
</script>

<style scoped>
/* 在这里添加你的样式 */
h1 {
  color: #42b983;
}
</style>