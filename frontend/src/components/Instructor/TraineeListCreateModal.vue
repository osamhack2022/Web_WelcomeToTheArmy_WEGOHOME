<template>
<div class="modal-background" v-if="visible" @click.self="handlebackgroundClick">
    <div class="modal-window">
        <h1 class="title">병사 추가하기</h1>
        <form>
            <div class="data-area">
                <div class="data-field">
                    <p class="data-label">이름</p>
                    <input class="data-input form-control" type="text" v-model="trainee.name" required>
                </div>
                <div class="data-field">
                    <p class="data-label">기수</p>
                    <input class="data-input form-control" type="number" v-model="trainee.generation" required>
                </div>
                <div class="data-field">
                    <p class="data-label">소대번호</p>
                    <input class="data-input form-control" type="number" v-model="trainee.platoonNum" required>
                </div>
                <div class="data-field">
                    <p class="data-label">생년월일</p>
                    <input class="data-input form-control" type="date" v-model="trainee.birthday" required>
                </div>
                <div class="data-field">
                    <p class="data-label">연락처</p>
                    <input class="data-input form-control" type="tel" v-model="trainee.phoneNumber" pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}" placeholder="010-1234-5678" required>
                </div>
                <div class="data-field">
                    <p class="data-label">보호자 연락처</p>
                    <input class="data-input form-control" type="text" v-model="trainee.homeTel" pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}" placeholder="010-1234-5678" required>
                </div>
            </div>
            <div class="button-area">
                <button class="btn btn-primary" @click.prevent="createTrainee" type="submit">생성</button>
                <button class="btn btn-danger" @click="$emit('update', !visible)">취소</button>
            </div>
        </form>
    </div>
</div>
</template>

<script>
import useAxios from "@app_modules/axios.js"

const { axiosPost } = useAxios()

export default {
    name: 'TraineeCreateModal',
    data() {
        return {
            trainee:  {
                platoonNum: null,
                belong: null,
                birthday: null,
                generation: null,
                name: "",
                phoneNumber: "",
                homeTel: "",
            },
        }
    },
    props: {
        visible: {
            type: Boolean,
            require: true,
            default: false
        },
    },
    methods: {
        handlebackgroundClick(){
            this.$emit('update', false)
        },
        createTrainee(){
            this.trainee.platoonNum = this.trainee.platoonNum.toString()
            this.trainee.belong = this.trainee.platoonNum.substr(0,3)
            const onSuccess = (data) => {
                alert(this.trainee.name + " 훈련병이 성공적으로 추가되었습니다!")
                this.$router.go(this.$router.currentRoute)
            }
            const onFailed = (data) => {
                alert("훈련병을 추가하는데 실패하였습니다.\n"+data.response.data.message)
            }
            axiosPost("soldier", this.trainee, onSuccess, onFailed)
        },
    }
}
</script>

<style scoped>
.modal-background {
    background-color: rgba(0,0,0,0.7);
    top: 0; right: 0; bottom: 0; left: 0;
    position: fixed;
    overflow: auto;
    margin: 0;
    z-index: 1020;
}

.modal-window {
    left: 50%; top: 50%;
    width: 700px;
    border-radius: 10px;
    transform: translate(-50%, -50%);
    position: absolute;
    background-color: white;
    margin-bottom: 50px;
    padding: 20px 10px;
}

.title {
    font-family: ROKAF Sans;
    font-size: 25px;
    text-align: center;
    margin-bottom: 25px;
}

.data-area { width: 100%; align-items: center; text-align: center;}
.data-field { display: inline-block; width: 40%; margin: 3px 5%;}
.data-label { float: left; font-size: 15px; font-weight:bold; }
.data-input { float: right; width: 150px;}
.form-control:invalid {border-color: red;}

.btn {
    margin: 0px 3px;
}
.button-area {
    width: 100%;
    text-align: center;
    align-items: center;
}
</style>

<style scoped>
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