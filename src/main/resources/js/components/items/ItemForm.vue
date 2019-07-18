<template>
    <v-container>
        <v-layout>
            <v-text-field
                    placeholder="Что?"
                    v-model="name"
                    :rules="[rules.required]"
            />
            <v-text-field
                    placeholder="Сколько?"
                    v-model="count"
                    :rules="[rules.numbers]"
            />
            <v-text-field
                    placeholder="Цена за шт?"
                    v-model="price"
                    :rules="[rules.numbers]"
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
                count: 1,
                price: 0,
                rules: {
                    required: value => !!value || 'Нельзя купить \"ничего\"',
                    numbers: value => {
                        const pattern = /^[0-9]+$/
                        return pattern.test(value) || 'Только цифры'
                    }
                },
                true: true
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
                const pattern = /^[0-9]+$/
                if (this.name === '' ||
                    !pattern.test(this.count) ||
                    !pattern.test(this.price)
                ) {
                    return
                }
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
                this.count = 1
                this.price = 0
            }
        }
    }

</script>

<style>
    


</style>