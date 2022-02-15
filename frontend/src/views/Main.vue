<template>
  <div id="base-font" class="d-flex justify-content-center">
    <img src="https://ifh.cc/g/2DSNoy.jpg" id="bg" alt="bgImg">
    <!-- 로그인 후 버튼-->
    <div class="col-2 row btn-position" style="height:30vh;" v-if="!isLogin">
      <div class="d-flex flex-column justify-content-evenly">
        <router-link to="/Login">
          <b-button class="col-12 font-size" variant="outline-light">로그인</b-button>
        </router-link>
        <router-link to="/Signup">
          <b-button class="col-12 font-size" variant="outline-light">회원가입</b-button>
        </router-link>
        <router-link to="/Invite">
          <b-button class="col-12 font-size" variant="outline-light">방 바로가기</b-button>
        </router-link>
      </div>
    </div>
    <!-- 로그인 전 버튼 -->
    <div class="col-2 row btn-position" style="height:30vh;" v-if="isLogin">
      <div class="d-flex flex-column justify-content-evenly">
        <b-button
          class="font-size col-12"
          variant="outline-light"
          @click="createRoomRequest(userName)"
        > 방 만들기
        </b-button>
        <b-button
          class="font-size col-12"
          variant="outline-light"
          @click='logout'
        > 로그아웃
        </b-button>
      </div>
    </div>
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
.btn-position {
  position: fixed;
  top: 60%;
}
.font-size {
  font-size:1.5rem;
}
</style>