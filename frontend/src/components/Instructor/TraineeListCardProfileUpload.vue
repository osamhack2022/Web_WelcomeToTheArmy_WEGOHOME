<template>
    <div class="modal-background" v-if="visible" @click.self="handlebackgroundClick">
        <div class="modal-window">
            <h1 class="title">프로필 사진 업로드</h1>
            <form>
                <div class="uploader-area">
                    <input class="file-uploader" type="file" accept=".jpg, .jpeg, .png" @change="onFileUpload" ref="photo" required /><br>
                    <button class="btn btn-primary" type="submit" @click.prevent="uploadPhoto">업로드</button>
                    <button class="btn btn-outline-dark" type="button" @click="$emit('update', !visible)">취소</button>
                </div>
            </form>
        </div>
    </div>
    </template>
    
    <script>
    import useAxios from "@app_modules/axios.js"
    
    const { axiosMultiparts } = useAxios()
    
    export default {
        name: 'UploadModal',
        data() {
            return {
                photo: {
                    file: null,
                }
            }
        },
        props: {
            visible: {
                type: Boolean,
                require: true,
                default: false
            },
            id: {
                type: Number,
                require: true,
            },
        },
        methods: {
            handlebackgroundClick(){
                this.$emit('update', false)
            },
            onFileUpload() {
                this.photo.file = this.$refs.photo.files[0]
                console.log(this.photo.file)
            },
            uploadPhoto() {
                const onSuccess = (data) => {
                    alert("사진이 성공적으로 업로드되었습니다.")
                    this.$router.push("/instructor/trainee")
                }
                const onFailed = (data) => {
                    alert(data.response.data.message)
                }
                axiosMultiparts("soldier/profile/"+this.id, this.photo, onSuccess, onFailed)
            }
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

    
.data-area { width: 100%; align-items: center; text-align: center;}
.data-field { display: inline-block; width: 40%; margin: 3px 5%;}
.data-label { float: left; font-size: 15px; font-weight:bold; }
.data-input { float: right; width: 150px;}
.form-control:invalid {border-color: red;}
    </style>