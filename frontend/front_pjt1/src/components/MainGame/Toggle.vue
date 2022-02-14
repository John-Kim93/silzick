<template>
  <div>
    <button
      id="btn-color"
      class="btn"
      style="background-color:transparent; border:solid 0.2rem #30475E;font-size:1.5rem;"
      v-b-toggle.sidebar-backdrop
    >◀</button>
    <b-sidebar
      id="sidebar-backdrop"
      title="Sidebar with backdrop"
      :backdrop-variant="variant"
      backdrop
      right
      width="43%"
      shadow
      no-header
    >
      <div id="background-black" class="pt-4 full-screen d-flex justify-content-center container ">
        <!-- <div class="wide d-flex row align-items-start" v-if="toggle">
        <button id="btn-color" class="select-btn offset-4 col-4 mt-4" style="height:8vh" @click="skill">Skill</button>
          <h1>Mission</h1>
          <mission></mission>
        </div> -->
        <div class="wide d-flex row align-content-start">
        <!-- <button id="btn-color" class="select-btn offset-4 col-4 my-4" style="height:8vh">Mission</button> -->
          <h1 class="my-2">SKILL</h1>
          <hr>
          <skill class="mt-3"/>
        </div>
      </div>
    </b-sidebar>
  </div>
</template>

<script>
import Skill from './Skill.vue'

export default {
  name: 'Toggle',
  components: {
    Skill,
  },
  data(){
    return {
      // toggle : false,
    }
  },
  methods: {
    skill(){
      this.toggle = !this.toggle
    }
  }
}
</script>

<style scoped>
.wide {
  width: 200%;
}
</style>