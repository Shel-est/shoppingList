import Vue from 'vue'

const shoppingLists = Vue.resource('/shopping-list/{id}')

export default {
    get: id => shoppingLists.get({id}),
    add: shoppingList => shoppingLists.save({}, shoppingList),
    update: shoppingList => shoppingLists.update({id: shoppingList.id}, shoppingList),
    remove: id => shoppingLists.remove({id}),
    updateStatus: id => Vue.http.post(`/shopping-list/update-status/${id}`)
}