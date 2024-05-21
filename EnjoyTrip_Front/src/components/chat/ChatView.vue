<template>
  <div v-if="userInfo">
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
        <div class="chat-top-bar-right">
          <div class="profile">
            <template v-if="userInfo.image == null">
              <img src="@/img/member/default_img.jpg" />
            </template>
            <template v-else>
              <img
                :src="userInfo.image"
                alt="Profile"
                class="chat-top-bar-right-img"
              />
            </template>
            <span>{{ userInfo.nickname }}</span>
          </div>
          <button
            type="button"
            class="btn btn-outline-primary minimize-button"
            @click="toggleMinimize"
          >
            접기
          </button>
        </div>
      </div>
      <div class="chat-content">
        <div class="chat-selectbar">
          <div class="chat-selectbar-wrap" @click="clickCallMyFriendList">
            <img
              v-show="currentMode == `FRIEND`"
              src="@/img/chat/friend-select.png"
              alt="내친구"
              class="chat-selectbar-img"
            />
            <img
              v-show="currentMode != `FRIEND`"
              src="@/img/chat/friend.png"
              alt="내친구"
              class="chat-selectbar-img"
            />
            <p class="chat-selectbar-title">친구</p>
          </div>
          <div class="chat-selectbar-wrap" @click="clickCallMyChatRoomList">
            <img
              v-show="currentMode == `CHATTING`"
              src="@/img/chat/talkballoon-select.png"
              alt="내채팅방"
              class="chat-selectbar-img"
            />
            <img
              v-show="currentMode != `CHATTING`"
              src="@/img/chat/talkballoon.png"
              alt="내채팅방"
              class="chat-selectbar-img"
            />
            <p class="chat-selectbar-title">채팅</p>
          </div>
          <div class="chat-selectbar-wrap" @click="clickCallGroupChatRoomList">
            <img
              v-show="currentMode == `GROUP`"
              src="@/img/chat/grouptalk-select.png"
              alt="그룹채팅"
              class="chat-selectbar-img"
            />
            <img
              v-show="currentMode != `GROUP`"
              src="@/img/chat/grouptalk.png"
              alt="그룹채팅"
              class="chat-selectbar-img"
            />
            <p class="chat-selectbar-title">그룹채팅</p>
          </div>
        </div>
        <div class="chat-sidebar">
          <div class="chat-list">
            <template v-if="currentMode == `FRIEND`">
              <ChatMemberItem
                v-for="friend in friends"
                :key="friend.id"
                :friend="friend"
                :unreadMessageCountList="unreadMessageCountList"
                :selectedFriend="selectedFriend"
                @select-friend="selectFriend"
              />
            </template>
            <template v-if="currentMode == `CHATTING`">
              <ChatMemberItem
                v-for="friend in chattingMembers"
                :key="friend.id"
                :friend="friend"
                :unreadMessageCountList="unreadMessageCountList"
                :selectedFriend="selectedFriend"
                @select-friend="selectFriend"
              />
            </template>
          </div>
        </div>
        <div class="chat-main">
          <div class="chat-header">
            <div class="chat-header-details">
              <template v-if="activeChat.name === ``">
                <div class="chat-header-details-notice">
                  대화할 상대를 선택하세요!
                </div>
              </template>
              <template v-else>
                <template v-if="activeChat.avatar == null">
                  <img
                    src="@/img/member/default_img.jpg"
                    alt="ProfileImage"
                    class="chat-header-avatar"
                  />
                </template>
                <template v-else>
                  <img
                    :src="activeChat.avatar"
                    alt="ProfileImage"
                    class="chat-header-avatar"
                  />
                </template>
                <div class="chat-header-details-nickname">
                  {{ activeChat.name }}
                </div>
              </template>
            </div>
          </div>
          <div class="chat-body" ref="chatBody">
            <div
              class="chat-message"
              v-for="message in activeChat.messages"
              :key="message.messageId"
            >
              <div
                :class="[
                  'message',
                  message.memberId == userId ? 'sent' : 'received',
                ]"
              >
                <div class="message-text">{{ message.message }}</div>
              </div>
            </div>
          </div>
          <div class="chat-footer">
            <template v-if="activeChat.name === ``">
              <input
                type="text"
                v-model="newMessage"
                @keydown.enter="sendMessage"
                placeholder="대화할 상대를 선택하세요!"
                readonly
              />
            </template>
            <template v-else>
              <input
                type="text"
                v-model="newMessage"
                @keydown.enter="sendMessage"
                placeholder="메시지를 입력하세요."
              />
            </template>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <button
        type="button"
        class="chat-minimized btn btn-outline-secondary"
        ref="minimizedButton"
        @mousedown="startDrag"
        @dblclick="toggleMinimize"
        :style="{ left: `${position.x}px`, top: `${position.y}px` }"
      >
        💬
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, nextTick } from "vue";
import ChatMemberItem from "@/components/item/chat/ChatMemberItem.vue";
import { getLocalStorage } from "@/util/localstorage/localstorage";
import {
  getUserInfomationById,
  getChattingMemberId,
} from "@/api/member/member.js";
import {
  bringFriendList,
  searchFriendInfoByRoomIdAndMyId,
} from "@/api/friend/friend.js";
import {
  registChatMessage,
  searchChatMessageList,
  enterOrRegistPrivateChatRoom,
  searchPrivateChatRoom,
} from "@/api/chat/chat.js";
import {
  countResetUnreadMessageCount,
  searchUnreadMessageCountListById,
} from "@/api/unreadmessagecount/unreadmessagecount.js";
import { storeToRefs } from "pinia";
import { useChatStore } from "@/store/chat/chat";
import { useWebSocketChatStore } from "@/store/chat/web-socket-chat.js";
import { useMemberStore } from "@/store/member";
import { useFriendManagementStore } from "@/store/friend-management/friend-management.js";

const friendManagementStore = useFriendManagementStore();
const { friends } = storeToRefs(friendManagementStore);
const { bringMyFriendsList } = friendManagementStore;

// Pinia 스토어 사용
const userId = getLocalStorage("userId");
const chatStore = useChatStore();
const webSocketChat = useWebSocketChatStore();
const { chatRoom, currentSelectedRoomId } = storeToRefs(chatStore);
const { enterOrRegistChatRoom, bringMyChatRoomList } = chatStore;
const { socket } = storeToRefs(webSocketChat);
const { sendMsg, sendEnterMsg, sendEscapeMsg } = webSocketChat;
const memberStore = useMemberStore();
const { userInfo, isLogin } = storeToRefs(memberStore);

// 반응형 변수
const isMinimized = ref(true);
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
  name: "",
  avatar: "",
  messages: [],
});
const newMessage = ref("");
const currentMode = ref("FRIEND");

const chattingMembers = ref([]);
const unreadMessageCountList = ref([]);
const selectedFriend = ref();

// DOM 참조
const chatContainer = ref(null);
const minimizedButton = ref(null);
const chatBody = ref(null); // 추가: chat-body 참조

// 메시지가 업데이트될 때마다 스크롤 조정
watch(
  () => activeChat.value.messages,
  (newMessages) => {
    if (newMessages.length > 0) {
      nextTick(() => {
        const chatBodyElement = chatBody.value;
        console.log("############################");
        chatBodyElement.scrollTop = chatBodyElement.scrollHeight;
      });
    }
  },
  { deep: true }
);

const callUnreadMessageCountList = () => {
  searchUnreadMessageCountListById(
    getLocalStorage("userId"),
    (response) => {
      console.log(response.data);
      unreadMessageCountList.value = response.data;
      console.log("%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      console.log(unreadMessageCountList.value);
    },
    (error) => {
      console.log(error);
    }
  );
};
callUnreadMessageCountList();

socket.value.onmessage = function (e) {
  const parsedData = JSON.parse(e.data);
  const tempData = {
    roomId: parsedData.chatRoomId,
    memberId: parsedData.senderId,
    message: parsedData.message,
  };

  console.log(tempData);
  console.log(
    "userId=",
    getLocalStorage("userId"),
    "currentRoomId=",
    currentSelectedRoomId.value
  );

  // 내가 보낸 메시지가 아니면서 현재 입장해있는 방이 아니라면
  if (
    parsedData.senderId != getLocalStorage("userId") &&
    parsedData.chatRoomId != currentSelectedRoomId.value
  ) {
    console.log("여긴 들어오냐?");
    if (currentMode.value === "FRIEND") {
      console.log(unreadMessageCountList.value);
      unreadMessageCountList.value.forEach((unreadMessageCount, index) => {
        if (unreadMessageCount.roomId == parsedData.chatRoomId) {
          console.log(unreadMessageCountList.value[index]);
          unreadMessageCountList.value[index].count =
            unreadMessageCountList.value[index].count + 1;
          console.log("index", index);
        }
      });
      console.log(unreadMessageCountList.value);
      // friends.value.forEach((friend) => {
      //   if (friend.id == parsedData.chatRoomId) {
      //     friends.value.count = friends.value.count + 1;
      //   }
      // });

      // clickCallMyFriendList();
    } else if (currentMode.value === "CHATTING") {
      // clickCallMyChatRoomList();
      // chattingMembers.value.forEach((friend) => {
      //   if (friend.id == parsedData.chatRoomId) {
      //     chattingMembers.value.count = chattingMembers.value.count + 1;
      //   }
      // });
    }
  }
  if (parsedData.chatRoomId == currentSelectedRoomId.value) {
    activeChat.value.messages.push(tempData);
  }
};

onMounted(() => {
  // DOM 요소가 렌더링된 후 참조할 수 있도록 onMounted 사용
  chatContainer.value = document.querySelector(".chat-container");
  minimizedButton.value = document.querySelector(".chat-minimized");
  clickCallMyFriendList();
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
    sendMsg(newMessage.value, getLocalStorage("userId"));
    registChatMessage(
      {
        roomId: currentSelectedRoomId.value,
        memberId: getLocalStorage("userId"),
        message: newMessage.value,
      },
      (response) => {
        console.log(response.data);
      },
      (error) => {
        console.log(error);
      }
    );
    newMessage.value = "";
  } else {
    console.error("No chat room selected or message is empty");
  }
};

const selectFriend = (friendId) => {
  selectedFriend.value = friendId;
  enterChatRoom(friendId);
  enterOrRegistPrivateChatRoom(
    { myId: getLocalStorage("userId"), opponentId: receiverId.value },
    (response) => {
      currentSelectedRoomId.value = response.data;
      sendEnterMsg();
      searchChatMessageList(
        (response) => {
          activeChat.value.messages = response.data;
        },
        (error) => {
          console.log(error);
        }
      );
      getUserInfomationById(
        friendId,
        (response) => {
          console.log(response.data);

          activeChat.value.id = response.data.id;
          activeChat.value.name = response.data.nickname;
          activeChat.value.avatar = response.data.image;
          // resetCount(response.data);
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
};

const resetCount = (roomId) => {
  countResetUnreadMessageCount(
    roomId,
    getLocalStorage("userId"),
    (response) => {
      if (currentMode.value === "FRIEND") {
        clickCallMyFriendList();
      } else if (currentMode.value === "CHATTING") {
        // clickCallMyChatRoomList();
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

const clickCallMyFriendList = () => {
  currentMode.value = "FRIEND";
  bringFriendList(
    getLocalStorage("userId"),
    (response) => {
      friends.value = [];
      friends.value = response.data;
      response.data.forEach((friend) => {
        searchPrivateChatRoom(
          getLocalStorage("userId"),
          friend.friendId,
          (roomId) => {
            enterOrRegistPrivateChatRoom(roomId);
            console.log("enter room ", roomId);
          },
          (error2) => {
            console.log(error2);
          }
        );
        // bringFriendInfo(friend.friendId);
      });
      console.log(response.data);
    },
    (error) => {
      console.log(error);
    }
  );
};

// const bringFriendInfo = (friendId) => {
//   getUserInfomationById(
//     friendId,
//     (response) => {
//       friends.value.push(response.data);
//     },
//     (error) => {
//       console.log(error);
//     }
//   );
// };

const clickCallGroupChatRoomList = () => {
  currentMode.value = "GROUP";
};

const clickCallMyChatRoomList = () => {
  currentMode.value = "CHATTING";
  getChattingMemberId(
    getLocalStorage("userId"),
    (response) => {
      chattingMembers.value = [];
      const tempArray = [];
      response.data.forEach(async (receiverId) => {
        await searchPrivateChatRoom(
          getLocalStorage("userId"),
          receiverId,
          (roomId) => {
            // tempArray.push({
            //   id: roomId.data,
            //   myId: getLocalStorage("userId"),
            //   opponentId: receiverId,
            // });
            chattingMembers.value.push({
              id: roomId.data,
              myId: getLocalStorage("userId"),
              friendId: receiverId,
            });
          },
          (error2) => {
            console.log(error2);
          }
        );
        // bringFriendInfo(chatMemberId);
      });
      console.log(chattingMembers.value);
      // friends.value = tempArray;
      // console.log(friends);
    },
    (error) => {
      console.log(error);
    }
  );
};

const enterChatRoom = (friendId) => {
  receiverId.value = friendId;
};
</script>

<style scoped>
.chat-container {
  position: fixed;
  border: 1px solid #ccc;
  border-radius: 10px;
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
  /* background-color: white; */
  border-bottom: 1px solid #ccc;
  border-radius: 10px;
}
.chat-top-bar-right {
  display: flex;
}
.chat-top-bar-right-img {
  width: 30px;
  height: 30px;
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
  font-size: 15px;
  cursor: pointer;
  margin-left: 10px;
  font-weight: bold;
}
.chat-content {
  display: flex;
  height: calc(100% - 50px); /* Adjust the height to account for the top bar */
}
.chat-selectbar {
  width: 60px;
  display: flex;
  flex-direction: column;
  justify-items: center;
  align-items: center;
  /* background-color: rgb(234, 226, 226); */
}
.chat-selectbar-logo {
  width: 60px;
}
.chat-header-details {
  display: flex;
}
.chat-header-details-notice {
  font-size: 15px;
}
.chat-header-details-nickname {
  font-size: 25px;
  font-weight: bold;
}
.chat-selectbar-wrap {
  border-radius: 10px;
  margin: 5px 0;
  cursor: pointer;
}
.chat-selectbar-img {
  margin: 30px, 2px;
  width: 50px;
  margin: 0 auto;
}
.chat-selectbar-title {
  font-size: 13px;
  font-weight: bold;
  margin: 0 auto;
  text-align: center;
}
.chat-selectbar-wrap:hover {
  background-color: #e9e9e9;
}
.chat-sidebar {
  width: 200px;
  /* background-color: white; */
  border-left: 1px solid #ccc;
  border-right: 1px solid #ccc;
  overflow-y: auto;
  flex-shrink: 0;
}
.chat-list {
  /* padding: 10px; */
}
.chat-item {
  display: flex;
  align-items: center;
  /* padding: 10px; */
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
  background-color: #c1c5fd;
  text-align: right;
}
.received .message-text {
  background-color: #fff;
  border: 1px solid #ccc;
  text-align: left;
}
.chat-footer {
  padding: 10px;
  /* background-color: #fff; */
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
  /* border: 2px solid; */
  /* background-color: #007bff; */
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: move;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}
</style>
