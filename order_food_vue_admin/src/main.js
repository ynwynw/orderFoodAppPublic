import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from "element-ui";
import 'element-ui/lib/theme-chalk/index.css';
import './assets/icons' // icon
import './permission'
Vue.config.productionTip = false

// 全局引入 在main.js中引入nprogress插件和样式，
import NProgress from "nprogress"
import "nprogress/nprogress.css"

Vue.use(ElementUI)
new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
