<template>
  <div class="container">
    <div class="header">
      <h1>GitHub 项目工具</h1>
      <p>
        使用我们的强大工具高效分析您的 GitHub
        项目，获取有关贡献者、排名等方面的深入见解。
      </p>
    </div>

    <button class="start-analysis-button">开始分析</button>

    <div class="grid-container">
      <AnalysisCard
        title="GitHub 排名"
        description="发现您的 GitHub 项目的排名。"
      />
      <AnalysisCard
        title="项目排名"
        description="评估项目的性能指标。"
        color="#8eedd7"
      />
      <AnalysisCard
        title="贡献者"
        description="识别并分析贡献者。"
        color="#98b4ff"
      />
      <AnalysisCard title="合作者" description="查看您的项目协作情况。" />
      <AnalysisCard title="项目分析" description="详细分解项目的各项指标。" />
      <AnalysisCard title="统计分析" description="深入了解统计数据和趋势。" />
      <AnalysisCard title="代码质量" description="衡量代码质量并寻找改进点。" />
      <AnalysisCard title="问题与拉取请求" description="分析问题和拉取请求。" />
      <AnalysisCard title="提交分析" description="获取提交活动的见解。" />
      <AnalysisCard title="社区增长" description="监控您的社区参与度。" />
    </div>
  </div>
</template>

<script setup>
import AnalysisCard from "@/components/AnalysisCard.vue";
</script>

<style>
.container {
  width: 90%;
  margin: 0 auto;
  text-align: center;
}
.header {
  margin-top: 30px;
}
.header h1 {
  font-size: 3em;
  margin-bottom: 10px;
}
.header p {
  font-size: 1.2em;
  margin-bottom: 20px;
}
.grid-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-top: 40px;
}
.card {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
  text-align: center;
}
.card:hover {
  transform: translateY(-10px);
}
.card h3 {
  font-size: 1.5em;
  margin-bottom: 15px;
}
.card p {
  font-size: 1em;
  color: #666;
}
.start-analysis-button {
  margin-top: 40px;
  padding: 15px 30px;
  font-size: 1.5em;
  color: #fff;
  background-color: #eff375;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  transition: background-color 0.3s;
}
.start-analysis-button:hover {
  background-color: #e63946;
}
</style>
