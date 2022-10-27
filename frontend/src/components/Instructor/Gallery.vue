<template>
<UploadModal :visible="uploadModalVisible" @update="updateUploadModalVisibility" />
<section class="title-area">
    <h1 class="title">훈련 사진</h1>
</section>
<section class="button-area">
    <button type="button" class="btn btn-outline-dark" @click="toggleUploadModal">사진 업로드</button> 
</section>
<section class="gallery-deck">
    <div class="card-group">
        <div class="card" v-for="photo in galleryData">
            <img class="card-img-top img-preview" :src="'https://flask.giopaik.me/api/gallery/display/'+photo.imgList[0].id" alt="훈련사진" />
            <div class="card-body">
                <h5 class="card-title">{{photo.title}}</h5>
                <p class="card-text">{{photo.content}}</p>
                <p class="card-text"><small class="text-muted">공개대상: {{photo.generation}}기 {{photo.belong.substr(0,1)}}대대 {{photo.belong.substr(1,1)}}중대 {{photo.belong.substr(2,1)}}소대</small></p>
            </div>
        </div>
    </div>
</section>
</template>

<script>
import UploadModal from "./GalleryUploadModal.vue"
import useAxios from "@app_modules/axios.js"

const { axiosGet } = useAxios()

export default {
    data() {
        return {
            uploadModalVisible: false,
            galleryData: null,
        }
    },
    components: {
        UploadModal,
    },
    methods: {
        toggleUploadModal() {
            this.uploadModalVisible = !this.uploadModalVisible
        },
        updateUploadModalVisibility(v) {
            this.uploadModalVisible = v
        },
    },
    created() {
        const onSuccess = (data) => {
            this.galleryData = data.data
        }
        const onFailed = (data) => {
            console.error("사진들을 불러오는 과정에서 오류가 발생했습니다..\n"+data.response.data.message)
        }
        axiosGet("gallery", onSuccess, onFailed)
    }
}
</script>

<style scoped>
.title-area {
    width: 100%; margin: 10px 0px;
    align-items: center;
}
.title {
    text-align: center;
    font-size: 32px;
}
.card { overflow: hidden; margin: 0 10px; box-shadow: 1px 1px 1px; }

.button-area {width: 100%; height: 50px;}
.btn {float:right;}
</style>