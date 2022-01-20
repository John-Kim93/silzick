<template>
  <div>
    <p>RequestDetail</p>
    <p>{{ user_post.user }}</p>
    <p>{{ user_post.created_at }}</p>
    <p>{{ user_post.title }}</p>
    <p>{{ user_post.content }}</p>
    <div>
      <router-link :to="{name:'RequestUpdate', params:{id:index}}">
        <button>수정</button>
      </router-link>
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