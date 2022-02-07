import axios from 'axios'
import { OpenVidu } from "openvidu-browser";
import { OPENVIDU_SERVER_URL, OPENVIDU_SERVER_SECRET } from '@/config/index.js'
import { jobs } from './gameUtil.js'

axios.defaults.headers.post["Content-Type"] = "application/json";

const gameStore = {
  namespaced: true,

  state: {
    // customed
    isHost: false,
    hostname: undefined,
    nickname: undefined,
    is_enter: false,
    isReady: false,
    readyCount: 0,

    // Ovenvidu
    OV: undefined,
    OVToken: undefined,
    session: undefined,
    publisher: undefined,
    subscribers: [],

    //game
    jobs: jobs,

    //chatting
    messages: [],
  },
  
  mutations: {
    NICKNAME_UPDATE (state, res) {
      console.log(state.is_enter)
      if (state.is_enter == true) {
        state.is_enter = false
        state.nickname = res
      } else {
        state.is_enter = true
        console.log(res)
        state.nickname = res
      }
    },
    IS_HOST (state) {
      state.isHost = true
    },
    SET_HOSTNAME (state, hostname) {
      state.hostname = hostname
    },
    SET_PUBLISHER (state, res) {
      state.publisher = res
    },
    SET_OV (state, res) {
      state.OV = res
    },
    SET_SESSION (state, res) {
      state.session = res
    },
    SET_SUBSCRIBERS (state, res) {
      state.subscribers = res
    },
    SET_OVTOKEN (state, res) {
      state.OVToken = res
    },
    SET_MY_READY (state) {
      state.publisher.ready = !state.publisher.ready
      if (state.publisher.ready) {
        state.readyCount ++
      } else {
        state.readyCount --
      }
    },

    // 채팅 관련 기능
    SET_MESSAGES(state, res) {
      state.messages.push(res.message)
    },

    // 게임 관련 기능
    GET_JOB_PROPS (state, jobProps) {
      state.jobs = jobProps
    },

    CHANGE_JOB_COUNT(state, jobProps) {
      state.jobs.forEach(job => {
        if (job.jobName === jobProps.jobName) {
          job.count = jobProps.count
        }
      })
    },
    
  },
  // setHostname, nicknameUpdate, joinSession, getToken, createSession, createToken, leaveSession
  actions: {
    setHostname ({commit}, hostname) {
      commit('SET_HOSTNAME', hostname)
    },
    async nicknameUpdate ({ state, commit, dispatch }, res) {
      commit('NICKNAME_UPDATE', res)
      await dispatch('joinSession')
      state.session.signal({
        type: 'getJobProps',
        to: [],
      })
    },
    joinSession({ commit, dispatch, state }) {
      // --- Get an OpenVidu object ---
      const OV = new OpenVidu();
      // --- Init a session ---
      const session = OV.initSession();
      const subscribers = [];
      
      // --- Specify the actions when events take place in the session ---
      
      // On every new Stream received...
      session.on("streamCreated", ({ stream }) => {
        const subscriber = session.subscribe(stream);
        subscriber.ready = false
        // const readyStatus = {
        //   subscrberId: subscriber.stream.connection.connectionId,
        //   ready: false,
        // }
        subscribers.push(subscriber);
        // subsReady.push(readyStatus);
      });
      
      // On every Stream destroyed...
      session.on("streamDestroyed", ({ stream }) => {
        const index = subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          subscribers.splice(index, 1);
        }
      });
      
      // On every asynchronous exception...
      session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      session.on("signal:chat", (event)=>{
        let eventData = JSON.parse(event.data);
        let data = new Object()
        data.message = eventData.message;
        commit('SET_MESSAGES', data)
      });

      session.on("signal:ready", (event)=> {
        state.subscribers.forEach(subscriber => {
          if (subscriber.stream.connection.connectionId === event.from.connectionId) {
            subscriber.ready = !subscriber.ready
            if (subscriber.ready) {
              state.readyCount ++
            } else {
              state.readyCount --
            }
          }
        })
      });

      session.on("signal:getJobProps", (event) => {
        let jobProps = JSON.parse(event.data)
        commit('GET_JOB_PROPS', jobProps)
      });

      session.on("signal:changeJobCount", (event) => {
        let job = JSON.parse(event.data)
        commit('CHANGE_JOB_COUNT', job)
      });

      
      
      // --- Connect to the session with a valid user token ---
      
      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      console.log('겟토큰 전에 스테이트호스트네임')
      console.log(state.hostname)
      dispatch("getToken", state.hostname).then((token) => {
        session
        .connect(token, { clientData: state.nickname })
        .then(() => {
          // --- Get your own camera stream with the desired properties ---
          let publisher = OV.initPublisher(undefined, {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: undefined, // The source of video. If undefined default webcam
            publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: true, // Whether you want to start publishing with your video enabled or not
            resolution: "640x480", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: false, // Whether to mirror your local video or not
          });
          publisher.ready = false
          commit('SET_OV', OV)
          commit('SET_PUBLISHER', publisher)
          commit('SET_SESSION', session)
          commit('SET_SUBSCRIBERS', subscribers)
          commit('SET_OVTOKEN', token)

            // --- Publish your stream ---
          session.publish(state.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });
      // window.addEventListener("beforeunload", this.leaveSession);
    },
    getToken({ dispatch }, mySessionId) {
      console.log('겟토큰')
      console.log(mySessionId)

      return dispatch('createSession', mySessionId).then((sessionId) =>
        dispatch('createToken', sessionId)
      );
    },
    createSession(context, sessionId) {
      console.log('크리에이트세션')
      console.log(sessionId)
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,}),
            {
              headers: {
                'Content-Type' : 'application/json'
              },
              auth: {
                username: "OPENVIDUAPP",
                password: "MY_SECRET",
              },
            }
          )
          .then((response) =>response.data)
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
    createToken(context, sessionId) {
      console.log('크리에이트토큰')
      console.log(sessionId)
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/api/tokens`,JSON.stringify({
              "session": sessionId,}),
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
    leaveSession({state, commit}) {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (state.session) state.session.disconnect();

      commit('SET_SESSION', undefined)
      commit('SET_PUBLISHER', undefined)
      commit('SET_OV', undefined)
      commit('SET_OVTOKEN', undefined)
      commit('SET_SUBSCRIBERS', [])
      commit('SET_HOSTNAME', undefined)
      commit('SET_NICKNAME', undefined)
      commit('NICKNAME_UPDATE', undefined)

      // window.removeEventListener("beforeunload", this.leaveSession);
    },
    // 채팅 관련 통신
    sendMessage ({ state }, message) {
      state.session.signal({
        type: 'chat',
        data: JSON.stringify({message}),
        to: [],
      })
    },
    setReady ({commit, state}) {
      commit('SET_MY_READY')
      const readyRequest = {
        isReady : state.isReady
      }
      state.session.signal({
        type: 'ready',
        data: JSON.stringify(readyRequest),
        to: [],
      })
    },

    // 게임 기능
    changeJobCount({ state }, jobProps) {
      state.session.signal({
        type: 'plusJobCount',
        data: JSON.stringify(jobProps),
        to: [],
      })
    },
    // prepareGame ({state}) {

    // }

  },
}

export default gameStore;