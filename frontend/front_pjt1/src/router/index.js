import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Login from '../views/Login.vue'
import Signup from '../views/Signup.vue'
import Notice from '../views/Notice.vue'
import NoticeCreate from '@/components/notice/NoticeCreate.vue'
import NoticeDetail from '@/components/notice/NoticeDetail.vue'
import NoticeUpdate from '@/components/notice/NoticeUpdate.vue'
import Request from '../views/Request.vue'
import RequestCreate from '@/components/request/RequestCreate.vue'
import RequestDetail from '@/components/request/RequestDetail.vue'
import RequestUpdate from '@/components/request/RequestUpdate.vue'
import MainGame from '../views/MainGame.vue'
import Rule from '../views/Rule.vue'
import CardExchange from '../views/CardExchange.vue'
import Attend from '../views/Attend.vue'
import Join from '../views/Join.vue'
import GameEnd from '../views/GameEnd.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/Join',
    name: 'Join',
    component: Join
  },
  {
    path: '/Attend/:hostname',
    name: 'Attend',
    component: Attend,
  },
  {
    path: '/MainGame',
    name: 'MainGame',
    component: MainGame
  },
  {
    path: '/Rule',
    name: 'Rule',
    component: Rule,
  },
  {
    path: '/CardExchange',
    name : 'CardExchange',
    component : CardExchange,
  },
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/GameEnd',
    name: 'GameEnd',
    component: GameEnd
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
    path: '/Notice/Create',
    name : 'NoticeCreate',
    component : NoticeCreate
  },
  {
    path: '/Notice/Detail/:id',
    name : 'NoticeDetail',
    component : NoticeDetail
  },
  {
    path: '/Notice/Detail/:id/Update',
    name : 'NoticeUpdate',
    component : NoticeUpdate
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
