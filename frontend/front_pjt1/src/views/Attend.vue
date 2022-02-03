<template>
  <div id="main-container" class="container d-flex">
    <!-- 닉네임 작성전 대기 -->
    <div id="noenter" v-if="!is_enter">
      <entrance />
    </div>

    <!-- 작성 후 세션의 대기방 -->
    <div id="enter" v-else class="container j">
      <div class=" row justify-content-center">
        {{hostname}}의 방
      </div>
      <div v-if="!is_ready" class="row justify-content-center">
        <!-- 참가자 리스트 -->
        <div class="col status">
          <ready :stream-manager="publisher"/>
          <div
            v-for="sub in subscribers"
            :key="sub.stream.connection.connectionId"
          >
            <ready :stream-manager="sub" />
          </div>
        </div>
        <!-- 직업리스트 -->
        <div class="col status">
          <jobs :job="job" v-for="job in jobs" v-bind:key="job"/>          
        </div>

        <!-- 적용직업리스트 -->
        <div class="col status">
          <job-select :job="job" v-for="job in jobs" v-bind:key="job"/>
        </div>
        <hr>
        <div class="row d-flex justify-content-between">
          <div class="chat col-8">
            <div> chat</div>
            <input type="text" class="w-auto">
          </div>
          <button class="btn btn-success col" @click="ready()">Ready</button>
        </div>
        
      </div>
      <!-- 레디시 영상 송출 -->
      <div id="RTC" v-if="is_ready">
          <h1 id="session-title">{{ hostname }}</h1>
          <input
            class="btn btn-large btn-danger"
            type="button"
            id="buttonLeaveSession"
            @click="leaveSession"
            value="Leave session"
          />
          <div id="video-container" class="col-md-6">
            <user-video
              :stream-manager="publisher"
              @click.native="updateMainVideoStreamManager(publisher)"
            />
            <div
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
            >
              <user-video
                :stream-manager="sub"
                @click.native="updateMainVideoStreamManager(sub)"
              />
              <button @click="videoOff(sub)">OFF</button>
            </div>
          </div>
        
      </div>
    </div>
  </div>
</template>

<script>
import UserVideo from "../components/Attend/UserVideo.vue";
import Entrance from "@/components/Attend/Entrance.vue";
import Ready from '@/components/Attend/Ready.vue';
import Jobs from  '@/components/Attend/Jobs.vue';
import JobSelect from '@/components/Attend/JobSelect.vue';
import { mapState, mapActions } from 'vuex'

const gameStore = 'gameStore';

export default {
  name: "Attend",
  components: {
    UserVideo,
    Entrance,
    Ready,
    Jobs,
    JobSelect,
  },
  data () {
    return {
      
    }
  },
  computed: {
    ...mapState(gameStore, ['hostname', 'subscribers', 'publisher', 'is_enter', 'is_ready', 'jobs'])
  },
  methods: {
    ...mapActions(gameStore, ['setHostname']),
    ready () {
      this.is_ready = true
      
      // if(this.ready){
      //   this.ready = false
      // }else{
      //   this.ready = true
      // }
      // this.$socket.emit('ready', {
      //   message: this.is_ready
      // });
    },
    videoOff(subscriber){
      subscriber.subscribeToAudio(false);  // true to unmute the audio track, false to mute it
      subscriber.subscribeToVideo(false);  // true to enable the video, false to disable it
    },
  },
  created (){
    const hostname = this.$router.history.current.params.hostname
    this.setHostname(hostname)
  }
}
</script>

<style>
.status{
  border: 5px  solid black;
  margin: 2px;
  border-radius: 5%;
  height: 400px;
  
}

.chat{
  border: 5px  solid black;
  margin: 2px;
  border-radius: 5% / 8%;
  height: 150px;
  flex-direction : column;
  
}
</style>