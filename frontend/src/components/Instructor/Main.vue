<template>
    <div class="background">
        <Nav />
        <div class="container">
            <router-view />
        </div>
    </div>
</template>

<script>
import Nav from "@components/Instructor/Nav.vue";

export default {
    components: {
        Nav,
    }
}
</script>

<style scoped>
    .background {
        
    }
</style>