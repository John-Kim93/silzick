<template>
  <div>
    <button v-show="!mission" @click="missionSelect">mission</button>
    {{mission}}
    <pose v-if="mission==1" />
    

    <!-- 요청사항: 출력 문구 :15초 안에 해당 단어를 ?번 말하시오 -->
    <!-- ex: 0/{요구횟수} -->
    <div v-else-if="mission==2">
      
      <br>
      <vue-web-speech
        v-model="record"
      >
      <!-- <template v-slot:input="{text}">
        <textarea :value="text" />
      </template> -->

      </vue-web-speech>
    </div>
  </div>
      
</template>
<script>
import pose from './Pose.vue'
import { mapState, mapActions } from 'vuex'

const mission = 'mission';

export default {
  components: { 
    pose,
    },

  computed: {
    ...mapState(mission, ['mission','record']),
  },
    methods:{
      ...mapActions(mission, ['missionSelect','recordReset']),
    }
    
}
</script>

