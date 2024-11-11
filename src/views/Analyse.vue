<template>
  <div class="project-stats-page">
    <!-- 项目基本信息区 -->
    <el-card class="project-info" shadow="hover">
      <h2>项目基本信息</h2>
      <p>项目名称: {{ data?.name }}</p>
      <p>项目创建者: {{ data?.ownerLogin }}</p>
      <p>Stars: {{ data?.stars }}</p>
      <p>Forks: {{ data?.forks }}</p>
      <p>Issues: {{ data?.issues }}</p>
      <p>项目介绍: {{ data?.description }}</p>
    </el-card>
    <!-- <button @click="show">1</button> -->
    <!-- 统计指标概览区 -->
    <el-card class="overview-stats" shadow="hover">
      <h2>统计指标概览</h2>
      <div class="flex">
        <div class="stat-card">总贡献者数: {{ data?.projectUser }}</div>
        <div class="stat-card">总提交次数: {{ data?.totalCommits }}</div>
        <div class="stat-card">代码总行数: {{ data?.projectCode }}</div>
        <div class="stat-card">项目创建时间: {{ data?.createdAt }}</div>
      </div>
    </el-card>

    <!-- 贡献者活跃度分析 -->
    <el-card class="contributor-activity" shadow="hover">
      <h2>ai分析结果</h2>
      <p>{{ formattedResult }}</p>
    </el-card>

    <!-- <el-card class="code-changes" shadow="hover">
      <h2>代码变更统计</h2>
      <p>图表：代码行数变化（增减行数）</p>
      <p>主要变更文件</p>
    </el-card>


    <el-card class="code-quality" shadow="hover">
      <h2>代码质量分析</h2>
      <p>图表：Issue 处理情况 & Pull Request 合并情况</p>
    </el-card>


    <el-card class="interactive-tools" shadow="hover">
      <div class="button-group">
        <el-button>时间筛选</el-button>
        <el-button>贡献者筛选</el-button>
        <el-button>导出数据</el-button>
        <el-button>切换图表</el-button>
      </div>
    </el-card> -->
  </div>
</template>

<script setup>
import { ref, watchEffect, onMounted, computed } from "vue";
import { useDataStore } from "@/stores/dataStore";
import { aiUrl } from "@/api/ai/ai";
const dataStore = useDataStore();
const data = ref({});

watchEffect(() => {
  data.value = dataStore.projectData.data;
});

let result = ref("");

const show = () => {
  console.log("projectData999", result.value); // 使用 result.value
};

onMounted(async () => {
  result.value = await aiUrl(); // 等待异步请求完成后再赋值
});
const formattedResult = computed(() =>
  result.value?.data?.data.replace(/^【+|】+$/g, "")
);
</script>

<style scoped lang="scss">
.project-stats-page {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
  font-family: Arial, sans-serif;

  .el-card {
    padding: 20px;
    border-radius: 8px; // 调整圆角
  }

  /* 项目基本信息区标题 */
  .project-info h2,
  .contributor-activity h2,
  .code-changes h2,
  .code-quality h2 {
    font-size: 1.2em;
    color: #333;
    margin: 0 0 10px;
  }

  /* 统计指标概览区，保持原有的 flex 布局 */
  .overview-stats {
    text-align: center;

    .flex {
      display: flex;
      gap: 10px;
    }

    .stat-card {
      flex: 1;
      background-color: #e0f7fa;
      padding: 20px;
      border-radius: 5px;
      font-weight: bold;
      box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.1);
    }
  }

  /* 交互功能按钮 */
  .interactive-tools {
    display: flex;
    gap: 10px;
    justify-content: space-around;

    .el-button {
      padding: 10px 15px;
      font-size: 0.9em;
    }
  }
}
</style>