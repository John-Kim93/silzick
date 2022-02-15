<template>
  <div id="background-black" class="full-screen d-flex justify-content-center align-items-center">
    <div id="base-border" class="container d-flex flex-row justify-content-center align-content-center row col-7" style="height:40vh">
      <div class="col-11 row d-flex align-content-center" style="height:13vh">
        <h3 id="base-font" class="m-2">Nickname</h3>
          <input
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
  <!-- <div id="background-black" class="full-screen d-flex justify-content-center align-items-center">
    <b-container id="card-bg" class="d-flex flex-row justify-content-center b-row row col-7">
      <b-form-row id="background-black" class="m-0 col-8 offset-2 card justify-content-center align-items-center py-4">
        <b-col cols="10" align-self="center">
          <h3 id="base-font" class="m-2">Nickname</h3>
          <input
          type="text"
          v-model="nickname"
          class="form-control m-2"
          placeholder="사용하실 닉네임을 입력해주세요."
          required
          >
          <button id="btn-color" class="btn mt-4 px-5" @click="nicknameUpdate(nickname)">참가</button>
        </b-col>
      </b-form-row>
    </b-container>
  </div> -->
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