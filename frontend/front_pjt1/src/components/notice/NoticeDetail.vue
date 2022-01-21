<template>
  <div>
    <p>NoticeDetail</p>
    <p>{{ admin_post.user }}</p>
    <p>{{ admin_post.created_at }}</p>
    <p>{{ admin_post.title }}</p>
    <p>{{ admin_post.content }}</p>
    <div>
      <router-link :to="{name:'NoticeUpdate', params:{id:index}}">
        <button>수정</button>
      </router-link>
      <button @click="deleteNotice">삭제</button>
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

</style>