<template>
    <v-app>
        <v-toolbar app>
        <v-toolbar-title>Список покупок</v-toolbar-title>
        <v-spacer></v-spacer>
        <span v-if="profile">
            {{profile.name}}
        </span>
        <v-btn v-if="profile" icon href="/logout">
            <v-icon>exit_to_app</v-icon>
        </v-btn>
        </v-toolbar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import { mapState } from 'vuex'

    export default {
        computed: mapState(['profile']),
        methods: {
            showActive() {
                this.$router.push('/')
            },
            showCompleted() {
                this.$router.push('/completed')
            },
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/auth')
            }
        }
    }

</script>

<style>
    


</style>