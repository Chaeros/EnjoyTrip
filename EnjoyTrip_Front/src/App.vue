<template>
  <div id="app">
    <ChatComponent v-if="isLogin" />
    <RouterView />
    <button @click="scrollToTop" class="scroll-to-top" v-if="showScrollButton">
      ↑
    </button>
  </div>
</template>

<script>
import { ref, watch, onMounted, onBeforeUnmount } from "vue";
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

    const showScrollButton = ref(false);

    const checkLogin = () => {
      const email = getCookie("email");
      if (email) {
        console.log(email);
        console.log(isLogin.value);
      } else {
        console.log("test");
      }
    };

    const handleScroll = () => {
      showScrollButton.value = window.scrollY > 200;
    };

    const scrollToTop = () => {
      window.scrollTo({ top: 0, behavior: "smooth" });
    };

    watch(isLogin, (newVal) => {
      if (newVal === true) {
        connect();
      }
    });

    onMounted(() => {
      checkLogin();
      window.addEventListener("scroll", handleScroll);
    });

    onBeforeUnmount(() => {
      window.removeEventListener("scroll", handleScroll);
    });

    return {
      isLogin,
      socket,
      scrollToTop,
      showScrollButton,
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

.scroll-to-top {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 50px;
  height: 50px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  cursor: pointer;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  transition: opacity 0.3s ease-in-out;
  opacity: 0;
  visibility: hidden;
}

.scroll-to-top.show {
  opacity: 1;
  visibility: visible;
}
</style>
