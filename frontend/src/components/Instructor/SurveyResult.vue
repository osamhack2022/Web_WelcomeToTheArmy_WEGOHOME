<template>
<section class="title-area">
    <h1 class="title">{{survey.title}}</h1>
</section>
<section class="response-info-area">
    <p class="info-text">응답자 수: {{survey.answeredNum}}</p>
</section>
<section class="controller-area">
  <button class="controller-btn btn btn-secondary btn-left" type="button" @click="decreaseId">◀</button>
  <div class="responder-info">  
    <p class="responder-name">응답자: {{survey_results[answer_id].soldierName}}</p>
    <p class="responder-name">응답시간: {{survey_results[answer_id].createdDate}}</p>
  </div>
  <button class="controller-btn btn btn-secondary btn-right" type="button" @click="increaseId">▶</button>
</section>
<section class="response-area">
  <div v-for="question in survey.questions">
            <div class="question-box" v-if="question.type=='주관식'">
                <h3 class="question-title">{{ question.title }}</h3>
                <p class="question-description">{{ question.description }}</p>
                <hr />
                <input type="text" class="form-control" v-model="survey_results[answer_id].answers[question.id].answer" disabled/>
            </div>
            <div class="question-box" v-if="question.type=='객관식'">
                <h3 class="question-title">{{ question.title }}</h3>
                <p class="question-description">{{ question.description }}</p>
                <hr />
                <div class="form-check" v-for="option in question.options">
                    <input type="radio" class="form-check-input" :name="survey" :id="option" :value="option" v-model="survey_results[answer_id].answers[question.id].answer" disabled/>
                    <label class="form-check-label" :for="option">{{ option }}</label>
                </div>
            </div>
        </div>
</section>
</template> 

<script>
import useAxios from "@app_modules/axios.js"

const { axiosGet } = useAxios()

export default {
    data() {
        return {
            survey: Object,
            survey_results: [],
            answer_id: 0,
        }
    },
    methods: {
        loadSurvey() {
            const onSuccess = (data) => {
                this.survey = data.data
            }
            const onFailed = (data) => {
                alert("조사전달을 받아오지 못했습니다.")
            }
            axiosGet("survey/"+this.$route.params.id, onSuccess, onFailed)
        },
        loadAnswers() {
            const onSuccess = (data) => {
                this.survey_results = data.data
                console.log(data.data)
            }
            const onFailed = (data) => {
                alert("응답을 받아오지 못했습니다.")
            }
            axiosGet("survey-answer/all/"+this.$route.params.id, onSuccess, onFailed)
        },
        increaseId() {
            if (this.survey.total - 1 > this.answer_id) {
                this.answer_id++
            }
        },
        decreaseId() {
            if (this.answer_id > 0) {
                this.answer_id--
            }
        }
    },
    created() {
        this.loadSurvey()
        this.loadAnswers()
    },
}
</script>

<style scoped>
.title-area {
    width: 100%;
}
.title {
    margin: 20px 0px;
    text-align: center;
    font-size: 32px; font-weight: bold; font-family: ROKAF Sans;
}
.info-text {
    margin: 0; text-align: right;
}

.controller-area { width: 100%; height: 40px; margin: 20px 0px; position: relative;}

.btn-left { float: left; }
.responder-info { text-align: center; position: absolute; left: 50%; transform: translate(-50%, -30%); margin-top: 10px;}
.responder-name { margin: 0;  font-size: 14px;}
.btn-right { float: right;}

.response-area { width: 100%; }
.question-box {
    background-color: #D9D9D9; width: 100%;
    border: solid 1px black; border-radius: 8px;
    padding: 5px 5px; margin: 20px 0px; 
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.question-title { font-size: 24px; font-weight: bold; margin: 0; }
.question-description { font-size: 16px; }
</style>