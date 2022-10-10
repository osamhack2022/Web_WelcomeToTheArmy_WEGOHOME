<template>
<div class="modal-background" v-if="visible" @click.self="handlebackgroundClick">
    <div class="modal-window">
        <h1 class="title">액셀 업로드하기</h1>
        <p class="description">액셀 파일을 업로드하여 여러 명의 훈련병을 한번에 추가하실 수 있습니다.</p>
        <p class="description"><a href="#">탬플릿 파일 다운로드</a></p>
        <div class="uploader-area">
            <form>
                <input class="file-uploader" type="file" accept=".xlsx, .csv, .cell" required /><br>
                <button class="btn btn-primary" type="submit">업로드</button>
                <button class="btn btn-outline-dark" type="button" @click="$emit('update', !visible)">취소</button>
            </form>
        </div>
    </div>
</div>
</template>

<script>
export default {
    name: 'ExcelUploadModal',
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
    }
}
</script>

<style scoped>
* {
    margin: 0; padding: 0;
}
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

.description {
    text-align: center;
}

.uploader-area {
    width:100%;
    text-align: center;
}

.file-uploader {
    margin-top: 20px;
    background-color: #D9D9D9;
}

.btn {
    padding: 3px 10px;
    margin-top: 10px;
}
.btn-outline-dark { margin-left: 10px; }
</style>