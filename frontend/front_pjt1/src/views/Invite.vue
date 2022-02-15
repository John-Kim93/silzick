<template>
  <div id="background-black" class="full-screen">
    <b-container id="card-bg" class="justify-content-center b-row">
      <b-form-row id="background-black" class="col-8 offset-2 card justify-content-center align-items-center py-4">
        <b-col cols="10" align-self="center">
          <h3 id="base-font" class="mt-5">SessionId</h3>
          <input
          type="text"
          v-model="hostId"
          class="form-control m-2"
          placeholder="호스트의 회원 아이디를 입력해주세요."
          required
          >
          <button id="btn-color" class="btn mt-4 px-5" @click="guestJoinRoom(hostId)">참가</button>
        </b-col>
      </b-form-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

const gameStore = 'gameStore';

export default {
  name: 'Invite',
  data () {
    return{
      hostId : '',
    }
  },
  methods:  {
    ...mapActions(gameStore, ['setSessionID', 'guestJoinRoom']),
  },
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