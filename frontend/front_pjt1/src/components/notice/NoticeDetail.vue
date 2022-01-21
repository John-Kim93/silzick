<template>
  <div>
    <img src="https://ifh.cc/g/7ruaO5.png" id="bg" alt="bgImg">
    <div class="container location" style="width:70%">
      <p class="line">{{ admin_post.user }}</p>
      <p>{{ admin_post.created_at }}</p>
      <p>{{ admin_post.title }}</p>
      <p>{{ admin_post.content }}</p>
    <div>
      <router-link :to="{name:'NoticeUpdate', params:{id:index}}">
        <b-button variant="outline-light" class="d-inline" style="margin-left:auto">수정</b-button>
      </router-link>
      <b-button @click="deleteNotice" variant="outline-light" class="d-inline" style="margin-left:auto">삭제</b-button>
    </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NoticeDetail',
  data: function () {
    const index = this.$route.params.id
    const admin_posts = this.$store.state.admin_post
    return {
      admin_post : admin_posts[index],
      index: index,
    }
  },
  methods: {
    deleteNotice: function () {
      this.$store.dispatch('deleteNotice', this.index)
      this.$router.push({
        name: 'Notice',
      })
    },
  }
}
</script>

<style>
  .location {
    position: fixed;
    top: 60%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 80%;
  }
  .line {
    border-bottom-color: white;

  }
</style>