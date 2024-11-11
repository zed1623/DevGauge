<template>
  <div class="list-container">
    <div
      v-for="(item, index) in list"
      :key="index"
      class="list-item"
      :class="{ selected: selectedRow === index }"
      @click="selectRow(index)"
    >
      <img :src="item.avatarUrl" alt="avatar" class="avatar" />
      <span>{{ item.name }}</span>
    </div>
  </div>
  <div class="contributor-details-page">
    <!-- 个人信息区 -->
    <el-card class="personal-info" shadow="hover">
      <img :src="selectedContributor.avatarUrl" alt="头像" class="avatar" />
      <h2>{{ selectedContributor.name }}</h2>
      <p>{{ selectedContributor.bio }}</p>
      <p>
        {{ selectedContributor.nation }} | {{ selectedContributor.field }} |
        加入时间: {{ formatDate(selectedContributor.createdAt) }}
      </p>
    </el-card>

    <!-- 贡献统计概览 -->
    <el-card class="stats-overview" shadow="hover">
      <div class="flex">
        <div class="stat-card">
          总提交次数: {{ selectedContributor.number }}
        </div>
        <div class="stat-card">
          代码行数增量: {{ selectedContributor.totalAdditions }}
        </div>
        <div class="stat-card">
          代码行数减量: {{ selectedContributor.totalDeletions }}
        </div>
      </div>
    </el-card>

    <!-- 提交记录列表 -->

    <el-card class="code-change-analysis" shadow="hover">
      <h2>代码变更分析</h2>
      <div ref="chartRef" style="width: 100%; height: 400px"></div>
    </el-card>

    <!-- <el-card class="interactive-tools" shadow="hover">
      <div class="button-group">
        <el-button type="primary">日期筛选</el-button>
        <el-button type="primary">文件类型筛选</el-button>
        <el-button type="primary">导出数据</el-button>
      </div>
    </el-card> -->
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from "vue";
import axios from "axios";
import qs from "qs";
import * as echarts from "echarts";

import { useRepoStore } from "@/stores/repoStore";

const store = useRepoStore();
const repoUrl = computed(() => store.repoUrl);
const requestData = { url: repoUrl.value };

const list = ref([]);
const selectedRow = ref(0);
const selectedContributor = ref({});
const chartRef = ref(null);
let chartInstance = null;

// 初始化和更新图表
function initChart() {
  if (chartInstance) {
    chartInstance.dispose();
  }
  chartInstance = echarts.init(chartRef.value);

  const option = {
    title: {
      text: "代码增删行数对比",
      left: "center",
      textStyle: {
        color: "#333",
        fontSize: 18,
        fontWeight: "bold",
      },
    },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow",
      },
    },
    grid: {
      left: "10%",
      right: "10%",
      bottom: "10%",
      containLabel: true,
    },
    xAxis: {
      type: "category",
      data: ["增加行数", "删除行数"],
      axisLabel: {
        color: "#666",
        fontSize: 14,
      },
      axisLine: {
        lineStyle: {
          color: "#ddd",
        },
      },
    },
    yAxis: {
      type: "value",
      axisLabel: {
        color: "#666",
        fontSize: 14,
      },
      splitLine: {
        lineStyle: {
          type: "dashed",
          color: "#eee",
        },
      },
      axisLine: {
        show: false,
      },
    },
    series: [
      {
        name: "代码行数",
        type: "bar",
        barWidth: "40%",
        data: [
          {
            value: selectedContributor.value.totalAdditions || 0,
            itemStyle: { color: "rgba(0, 176, 255, 0.8)" },
          },
          {
            value: selectedContributor.value.totalDeletions || 0,
            itemStyle: { color: "rgba(255, 99, 132, 0.8)" },
          },
        ],
        itemStyle: {
          color: (params) => params.data.itemStyle.color,
          borderRadius: [4, 4, 0, 0], // 圆角柱状图
          shadowColor: "rgba(0, 0, 0, 0.1)",
          shadowBlur: 10,
        },
        label: {
          show: true,
          position: "top",
          fontSize: 14,
          color: "#333",
          fontWeight: "bold",
        },
        emphasis: {
          itemStyle: {
            color: (params) => params.data.itemStyle.color,
            shadowBlur: 20,
            shadowColor: "rgba(0, 0, 0, 0.3)",
          },
        },
      },
    ],
  };

  chartInstance.setOption(option);
}

// 异步获取开发者列表并赋值给 list
async function fetchDevelopers() {
  try {
    const url = "http://47.113.195.131:8090/developer/getUrlDeveloper";
    const params = requestData;
    const response = await axios.post(
      `${url}?${qs.stringify(params, { encode: false })}`
    );

    list.value = response.data.data;
    if (list.value.length > 0) {
      selectedContributor.value = list.value[0];
      initChart();
    }
  } catch (error) {
    console.error("获取用户信息失败:", error);
  }
}

// 选择行的方法
function selectRow(index) {
  selectedRow.value = index;
  selectedContributor.value = list.value[index];
  initChart();
}

// 监视 selectedContributor 变化以更新图表
watch(selectedContributor, initChart);

// 格式化日期函数
function formatDate(dateString) {
  const options = { year: "numeric", month: "2-digit", day: "2-digit" };
  return new Date(dateString).toLocaleDateString(undefined, options);
}

// 在组件挂载时调用 fetchDevelopers 以获取并设置列表数据
onMounted(() => {
  fetchDevelopers();
});
</script>

<style scoped lang="scss">
.contributor-details-page {
  position: relative;
  margin-left: 20vw;
  display: flex;
  flex-direction: column;
  gap: 20px;
  max-width: 900px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
  z-index: 20;
  .el-card {
    padding: 15px;
    border-radius: 8px;
  }

  .personal-info {
    display: flex;
    align-items: center;
    gap: 15px;

    .avatar {
      width: 60px;
      height: 60px;
      border-radius: 50%;
    }
  }

  .stats-overview {
    .flex {
      display: flex;
      gap: 10px;
    }
    .stat-card {
      flex: 1;
      background-color: #e0f7fa;
      padding: 10px;
      text-align: center;
      border-radius: 5px;
      font-weight: bold;
    }
  }

  .commit-records,
  .activity-timeline,
  .code-change-analysis {
    background-color: #f7f7f7;
    padding: 15px;
    border-radius: 5px;

    h2 {
      font-size: 1.2em;
      color: #333;
    }
  }

  .interactive-tools {
    display: flex;
    gap: 10px;
    justify-content: flex-start;

    .el-button {
      padding: 8px 15px;
    }
  }
}
.list-container {
  width: 20vw;
  position: fixed;
  top: 160px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  z-index: 10;
  .list-item {
    user-select: none;
    height: 15px;
    display: flex;
    align-items: center;
    padding: 10px;
    cursor: pointer;
    border: 1px solid #ddd;
    border-radius: 5px;
    transition: background-color 0.3s;

    background-color: white;
    color: black;

    &.selected {
      background-color: rgb(140, 134, 134);
      color: rgb(250, 246, 246);
      font-weight: 600;
    }

    .avatar {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      margin-right: 10px;
    }
  }
}
</style>