<template>
  <div class="header">
    <div class="logo" @click="toHome">
      <h1>DARPC</h1>
    </div>
    <div class="nav-links">
      <router-link to="/"><div class="pointer homeNav">首页</div></router-link>

      <router-link to="/overview">
        <div class="pointer">项目概况</div></router-link
      >

      <router-link to="/analyse">
        <div class="pointer">项目分析</div></router-link
      >

      <router-link to="/personal">
        <div class="pointer">个人信息</div></router-link
      >

      <!-- <router-link to="/location">
        <div class="pointer">成员位置</div></router-link
      >
      <router-link to="/chat"> <div class="pointer">AI分析</div></router-link> -->

      <!-- <li>个人分析</li>
      <li>成员位置</li> -->
      <!-- <el-input
        v-model="input"
        style="width: 600px; margin-left: 20px"
        placeholder="请输入仓库地址"
        :prefix-icon="Search"
      /> -->
      <!-- <li><router-link to="/contact">Contact</router-link></li> -->
    </div>
  </div>
</template>

<script setup>
import { Search } from "@element-plus/icons-vue";
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
// const input = ref("");
const toHome = () => {
  console.log("jump");

  router.push("/");
};
</script>

<style scoped lang="scss">
.header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 60px;
  background: linear-gradient(
    to right,
    rgba(161, 71, 118, 1),
    rgb(255, 255, 255, 1)
  );
  color: white;
  padding: 10px 20px;
  margin: 0;
  display: flex;
  align-items: center;
  z-index: 999;
  .logo {
    cursor: pointer;
    user-select: none;
    h1 {
      margin: 0;
    }
  }

  .nav-links {
    display: flex;
    gap: 5px;
    margin-left: 20px;
    .homeNav {
      margin-right: -1px;
    }
    .pointer {
      user-select: none;
      color: white;
      font-weight: 600;
      text-align: center;
      display: inline-block;
      width: 80px;
      height: 40px;
      line-height: 40px;
      border-radius: 20%;
      background-color: transparent;
      transition: background-color 1s ease;
      &:hover {
        background-color: black;
        transition-delay: 0s;
      }
      a {
        color: white;
        text-decoration: none;
        font-weight: bold;
      }
    }
    .router-link-active > .pointer {
      background-color: black;
    }
  }
}
</style>
