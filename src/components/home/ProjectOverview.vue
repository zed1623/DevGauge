<template>
  <div>
    <el-card style="width: 100%; height: 100%" shadow="hover"
      ><div class="topic">项目总览</div>
      <div class="content">
        <div class="basic">
          <div class="show">
            <div class="name">项目名称：</div>
            <div class="data">{{ data?.data?.name }}</div>
          </div>
          <div class="show introduce">
            <div class="name">简介：</div>
            <div class="data">{{ data?.data?.description }}</div>
          </div>
          <div class="show">
            <div class="name">star：</div>
            <div class="data">{{ data?.data?.stars }}</div>
          </div>
          <div class="show">
            <div class="name">forks：</div>
            <div class="data">{{ data?.data?.forks }}</div>
          </div>
          <div class="show">
            <div class="name">issues：</div>
            <div class="data">{{ data?.data?.issues }}</div>
          </div>
        </div>
        <div class="summary">
          <div class="show">
            <div class="name">开发者数量:</div>
            <div class="data">{{ data?.data?.projectUser }}</div>
          </div>
          <div class="show">
            <div class="name">总提交次数：</div>
            <div class="data">{{ data?.data?.totalCommits }}</div>
          </div>
          <div class="show">
            <div class="name">总代码行数：</div>
            <div class="data">{{ data?.data?.projectCode }}</div>
          </div>
          <div class="show">
            <div class="name">最近更新：</div>
            <div class="data">{{ data?.data?.updatedAt }}</div>
          </div>
        </div>
      </div></el-card
    >
  </div>
</template>

<script setup>
import { ref, defineProps, watch } from "vue";
const props = defineProps({
  data: {
    type: [Object, Array], // 根据 data 的实际类型指定
    required: true,
  },
});

console.log("Initial props data:", props.data);

// watch(
//   () => props.data,
//   (newValue) => {
//     if (newValue && Object.keys(newValue).length > 0) {
//       // 检查 newValue 是否有效
//       console.log("data has been updated in child component:", newValue);
//       // 在这里处理 data 更新后的逻辑
//     }
//   },
//   { immediate: true, deep: true }
// );
</script>

<style scoped lang = "scss">
.topic {
  text-align: center;
  font-size: 23px;
  /* background-color: antiquewhite; */
}
.content {
  .basic,
  .summary {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    padding: 10px;
  }
}
.show {
  /* background-color: deeppink; */
  width: 360px;
  height: 60px;
  display: flex;
  gap: 6px;
  overflow: hidden;
  overflow-y: auto;
  .name {
    line-height: 36px;
    color: rgb(87, 21, 122);
  }

  .data {
    line-height: 36px;
    color: #4a4a8b;
  }
  /* 自定义滚动条样式 */
  &::-webkit-scrollbar {
    width: 10px; /* 滚动条的宽度 */
  }

  &::-webkit-scrollbar-thumb {
    background-color: gray; /* 滚动条的颜色 */
    border-radius: 5px; /* 滚动条圆角 */
  }

  &::-webkit-scrollbar-track {
    background-color: #f1f1f1; /* 滚动条轨道的颜色 */
  }
}
.intrduce {
  width: 720px;
}
::v-deep .el-card {
  --el-card-padding: 8px;
}
</style>
