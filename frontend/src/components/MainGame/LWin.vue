<template>
  <div id="background-black" class="full-screen d-flex justify-content-center align-items-center">
    <div id="base-border" class="container d-flex row justify-content-center align-content-center col-7 flex-wrap" style="height:60vh">
      <div class="col-8 row d-flex" style="height:13vh">
        <b-icon icon="award-fill" font-scale="7.5" class="col-3" variant="primary" />
        <h1 class="col-7 align-self-center">경찰 측 승리 !</h1>
      </div>
      <hr id="blue-hr" class="col-11 my-3">
      <div class="col-8 d-flex row justify-content-start flex-wrap">
        <h2 class="col-12" style="text-align:left;">Losers</h2>
        <div
          class="col-12"
          v-for="Kira, idx in KiraTeam"
          :key="idx"
        >
          <h3>{{Kira.nickName}} : {{Kira.job}}</h3>
        </div>
        <h2 class="col-12" style="text-align:left;">Winners</h2>
        <div
          class="col-12"
          v-for="L, idx in LTeam"
          :key="idx"
        >
          <h3>{{L.nickName}} : {{L.job}}</h3>
        </div>
      </div>
      <div class="col-8 row d-flex align-content-center" style="height:13vh">
        <button id="btn-color" class="btn col" @click="gameReset">돌아가기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

const gameStore = 'gameStore';

export default {
  name:'LWin',
  data () {
    return {
      KiraTeam: [],
      Lteam: [],
    }
  },
  computed: {
    ...mapState(gameStore, ['participantsLog', 'finalInfo'])
  },
  methods: {
    ...mapActions(gameStore, [ 'gameReset']),
  },
  created() {
    this.participantsLog.forEach(participant => {
      // participantsLog(전체 참여자) 받아서 직업확인하고 KIRA팀이면 KiraTeam에 넣고 L팀이면 Lteam에 넣는다.
      // participants에 넣는 객체 : {nickname: clientData, connectionId: connectionId}
      // finalInfo는 {connectionId:직업}
      console.log('1111111111111')
      console.log(this.participantsLog)
      console.log(this.finalInfo)
      const job = this.finalInfo[participant.connectionId]
      job == 'KIRA' || job == 'CRIMINAL' 
      ? this.KiraTeam.push({nickName:participant.nickName, job:job})
      : this.LTeam.push({nickName:participant.nickName, job:job})
    })
  }

}
</script>

<style>
</style>