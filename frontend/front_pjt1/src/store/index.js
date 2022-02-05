import Vue from 'vue'
import Vuex from 'vuex'

import VueJwtDecode from 'vue-jwt-decode'
import cookies from 'vue-cookies'
import board from './modules/board'
import notice from './modules/notice'
import gameStore from './modules/gameStore'

Vue.use(Vuex)

export default new Vuex.Store({
  
  state: {
    username: null,
  },
  mutations: {
    SAVE_USER (state, user) {
      state.username = user
    },
    RESET_USER (state) {
      state.username = null
    },
  },
  actions: {
    saveUser({ commit }) {
      const user = VueJwtDecode.decode(cookies.get('JWT-AUTHENTICATION')).sub
      commit('SAVE_USER', user)
    },
  },
  getters: {
  },
  modules: {
    board,
    notice,
    gameStore,
  }
})