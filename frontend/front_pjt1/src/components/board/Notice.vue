<template>
  <div>
    <div>
      <p>공지사항</p>
    </div>
    <table class="table table-dark table-hover">
      <thead>
        <tr>
          <td>번호 : {{ admin_post.id }}</td>
          <td>제목 : {{ admin_post.title }}</td>
          <td>작성일자 : {{ admin_post.created_at }}</td>
        </tr>
      </thead>
      <tbody>

      </tbody>
    </table>
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