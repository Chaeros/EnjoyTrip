import { ref } from "vue";
import { getLocalStorage } from "@/util/localstorage/localstorage";
import { defineStore, storeToRefs } from "pinia";
import { useChatStore } from "@/store/chat/chat";

const userId = getLocalStorage("userId");

export const useWebSocketChatStore = defineStore("webSocketChatStore", () => {
  const chatStore = useChatStore();
  const { chatRoom, currentSelectedRoomId } = storeToRefs(chatStore);
  const webSocketURL = import.meta.env.VITE_WEB_SOCKET_URL;
  let socket = ref(null);

  const connect = () => {
    socket.value = new WebSocket(webSocketURL + "/chat");

    socket.value.onopen = function (e) {
      console.log("채팅 서버와 연결되었습니다!");
    };

    socket.value.onerror = function (e) {
      console.log("채팅 서버 연결 오류 발생");
      console.log(e);
    };

    socket.value.onmessage = function (e) {
      console.log(e.data);
      let msgArea = document.querySelector(".msgArea");
      let newMsg = document.createElement("div");
      newMsg.innerText = e.data;
      msgArea.append(newMsg);
    };
  };

  const sendMsg = (content) => {
    if (socket.value) {
      let payload = {
        messageType: "TALK",
        senderId: userId,
        message: content,
        chatRoomId: currentSelectedRoomId.value,
      };
      console.log(payload);
      socket.value.send(JSON.stringify(payload));
    } else {
      console.log("WebSocket 연결이 열리지 않았습니다.");
    }
  };

  const sendEnterMsg = () => {
    let content = document.querySelector(".content").value;
    if (socket.value) {
      let message = {
        messageType: "ENTER",
        senderId: userId,
        message: userId + "님이 입장하셨습니다.",
        chatRoomId: currentSelectedRoomId.value,
      };
      socket.value.send(JSON.stringify(message));
    } else {
      console.log("WebSocket 연결이 열리지 않았습니다.");
    }
  };

  const sendEscapeMsg = () => {
    if (socket.value) {
      let message = {
        messageType: "LEAVE",
        senderId: 13,
        message: "사용자가 방을 떠났습니다.",
        chatRoomId: 1,
      };
      socket.value.send(JSON.stringify(message));
      socket.value.close();
      console.log("서버와 연결이 끊어졌습니다.");
    } else {
      console.log("WebSocket 연결이 열리지 않았습니다.");
    }
  };

  return {
    socket,
    connect,
    sendMsg,
    sendEnterMsg,
    sendEscapeMsg,
  };
});
