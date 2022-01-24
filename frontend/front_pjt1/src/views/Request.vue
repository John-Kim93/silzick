<template>
  <div>
    <img src="https://ifh.cc/g/7ruaO5.png" id="bg" alt="bgImg">
    <div class="d-flex location2">
      <b-button @click="moveToNotice" variant="outline-light">공지사항</b-button>
      <b-button class="mx-3" @click="moveToRequest" variant="outline-light">건의사항</b-button>
    </div>
    <div class="location3">
      <b-button @click="create" variant="outline-light">건의하기</b-button>
    </div>
    <div class="container location">
      <table class="table table-hover my-3">
        <thead class="white">
          <tr>
            <td>번호</td>
            <td>작성자</td>
            <td>제목</td>
            <td>작성일자</td>
          </tr>
        </thead>
        <tbody class="white">
          <tr :key="index" v-for="(post, index) in user_post" @click="detail(index)">
            <td>{{ index + 1 }}</td>
            <td>{{ post.user }}</td>
            <td>{{ post.title }}</td>
            <td>{{ post.created_at }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="btn-cover location4 d-flex">
      <b-button :disabled="pageNum === 0" @click="prevPage" class="page-btn btn-sm" variant="outline-light">
        이전
      </b-button>
      <span class="page-count white mx-3 mt-1">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
      <b-button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn btn-sm" variant="outline-light">
        다음
      </b-button>
    </div>
  </div>
</template>

<script>
export default {
  name : 'Request',
  data : function() {
    return {
      pageNum: 0,
      pageSize: 10,
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
    moveToNotice : function () {
      this.$router.push({
        name: 'Notice'
      })
    },
    moveToRequest : function () {
      this.$router.push({
        name: 'Request'
      })
    }
  },
  created: function () {
    if (this.isLogin === false) {
      this.$router.push({
        name: 'Main'
      })
    }
  }
}
</script>

<style scoped>
  .location {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 70%;
  }
  .table-hover thead tr:hover th, .table-hover tbody tr:hover td {
    background-color: white;
  }
  .white {
    color: white;
  }
  .location2 {
    position: fixed;
    top: 20%;
    left: 16%
  }
</style>