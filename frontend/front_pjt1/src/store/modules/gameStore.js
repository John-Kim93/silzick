import axios from 'axios'
import { OpenVidu } from "openvidu-browser";
import { OPENVIDU_SERVER_URL, OPENVIDU_SERVER_SECRET } from '@/config/index.js'
import { jobs } from './gameUtil.js'
import router from '@/router/index.js'

axios.defaults.headers.post["Content-Type"] = "application/json";

const gameStore = {
  namespaced: true,

  state: {
    // customed
    isHost: false,
    nickname: undefined,
    isReady: false,
    readyCount: 0,
    activeGameStart: false,
    
    // Ovenvidu
    OV: undefined,
    OVToken: undefined,
    sessionId: undefined,
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
        state.nickname = res
    },
    // 메인페이지에서 "방생성" 누르고 들어오면 isHost = true
    IS_HOST (state) {
      state.isHost = true
    },
    SET_PUBLISHER (state, res) {
      state.publisher = res
    },
    SET_OV (state, res) {
      state.OV = res
    },
    SET_SESSIONID (state, sessionId) {
      console.log('!!!!!!!!!!!!!!!!!')
      state.sessionId = sessionId
    },
    SET_SESSION (state, res) {
      console.log(res)
      console.log('@@@@@@@@@@@@@@@@@')
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
    // 직업 리스트 입력
    GET_JOB_PROPS (state, jobProps) {
      state.jobs = jobProps
    },

    // 직업 정보 내 count 증감
    CHANGE_JOB_COUNT(state, jobProps) {
      state.jobs.forEach(job => {
        if (job.jobName === jobProps.jobName) {
          job.count = jobProps.count
        }
      })
    },
    
  },

  actions: {
    // Attend에서 참가 누르면 닉네임 받아옴. 닉네임 받아서 조인세션허고 직업 리스트 요청
    nicknameUpdate ({ commit, dispatch }, res) {
      console.log('세션아이디 잘 받았다')
      console.log(res.sessionId)
      commit('NICKNAME_UPDATE', res.nickname)
      commit('SET_SESSIONID', res.sessionId)
      dispatch('joinSession')
    },
    // ★★★★★★★★★★★★★★겁나 중요함★★★★★★★★★★★★★★★★★
    // 오픈바이두 연결하는 세션만드는 함수, 닉네입 입력 후 참가 누르면 동작함
    async joinSession({ commit, dispatch, state }) {
      // --- Get an OpenVidu object ---
      const OV = new OpenVidu();
      // --- Init a session ---
      const session = OV.initSession();

      const subscribers = [];
      // --- Specify the actions when events take place in the session ---
      
      // On every new Stream received...
      // stream = 영상 송출과 관련된 정보들
      // 세션에 publisher를 등록하면 자동으로 streamCreated가 실행되고 다른사람의 subscribers에 내 stream정보를 담는 로직
      session.on("streamCreated", ({ stream }) => {
        const subscriber = session.subscribe(stream);
        console.log('스트림 크리에이티드 섭스크라이버스 출력')
        console.log(subscriber)
        subscriber.ready = false
        subscribers.push(subscriber);
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

      // session.on의 첫번째 인자 = event(String), 두번째 인자 = 앞의 event를 받아서 실행하는 함수(Function)
      // event.data에 채팅 input에서 받은 내용을 parsing해서 state의 messages에 반영
      session.on("signal:chat", (event)=>{
        let eventData = JSON.parse(event.data);
        let data = new Object()
        data.message = eventData.message;
        commit('SET_MESSAGES', data)
      });

      // ready 시그널에서 보낸사람 ID를 받음, 내 subscribers 기준으로 같은 사람 찾아서 ready 상태 변경
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

      // 직업 리스트 백에서 받아와서 state 수정
      session.on("signal:game", (event) => {
        if(event.data.gameStatus === 2){
          state.subscribers.forEach(subscriber => {
            subscriber.ready = event.data[subscriber.stream.connection.connectionId]
          })
        }
      });
      session.on("signal:game", (event) => {
        if(event.data.gameStatus === 3){
          console.log('게임시그널 받았다.')
          console.log(event)
          console.log(typeof(event))
          state.subscribers.forEach(subscriber => {
            subscriber.ready = event.data[subscriber.stream.connection.connectionId]
          })
        }
      });

      // 프론트에서 방장이 직업 +- 누르면 state의 직업별 count 숫자 바꿔주기
      session.on("signal:changeJobCount", (event) => {
        let job = JSON.parse(event.data)
        commit('CHANGE_JOB_COUNT', job)
      });
      // --- Connect to the session with a valid user token ---
      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      await dispatch("getToken", state.sessionId).then((token) => {
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
          console.log('퍼블리싱 되고있다')
          session.publish(state.publisher);
          router.push({
            name: 'Attend',
            params: { hostname: state.sessionId}
          })
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
    getToken({ dispatch }, mySessionId) {
      return dispatch('createSession', mySessionId).then((sessionId) =>
        dispatch('createToken', sessionId)
      );
    },
    createSession(context, sessionId) {
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
      commit('SET_SESSIONID', undefined)
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
        type: 'changeJobCount',
        data: JSON.stringify(jobProps),
        to: [],
      })
    },

    getReadyState({state}) {
      state.session.signal({
        type: 'game',
        data: {
          gameStatus: 2
        },
        to: [],
      })
    }
  },
}

export default gameStore;