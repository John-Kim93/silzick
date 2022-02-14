<template>
  <main class="form-login box-position">
    <img src="https://ifh.cc/g/g1KuX0.jpg" id="bg" alt="bgImg">
    <div class="card bg-dark box-shadow">
      <div class="card-body p-5">
          <label style="font-size:2rem;">Log In</label>
        <div>
          <label class="d-flex align-items-start mx-2">Username</label>
          <input
            id="black-font"
            type="text"
            class="form-control form-control-lg mb-4"
            placeholder="ID"
            v-model="requestInfo.userId"
          >
        </div>
        <div class="form-group">
          <label class="d-flex align-items-start mx-2">Password</label>
          <input
            type="password"
            class="form-control form-control-lg"
            placeholder="PW"
            v-model="requestInfo.password"
            @keyup.enter="login({data:requestInfo})"
          >
        </div>
        <div class="checkbox mb-2">
          <label class="d-flex align-items-start mx-2">
            <input style="margin-top:10px; margin-right:5px;" type="checkbox" value="remember-me">
            <p class="my-1">Remember me</p>
          </label>
        </div>
        <button class="w-100 btn btn-lg btn-secondary" @click="login({data:requestInfo})">Log In</button>
        <router-link to="/FindID">아이디 찾기 / </router-link>
        <router-link to="/FindPW">비밀번호 찾기</router-link>
      </div>
    </div>  
  </main>
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex'
import cookies from 'vue-cookies'
import { login } from '@/api/user.js'

const gameStore = 'gameStore'

export default {
  name: 'Login',
  computed: {
    ...mapState(['user'])
  },
  data() {
    return {
      requestInfo: {
        password: null,
        userId: null,
      }
    }
  },
  mounted() {
    if (cookies.isKey('JWT-AUTHENTICATION')) {
      this.$router.push('/')
    }
  },
  methods: {
    ...mapMutations(gameStore, ['SET_USER_ID']),
    ...mapActions(['saveUser']),
    login () {
      const user = this.requestInfo
      login(
        user,
        () => {
          this.SET_USER_ID(user.userId)
          this.saveUser()
          this.$router.go(-1)
          alert("로그인에 성공했습니다.")
        },
        (err) => {
          console.log(err)
          alert("로그인에 실패했습니다.")
        }
      )
    }
  }
}
</script>

<style scoped>
  body {
    display: flex;
    padding-top: 60px;
    padding-bottom: 60px;        
    align-items: center;
    background-color: #f6f6f6;
  }
  .form-login {
    width: 100%;
    max-width: 450px;
    margin: auto;
    top: 50%;
  }
  label {
    font-weight: 600;
    color: white;
  }
  .box-shadow {
    box-shadow: 5px 5px 5px 5px rgb(24, 24, 24) inset;
    border-radius: 1rem;
  }
  .box-position {
    position: absolute;
    top: 40%;
    left: 0;
    right: 0;
  }
</style>