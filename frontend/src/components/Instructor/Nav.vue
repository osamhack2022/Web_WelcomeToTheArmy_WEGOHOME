<template>
    <nav class="navbar sticky-top">
        <div class="nav-top">
            <div class="nav-left">
                <a class="navbar-brand" href="/instructor/trainee">WELCOME TO ARMY</a>
            </div>
            <div class="nav-right">
                <div class="profile">
                    <p class="user-name">{{userInfo.rank}} {{userInfo.name}}</p>
                    <p class="user-position">{{userInfo.position}}</p>
                </div>
                <img class="user-image rounded-circle" src="@/assets/images/instructor_profile_example.jpg" alt="user profile image" />
            </div>
        </div>
        <div class="nav-bottom">
            <!-- 병사관리 훈련일정 조사전달 병사상담 훈련사진 공지사항 -->
            <router-link to="/instructor/trainee" class="nav-link">병사관리</router-link>
            <router-link to="/instructor/calendar" class="nav-link">훈련일정</router-link>
            <router-link to="/instructor/survey" class="nav-link">조사전달</router-link>
            <router-link to="#" class="nav-link">병사상담</router-link>
            <router-link to="#" class="nav-link">훈련사진</router-link>
            <router-link to="#" class="nav-link">공지사항</router-link>
            <router-link to="/instructor/admin" class="nav-link">훈육관관리</router-link>
        </div>
    </nav>
</template>

<script>
import useAxios from "@app_modules/axios.js"

const { axiosGet } = useAxios()

export default {
    data() {
        return {
            userInfo: null,
        }
    },
    created() {
        if(localStorage.getItem("userInfo")){
            this.userInfo = JSON.parse(localStorage.getItem("userInfo"))
            return
        }
        const onSuccess = (data) => {
            this.userInfo = data.data
            localStorage.setItem("userInfo", JSON.stringify(this.userInfo))
        }
        const onFailed = (data) => {
            alert("유저 정보를 받아오지 못했습니다.")
            this.$router.push("/inslogin")
        }
        axiosGet("manager/"+localStorage.getItem("userId"), onSuccess, onFailed)
    }
}
</script>

<style scoped>

* {
    padding: 0; margin: 0;
}

nav {
    height: 100px;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}

.nav-top {
    width: 100%; height: 75px;
    background-color: #F2F2F2;
    display: inline-block;
    align-items: center;
    padding: 0px 10px;
}

.nav-left {
    float: left;
    height: 100%;
    display: flex;
    align-items: center;
}

.navbar-brand {
    margin: 0; padding: 0;
    font-family: "ROKAF Sans";
    font-size: 24px;
    font-weight: bold;
}

.nav-right {
    float: right;
    height: 100%;
    text-align: right;
    display: flex;
    align-items: center;
}

.user-name {font-size: 24px; font-weight: bold;}
.user-position {font-size: 12px;}
.user-image {width: 45px; height: 45px; margin-left: 10px; object-fit: cover;}

.nav-bottom {
    width: 100%; height: 25px;
    background-color: black;
    color: #F2F2F2;
    display: flex;
}

.nav-link {
    margin: 0px 10px;
}

</style>