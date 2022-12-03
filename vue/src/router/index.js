import Vue, {nextTick} from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'
import HomeView from '../views/Manage.vue'
import {Store} from "vuex/dist/vuex.esm.browser";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('../views/Manage.vue'),
    redirect: "/home",
    children: [
      {
        path: 'User',
        name: '用户管理',
        component: () => import('../views/User.vue'),
      },
      {
        path: 'Home',
        name: '首页',
        component: () => import('../views/Home.vue'),
      },
      {
        path: 'person',
        name: '个人信息',
        component: () => import('../views/Person.vue'),
      },
      {
        path: 'file',
        name: '文件管理',
        component: () => import('../views/File.vue'),
      },
      {
        path: 'role',
        name: '角色管理',
        component: () => import('../views/Role.vue'),
      },
      {
        path: 'Menu',
        name: '菜单管理',
        component: () => import('../views/Menu.vue'),
      }

    ]
  },
  //上面的是子路由
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新
  next()  // 放行路由
})

export default router
