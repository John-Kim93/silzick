
const mission = {
  namespaced: true,
  
  state: {
    mission: 0,
    random_int: 0,
    record: false
    // loading: true,
    // loadingTime: 0,
    // maxLoadingTime: 300,
    // min: 1,
    // max: 5,

    // url : "https://teachablemachine.withgoogle.com/models/Zcd4DPpuu/",
    // modelURL: '',
    // metadataURL: '',

    // impormation: '',
    // similiarity: '',

    // model: undefined, 
    // webcam: undefined, 
    // ctx: undefined, 
    // labelContainer: undefined, 
    // maxPredictions: undefined,
  },
  mutations: {
    RANDOM_INT(state,res){
      const min = Math.ceil(res.min)
      const max = Math.floor(res.max)
      state.random_int = Math.floor(Math.random()*(max-min+1))+min
    },
    MISSION_SELECT(state){
      state.mission = state.random_int
      state.random_int = 0
    },
    MISSION_RESET(state){
      state.mission = 0
    },
    RECORD_RESET(state){
      state.record = !state.record
    }
  },
  actions: {
    randomInt({commit},res){
      commit('RANDOM_INT',res)
    },
    missionSelect({commit,dispatch},){
      dispatch('randomInt',{min:1,max:2})
      commit('MISSION_SELECT')
    },
    missionReset({commit}){
      commit('MISSION_RESET')
    },
    recordReset({commit}){
      commit('RECORD_RESET')
    }

  },
//   async init ({state,commit,dispatch}) {
//     commit('SET_MODEL_URL')
//     commit('SET_METADATA_URL')
//     state.modelURL = state.url + "model.json"
//     state.metadataURL = state.url + "metadata.json"
//     state.model = await tmPose.load(state.modelURL, state.metadataURL);
//     maxPredictions = model.getTotalClasses();
//     console.log(maxPredictions)
//     // Convenience function to setup a webcam
//     const size = 200
//     const flip = true; // whether to flip the webcam
//     webcam = new tmPose.Webcam(size, size, flip); // width, height, flip
//     // webcam = new tmImage.Webcam(width, height, flip)
//     await webcam.setup(); // request access to the webcam
//     await webcam.play();
//     // document.getElementById("webcam-container").appendChild(webcam.canvas);
//     window.requestAnimationFrame(this.loop);

//     // append/get elements to the DOM

//     const canvas = document.getElementById("canvas");
//     canvas.width = size
//     canvas.height = size
//     ctx = canvas.getContext("2d");
//     labelContainer = document.getElementById("label-container");
//     for (let i = 0; i < maxPredictions; i++) { // and class labels
//         labelContainer.appendChild(document.createElement("div"));
//     }
//   },
//   async loop() {
//     webcam.update();
//     await this.predict()
//     window.requestAnimationFrame(this.loop)
//   },
  
//   drawPose(pose) {
//       if (webcam.canvas) {
//           ctx.drawImage(webcam.canvas, 0, 0);
//           pose

//       }
//   },
//   async predict() {
//     const { pose, posenetOutput } = await model.estimatePose(webcam.canvas);
//     const prediction = await model.predict(posenetOutput);
//     this.impormation = prediction[this.mission].className
//     this.similiarity = '정확도 :' + parseInt(prediction[this.mission].probability.toFixed(2) *100) + '%';
//     for (let i = 0; i < maxPredictions; i++) {
//         const classPrediction =
//             prediction[i].className + ": " + prediction[i].probability.toFixed(2);
//         if(this.mission < 6) {
//           if (prediction[this.mission].probability.toFixed(2) > 0.8) {
//             if( this.loadingTime != this.maxLoadingTime) {
//               this.loadingTime++
              
//             }else {
//             this.loading = false
//             this.mission = ''
//             this.impormation=''
//             this.similiarity = ''
//             setTimeout(()=>{
//               this.loadingTime = 0
//               dispatch('convertBtn')
//               this.webcam.stop()
//             },2000)
            
//             }
//           }
//         }
//         labelContainer.childNodes[i].innerHTML = classPrediction;
//     }

//       // finally draw the poses
//     this.drawPose(pose);
//   },
// }
// }
  
}
export default mission;