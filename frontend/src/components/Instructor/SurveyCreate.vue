<template>
<section class="title-area">
    <h1 class="title">새 조사전달 만들기</h1>
    <span class="title">제목</span>
    <input type="text" class="data-title" v-model="survey.title" />
    <br />
    <div class="title-wrapper">
        <span class="title-sm">조사 대상 (기수, 범위)</span><br />
        <select class="form-control float-left" v-model="survey.range" style="width: 20%;">
            <option value="ALL" selected>전체</option>
            <option value="BATTALION">대대</option>
            <option value="COMPANY">중대</option>
            <option value="PLATOON">소대</option>
        </select>
        <input type="text" class="form-control float-left" placeholder="기수입력 (예시: 824)" v-model="survey.generation" style="width:20%;"/>
        <input type="text" class="form-control float-left" placeholder="4대대 3중대 1소대 -> 431" v-model="survey.belong" style="width: 40%" />
    </div>
    <div class="date-wrapper">
        <div class="title-wrapper float-left" style="width: 45%;">
            <span class="title-sm">조사 시작</span><br/>
            <input type="datetime-local" class="form-control" v-model="survey.startDate" />
        </div>
        <div class="title-wrapper float-right" style="width: 45%;">
            <span class="title-sm">조사 마감</span><br/>
            <input type="datetime-local" class="form-control" v-model="survey.endDate" />
        </div>
    </div>
</section>
<section class="questions-area">
    <div v-for="(question, i) in questionList">
        <QuestionCard :questionIndex="i" />
    </div>
</section>
<section class="button-area">
    <button class="btn btn-secondary" @click="addQuestion">질문 추가</button>
    <button class="btn btn-primary" @click="createSurvey">작성 완료</button>
</section>
</template>

<script>
import QuestionCard from "@components/Instructor/SurveyCreateQuestionCard.vue"
import useAxios from "@app_modules/axios.js"

const { axiosPost } = useAxios()

export default {
    data() {
        return {
            questionList: [],
            survey: {
                title: "",
                questions: null,
                generation: null,
                range: "ALL",
                belong: null,
            }
        }
    },
    components: {
        QuestionCard,
    },
    methods: {
        dateToString(date) {
            var strDate = date.toString()
            strDate = strDate.substr(0, 10) + " " + strDate.substr(11, 5)
            return strDate
        },
        addQuestion() {
            this.questionList.push("")
        },
        removeQuestion(index) {
            this.questionList.splice(index, 1)
        },
        updateQuestion(question, i) {
            this.questionList[i] = question
            this.$forceUpdate()
        },
        createSurvey() {
            this.survey.belong = this.survey.belong.toString()
            this.survey.startDate = this.dateToString(this.survey.startDate)
            this.survey.endDate = this.dateToString(this.survey.endDate)
            const onSuccess = (data) => {
                alert("조사전달이 성공적으로 생성되었습니다.")
                this.$router.push("/instructor/survey")
            }
            const onFailed = (data) => {
                alert("조사전달을 추가하는데 실패하였습니다.")
            }
            this.survey.questions = this.questionList
            console.log(this.survey)
            axiosPost("survey", this.survey, onSuccess, onFailed)
        }
    },
}
</script>

<style scoped>
.title-area { margin: 10px 0px; width: 100%; height: 250px;}
.title { font-size: 25px; font-weight: bold; }
.title-sm { font-size: 16px;}

.data-title {
    margin-left: 5px;
    font-size: 25px;
    width: 60%;
    border-radius: 3px;
}

.form-control { }

.title-wrapper { margin: 10px 0; width: 100%; }
.date-wrapper { width: 100%; }
.button-area {
    width: 100%; display:inline-block;
    text-align: center; align-items: center;
}

.btn {
    margin: 0px 3px;
}
.float-left {float:left;}
.float-right{float:right;}
</style>