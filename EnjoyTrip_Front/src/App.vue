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
import { useWebSocketChatStore } from "@/store/chat/web-socket-chat";

export default {
  name: "App",
  components: {
    ChatComponent,
  },
  setup() {
    const memberStore = useMemberStore();
    const webSocketChatStore = useWebSocketChatStore();
    const { userInfo, isLogin } = storeToRefs(memberStore);
    const { socket } = storeToRefs(webSocketChatStore);
    const { connect } = webSocketChatStore;

    const checkLogin = () => {
      const email = getCookie("email");
      if (email) {
        console.log(email);
        console.log(isLogin.value);
      } else {
        console.log("test");
      }
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
