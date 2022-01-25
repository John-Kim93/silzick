<template>
  <main class="form-login box-position">
    <img src="https://ifh.cc/g/g1KuX0.jpg" id="bg" alt="bgImg">
    <div class="card bg-dark box-shadow">
      <div class="card-body p-5">
        <!-- <div class="form-group">
            <label>Email address</label>
            <input type="email" class="form-control form-control-lg" placeholder="Email address" />
        </div> -->
          <label style="font-size:2rem;">Log In</label>
        <div>
          <label class="d-flex align-items-start mx-2">Username</label>
          <input
            type="text"
            class="form-control form-control-lg mb-4"
            placeholder="ID"
            v-model="credentials.username"
          >
        </div>
        <div class="form-group">
          <label class="d-flex align-items-start mx-2">Password</label>
          <input
            type="password"
            class="form-control form-control-lg"
            placeholder="PW"
            v-model="credentials.password"
          >
        </div>
        <div class="checkbox mb-2">
          <label class="d-flex align-items-start mx-2">
            <input style="margin-top:10px; margin-right:5px;" type="checkbox" value="remember-me">
            <p class="my-1">Remember me</p>
          </label>
        </div>
        <button class="w-100 btn btn-lg btn-secondary" @click=logIn({data:credentials})>Log In</button>
        <router-link to="/FindID">아이디 찾기 / </router-link>
        <router-link to="/FindPW">비밀번호 찾기</router-link>
      </div>
    </div>  
  </main>
</template>

<script>
import {mapActions} from 'vuex'
export default {
  name: 'login',
  computed: {
    isLogin() {
      return this.$store.state.isLogin
    },
    user() {
      return this.$store.state.user
    }
  },
  data() {
    return {
      credentials: {
        username: null,
        password: null,
      }
    }
  },
  mounted() {
    if (this.isLogin) {
      this.$router.push('/')
    }
  },
  methods: {
    ...mapActions(['logIn'])
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