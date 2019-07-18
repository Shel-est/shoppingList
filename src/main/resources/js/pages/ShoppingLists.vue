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
                        <span>Последний день чтобы всё купить</span>
                        <v-spacer></v-spacer>
                        <span>{{list.deadline}}</span>
                    </v-layout>
                    <v-layout>
                        <v-btn @click="edit(list)" small>
                            Изменить
                        </v-btn>
                        <v-btn @click="complete(list)" small>
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
    import ShoppingListForm from 'components/ShoppingLists/ShoppingListForm.vue'

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
            ...mapActions(['removeShoppingListAction', 'updateShoppingListStatusAction', 'updateShoppingListAction']),
            totalCost(list) {
                var total = 0
                for (var i = 0; i < list.items.length; i++) {
                    total += list.items[i].item.price * list.items[i].item.count
                }
                return total
            },
            edit(listAttr) {
                this.listAttr = listAttr
            },
            complete(list) {
                if (list.isPeriodic) {
                    switch (list.period) {
                        case 'Day':
                            var newDeadline = new Date(list.deadline)
                            newDeadline.setDate(newDeadline.getDate() + 1)
                            list.deadline = newDeadline.toISOString().substr(0, 10)
                            break
                        case 'Week':
                            var newDeadline = new Date(list.deadline)
                            newDeadline.setDate(newDeadline.getDate() + 7)
                            list.deadline = newDeadline.toISOString().substr(0, 10)
                            break
                        case 'Month':
                            var newDeadline = new Date(list.deadline)
                            newDeadline.setMonth(newDeadline.getMonth() + 1)
                            list.deadline = newDeadline.toISOString().substr(0, 10)
                            break
                        default:
                            break
                    }
                    this.updateShoppingListAction(list)
                } else {
                    this.updateShoppingListStatusAction(list.id)
                }
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