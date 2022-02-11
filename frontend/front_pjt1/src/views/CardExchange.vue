<template>
  <div style="color:white" class="container">
  <img src="https://ifh.cc/g/N2y3IK.png" id="bg" alt="bgImg">
    <div class="my_cam">
      <user-video :streamManager="subPublisher"/>
    </div>
      <div class="my_after_offer">
        나의 명함
        <h3>{{ selected }}</h3>
      </div>
    <div class="my_before_offer">
      <h3>명함 제시</h3>
      <div>
        <h5>
        <b-form-select v-model="selected" :options="options" class="card_select mx-2" :disabled="confirm"></b-form-select>
        <b-button variant="outline-light" class="mx-2" @click="confirm=true">확정!</b-button>
        </h5>
      </div>
    </div>
    <b-icon icon="arrow-left-right" font-scale="3" animation="fade" class="icon_location"></b-icon>
    <div class="opp_cam">
      <user-video :streamManager="subSubscribers[0]"/>
    </div>
    <div class="opp_after_offer">
      상대방의 명함
      <h3>{{ receivedCard }}</h3>
    </div>
    <!-- 상대방의 명함 제시는 필요없다 -->
    <!-- <div class="opp_offer">
      <h3>상대방의 명함 제시</h3>
        <b-form-select v-model="selected" :options="options"></b-form-select>
        <button>확정</button>
    </div> -->
    <div class="timer d-block">
      <p>명함 교환 종료까지...</p>
      <h2>{{ timerCount }}초</h2>
    </div>
    <!-- <button class="skill_button">스킬 사용</button> -->
    <button @click="exitCard">명교 나가기(임시)</button>
    <!-- <b-form-textarea
      id="textarea"
      v-model="text"
      placeholder="Enter something..."
      rows="3"
      max-rows="6"
    ></b-form-textarea>
    <pre class="mt-3 mb-0">{{ text }}</pre>
    <input type="text"> -->
    <div class="d-flex chat">
      <!--채팅 내역 : chat_list-->
      <p
        v-for="message, idx in messages"
        :key="idx"
      >
        {{message}}
      </p>
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
  </div>
</template>

<script>
import UserVideo from '../components/Attend/UserVideo.vue';
import {mapState, mapActions, mapMutations} from 'vuex'

const gameStore = 'gameStore'

export default {
  name:'CardExchange',
  components: { UserVideo },
  data() {
    return {
      chatMessage: '',
      timerCount:20,
      timerExit:23,
      text: '',
      selected: '선택 중',
      options: [
        { value: '선택 중', text: '직업 선택', disabled: true },
        { value: 'KIRA', text: '노트주인' },
        { value: 'CRIMINAL', text: '추종자' },
        { value: 'L', text: '경찰총장' },
        { value: 'POLICE', text: '경찰' },
        { value: 'GUARD', text: '보디가드' },
        { value: 'BROADCASTER', text: '방송인' },
      ],
      confirm: false,
    }
  },
  computed: {
    ...mapState(gameStore, ['subPublisher', 'subSubscribers', 'myJob', 'subSession', 'receivedCard', 'session', 'messages', 'isAlive'])
  },
  watch: {
    timerCount: {
      handler(value) {
        if (value > 0) {
          setTimeout(() => {
            this.timerCount--;
          }, 1000);
        } else {
          if (!this.confirm) {
            this.selected = this.myJob
            this.confirm = true
          }
        }
      },
    immediate: true
    },
    timerExit: {
      handler(val) {
        if (val > 0) {
          setTimeout(() => {
            this.timerExit--;
          }, 1000);
        } else {
          this.RECEIVE_CARD('선택 중')
          this.exitCard()
        }
      },
    immediate: true
    },
    confirm (cur) {
      const selectedCard = {jobName : this.selected}
      if (cur == true) {
        console.log('왓치드 잘 되고 있냐')
        console.log(typeof(this.subSubscribers[0].stream.connection))
        this.subSession.signal({
          type: 'exchangeCard',
          data: JSON.stringify(selectedCard),
          to: [this.subSubscribers[0].stream.connection],
        })
        let checkCardAndJob = false
        if (this.selected == this.myJob) {
          checkCardAndJob = true
        }
        this.session.signal({
          type: 'game',
          data: {
            gameStatus: 5,
            skillType: 'announceToL',
            result: checkCardAndJob
          },
          to: [],
        })
      }
    },
    isAlive (cur) {
      if (cur == false) {
        this.subSession.unPublish(this.subPublisher)
        this.SET_SUB_PUBLISHER(undefined)
      }
    }

  },
  methods: {
    ...mapActions(gameStore, ['exitCard']),
    ...mapMutations(gameStore, ['RECEIVE_CARD', 'SET_SUB_PUBLISHER']),
  }
}
</script>

<style scoped>
.container{
  display: inline-flex;
  border: 1px, solid, yellow;
}
.my_cam {
  position: fixed;
  display: flex;
  border: 1px solid white;
  width: 23%;
  color:white;
  height: 30%;
  left: 15%;
  top: 10%;
}
.opp_cam {
  position: fixed;
  display: flex;
  border: 1px solid white;
  width: 23%;
  color:white;
  height: 30%;
  left: 64%;
  top: 10%;
}
.icon_location {
  position: fixed;
  width: 6%;
  height: 35%;
  top: 7%;
  left: 48%;
}
.timer {
  position: fixed;
  display: flex;
  border: 1px solid transparent;
  width: 10%;
  height: 11%;
  top: 45%;
  left: 46%;
}
.chat {
  position: fixed;
  bottom: 6%;
  right: 9%;
  border-style: 1px solid red;
  border-color: white;
  border-radius: 5px;
  width: 80%;
  height: 35%;
}
.chat_list {
  position: absolute;
  top: 5%;
  left: 5%;
  border-style: solid;
  border-color: white;
  width: 90%;
  height: 70%;
  border-radius: 25px;
}
.chat_input {
  position: absolute;
  top: 80%;
  left: 5%;
  /* border-style: solid; */
  /* border-color: white; */
  background-color: transparent;
  width: 90%;
  height: 15%;
  border-radius: 8px;
}
input[type=text] {
  border: solid transparent;
  color: white
}
input[type=text]:focus {
  border: solid white;
  color: transparent
}
.my_before_offer {
  position: fixed;
  top: 45%;
  left: 21%;
}
.my_after_offer {
  position: fixed;
  top: 21%;
  left: 41.5%;
}
.opp_after_offer {
  position: fixed;
  top: 21%;
  left: 56%;
}
/* .opp_offer {
  position: fixed;
  top: 45%;
  left: 62%;
} */
.card_select {
  background-color: transparent;
  background: black;
  color: white;
  border-radius: 8px;
  text-align: center;
}
.skill_button {
  position: fixed;
  top: 46.5%;
  left: 71.5%;
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
  position:fixed;
	top:1px;
}
.skill_button_police {
  position: fixed;
  top: 46.5%;
  left: 71.5%;
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
  position: fixed;
  top: 46.5%;
  left: 71.5%;
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
</style>
