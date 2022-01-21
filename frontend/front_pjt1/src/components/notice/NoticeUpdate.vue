<template>
  <div>
    <p>NoticeUpdate</p>
    <div>
      제목 : <input type="text" v-model="title">
      내용 : <textarea v-model="content" cols="30" rows="10"></textarea>
    </div>
    <button @click="updateNotice">수정</button>
  </div>
</template>

<script>
import dayjs from 'dayjs'
export default {
  name: 'NoticeUpdate',
  data : function() {
    const index = this.$route.params.id
    return {
      id: index,
      user : this.$store.state.user.user_id,
      title: this.$store.state.admin_post[index].title,
      content: this.$store.state.admin_post[index].content,
      created_at: dayjs().format("YY.MM.DD"),
    }
  },
  methods: {
    updateNotice: function () {
      // console.log(this.id)
      const noticeData = {
        user: this.user,
        title: this.title,
        content: this.content,
        created_at: this.created_at,
        index : this.id,
      }
      this.$store.dispatch("updateNotice", noticeData),
      this.$router.push({
        name: 'NoticeDetail',
      })
    },
  },
}
</script>

<style>

</style>