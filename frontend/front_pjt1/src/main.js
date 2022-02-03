import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import CSS from './assets/css/common.css'
import { BootstrapVue, BootstrapVueIcons} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import axios from 'axios'
import VueCookies from "vue-cookies"

Vue.prototype.$axios = axios

Vue.use(VueCookies)
Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.config.productionTip = false
Vue.$cookies.config("1d")
// axios.defaults.withCredentials = true

new Vue({
  router,
  store,
  render: h => h(App),
  CSS,
}).$mount('#app')
