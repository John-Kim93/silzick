<template>
  <div>
    <img src="https://ifh.cc/g/g1KuX0.jpg" id="bg" alt="bgImg">
    <router-link to="/Login">
      <b-button class="btn-1st-position" variant="outline-light">로그인</b-button>
    </router-link>
    <router-link to="/Signup">
      <b-button class="btn-2nd-position" variant="outline-light">회원가입</b-button>
    </router-link>
  </div>
</template>

<script>
export default {
  name: 'Main',
  components: {
    
  }
}
</script>

<style scoped>
.btn-1st-position {
        position: fixed;
        top: 65%;
        left: 48%;
    }

.btn-2nd-position {
        position: fixed;
        top: 73%;
        left: 47.5%;
    }
</style>