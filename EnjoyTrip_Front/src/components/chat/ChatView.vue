<template>
  <div>
    <div
      v-if="!isMinimized"
      class="chat-container"
      ref="chatContainer"
      @mousedown="startDrag"
      :style="{
        left: `${position.x}px`,
        top: `${position.y}px`,
        width: '800px',
        height: '650px',
      }"
    >
      <div class="chat-top-bar" @mousedown.stop="startDrag">
        <div class="logo">
          <img src="@/img/coldragon.png" alt="Logo" />
          <div class="logo-title">Cold Dragon</div>
        </div>
        <div class="profile">
          <img src="" alt="Profile" />
          <span>조이마미</span>
        </div>
        <button class="minimize-button" @click="toggleMinimize">-</button>
      </div>
      <div class="chat-content">
        <div class="chat-selectbar">
          <div class="chat-selectbar-img">
            <img src="" alt="내친구" @click="clickCallMyFriendList" />
          </div>
          <div class="chat-selectbar-img">
            <img src="" alt="내채팅방" @click="clickCallMyChatRoomList" />
          </div>
        </div>
        <div class="chat-sidebar">
          <div class="chat-list">
            <ChatMemberItem
              v-for="friend in friendList"
              :key="friend.id"
              :friend="friend"
              @select-friend="selectFriend(friend.id)"
            />
          </div>
        </div>
        <div class="chat-main">
          <div class="chat-header">
            <div class="chat-header-details">
              <img
                :src="activeChat.avatar"
                alt="Avatar"
                class="chat-header-avatar"
              />
              <div>{{ activeChat.name }}</div>
            </div>
          </div>
          <div class="chat-body">
            <div
              class="chat-message"
              v-for="message in activeChat.messages"
              :key="message.id"
            >
              <div
                :class="[
                  'message',
                  message.sender === 'me' ? 'sent' : 'received',
                ]"
              >
                <div class="message-text">{{ message.text }}</div>
              </div>
            </div>
          </div>
          <div class="chat-footer">
            <input
              type="text"
              v-model="newMessage"
              @keydown.enter="sendMessage"
              placeholder="메시지를 입력하세요..."
            />
          </div>
        </div>
      </div>
    </div>
    <div
      v-else
      class="chat-minimized"
      ref="minimizedButton"
      @mousedown="startDrag"
      @dblclick="toggleMinimize"
      :style="{ left: `${position.x}px`, top: `${position.y}px` }"
    >
      💬
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import ChatMemberItem from "@/components/item/chat/ChatMemberItem.vue";
import { getLocalStorage } from "@/util/localstorage/localstorage";
import { getUserInfomationById } from "@/api/member/member.js";
import { bringFriendList } from "@/api/friend/friend.js";
import { storeToRefs } from "pinia";
import { useChatStore } from "@/store/chat/chat";
import { useWebSocketChatStore } from "@/store/chat/web-socket-chat.js";
// Pinia 스토어 사용
const userId = getLocalStorage("userId");
const chatStore = useChatStore();
const webSocketChat = useWebSocketChatStore();
const { chatRoom, currentSelectedRoomId } = storeToRefs(chatStore);
const { enterOrRegistChatRoom, bringMyChatRoomList } = chatStore;
const { socket } = storeToRefs(webSocketChat);
const { sendMsg, sendEnterMsg, sendEscapeMsg } = webSocketChat;

// 반응형 변수
const isMinimized = ref(false);
const receiverId = ref(-1);
const position = ref({
  x: window.innerWidth / 2 - 400,
  y: window.innerHeight / 2 - 325,
});
const dragging = ref(false);
const offset = ref({ x: 0, y: 0 });
const friendList = ref([]);
const activeChat = ref({
  id: 1,
  name: "조이마미",
  avatar: "avatar1.png",
  messages: [
    { id: 1, sender: "other", text: "안녕하세요" },
    { id: 2, sender: "me", text: "네, 안녕하세요" },
  ],
});
const newMessage = ref("");

// DOM 참조
const chatContainer = ref(null);
const minimizedButton = ref(null);

onMounted(() => {
  // DOM 요소가 렌더링된 후 참조할 수 있도록 onMounted 사용
  chatContainer.value = document.querySelector(".chat-container");
  minimizedButton.value = document.querySelector(".chat-minimized");
});

const toggleMinimize = () => {
  isMinimized.value = !isMinimized.value;
};

const startDrag = (e) => {
  const target = e.target;
  if (
    target === chatContainer.value ||
    target === minimizedButton.value ||
    target.classList.contains("chat-header") ||
    target.classList.contains("chat-top-bar")
  ) {
    dragging.value = true;
    offset.value.x = e.clientX - target.getBoundingClientRect().left;
    offset.value.y = e.clientY - target.getBoundingClientRect().top;
    document.addEventListener("mousemove", onDrag);
    document.addEventListener("mouseup", stopDrag);
  }
};

const onDrag = (e) => {
  if (dragging.value) {
    position.value.x = e.clientX - offset.value.x;
    position.value.y = e.clientY - offset.value.y;
  }
};

const stopDrag = () => {
  dragging.value = false;
  document.removeEventListener("mousemove", onDrag);
  document.removeEventListener("mouseup", stopDrag);
};

const sendMessage = () => {
  if (newMessage.value.trim() !== "") {
    sendMsg(newMessage.value);
    newMessage.value = "";
  } else {
    console.error("No chat room selected or message is empty");
  }
};

// const sendMessage = () => {
//   console.log(newMessage.value);
//   console.log(currentSelectedRoomId.value);
//   sendMsg(newMessage.value, currentSelectedRoomId.value);
//   // if (newMessage.value.trim() !== "") {
//   //   activeChat.value.messages.push({
//   //     id: Date.now(),
//   //     sender: "me",
//   //     text: newMessage.value,
//   //   });
//   //   newMessage.value = "";
//   // }
// };

const selectFriend = (friendId) => {
  // activeChat.value = chat;
  enterChatRoom(friendId);
  enterOrRegistChatRoom({ myId: userId, opponentId: receiverId.value });
  sendEnterMsg();
};

const clickCallMyFriendList = () => {
  bringFriendList(
    userId,
    (response) => {
      console.log(response.data);
      friendList.value = [];
      response.data.forEach((friend) => {
        bringFriendInfo(friend.friendId);
      });
      console.log(friendList.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

const bringFriendInfo = (friendId) => {
  getUserInfomationById(
    friendId,
    (response) => {
      console.log(friendList.value);
      console.log(response.data);
      friendList.value.push(response.data);
      console.log(friendList.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

const clickCallMyChatRoomList = () => {
  // 여기에 로직 추가
};

const enterChatRoom = (friendId) => {
  console.log("@@@@@@@@@@@@@@@@@@@22");
  console.log(friendId);
  receiverId.value = friendId;
};
</script>

<style scoped>
.chat-container {
  position: fixed;
  border: 1px solid #ccc;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  cursor: move;
}
.chat-top-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: white;
  border-bottom: 1px solid #ccc;
}
.logo {
  display: flex;
}
.logo img {
  height: 30px;
}
.logo-title {
  font-weight: bold;
}
.profile {
  display: flex;
  align-items: center;
}
.profile img {
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}
.minimize-button {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}
.chat-content {
  display: flex;
  height: calc(100% - 50px); /* Adjust the height to account for the top bar */
}
.chat-selectbar {
  width: 60px;
  background-color: #f5f5f5;
}
.chat-selectbar-img {
  margin: 30px, 2px;
  cursor: pointer;
}
.chat-selectbar-img:hover {
  background-color: #e9e9e9;
}
.chat-sidebar {
  width: 200px;
  background-color: white;
  border-right: 1px solid #ccc;
  overflow-y: auto;
  flex-shrink: 0;
}
.chat-list {
  padding: 10px;
}
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
.chat-main {
  flex: 1;
  display: flex;
  flex-direction: column;
}
.chat-header {
  display: flex;
  align-items: center;
  padding: 10px;
  background-color: #fff;
  border-bottom: 1px solid #ccc;
}
.chat-header-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}
.chat-body {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
  background-color: white;
}
.chat-message {
  margin-bottom: 10px;
}
.message {
  display: flex;
  align-items: center;
}
.sent {
  justify-content: flex-end;
}
.received {
  justify-content: flex-start;
}
.message-text {
  max-width: 60%;
  padding: 10px;
  border-radius: 10px;
}
.sent .message-text {
  background-color: #dcf8c6;
  text-align: right;
}
.received .message-text {
  background-color: #fff;
  border: 1px solid #ccc;
  text-align: left;
}
.chat-footer {
  padding: 10px;
  background-color: #fff;
  border-top: 1px solid #ccc;
}
.chat-footer input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.chat-minimized {
  position: fixed;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #007bff;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: move;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}
</style>
