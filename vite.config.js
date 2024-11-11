import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    port: 8080,
    // proxy: {
    //   '/project': {
    //     target: 'http://47.113.195.131:8090', // 你的目标服务器
    //     changeOrigin: true, // 如果后端服务器需要此选项来允许 CORS 请求，请设置为 true
    //   },
    // },
  },
})
