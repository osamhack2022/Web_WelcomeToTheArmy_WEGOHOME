<template>
    <section class="title-area">
        <h1 class="survey-title">{{ survey.title }}</h1> 
    </section>
    <form>
    <section class="questions-area">
        <div v-for="question in survey.questions">
            <div class="question-box" v-if="question.type=='주관식'">
                <h3 class="question-title">{{ question.title }}</h3>
                <p class="question-description">{{ question.description }}</p>
                <hr />
                <input type="text" class="form-control" placeholder="응답입력" v-model="this.survey_result.answers[question.title]" required/>
            </div>
            <div class="question-box" v-if="question.type=='객관식'">
                <h3 class="question-title">{{ question.title }}</h3>
                <p class="question-description">{{ question.description }}</p>
                <hr />
                <div class="form-check" v-for="option in question.options">
                    <input type="radio" class="form-check-input" :name="survey" :id="option" :value="option" v-model="this.survey_result.answers[question.title]" required/>
                    <label class="form-check-label">{{ option }}</label>
                </div>
            </div>
        </div>
    </section>
    <section class="buttons-area">
        <button type="submit" class="btn btn-primary">제출</button>
    </section>
    </form>
</template>

<script>
import useAxios from "@app_modules/axios.js"

const { axiosGET } = useAxios()

export default {
    data() {
        return {
            survey: Object,
            survey_result: {
                response_time: "",
                answers: {},
            },
        }
    },
    created() {
        //survey = axiosGET("survey/"+this.$route.params.id)
        const test_survey = {
            "title": "테스트 조사전달",
            "questions": [
                {
                    "type": "주관식",
                    "title": "이름이 뭐에용",
                    "description": "주관식 질문 예시",
                    "options": [
                        "",
                        ""
                    ]
                },
                {
                    "type": "객관식",
                    "title": "좋아하는 베라 맛은?",
                    "description": "객관식 질문 예시",
                    "options": [
                        "민트초코",
                        "초코나무숲",
                        "슈팅스타"
                    ]
                }
            ]
        }
        this.survey = test_survey
        this.survey_result.response_time = new Date()
    },
}
</script>

<style scoped>

.survey-title { font-size: 32px; font-weight: bold; }
.title-area { margin: 10px 0px; }

.question-box {
    background-color: #D9D9D9; width: 100%;
    border: solid 1px black; border-radius: 8px;
    padding: 5px 5px; margin: 20px 0px; 
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.question-title { font-size: 24px; font-weight: bold; margin: 0; }
.question-description { font-size: 16px; }

hr { margin: 0; margin-bottom: 15px; }
.form-check-input { padding: 1px 2px; }

</style>