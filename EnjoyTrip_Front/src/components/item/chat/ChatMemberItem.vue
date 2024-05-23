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
import { ref, onMounted, watch } from "vue";
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

const memberInfo = ref();
const unreadMessageCount = ref(0);
const emit = defineEmits(["select-friend"]);
const tempRoomId = ref(0);

console.log(props.unreadMessageCountList);

const selectFriend = () => {
  console.log("###################################################");
  console.log(props);
  console.log(props.friend.friendId, getLocalStorage("userId"));

  searchPrivateChatRoom(
    getLocalStorage("userId"),
    props.friend.friendId,
    (response) => {
      countResetUnreadMessageCount(
        response.data,
        getLocalStorage("userId"),
        (response2) => {
          unreadMessageCount.value = 0;
        },
        (error2) => {
          console.log(error2);
        }
      );
      emit("select-friend", props.friend.friendId);
    },
    (error) => {
      console.log(error);
    }
  );
};

const updateUnreadMessageCount = () => {
  searchPrivateChatRoom(
    getLocalStorage("userId"),
    props.friend.friendId,
    (response) => {
      const roomId = response.data;
      const unreadMessage = props.unreadMessageCountList.find(
        (message) => message.roomId == roomId
      );
      if (unreadMessage) {
        unreadMessageCount.value = unreadMessage.count;
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

watch(
  () => props.unreadMessageCountList,
  (newValue, oldValue) => {
    updateUnreadMessageCount();
    console.log(newValue, oldValue);
  },
  { deep: true }
);

onMounted(() => {
  getUserInfomationById(
    props.friend.friendId,
    (response) => {
      memberInfo.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
  updateUnreadMessageCount();
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
