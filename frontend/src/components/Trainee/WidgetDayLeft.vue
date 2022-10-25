<template>
<div class="widget">
    <p class="trainee-profile" v-if="profile">{{profile}}</p>
    <p class="left-msg">수료까지 <span class="day">{{ day_left }}</span>일</p>
</div>
</template>

<script>
import useAxios from "@app_modules/axios.js"

const { axiosGet } = useAxios()

export default {
    name: "DayLeft Widget",
    data() {
        return {
            today: new Date(),
            dday: new Date(),
            day_left: 0,
            userInfo: null,
            profile: "",
        }
    },
    props: {
        trainee: {
            require: true,
            default: "",
        }
    },
    created() {
        //this.dday = axiosGet("dday")
        this.dday = new Date('2022-12-14 00:00:00')
        this.day_left = parseInt((this.dday - this.today) / 86400000)

        try {
            this.userInfo = JSON.parse(localStorage.getItem("userInfo"))
            this.userInfo.number = this.userInfo.platoonNum.substr(3, 2)
            this.profile = this.userInfo.battalion + "대대 " + this.userInfo.company + "중대 " + this.userInfo.platoon + "소대 " + this.userInfo.number + "번 " + this.userInfo.name + " 훈련병"
        } catch(err) {
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
    font-size: 16px; margin-bottom: -15px;
}
.left-msg {
    font-size: 35px; font-weight: bold;
}
.day {
    font-size: 50px; font-weight: 800; color: red;
}

</style>