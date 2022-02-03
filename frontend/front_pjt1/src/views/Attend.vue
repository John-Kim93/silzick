<template>
  <div id="main-container" class="container d-flex">
    
    <!-- 닉네임 작성전 대기 -->
    <div id="noenter" v-if="!is_enter">
      <entrance  @nick-name-update='nicknameUpdate'/>
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
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "../components/Attend/UserVideo.vue";
import Entrance from "@/components/Attend/Entrance.vue";
import Ready from '@/components/Attend/Ready.vue';
import Jobs from  '@/components/Attend/Jobs.vue';
import JobSelect from '@/components/Attend/JobSelect.vue';

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "Attend",
  components: {
    UserVideo,
    Entrance,
    Ready,
    Jobs,
    JobSelect,
  },
  data: function(){
    return {
      hostname: this.$route.params.hostname,
      nickname: '',
      is_enter: false,
      is_ready: false,

      // Ovenvidu
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      //game
      jobs:['키라','추종자','경찰총장','방송인','경찰']
    }
  },
  methods: {
    nicknameUpdate:function(res){
      console.log(res)
      this.is_enter = true
      this.nickname = res
      this.joinSession()
    },

    ready: function(){
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
    // Openvidu
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.hostname).then((token) => {
        this.session
          .connect(token, { clientData: this.nickname })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      this.is_enter = false
      this.is_ready = false

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
  },
  created: function(){
    // this.$socket.on('ready', (data) => {
    //   console.log(data)
    // })
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