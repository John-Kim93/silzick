<template>
  <div class="d-flex row justify-content-center align-content-start m-1" style="height:23vh">
    <p id="base-font" class="col-12 m-1" style="font-size:1.5rem">MISSION</p>
    <hr class="m-0 col-12">
    <div class="row d-flex justify-content-center" v-if=!missionStart>
      <button id="btn-color" class="btn btn-lg col-4 mt-5" v-show="!mission && webcam" @click="missionSelect(true)">start</button>
    </div>
    <div v-else>
      <pose v-if="mission==1" />
      <vue-web-speech v-if="mission==2"/>
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
      missionStart: false,
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
    ...mapState(gameStore, ['mission','webcam']),
  },
  methods:{
    ...mapActions(gameStore, ['missionSelect','init','missionReset']),
  }
    
}
</script>

<style scoped>
hr {
  background: #30475E;
}

</style>