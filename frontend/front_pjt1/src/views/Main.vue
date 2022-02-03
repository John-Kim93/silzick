<template>
  <div>
    <img src="https://ifh.cc/g/2DSNoy.jpg" id="bg" alt="bgImg">
    <router-link to="/Login" v-if="!isLogin">
      <b-button class="btn-1st-position" variant="outline-light">로그인</b-button>
    </router-link>
    <router-link to="/Signup" v-if="!isLogin">
      <b-button class="btn-2nd-position" variant="outline-light">회원가입</b-button>
    </router-link>
    <b-button
      v-if="isLogin"
      class="btn-1st-position"
      variant="outline-light"
    > 방생성
    </b-button>
    <b-button
      v-if="isLogin"
      class="btn-2nd-position"
      variant="outline-light"
      @click='logout'
    > 로그아웃
    </b-button>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import cookies from 'vue-cookies'

export default {
  name: 'Main',
  computed: {
    isLogin () {
      return cookies.isKey("JWT-AUTHENTICATION")
    }
  },
  methods: {
    ...mapMutations(['RESET_USER']),
    logout () {
      if (cookies.isKey('JWT-AUTHENTICATION')) {
        cookies.remove('JWT-AUTHENTICATION')
        this.RESET_USER()
        this.$router.go(this.$router.currentRoute)
      }
    }

  },
}
</script>

<style scoped>
.btn-1st-position {
        position: fixed;
        top: 65%;
        left: 48%;
    }

.btn-2nd-position {
        position: fixed;
        top: 73%;
        left: 47.5%;
    }
</style>