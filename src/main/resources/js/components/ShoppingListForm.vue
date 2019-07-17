<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container>
        <v-layout column>
            <v-flex xs12 sm6>
                <v-text-field
                        placeholder="Название списка"
                        v-model="name"
                />
            </v-flex>
            <v-flex xs12 sm6>
                <v-text-field
                        placeholder="Описание"
                        v-model="description"
                />
            </v-flex>
            <v-flex xs12 sm6>
                <v-dialog
                        ref="dialog"
                        v-model="modal"
                        :return-value.sync="deadline"
                        persistent
                        lazy
                        full-width
                        width="290px"
                >
                    <template v-slot:activator="{ on }">
                        <v-text-field
                                v-model="deadline"
                                label="Введите дату покупки"
                                prepend-icon="event"
                                readonly
                                v-on="on"
                        ></v-text-field>
                    </template>
                    <v-date-picker v-model="deadline" scrollable locale="ru-ru">
                        <v-spacer></v-spacer>
                        <v-btn flat color="primary" @click="modal = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="$refs.dialog.save(deadline)">OK</v-btn>
                    </v-date-picker>
                </v-dialog>
                <V-layout align-center>
                    <span class="pr-3">Повторять</span>
                    <v-checkbox v-model="isPeriodic"></v-checkbox>
                    <v-select
                            v-if="isPeriodic"
                            placeholder="Как часто?"
                            :items="periods"
                            label="Период"
                            v-model="str"
                    ></v-select>
                </V-layout>
            </v-flex>
            <v-btn @click="addNewList">
                Добавить список
            </v-btn>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        props: ['listAttr'],
        data() {
            return {
                id: null,
                name: '',
                description: '',
                deadline: null,
                isPeriodic: false,
                period: null,
                modal: false,
                deadline: new Date().toISOString().substr(0, 10),
                periods: ['Каждый день', 'Каждую неделю', 'Каждый месяц'],
                str: ''
            }
        },
        watch: {
            listAttr(newVal, oldVal) {
                this.id = newVal.id
                this.name = newVal.name
                this.description = newVal.description
                this.deadline = newVal.deadline
                this.isPeriodic = newVal.isPeriodic
                this.period = newVal.period
            }
        },
        methods: {
            ...mapActions(['addShoppingListAction', 'updateShoppingListAction']),
            getPeriod(str) {
                switch(str) {
                    case 'Каждый день':
                        return 0
                    case 'Каждую неделю':
                        return 1
                    case 'Каждый месяц':
                        return 2
                }
            },
            addNewList() {
                const list = {
                    id: this.id,
                    name: this.name,
                    description: this.description,
                    deadline: this.deadline,
                    isPeriodic: this.isPeriodic,
                    period: this.getPeriod(this.str)
                }

                if (this.id) {
                    this.updateShoppingListAction(list)
                } else {
                    this.addShoppingListAction(list)
                }

                this.id = null
                this.name = ''
                this.description = ''
                this.deadline = new Date().toISOString().substr(0, 10)
                this.isPeriodic = false
                this.period = null
                this.str = ''
            }
        }
        
    }

</script>

<style>

</style>