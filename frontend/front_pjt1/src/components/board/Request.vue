<template>
  <div>
    <div id="nav">
      <router-link to="/Board/Notice">공지사항</router-link> |
      <router-link to="/Board/Request">건의사항</router-link>
    </div>
    <router-view/>
    <div>
      <p>건의사항</p>
    </div>
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
        <tr v-for="post in user_post" :key="post.id">
          <td>{{ post.id }}</td>
          <td>{{ post.author }}</td>
          <td>{{ post.title }}</td>
          <td>{{ post.created_at }}</td>
        </tr>
      </tbody>
    </table>
    <button @click="create">글쓰기</button>
  </div>
</template>

<script>
export default {
  name : 'Request',
  data : function() {
    return {
      user_post : this.$store.state.user_post
    }
  },
  methods : {
    create : function () {
      this.$router.push('Request/Create')
    },
  }
}
</script>

<style>

</style>