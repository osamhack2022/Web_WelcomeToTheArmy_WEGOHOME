<template>
<div class="background">
    <section class="container">
        <h1 class="brand">WELCOME TO ARMY</h1>
        <button class="btn btn-outline-dark btn-lg" @click="goInstructor">관리자 페이지</button><br />
        <button class="btn btn-outline-dark btn-lg" @click="goTrainee">훈련병 페이지</button>
    </section>
</div>
</template>

<script>
export default{
    methods: {
        goInstructor() {
            if (localStorage.getItem("traineeLoginToken")){
                localStorage.removeItem("traineeLoginToken")
            }
            this.$router.push("instructor")
        },
        goTrainee() {
            if (localStorage.getItem("instructorLoginToken")){
                localStorage.removeItem("instructorLoginToken")
            }
            this.$router.push("trainee")
        }
    }
}
</script>

<style scoped>
.background {
    width: 100vw; height: 100vh;
    background-color: orange;
}
.container {
    align-items: center; text-align: center;
    position: absolute;
    transform: translate(-50%, -50%);
    left: 50%;
    top: 50%;
}

.brand {
    font-family: "ROKAF Sans";
    font-size: 24px; font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
}
.btn {
    width: 100%;
    font-weight: bold;
    background-color: white;
    margin: 10px 0px;
}
</style>