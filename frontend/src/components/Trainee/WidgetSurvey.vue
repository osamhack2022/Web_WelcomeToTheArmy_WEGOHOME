<template>
<div>
<h1 class="widget-title"> 조사전달 </h1>
<div class="widget">
    <div v-for="survey in surveyList">
        <router-link :to="'survey/'+survey.id" :class="survey.class"><p class="survey">{{survey.title}}</p></router-link>
    </div>
</div>
</div>
</template>

<script>
import useAxios from "@app_modules/axios.js"

const { axiosGet } = useAxios()

export default {
    name: "Survey Widget",
    data() {
        return {
            surveyList: [],
        }
    },
    props: {
        trainee: {
            require: true,
            default: "",
        }
    },
    created() {
        const onSuccess = (data) => {
            this.surveyList = data.data
        }
        const onFailed = (data) => {
            alert("조사전달을 받아오지 못했습니다.")
        }
        axiosGet("survey/?loadCompleted=true", onSuccess, onFailed)
    },
    updated() {
        for (var survey of this.surveyList) {
            survey.class = "normal"
            if (new Date(survey.endDate).toDateString() == new Date().toDateString()) {
                survey.class = "urgent"
            }
            if (survey.answered) {
                survey.class = "answered"
            }
        }
    }
}
</script>

<style scoped>
* {
    margin: 0; padding: 0;
}
a {
    text-decoration: none;
    color: black;
}
.widget-title {
    font-size: 16px;
    font-weight: bold;
}
.widget {
    width: 100%; padding: 10px 5px;
    border: solid 1px; border-radius: 8px;
}
.survey {
    font-size: 16px;
}
.survey:hover {
    border-radius: 3px;
    background-color: #D9D9D9;
}
.normal {
    color: red;
}
.urgent {
    color: red !important;
    font-weight: bold;
}
.answered {
    color: lightgray !important;
}
</style>