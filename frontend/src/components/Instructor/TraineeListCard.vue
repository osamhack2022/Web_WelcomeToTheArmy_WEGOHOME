<template>
    <div class="trainee-card">
        <section class="head" @click="toggleBody($event)">
            <div class="head-left">
                <img class="trainee-image rounded-circle" src="@/assets/images/trainee_profile_example.jpg" alt="훈련병 프로필 이미지" />
                <div class="trainee-profile">
                    <h1 class="trainee-name">이하늘 훈련병</h1>
                    <h1 class="trainee-position">신병 4대대 3중대 1소대 1번</h1>
                </div>
            </div>
            <div class="head-right">
                <p class="flag">⚠</p>
            </div>
        </section>
        <section class="body">
            <div class="data-area">
                <div class="data-field">
                    <p class="data-label">이름</p>
                    <input class="data-input data-text" type="text">
                </div>
                <div class="data-field">
                    <p class="data-label">다른</p>
                    <input class="data-input data-text" type="text">
                </div>
                <div class="data-field">
                    <p class="data-label">정보</p>
                    <input class="data-input data-text" type="text">
                </div>
                <div class="data-field">
                    <p class="data-label">소대번호</p>
                    <input class="data-input data-text" type="text">
                </div>
                <div class="data-field">
                    <p class="data-label">전화번호</p>
                    <input class="data-input data-text" type="text">
                </div>
                <div class="data-field">
                    <p class="data-label">보호자 연락처</p>
                    <input class="data-input data-text" type="text">
                </div>
                <div class="data-field">
                    <p class="data-label">관심병사</p>
                    <div class="data-input">
                        <label class="switch">
                            <input type="checkbox">
                            <span class="slider round"></span>
                        </label>
                    </div>
                </div>
                <div class="data-field">
                    <p class="data-label">관심병사</p>
                    <div class="data-input">
                        <label class="switch">
                            <input type="checkbox">
                            <span class="slider round"></span>
                        </label>
                    </div>
                </div>
            </div>
        </section>
        <section class="button-area">
            <button class="btn btn-primary">수정</button> 
            <button class="btn btn-danger">삭제</button>
            <button class="btn btn-outline-dark">취소</button>
        </section>
    </div>
</template>

<script>
    export default {
        methods: {
            toggleBody(event) {
                const card = event.currentTarget.parentNode;
                const body = card.childNodes[1];
                const buttons = card.childNodes[2];
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
.trainee-name {font-size: 20px; font-weight: bold; font-family: "ROKAF Slab";}
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
    display: none;
}

.data-area { width: 100%; }
.data-field { display: inline-block; width: 40%; margin: 3px 5%;}
.data-label { float: left; font-size: 15px; font-weight:bold; }
.data-input { float: right; width: 150px; text-align: center;}
.data-text {background: #D9D9D9; border: solid 1px;}

.button-area { padding-right: 10px; display: none; width: 100%; margin-bottom: 10px; }
.btn {
    float: right;
    padding: 5px 20px;
    margin-left: 5px;

    font-size: 15px;
}

/* toggle switch */
/* The switch - the box around the slider */
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 25px;
}

/* Hide default HTML checkbox */
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #d9d9d9;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 17px;
  width: 17px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: rgb(196, 196, 0);
}

input:focus + .slider {
  box-shadow: 0 0 1px rgb(196, 196, 0);
}

input:checked + .slider:before {
  -webkit-transform: translateX(33px);
  -ms-transform: translateX(33px);
  transform: translateX(33px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 25px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>
