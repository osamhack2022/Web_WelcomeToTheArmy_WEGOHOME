<template>
<section class="question-card">
    <section class="option-area">
        <div class="option-right">
            <select class="question-type">
                <option selected>객관식</option>
                <option>주관식</option>
            </select>
            <button class="btn btn-danger">질문 삭제</button>
        </div>
    </section>
    <section class="question-area">
        <div class="questions-fixed">
            <div>
                <span class="question-title">질문</span><br />
                <input class="data-text data-title" type="text" />
            </div>
            <div>
                <span class="question-title">질문 설명</span><br />
                <input class="data-text data-title" type="text" />
            </div>
        </div>
        <div class="questions-variable">
            
        </div>
    </section>
</section>
</template>

<script>
</script>

<style scoped>
.question-card {
    width: 100%; height: 200px;
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
</style>