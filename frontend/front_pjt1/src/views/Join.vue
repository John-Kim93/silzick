<template>
  <div class="d-flex">
    <div class="col-6 m-5 card">
      <label>Nickname</label>
      <input
      type="text"
      v-model="joinData.nickname"
      class="form-control"
      placeholder="사용하실 닉네임을 입력해주세요."
      required
      >
      <label>SessionId</label>
      <input
      type="text"
      v-model="joinData.sessionId"
      class="form-control"
      placeholder="호스트의 회원 아이디을 입력해주세요."
      required
      >
      <button class="btn btn-lg btn-success" @click="nicknameUpdate(joinData)">참가</button>
    </div>
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

