<template>
    <div class="modal-background" v-if="visible" @click.self="handlebackgroundClick">
        <div class="modal-window">
            <h1 class="title">일정 추가하기</h1>
            <form>
                <div class="data-area">
                    <div class="data-field">
                        <p class="data-label">일정명</p>
                        <input class="data-input form-control" type="text" v-model="schedule.name" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">일정 공개 범위</p>
                        <select class="data-input" v-model="schedule.range">
                            <option>4대대 3중대 1소대</option>
                            <option>기본군사훈련단</option>
                        </select>
                    </div>
                    <div class="data-field">
                        <p class="data-label">시작일자</p>
                        <input class="data-input form-control" type="date" v-model="schedule.start_date" required>
                    </div>
                    <div class="data-field">
                        <p class="data-label">종료일자</p>
                        <input class="data-input form-control" type="date" v-model="schedule.end_date" required>
                    </div>
                </div>
                <div class="button-area">
                    <button class="btn btn-primary" @click.prevent="createSchedule" type="submit">생성</button>
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
    name: 'CalendarCreateModal',
    data() {
        return {
            schedule:  {
                name: "",
                range: "기본군사훈련단",
                start_date: null,
                end_date: null,
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
        createSchedule(){
            const onSuccess = (data) => {
                alert("일정이 성공적으로 추가되었습니다!")
            }
            const onFailed = (data) => {
                alert("일정을 추가하는데 실패하였습니다.")
            }
            axiosPost("/schedule", this.schedule, onSuccess, onFailed)
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
    