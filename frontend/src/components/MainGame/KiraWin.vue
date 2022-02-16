<template>
  <div id="background-black" class="full-screen d-flex justify-content-center align-items-center">
    <div id="red-border" class="container d-flex flex-row justify-content-center align-content-center row col-7" style="height:40vh">
      <div class="col-8 row d-flex" style="height:13vh">
        <b-icon icon="award-fill" font-scale="7.5" class="col-3" variant="danger" />
        <h1 class="col-8 align-self-center">노트주인 측 승리 !</h1>
      </div>
      <hr id="red-hr" class="col-11 my-3">
      <div class="col-8 row d-flex align-content-center" style="height:13vh">
        <button id="btn-color-kira" class="btn col" @click="gameReset">돌아가기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

const gameStore = 'gameStore';

export default {
  name: 'KiraWin',
  
   methods: {
    ...mapActions(gameStore, [ 'gameReset']),

  }
}
</script>

<style>
</style>