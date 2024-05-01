import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'


import { resolve } from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],

  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      }
    },
  },

  resolve: {
    // 设置路径别名
    alias: {
      '@': resolve(__dirname, './src'),
      '*': resolve('')
    },
  },

  css: {
    preprocessorOptions: {
      less: {
        javascriptEnabled: true,
      }
    },
  },
})
