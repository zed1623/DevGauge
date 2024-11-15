<!-- Login.vue -->
<template>
  <div>
    <div>
      <a>github授权登录</a>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

// const login = () => {
// window.location.href = "http://47.113.195.131:8091/login/oauth2/code/github";
// 假设这里处理 GitHub 登录逻辑
// 登录成功后，保存认证状态到 localStorage
localStorage.setItem("isAuthenticated", "true");
// // 获取 redirect 参数并跳转
// const redirect = router.currentRoute.value.query.redirect || "/";
// router.push(redirect);
// };
</script>
