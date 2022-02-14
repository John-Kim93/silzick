<template>
  <div>
    <!--채팅 내역 : chat_list-->
    <p
      v-for="message, idx in messages"
      :key="idx"
    >
      {{message}}
    </p>
    <!--채팅 입력 : chat_input-->
    <input
      class="chat_input"
      type="text"
      placeholder="메세지를 입력하세요"
      v-model="chatMessage"
      style="color:white;"
      @keyup.enter="enterMessage"
    >
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>