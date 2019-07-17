import Vue from 'vue'
import VueRouter from 'vue-router'
import ShoppingLists from 'pages/ShoppingLists.vue'
import ShoppingListView from 'pages/ShoppingListView.vue'
import Auth from 'pages/Auth.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: ShoppingLists },
    { path: '/auth', component: Auth },
    { path: '/items/:listId?', component: ShoppingListView },
    { path: '*', component: ShoppingLists },
]

export default new VueRouter({
    mode: 'history',
    routes
})