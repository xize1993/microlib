import Vue from 'vue'
import App from './App.vue'
import router from './router.js'
import vuetify from './plugins/vuetify';
// import axios from 'axios'
// import VueAxios from 'vue-axios'
import request from './common/request'

Vue.config.productionTip = false

// Vue.use(VueAxios, axios)

// グロバールリクエストツール
Vue.prototype.$request = request 

// フィルター定義
Vue.filter('formatPrice', function (val) {
  return val ? `￥${val}` : ''
})
Vue.filter('formatTitle', function (val) {
  return val ? `${val}` : '【未定】'
})
Vue.filter('formatPageCount', function (val) {
  return val ? `${val}頁` : ''
})
Vue.filter('formatBooksLen', function (val) {
  return val ? val.length : '0'
})

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
