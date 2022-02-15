<template>
  <div id="app">
    <div
      v-if="isLogin && !join"
      class="d-flex mx-3 py-3 fix"
    >
      <button class="btn" style="background-color:black;">
        <router-link to="/">
          <b-icon icon="house" font-scale="2.5" variant="light"></b-icon>
        </router-link>
      </button>
      <button
        class="btn"
        style="background-color:black;"
      >
        <router-link to="/Request">
          <b-icon
            icon="card-text"
            font-scale="2.7"
            variant="light"
          >
          </b-icon>
        </router-link>
      </button>
      <button
        class="btn"
        style="background-color:black;"
      > 
        <router-link to="/Rule">
          <b-icon
              icon="question-circle"
              font-scale="2.5"
              variant="light"
            >
            </b-icon>
        </router-link>
      </button>
    </div>
    <router-view/>
  </div>
</template>

<script>
import cookies from 'vue-cookies'
import { mapState } from 'vuex'


const gameStore = 'gameStore'

export default {
  name: 'APP',
  data () {
    return {
      isLogin : false,
    }
  },
  computed: {
    ...mapState(gameStore, ['join'])
  },
  updated () {
    this.isLogin = cookies.isKey("JWT-AUTHENTICATION")
  },
  created () {
    this.isLogin = cookies.isKey("JWT-AUTHENTICATION")
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100vh;
  /* background-color: rgb(26, 26, 26);
  z-index: -2; */
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #ffffff;
}

.fix {
  position: fixed;
}
</style>
