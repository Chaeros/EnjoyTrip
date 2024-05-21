<template>
  <div
    class="chat-item"
    :class="{ selected: selectedFriend == friend.friendId }"
    @click="selectFriend"
    v-if="memberInfo"
  >
    <div class="chat-item-left">
      <div class="chat-avatar">
        <template v-if="memberInfo.image == null">
          <img src="@/img/member/default_img.jpg" />
        </template>
        <template v-else>
          <img :src="memberInfo.image" alt="Profile" />
        </template>
      </div>
      <div class="chat-details">
        <div class="chat-name">{{ memberInfo.nickname }}</div>
      </div>
    </div>
    <template v-if="unreadMessageCount !== 0">
      <div class="chat-item-right">{{ unreadMessageCount }}</div>
    </template>
  </div>
</template>
<script setup>
import { defineProps, defineEmits } from "vue";
import { ref, onMounted } from "vue";
import {
  registChatMessage,
  searchChatMessageList,
  enterOrRegistPrivateChatRoom,
  searchPrivateChatRoom,
} from "@/api/chat/chat.js";
import { getUserInfomationById } from "@/api/member/member.js";
import {
  countResetUnreadMessageCount,
  searchUnreadMessageCount,
} from "@/api/unreadmessagecount/unreadmessagecount.js";
import { getLocalStorage } from "@/util/localstorage/localstorage";
import { storeToRefs } from "pinia";
import { useWebSocketChatStore } from "@/store/chat/web-socket-chat.js";

const webSocketChat = useWebSocketChatStore();
const { socket } = storeToRefs(webSocketChat);

const props = defineProps({
  friend: {
    type: Object,
    required: true,
  },
  unreadMessageCountList: {
    type: Array,
  },
  selectedFriend: {
    type: Number,
  },
});

console.log("1111", props.friend);
console.log("&&&&&&&&&&&&&&&&&&");
console.log(props.unreadMessageCountList);

const memberInfo = ref();
const unreadMessageCount = ref(0);
const emit = defineEmits(["select-friend"]);

const selectFriend = () => {
  console.log(props.friend.friendId);
  console.log(props.friend);
  countResetUnreadMessageCount(
    props.friend.id,
    getLocalStorage("userId"),
    (response) => {
      console.log(response.data);
      unreadMessageCount.value = 0;
      props.friend.count = 0;
    },
    (error) => {
      console.log(error);
    }
  );
  emit("select-friend", props.friend.friendId);
};

onMounted(() => {
  getUserInfomationById(
    props.friend.friendId,
    (response) => {
      console.log(response.data);
      memberInfo.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
  console.log(props.friend.id);
  console.log(getLocalStorage("userId"));
  searchPrivateChatRoom(
    getLocalStorage("userId"),
    props.friend.friendId,
    (response) => {
      console.log("result=" + response.data);
      props.unreadMessageCountList.forEach((unreadMessageCount) => {
        if (unreadMessageCount.roomId == response.data) {
          unreadMessageCount.value = response.data;
        }
      });
    },
    (error) => {
      console.log(error);
    }
  );
});
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
.selected {
  background-color: #e9e9e9;
}
</style>
