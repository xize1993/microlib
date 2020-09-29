import Vue from 'vue'
import App from './App.vue'
import router from './router.js'
import vuetify from './plugins/vuetify'
import request from './common/request'
import ja from 'vee-validate/dist/locale/ja.json'
import { required, min, max, min_value } from 'vee-validate/dist/rules'
import { extend, ValidationObserver, ValidationProvider, setInteractionMode, localize } from 'vee-validate'

Vue.config.productionTip = false

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

// vee-validate設定 TODO
setInteractionMode('eager')
extend('required', required)
extend('max', max)
extend('min', min)
extend('min_value', min_value)
localize('ja', ja);
Vue.component('ValidationObserver', ValidationObserver);
Vue.component('ValidationProvider', ValidationProvider);


new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
