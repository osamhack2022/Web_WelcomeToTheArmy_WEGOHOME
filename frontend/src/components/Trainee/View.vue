<template>
    <Nav />
    <div class="container">
        <router-view />
    </div>
</template>

<script>
import Nav from "@components/Trainee/Nav.vue";

export default {
    components: {
        Nav,
    }
}
</script>

<style scoped>
.container {
    margin-top: 10px;
}
</style>