<template>
    <v-container>
        <v-layout align-center>
            <v-checkbox
                    v-model="isBought"
                    @change="updateStatus(item, listId)"
            ></v-checkbox>
            <v-container align-left>{{this.item.name}}</v-container>
            <v-container align-right>{{this.item.count}}</v-container>
            <v-container align-right>Цена = {{this.item.price * this.item.count}}</v-container>
            <v-btn
                    align-righ
                    icon
                    small
                    @click="editItem()"
            >
                <v-icon>edit</v-icon>
            </v-btn>
            <v-btn
                    align-righ
                    icon
                    small
                    @click="del(item, listId)"
            >
                <v-icon>delete</v-icon>
            </v-btn>
        </v-layout>
        <v-divider></v-divider>
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'
    import { mapGetters } from 'vuex'
    import itemsApi from 'api/items'

    export default {
        props: ['item', 'listId', 'edit'],
        data() {
            return {
                isBought: this.$store.getters.getBought({
                    itemId: this.item.id,
                    listId: this.listId
                })
            }
        },
        computed: {
            ...mapGetters(['getBought']),
            getIds() {
                const ids = {
                    itemId: this.item.id,
                    listId: this.listId
                }

                return ids
            }
        },
        methods: {
            ...mapActions(['removeItemAction']),
            del(item, listId) {
            const v = {
                item: item,
                id: listId
            }
                this.removeItemAction(v)
            },
            editItem() {
                this.edit(this.item)
            },
            async updateStatus(item, listId) {
                const v = {
                    itemId: this.item.id,
                    listId: this.listId
                }
                await itemsApi.updateStatus(v)
            }
        }
    }


</script>

<style>
    


</style>