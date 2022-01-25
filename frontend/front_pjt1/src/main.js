import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import CSS from './assets/css/common.css'
import { BootstrapVue, BootstrapVueIcons} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import axios from 'axios'

Vue.prototype.$axios = axios

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
  CSS,
}).$mount('#app')
