import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Login from '../views/Login.vue'
import Signup from '../views/Signup.vue'
import Notice from '../views/Notice.vue'
import Request from '../views/Request.vue'
import RequestCreate from '@/components/request/RequestCreate.vue'
import RequestDetail from '../components/request/RequestDetail.vue'
import RequestUpdate from '../components/request/RequestUpdate.vue'

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
    path: '/Signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/Notice',
    name : 'Notice',
    component: Notice
  },
  {
    path: '/Request',
    name : 'Request',
    component: Request
  },
  {
    path: '/Request/Create',
    name : 'RequestCreate',
    component : RequestCreate
  },
  {
    path: '/Request/Detail/:id',
    name : 'RequestDetail',
    component : RequestDetail
  },
  {
    path: '/Request/Detail/:id/Update',
    name : 'RequestUpdate',
    component : RequestUpdate
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
