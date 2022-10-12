<template>
    <CalendarCreateModal :visible="createModalVisible" @update="updateCreateModalVisibility" />
    <section class="title-area">
        <h1 class="title">훈련일정</h1>
    </section>
    <section class="menu-area">
        <select class="range-selector">
            <option>4대대 3중대 1소대</option>
            <option>기본군사훈련단</option>
        </select><br />
        <button @click="toggleCreateModal" class="btn btn-outline-dark">일정추가</button> 
    </section>
    <section class="calendar-area">
        <CalendarView />
    </section>
</template>

<script>
import Nav from "@components/Instructor/Nav.vue"
import CalendarView from "@components/Instructor/CalendarView.vue"
import CalendarCreateModal from "@components/Instructor/CalendarCreateModal.vue"

export default {
    components: {
        Nav,
        CalendarView,
        CalendarCreateModal,
    },
    data() {
        return {
            createModalVisible: false,
        }
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
.title-area {
    margin: 25px 0px;
}
.title {
    text-align: center;
    font-size: 32px;
    font-family: ROKAF Sans;
}
.menu-area { display: inline-block; width: 100%; margin: 10px 0px;}
.range-selector { float: right; }

.btn {
    float: right;
    padding: 5px;
    margin-left: 5px;
    margin-top: 10px;

    font-size: 12px;
    font-weight: bold;
}

</style>