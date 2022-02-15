<template>
  <div id="dead-box" class="image-wrapper w-100 h-100" style="">
    <img id="frame" class="w-100 h-100" src="@/assets/img/이모지/영정.png" style="display:none" alt="액자">
    <canvas id='dead' >
    </canvas>
    <button @click="screenShot('local-video-str_CAM_O3UM_con_YeaxSnsiB1')">screenshot</button>
    <button @click="drawImg(pic)">convert</button>
  </div>
</template>
<script>
import {mapActions } from 'vuex'

const gameStore = 'gameStore'
export default {
  props:{
    pic:String
  },
  methods: {
    ...mapActions(gameStore, ['screenShot',]),
    drawImg(imgData) {
      console.log(imgData); 
      return new Promise(function reslove() {
      const canvas = document.getElementById('dead');
      const target = document.getElementById('dead-box')
      const ctx = canvas.getContext('2d'); 
      ctx.clearRect(0, 0, target.width, target.height);
      const imageObj = new Image();
      imageObj.onload = function () {
        ctx.drawImage(document.getElementById('frame'),0,0,target.width,target.height) 
        // ctx.globalAlpha = 0.5
        // // ctx.fillstyle ="black"
        // // ctx.fillRect(0, 0,canvas.width,canvas.height)
        // ctx.drawImage(imageObj, 0, 0,target.width,target.height);
        };
      imageObj.src = imgData;
      console.log('완료')   
      });
    }
  }
}
</script>
<style scoped>
/* .image-wrapper {
  background: white;
}
.image-wrapper img {
  display: none;
}
.image-wrapper canvas {
  mix-blend-mode: luminosity;
} */
</style>