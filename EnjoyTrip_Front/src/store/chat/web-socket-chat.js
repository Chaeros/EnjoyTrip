import { ref } from "vue";
import { getLocalStorage } from "@/util/localstorage/localstorage";
import { defineStore, storeToRefs } from "pinia";
import { useChatStore } from "@/store/chat/chat";

export const useWebSocketChatStore = defineStore("webSocketChatStore", () => {
  const chatStore = useChatStore();
  const { chatRoom, currentSelectedRoomId } = storeToRefs(chatStore);
  const webSocketURL = import.meta.env.VITE_WEB_SOCKET_URL;
  const userId = getLocalStorage("userId");
  let socket = ref(null);

  const connect = () => {
    if (socket.value) {
      socket.value.close();
    }
    socket.value = new WebSocket(
      webSocketURL + "/chat?token=" + getLocalStorage("access_token")
    );

    socket.value.onopen = function (e) {
      console.log("채팅 서버와 연결되었습니다!");
    };

    socket.value.onerror = function (e) {
      console.log("채팅 서버 연결 오류 발생");
      console.log(e);
    };

    socket.value.onclose = function (e) {
      console.log("WebSocket 연결이 닫혔습니다.", e);
    };

    socket.value.onmessage = function (e) {
      console.log(e.data);
      // let msgArea = document.querySelector(".msgArea");
      // let newMsg = document.createElement("div");
      // newMsg.innerText = e.data;
      // msgArea.append(newMsg);
    };
  };

  const sendMsg = (content, senderId) => {
    console.log(senderId);
    if (socket.value) {
      let payload = {
        messageType: "TALK",
        senderId: parseInt(getLocalStorage("userId"), 10),
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
    if (socket.value && socket.value.readyState === WebSocket.OPEN) {
      let payload = {
        messageType: "ENTER",
        senderId: getLocalStorage("userId"),
        message: getLocalStorage("userId") + "님이 입장하셨습니다.",
        chatRoomId: currentSelectedRoomId.value,
      };
      console.log(payload);
      socket.value.send(JSON.stringify(payload));
      console.log("에러확인용");
    } else {
      console.log("WebSocket 연결이 열리지 않았습니다.");
    }
  };

  const sendEnterToRoomMsg = (roomId) => {
    if (socket.value) {
      let payload = {
        messageType: "ENTER",
        senderId: getLocalStorage("userId"),
        message: getLocalStorage("userId") + "님이 입장하셨습니다.",
        chatRoomId: roomId,
      };
      console.log(payload);
      socket.value.send(JSON.stringify(payload));
    } else {
      console.log("WebSocket 연결이 열리지 않았습니다.");
    }
  };

  const sendEscapeMsg = () => {
    if (socket.value) {
      let payload = {
        messageType: "LEAVE",
        senderId: getLocalStorage("userId"),
        message: "사용자가 방을 떠났습니다.",
        chatRoomId: currentSelectedRoomId.value,
      };
      socket.value.send(JSON.stringify(payload));
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
    sendEnterToRoomMsg,
  };
});
