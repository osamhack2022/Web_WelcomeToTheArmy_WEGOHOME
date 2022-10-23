<template>
<div>
<h1 class="widget-title"> 조사전달 </h1>
<div class="widget">
    <router-link to="survey/1"><p class="survey answered">[오늘마감] 개인정보수집동의서</p></router-link>
    <p class="survey">[D-3] 개인 피복류 사이즈 조사</p>
    <p class="survey urgent">[D-5] 상담 희망자 조사</p>
</div>
</div>
</template>

<script>
export default {
    name: "Survey Widget",
    data() {
        return {

        }
    },
    props: {
        trainee: {
            require: true,
            default: "",
        }
    },
    methods: {

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
.urgent {
    color: red !important;
}
.answered {
    color: lightgray !important;
}
</style>