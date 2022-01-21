<template>
  <div>
    <div>
      <p>건의사항</p>
    </div>
    <div class="container">
      <table class="table table-dark table-hover">
        <thead>
          <tr>
            <td>번호</td>
            <td>작성자</td>
            <td>제목</td>
            <td>작성일자</td>
          </tr>
        </thead>
        <tbody>
          <tr :key="index" v-for="(post, index) in user_post" @click="detail(index)">
            <td>{{ index + 1 }}</td>
            <td>{{ post.user }}</td>
            <td>{{ post.title }}</td>
            <td>{{ post.created_at }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <button @click="create">글쓰기</button>
  </div>
  </template>

<script>
export default {
  name : 'Request',
  data : function() {
    return {
      isLogin: this.$store.state.isLogin,
      user_post : this.$store.state.user_post,
      user : this.$store.state.user,
    }
  },
  methods : {
    create : function () {
      this.$router.push('Request/Create')
    },
    detail : function (index) {
      this.$router.push({
        name: 'RequestDetail',
        params: {
          id: index
        }
      })
    },
    
  },
  mounted: function () {
    if (this.isLogin === false) {
      this.$router.push({
        name: 'Main'
      })
    }
  }
}
</script>

<style>

</style>