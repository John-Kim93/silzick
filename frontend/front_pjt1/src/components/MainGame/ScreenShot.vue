<template>
  <div class="image-wrapper w-100 h-100">
    <img id="frame" class="w-100 h-100" src="@/assets/img/이모지/영정.png"  alt="액자">
    <canvas id='dead' class="w-100 h-100" >
    </canvas>
  </div>
</template>
<script>
import {mapActions, mapState } from 'vuex'

const gameStore = 'gameStore'
export default {
  data(){
    return {
      pic_string:''
    }
  },
  props:{
    idx:Number
  },
  computed:{
    ...mapState(gameStore,['pic_list','box_width','box_height']),
  },
  mounted(){
    this.drawImg(this.pic_list[this.idx],this.box_width,this.box_height)
    this.saveAs(this.pic_list[this.idx],'filename.jpg')
  },
  methods: {
    ...mapActions(gameStore, ['screenShot',]),
    drawImg(imgData,width,height) {
      console.log(this.pic_list);
      console.log(this.box_height);
      console.log(this.box_width); 
      return new Promise(function reslove() {
      console.log(document.body)
      const canvas = document.querySelector('#dead');
      console.log(canvas)
      const ctx = canvas.getContext('2d');
      console.log(ctx)
      canvas.width = width
      canvas.height = height 
      // ctx.clearRect(0, 0, 300,300);
      ctx.clearRect(0, 0, width,height);
      const imageObj = new Image();
      imageObj.onload = function () {
        ctx.drawImage(imageObj, 0, 0,width,height);
        // ctx.drawImage(imageObj, 0, 0,300,300);
        ctx.globalAlpha = 0.5
        ctx.drawImage(document.getElementById('frame'),0,0,width,height)
        // ctx.drawImage(document.getElementById('frame'),0,0,300,300)  
        };
      imageObj.src = imgData;
      console.log('완료')   
      });
    },
    saveAs(uri, filename) {
      var link = document.createElement('a');
      if (typeof link.download === 'string') {
        link.href = uri; link.download = filename;
        document.body.appendChild(link); 
        link.click();
        document.body.removeChild(link); 
      }else { window.open(uri); 
      }
    },
  }
}
</script>
<style scoped>
.image-wrapper {
  background: transparent;
}
.image-wrapper img {
  display: none;
}
.image-wrapper canvas {
  mix-blend-mode: luminosity;
}
</style>