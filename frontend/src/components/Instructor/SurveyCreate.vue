<template>
<section class="title-area">
    <h1 class="title">새 조사전달 만들기</h1>
    <span class="title">제목</span>
    <input type="text" class="data-title"/>
</section>
<section class="questions-area">
    <QuestionCard />
</section>
<section class="button-area">
    <button class="btn btn-secondary">질문 추가</button>
    <button class="btn btn-primary">작성 완료</button>
</section>
</template>

<script>
import QuestionCard from "@components/Instructor/SurveyCreateQuestionCard.vue"
export default {
    components: {
        QuestionCard,
    }
}
</script>

<style scoped>
.title-area { margin: 10px 0px; }
.title { font-size: 25px; font-weight: bold; }

.data-title {
    margin-left: 5px;
    font-size: 25px;
    width: 60%;
}

.button-area {
    width: 100%; display:inline-block;
    text-align: center; align-items: center;
}

.btn {
    margin: 0px 3px;
}
</style>