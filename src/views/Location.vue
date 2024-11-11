<template>
  <div class="contributor-map-page">
    <!-- 页面头部信息 -->
    <el-card class="page-header" shadow="hover">
      <h2>项目贡献者地理分布</h2>
      <p>项目名称: Example Project | Stars: 1234 | Forks: 567</p>
      <div class="filters">
        <label for="time-filter">按时间筛选</label>
        <select id="time-filter">
          <option>全部时间</option>
          <option>最近一个月</option>
          <option>最近一年</option>
        </select>
      </div>
    </el-card>
    <button @click="showAll">show</button>
    <!-- 地图展示区 -->
    <el-card class="map-display" shadow="hover">
      <div id="contributor-map">地图加载中...</div>
    </el-card>

    <!-- 统计概览区 -->
    <el-card class="stats-overview" shadow="hover">
      <div class="flex">
        <div class="stat-item">全球贡献者数: 200</div>
        <div class="stat-item">最多贡献国家: USA</div>
        <div class="stat-item">主要活跃时间: 每日 9AM - 6PM</div>
        <div class="stat-item">平均提交数: 5 次</div>
      </div>
    </el-card>

    <!-- 贡献者列表区 -->
    <el-card class="contributor-list" shadow="hover">
      <h3>贡献者列表</h3>
      <ul>
        <li>头像 - 用户名 - 国家 - 提交次数 - 增删代码行数</li>
        <!-- 其他贡献者列表项 -->
      </ul>
    </el-card>

    <!-- 交互工具 -->
    <el-card class="interactive-tools" shadow="hover">
      <div class="button-group">
        <el-button type="primary">数据导出</el-button>
        <el-button type="primary">切换图表</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { ElButton, ElCard } from "element-plus";
import { useDataStore } from "@/stores/dataStore";
const dataStore = useDataStore();
const showAll = () => {
  console.log("projectdata", dataStore.projectData);
  console.log("listdata", dataStore.listData);
};
</script>

<style scoped lang="scss">
.contributor-map-page {
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
  // max-width: 1000px;
  margin: 0 auto;
  font-family: Arial, sans-serif;

  .el-card {
    padding: 15px;
    border-radius: 8px;
    width: 80vw;
  }

  .page-header {
    display: flex;
    flex-direction: column;
    align-items: center;

    .filters {
      margin-top: 10px;

      label {
        margin-right: 5px;
      }

      select {
        padding: 5px;
      }
    }
  }

  .map-display {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f0f0f0;
    height: 400px;
    border-radius: 10px;

    #contributor-map {
      width: 100%;
      height: 100%;
    }
  }

  .stats-overview {
    .flex {
      display: flex;
      justify-content: space-around;
      gap: 10px;
    }
    .stat-item {
      flex: 1;
      background-color: #e0f7fa;
      padding: 10px;
      text-align: center;
      border-radius: 5px;
      font-weight: bold;
    }
  }

  .contributor-list {
    // background-color: #f7f7f7;
    padding: 15px;
    border-radius: 5px;

    h3 {
      margin-top: 0;
      color: #333;
    }

    ul {
      list-style: none;
      padding: 0;

      li {
        padding: 10px 0;
        border-bottom: 1px solid #ccc;
      }
    }
  }

  .interactive-tools {
    display: flex;
    gap: 10px;
    justify-content: center;

    .el-button {
      padding: 10px 15px;
    }
  }
}
</style>
