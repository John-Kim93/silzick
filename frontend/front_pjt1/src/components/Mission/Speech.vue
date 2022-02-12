
<template>
  <div>
    <!-- <slot name="input" :text="text"></slot> -->
    
    <div v-if="ready">
      <button @click="recordReset">{{record?'Stop':'Start'}}</button>
      <p>남은시간 : {{ timerCount }}</p>
      <p>{{ importmation }}</p>
      <p>{{cnt}}회 / 2회</p>
    </div>
    <div v-else>
      출력중
      <div v-if="success"> 
        <p class="text-success">미션 성공</p>
        <b-icon icon="check2-circle" font-scale="2.5" variant="success"></b-icon>
      </div>
      <div v-if="!success"> 
        <p class="text-danger">미션 실패</p>
        <b-icon icon="exclamation-circle" font-scale="2.5" variant="danger"></b-icon>
        </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable no-use-before-define */
/* eslint-disable no-undef */
import { mapState, mapActions } from 'vuex'

const mission = 'mission';

export default {
  props: {
    lang: {
      type: String,
      default: 'ko-KR'
    },
    value: {
      type: Boolean
    },
    confidenceLimit: {
      type: [Number, String],
      default: 0.8
    },
    continuous: {
      type: Boolean,
      default: true
    },
    interimResults: {
      type: Boolean
    },
    maxAlternatives: {
      type: [Number, String],
      default: 1
    },
    grammar: {
      type: String,
      default: null
    }
  },
  data () {
    return {
      text: '',
      recognition: null,
      signal: false,
      textdata: '',
      timerCount: 15,
      mission_list:['사과', '바나나', '파인애플', '복숭아', '경찰', '노트', '키라', '명함', '거짓말', '가짜', '직업', '미션', '화면', '컴퓨터', '버그'],
      s_mission: '',
      importmation: '',
      cnt : 0,
      success: true,
      ready:true
    }
  },
  watch: {
    // 타이머의 시간이 다 되면 종료
    timerCount: {
      handler(value) {
        if(this.cnt>=2){
            console.log("4")
            console.log(this.success)
            this.recognition.stop()
            this.success = true
            console.log("5")
            console.log(this.success)
            this.ready = false
            setTimeout(() => {
              this.missionReset()
              this.recordReset()
            }, 2000);
        }else{
          if (value > 0) {
            console.log("3")
            setTimeout(() => {
              this.timerCount--;
            }, 1000) 
          }else {
            this.recognition.stop()
            console.log("1")
            console.log(this.success)
            this.success = false
            this.ready = false
            setTimeout(() => {
              this.missionReset()
              this.recordReset()
              console.log("2")
              console.log(this.success)
            }, 1000);     
          }
        }
      },    
    },
    record (record) {
      if (this.recognition) {
        // 버튼이 눌리면 타이머의 시간을 맞추고 음성감지 시작
        if(record) {
          this.timerCount = 15
          this.recognition.start()
          console.log('start')
          this.timerCount -= 1
        }else {
          console.log('end')
          this.timerCount = 0
        }
      }
    },
  },
  computed: {
    ...mapState(mission, ['mission','record']),
  },
  created () {
    this.initRecognition()
    this.getmission()
  },
  methods: {
    ...mapActions(mission, ['missionReset','recordReset']),

    getmission () {
      this.s_mission = this.mission_list[Math.floor(Math.random()*this.mission_list.length)];
      this.importmation = `15초 안에 <${this.s_mission}>을(를) 2번 말하시오`
    },
    initRecognition () {
      const SpeechRecognition = SpeechRecognition || webkitSpeechRecognition
      const SpeechGrammarList = SpeechGrammarList || webkitSpeechGrammarList
      const SpeechRecognitionEvent = SpeechRecognitionEvent || webkitSpeechRecognitionEvent

      this.recognition = new SpeechRecognition()
      const speechRecognitionList = new SpeechGrammarList()
      if (this.grammar) {
        speechRecognitionList.addFromString(this.grammar, 1)
      }
      this.recognition.grammars = speechRecognitionList

      this.recognition.lang = this.lang
      this.recognition.continuous = this.continuous
      this.recognition.interimResults = this.interimResults
      this.recognition.maxAlternatives = this.maxAlternatives

      this.recognition.onresult = (event) => {
        // const transcript = event.results[0].map(a => a.transcript)
        const textArr = Array.from(event.results).filter(srr => {
          return srr[0].confidence >= this.confidenceLimit
        }).map(srr => {
          return srr[0].transcript
        })
        this.textdata = textArr.join('\n')
        if (this.textdata.search(this.s_mission) != -1) {
          this.cnt = this.textdata.split(this.s_mission).length-1
        }
      }
    },
  }
}

</script>
