<template>
  <div class="d-flex" style="color:white">
    <!--disabled 사용해서 skill이 1이상일때만 사용가능하도록-->
    <button id="btn-color" class="btn btn-lg" style="height:10vh; width:15vw" @click="show=true"> Skill</button>
    <!-- 1.노트주인 -->
    <b-modal v-model='show' v-if='myJob == "KIRA"' 
      id='skill' 
      size="md"
      variant='outline-primary' 
      header-bg-variant="black"
      header-text-variant="danger"
      body-bg-variant="black"
      body-text-variant="light"
      footer-bg-variant="black"
      footer-text-variant="light"
      centered
      >
      <template #modal-header  >
      <div></div>
        <h1>
          <b-iconstack scale="2" animation="">
            <b-icon stacked icon="receipt" animation="" rotate="180"></b-icon>
            <b-icon stacked 
              icon="pencil" 
              animation="cylon" 
              rotate="90"
              scale="0.35"
              ></b-icon>
          </b-iconstack>
          DEATH NOTE
        </h1>
        <div></div>
      </template> 
      <div variant="black" class="d-block text-center">
        <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
        <h4 class="link-warning">
          누구를 죽이시겠습니까?
        </h4>
        <h5>
          <select
            v-model="selectParticipant"
            class="select_list"
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
            class="select_list"
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
      <template #modal-footer>
        <div class="w-100 d-block text-center" >
          <b-button
            size="sm"
            class="skill_button_note  my-3"
            @click="noteWrite"
            :disabled="numberOfSkillUsed < 1"
          >
            노트에 적는다(사용가능 : {{numberOfSkillUsed}})
          </b-button>
          <b-button
            size="sm"
            class="skill_button_note my-3"
            @click="noteUse"
          >
            모두 죽이기
          </b-button>
        </div>
      </template>
    </b-modal>

    <!-- 2. 추종자 -->
    <b-modal v-model="show" v-if='myJob == "CRIMINAL"'
      id='skill' 
      size="lg"
      variant='outline-primary' 
      header-bg-variant="black"
      header-text-variant="danger"
      body-bg-variant="black"
      body-text-variant="light"
      footer-bg-variant="black"
      footer-text-variant="light"
      centered
      >
      <template #modal-header  >
        <div></div>
        <h1>
          <b-iconstack scale="2" animation="">
            <b-icon stacked icon="receipt" animation="" rotate="180"></b-icon>
            <b-icon stacked 
              icon="pencil" 
              animation="cylon" 
              rotate="90"
              scale="0.35"
              ></b-icon>
          </b-iconstack>
          노트 조각
        </h1>
        <div></div>
      </template> 
      <div variant="black" class="d-block text-center">
        <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
        <h4 class="link-warning">
          누구를 죽이시겠습니까?
        </h4>
        <h5>
          <select
            v-model="selectParticipant"
            class="select_list"
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
            class="select_list"
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
      <template #modal-footer>
        <div class="w-100 d-block text-center" >
          <b-button
            size="sm"
            class="skill_button_note my-3"
            :disabled='numberOfSkillUsed < 1'
            @click="kill"
          >
            Kill (사용가능 : {{numberOfSkillUsed}})
          </b-button>
        </div>
      </template>
    </b-modal>
    <!-- 4.보디가드 -->
    <b-modal v-model="show" v-if='myJob == "GUARD"' 
      id='skill' 
      size="lg"
      variant='outline-primary' 
      header-bg-variant="black"
      header-text-variant="primary"
      body-bg-variant="black"
      body-text-variant="light"
      footer-bg-variant="black"
      footer-text-variant="light"
      centered
      >
      <template #modal-header  >
        <div></div>
        <h1>
          <b-iconstack  animation="">
            <b-icon stacked icon="shield" font-scale="3" animation="fade"></b-icon>
            <b-icon stacked icon="people" animation=""></b-icon>
          </b-iconstack>  
          <font size="6">
             보호
          </font>
        </h1>
        <div></div>
      </template> 
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
            class="select_list"
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
      <template #modal-footer>
        <div class="w-100 d-block text-center" >
          <b-button
            variant="primary"
            size="sm"
            class="skill_button_police my-3"
            :disabled='numberOfSkillUsed < 1'
            @click="protect"
          >
            PROTECT!(사용가능 : {{numberOfSkillUsed}})
          </b-button>
        </div>
      </template>
    </b-modal>
    <!-- 5.방송인 -->
    <b-modal v-model="show" v-if='myJob == "BROADCASTER"'
      id='skill' 
      size="lg"
      variant='outline-primary' 
      header-bg-variant="black"
      header-text-variant="primary"
      body-bg-variant="black"
      body-text-variant="light"
      footer-bg-variant="black"
      footer-text-variant="light"
      centered
      >
      <template #modal-header  >
        <div></div>
        <h1>
          <font size="7">
            <b-icon icon="mic" animation="fade"></b-icon>
             방송 
          </font>
        </h1>
        <div></div>
      </template> 
      <div variant="black" class="d-block text-center">
        <h5>
          <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
          <h4 class="link-warning">
            당신은 익명의 권리를 행사할 수 있습니다<br>
            단, 그 권리가 당신에게 득이 될지 실이 될지 아무도 모릅니다.<br>
            신중한 선택을 하시기 바랍니다.
          </h4>
          <textarea
            type="text" 
            class="border rounded" 
            rows="6"
            style="background-color:transparent; width:500px; color:white;" 
            placeholder="방송할 멘트를 입력하세요"
            v-model="broadcastMessage"
          >
          </textarea>
        </h5>
      </div>
      <template #modal-footer>
        <div class="w-100 d-block text-center" >
          <b-button
            variant="primary"
            size="sm"
            class="skill_button_police my-3"
            :disabled='numberOfSkillUsed < 1'
            @click="broadcast"
          >
            BROADCAST!(사용가능 : {{numberOfSkillUsed}})
          </b-button>
        </div>
      </template>
    </b-modal>
 
    <!-- 6.경찰 -->
    <b-modal v-model="show" v-if='myJob == "POLICE"'
      id='skill' 
      size="lg"
      variant='outline-primary' 
      header-bg-variant="black"
      header-text-variant="primary"
      body-bg-variant="black"
      body-text-variant="light"
      footer-bg-variant="black"
      footer-text-variant="light"
      centered
      >
      <template #modal-header  >
        <div></div>
        <!-- 아래 이미지 없음 -->
        <!-- <img src="../../assets/img/scull.jpg" alt="scull"> -->
        <h1 ><font size="7">검거 </font></h1>
        <div></div>
      </template> 
      <div variant="black" class="d-block text-center">
        <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
        <h4 class="link-warning">
          당신은 누군가를 검거할 수 있는 힘이 있습니다.<br>
          단, 검거 대상이 경찰측인 경우 당신은 해고당합니다.(사망처리)<br>
          신중한 선택을 하시기 바랍니다.
          </h4>
        <h5>
          <select
            v-model="selectParticipant"
            class="select_list"
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
      <template #modal-footer>
        <div class="w-100 d-block text-center" >
          <b-button
            variant="primary"
            size="sm"
            class="skill_button_police my-3"
            :disabled="numberOfSkillUsed < 2"
            @click="arrest"
          >
            ARREST!(사용가능 : {{Math.floor(numberOfSkillUsed/2)}})
          </b-button>
        </div>
      </template>
    </b-modal>
  </div>
</template>
<script src="https://kit.fontawesome.com/ac38071ee5.js" crossorigin="anonymous"></script>
<script>
import {mapState, mapActions} from 'vuex'

const gameStore = 'gameStore'

export default {
  name: 'ActiveSkill',
  data(){
    return {
      show: false,
      selectParticipant: '참가자 목록',
      selectJobName: '직업',
      broadcastMessage: '',
    }
  },
  computed: {
    ...mapState(gameStore, ['myJob', 'jobs', 'session', 'participants','missionSuccess','numberOfSkillUsed','isAlive'])
  },
  methods: {
    ...mapActions(gameStore, ['numberOfSkillUse']),

    noteWrite () {
      if(this.numberOfSkillUsed>0 && this.isAlive == true){
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
        this.numberOfSkillUse(-1)
      }
    },
    noteUse () {
      if(this.isAlive == true) {
        this.show = false
        this.session.signal({
          type: 'game',
          data: {
            gameStatus: 5,
            skillType: 'noteUse',
          },
          to: [],
        })
      }
    },
    broadcast () {
      if(this.numberOfSkillUsed>0 && this.isAlive == true){
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
        this.numberOfSkillUse(-1)
      }
    },
    arrest () {
      if(this.numberOfSkillUsed>0 && this.isAlive == true){
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
        this.numberOfSkillUse(-2)
      }
    },
    protect () {
      if(this.numberOfSkillUsed>0 && this.isAlive == true){
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
        this.numberOfSkillUse(-1)
      }
    },
    kill () {
      if(this.numberOfSkillUsed>0 && this.isAlive == true){
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
        this.numberodSkillUse(-1)
      }
    }
  },
}
</script>
 
<style scoped>
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