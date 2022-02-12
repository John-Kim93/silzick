<template>
  <div id="background-black" class="full-screen">
    <b-container id="card-bg" class="justify-content-center b-row">
      <b-form-row id="background-black" class="col-8 offset-2 card justify-content-center align-items-center py-4">
        <b-col cols="10" align-self="center">
          <h3 id="base-font" class="m-2">Nickname</h3>
          <input
          type="text"
          v-model="joinData.nickname"
          class="form-control m-2"
          placeholder="사용하실 닉네임을 입력해주세요."
          required
          >
          <h3 id="base-font" class="mt-5">SessionId</h3>
          <input
          type="text"
          v-model="joinData.sessionId"
          class="form-control m-2"
          placeholder="호스트의 회원 아이디을 입력해주세요."
          required
          >
          <button id="btn-color" class="btn mt-4 px-5" @click="nicknameUpdate(joinData)">참가</button>
        </b-col>
      </b-form-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapMutations } from 'vuex';

const gameStore = 'gameStore';

export default {
  name: 'Join',
  data () {
    return{
      joinData: {
        nickname:'',
        sessionId:'',
      }
    }
  },
  methods:  {
    ...mapActions(gameStore, ['nicknameUpdate']),
    ...mapMutations(gameStore, ['GAME_CHECKIN']),
  },
  created () {
    this.GAME_CHECKIN()
  }
};
</script>

<style scoped>
input[type=text] {
  border: solid #30475E;
  color: #212121;
  border-radius: 8px;
}
#card-bg {
  background: #222831;
  padding-top: 9%;
}
.card {
  border: solid 3px #30475E;
  height: auto;
  width: 60vw;
}

</style>