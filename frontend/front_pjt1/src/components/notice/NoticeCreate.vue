<template>
  <div>
    <p>NoticeCreate</p>
      <div>
      <!-- 작성자 : <input type="text" v-model="user"> -->
      제목 : <input type="text" v-model="title">
      내용 : <textarea v-model="content" cols="30" rows="10"></textarea>
    </div>
    <button @click="create">작성</button>
  </div>
</template>

<script>
import dayjs from 'dayjs'
export default {
  name: 'NoticeCreate',
  data : function() {
    return {
      user : this.$store.state.user.user_id,
      title: null,
      content: null,
      created_at: dayjs().format("YY.MM.DD"),
    }
  },
  methods: {
    create: function () {
      const noticeData = {
        user: this.user,
        title: this.title,
        content: this.content,
        created_at: this.created_at,
      }
      this.$store.dispatch("createNotice", noticeData),
      this.$router.push({
        name: 'Notice',
      })
    },
  },
}
</script>

<style>

</style>