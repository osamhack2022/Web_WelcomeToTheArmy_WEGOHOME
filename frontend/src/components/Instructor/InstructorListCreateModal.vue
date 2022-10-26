<template>
    <div class="modal-background" v-if="visible" @click.self="handlebackgroundClick">
        <div class="modal-window">
            <h1 class="title">훈육관 추가하기</h1>
            <form>
                <div class="data-area">
                    <div class="data-field">
                        <p class="data-label">이름</p>
                        <input class="data-input form-control" type="text" v-model="instructor.name" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">계급</p>
                        <input class="data-input form-control" type="text" v-model="instructor.rank" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">군번</p>
                        <input class="data-input form-control" type="text" v-model="instructor.managerId" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">직책</p>
                        <input class="data-input form-control" type="text" v-model="instructor.position" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">비밀번호</p>
                        <input class="data-input form-control" type="password" v-model="instructor.password" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">비밀번호 확인</p>
                        <input class="data-input form-control" type="password" v-model="instructor.passwordCheck" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">소대번호</p>
                        <input class="data-input form-control" type="text" v-model="instructor.belong" placeholder="431 | 없을 시 000" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">연락처</p>
                        <input class="data-input form-control" type="phoneNumber" v-model="instructor.phoneNumber" pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}" placeholder="010-1234-5678" required>
                    </div>
                </div>
                <div class="button-area">
                    <button class="btn btn-primary" @click.prevent="createInstructor" type="submit">생성</button>
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
    name: 'InstructorCreateModal',
    data() {
        return {
            instructor:  {
                managerId: null,
                password: null,
                passwordCheck: null,
                name: null,
                rank: null,
                belong: null,
                position: null,
                phoneNumber: null,
            }
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
        createInstructor(){
            this.instructor.belong = this.instructor.belong.toString()
            const onSuccess = (data) => {
                alert(this.instructor.name + "훈육관이 성공적으로 추가되었습니다!")
            }
            const onFailed = (data) => {
                alert("훈육관을 추가하는데 실패하였습니다.\n"+data.response.data.message)
            }
            axiosPost("manager", this.instructor, onSuccess, onFailed)
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
.data-label { float: left; font-size: 15px; font-weight:bold; text-align: left; }
.label-sm {font-size: 7px; font-weight: 100;}
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

