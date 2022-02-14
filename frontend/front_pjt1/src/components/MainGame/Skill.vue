<template>
<div id="base-font">
  <!-- 1. 노트주인 -->
  <div class="" v-if='myJob == "노트주인"'>
    <div>
      <h1>
        <font size="7">
          <b-iconstack  animation="">
            <b-icon stacked icon="receipt" animation="" rotate="180"></b-icon>
            <b-icon stacked icon="pencil" animation="cylon" rotate="90" scale="0.35"></b-icon>
          </b-iconstack>
          정의구현
        </font>
      </h1>
    </div>
    <div variant="black" class="d-block text-center">
      <b-icon
        icon="exclamation-triangle"
        font-scale="4"
        variant="warning"
        class="m-2"
      ></b-icon>
      <h4>
        <p>누구를 죽이시겠습니까?</p>
        <select
            v-model="selectSubscriber"
            id="background-black"
            class="select-btn"
          >
            <option selected disabled>참가자 목록</option>
            <option
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
              :value="sub.stream.connection.connectionId"
            >
              {{sub.stream.connection.data.slice(15, -2)}}
            </option>
          </select>
          은/는 
          <select
            v-model="selectJobName"
            id="background-black"
            class="select-btn"
          >
            <option selected disabled>직업</option>
            <option
              v-for="job in jobs"
              :key="job.jobName"
            >
              {{job.jobName}}
            </option>
          </select>
          이다.
      </h4>
    </div>
    <div class="w-100 d-block text-center" >
      <button
        id="btn-color-kira"
        class="btn btn-lg m-3"
        @click="noteWrite"
      >
        노트에 적는다
      </button>
      <b-button
        id="btn-color-kira"
        class="btn btn-lg m-3"
        @click="noteUse"
      >
        KILL !
      </b-button>
    </div>
  </div>

  <!-- 2. 추종자 -->
  <div class="border border-dark rounded-3" v-if='myJob == "추종자"'>
    <h1 class="" style="">
      <font size="7">
        <b-iconstack  animation="">
          <b-icon stacked icon="receipt" animation="" rotate="180"></b-icon>
          <b-icon stacked icon="pencil" animation="cylon" rotate="90" scale="0.35"
            ></b-icon>
        </b-iconstack>
        노트 조각
      </font>
    </h1>
    <div variant="black" class="d-block text-center">
      <b-icon class="mb-3" icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning">
        누구를 죽이시겠습니까?
      </h4>
      <h5>
        <select
          v-model="selectParticipant"
          id="background-black"
          class="select-btn"
        >
          <option selected disabled>참가자 목록</option>
          <option
            v-for="participant, idx in participants"
            :key="idx"
            :value="participant.connectionId"
          >
            {{participant.nickname}}
          </option>
        </select>
        은/는 
        <select
          v-model="selectJobName"
          id="background-black"
          class="select-btn"
        >
          <option selected disabled>직업</option>
          <option
            v-for="job in jobs"
            :key="job.jobName"
          >
            {{job.jobName}}
          </option>
        </select>
        이다.
      </h5>
    </div>
    <div class="w-100 d-block text-center" >
      <b-button
        id="btn-color-kira"
        size="lg"
        class="my-3"
        @click="kill"
      >
        Kill
      </b-button>
    </div>
  </div>

  <!-- 3.경찰총장 -->
  <div class="border border-dark rounded-3" v-if='myJob == "경찰총장"'>
    <div>
      <h1 class="" style="">
        <font size="7">
          <b-iconstack  animation="">
            <b-icon stacked icon="eye" variant="primary" animation="throb"></b-icon>
            <!-- <b-icon stacked icon="arrows-angle-contract" animation="throb" rotate="90"></b-icon> -->
            <b-icon stacked icon="person" animation="" variant="danger"></b-icon>
            <!-- <b-icon stacked icon="arrows-angle-contract" animation="throb"></b-icon> -->
          </b-iconstack>
          진실의 눈
        </font>
      </h1>
    </div>
    <div variant="black" class="d-block text-center">
      <b-icon class="mb-3" icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning">
        당신은 특유의 통찰력으로<br>
        진실을 파악할 수 있습니다.<br>
        단, 명함의 진위여부 뿐입니다.<br>
        신중한 선택을 하시기 바랍니다.
      </h4>
    </div>
  </div>

  <!-- 4. 보디가드 -->
  <div class="border border-dark rounded-3" v-if='myJob == "보디가드"'>
    <div>
      <h1 class="" style="">
        <font size="7">
          <b-iconstack  animation="">
            <b-icon stacked icon="shield" font-scale="3" animation="fade"></b-icon>
            <b-icon stacked icon="people" animation=""></b-icon>
          </b-iconstack>  
            보호하기
        </font>
      </h1>
    </div>
    <div variant="black" class="d-block text-center">
        <h5>
          <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
          <h4 class="link-warning">
            당신은 누군가를 보호할 힘을 가지고 있습니다<br>
            단, 자신은 보호할 수 는 없습니다. <br>
            신중한 선택을 하시기 바랍니다.
          </h4>
          <select
            v-model="selectParticipant"
            id="background-black"
            class="select-btn"
          >
            <option selected disabled>참가자 목록</option>
            <option
              v-for="participant, idx in participants"
              :key="idx"
              :value="participant.connectionId"
            >
              {{participant.nickname}}
            </option>
          </select>
          을/를 보호합니다.
        </h5>
      </div>
    <div class="w-100 d-block text-center" >
      <b-button
        id="btn-color"
        size="lg"
        class="my-3"
        @click="protect"
      >
        PROTECT!
      </b-button>
    </div>
  </div>

  <!-- 5. 방송인 -->
  <div class="border border-dark rounded-3" v-if='myJob == "방송인"'>
    <div>
      <h1 class="" style="">
        <font size="7">
          <b-icon icon="mic" animation="fade"></b-icon>
          방송하기
        </font>
      </h1>
    </div>
    <div variant="black" class="d-block text-center">
      <h5>
        <b-icon class="mb-3" icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
        <h4 class="link-warning">
          당신은 익명의 권리를 행사할 수 있습니다<br>
          단, 그 권리가 당신에게 득이 될지 <br>
          실이 될지 아무도 모릅니다.<br>
          신중한 선택을 하시기 바랍니다.
        </h4>
        <textarea
          id="base-font"
          type="text" 
          class="border rounded" 
          rows="6"
          style="background-color:transparent; width:50vh;" 
          placeholder="방송할 멘트를 입력하세요"
          v-model="broadcastMessage"
        >
        </textarea>
      </h5>
    </div>
    <div class="w-100 d-block text-center" >
      <b-button
        id="btn-color"
        size="lg"
        class="my-3"
        @click="broadcast"
      >
        BROADCAST!
      </b-button>
    </div>
  </div>

  <!-- 6. 경찰 -->
  <div class="border border-dark rounded-3" v-if='myJob == "경찰"'>
    <div>
      <h1 class="" style="">
        <font size="7">
          <b-iconstack>
            <b-icon stacked icon="fullscreen" animation="throb"></b-icon>
            <b-icon stacked icon="person" animation="" variant="danger"></b-icon>
          </b-iconstack>
          검거하기
        </font>
      </h1>
    </div>
    <div variant="black" class="d-block text-center">
      <b-icon class="mb-3" icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning mb-3">
        당신은 누군가를<br>
        검거할 수 있는 힘이 있습니다.<br>
        단, 검거 대상이 경찰측인 경우 <br>
        당신은 해고당합니다.(사망처리)<br>
        신중한 선택을 하시기 바랍니다.
      </h4>
      <h5>
        <select
          v-model="selectParticipant"
          id="background-black"
          class="select-btn"
        >
          <option selected disabled>참가자 목록</option>
          <option
            v-for="participant, idx in participants"
            :key="idx"
            :value="participant.connectionId"
          >
            {{participant.nickname}}
          </option>
        </select>
        을/를 검거한다.
      </h5>
    </div>
    <div class="w-100 d-block text-center" >
      <b-button
        id="btn-color"
        size="lg"
        class="my-3"
        @click="arrest"
      >
        ARREST!
      </b-button>
    </div>
  </div>
</div>
</template>

<script src="https://kit.fontawesome.com/ac38071ee5.js" crossorigin="anonymous"></script>
<script>
import {mapState} from 'vuex'

const gameStore = 'gameStore'

export default {
  name: 'Skill',
  data(){
    return {
      show: false,
      selectParticipant: '참가자 목록',
      selectJobName: '직업',
      broadcastMessage: '',
    }
  },
  computed: {
    ...mapState(gameStore, ['myJob', 'jobs', 'session', 'participants'])
  },
  methods: {
    noteWrite () {
      this.show = false
      console.log(this.selectParticipant)
      this.session.signal({
        type: 'game',
        data: {
          gameStatus: 5,
          skillType: 'noteWrite',
          target: this.selectParticipant,
          jobName: this.selectJobName
        },
        to: [],
      })
      this.selectParticipant = '참가자 목록'
      this.selectJobName = '직업'
    },
    noteUse () {
      this.show = false
      this.session.signal({
        type: 'game',
        data: {
          gameStatus: 5,
          skillType: 'noteUse',
        },
        to: [],
      })
    },
    broadcast () {
      this.show = false
      this.session.signal({
        type: 'game',
        data: {
          gameStatus: 5,
          skillType: 'announce',
          announce: this.broadcastMessage,
        },
        to: [],
      })
      this.broadcastMessage = ''
    },
    arrest () {
      this.show = false
      this.session.signal({
        type: 'game',
        data: {
          gameStatus: 5,
          skillType: 'arrest',
          target: this.selectParticipant,
        },
        to: [],
      })
      this.selectParticipant = '참가자 목록'
    },
    protect () {
      this.show = false
      this.session.signal({
        type: 'game',
        data: {
          gameStatus: 5,
          skillType: 'protect',
          target: this.selectParticipant,
        },
        to: [],
      })
      this.selectParticipant = '참가자 목록'
    },
    kill () {
      this.show = false
      this.session.signal({
        type: 'game',
        data: {
          gameStatus: 5,
          skillType: 'kill',
          target: this.selectParticipant,
          jobName: this.selectJobName
        },
        to: [],
      })
      this.selectParticipant = '참가자 목록'
      this.selectJobName = '직업'
    }
  },
}
</script>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Black+And+White+Picture&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Kirang+Haerang&display=swap');
.center_position {
  position: fixed;
  display: block;
  width: 50%;
  height: 50%;
  top: 38%;
  left: 25%;
  justify-content: center;
  text-align: center;
  align-items: center;
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
.skill_button_police:active {
	position:relative;
	top:1px;
}
.skill_button_note {
	background-color:#F05454;
	border-radius:28px;
	border:1px solid #F05454;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:17px;
	padding:16px 31px;
	text-decoration:none;
}
.skill_button_note:active {
	position:relative;
	top:1px;
}
input[type=text] {
  border: solid white;
  border-radius: 5px;
  color: transparent;
}
input[type=text]:focus {
  border: solid white;
  color: white
}
.skill_button {
	background-color:#30475E;
	border-radius:28px;
	border:1px solid #30475E;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:0.8rem;
	font-weight:bold;
	padding:16px 31px;
	text-decoration:none;
}
</style>