<template>
  <div>
    <p>BOARD</p>
    <div id="nav">
      <router-link to="/Board/Notice">공지사항</router-link> |
      <router-link to="/Board/Request">건의사항</router-link>
    </div>
    <router-view/>
    <!-- <div>
      <notice></notice>
      <request></request>
    </div> -->
  </div>
</template>

<script>
// import Notice from '../components/board/Notice.vue'
// import Request from '../components/board/Request.vue'

export default {
  name : 'Board',
  components : {
    // Notice,
    // Request,
  }
}
</script>

<style>

</style>