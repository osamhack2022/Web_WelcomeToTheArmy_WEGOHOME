<template>
<div>
<h1 class="widget-title"> {{ tomorrow.toLocaleDateString() }} 일정 </h1>
<div class="widget">
    <p class="schedules">0700 아침뜀걸음<br />
    0730 조식<br />
    0900 유격체조 실습 (제1전천후)<br />
    1130 중식<br />
    1300 유격훈련 실습 (유격훈련장)<br />
    1600 부대 미화의 날<br />
    1700 석식<br />
    1800 개인정비</p>
</div>
</div>
</template>

<script>
export default {
    name: "Tomorrow Schedules Widget",
    data() {
        return {
            today: new Date(),
            tomorrow: new Date(),
        }
    },
    props: {
        trainee: {
            require: true,
            default: "",
        }
    },
    created: function () {
        this.tomorrow.setDate(this.today.getDate() + 1)
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
.schedules {
    font-size: 16px;
    font-weight: bold;
}
</style>