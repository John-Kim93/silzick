<template>
  <div>
    <div>
      <p>공지사항</p>
    </div>
    <div>
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
          <tr v-for="post in admin_post" :key="post.id">
            <td>{{ post.id }}</td>
            <td>{{ post.author }}</td>
            <td>{{ post.title }}</td>
            <td>{{ post.created_at }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name : 'Notice',
  data : function() {
    return {
      admin_post : this.$store.state.admin_post
    }
  }
}
</script>

<style>

</style>