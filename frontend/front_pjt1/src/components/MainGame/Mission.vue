<template>
  <div>
    <!-- <button @click="randomInt({min:1,max:4})">{{random_int}}</button> -->
    <button v-show="!mission && webcam" @click="missionSelect(true)">mission</button>
    {{mission}}
    <pose v-if="mission==1" />
    
    <!-- 요청사항: 출력 문구 :15초 안에 해당 단어를 ?번 말하시오 -->
    <!-- ex: 0/{요구횟수} -->
    <div v-if="mission==2">

      <vue-web-speech
        v-model="record"
      >

      </vue-web-speech>
    </div>
  </div>
      
</template>
<script>
import pose from '../Mission/Pose.vue'
import { mapState, mapActions } from 'vuex'

const gameStore = 'gameStore';

export default {
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

