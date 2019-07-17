<template>
    <v-container>
        <v-text-field
            placeholder="Имя пользователя"
            v-model="username"
        />
        <v-text-field
            placeholder="Пароль"
            v-model="password"
        />
        <v-btn
            @click="auth"
        >Войти</v-btn>
    </v-container>
</template>

<script>
    export default {
        data() {
            return {
                username: '',
                password: ''
            }
        },
        methods: {
            auth() {
                Vue.http.post('/login')
            }
        },
    }

</script>

<style>
    


</style>