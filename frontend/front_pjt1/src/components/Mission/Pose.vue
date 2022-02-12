<template>
  <div>
    <h1>미션</h1>
    <!-- canvas는 안보이더라도 존재해야 나머지가 작동됨 -->
    <div v-show="false"><canvas id="canvas"></canvas></div>
    <!-- 미션이 완료되는동안 확률이 계속 표시되서 게이지가 다 차면 안보이게 수정 -->
    <div v-show="loading == true"><p>{{impormation}} <br> {{similiarity}}</p></div>
    <!-- 미션게이지와 미션완료시 아이콘 표시 -->
    <div v-if="loadingTime!=maxLoadingTime" class="d-flex justify-content-center mb-3">
      <b-progress class="w-75" :max="maxLoadingTime" height="1.5rem">
        <b-progress-bar :value="loadingTime" :label="`${((loadingTime / maxLoadingTime) * 100).toFixed(2)}%`"></b-progress-bar>
      </b-progress>
    </div>
    <div v-else> <b-icon icon="check2-circle" font-scale="2.5" variant="success"></b-icon></div>
  
  </div>
</template>
<script>
// import * as tf from '@tensorflow/tfjs'
import * as tmPose from '@teachablemachine/pose'
import { mapState, mapActions } from 'vuex'

const gameStore = 'gameStore'
 
let model, webcam, maxPredictions
export default {
  data () {
    return {
      loading: true,
      loadingTime: 0,
      maxLoadingTime: 300,

      url : "https://teachablemachine.withgoogle.com/models/Zcd4DPpuu/",
      modelURL: '',
      metadataURL: '',

      impormation: '',
      similiarity: '',
    }
  },
  computed: {
    ...mapState(gameStore, ['mission','random_int','isNormalMission']),
  },
  created(){
    this.randomInt({min:1,max:4})
    this.init()
    
  },
  methods: {
    ...mapActions(gameStore, ['missionReset','randomInt','missionSuccess','hiddenMissionSuccess']),

    async init () {
      this.modelURL = this.url + "model.json"
      this.metadataURL = this.url + "metadata.json"
      model = await tmPose.load(this.modelURL, this.metadataURL)
      maxPredictions = model.getTotalClasses()
      const size = 200
      const flip = true
      webcam = new tmPose.Webcam(size, size, flip)
      // webcam = new tmImage.Webcam(width, height, flip)
      await webcam.setup()
      await webcam.play()
      // document.getElementById("webcam-container").appendChild(webcam.canvas)
      window.requestAnimationFrame(this.loop)

      const canvas = document.getElementById("canvas")
      canvas.width = size
      canvas.height = size
      // ctx = canvas.getContext("2d")
    },
    async loop() {
      webcam.update()
      await this.predict()
      window.requestAnimationFrame(this.loop)
    },
    
    // drawPose(pose) {
    //     if (webcam.canvas) {
    //         ctx.drawImage(webcam.canvas, 0, 0)
    //         pose
 
    //     }
    // },
    async predict() {
      const { posenetOutput } = await model.estimatePose(webcam.canvas)
      const prediction = await model.predict(posenetOutput)
      this.impormation = prediction[this.random_int].className
      this.similiarity = '정확도 :' + parseInt(prediction[this.random_int].probability.toFixed(2) *100) + '%'
      for (let i = 0; i < maxPredictions; i++) {
        if (prediction[this.random_int].probability.toFixed(2) > 0.8) {
          if( this.loadingTime < this.maxLoadingTime) {
            this.loadingTime++
          }else {
            //loading진행바가 true면 끝남.
            if (this.loading == true){
              this.loading = false
              this.impormation=''
              this.similiarity = ''
              webcam.stop()
              //성공한게 노멀미션이면 명교횟수 +1
              if(this.isNormalMission){
                this.missionSuccess()
                //히든미션이면 스킬포인트 +1
              }else{
                this.hiddenMissionSuccess()
              }
              //성공 후 리셋
              setTimeout(()=>{
                this.loadingTime = 0
                this.missionReset()
              },2000)
            }
          }
        }
      }

        // finally draw the poses
      // this.drawPose(pose)
    },
  }
}
</script>

<style>

</style>