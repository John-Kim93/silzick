import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Login from '../views/Login.vue'
import Board from '../views/Board.vue'
import Signup from '../views/Signup.vue'

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
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
