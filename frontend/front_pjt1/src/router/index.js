import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Login from '../views/Login.vue'
import Board from '../views/Board.vue'
import Signup from '../views/Signup.vue'
import FindID from '../views/FindID.vue'
import FindPW from '../views/FindPW.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/Login',
    name: 'Login',
    component: Login
  },
  {
    path: '/Board',
    name: 'Board',
    component: Board
  },
  {
    path: '/Signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/FindID',
    name: 'FindID',
    component: FindID
  },
  {
    path: '/FindPW',
    name: 'FindPW',
    component: FindPW
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
