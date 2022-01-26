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
          @click="updateRequest"
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

<style scoped>
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