<template>
  <div>
    <p>RequestCreate</p>
    <div>
      작성자 : <input type="text" v-model="author">
      제목 : <input type="text" v-model="title">
      내용 : <textarea v-model="content" cols="30" rows="10"></textarea>
    </div>
    <button @click="create">작성</button>
  </div>
</template>

<script>
export default {
  name: 'RequestCreate',
  data: function () {
    return {
      author: null,
      title: null,
      content: null,
      created_at: new Date
    }
  },
  methods: {
    create: function () {
      this.data.push({
        author: this.author,
        title : this.title,
        content: this.content,

      })
    }
  }
}
</script>

<style>

</style>