<template>
  <div class="parent">
    <!--채팅 내역 : chat_list-->
    <p
      v-for="message, idx in messages"
      :key="idx"
    >
      {{message}}
    </p>
    <!--채팅 입력 : chat_input-->
    <input
      id=""
      class="col-12 chat-input"
      type="text"
      placeholder="메세지를 입력하세요"
      v-model="chatMessage"
      style="color:#222831;"
      @keyup.enter="enterMessage"
    >
  </div>
</template>

<script>
export default {
  name: 'Chatting',
}
</script>

<style scoped>
input[type=text] {
  border: solid #30475E;
  color: #212121;
  border-radius: 8px;
}

</style>