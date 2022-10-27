<template>
<section class="title-area">
    <h1 class="title">훈련 사진</h1>
</section>
<section class="gallery-area">
    <div class="card-group">
        <div class="card">
            <img class="card-img-top img-preview" src="@assets/images/training_photo.png" alt="훈련사진" />
            <div class="card-body">
                <h5 class="card-title">제목</h5>
                <p class="card-text">설명</p>
                <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
            </div>
        </div>
        <div class="card">
            <img class="card-img-top img-preview" src="@assets/images/training_photo.png" alt="훈련사진" />
            <div class="card-body">
                <h5 class="card-title">제목</h5>
                <p class="card-text">설명</p>
                <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
            </div>
        </div>
        <div class="card">
            <img class="card-img-top img-preview" src="@assets/images/training_photo.png" alt="훈련사진" />
            <div class="card-body">
                <h5 class="card-title">제목</h5>
                <p class="card-text">설명</p>
                <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
            </div>
        </div>
    </div>
</section>
</template>

<script>
export default {
    
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
.card { overflow: hidden; margin: 0 10px; }
.img-preview { width: 30vw; }
</style>