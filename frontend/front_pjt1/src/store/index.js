import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"
import axios from 'axios'
import VueJwtDecode from 'vue-jwt-decode'
import router from '../router'
import { login } from '@/api/user.js'
import cookies from 'vue-cookies'

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState(),
  ],
  state: {
    admin_post : [],
    user: null,
    user_post : [],
  },
  mutations: {
    CREATE_NOTICE: function (state, res) {
      state.admin_post.push(res)
    },
    DELETE_NOTICE: function (state, res) {
      state.admin_post.forEach(function (post, index) {
        if (post.id == res) {
          state.admin_post.splice(index, 1)
        }
      })
    },
    UPDATE_NOTICE: function (state, res) {
      state.admin_post.forEach(function (post, index) {
        if (post.id == res.id) {
          state.admin_post[index] = res
        }
      })

    },
    GET_REQUESTS: function (state, res) {
      state.user_post = res
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
      const jwt_info = VueJwtDecode.decode(res.data)
      state.user = jwt_info
    },
  },
  actions: {
    async createNotice ({commit}, res) {
      await axios({
        method: 'post',
        url: 'http://127.0.0.1:8000/notice/',
        data: res,
        headers: this.getters.setToken,
      })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
      commit('CREATE_NOTICE', res)
    },
    deleteNotice: function({commit}, res) {
      commit('DELETE_NOTICE', res)
    },
    updateNotice: function ({commit}, res) {
      commit('UPDATE_NOTICE', res)
    },
    getRequests ({commit}) {
      const cookie = cookies.get('JWT-AUTHENTICATION')
      console.log(cookie)
      axios({
        method: 'get',
        url: 'http://127.0.0.1:8080/board',
        headers: { 'Cookie': cookie }
      })
        .then(res => {
          console.log('@@@@@@@')
          console.log(res)
          commit()
        })
        .catch(err => {
          console.log(err)
          alert(err)
        })
      },
    createRequest: function ({commit}, res) {
      axios({
        method: 'post',
        url: 'http://127.0.0.1:8080/board/regist/',
        data: res,
        // headers: cookies.get('JWT')
      })
        .then(res => {
          console.log(res)
          commit('CREATE_REQUEST', res)
          router.push({name:'RequestDetail', params: {id:`${res.data.id}`}})
        })
        .catch(err => {
          console.log(err)
        })
    },
    deleteRequest: function({commit}, res) {
      commit('DELETE_REQUEST', res)
    },
    updateRequest: function ({commit}, res) {
      commit('UPDATE_REQUEST', res)
    },
    async login ({commit}, user){
      await login(
        user.data,
        (res) => {
          console.log(res.data)
          commit(res.data)
          router.push('/')
          alert("로그인에 성공했습니다.")
        },
        (error) => {
          console.log(error)
          alert("로그인에 실패했습니다.")
        }
      )
    },
  },
  getters: {
  },
  modules: {
  }
})