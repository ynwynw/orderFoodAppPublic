import App from './App'

// #ifndef VUE3
import Vue from 'vue'
import store from './store'
import api from './api'
import util from './common/util'
//挂载到vue上
Vue.prototype.$store = store
Vue.prototype.$api = api
Vue.prototype.$util = util
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App,
	store
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif

