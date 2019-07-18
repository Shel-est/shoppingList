<template>
    <v-layout align-center justify-start column>
        <v-card class="mt-3">
            <v-card-text align-center primary-title>
                <v-btn
                        flat
                        icon
                        @click="backToLists"
                ><v-icon>keyboard_backspace</v-icon></v-btn>
                <item-view
                        class="py-0"
                        v-for="item in getItemsByListId(listId).items"
                        :key="item.item.id"
                        :item="item.item"
                        :listId="listId"
                        :edit="edit"
                >
                </item-view>
                <item-form
                        :itemAttr="item"
                        :listId="listId">
                </item-form>
            </v-card-text>
        </v-card>
    </v-layout>
</template>

<script>
    import { mapGetters } from 'vuex'
    import ItemView from 'components/items/ItemView.vue'
    import ItemForm from 'components/items/ItemForm.vue'

    export default {
        data() {
            return {
                listId: parseInt(this.$route.params.listId),
                item: null
            }
        },
        components: {ItemView, ItemForm},
        computed: {
            ...mapGetters(['getItemsByListId']),
        },
        methods: {
            edit(item) {
                this.item = item
            },
            backToLists() {
                this.$router.push('/')
            }
        }
    }

</script>

<style>
    


</style>