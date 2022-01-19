import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    admin_post : [
      {
        id : 1,
        author : 'admin',
        title : 'test1_admin_title',
        content : 'test1_admin_content',
        created_at : '22.01.19',
      },
      {
        id : 2,
        author : 'admin',
        title : 'test2_admin_title',
        content : 'test2_admin_content',
        created_at : '22.01.20',
      },
      {
        id : 3,
        author : 'admin',
        title : 'test3_admin_title',
        content : 'test3_admin_content',
        created_at : '22.01.21',
      },
      {
        id : 4,
        author : 'admin',
        title : 'test4_admin_title',
        content : 'test4_admin_content',
        created_at : '22.01.22',
      },
      {
        id : 5,
        author : 'admin',
        title : 'test5_admin_title',
        content : 'test5_admin_content',
        created_at : '22.01.23',
      },
    ],
    user_post : [
      {
        id : 1,
        author : 'A',
        title : 'test1_user_title',
        content : 'test1_user_content',
        created_at : '22.01.19',
      },
      {
        id : 2,
        author : 'B',
        title : 'test2_user_title',
        content : 'test2_user_content',
        created_at : '22.01.20',
      },
      {
        id : 3,
        author : 'C',
        title : 'test3_user_title',
        content : 'test3_user_content',
        created_at : '22.01.21',
      },
      {
        id : 4,
        author : 'D',
        title : 'test4_user_title',
        content : 'test4_user_content',
        created_at : '22.01.22',
      },
      {
        id : 5,
        author : 'E',
        title : 'test5_user_title',
        content : 'test5_user_content',
        created_at : '22.01.23',
      },
    ],
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
