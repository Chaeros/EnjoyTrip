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
              @select-friend="selectFriend"
              @click="enterChatRoom"
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

<script>
import { getLocalStorage } from "@/util/localstorage/localstorage";
import { ref } from "vue";
import {
  addFriend,
  removeFriend,
  bringFriendList,
} from "@/api/friend/friend.js";
import {
  getUserInfomation,
  getUserInfomationById,
} from "@/api/member/member.js";
import ChatMemberItem from "@/components/item/chat/ChatMemberItem.vue";

const userId = getLocalStorage("userId");

export default {
  components: {
    ChatMemberItem, // ChatMemberItem 컴포넌트를 등록합니다.
  },
  data() {
    return {
      isMinimized: false,
      receiverId: ref(-1),
      position: {
        x: window.innerWidth / 2 - 400,
        y: window.innerHeight / 2 - 325,
      },
      dragging: false,
      offset: { x: 0, y: 0 },
      friendList: [
        {
          id: 1,
          name: "조이마미",
          lastMessage: "메시지를 보냈어요",
          avatar: "avatar1.png",
          messages: [],
        },
        {
          id: 2,
          name: "강태호",
          lastMessage: "안녕하세요",
          avatar: "avatar2.png",
          messages: [],
        },
        // 다른 채팅 목록 추가...
      ],
      activeChat: {
        id: 1,
        name: "조이마미",
        avatar: "avatar1.png",
        messages: [
          { id: 1, sender: "other", text: "안녕하세요" },
          { id: 2, sender: "me", text: "네, 안녕하세요" },
          // 다른 메시지 추가...
        ],
      },
      newMessage: "",
    };
  },
  methods: {
    toggleMinimize() {
      this.isMinimized = !this.isMinimized;
    },
    startDrag(e) {
      const target = e.target;
      if (
        target === this.$refs.chatContainer ||
        target === this.$refs.minimizedButton ||
        target.classList.contains("chat-header") ||
        target.classList.contains("chat-top-bar")
      ) {
        this.dragging = true;
        this.offset.x = e.clientX - target.getBoundingClientRect().left;
        this.offset.y = e.clientY - target.getBoundingClientRect().top;
        document.addEventListener("mousemove", this.onDrag);
        document.addEventListener("mouseup", this.stopDrag);
      }
    },
    onDrag(e) {
      if (this.dragging) {
        this.position.x = e.clientX - this.offset.x;
        this.position.y = e.clientY - this.offset.y;
      }
    },
    stopDrag() {
      this.dragging = false;
      document.removeEventListener("mousemove", this.onDrag);
      document.removeEventListener("mouseup", this.stopDrag);
    },
    sendMessage() {
      if (this.newMessage.trim() !== "") {
        this.activeChat.messages.push({
          id: Date.now(),
          sender: "me",
          text: this.newMessage,
        });
        this.newMessage = "";
      }
    },
    selectFriend(chat) {
      this.activeChat = chat;
    },
    clickCallMyFriendList() {
      bringFriendList(
        userId,
        (response) => {
          console.log(response.data);
          this.friendList = [];
          response.data.forEach((friend) => {
            this.bringFriendInfo(friend.friendId);
          });
          console.log(this.friendList);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    bringFriendInfo(friendId) {
      getUserInfomationById(
        friendId,
        (response) => {
          console.log(this.friendList);
          console.log(response.data);
          this.friendList.push(response.data);
          console.log(this.friendList);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    clickCallMyChatRoomList() {},
    enterChatRoom(friendId) {
      receiverId.value = friendId;
    },
  },
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
