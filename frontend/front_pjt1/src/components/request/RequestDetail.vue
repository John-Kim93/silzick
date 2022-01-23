<template>
  <div>
    <img src="https://ifh.cc/g/7ruaO5.png" id="bg" alt="bgImg">
    <div>
      <h1 class="location2" style="color:white">REQUEST</h1>
    </div>
    <div class="location3">
      <router-link style="text-decoration:none" :to="{name:'RequestUpdate', params:{id:index}}">
        <b-button variant="outline-primary" class="d-inline mx-3" style="margin-left:auto">수정</b-button>
      </router-link>
      <b-button @click="deleteRequest" variant="outline-danger" class="d-inline" style="margin-left:auto">삭제</b-button>
    </div>
    <div class="container location" style="width:100%; color:white; text-align:left">
      <!-- <hr>
      <h5>{{ user_post.user }}</h5> -->
      <hr>
      <h5>{{ user_post.created_at }}</h5>
      <hr>
      <h5>{{ user_post.title }}</h5>
      <hr>
      <p class="enter">{{ user_post.content }}</p>
      <hr>
      <div class="d-flex justify-content-center">
        <router-link :to="{name:'Request'}">
          <b-button variant="outline-light">목록으로</b-button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RequestDetail',
  data: function () {
    const index = this.$route.params.id
    const user_posts = this.$store.state.user_post
    return {
      user_post : user_posts[index],
      index: index,
    }
  },
  methods: {
    deleteRequest: function () {
      this.$store.dispatch('deleteRequest', this.index)
      this.$router.push({
        name: 'Request',
      })
    },
  }
}
</script>

<style>
  .location2 {
    position: fixed;
    top: 20%;
    left: 16%
  }
  /* .location3 {
    position: fixed;
    top: 20%;
    right: 16%
  } */
</style>