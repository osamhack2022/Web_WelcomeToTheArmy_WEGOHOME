<template>
<InstructorCreateModal :visible="createModalVisible" @update="updateCreateModalVisibility" />
<section class="notice-area">
    <h1 class="notice">현재 <span class="role">4대대 3중대 1소대장</span> 권한입니다.</h1>
</section>
<section class="button-area">
    <button @click="toggleCreateModal" class="btn btn-outline-dark">훈육관 추가</button> 
</section>
<section class="list-area">
        <InstructorListCard />
        <InstructorListCard />
        <InstructorListCard />
        <InstructorListCard />
    </section>
</template>

<script>
import InstructorListCard from "./InstructorListCard.vue";
import InstructorCreateModal from "./InstructorListCreateModal.vue"

export default {
    data() {
        return {
            createModalVisible: false,
        }
    },
    components: {
        InstructorListCard,
        InstructorCreateModal,
    },
    methods: {
        toggleCreateModal() {
            this.createModalVisible = !this.createModalVisible
        },
        updateCreateModalVisibility(v) {
            this.createModalVisible = v
        },

    }
}
</script>

<style scoped>
.notice-area { margin: 10px 0px; }
.notice { font-size: 24px; font-weight: bold; }
.role { color: red; }

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

.list-area > * {
    margin: 15px 0px;
}

</style>