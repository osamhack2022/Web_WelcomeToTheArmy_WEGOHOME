<template>
    <Nav />
    <div class="container">
        <router-view />
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
</style>