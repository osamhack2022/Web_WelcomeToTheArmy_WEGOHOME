<template>
<section class="title-area">
    <h1 class="title">당신은 도움받을 권리가 있습니다.</h1>
    <button class="btn btn-outline-dark" @click="createCounsel">상담신청하기</button>
</section>
</template>

<script>
export default {
    methods: {
        createCounsel() {
            this.$router.push("/trainee/createCounsel")
        }
    }
}
</script>

<style scoped>
.title-area {
    text-align: center;
    margin-top: 40vh;
}
.title { font-weight: bold; }
</style>