<template>
  <div class="homePage">
    <div class="projectInformation">
      <ProjectOverview class="flexItem" :data="dataStore.projectData" />
      <!-- 项目概览区组件 -->
      <!-- <QuickStats class="flexItem" /> -->
      <!-- 快速统计组件 -->
      <ContributorCharts class="flexItem" :data="computedData" />
    </div>

    <!-- 贡献者分析图表组件 -->
    <ContributorList :data="computedData" />
    <!-- 贡献者列表组件 -->
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from "vue";
import ProjectOverview from "@/components/home/ProjectOverview.vue";
import QuickStats from "@/components/home/QuickStats.vue";
import ContributorCharts from "@/components/home/ContributorCharts.vue";
import ContributorList from "@/components/home/ContributorList.vue";
import { useDataStore } from "@/stores/dataStore";
import { useRepoStore } from "@/stores/repoStore";

const dataStore = useDataStore();
const store = useRepoStore();
const repoUrl = computed(() => store.repoUrl);
const requestData = { url: repoUrl.value };
onMounted(() => {
  dataStore.fetchAllData(requestData);
});
const computedData = computed(() => {
  const data = dataStore.listData?.data;
  // 返回一个新的数组引用
  return data ? [...data] : data;
});
watch(
  computedData,
  (newData, oldData) => {
    console.log("computedData updated:", newData);
  },
  { deep: true } // 深度监听，以便监听嵌套属性的变化
);

const showAll = () => {
  console.log("projectdata", dataStore.projectData);
  console.log("listdata", dataStore.listData);
};
</script>

<style scoped lang = "scss">
.homePage {
  padding: 10px;
  .projectInformation {
    display: flex;
    gap: 20px;
    justify-content: space-between; // 子元素之间均匀分布
    align-items: center; // 垂直方向居中对齐

    .flexItem {
      flex: 1;
      height: 480px;
    }
  }
}
</style>

