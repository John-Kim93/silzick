<template>
  <div>
    <img src="https://ifh.cc/g/7ruaO5.png" id="bg" alt="bgImg">
    <div class="container location" style="width:70%">
      <input
        type="text"
        v-model="title"
        style="background-color:black"
        class="my-title form-control"
      >
      <textarea
        style="background-color:black"
        class="my-content form-control my-3"
        type="text"
        v-model="content"
        cols="30" rows="10"
      ></textarea>
      <div>
        <b-button
          @click="updateNotice"
          variant="outline-light"
          class="d-flex"
          style="margin-left:auto"
        >수정</b-button>
      </div>
    </div>
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
  .location {
    position: fixed;
    top: 60%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 80%;
  }
  input[type=text] {
    border: solid black;
    color: white
  }
  input[type=text]:focus {
    border: solid white;
    color: white
  }
  textarea[type=text] {
    border: solid black;
    color: white;
  }
  textarea[type=text]:focus {
    border: solid white;
    color: white
  }
</style>