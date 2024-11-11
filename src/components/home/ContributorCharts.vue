<template>
  <div class="main">
    <el-card style="width: 100%; height: 100%" shadow="hover">
      <div class="topic">开发者贡献</div>
      <div class="content">
        <div ref="chart1" class="percent"></div></div
    ></el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, defineProps } from "vue";
import * as echarts from "echarts";

// 定义 props 以接收父组件传入的 data
const props = defineProps({
  data: {
    type: Array,
    required: true,
  },
});

// 创建 ECharts 实例的引用
const chart1 = ref(null);
let myChart1 = null; // 将 ECharts 实例存储在一个变量中

// 定义 ECharts 的初始配置
// const option1 = {
//   backgroundColor: "#2c343c",
//   title: {
//     text: "代码提交量",
//     left: "center",
//     top: 20,
//     textStyle: {
//       color: "#ccc",
//     },
//   },
//   tooltip: {
//     trigger: "item",
//   },
//   visualMap: {
//     show: false,
//     min: 80,
//     max: 600,
//     inRange: {
//       colorLightness: [0, 1],
//     },
//   },
//   series: [
//     {
//       name: "代码提交量",
//       type: "pie",
//       radius: "55%",
//       center: ["50%", "50%"],
//       data: [
//         { value: 335, name: "user1" },
//         { value: 310, name: "user2" },
//         { value: 274, name: "user3" },
//         { value: 235, name: "user4" },
//         { value: 400, name: "user5" },
//       ].sort(function (a, b) {
//         return a.value - b.value;
//       }),
//       roseType: "radius",
//       label: {
//         color: "rgba(255, 255, 255, 0.3)",
//       },
//       labelLine: {
//         lineStyle: {
//           color: "rgba(255, 255, 255, 0.3)",
//         },
//         smooth: 0.2,
//         length: 10,
//         length2: 20,
//       },
//       itemStyle: {
//         color: "#c23531",
//         shadowBlur: 200,
//         shadowColor: "rgba(0, 0, 0, 0.5)",
//       },
//       animationType: "scale",
//       animationEasing: "elasticOut",
//       animationDelay: function (idx) {
//         return Math.random() * 200;
//       },
//     },
//   ],
// };
const option1 = {
  backgroundColor: "#2c343c",
  title: {
    text: "代码提交量",
    left: "center",
    top: 20,
    textStyle: {
      color: "#ccc",
    },
  },
  tooltip: {
    trigger: "item",
  },
  // visualMap: {
  //   show: false,
  //   min: 80,
  //   max: 600,
  //   inRange: {
  //     colorLightness: [0.3, 0.7],
  //   },
  // },
  series: [
    {
      name: "Access From",
      type: "pie",
      radius: "55%",
      center: ["50%", "50%"],
      data: [
        { value: 335, name: "Direct" },
        { value: 310, name: "Email" },
        { value: 274, name: "Union Ads" },
        { value: 235, name: "Video Ads" },
        { value: 400, name: "Search Engine" },
      ].sort(function (a, b) {
        return a.value - b.value;
      }),
      roseType: "radius",
      label: {
        color: "rgba(255, 255, 255, 0.3)",
      },
      labelLine: {
        lineStyle: {
          color: "rgba(255, 255, 255, 0.3)",
        },
        smooth: 0.2,
        length: 10,
        length2: 20,
      },
      itemStyle: {
        color: "#c23531",
        shadowBlur: 200,
        shadowColor: "rgba(0, 0, 0, 0.5)",
      },
      animationType: "scale",
      animationEasing: "elasticOut",
      animationDelay: function (idx) {
        return Math.random() * 200;
      },
    },
  ],
};
// 初始化 ECharts
onMounted(() => {
  myChart1 = echarts.init(chart1.value); // 初始化 ECharts 实例
  myChart1.setOption(option1); // 设置初始配置
});

// 动态更新图表数据
function updateChartData(data) {
  option1.series[0].data = data.map((item) => ({
    value: item.totalAdditions,
    name: item.login,
  }));

  // 更新图表
  if (myChart1) {
    myChart1.setOption(option1); // 使用更新后的配置
  }
}

// 监听 props.data 的变化，并更新图表数据
watch(
  () => props.data,
  (newData) => {
    if (Array.isArray(newData)) {
      updateChartData(newData); // 使用传入的数据更新图表
      console.log("Chart data updated:", newData);
    } else {
      console.warn("Expected array data for chart, but received:", newData);
    }
  },
  { immediate: true, deep: true }
);
</script>


<style scoped lang="scss">
/* 在这里添加你的样式 */
.main {
  .topic {
    text-align: center;
    font-size: 23px;
    // background-color: antiquewhite;
  }
  .content {
    display: flex;
    align-items: center;
    justify-content: space-around;
    padding: 30px;
    .percent {
      width: 400px;
      height: 400px;
    }
  }
  ::v-deep .el-card {
    --el-card-padding: 8px;
  }
  height: 500px;

  margin-top: 10px;
}
</style>