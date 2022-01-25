import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState(),
  ],
  state: {
    admin_post : [
      {
        id : 0,
        user : 'admin',
        title : 'test1_admin_title',
        content : 'test1_admin_content',
        created_at : '22.01.19',
      },
      {
        id : 1,
        user : 'admin',
        title : 'test2_admin_title',
        content : 'test2_admin_content',
        created_at : '22.01.20',
      },
      {
        id : 2,
        user : 'admin',
        title : 'test3_admin_title',
        content : 'test3_admin_content',
        created_at : '22.01.21',
      },
      {
        id : 3,
        user : 'admin',
        title : 'test4_admin_title',
        content : 'test4_admin_content',
        created_at : '22.01.22',
      },
      {
        id : 4,
        user : 'admin',
        title : 'test5_admin_title',
        content : 'test5_admin_content',
        created_at : '22.01.23',
      },
    ],
    isLogin: true,
    user: {
      user_id: 'ssafy',
      user_name: '윤종목',
    },
    user_post : [
      {
        id : 0,
        user : 'A',
        title : 'test1_user_title',
        content : 'test1_user_content',
        created_at : '22.01.19',
      },
      {
        id : 1,
        user : 'B',
        title : 'test2_user_title',
        content : 'test2_user_content',
        created_at : '22.01.20',
      },
      {
        id : 2,
        user : 'C',
        title : 'test3_user_title',
        content : 'test3_user_content',
        created_at : '22.01.21',
      },
      {
        id : 3,
        user : 'D',
        title : 'test4_user_title',
        content : 'test4_user_content',
        created_at : '22.01.22',
      },
      {
        id : 4,
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
    LOGOUT : function (state) {
      state.isLogin = false
    },
    LOGIN : function (state) {
      state.isLogin = true
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
    logout : function ({commit}) {
      commit('LOGOUT')
    },
    login : function ({commit}) {
      commit('LOGIN')
    },
  },
  modules: {
  }
})
