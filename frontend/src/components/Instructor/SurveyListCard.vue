<template>
<section class="survey-card" :class="class" @click="loadResult">
    <div class="survey-left">
        <span class="text-main">{{propSurvey.title}}</span><br />
        <span class="text-sub">{{propSurvey.endDate}}까지</span> 
    </div>
    <div class="survey-right">
        <span class="text-main">{{status}} (36%)</span><br />
        <span class="text-sub">작성자: {{propSurvey.title}}</span>
    </div>
</section>
</template>

<script>
export default {
    props: {
        propSurvey: Object,
    },
    data() {
        return {
            class: "",
            status: "",
        }
    },
    methods: {
        checkStatus(date) {
            const today = new Date()
            const d = new Date(date)
            if (d < today) {
                return "survey-complete"
            }
            return "survey-on"
        },
        loadResult() {
            this.$router.push("/instructor/survey/"+this.propSurvey.id)
        }
    },
    mounted() {
        this.class = this.checkStatus(this.propSurvey.endDate)
        if (this.class==="survey-on") {
            this.status = "조사 중"
        } else {
            this.status = "조사 완료"
        }
    }
}
</script>

<style scoped>
.survey-card {
    width: 100%;
    margin: 5px 0px; padding: 10px;
    border-radius: 15px;
    display: inline-block;
}
.survey-complete { background-color: #CECECE; }
.survey-on { background-color: #FFF59D ;}

.survey-left { float: left; text-align: left;}
.survey-right { float: right; text-align: right;}

.text-main {
    font-size: 25px;
    font-weight: bold;
    font-family: ROKAF Sans;
}
.text-sub {
    font-size: 15px;
}
</style>