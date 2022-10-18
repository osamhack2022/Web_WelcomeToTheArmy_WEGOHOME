<template>
    <nav class="navbar sticky-top">
        <div class="nav-top">
            <a class="navbar-brand" href="/trainee/main">WELCOME TO ARMY</a>
        </div>
    </nav>
</template>

<style scoped>
* {
    padding: 0; margin: 0;
}
nav {
    height: 75px;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    width: 100%;
    background-color: #F2F2F2;
}
.nav-top {
    width: 100%; height: 100%;
    position: relative;
}
.navbar-brand {
    font-family: "ROKAF Sans";
    font-size: 24px;
    font-weight: bold;
    text-align: center;

    position: absolute;
    transform: translate(-50%, -50%);
    left: 50%;
    top: 50%;
}
</style>
