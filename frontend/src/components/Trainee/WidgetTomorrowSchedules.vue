<template>
<div>
<h1 class="widget-title"> {{ tomorrow.toLocaleDateString() }} 일정 </h1>
<div class="widget">
    <p v-for="schedule in schedules" class="schedule">
        <div class="schedule-time">{{schedule.startTime}}</div>  <div class="schedule-name">{{ schedule.name }}</div>
    </p>
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
        const onSuccess = (data) => {
            this.schedules = data.data
        }
        const onFailed = (data) => {
            alert("조사전달을 받아오지 못했습니다.")
        }
        axiosGet("schedule?date=2022-10-29", onSuccess, onFailed)
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
.schedule-time {width: 50px; margin:0; float:left;}
.schedule-name {width: 200px; margin:0;}
</style>