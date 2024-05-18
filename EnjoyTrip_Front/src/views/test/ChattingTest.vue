<template>
  <body>
    <input
      type="text"
      placeholder="보낼 메세지를 입력하세요."
      class="content"
    />
    <button type="button" value="연결" class="sendBtn" @click="connect">
      연결
    </button>
    <button type="button" value="연결" class="sendBtn" @click="sendEnterMsg">
      방 입장
    </button>
    <button type="button" value="연결" class="sendBtn" @click="sendEscapeMsg">
      방 탈출
    </button>
    <button type="button" value="전송" class="sendBtn" @click="sendMsg">
      전송
    </button>
    <div>
      <span>메세지</span>
      <div class="msgArea"></div>
    </div>
  </body>
</template>

<script setup>
import { ref } from "vue";

let socket = ref(null);

const connect = () => {
  socket.value = new WebSocket("ws://localhost:8080/ws/chat");

  socket.value.onopen = function (e) {
    console.log("서버와 연결되었습니다!");
  };

  socket.value.onerror = function (e) {
    console.log("연결 오류 발생");
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

const sendMsg = () => {
  let content = document.querySelector(".content").value;
  if (socket.value) {
    let message = {
      messageType: "TALK",
      senderId: 13,
      message: content,
      chatRoomId: 1,
    };
    socket.value.send(JSON.stringify(message));
  } else {
    console.log("WebSocket 연결이 열리지 않았습니다.");
  }
};

const sendEnterMsg = () => {
  let content = document.querySelector(".content").value;
  if (socket.value) {
    let message = {
      messageType: "ENTER",
      senderId: 13,
      message: content,
      chatRoomId: 1,
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
</script>

<style scoped></style>
