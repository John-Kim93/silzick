<template>
  <div class="container" style="color:white">
    <img src="https://ifh.cc/g/1WPkxv.png" id="bg" alt="bgImg">
    <h1>{{myJob}}</h1>
    <!--6명 이하 6분할-->
    <div
      v-if="subscribers.length < 6"
      class="d-inline-flex row align-middle total_cam"
    > 
      <!-- 내 비디오 -->
      <div
        class="col-4 private_cam_6"
      >
        <user-video
          class="m-1"
          :stream-manager="publisher"
        />
      </div>
      <!-- 다른 사람 비디오 -->
      <div
        class="col-4 private_cam_6"
        v-for="subscriber in subscribers"
        :key="subscriber.stream.connection.connectionId"
      >
        <user-video
          class="m-1"
          :stream-manager="subscriber"
        />
      </div>
     
    </div>

    <!-- 6명 넘으면 9분할(미구현)-->

    <!--다음 명함 교환 까지(타이머) : timer-->
    <div class="timer d-flex">
      <div>
        <p>
        다음 명함 교환까지
        <br>
        XX초 남았습니다.</p>
      </div>
    </div>

    <!--직업/직업설명/능력/승리조건 : explain-->
    <div class="explain">
      <!--직업-->
      <div class="explain_job dotted_line">
        <strong>직업</strong>
      </div>
      <!--직업설명-->
      <div class="explain_job_description dotted_line">
        직업설명
      </div>
      <!--능력-->
      <div class="explain_skill dotted_line">
        능력
      </div>
      <!--승리조건-->
      <div class="explain_win">
        승리조건
      </div>
    </div>

    <!--채팅 전체 : chat-->
    <div class="d-flex chat">
      <!--채팅 내역 : chat_list-->
      <div class="chat_list">
        <p
          v-for="message, idx in messages"
          :key="idx"
        >
        {{ message }}
        </p>
      </div>
      <!--채팅 입력 : chat_input-->
      <input
        class="chat_input"
        type="text"
        placeholder="메세지를 입력하세요"
        v-model="chatMessage"
        style="color:white;"
        @keyup.enter="enterMessage"
      >
    </div>

    <!--의심 직업 : doubt-->
    <div class="doubt">
      <p>추측 현황</p>
      <div
        class="d-flex justify-content-around align-items-center"
        v-for="subscriber, idx in subscribers"
        :key="idx"
      >
        <doubt :subscriber="subscriber"/>

      </div>
    </div>

    <!--정체 숨기기 횟수 / 스킬 횟수 / 검거권 수-->
    <div class="counts">
      <div>
        <!-- 정체 숨기기 횟수(미션 성공 횟수) -->
        <b-icon icon="eye-slash-fill" font-scale="2" variant="light"></b-icon><p>2회</p>
      </div>
      <div>
        <!-- 스킬 사용 가능 횟수(히든 미션 성공 횟수) -->
        <b-icon icon="lightning-fill" font-scale="2" variant="light"></b-icon><p>3회</p>
      </div>
      <!-- 검거권 수(경찰일때만)-->
      <!-- v-if=="경찰" 사용-->
      <div>
        <b-icon icon="person-bounding-box" font-scale="2" variant="light"></b-icon><p>1회</p>
      </div>
    </div>

    <!--미션 및 히든 미션 : mission-->
    <div class="mission">
      미션 및 히든 미션
    </div>

    <!--스킬사용 및 메모팝업 버튼 : buttons-->
    <div class="d-flex justify-content-around buttons">
      <!--능력사용-->
      <active-skill/>
      <!--메모팝업-->
      <!--1.버튼 사용-->
      <!-- <div>
        <button class="memo_button" @click="openmemo">메모하기</button>
        <div class="modal" v-if="this.memo==true">
          <h1>Memo</h1>
          <textarea name="" id="" cols="auto" rows="auto"></textarea>
        </div>
      </div> -->
      <!--2.인풋과 라벨로만 만들기-->
      <input type="checkbox" id="popup">
      <label for="popup">메모 하기</label>
      <div>
        <div class="popup">
          <label style="color:black" for="popup">X</label>
          <textarea name="" style="border:none; color:#44c767;" id="popup" class="textarea_position" cols="30" rows="10"></textarea>
        </div>
      </div>
      <!-- <label for="popup"></label> -->
    </div>
    <button @click="enterCard">test</button>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import Doubt from '@/components/MainGame/Doubt.vue'
import UserVideo from '@/components/Attend/UserVideo.vue'
import ActiveSkill from '@/components/MainGame/ActiveSkill.vue'

const gameStore = 'gameStore'

export default {
  name : 'MainGame',
  components: {
    Doubt,
    UserVideo,
    ActiveSkill
  },
  data () {
    return {
      chatMessage: '',
      memo : false,
    }
  },
  computed: {
    ...mapState(gameStore, ['myJob', 'nickname', 'subscribers', 'publisher', 'subSession', 'session', 'messages'])
  },

  methods : {
    ...mapActions(gameStore, ['sendMessage']),
    enterCard () {
      console.log(this.publisher.stream.connection.connectionId)
      console.log(this.subscribers[0].stream.connection.connectionId)
      this.session.signal({
        type: 'autoSystem',
        data: JSON.stringify({ action: 'exchangeNameStart' }),
        target: [this.publisher.stream.connection.connectionId, this.subscribers[0].stream.connection.connectionId]
      })
    },
    openmemo () {
      this.memo = true
    },
    enterMessage() {
      if (this.chatMessage.trim()) {
        this.sendMessage(this.chatMessage)
        this.chatMessage=""
      }
    },
  },
}
</script>

<style scoped>
/* custom css */
  .private_cam_6 {
    border-style: solid;
    border-color: white;
    width: 33%;
    height: 50%;
  }
  .private_cam_9 {
    border-style: solid;
    border-color: white;
    width: 33%;
    height: 33%;
  }


/* braught css */
.textarea_position {
  position: absolute;
  top: 5%;
  left: 8%;
  width: 85%;
  height: 90%;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  background: transparent;
  border: none;
  resize: none;
  outline: none;
}

/* 신경X */
input[id*="popup"] {
  display:none;
}
/* 메모하기 버튼 */
input[id*="popup"] + label {
  display: inline-block;
  padding: 20px;
  background: #ffcd41;
  color: #fff;
  background-color:transparent;
	border-radius:28px;
	border:1px solid white;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:17px;
	font-weight:bold;
	padding:16px 31px;
	text-decoration:none;
	text-shadow:0px 1px 0px #2f6627;
}
/* 버튼 클릭 후 div 전체화면인듯?*/
input[id*="popup"] + label + div {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 100;
}
/* 모달창 배경 위에 fixed로 설정해서 absolute가 그 영향 받음 - 메모창 위치*/
input[id*="popup"] + label + div > div {
  position: absolute;
  top: 50%;
  left: 75%;
  transform:translate(-50%, -50%);
  width: 30%;
  height: 30%;
  background: black;
  opacity: 80%;
  border: solid white;
  border-radius: 15px;
  z-index: 2;
}
/* 메모창 닫기버튼 */
input[id*="popup"] + label + div > div > label {
  position: absolute;
  top: 0%;
  right: 0%;
  transform:translate(40%, -40%);
  padding: 2%;
  background: white;
  border-radius: 100%;
  z-index: 1;
  border: solid white;
}
/* 필요X */
/* input[id*="popup"] + label + div > label {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: blue;
  z-index: 1;
} */
input[id*="popup"] + label + div {
  display:none;
}
input[id*="popup"]:checked + label + div {
  display:block;
}
/* input[id*="popup"] + label + div:hover {
  background-color:transparent;
} */
/* .input[id*="popup"] + label :active {
	position:relative;
	top:1px;
} */
.buttons {
  position: fixed;
  top: 79%;
  right: 5%;
  width: 20%
}
.skill_button {
	background-color:#44c767;
	border-radius:28px;
	border:1px solid #18ab29;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:17px;
	font-weight:bold;
	padding:16px 31px;
	text-decoration:none;
	text-shadow:0px 1px 0px #2f6627;
}
.skill_button:hover {
	background-color:#5cbf2a;
}
.skill_button:active {
  position:relative;
	top:1px;
}
.skill_button_police {
	background-color:#1186cf;
	border-radius:28px;
	border:1px solid #4e6096;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:17px;
	padding:16px 31px;
	text-decoration:none;
	text-shadow:0px 1px 0px #283966;
}
.skill_button_police:hover {
  background-color:#476e9e;
}
.skill_button_police:active {
	position:relative;
	top:1px;
}
.skill_button_note {
	background-color:#fc5230;
	border-radius:28px;
	border:1px solid #d02718;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:17px;
	padding:16px 31px;
	text-decoration:none;
	text-shadow:0px 1px 0px #810e05;
}
.skill_button_note:hover {
	background-color:#d65246;
}
.skill_button_note:active {
	position:relative;
	top:1px;
}
.memo_button {
	background-color:transparent;
	border-radius:28px;
	border:1px solid white;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:17px;
	font-weight:bold;
	padding:16px 31px;
	text-decoration:none;
	text-shadow:0px 1px 0px #2f6627;
}
.memo_button:hover {
	background-color:rgb(46, 48, 47);
}
.memo_button:active {
	position:relative;
	top:1px;
}
.total_cam {
  position: fixed;
  top: 18%;
  left: 5%;
  border-style: solid;
  border-color: white;
  width: 50%;
  height: 50%;
  flex-wrap: wrap;
}
.private_cam_4 {
  border-style: solid;
  border-color: white;
  width: 50%;
  height: 50%;
}
.timer {
  position: fixed;
  top: 15%;
  left: 57.5%;
  border-style: solid;
  border-color: white;
  width: 15%;
  height: 10%;
  display: flex;
  justify-content: center;
  align-items: center;
  /* text-align: center; */
}
.chat {
  position: fixed;
  top: 15%;
  right: 5%;
  border-style: solid;
  border-color: white;
  width: 20%;
  height: 60%;
}
.chat_list {
  position: absolute;
  top: 5%;
  left: 5%;
  border-style: solid;
  border-color: white;
  width: 90%;
  height: 70%
}
.chat_input {
  position: absolute;
  top: 80%;
  left: 5%;
  border-style: solid;
  border-color: white;
  background-color: transparent;
  width: 90%;
  height: 15%;
}
.mission {
  position: fixed;
  top: 60%;
  left: 57.5%;
  border-style: solid;
  border-color: white;
  width: 15%;
  height: 27%;
}
.explain {
  position: fixed;
  top: 29%;
  left: 57.5%;
  border-style: solid;
  border-color: white;
  width: 15%;
  height: 27%;
  /* text-align: center; */
}
.dotted_line {
  border-bottom-style: dotted;
  border-color: white;
}
.doubt {
  position: fixed;
  top: 72%;
  left: 5%;
  border-style: solid;
  border-color: white;
  width: 38%;
  height: 15%;
  flex-wrap: wrap;
}

.counts {
  position: fixed;
  top: 72%;
  left: 45%;
  border-style: solid;
  border-color: white;
  width: 10%;
  height: 15%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.explain_job {
  font-size: 120%;
  height: 15%;
}
.explain_job_description {
  height: 30%;
}
.explain_skill {
  height: 25%;
}
.explain_victory {
  height: 30%;
}
</style>