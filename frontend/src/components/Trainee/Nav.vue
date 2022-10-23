<template>
<div class="background">
    <nav class="navbar sticky-top navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="/trainee/main">WELCOME TO ARMY</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    </nav>
    <div class="collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
                <router-link to="calendar" class="nav-link">훈련일정</router-link>
            </li>
            <li class="nav-item">
                <router-link to="survey" class="nav-link">조사전달</router-link>
            </li>
            <li class="nav-item">
                <router-link to="counsel" class="nav-link">상담신청</router-link>
            </li>
            <li class="nav-item">
                <router-link to="gallery" class="nav-link">훈련사진</router-link>
            </li>
            <li class="nav-item">
                <a href="https://www.youtube.com/channel/UCsEF5EAdc5fTuRmkWcFjIhA" target="_blank" class="nav-link">정신전력교육</a>
            </li>
            <li class="nav-item">
                <p @click="logout" class="nav-link">로그아웃</p>
            </li>
        </ul>
    </div>
</div>
</template>

<script>
export default {
    methods: {
        logout() {
            localStorage.clear()
            this.$router.push("/")
        }
    }
}
</script>

<style scoped>
* {
    padding: 0; margin: 0;
}
nav {
    min-height: 75px;
    width: 100%;
}
.background { 
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25); 
    background-color: rgba(248,249,250, 1)!important;
    padding-bottom: 5px;
}
.navbar-brand {
    font-family: "ROKAF Sans";
    font-size: 24px;
    font-weight: bold;
    text-align: center;

    position: absolute;
    transform: translate(-50%, -50%);
    left: 50%;
    top: 50%;
}

.navbar-toggler { margin-left: 11px; display: block;}
.show {
    width: 100%;
}
.nav-link {
    font-size: 24px;
    font-weight: bold;
    margin: 12px 0;
    text-align: center;
}
</style>
