<template>
<div class="widget">
    <p class="trainee-profile">신병4대대 3중대 1소대 26번 백지오 훈련병</p>
    <p class="left-msg">수료까지 <span class="day">20</span>일</p>
</div>
</template>

<script>
export default {
    name: "DayLeft Widget",
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
.widget {
    width: 100%; padding: 10px 0px;
    border: solid 1px; border-radius: 8px;
}
p {
    text-align: center;
}
.trainee-profile {
    font-size: 16px; font-weight: bold;
}
.left-msg {
    font-size: 35px; font-weight: bold;
}
.day {
    font-size: 50px; font-weight: 800; color: red;
}

</style>