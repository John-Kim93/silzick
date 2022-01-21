import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    admin_post : {
      id : 1,
      title : 'test1_admin_title',
      content : 'test1_admin_content',
      created_at : '22.01.19',
    },
    user_post : {
      id : 1,
      title : 'test1_user',
      created_at : '22.01.19',
    },
    isLogin : false,
    user : {
      user_id: 'ssafy',
      user_name: '김종현',
    },
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
