<!-- AnalysisCard.vue -->
<template>
  <div class="card" :style="{ backgroundColor: color }">
    <h3>{{ title }}</h3>
    <p>{{ description }}</p>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true,
    },
    description: {
      type: String,
      required: true,
    },
    color: {
      type: String,
      default: "#ffffff", // 设置默认背景颜色
    },
  },
};
</script>
