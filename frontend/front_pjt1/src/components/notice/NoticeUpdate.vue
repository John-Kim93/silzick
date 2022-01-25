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
// import dayjs from 'dayjs'
import { mapState } from 'vuex';
export default {
  name: 'NoticeUpdate',
  data : function() {
    // const index = this.$route.params.id
    return {
      index : this.$route.params.id,
      // user : this.$store.state.user.user_id,
      title: null,
      content: null,
      // created_at: this.$store.state.admin_post[this.index].created_at,
    }
  },
  computed: {
    ...mapState(["admin_post", "user"]),
    post: function () {
      return this.admin_post.find(post => post.id === this.index)
    } 
  },
  created: function () {
    this.title = this.post.title;
    this.content = this.post.content;
  },
  methods: {
    updateNotice: function () {
      const noticeData = {
        id : this.index,
        user: this.user.user_id,
        title: this.title,
        content: this.content,
        created_at: this.post.created_at,
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