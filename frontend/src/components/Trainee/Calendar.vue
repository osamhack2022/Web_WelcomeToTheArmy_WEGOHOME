<template>
<section class="title-area">
    <h1 class="title">훈련일정</h1>
</section>
<section class="calendar-area">
    <CalendarView />
</section>
</template>

<script>
import CalendarView from "@components/Instructor/CalendarView.vue"

export default {
    components: {
        CalendarView,
    },
    data() {
    },
    methods: {
    }
}
</script>

<style scoped>
.title-area {
    margin: 25px 0px;
}
.title {
    text-align: center;
    font-size: 32px;
    font-family: ROKAF Sans;
}
.menu-area { display: inline-block; width: 100%; margin: 10px 0px;}
.range-selector { float: right; }

.btn {
    float: right;
    padding: 5px;
    margin-top: 10px;

    font-size: 12px;
    font-weight: bold;
}

.calendar-area {
    margin-bottom: 100px;
}

</style>