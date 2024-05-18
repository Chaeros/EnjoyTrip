<template>
  <div id="app">
    <ChatComponent v-if="isLogin" />
    <RouterView />
  </div>
</template>

<script>
import { ref, watch, onMounted } from "vue";
import ChatComponent from "@/components/chat/ChatView.vue";
import { getCookie } from "@/util/cookie";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/store/member";

export default {
  name: "App",
  components: {
    ChatComponent,
  },
  setup() {
    const memberStore = useMemberStore();
    const { userInfo, isLogin } = storeToRefs(memberStore);

    const socket = ref(null);

    const checkLogin = () => {
      const email = getCookie("email");
      if (email) {
        console.log(email);
        console.log(isLogin.value);
      } else {
        console.log("test");
      }
    };

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

    watch(isLogin, (newVal) => {
      if (newVal) {
        connect();
      }
    });

    onMounted(() => {
      checkLogin();
    });

    return {
      isLogin,
      socket,
    };
  },
};
</script>

<style>
body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    "Helvetica Neue", Arial, "Noto Sans", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
