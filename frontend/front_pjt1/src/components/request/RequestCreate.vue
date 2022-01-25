<template>
  <div>
    <img src="https://ifh.cc/g/7ruaO5.png" id="bg" alt="bgImg">
    <div>
      <b-button
        @click="create"
        variant="outline-light"
        class="d-flex location3"
        style="margin-left:auto"
      >건의하기
      </b-button>
    </div>
    <div class="container location" style="width:70%">
      <input
        style="background-color:black"
        class="form-control"
        type="number"
        v-model="id"
        placeholder="id를 입력하세요">
      <input
        style="background-color:black"
        class="form-control"
        type="text"
        v-model="title"
        placeholder="제목을 입력하세요."
      >
      <textarea
        style="background-color:black"
        class="form-control my-3"
        type="text"
        v-model="content"
        cols="30" rows="10"
        placeholder="건의사항을 입력하세요."
      ></textarea>
    </div>
  </div>
</template>

<script>
import dayjs from 'dayjs'
export default {
  name: 'RequestCreate',
  data : function() {
    return {
      id : null,
      user : this.$store.state.user.user_id,
      title: null,
      content: null,
      created_at: dayjs().format("YY.MM.DD"),
    }
  },
  methods: {
    create: function () {
      const requestData = {
        id : this.id,
        user: this.user,
        title: this.title,
        content: this.content,
        created_at: this.created_at,
      }
      this.$store.dispatch("createRequest", requestData),
      this.$router.push({
        name: 'Request',
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