import Vue from 'vue'

 const items = Vue.resource('/shopping-list/{listId}/item/{id}')

 export default {
     add: v => items.save({listId: v.id}, v.item),
     update: v => items.update({listId: v.id, id: v.item.id}, v.item),
     remove: v => items.remove({listId: v.id, id: v.item.id}),
     updateStatus: v => Vue.http.get(`/shopping-list/${v.listId}/item/${v.itemId}/update-status`)
 }