<template>
<section class="title-area">    
    <h1>상담 신청이 접수되었습니다.</h1>
    <h4>곧 상담관이 배정되어 알림톡으로 안내될 예정입니다.</h4>
    <button @click="backToMain" class="btn btn-outline-dark">메인화면으로 돌아가기</button>
</section>
</template>

<script>
export default {
    methods: {
        backToMain() {
            this.$router.push("/trainee/main")
        }
    }
}
</script>

<style scoped>
.title-area {
    text-align:center;
    position: absolute;
    left: 50%; top: 50%;
    width: 100%;
    transform: translate(-50%, -50%);
}
</style>