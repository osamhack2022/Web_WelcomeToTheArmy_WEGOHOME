<template>
    <div class="background">
    <section class="login-card">
        <h1>WELCOME TO ARMY</h1>
        <h4>trainee page</h4>
        <form action="">
            <div class="input-area">
                <input type="text" name="id" id="id" 
                autocomplete="off" v-model="user.platoonNum" required/>
                <label for="id">군번</label>
            </div>
            <div class="input-area">
                <input type="password" name="pw" id="pw"
                autocomplete="off" v-model="user.password" required/>
                <label for="pw">생년월일 (yymmdd)</label>
            </div>
            <div class="button-area">
                <button @click.prevent="login" type="submit">LOGIN</button>
            </div>
        </form>
    </section>
    </div>
</template>

<script>
import useAxios from "@app_modules/axios.js"

const { axiosPost } = useAxios()

export default {
    data() {
        return {
            user: {
                platoonNum: null,
                password: null,
            }
        }
    },
    methods: {
        login(){
            const onSuccess = (data) => {
                console.log(data.data)
                localStorage.setItem("traineeLoginToken", data.data)
                this.$router.push("/trainee")
            }
            const onFailed = (data) => {
                alert("로그인 실패! 군번과 비밀번호를 확인하세요.")
            }
            axiosPost("soldier/login", this.user, onSuccess, onFailed)
        }
    }
}
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.background {
    /* font-family:  */
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background: url("@/assets/images/insloginBG.jpg") no-repeat center;
    background-size: cover;
}
.background::before {
    content: "";
    position: absolute;
    z-index: 1;
    top: 0; right: 0; bottom: 0; left: 0;
    background-color: rgba(0,0,0,.45)
}

.login-card {position: relative; z-index: 2;}

h1 {
    font-size: 32px; color: #FFFFFF;
    text-align: center;
    font-weight: bold;
}
h4 {
    font-size: 16px;
    color: #FFFFFF;
    text-align: center;
}
.input-area {
    width: 400px; position: relative;
    margin-top: 20px;
}
.input-area:first-child {
    margin-top: 0;
}
.input-area input {
    width: 100%;
    padding: 20px 10px 10px;
    background-color: transparent;
    border: none;
    border-bottom: 1px solid #999999;
    font-size: 18px; color: #fff;
    outline: none;
}
.input-area label {
    position: absolute; left: 10px; top: 15px;
    font-size: 18px; color: #999999;
    transition: top .5s ease;
}
.input-area input:focus + label,
.input-area input:valid + label {
    top: -2px;
    font-size: 13px; color: #166cea;
}

button {
    background-color: #3574d1;
    color: white;
    border-radius: 8px;
    width: 100%;
    padding: 5px 0px;
    margin-top: 15px;
    border: none;
}

button:hover {
    background-color: #275292;
}

</style>