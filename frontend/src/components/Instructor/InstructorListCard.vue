<template>
    <div class="trainee-card">
        <section class="head" @click="toggleBody($event)">
            <div class="head-left">
                <img class="trainee-image rounded-circle" src="@/assets/images/instructor_profile_example.jpg" alt="훈련병 프로필 이미지" />
                <div class="trainee-profile">
                    <h1 class="trainee-name">상사 김훈련</h1>
                    <h1 class="trainee-position">신병 4대대 3중대 1소대장</h1>
                </div>
            </div>
            <div class="head-right">
                <p class="flag"></p>
            </div>
        </section>
        <form class="form-c">
            <section class="body" style="display:none;">
                <div class="data-area">
                    <div class="data-field">
                        <p class="data-label">이름</p>
                        <input class="data-input data-text" type="text" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">계급</p>
                        <input class="data-input data-text" type="text" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">군번</p>
                        <input class="data-input data-text" type="text" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">직책</p>
                        <input class="data-input data-text" type="text" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">소속</p>
                        <input class="data-input data-text" type="text" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">연락처</p>
                        <input class="data-input data-text" type="tel" pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}" placeholder="010-1234-5678" required>
                    </div>
                </div>
            </section>
            <section class="button-area">
                <button class="btn btn-primary">수정</button> 
                <button class="btn btn-danger" type="button">삭제</button>
                <button class="btn btn-outline-dark" @click="cancelChange($event)" type="button">취소</button>
            </section>
        </form>
    </div>
</template>

<script>
    export default {
        methods: {
            toggleBody(event) {
                const card = event.currentTarget.parentNode;
                const body = card.childNodes[1].firstChild;
                const buttons = card.childNodes[1].childNodes[1];
                if (body.style.display === "none") {
                    card.style.height = "auto";
                    body.style.display = "block";
                    buttons.style.display = "inline-block";
                }
                else {
                    card.style.height = "60px";
                    body.style.display = "none";
                    buttons.style.display = "none";
                }
            },
            cancelChange(event) {
                const card = event.currentTarget.parentNode.parentNode.parentNode;
                const body = card.childNodes[1].firstChild;
                const buttons = card.childNodes[1].childNodes[1];
                card.style.height = "60px";
                body.style.display = "none";
                buttons.style.display = "none";
            }
        }
    }
</script>

<style scoped>
* {
    margin: 0; padding: 0;
}

.trainee-card {
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 8px;
    height: 60px;
    overflow: hidden;
}

.head {
    background-color: #D9D9D9;
    width: 100%; height: 60px;
    display: inline-block;
    align-items: center;
}

.head > div {
    height: 100%;
    display: flex;
    align-items: center;
}

.head-left {
    float: left;
}

.trainee-image {width: 45px; height: 45px; margin-left: 10px; object-fit: cover;}
.trainee-profile {margin-left: 3px;}
.trainee-name {font-size: 20px; font-weight: bold; }
.trainee-position {font-size: 15px;}

.head-right {
    float: right;
}

.flag {
    margin-right: 10px; 
    text-align: right; font-size: 25px;
}

.body {
    width: 100%;
    padding: 10px 0px;
}

.data-area { width: 100%; align-items: center; text-align: center;}
.data-field { display: inline-block; width: 40%; margin: 3px 5%;}
.data-label { float: left; font-size: 15px; font-weight:bold; }
.data-input { float: right; width: 150px;}
.data-text {background: #F9F9F9; border: solid 1px;}.data-text:invalid {border-color: red;}

.button-area { padding-right: 10px; display: none; width: 100%; margin-bottom: 10px; }
.btn {
    float: right;
    padding: 5px 20px;
    margin-left: 5px;

    font-size: 15px;
}

</style>
