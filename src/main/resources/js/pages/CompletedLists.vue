<template>
    <v-container>
        <v-layout align-center justify-start column>
            <v-card class="my-2" v-for="list in sortedLists" width="450" v-if="!list.isBought">
                <v-card-text align-center primary-title>
                    <v-layout class="pb-3" align-center>
                        <span class="pr-2">{{list.name}}</span>
                        <v-spacer></v-spacer>
                        <span>Общая сумма = {{totalCost(list)}}</span>
                        <span class="pl-4">{{boughtItemsCount(list.id)}}/{{list.items.length}}</span>
                    </v-layout>
                    <v-textarea
                            v-if="list.description"
                            box
                            readonly
                            label="Описание"
                            auto-grow
                            :value="list.description"
                    ></v-textarea>
                    <v-layout>
                        <v-btn @click="edit(list)" small>
                            Изменить
                        </v-btn>
                        <v-btn @click="complete" small>
                            Завершить
                        </v-btn>
                        <v-btn @click="addItems(list.id)" small>
                            Открыть список
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn
                                icon
                                @click="del(list)"
                                small
                        >
                            <v-icon>delete</v-icon>
                        </v-btn>
                    </v-layout>
                </v-card-text>
            </v-card>
        </v-layout>
        <shopping-list-form :listAttr="listAttr"></shopping-list-form>
    </v-container>
</template>

<script>
    import { mapGetters } from 'vuex'
    import { mapActions } from 'vuex'
    import ShoppingListForm from 'components/ShoppingListForm.vue'

    export default {
        components: {ShoppingListForm},
        data() {
            return {
                listAttr: {},
                checkbox: false
            }
        },
        computed: {
            ...mapGetters(['sortedLists', 'boughtItemsCount']),
        },
        methods: {
            ...mapActions(['removeShoppingListAction']),
            totalCost(list) {
                var total = 0
                for (var i = 0; i < list.items.length; i++) {
                    total += list.items[i].item.price
                }
                return total
            },
            edit(listAttr) {
                this.listAttr = listAttr
            },
            complete() {

            },
            del(list) {
                this.removeShoppingListAction(list)
            },
            addItems(listId) {
                this.$router.push(`/items/${listId}`)
            }
        }
    }

</script>

<style>

</style>