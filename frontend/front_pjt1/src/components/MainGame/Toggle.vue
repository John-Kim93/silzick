<template>
  <div>
    <button id="btn-color" class="btn" style="background-color:transparent; border:solid 0.2rem #30475E;font-size:1.5rem;">◀</button>
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
      <div class="px-3 py-2 d-flex justify-content-center">
          <div v-if="toggle">
          <button @click="skill">스킬창으로!</button>
            <h1>MISSION</h1>
            <mission></mission>
          </div>
          <div class="wide" v-else>
          <button @click="skill">미션창으로!</button>
            <h1>SKILL</h1>
            <skill/>
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
      toggle : true,
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