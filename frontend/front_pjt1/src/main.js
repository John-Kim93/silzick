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
import CircularCountDownTimer from "vue-circular-count-down-timer"
import VueWebSpeech from 'vue-web-speech'
import VueSpeech from '../src/install.js'

Vue.prototype.$axios = axios

Vue.use(VueCookies)
Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.use(CircularCountDownTimer)
Vue.use(VueWebSpeech)
Vue.use(VueSpeech)
Vue.config.productionTip = false
Vue.$cookies.config("1d")
// axios.defaults.withCredentials = true

new Vue({
  router,
  store,
  render: h => h(App),
  CSS,
}).$mount('#app')
