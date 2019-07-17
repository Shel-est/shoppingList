import Vue from 'vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'api/resource'
import router from 'router/router'
import store from 'store/store'
import App from 'pages/App.vue'
import 'vuetify/dist/vuetify.min.css'

Vue.use(Vuetify)

new Vue({
    el: '#app',
    store,
    router,
    render: a => a(App)
})
