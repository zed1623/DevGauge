<template>
  <div class="container">
    <div class="header">
      <h1>GitHub 项目工具</h1>
      <p>
        使用我们的强大工具高效分析您的 GitHub
        项目，获取有关贡献者、排名等方面的深入见解。
      </p>
    </div>
    <router-link to="/home">
      <button class="start-analysis-button">开始分析</button>
    </router-link>
    <div class="grid-container">
      <AnalysisCard
        v-for="(card, index) in analysisCards"
        :key="index"
        :title="card.title"
        :description="card.description"
        :colorFrom="card.colorFrom"
        :colorTo="card.colorTo"
        :icon="card.icon"
        :route="card.route"
      />
    </div>

    <!-- 左侧和右侧的装饰图案，包括动态齿轮和图标 -->
    <div class="decorative-element gear decorative-left"></div>
    <div class="decorative-element gear decorative-right"></div>
    <div class="decorative-element icon decorative-left-small"></div>
    <div class="decorative-element icon decorative-right-small"></div>
  </div>
</template>

<script setup>
import AnalysisCard from "@/components/AnalysisCard.vue";
import {
  faUser,
  faLink,
  faInfoCircle,
  faEye,
  faRobot,
  faChartLine,
  faTrophy,
} from "@fortawesome/free-solid-svg-icons";

const analysisCards = [
  {
    title: "GitHub 用户名分析",
    description: "分析 GitHub 用户名的相关信息，了解用户的贡献和影响力。",
    colorFrom: "#9b59b6",
    colorTo: "#8e44ad",
    icon: faUser,
    route: "/personal",
  },
  {
    title: "仓库链接分析",
    description: "输入仓库链接，获取仓库的详细分析和统计信息。",
    colorFrom: "#f1c40f",
    colorTo: "#f39c12",
    icon: faLink,
    route: "/home",
  },
  {
    title: "个人信息页面",
    description: "查看和编辑您的个人信息和偏好设置。",
    colorFrom: "#e74c3c",
    colorTo: "#c0392b",
    icon: faInfoCircle,
    route: "/user",
  },
  {
    title: "监控页面",
    description: "监控 GitHub 项目和用户的活动情况。",
    colorFrom: "#2ecc71",
    colorTo: "#27ae60",
    icon: faEye,
    route: "/analyse",
  },
  {
    title: "AI 问答页面",
    description: "使用 AI 进行 GitHub 相关问题的智能回答。",
    colorFrom: "#ff6b6b",
    colorTo: "#c0392b",
    icon: faRobot,
    route: "/chat",
  },
  {
    title: "项目排行页面",
    description: "查看 GitHub 项目的排行情况。",
    colorFrom: "#3498db",
    colorTo: "#2980b9",
    icon: faChartLine,
    route: "/projectRank",
  },
  {
    title: "个人排行页面",
    description: "查看在 GitHub 中的用户排行情况。",
    colorFrom: "#1abc9c",
    colorTo: "#16a085",
    icon: faTrophy,
    route: "/personal",
  },
];
</script>

<style scoped>
/* .container {
  position: relative;
  width: 90%;
  margin: 0 auto;
  text-align: center;
  background: linear-gradient(45deg, #ffd56b, #f36e3a);
  padding: 50px;
  border-radius: 20px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
  overflow: hidden;
} */
.container {
  display: flex;
  flex-direction: column; /* 垂直排列内容 */
  justify-content: space-between; /* 根据内容调整间距 */
  align-items: center;
  width: 100vw; /* 占满宽度 */
  height: 100%; /* 刚好占满视口高度 */
  min-height: 100vh;
  background: linear-gradient(45deg, #ffd56b, #f36e3a);
  padding: 0px; /* 避免内容贴边 */
  box-sizing: border-box; /* 包含 padding */
  text-align: center;
  overflow: hidden;
  overflow-y: auto; /* 内容高度超出时启用滚动条 */
  overflow-x: hidden; /* 禁止水平滚动条 */
  /* border-radius: 20px; */
  /* box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2); */
}
/* .header {
  margin-top: 30px;
  color: #ffffff;
} */
.header {
  flex: 0 0 auto; /* 保持内容固定大小 */
  margin-top: 20px;
  color: #ffffff;
}
.header h1 {
  font-size: 3.5em;
  margin-bottom: 15px;
  text-transform: uppercase;
  letter-spacing: 2px;
  text-shadow: 2px 2px 10px rgba(0, 0, 0, 0.3);
}

.header p {
  font-size: 1.4em;
  margin-bottom: 25px;
  line-height: 1.5;
  text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.3);
}

.start-analysis-button {
  margin-top: 40px;
  padding: 20px 40px;
  font-size: 1.8em;
  color: #ffffff;
  background-color: #ff6f61;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.3);
}

.start-analysis-button:hover {
  background-color: #ff3b30;
  transform: scale(1.05);
}

/* .grid-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 30px;
  margin: 40px auto;
  padding: 0 100px;
  max-width: 1200px;
} */
.grid-container {
  flex: 1 0 auto; /* 可扩展到剩余空间 */
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 30px;
  max-width: 1200px;
  padding: 0 20px;
  margin: 20px 0;
  box-sizing: border-box;
}
.card {
  background-color: #ffffff;
  padding: 25px;
  border-radius: 20px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
  transition: transform 0.3s, box-shadow 0.3s;
  text-align: center;
  width: 260px;
  position: relative;
}

.card:hover {
  transform: translateY(-10px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
}

.card h3 {
  font-size: 1.7em;
  margin-bottom: 15px;
  color: #333333;
}

.card p {
  font-size: 1.1em;
  color: #555555;
}

/* 背景装饰元素 */
.decorative-element {
  position: absolute;
  background: rgba(255, 255, 255, 0.15);
  box-shadow: 0 0 25px rgba(0, 0, 0, 0.2);
  display: block; /* 确保显示 */
  z-index: -1;
}

/* 齿轮装饰 */
.gear {
  width: 120px;
  height: 120px;
  background: radial-gradient(circle, #f36e3a, #ffb743);
  clip-path: polygon(
    50% 0%,
    60% 20%,
    80% 20%,
    100% 40%,
    80% 60%,
    100% 80%,
    80% 100%,
    60% 80%,
    50% 100%,
    40% 80%,
    20% 80%,
    0% 60%,
    20% 40%,
    0% 20%,
    20% 20%,
    40% 20%
  );
  animation: spin 10s linear infinite;
}

/* 分析图标 */
.icon {
  width: 60px;
  height: 60px;
  background: radial-gradient(circle, #98b4ff, #8eedd7);
  clip-path: circle(50%);
}

/* 左侧装饰图案 */
.decorative-left {
  top: 20%;
  left: 20px;
}

.decorative-left-small {
  bottom: 30%;
  left: 20px;
}

/* 右侧装饰图案 */
.decorative-right {
  bottom: 20%;
  right: 20px;
}

.decorative-right-small {
  top: 30%;
  right: 20px;
}
.start-analysis-button {
  flex: 0 0 auto; /* 固定大小按钮 */
  margin-top: 20px;
  padding: 15px 30px;
  font-size: 1.4em;
  color: #ffffff;
  background-color: #ff6f61;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.3);
}
/* 动画：旋转齿轮 */
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
/* * {
  box-sizing: border-box;
} */
</style>
