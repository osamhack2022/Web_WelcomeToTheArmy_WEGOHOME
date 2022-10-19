<template>
<section class="title-area">
    <h1 class="title">새 조사전달 만들기</h1>
    <span class="title">제목</span>
    <input type="text" class="data-title" v-model="survey.title" />
</section>
<section class="questions-area">
    <div v-for="(question, i) in questionList">
        <QuestionCard :questionIndex="i"/>
    </div>
</section>
<section class="button-area">
    <button class="btn btn-secondary" @click="addQuestion">질문 추가</button>
    <button class="btn btn-primary">작성 완료</button>
</section>
</template>

<script>
import QuestionCard from "@components/Instructor/SurveyCreateQuestionCard.vue"
export default {
    data() {
        return {
            questionList: [""],
            survey: {
                title: "",
            }
        }
    },
    components: {
        QuestionCard,
    },
    methods: {
        addQuestion() {
            this.questionList.push("")
        },
        removeQuestion(index) {
            this.questionList.splice(index, 1)
        }
    },
}
</script>

<style scoped>
.title-area { margin: 10px 0px; }
.title { font-size: 25px; font-weight: bold; }

.data-title {
    margin-left: 5px;
    font-size: 25px;
    width: 60%;
    border-radius: 3px;
}

.button-area {
    width: 100%; display:inline-block;
    text-align: center; align-items: center;
}

.btn {
    margin: 0px 3px;
}
</style>