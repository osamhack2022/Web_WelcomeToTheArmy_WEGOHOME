<template>
<section class="question-card">
    <section class="option-area">
        <div class="option-right">
            <select v-model="question.type" class="question-type">
                <option selected>주관식</option>
                <option>객관식</option>
            </select>
            <button class="btn btn-danger" @click="this.$parent.removeQuestion(questionIndex)">질문 삭제</button>
        </div>
    </section>
    <section class="question-area">
        <div class="title-area">
            <div>
                <span class="question-title">질문. {{ questionIndex + 1}} </span><br />
                <input v-model="question.title" class="data-text data-title form-control" type="text" />
            </div>
            <div>
                <span class="question-title">질문 설명</span><br />
                <input v-model="question.description" class="data-text data-title form-control" type="text" />
            </div>
        </div>
        <div class="answer-area" v-if="question.type == '객관식'">
            <hr />
            <div v-for="(option, i) in options">
                <span class="option-num">선택 {{ i + 1 }}.</span>
                <div class="option-box">
                    <input v-model="options[i]" type="text" class="option data-text form-control" />
                    <button class="btn btn-outline-danger" @click="removeOption(i)">선택 삭제</button>
                </div>
                <br />
            </div>
            <button class="btn btn-outline-dark" @click="addOption">선택 추가</button>
        </div>
    </section>
</section>
</template>

<script>
export default {
    props: {
        questionIndex: {
            type: Number,
        }
    },
    data() {
        return {
            question: {
                type: "주관식",
                title: "",
                description: "",
                value: "",
            },
            options: [""],
        }
    },
    methods: {
        addOption() {
            this.options.push("")
        },
        removeOption(index) {
            this.options.splice(index, 1)
        }
    },
}
</script>

<style scoped>
.question-card {
    width: 100%;
    background-color: #EFEFEF;
    border-radius: 5px;
    margin: 15px 0px;
    padding: 5px;
}
.options-area { display: inline-block; width: 100%; }
.option-right { float: right; align-items: center; }
.question-type {
    font-size: 12px;
    margin-right: 3px;
}
.btn {
    font-size: 12px;
    padding: 2px 4px;
}

.question-title { font-size: 12px; }
.data-title { width: 40%; }
.data-text {
    font-size: 15px;
}

.option-num {
    font-size: 12px;
}
.option {
    width: 200px;
    float: left;
}
.option-box { width: 100%; height: 100%; display: inline-block; align-items: center; }
.btn-outline-dark { margin-top: 10px; }
.btn-outline-danger { height: 100%; padding: 7px 3px; margin-left: 5px;}

</style>