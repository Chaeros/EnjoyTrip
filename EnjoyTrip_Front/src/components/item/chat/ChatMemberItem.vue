<template>
  <div class="chat-item" @click="selectFriend">
    <div class="chat-item-left">
      <div class="chat-avatar">
        <!-- <img :src="friend.avatar" alt="Avatar" /> -->
        <template v-if="friend.image == null">
          <img src="@/img/member/default_img.jpg" />
        </template>
        <template v-else>
          <img :src="friend.image" alt="Profile" />
        </template>
      </div>
      <div class="chat-details">
        <div class="chat-name">{{ friend.nickname }}</div>
        <!-- <div class="chat-last-message">{{ friend.lastMessage }}</div> -->
      </div>
    </div>
    <template v-if="unreadMessageCount !== 0">
      <div class="chat-item-right">{{ unreadMessageCount }}</div>
    </template>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import {
  registChatMessage,
  searchChatMessageList,
  enterOrRegistPrivateChatRoom,
  searchPrivateChatRoom,
} from "@/api/chat/chat.js";
import {
  countResetUnreadMessageCount,
  searchUnreadMessageCount,
} from "@/api/unreadmessagecount/unreadmessagecount.js";
import { ref } from "vue";
import { getLocalStorage } from "@/util/localstorage/localstorage";

const props = defineProps({
  friend: {
    type: Object,
    required: true,
  },
});

const unreadMessageCount = ref(0);

const emit = defineEmits(["select-friend"]);

const selectFriend = () => {
  emit("select-friend", props.friend);
};

console.log(props.friend.id);
console.log(getLocalStorage("userId"));
searchPrivateChatRoom(
  getLocalStorage("userId"),
  props.friend.id,
  (response) => {
    console.log("result=" + response.data);
    searchUnreadMessageCount(
      response.data,
      getLocalStorage("userId"),
      (response) => {
        console.log(response.data);
        unreadMessageCount.value = response.data;
      },
      (error) => {
        console.log(error);
      }
    );
  },
  (error) => {
    console.log(error);
  }
);

// const { friend } = defineProps({
//   friend: Object,
// });
</script>

<style scoped>
.chat-item {
  display: flex;
  align-items: center;
  padding: 10px;
  cursor: pointer;
  border-bottom: 1px solid #ccc;
  justify-content: space-between;
}
.chat-item-left {
  display: flex;
}
.chat-item-right {
  background-color: red;
  width: 25px;
  height: 25px;
  font-size: 15px;
  border-radius: 100%;
  text-align: center;
  color: white;
  font-weight: bold;
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
