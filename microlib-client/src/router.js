import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginPage from "./pages/LoginPage";
import NotFoundPage from "./pages/NotFoundPage";
import MainPage from "./pages/MainPage";
import BookView from "./views/BookView";
import AuthorView from "./views/AuthorView";

Vue.use(VueRouter)

// グローバルルーター設定
const globalRoutes = [
    { path: '/404', component: NotFoundPage, name: '404', meta: { title: 'Not Found' } },
    { path: '/login', component: LoginPage, name: 'login', meta: { title: 'ログイン' } }
]

// 業務ルーター設定
const mainRoutes = {
    path: '/microlib',
    component: MainPage,
    name: 'main',
    redirect: { name: 'book' },
    meta: { title: 'メインウインドウ' },
    children: [
      { path: '/microlib/book', component: BookView, name: 'book', meta: { title: '書籍管理ウインドウ'} },
      { path: '/microlib/author', component: AuthorView, name: 'author', meta: { title: '著者管理ウインドウ'} }
    ],
  }

// ルーター
const router = new VueRouter({
    mode: 'hash',
    routes: globalRoutes.concat(mainRoutes)
  })

export default router
