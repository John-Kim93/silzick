<template>
  <div>
    <img src="https://ifh.cc/g/7ruaO5.png" id="bg" alt="bgImg">
    <div>
      <h1 class="location2" style="color:white">NOTICE</h1>
    </div>
    <div class="location3">
      <router-link style="text-decoration:none" :to="{name:'NoticeUpdate', params:{id:index}}">
        <b-button variant="outline-primary" class="d-inline mx-3" style="margin-left:auto">수정</b-button>
      </router-link>
      <b-button @click="deleteNotice" variant="outline-danger" class="d-inline" style="margin-left:auto">삭제</b-button>
    </div>
    <div class="container location" style="width:70%; color:white; text-align:left">
      <!-- <p>d : {{ post.id }}</p> -->
      <hr>
      <h5>{{ post.created_at }}</h5>
      <hr>
      <h5>{{ post.title }}</h5>
      <hr>
      <p class="enter">{{ post.content }}</p>
      <hr>
      <div class="d-flex justify-content-center location5">
        <router-link :to="{name:'Notice'}">
          <b-button variant="outline-light">목록으로</b-button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'NoticeDetail',
  // data: function () {
  //   const index = this.$route.params.id
  //   const admin_posts = this.$store.state.admin_post
  //   return {
  //     admin_post : admin_posts[index],
  //     index: index,
  //   }
  // },
  data: function () {
    return {
      index: this.$route.params.id
    }
  },
  computed: {
    ...mapState(["admin_post"]),
    post : function () {
      // return this.admin_post[this.index]
      // return this.admin_post.keys(post).find(id => post[id]==this.index)
      return this.admin_post.find(post => post.id === this.index)
    }
  },
  created: function () {
      // console.log(this.index)
    },
  methods: {
    deleteNotice: function () {
      // console.log(this.index)
      this.$store.dispatch('deleteNotice', this.index)
      this.$router.push({
        name: 'Notice',
      })
    },
  }
}
</script>

<style>
  .location {
    position: fixed;
    top: 60%;
    left: 50%;
    /* transform: translate(-50%, -50%); */
    width: 80%;
  }
  .line {
    border-bottom-color: white;
  }
  .location2 {
    position: fixed;
    top: 20%;
    left: 16%
  }
  .location3 {
    position: fixed;
    top: 20%;
    right: 16%
  }
</style>