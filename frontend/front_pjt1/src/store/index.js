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
    admin_post : [
      {
        user : 'admin',
        title : 'test1_admin_title',
        content : 'test1_admin_content',
        created_at : '22.01.19',
      },
      {
        user : 'admin',
        title : 'test2_admin_title',
        content : 'test2_admin_content',
        created_at : '22.01.20',
      },
      {
        user : 'admin',
        title : 'test3_admin_title',
        content : 'test3_admin_content',
        created_at : '22.01.21',
      },
      {
        user : 'admin',
        title : 'test4_admin_title',
        content : 'test4_admin_content',
        created_at : '22.01.22',
      },
      {
        user : 'admin',
        title : 'test5_admin_title',
        content : 'test5_admin_content',
        created_at : '22.01.23',
      },
    ],
    isLogin: false,
    user: {
      user_name: null,
    },
    user_post : [
      {
        user : 'A',
        title : 'test1_user_title',
        content : 'test1_user_content',
        created_at : '22.01.19',
      },
      {
        user : 'B',
        title : 'test2_user_title',
        content : 'test2_user_content',
        created_at : '22.01.20',
      },
      {
        user : 'C',
        title : 'test3_user_title',
        content : 'test3_user_content',
        created_at : '22.01.21',
      },
      {
        user : 'D',
        title : 'test4_user_title',
        content : 'test4_user_content',
        created_at : '22.01.22',
      },
      {
        user : 'E',
        title : 'test5_user_title',
        content : 'test5_user_content',
        created_at : '22.01.23',
      },
    ],
  },
  mutations: {
    CREATE_NOTICE: function (state, res) {
      state.admin_post.push(res)
    },
    DELETE_NOTICE: function (state, res) {
      // const index = state.user_post[res]
      // console.log(index)
      state.admin_post.splice(res, 1)
    },
    UPDATE_NOTICE: function (state, res) {
      const noticeId = res.index
      state.admin_post[noticeId] = res
    },
    CREATE_REQUEST: function (state, res) {
      state.user_post.push(res)
    },
    DELETE_REQUEST: function (state, res) {
      // const index = state.user_post[res]
      // console.log(index)
      state.user_post.splice(res, 1)
    },
    UPDATE_REQUEST: function (state, res) {
      // console.log(res)
      const requestId = res.index
      // const index = state.user_post.findIndex(res => {
      //   return res.index === requestId
      // })
      state.user_post[requestId] = res
    },
    LOGIN: function (state, res) {
      const jwt_info = VueJwtDecode.decode(res.data.token)
      state.user.user_name = jwt_info.username
      state.isLogin = true
    },
    LOGOUT: function (state) {
      state.isLogin = false
    }
  },
  actions: {
    createNotice: function ({commit}, res) {
      // console.log(res)
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
  modules: {
  }
})
