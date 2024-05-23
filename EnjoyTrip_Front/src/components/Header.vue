<template>
  <div class="header-wrap">
    <div class="header">
      <div class="left-side-header">
        <h3 class="header-logo" @click="clickTitle">CHANGON</h3>
        <a class="header-link" @click="clickAttraction">관광지찾기</a>
        <a class="header-link" @click="clickCummunity">커뮤니티</a>
        <a class="header-link">공지사항</a>
      </div>
      <div class="right-side-header">
        <div class="search-box">
          <input
            class="form-control me-2 search-text"
            type="search"
            placeholder="Search"
            aria-label="Search"
          />
          <img
            src="@/img/search_icon.png"
            alt="Show Password"
            title="Show Password"
            class="search_icon"
            @mousedown="showPassword(true)"
            @mouseup="showPassword(false)"
          />
        </div>
        <template v-if="isLogin === false">
          <button
            type="button"
            @click="clickLogin"
            class="btn btn-outline-dark header-btn"
          >
            로그인
          </button>
          <button
            type="button"
            @click="clickSignUp"
            class="btn btn-outline-dark header-btn"
          >
            회원가입
          </button>
        </template>
        <template v-else>
          <button
            type="button"
            @click="clickLogOut"
            class="btn btn-outline-dark header-btn"
          >
            로그아웃
          </button>
          <button
            type="button"
            @click="clickMyPage"
            class="btn btn-outline-dark header-btn"
          >
            내 정보
          </button>
          <p class="user-id">{{ displayMember.id }}</p>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/store/member";
import { useWebSocketChatStore } from "@/store/chat/web-socket-chat.js";
import { storeToRefs } from "pinia";
import { ref } from "vue";

const memberStore = useMemberStore();
const webSocketChatStore = useWebSocketChatStore();
const { userInfo, isLogin } = storeToRefs(memberStore);
const { sendEscapeMsg } = webSocketChatStore;
const router = useRouter();

const clickLogin = () => {
  router.push({ name: "login" });
};
const clickSignUp = () => {
  router.push({ name: "normalSingUp" });
};
const clickTitle = () => {
  router.push({ name: "home" });
};
const clickAttraction = () => {
  router.push({ name: "searchattraction" });
};
const clickCummunity = () => {
  router.push({ name: "reviewBoardList" });
};
const clickLogOut = () => {
  userInfo.value = null;
  isLogin.value = false;
  sendEscapeMsg();
};
const clickMyPage = () => {
  router.push({ name: "myPage" });
};
const displayMember = ref({
  id: "",
  img: "",
});
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap");

.header-wrap {
  height: 80px;
  align-content: center;
  background-color: #fff;
  border-bottom: 1px solid #eaeaea;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 auto;
  width: 90%;
  max-width: 1200px;
  /* padding: 0 20px; */
}

.header-logo {
  font-size: 30px;
  font-family: "Noto Sans KR", sans-serif;
  font-weight: bold;
  cursor: pointer;
  transition: color 0.3s;
}

.header-logo:hover {
  color: #007bff;
}

.header-link {
  margin: 0 10px;
  font-family: "Noto Sans KR", sans-serif;
  color: #333;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s;
}

.header-link:hover {
  color: #007bff;
  cursor: pointer;
}

.left-side-header {
  display: flex;
  align-items: center;
}

.right-side-header {
  display: flex;
  align-items: center;
}

.search-box {
  position: relative;
}

.search-text {
  width: 300px;
  padding: 8px 40px 8px 12px;
  border: 1px solid #ccc;
  border-radius: 20px;
  transition: border-color 0.3s;
}

.search-text:focus {
  border-color: #007bff;
  outline: none;
}

.search_icon {
  width: 20px;
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}

.header-btn {
  width: auto;
  padding: 8px 10px;
  margin: 0 5px;
  border-radius: 10px;
  text-align: center;
  /* text-decoration: none; */
  font-family: "Noto Sans KR", sans-serif;
  cursor: pointer;
  transition: background-color 0.3s;
  /* font-weight: bold; */
}

.header-btn:hover {
  /* background-color: #0056b3; */
}

.user-id {
  margin: 0 10px;
  font-weight: bold;
  color: #333;
}
</style>
