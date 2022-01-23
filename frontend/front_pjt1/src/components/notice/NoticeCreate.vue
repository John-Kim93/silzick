<template>
  <div>
    <img src="https://ifh.cc/g/7ruaO5.png" id="bg" alt="bgImg">
    <div class="container location" style="width:70%">
      <input
        style="background-color:black"
        class="form-control"
        type="text"
        v-model="title"
        placeholder="제목을 입력하세요."
      >
      <textarea
        style="background-color:black"
        class="form-control my-3 enter"
        type="text"
        v-model="content"
        cols="30" rows="10"
        placeholder="공지사항을 입력하세요."
      ></textarea>
      <div>
        <b-button
          @click="create"
          variant="outline-light"
          class="d-flex"
          style="margin-left:auto"
        >공지하기</b-button>
      </div>
    </div>
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
  .enter {
    white-space: pre-line;
  }
  /* .notes {
    background-attachment: local;
    background-image:
      linear-gradient(to right, white 10px, transparent 10px),
      linear-gradient(to left, white 10px, transparent 10px),
      repeating-linear-gradient(white, white 30px, #ccc 30px, #ccc 31px, white 31px);
    line-height: auto;
    padding: 8px 10px;
  } */
</style>