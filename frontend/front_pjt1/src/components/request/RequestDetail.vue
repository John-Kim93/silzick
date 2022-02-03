<template>
  <div>
    <img src="https://ifh.cc/g/7ruaO5.png" id="bg" alt="bgImg">
    <div>
      <h1 class="location2" style="color:white">REQUEST</h1>
    </div>
    <div class="location3">
      <b-button
        variant="outline-primary"
        class="d-inline mx-3"
        style="margin-left:auto"
        @click="moveToUpdate"
      >수정</b-button>
      <b-button
        variant="outline-danger"
        class="d-inline"
        style="margin-left:auto"
        @click="deleteRequest"
      >삭제</b-button>
    </div>
    <div class="container location" style="width:70%; color:white; text-align:left">
      <h5>제목 : {{ post.title }}</h5>
      <hr>
      <!-- <h5>작성자 : {{ post.userId }}</h5>
      <hr>
      <h5>작성일 : {{ post.createdAt.slice(0,10) }}</h5>
      <hr>
      <h5>수정일 : {{ post.modifiedAt.slice(0,10) }}</h5>
      <hr>
      <h5>조회수 : {{ post.hit }}</h5>
      <hr>
      <h5>내용</h5>
      <p class="enter">{{ post.content }}</p> -->
      <hr>
      <div class="d-flex justify-content-center location5">
        <b-button
          variant="outline-light"
          @click="moveToRequest"
        >목록으로</b-button>
      </div>
    </div>
  </div>
</template>

<script>
import { deleteRequest } from '@/api/board.js'
import { createNamespacedHelpers } from 'vuex'

const { mapState } = createNamespacedHelpers('board')

export default {
  name: 'RequestDetail',
  data () {
    return {
      index: this.$route.params.id,
    }
  },
  computed: {
    ...mapState(['user_post']),
    post () {
      return this.user_post.find(post => post.id == this.index)
    },
  },
  methods: {
    moveToUpdate () {
      this.$router.push({
        name:'RequestUpdate',
        params:{ id: this.index }
      })
    },
    deleteRequest () {
      deleteRequest(
        this.index,
        () => {
          alert('삭제 완료')
        },
        (err) => {
          console.log(err)
          alert('삭제 실패')
        }
      )
      this.$router.push({ name: 'Request' })
    },
    moveToRequest () {
      this.$router.push({ name: 'Request' })
    }
  }
}
</script>

<style scoped>
  .location2 {
    position: fixed;
    top: 20%;
    left: 16%
  }
  .location5 {
    position: fixed;
    top: 85%;
    left: 47%
  }
</style>