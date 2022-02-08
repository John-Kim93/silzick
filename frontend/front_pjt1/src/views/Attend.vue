<template>
  <div id="main-container" class="container d-flex">
    <!-- 작성 후 세션의 대기방 -->
    <div id="enter" class="container">
      <div>
        <div class=" row">
          {{sessionId}}의 방
        </div>
        <button @click="test3">test3</button>
        <hr>
        <div class="row justify-content-center">
          <!-- 참가자 리스트 -->
          <div class="col status">
            <div v-if="publisher">
              <ready :streamManager="publisher"/>
              <div
                v-for="sub in subscribers"
                :key="sub.stream.session.connection.connectionId"
              >
                <ready :stream-manager="sub" />
              </div>
              <div>{{ readyCount }}</div>
            </div>
          </div>
          <!-- 직업리스트 -->
          <div class="col status">
            <jobs :job="job" v-for="job in jobs" :key="job.jobName"/>          
          </div>

          <!-- 적용직업리스트 -->
          <div class="col status">
            <job-select :job="job" v-for="job in jobs" :key="job.jobName"/>
          </div>
          <hr>
          <div class="row d-flex justify-content-between">
            <div class="chat col-8">
              <div> chat</div>
              <input type="text" class="w-auto">
            </div>
            <button class="btn btn-success col" @click="setReady">Ready</button>
          </div>
          
        </div>
      </div>  
      <!-- 레디시 영상 송출 -->
      <div id="RTC" v-if="false">
        <div>
          <div
            v-for="(message, index) of messages"
            :key="index"
          >
            <!-- <span>{{ message.sender }}</span> -->
            <!-- <span>{{ message.time }}</span> -->
            <span>{{ message }}</span>
          </div>
          <input type="text" style="color:black;" v-model="message" @keyup.enter="clickSendMessage">
        </div>
          <h1 id="session-title">{{ sessionId }}</h1>
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
              
            />
            <div
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
            >
              <user-video
                :stream-manager="sub"
                
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
import Ready from '@/components/Attend/Ready.vue';
import Jobs from  '@/components/Attend/Jobs.vue';
import JobSelect from '@/components/Attend/JobSelect.vue';
import { mapState, mapActions } from 'vuex'

const gameStore = 'gameStore';

export default {
  name: "Attend",
  components: {
    UserVideo,
    Ready,
    Jobs,
    JobSelect,
  },
  data () {
    return {
      message: "init",
      ready: true
    }
  },
  computed: {
    ...mapState(gameStore, ['sessionId', 'subscribers', 'publisher', 'jobs', 'nickname', 'messages', 'readyCount', 'isHost', 'session']),
  },
  methods: {
    ...mapActions(gameStore, [ 'sendMessage', 'leaveSession', 'setReady', 'getReadyState']),
    
    test3() {
      this.session.signal({
        type: 'game',
        data: {
          gameStatus: 3
        },
        to: [],
      })
    },

    clickSendMessage() {
      if (this.message.trim()) {
        this.sendMessage(this.message)
        this.message=""
      }
    },
    
    videoOff(subscriber){
      subscriber.subscribeToAudio(false);  // true to unmute the audio track, false to mute it
      subscriber.subscribeToVideo(false);  // true to enable the video, false to disable it
    },
  },
  created () {
    this.getReadyState
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