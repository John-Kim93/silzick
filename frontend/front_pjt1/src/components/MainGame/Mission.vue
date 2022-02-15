<template>
  <div class="d-flex row justify-content-center align-content-start m-1" style="height:23vh">
    <p
      v-if="isNormalMission"
      id="base-font"
      class="col-12 m-1"
      style="font-size:1.5rem"
    >MISSION</p>
    <p
      v-else
      id="base-font"
      class="col-12 m-1"
      style="font-size:1.5rem"
    >HIDDEN MISSION</p>
    <hr class="m-0 col-12">
    <!-- 미션 스타트 버튼 -->
    <div class="row d-flex justify-content-center" v-if="!mission && webcam">

      <button
        id="btn-color"
        class="btn btn-lg col-4 mt-5"
        @click="missionSelect(true)"
        v-if="isKIRAorL == false"
      >start</button>
      <p v-else>KIRA와L은 미션을 수행할수 없습니다</p>
    </div>
    <!-- 버튼 클릭 시 미션 시작 -->
    <div v-else>
      <pose v-if="mission==1"/>
      <div v-if="mission==2">
        <vue-web-speech></vue-web-speech>
      </div>
    </div>
  </div>
</template>
<script>
import pose from '../Mission/Pose.vue'

import { mapState, mapActions } from 'vuex'

const gameStore = 'gameStore';

export default {
  name: 'Mission',
  data () {
    return {
      missionShow: false,
    }
  },
  components: { 
    pose,
  },
  created(){
    this.init()
    setTimeout(()=>{
      this.missionReset()
    },2000)
  },
  computed: {
    ...mapState(gameStore, ['mission','webcam', 'isNormalMission', 'isKIRAorL']),
  },
  methods:{
    ...mapActions(gameStore, ['missionSelect','init','missionReset']),
  },    
}
</script>

<style scoped>
hr {
  background: #30475E;
}

</style>