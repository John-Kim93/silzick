import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"
import axios from 'axios'
import VueJwtDecode from 'vue-jwt-decode'
import router from '../router'

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState(),
  ],
  state: {
    admin_post : [],
    isLogin: false,
    user: null,
    user_post : [],
    token: null,
  },
  mutations: {
    CREATE_NOTICE: function (state, res) {
      state.admin_post.push(res)
    },
    DELETE_NOTICE: function (state, res) {
      // const index = state.user_post[res]
      // console.log(res)
      state.admin_post.forEach(function (post, index) {
        if (post.id == res) {
          state.admin_post.splice(index, 1)
        }
      })
    },
    UPDATE_NOTICE: function (state, res) {
      // console.log(res)
      state.admin_post.forEach(function (post, index) {
        if (post.id == res.id) {
          state.admin_post[index] = res
        }
      })

    },
    CREATE_REQUEST: function (state, res) {
      state.user_post.push(res)
    },
    DELETE_REQUEST: function (state, res) {
      state.user_post.forEach(function (post, index) {
        if (post.id == res) {
          state.user_post.splice(index, 1)
        }
      })
    },
    UPDATE_REQUEST: function (state, res) {
      state.user_post.forEach(function (post, index) {
        if (post.id == res.id) {
          state.user_post[index] = res
        }
      })
    },
    LOGIN: function (state, res) {
      const jwt_info = VueJwtDecode.decode(res.data.token)
      // console.log(jwt_info)
      state.isLogin = true
      state.user = jwt_info
      state.token = res.data.token
    },
    LOGOUT: function (state) {
      state.isLogin = false
    }
  },
  actions: {
    createNotice: function ({commit}, res) {
      axios({
        method: 'post',
        url: 'http://127.0.0.1:8000/community/notice/',
        data: res.data,
        headers: this.getters.setToken
      })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
      // commit('CREATE_REQUEST', res)
      commit('CREATE_NOTICE', res)
    },
    deleteNotice: function({commit}, res) {
      // console.log('delete')
      commit('DELETE_NOTICE', res)
    },
    updateNotice: function ({commit}, res) {
      commit('UPDATE_NOTICE', res)
    },
    createRequest: function ({commit}, res) {
      // console.log(res)
      axios({
        method: 'post',
        url: 'http://127.0.0.1:8000/community/request/',
        data: res.data,
        headers: this.getters.setToken
      })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
      commit('CREATE_REQUEST', res)
    },
    deleteRequest: function({commit}, res) {
      // console.log('delete')
      commit('DELETE_REQUEST', res)
    },
    updateRequest: function ({commit}, res) {
      commit('UPDATE_REQUEST', res)
    },
    logIn: function ({commit}, res){
      axios({
        method: 'post',
        url: 'http://127.0.0.1:8000/accounts/api-token-auth/',
        data: res.data,
      })
        .then(res => {
          localStorage.setItem('jwt',res.data.token)
          commit('LOGIN', res)
        })
        .then(()=> router.push('/'))
        .catch(err => {
          console.log(err)
        })
    },
    logOut: function ({commit}){
      commit('LOGOUT')
      localStorage.removeItem('jwt')
      router.go(router.currentRoute)
    },
  },
  getters: {
    setToken: function (state) {
      const config = {
        Authorization : `JWT  ${state.token}`
      }
      return config
    }
  },
  modules: {
  }
})
