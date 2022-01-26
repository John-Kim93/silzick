<template>
  <main class="form-login box-position">
    <img src="https://ifh.cc/g/g1KuX0.jpg" id="bg" alt="bgImg">
    <div class="card bg-dark box-shadow">
      <div class="card-body p-5">
        <!-- <div class="form-group">
            <label>Email address</label>
            <input type="email" class="form-control form-control-lg" placeholder="Email address" />
        </div> -->
          <label style="font-size:2rem;">Sign Up</label>
        <div>
          <label class="d-flex align-items-start mx-2">Username</label>
          <input
            id="black-font"
            type="text"
            class="form-control form-control-lg mb-3"
            placeholder="ID"
            v-model="credentials.username"
          >
        </div>
        <div class="form-group">
          <label class="d-flex align-items-start mx-2">Password</label>
          <input
            type="password"
            class="form-control form-control-lg mb-3"
            placeholder="PW"
            v-model="credentials.password"
          >
        </div>
        <div class="form-group">
          <label class="d-flex align-items-start mx-2">Confirm Password</label>
          <input
            type="password"
            class="form-control form-control-lg mb-3"
            placeholder="PW"
            v-model="credentials.passwordConfirmation"
          >
        </div>
        <div class="form-group">
          <label class="d-flex align-items-start mx-2">E-mail address</label>
          <input
            type="e-mail"
            class="form-control form-control-lg mb-4"
            placeholder="E-mail"
            v-model="credentials.email"
          >
        </div>
        <button class="w-100 btn btn-lg btn-secondary" @click="signUp">Sign Up</button>
      </div>
    </div>  
  </main>
</template>

<script>
export default {
  name: 'Signup',
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
        passwordConfirmation: null,
        email: null,
      }
    }
  },
  mounted() {
    if (this.isLogin) {
      this.$router.push('/')
    }
  },
  methods: {
    signUp : function() {
      this.$axios({
        method: 'post',
        url: 'http://127.0.0.1:8000/accounts/signup/',
        data: this.credentials
      })
        .then(res => {
          console.log(res)
          this.$router.push('/Login')
        })
        .catch(err => {
          console.log(err)
        })
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
    top: 15%;
    left: 0;
    right: 0;
  }
</style>