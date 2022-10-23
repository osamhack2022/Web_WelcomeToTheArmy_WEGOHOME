<template>
<div>
<h1 class="widget-title"> {{ tomorrow.toLocaleDateString() }} 일정 </h1>
<div class="widget">
    <p v-for="schedule in schedules" class="schedule">
    {{schedule.start}} {{ schedule.title }}</p>
</div>
</div>
</template>

<script>
import useAxios from "@app_modules/axios.js"

const { axiosGet } = useAxios()

export default {
    name: "Tomorrow Schedules Widget",
    data() {
        return {
            today: new Date(),
            tomorrow: new Date(),
            schedules: []
        }
    },
    props: {
        trainee: {
            require: true,
            default: "",
        }
    },
    created() {
        this.tomorrow.setDate(this.today.getDate() + 1)
        // this.schedules = axiosGet("schedules/"+this.tomorrow.toLocaleDateString())
        const test_schedules = [
            {
                title: "아침뜀걸음",
                start: "0700"
            },
            {
                title: "조식",
                start: "0730"
            },
            {
                title: "유격체조 실습 (제1전천후)",
                start: "0900"
            },
            {
                title: "중식",
                start: "1130"
            },
            {
                title: "유격훈련 실습 (유격훈련장)",
                start: "1300"
            },
            {
                title: "부대 미화의 날",
                start: "1600"
            },
            {
                title: "석식",
                start: "1700"
            },
            {
                title: "개인정비",
                start: "1800"
            }
        ]
        this.schedules = test_schedules
    },
    methods: {

    }
}
</script>

<style scoped>
* {
    margin: 0; padding: 0;
}
.widget-title {
    font-size: 16px;
    font-weight: bold;
}
.widget {
    width: 100%; padding: 10px 5px;
    border: solid 1px; border-radius: 8px;
}
.schedule {
    font-size: 16px;
}
</style>