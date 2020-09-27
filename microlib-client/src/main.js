import Vue from 'vue'
import App from './App.vue'
import router from './router.js'
import vuetify from './plugins/vuetify';
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.config.productionTip = false

Vue.use(VueAxios, axios)

// フィルター定義
Vue.filter('formatPrice', function (val) {
  return val ? `￥${val}` : 'ー'
})
Vue.filter('formatTitle', function (val) {
  return val ? `${val}` : '【未定】'
})

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
