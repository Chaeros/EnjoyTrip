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
          <!-- <div class="chat-selectbar-wrap" @click="clickCallGroupChatRoomList">
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
          </div> -->
        </div>
        <div class="chat-sidebar">
          <div class="chat-list">
            <template v-if="currentMode == `FRIEND`">
              <ChatMemberItem
                v-for="friend in friends"
                :key="friend.id"
                :friend="friend"
                :unread-message-count-list="unreadMessageCountList"
                :selected-friend="selectedFriend"
                @select-friend="selectFriend"
              />
            </template>
            <template v-if="currentMode == `CHATTING`">
              <ChatMemberItem
                v-for="friend in chattingMembers"
                :key="friend.id"
                :friend="friend"
                :unread-message-count-list="unreadMessageCountList"
                :selected-friend="selectedFriend"
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
                  message.memberId == getLocalStorage('userId')
                    ? 'sent'
                    : 'received',
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
                placeholder="대화할 상대를 선택하세요!"
                readonly
              />
            </template>
            <template v-else>
              <div class="chat-input-box">
                <input
                  type="text"
                  v-model="newMessage"
                  @keydown.enter="sendMessage"
                  placeholder="메시지를 입력하세요."
                />
                <!-- <input type="file" @change="onFileChange" accept="image/*" /> -->
              </div>
              <div>
                <!-- <button id="emoji_btn" @click="addEmoji">button</button>
                <input type="text" id="message" /> -->
              </div>
            </template>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <button
        type="button"
        class="btn btn-dark chat-minimized"
        ref="minimizedButton"
        @mousedown="startDrag"
        @dblclick="toggleMinimize"
        :style="{ left: `${position.x}px`, top: `${position.y}px` }"
      >
        🗨️
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, nextTick } from "vue";
import axios from "axios";
import ChatMemberItem from "@/components/item/chat/ChatMemberItem.vue";
import { getLocalStorage } from "@/util/localstorage/localstorage";
// import { Picker } from "emoji-mart-vue-fast";
// import "emoji-mart-vue-fast/css/emoji-mart.css";
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
  bringPrivateChatRoomList,
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
const { VITE_VUE_API_URL, VITE_VUE_IMAGE_SERVER_URL } = import.meta.env;
const friendManagementStore = useFriendManagementStore();
const { friends } = storeToRefs(friendManagementStore);
const { bringMyFriendsList } = friendManagementStore;

// Pinia 스토어 사용
const userId = ref(getLocalStorage("userId"));
const chatStore = useChatStore();
const webSocketChat = useWebSocketChatStore();
const { chatRoom, currentSelectedRoomId } = storeToRefs(chatStore);
const { enterOrRegistChatRoom, bringMyChatRoomList } = chatStore;
const { socket } = storeToRefs(webSocketChat);
const { sendMsg, sendEnterMsg, sendEscapeMsg, sendEnterToRoomMsg } =
  webSocketChat;
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
const myRooms = ref([]);

// DOM 참조
const chatContainer = ref(null);
const minimizedButton = ref(null);
const chatBody = ref(null); // 추가: chat-body 참조

const selectedFile = ref(null); // 파일 선택 상태 저장
const onFileChange = (event) => {
  selectedFile.value = event.target.files[0];
  sendImage();
};

const sendImage = async () => {
  if (!selectedFile.value) return;

  const formData = new FormData();
  formData.append("file", selectedFile.value);
  formData.append("senderId", getLocalStorage("userId")); // 필요한 경우 추가 데이터

  try {
    let accessToken = getLocalStorage("access_token");
    axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    const response = await axios.post(
      VITE_VUE_IMAGE_SERVER_URL + "/image/upload",
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );
    console.log(response.data);
    // 성공 시 처리
    sendMsg(response.data.imageUrl, getLocalStorage("userId"), "IMAGE"); // 이미지 URL과 함께 메시지 전송
    registChatMessage(
      {
        roomId: currentSelectedRoomId.value,
        memberId: getLocalStorage("userId"),
        message: response.data.imageUrl,
        messageType: "IMAGE", // 이미지 타입 메시지
      },
      (response) => {
        console.log(response.data);
      },
      (error) => {
        console.log(error);
      }
    );
  } catch (error) {
    console.error(error);
  }
};

searchUnreadMessageCountListById(
  getLocalStorage("userId"),
  (response) => {
    unreadMessageCountList.value = response.data;
    console.log("@@@@@@@@@@@@@");
    console.log(unreadMessageCountList.value);
  },
  (error) => {
    console.log(error);
  }
);

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
    messageType: parsedData.messageType,
  };
  console.log(parsedData);

  console.log(
    "송수신자 확인",
    parsedData.senderId,
    getLocalStorage("userId"),
    userId
  );

  if (parsedData.senderId == getLocalStorage("userId")) {
    activeChat.value.messages.push(tempData);
    return;
  }

  let isMember = false;
  let preFriend = null;
  if (currentMode.value == "CHATTING") {
    console.log(chattingMembers);
    console.log(parsedData);
    chattingMembers.value.forEach((member) => {
      if (member.friendId == parsedData.senderId) {
        isMember = true;
        console.log("여기 오긴함");
        preFriend = member;
      }
    });
    console.log("결과", isMember);
    if (isMember == false) {
      searchPrivateChatRoom(
        getLocalStorage("userId"),
        parsedData.senderId,
        (roomId) => {
          chattingMembers.value.push({
            id: roomId.data,
            myId: getLocalStorage("userId"),
            friendId: parsedData.senderId,
          });
          console.log("데이터삽입", chattingMembers);
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }
  console.log(parsedData.chatRoomId, currentSelectedRoomId.value);
  if (parsedData.chatRoomId == currentSelectedRoomId.value) {
    if (parsedData.messageType == "TALK") {
      activeChat.value.messages.push(tempData);
    }
    console.log("이 방으로 옴");
  } else {
    // 수신받은 메시지의 출처가 , 현재 내가 머문 방과 다른 경우
    // 1. 내가 지닌 방들 중 하나인 경우 - count를 1 증가시킨다.
    console.log(unreadMessageCountList.value);
    let isMyRoom = false;
    unreadMessageCountList.value.forEach((unreadMessageCount, index) => {
      if (unreadMessageCount.roomId == parsedData.chatRoomId) {
        console.log(unreadMessageCount.roomId, "1증가");
        console.log("index=", index);
        console.log(unreadMessageCountList.value[index]);
        const tempVal = unreadMessageCountList.value[index].count;
        if (parsedData.messageType == "TALK") {
          unreadMessageCountList.value[index].count = tempVal + 1;
        }
        console.log(unreadMessageCountList.value[index].count);
        isMyRoom = true;
      }
    });
    console.log(unreadMessageCountList.value);

    // 2. 내가 지니지 않은 방들 중 하나인 경우 - db를 검색하여 나와 해당 방의 연결관계를 파악한다.
    // 만약 나와의 연결관계가 존재한다면, unreadMessageCountList에 추가하여 상태를 관리한다.
    if (isMyRoom == false) {
      searchUnreadMessageCountListById(
        getLocalStorage("userId"),
        (response) => {
          unreadMessageCountList.value = response.data;
          console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2!#");
          console.log(unreadMessageCountList.value);
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }
};

onMounted(() => {
  // DOM 요소가 렌더링된 후 참조할 수 있도록 onMounted 사용
  chatContainer.value = document.querySelector(".chat-container");
  minimizedButton.value = document.querySelector(".chat-minimized");
  clickCallMyFriendList();
  userId.value = getLocalStorage("userId");
});

const toggleMinimize = () => {
  isMinimized.value = !isMinimized.value;
  console.log(isMinimized);
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
    console.log(newMessage.value, getLocalStorage("userId"));
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
  callUnreadMessageCountList;
};

const resetCount = (roomId) => {
  countResetUnreadMessageCount(
    roomId,
    getLocalStorage("userId"),
    (response) => {
      if (currentMode.value === "FRIEND") {
        clickCallMyFriendList();
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
      console.log(friends.value);
      response.data.forEach((friend) => {
        enterOrRegistPrivateChatRoom({
          myId: getLocalStorage("userId"),
          opponentId: friend.friendId,
        });
        // searchPrivateChatRoom(
        //   getLocalStorage("userId"),
        //   friend.friendId,
        //   (roomId) => {
        //     console.log("enter room ", roomId);
        //   },
        //   (error2) => {
        //     console.log(error2);
        //   }
        // );
      });
      console.log(response.data);
    },
    (error) => {
      console.log(error);
    }
  );
  callUnreadMessageCountList();
};

const clickCallGroupChatRoomList = () => {
  currentMode.value = "GROUP";
};

const clickCallMyChatRoomList = () => {
  currentMode.value = "CHATTING";
  getChattingMemberId(
    getLocalStorage("userId"),
    (response) => {
      chattingMembers.value = [];
      response.data.forEach(async (receiverId) => {
        await searchPrivateChatRoom(
          getLocalStorage("userId"),
          receiverId,
          (roomId) => {
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
      });
      console.log(chattingMembers.value);
    },
    (error) => {
      console.log(error);
    }
  );
  callUnreadMessageCountList();
};

const enterChatRoom = (friendId) => {
  receiverId.value = friendId;
};

// const addEmoji = (emoji) => {
//   newMessage.value += emoji.native;
// };

// const button = document.querySelector("#emoji_btn");
// const picker = new EmojiButton({
//   position: "bottom-start",
// });

// button.addEventListener("click", () => {
//   picker.togglePicker(button);
// });

// picker.on("emoji", (emoji) => {
//   const text_box = document.querySelector("#message");
//   text_box.value += emoji;
// });
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
  height: calc(100% - 50px);
}
.chat-selectbar {
  width: 60px;
  display: flex;
  flex-direction: column;
  justify-items: center;
  align-items: center;
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
  border-left: 1px solid #ccc;
  border-right: 1px solid #ccc;
  overflow-y: auto;
  flex-shrink: 0;
}
.chat-list {
}
.chat-item {
  display: flex;
  align-items: center;
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
  width: 70px;
  height: 70px;
  border-radius: 50%;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: move;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.talk-button-img {
  width: 70px;
  height: 70px;
}
.chat-input-box {
  display: flex;
}
.emoji {
  width: 2000px;
  height: 100px;
  z-index: 99999;
}
</style>
