<template>
  <div>
    <p>RequestDetail</p>
    <p>{{ user_post.user }}</p>
    <p>{{ user_post.created_at }}</p>
    <p>{{ user_post.title }}</p>
    <p>{{ user_post.content }}</p>
    <div>
      <button>수정</button>
      <button @click="deleteRequest">삭제</button>
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
      // const data = {
      //   user_post : this.user_post,
      //   index: this.index
      // }
      this.$store.dispatch('deleteRequest', this.index)
      this.$router.push({
        name: 'Request',
      })
    },
  }
}
</script>

<style>

</style>