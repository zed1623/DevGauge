<template>
  <div>
    <el-card style="width: 100%; height: 100%" shadow="hover">
      <div class="topic">快速统计</div>
      <div class="content">
        <div class="show">
          <div class="name">TalentRank：</div>
          <div class="data">1.xxx</div>
          <div class="data">2.xxx</div>
          <div class="data">3.xxx</div>
        </div>
        <div class="show">
          <div class="name">最多提交：</div>
          <div class="data">1.xxx</div>
          <div class="data">2.xxx</div>
          <div class="data">3.xxx</div>
        </div>
        <div class="show">
          <div class="name">最高代码行数：</div>
          <div class="data">1.xxx</div>
          <div class="data">2.xxx</div>
          <div class="data">3.xxx</div>
        </div>
        <div class="show">
          <div class="name">最近一个月活跃度：</div>
          <div class="data">1.xxx</div>
          <div class="data">2.xxx</div>
          <div class="data">3.xxx</div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from "vue";

// 定义响应式数据
const message = ref("快速统计");

// 在这里编写你的 Composition API 代码
</script>

<style scoped lang = "scss">
/* 在这里添加你的样式 */
.topic {
  text-align: center;
  font-size: 23px;
  background-color: antiquewhite;
}
::v-deep .el-card {
  --el-card-padding: 8px;
}
.content {
  padding: 10px;
}
.show {
  /* background-color: deeppink; */
  width: 360px;
  height: 36px;
  display: flex;
  gap: 6px;
  .name {
    line-height: 36px;
    color: rgb(87, 21, 122);
  }
  .data {
    line-height: 36px;
    color: #4a4a8b;
  }
}
</style>