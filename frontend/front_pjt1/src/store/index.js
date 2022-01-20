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
    // isLogin: true,
    user: {
      user_id: 'ssafy',
      user_name: '윤종목',
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
    CREATE_REQUEST: function (state, res) {
      state.user_post.push(res)
    },
    DELETE_REQUEST: function (state, res) {
      // const index = state.user_post[res]
      // console.log(index)
      state.user_post.splice(res, 1)
    }
  },
  actions: {
    createRequest: function ({commit}, res) {
      // console.log(res)
      commit('CREATE_REQUEST', res)
    },
    deleteRequest: function({commit}, res) {
      // console.log('delete')
      commit('DELETE_REQUEST', res)
    }
  },
  modules: {
  }
})
