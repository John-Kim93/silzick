<template>
  <div>
    <img src="https://ifh.cc/g/2DSNoy.jpg" id="bg" alt="bgImg">
    <router-link to="/Login" v-if="!isLogin">
      <b-button class="btn-1st-position" variant="outline-light">로그인</b-button>
    </router-link>
    <router-link to="/Signup" v-if="!isLogin">
      <b-button class="btn-2nd-position" variant="outline-light">회원가입</b-button>
    </router-link>
    <!-- css작업 -->
    <router-link to="/Invite" v-if="!isLogin">
      <b-button class="btn-3rd-position" variant="outline-light">게스트 방 참가</b-button>
    </router-link>
    <b-button
      v-if="isLogin"
      class="btn-1st-position"
      variant="outline-light"
      @click="createRoomRequest(userName)"
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
import { mapState, mapMutations, mapActions } from 'vuex'
import cookies from 'vue-cookies'

const gameStore = 'gameStore'
const userStore = 'userStore'

export default {
  name: 'Main',
  computed: {
    ...mapState(userStore, ['userName']),
    isLogin () {
      return cookies.isKey("JWT-AUTHENTICATION")
    }
  },
  methods: {
    ...mapMutations(userStore, ['RESET_USER']),
    ...mapMutations(gameStore, ['GAME_CHECKOUT']),
    ...mapActions(gameStore, ['createRoomRequest']),

    logout () {
      if (cookies.isKey('JWT-AUTHENTICATION')) {
        cookies.remove('JWT-AUTHENTICATION')
        this.RESET_USER()
        this.$router.go();
      }
    },
  },
  created () {
    this.GAME_CHECKOUT()
  }
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
.btn-3rd-position {
    position: fixed;
    top: 81%;
    left: 47.5%;
}
</style>