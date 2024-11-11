<template>
  <div class="homepage">
    <!-- 页面头部 -->
    <el-card class="header">
      <h1>GitHub 项目贡献者数据分析</h1>
      <p>
        全面分析项目的贡献者情况，深入挖掘参与者的开发领域、代码提交、贡献度等信息。
      </p>
    </el-card>

    <!-- 功能概览 -->
    <el-card class="features">
      <h2>核心功能</h2>
      <ul>
        <li>分析项目的具体信息与详细指标</li>
        <li>可视化展示代码变更情况</li>
        <li>贡献者个人信息分析</li>
        <!-- <li>导出分析数据，便于共享</li> -->
      </ul>
    </el-card>

    <!-- 使用步骤 -->
    <el-card class="usage-steps">
      <h2>如何使用</h2>
      <ol>
        <li>在下方输入框中粘贴 GitHub 仓库地址</li>
        <li>点击“开始分析”按钮</li>
        <li>浏览并查看详细的贡献者数据分析</li>
      </ol>
    </el-card>

    <!-- GitHub 仓库地址输入区和示例展示 -->
    <el-card class="input-demo">
      <div class="input-section">
        <label for="repo-url">输入 GitHub 仓库地址:</label>
        <input v-model="repoUrl" type="text" id="repo-url" placeholder="" />
        <button @click="goToAnalysis">开始分析</button>
      </div>
    </el-card>

    <!-- 常见问题解答 (FAQ) -->
    <el-card class="faq">
      <h2>常见问题</h2>
      <div class="faq-item" v-for="(faq, index) in faqs" :key="index">
        <h4>{{ faq.question }}</h4>
        <p>{{ faq.answer }}</p>
      </div>
    </el-card>

    <!-- 底部信息 -->
    <footer class="footer">
      <p>API 使用说明: 本项目基于 GitHub API 获取数据。</p>
      <p>
        项目开源地址:
        <a href="https://github.com/yourproject" target="_blank">GitHub 仓库</a>
      </p>
      <p>
        其他资源: <a href="https://github.com" target="_blank">GitHub</a> |
        <a href="https://yourwebsite.com" target="_blank">联系我们</a>
      </p>
    </footer>
  </div>
</template>


<script setup>
import { useRepoStore } from "@/stores/repoStore";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";

// 路由实例
const store = useRepoStore();
const router = useRouter();
const repoUrl = ref("");

// FAQ 数据
const faqs = ref([
  {
    question: "如何获取 GitHub 仓库地址？",
    answer: "打开 GitHub 项目主页，复制浏览器地址栏的链接即可。",
  },
  {
    question: "支持哪些 GitHub 仓库？",
    answer: "本项目支持公共仓库和经过授权的私有仓库。",
  },
  // 可继续添加问题
]);

// 跳转到分析页面的方法
function goToAnalysis() {
  if (repoUrl.value) {
    store.setRepoUrl(repoUrl.value); // 将输入的仓库地址存入全局状态
    router.push(`/overview`); // 跳转到分析页面
  } else {
    ElMessage.error("请输入有效的 GitHub 仓库地址！");
  }
}
</script>
<style scoped lang = "scss">
.homepage {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 30px;
  padding: 20px;
  min-height: 100vh;
  font-family: Arial, sans-serif;
  /* text-align: center; */

  // 页面头部、功能概览、使用步骤等
  .el-card {
    width: 100%;
    max-width: 800px;
    padding: 20px;
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);

    h1,
    h2,
    h3,
    h4 {
      color: #333;
    }

    p {
      color: #666;
      font-size: 1em;
    }

    ul,
    ol {
      margin-top: 10px;
      padding-left: 20px;
      font-size: 1em;
      color: #555;

      li {
        margin: 8px 0;
      }
    }

    .input-section {
      display: flex;
      flex-direction: column;
      align-items: center;

      label {
        font-weight: bold;
        font-size: 1.1em;
      }

      input[type="text"] {
        padding: 10px;
        width: 100%;
        max-width: 500px;
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-top: 10px;
        font-size: 1em;
      }

      button {
        margin-top: 15px;
        padding: 10px 20px;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 5px;
        font-size: 1em;
        cursor: pointer;

        &:hover {
          background-color: #0056b3;
        }
      }
    }

    .demo-section {
      text-align: center;
      margin-top: 20px;

      h3 {
        font-size: 1.2em;
        margin-bottom: 10px;
      }

      img {
        width: 100%;
        max-width: 600px;
        border-radius: 10px;
        border: 1px solid #ccc;
      }
    }

    .faq-item {
      margin-top: 15px;

      h4 {
        font-size: 1.1em;
        color: #333;
      }

      p {
        font-size: 1em;
        color: #555;
      }
    }
  }

  // 底部信息
  .footer {
    text-align: center;
    font-size: 0.9em;
    color: #888;
    width: 100%;
    max-width: 800px;
    padding: 20px;

    a {
      color: #007bff;
      text-decoration: none;

      &:hover {
        text-decoration: underline;
      }
    }

    p {
      margin: 5px 0;
    }
  }
}
</style>