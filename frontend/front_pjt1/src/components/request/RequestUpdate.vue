<template>
  <div>
    <p>RequestUpdate</p>
    <div>
      <!-- 작성자 : <input type="text" v-model="user"> -->
      제목 : <input type="text" v-model="title">
      내용 : <textarea v-model="content" cols="30" rows="10"></textarea>
    </div>
    <button @click="updateRequest">수정</button>
  </div>
</template>

<script>
import dayjs from 'dayjs'
export default {
  name: 'RequestUpdate',
  data : function() {
    const index = this.$route.params.id
    return {
      // user_post: this.$store.state.user_post,
      // user_postData : {
      //   id : this.$route.params.id,
      //   title: null,
      //   content: null,
      //   updated_at : dayjs().format("YY.MM.DD"),
      // },
      // id : this.$route.params.id,
      id: index,
      user : this.$store.state.user.user_id,
      title: this.$store.state.user_post[index].title,
      content: this.$store.state.user_post[index].content,
      created_at: dayjs().format("YY.MM.DD"),
    }
  },
  methods: {
    updateRequest: function () {
      // console.log(this.id)
      const requestData = {
        user: this.user,
        title: this.title,
        content: this.content,
        created_at: this.created_at,
        index : this.id,
      }
      this.$store.dispatch("updateRequest", requestData),
      this.$router.push({
        name: 'RequestDetail',
      })
    },
  },
}
</script>

<style>

</style>