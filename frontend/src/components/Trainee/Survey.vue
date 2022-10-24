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
                    <label class="form-check-label" :for="option">{{ option }}</label>
                </div>
            </div>
        </div>
    </section>
    <section class="buttons-area">
        <button type="submit" @click.prevent="submitSurveyAnswer" class="btn btn-primary">제출</button>
    </section>
    </form>
</template>

<script>
import useAxios from "@app_modules/axios.js"

const { axiosGet, axiosPost } = useAxios()

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
        const onSuccess = (data) => {
            this.survey = data.data
        }
        const onFailed = (data) => {
            alert("조사전달을 받아오지 못했습니다.")
        }
        axiosGet("survey/"+this.$route.params.id, onSuccess, onFailed)
        
        this.survey_result.response_time = new Date()
    },
    methods: {
        submitSurveyAnswer() {
            const onSuccess = (data) => {
                alert("응답이 저장되었습니다.")
            }
            const onFailed = (data) => {
                alert("응답을 저장하는데 실패했습니다.")
            }
            axiosPost("/survey/answer", this.survey_result, onSuccess, onFailed)
        }
    }
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

.button-area { width: 100%; }
.btn-primary { text-align: center; width: 16%; margin: 0 42%;  }

</style>