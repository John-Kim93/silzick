<template>
<div>
  <!-- 1. 노트주인 -->
  <div class="border border-dark rounded-3" v-if='myJob == "KIRA"'>
    <div>
      <h1 class="first_font" style="color:red">
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
      <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning first_font">
        누구를 죽이시겠습니까?
        <select
            v-model="selectSubscriber"
            class="select_list first_font"
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
            class="select_list first_font"
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
      <b-button
        size="sm"
        class="skill_button_note my-3 first_font font_size"
        @click="noteWrite"
      >
        KILL !
      </b-button>
    </div>
  </div>

  <!-- 2. 추종자 -->
  <div class="border border-dark rounded-3" v-if='myJob == "CRIMINAL"'>
    <div>
      <h1 class="first_font" style="color:red">
        <font size="7">
          <b-iconstack  animation="">
            <b-icon stacked icon="receipt" animation="" rotate="180"></b-icon>
            <b-icon stacked icon="pencil" animation="cylon" rotate="90" scale="0.35"
              ></b-icon>
          </b-iconstack>
          신의 뜻
        </font>
      </h1>
    </div>
    <div variant="black" class="d-block text-center">
        <h5>
        <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
        <h4 class="link-warning first_font">
          당신은 당신이 따르는 신을 만나 신의 능력 일부를 받았습니다.<br>
          단, 신에게 도움이 될 기회는 한 번 뿐 입니다.<br>
          다시 힘을 얻고 싶다면 다시 한 번 당신의 신과 만나십시오. <br>
          신중한 선택을 하시기 바랍니다.
        </h4>
        </h5>
        <h5 class="first_font">
          <select class="select_list first_font">
            <option selected disabled>참가자 목록</option>
            <option v-for="sub in subscribers"
            :key="sub.stream.connection.connectionId">
              <user-list :streamManager="sub" doubt="true" />
            </option>
          </select>
          은/는 
          <select class="select_list first_font">
            <option selected disabled>직업</option>
            <option v-for="job in jobs" :key="job">
              {{job.jobName}}
            </option>
          </select>
          이다.
        </h5>
    </div>
    <div class="w-100 d-block text-center" >
      <b-button
        size="sm"
        class="skill_button_note my-3 first_font font_size"
        @click="show=false"
      >
        GOD's WILL !
      </b-button>
    </div>
  </div>

  <!-- 3.경찰총장 -->
  <div class="border border-dark rounded-3" v-if='myJob == "L"'>
    <div>
      <h1 class="first_font" style="color:blue">
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
      <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning first_font">
        당신은 특유의 통찰력으로<br>
        진실을 파악할 수 있습니다.<br>
        단, 명함의 진위여부 뿐입니다.<br>
        신중한 선택을 하시기 바랍니다.
      </h4>
    </div>
    <div class="w-100 d-block text-center" >
      <b-button
        variant="primary"
        size="sm"
        class="skill_button_police my-3 first_font font_size"
        @click="show=false"
      >
        DETECT !
      </b-button>
    </div>
  </div>

  <!-- 4. 보디가드 -->
  <div class="border border-dark rounded-3" v-if='myJob == "GUARD"'>
    <div>
      <h1 class="first_font" style="color:blue">
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
      <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning first_font">
        당신은 누군가를 보호할 힘을 가지고 있습니다.<br>
        단, 자신은 보호할 수 는 없습니다. <br>
        신중한 선택을 하시기 바랍니다.
        <select class="select_list first_font">
            <option selected disabled>참가자 목록</option>
            <option v-for="sub in subscribers"
            :key="sub.stream.connection.connectionId">
              <user-list :streamManager="sub" doubt="true" />
            </option>
        </select>
        을/를 보호합니다.
      </h4>
    </div>
    <div class="w-100 d-block text-center" >
      <b-button
        variant="primary"
        size="sm"
        class="skill_button_police my-3 first_font font_size"
        @click="show=false"
      >
        SAVE !
      </b-button>
    </div>
  </div>

  <!-- 5. 방송인 -->
  <div class="border border-dark rounded-3" v-if='myJob == "BROADCASTER"'>
    <div>
      <h1 class="first_font" style="color:blue">
        <font size="7">
          <b-icon icon="mic" animation="fade"></b-icon>
          방송하기
        </font>
      </h1>
    </div>
    <div variant="black" class="d-block text-center">
      <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning first_font">
        당신은 익명의 권리를 행사할 수 있습니다.<br>
        단, 그 권리가 당신에게 득이 될지 <br>
        실이 될지 아무도 모릅니다.<br>
        신중한 선택을 하시기 바랍니다.
      </h4>
      <textarea type="text" 
        class="border rounded" 
        rows="6"
        style="background-color:transparent; width:500px" 
        placeholder="방송할 멘트를 입력하세요">
      </textarea>
    </div>
    <div class="w-100 d-block text-center" >
      <b-button
        variant="primary"
        size="sm"
        class="skill_button_police my-3 first_font font_size"
        @click="show=false"
      >
        BROADCAST!
      </b-button>
    </div>
  </div>

  <!-- 6. 경찰 -->
  <div class="border border-dark rounded-3" v-if='myJob == "POLICE"'>
    <div>
      <h1 class="first_font" style="color:blue">
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
      <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning first_font">
        당신은 누군가를 검거할 수 있는 힘이 있습니다.<br>
        단, 검거대상이 노트 주인측이 아니라면 본인 직업이 노출되고,<br>
        그 즉시 노트주인에게 살해 당합니다.<br>
        신중한 선택을 하시기 바랍니다.
      </h4>
      <h5 class="first_font">
        <select class="select_list first_font">
          <option selected disabled>참가자 목록</option>
          <option v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId">
            <user-list :streamManager="sub" doubt="true" />
          </option>
        </select>
        은/는 노트측이다.
      </h5>
    </div>
    <div class="w-100 d-block text-center" >
      <b-button
        variant="primary"
        size="sm"
        class="skill_button_police my-3 first_font font_size"
        @click="show=false"
      >
        ARREST !
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
  name: 'ActiveSkill',
  data(){
    return {
      show: false,
      selectSubscriber: '참가자 목록',
      selectJobName: '직업',
    }
  },
  computed: {
    ...mapState(gameStore, ['myJob', 'jobs', 'subscribers', 'session'])
  },
  methods: {
    noteWrite () {
      this.show = false
      console.log(this.selectSubscriber)
      this.session.signal({
        type: 'game',
        data: {
          gameStatus: 5,
          skillType: 'noteWrite',
          target: this.selectSubscriber,
          jobName: this.selectJobName
        },
        to: [],
      })
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
.select_list {
  background-color: transparent;
  background: black;
  color: white;
  border-radius: 8px;
  text-align: center;
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
	background-color:#7c7a7a;
	border-radius:28px;
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
.first_font {
  font-family: 'Chilgok_Kaj', sans-serif;
  font-size: 5vh;
}
.second_font {
  font-family: 'Chilgok_Kaj', cursive;
  font-size: 3vh;
}
.font_size {
  font-size: 130%;
}
.skill_border {
  border: 3px solid white;
}
input[type=text] {
  border: solid black;
  color: white;
  border-radius: 8px;
}
input[type=text]:focus {
  border: solid white;
  color: white;
}
</style>