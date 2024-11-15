<template>
  <div id="app">
    <ParticlesBackground v-if="!isLoginPage" />
    <div class="app-content">
      <Header v-if="!isLoginPage" />
      <router-view />
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import Header from "./components/Header.vue";
import ParticlesBackground from "./components/ParticlesBackground.vue";
import { useRoute } from "vue-router";

const route = useRoute();
const isLoginPage = computed(() => route.name === "Login");
</script>

<style scoped lang="scss">
#app {
  position: relative;
  width: 100%;
  min-height: 100vh;
  margin: 0;
  padding: 0;
}

.ParticlesBackground {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  z-index: -1;
}

.app-content {
  position: relative;
  z-index: 1;
  margin: 0;
  padding: 0;
}

header,
router-view {
  display: block;
}
</style>