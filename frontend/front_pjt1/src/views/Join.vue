<template>
  <div id="background-black" class="full-screen d-flex justify-content-center align-items-center">
    <div id="base-border" class="container d-flex flex-row justify-content-center align-content-center row col-7" style="height:40vh">
      <div class="col-11 row d-flex align-content-center" style="height:13vh">
        <h3 id="base-font" class="m-2">Nickname</h3>
          <input
          id="base-border"
          type="text"
          v-model="nickname"
          class="form-control m-2"
          placeholder="사용하실 닉네임을 입력해주세요."
          required
          >
      </div>
      <div class="col-8 row d-flex align-content-center" style="height:13vh">
        <button id="btn-color" class="btn mt-4 px-5" @click="nicknameUpdate(nickname)">참가</button>
      </div>
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
      nickname: ''
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

</style>