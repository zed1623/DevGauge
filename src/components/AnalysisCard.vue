<template>
  <div
    class="card"
    :style="{ background: `linear-gradient(90deg, ${colorFrom}, ${colorTo})` }"
    @click="navigate"
  >
    <font-awesome-icon :icon="icon" class="card-icon" />
    <h3>{{ title }}</h3>
    <p>{{ description }}</p>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router"; // 引入路由功能

const props = defineProps({
  title: String,
  description: String,
  colorFrom: String, // 渐变起始颜色
  colorTo: String, // 渐变结束颜色
  icon: Object, // 图标是一个对象
  route: String, // 跳转路径
});

const router = useRouter();

const navigate = () => {
  if (props.route) {
    router.push(props.route); // 使用 Vue Router 跳转
  }
};
</script>

<style scoped>
.card {
  cursor: pointer; /* 添加鼠标指针样式，表示可点击 */
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}
.card:hover {
  transform: translateY(-5px); /* 添加悬停效果 */
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.15);
}

.card-icon {
  font-size: 3em; /* 调整图标的大小，确保它在卡片中可见 */
  color: #333;
  margin-bottom: 15px;
}
</style>
