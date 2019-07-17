<template>
    <v-container>
        <v-layout>
            <v-text-field
                    placeholder="Что?"
                    v-model="name"
            />
            <v-text-field
                    placeholder="Сколько?"
                    v-model="count"
            />
            <v-text-field
                    placeholder="Цена за шт?"
                    v-model="price"
            />
            <v-btn
                    @click="addNewItem(listId)"
            >
                Add item
            </v-btn>
        </v-layout>
    </v-container>
</template>

<script>
    import itemsApi from 'api/items'
    import { mapActions } from 'vuex'

    export default {
        props: ['listId', 'itemAttr'],
        data() {
            return {
                id: null,
                name: '',
                count: null,
                price: null,
            }
        },
        watch: {
            itemAttr(newVal, oldVal) {
                this.id = newVal.id
                this.name = newVal.name
                this.count = newVal.count
                this.price = newVal.price
            }
        },
        methods: {
            ...mapActions(['addItemAction', 'updateItemAction']),
            addNewItem(listId) {
                const item = {
                    id: this.id,
                    name: this.name,
                    count: this.count,
                    price: this.price
                }
                const v = {
                    item: item,
                    id: listId
                }

                if (this.id) {
                    this.updateItemAction(v)
                } else {
                    this.addItemAction(v)
                }

                this.id = null
                this.name = ''
                this.count = null
                this.price = null
            }
        }
    }

</script>

<style>
    


</style>