import Vue from 'vue'
import Vuex from 'vuex'
import shoppingListApi from 'api/shoppingList'
import itemsApi from 'api/items'

Vue.use(Vuex)

export default new Vuex.Store({

    state: {
        shoppingLists,
        profile
    },
    getters: {
        sortedLists: state => (state.shoppingLists).sort((a, b) => (a.id - b.id)),
        getItemsByListId: state => id => {
            const index = state.shoppingLists.findIndex(list => list.id === id)
            const shoppingList = state.shoppingLists[index]
            return shoppingList
        },
        getBought: state => ids => {
            const listIndex = state.shoppingLists.findIndex(list => list.id === ids.listId)
            const shoppingList = state.shoppingLists[listIndex]
            const itemIndex = shoppingList.items.findIndex(item => item.item.id === ids.itemId)
            const shopping = shoppingList.items[itemIndex]
            return shopping.isBought
        },
        boughtItemsCount: state => listId => {
            const listIndex = state.shoppingLists.findIndex(list => list.id === listId)
            const shoppingList = state.shoppingLists[listIndex]
            const boughtItems = shoppingList.items.filter(item => item.isBought === true)

            return boughtItems.length
        }
    },
    mutations: {
        addShoppingListMutation(state, shoppingList) {
            state.shoppingLists = [
                ...state.shoppingLists,
                shoppingList
            ]
        },
        updateShoppingListMutation(state, shoppingList) {
            const updateIndex = state.shoppingLists.findIndex(list => list.id === shoppingList.id)

            state.shoppingLists = [
                ...state.shoppingLists.slice(0, updateIndex),
                shoppingList,
                ...state.shoppingLists.slice(updateIndex + 1)
            ]
        },
        removeShoppingListMutation(state, shoppingList) {
            const deletionIndex = state.shoppingLists.findIndex(list => list.id === shoppingList.id)

            if (deletionIndex > -1) {
                state.shoppingLists = [
                    ...state.shoppingLists.slice(0, deletionIndex),
                    ...state.shoppingLists.slice(deletionIndex + 1)
                ]
            }
        },
        addItemMutation(state, v) {
            const updateIndex = state.shoppingLists.findIndex(list => list.id === v.id)
            const shoppingList = state.shoppingLists[updateIndex]

            const shopping = {
                isBought: false,
                item: v.item,
                shoppingList: v.id
            }

            if (!shoppingList.items.find(item => item.id === v.item.id)) {
                state.shoppingLists = [
                    ...state.shoppingLists.slice(0, updateIndex),
                    {
                        ...shoppingList,
                        items: [
                            ...shoppingList.items,
                            shopping
                        ]
                    },
                    ...state.shoppingLists.slice(updateIndex + 1)
                ]
            }
        },
        updateItemMutation(state, v) {
            const updateListIndex = state.shoppingLists.findIndex(list => list.id === v.id)
            const shoppingList = state.shoppingLists[updateListIndex]
            const updateItemIndex = shoppingList.items.findIndex(item => item.item.id === v.item.id)

            const shopping = {
                isBought: false,
                item: v.item,
                shoppingList: v.id
            }

            state.shoppingLists = [
                ...state.shoppingLists.slice(0, updateListIndex),
                {
                    ...shoppingList,
                    items: [
                        ...shoppingList.items.slice(0, updateItemIndex),
                        shopping,
                        ...shoppingList.items.slice(updateItemIndex + 1)
                    ]
                },
                ...state.shoppingLists.slice(updateListIndex + 1)
            ]
        },
        removeItemMutation(state, v) {
            const updateListIndex = state.shoppingLists.findIndex(list => list.id === v.id)
            const shoppingList = state.shoppingLists[updateListIndex]
            const updateItemIndex = shoppingList.items.findIndex(item => item.item.id === v.item.id)

            state.shoppingLists = [
                ...state.shoppingLists.slice(0, updateListIndex),
                {
                    ...shoppingList,
                    items: [
                        ...shoppingList.items.slice(0, updateItemIndex),
                        ...shoppingList.items.slice(updateItemIndex + 1)
                    ]
                },
                ...state.shoppingLists.slice(updateListIndex + 1)
            ]
        }
    },
    actions: {
        async getListById({state}, id) {
            const result = await shoppingListApi.get(id)
            const data = await result.json()
            const index = state.shoppingLists.findIndex(list => list.id === data.id)
            const shoppingList = state.shoppingLists[index]

            return shoppingList
        },
        async addShoppingListAction({commit, state}, shoppingList) {
            const result = await shoppingListApi.add(shoppingList)
            const data = await result.json()
            const index = state.shoppingLists.findIndex(list => list.id === data.id)

            if (index > -1) {
                commit('updateShoppingListMutation', data)
            } else {
                commit('addShoppingListMutation', data)
            }
        },
        async updateShoppingListAction({commit}, shoppingList) {
            const result = await shoppingListApi.update(shoppingList)
            const data = await result.json()
            commit('updateShoppingListMutation', data)
        },
        async removeShoppingListAction({commit}, shoppingList) {
            const result = await shoppingListApi.remove(shoppingList.id)

            if (result.ok) {
                commit('removeShoppingListMutation', shoppingList)
            }
        },
        async updateShoppingListStatusAction({commit}, id) {
            const result = await shoppingListApi.updateStatus(id)
            const data = await result.json()
            commit('updateShoppingListMutation', data)
        },
        async addItemAction({commit, state}, v) {
            const response = await itemsApi.add(v)
            const data = await response.json()
            v.item = data
            const listIndex = state.shoppingLists.findIndex(list => list.id === v.id)
            const shoppingList = state.shoppingLists[listIndex]
            const index = shoppingList.items.findIndex(item => item.item.id === v.item.id)

            if (index > -1) {
                commit('updateItemMutation', v)
            } else {
                commit('addItemMutation', v)
            }
        },
        async updateItemAction({commit}, v) {
            const result = await itemsApi.update(v)
            const data = await result.json()
            v.item = data
            commit('updateItemMutation', v)
        },
        async removeItemAction({commit}, v) {
            const result = await itemsApi.remove(v)

            if (result.ok) {
                commit('removeItemMutation', v)
            }
        },
    }
})