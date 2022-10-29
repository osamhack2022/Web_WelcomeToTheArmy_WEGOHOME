<template>
<section class="title-area">
    <h3>상담 제목</h3>
    <input type="text" class="form-control" />
    <h5>상담 내용</h5>
    <textarea class="form-control" style="height: 55vh;"></textarea>
    <h5>비밀번호</h5> 
    <input type="password" class="form-control" />
</section>
<section class="submit-area">
    <hr />
    <p class="text-muted">모든 상담 내용은 상담 담당관 이외에게는 철저한 비밀이 보장됩니다.</p>
    <hr />
    <button class="btn btn-primary" @click="submitCounsel">상담신청</button>
</section>

</template>

<script>
export default {
    methods: {
        submitCounsel() {
            this.$router.push("/trainee/counselSubmit")
        }
    }
}
</script>

<style scoped>
.title-area {
    border: 1px solid;
    padding: 5px;
    border-radius: 5px;
    background-color: #D9D9D9;
}
.submit-area {
    text-align: center;
}
</style>