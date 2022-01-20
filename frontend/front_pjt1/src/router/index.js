import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Login from '../views/Login.vue'
import Board from '../views/Board.vue'
import Signup from '../views/Signup.vue'
import Notice from '../components/board/Notice.vue'
import Request from '../components/board/Request.vue'
import RequestCreate from '../components/board/RequestCreate.vue'
import RequestDetail from '../components/board/RequestDetail.vue'

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
    path: '/Board/Notice',
    name : 'Notice',
    component: Notice
  },
  {
    path: '/Board/Request',
    name : 'Request',
    component: Request
  },
  {
    path: '/Board/Request/Create',
    name : 'RequestCreate',
    component : RequestCreate
  },
  {
    path: '/Board/Request/Detail/:id',
    name : 'RequestDetail',
    component : RequestDetail
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
