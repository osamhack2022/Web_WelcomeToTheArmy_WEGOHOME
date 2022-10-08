<template>
    <TraineeListCreateModal :visible="visible" @update="updateCreateModalVisibility" />
    <section class="search-menu">
        <h3>훈련병 검색하기</h3>
    </section>
    <section class="button-area">
        <button class="btn btn-outline-dark">엑셀 업로드</button>
        <button @click="toggleCreateModal" class="btn btn-outline-dark">훈련병 추가</button> 
    </section>
    <section class="notice-area">
        <p>훈련병을 클릭하면 자세한 정보를 볼 수 있습니다.</p>
    </section>
    <section class="list-area">
        <TraineeListCard />
        <TraineeListCard />
        <TraineeListCard />
        <TraineeListCard />
    </section>
</template>

<script>
import Nav from "@components/Instructor/Nav.vue";
import TraineeListCard from "./TraineeListCard.vue";
import TraineeListCreateModal from "./TraineeListCreateModal.vue"

export default {
    data() {
        return {
            visible: false
        }
    },
    components: {
        Nav,
        TraineeListCard,
        TraineeListCreateModal,
    },
    methods: {
        toggleCreateModal() {
            this.visible = !this.visible
        },
        updateCreateModalVisibility(v) {
            this.visible = v
        }
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
