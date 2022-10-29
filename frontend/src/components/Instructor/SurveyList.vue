<template>
    <section class="title-area">
        <h1 class="title">진행 중인 조사전달</h1>
    </section>
    <section class="button-area">
        <router-link to="/instructor/survey/create"><button class="btn btn-outline-dark">조사전달 추가</button></router-link>
    </section>
    <section class="list-area">
        <SurveyListCard v-for="survey in surveyList" :propSurvey="survey" />
    </section>
</template>

<script>
import SurveyListCard from "@components/Instructor/SurveyListCard.vue"
import useAxios from "@app_modules/axios.js"

const { axiosGet } = useAxios()

export default {
    data() {
        return {
            surveyList: null
        }
    },
    components: {
        SurveyListCard,
    },
    created() {
        const onSuccess = (data) => {
            this.surveyList = data.data
        }
        const onFailed = (data) => {
            alert("조사전달을 받아오지 못했습니다.")
        }
        axiosGet("survey/?loadCompleted=true", onSuccess, onFailed)
    }
}
</script>

<style scoped>
.title {
    margin: 20px 0px;
    text-align: center;
    font-size: 32px; font-weight: bold; font-family: ROKAF Sans;
}
.button-area {
    width: 100%;
    display: inline-block;
}
.btn {
    float: right;
    padding: 5px;
    margin: 5px 0px;

    font-size: 12px;
    font-weight: bold;
}
</style>