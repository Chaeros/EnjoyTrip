<template>
  <div class="chat-item" @click="selectChat">
    <div class="chat-avatar">
      <img :src="friend.avatar" alt="Avatar" />
    </div>
    <div class="chat-details">
      <div class="chat-name">{{ friend.nickname }}</div>
      <div class="chat-last-message">{{ friend.lastMessage }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChatItem",
  props: {
    friend: {
      type: Object,
      required: true,
    },
  },
  methods: {
    selectChat() {
      this.$emit("select-friend", this.friend);
    },
  },
};
</script>

<style scoped>
.chat-item {
  display: flex;
  align-items: center;
  padding: 10px;
  cursor: pointer;
  border-bottom: 1px solid #ccc;
}
.chat-item:hover {
  background-color: #e9e9e9;
}
.chat-avatar img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.chat-details {
  margin-left: 10px;
}
.chat-name {
  font-weight: bold;
}
.chat-last-message {
  font-size: 0.9em;
  color: #777;
}
</style>
