<template>
    <TraineeCreateModal :visible="createModalVisible" @update="updateCreateModalVisibility" />
    <TraineeExcelUploadModal :visible="excelUploadModalVisible" @update="updateExcelUploadModalVisibility" />
    <section class="search-menu">
        <h3>훈련병 검색하기</h3>
    </section>
    <section class="button-area">
        <button @click="toggleExcelUploadModal" class="btn btn-outline-dark">엑셀 업로드</button>
        <button @click="toggleCreateModal" class="btn btn-outline-dark">훈련병 추가</button> 
    </section>
    <section class="notice-area">
        <p>훈련병을 클릭하면 자세한 정보를 볼 수 있습니다.</p>
    </section>
    <section class="list-area">
        <TraineeListCard v-for="trainee in traineeData" :propTrainee="trainee"/>
    </section>
</template>

<script>
import Nav from "@components/Instructor/Nav.vue";
import TraineeListCard from "./TraineeListCard.vue";
import TraineeCreateModal from "./TraineeListCreateModal.vue"
import TraineeExcelUploadModal from "./TraineeListExcelUploadModal.vue"
import useAxios from "@app_modules/axios.js"

const { axiosGet } = useAxios()

export default {
    data() {
        return {
            createModalVisible: false,
            excelUploadModalVisible: false,
            traineeData: [],
        }
    },
    components: {
        Nav,
        TraineeListCard,
        TraineeCreateModal,
        TraineeExcelUploadModal,
    },
    methods: {
        toggleCreateModal() {
            this.createModalVisible = !this.createModalVisible
        },
        updateCreateModalVisibility(v) {
            this.createModalVisible = v
        },
        toggleExcelUploadModal() {
            this.excelUploadModalVisible = !this.excelUploadModalVisible
        },
        updateExcelUploadModalVisibility(v) {
            this.excelUploadModalVisible = v
        },
    },
    created() {
        const onSuccess = (data) => {
            this.traineeData = data.data
        }
        const onFailed = (data) => {
            //console.error("훈련병을 불러오는 과정에서 오류가 발생했습니다..\n"+data.response.data.message)
        }
        axiosGet("soldier", onSuccess, onFailed)
    }
}
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
}
.search-menu {
    width: 100%; height: 340px;
    padding: 5px;
    background-color: #ABABAB;
}
.button-area {
    width: 100%;
    margin-top: 10px;
    display: inline-block;
}

.btn {
    float: right;
    padding: 5px;
    margin-left: 5px;

    font-size: 12px;
    font-weight: bold;
}

.notice-area {
    width: 100%;
    display: inline-block;
}

.notice-area > p {
    font-size: 12px;
    float: right;
}

.list-area > * {
    margin: 15px 0px;
}

</style>
