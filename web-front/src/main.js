import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import request from './request'
import Constants from './constants/constants'

import VueCodemirror from 'vue-codemirror'

// require styles
import 'codemirror/lib/codemirror.css'

Vue.prototype.constants = Constants;

Vue.use(ElementUI);
Vue.use(request);
Vue.config.productionTip = false
Vue.use(VueCodemirror)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
