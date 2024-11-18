<template>
  <div class="contributor-map-page">
    <!-- 页面头部信息 -->
    <div class="header-section">
      <el-card class="page-header" shadow="hover">
        <div class="header-content">
          <h2>🚀 DevGauge</h2>
          <p>快速计算 GitHub 排名</p>
          <el-button
            class="start-analysis-button"
            type="primary"
            round
            @click="navigateToExplore"
            >开始探索</el-button
          >
        </div>
      </el-card>
    </div>

    <!-- 卡片区域 -->
    <div class="card-container">
      <el-card
        v-for="(card, index) in bentoBoxes"
        :key="index"
        class="card"
        :class="card.span"
        shadow="hover"
        :style="{
          background: `linear-gradient(45deg, ${card.colorFrom}, ${card.colorTo})`,
        }"
      >
        <div class="card-content">
          <div class="card-icon">{{ card.emoji }}</div>
          <h3 class="card-title">{{ card.title }}</h3>
          <p class="card-description">{{ card.text }}</p>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ElButton, ElCard } from "element-plus";

import { useRouter } from "vue-router";

// 使用 Vue Router
const router = useRouter();

// 定义导航函数
const navigateToExplore = () => {
  router.push("/nav"); // 这里 '/explore' 是目标页面的路径，根据你的实际路由设置修改
};

// 定义卡片模板数据
const sloganBentoBoxTemplate = [
  {
    colorFrom: "#2fccba",
    colorTo: "#1abc9c",
    emoji: "❤️",
    title: "感谢有你",
    text: "你是我们的动力源泉！",
  },
  {
    colorFrom: "#3498db",
    colorTo: "#2980b9",
    emoji: "✨",
    title: "开源无限",
    text: "源源不断的惊喜等你来探索！",
  },
  {
    colorFrom: "#9b59b6",
    colorTo: "#8e44ad",
    emoji: "💜",
    title: "有趣到爆",
    text: "趣味无限，停不下来！",
  },
  {
    colorFrom: "#e74c3c",
    colorTo: "#c0392b",
    emoji: "🎃",
    title: "幸运降临",
    text: "今天是你幸运的一天！",
  },
  {
    colorFrom: "#f1c40f",
    colorTo: "#f39c12",
    emoji: "😂",
    title: "开心无限",
    text: "笑声连连，开心每一天！",
  },
  {
    colorFrom: "#1abc9c",
    colorTo: "#16a085",
    emoji: "👍",
    title: "超酷无敌",
    text: "颜值与实力并存！",
  },
  {
    colorFrom: "#e67e22",
    colorTo: "#d35400",
    emoji: "🍀",
    title: "超级幸运",
    text: "好运加持，一路顺风！",
  },
  {
    colorFrom: "#e74c3c",
    colorTo: "#c0392b",
    emoji: "🍭",
    title: "有趣爆棚",
    text: "每个细节都超有趣！",
  },
  {
    colorFrom: "#3498db",
    colorTo: "#2980b9",
    emoji: "🚀",
    title: "超级飞跃",
    text: "人生如火箭，势不可挡！",
  },
  {
    colorFrom: "#2ecc71",
    colorTo: "#27ae60",
    emoji: "🎉",
    title: "美好时光",
    text: "生活太美好，想要一起庆祝！",
  },
  {
    colorFrom: "#f39c12",
    colorTo: "#e67e22",
    emoji: "✨",
    title: "精彩无限",
    text: "创造您的精彩时刻！",
  },
  {
    colorFrom: "#ff6b6b",
    colorTo: "#c0392b",
    emoji: "🌟",
    title: "无限可能",
    text: "相信自己，一切皆有可能！",
  },
];

// 创建固定布局，确保所有卡片组成一个完整正方形
function fixedBentoBox() {
  return [
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[0] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[1] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[2] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[3] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[4] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[5] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[6] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[7] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[8] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[9] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[10] },
    { span: "col-span-1 row-span-1", ...sloganBentoBoxTemplate[11] },
  ];
}

// 使用固定布局
const bentoBoxes = fixedBentoBox();
</script>

<style scoped lang="scss">
.contributor-map-page {
  display: flex;
  justify-content: space-between;
  // align-items: center;
  padding: 40px;
  background: linear-gradient(45deg, #ffd56b, #f36e3a) !important;
  font-family: "Arial", sans-serif;
}

.header-section {
  // width: 30%;
  flex: 1;
  margin-right: 20px;
  height: 100%;
  position: relative;
  top: 30vh;
  .page-header {
    background: linear-gradient(45deg, #ffd56b, #f36e3a) !important;
    border-radius: 15px;
    padding: 30px;
    box-shadow: 0 15px 25px rgba(0, 0, 0, 0.1);
    text-align: center;
    transition: transform 0.3s, box-shadow 0.3s;
    height: 100%;
    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 20px 30px rgba(0, 0, 0, 0.2);
    }

    .header-content {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;

      h2 {
        font-size: 2.5em;
        color: #4caf50;
        text-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
        margin-bottom: 15px;
      }

      p {
        font-size: 1.4em;
        color: #666666;
        margin-bottom: 25px;
      }

      .start-analysis-button {
        background-color: #4caf50;
        border-color: #4caf50;
        color: #ffffff;
        padding: 12px 35px;
        font-size: 1.2em;
        transition: background-color 0.3s, transform 0.3s;

        &:hover {
          background-color: #388e3c;
          transform: scale(1.1);
        }
      }
    }
  }
}

.card-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: repeat(4, auto);
  gap: 20px;
  width: 65%;
  max-width: 900px;
}

.card {
  // height: 20vh;
  background-color: #ffffff;
  border-radius: 15px;
  padding: 20px;
  text-align: center;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
  transition: transform 0.3s, box-shadow 0.3s;

  &:hover {
    transform: translateY(-10px);
    box-shadow: 0 15px 30px rgba(0, 0, 0, 0.25);
  }

  .card-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 15px;

    .card-icon {
      font-size: 3em;
      color: #ff6f61;
      text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
    }

    .card-title {
      font-size: 1.6em;
      color: #333333;
      font-weight: bold;
    }

    .card-description {
      font-size: 1.1em;
      color: #555555;
      line-height: 1.4;
    }
  }
}

/* 卡片布局样式 */
.col-span-1 {
  grid-column: span 1;
}

.row-span-1 {
  grid-row: span 1;
}
</style>
