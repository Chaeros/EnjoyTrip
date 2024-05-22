<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { getUserInfomation } from "@/api/member/member.js";

// 'default' 대신 '*'를 사용하여 CommonJS 형식으로 가져오기
import VueJwtDecode from "vue-jwt-decode";
import {
  getLocalStorage,
  setLocalStorage,
} from "@/util/localstorage/localstorage.js";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/store/member";

const memberStore = useMemberStore();
const accessToken = ref("");
const email = ref("");
const route = useRoute();
const { userInfo, isLogin } = storeToRefs(memberStore);

onMounted(() => {
  accessToken.value = route.query.accessToken;
  console.log(accessToken.value);

  if (accessToken.value) {
    try {
      // CommonJS 형식으로 가져온 모듈에서 함수를 호출
      const decodedToken = VueJwtDecode.decode(accessToken.value);
      email.value = decodedToken.email;
      console.log(decodedToken);
      getUserInfomation(
        email.value,
        (response) => {
          console.log(response.data);
          setLocalStorage("userId", response.data.id);
          setLocalStorage("access_token", accessToken.value);
          console.log(getLocalStorage("userId"));
          isLogin.value = true;
          userInfo.value = response.data;
        },
        (error) => {
          console.log(error);
        }
      );
    } catch (error) {
      console.error("Invalid token:", error);
    }
  }
});
</script>

<template>
  <div>
    <Header></Header>
    <div id="index_01">
      <div id="slide1">
        <ul>
          <li>
            <a href="#"
              ><img
                src="@/img/mainpage/mainpage_background1.jpg"
                class="main-slide-image"
                alt="슬라이드1"
            /></a>
          </li>
          <!-- <li>
            <a href="#"
              ><img
                src="@/img/mainpage/mainpage_background1.jpg"
                alt="슬라이드2"
            /></a>
          </li>
          <li>
            <a href="#"
              ><img
                src="@/img/mainpage/mainpage_background1.jpg"
                alt="슬라이드3"
            /></a>
          </li> -->
        </ul>
      </div>
    </div>
    <div class="welcome-text">
      <h1>
        <span>W</span>
        <span>E</span>
        <span>L</span>
        <span>C</span>
        <span>O</span>
        <span>M</span>
        <span>E</span>
        <span>!</span>
        <span>!</span>
      </h1>
    </div>
    <Footer></Footer>
  </div>
</template>

<style scoped>
/* 스타일은 필요에 따라 추가 */
#index_01 {
  width: 1200px;
  height: 100%;
  margin: 0 auto;
}

/* #header1 {
  height: 100px;
  border-bottom: 1px solid dimgrey;
  box-sizing: border-box;
  text-align: center;
  line-height: 100px;
  font-size: 1.5rem;
} */

/* #slide1 {
  height: 100%;
  overflow: hidden;
  border-bottom: 1px solid dimgrey;
}
#slide1 > ul > li {
  display: inline-block;
  font-size: 0;
} */
#slide1 > ul {
  font-size: 0;
  list-style: none; /* 점을 제거합니다 */
}
.main-slide-image {
  width: 1200px;
  height: 500px;
  border-radius: 15px;
  margin-top: 20px;
}

.welcome-text {
  width: 1200px;
  margin: 0 auto;
  padding-bottom: 50px;
  display: flex;
  justify-content: center;
}

h1 span {
  position: relative;
  top: 20px;
  display: inline-block;
  animation: bounce 0.3s ease infinite alternate;
  font-family: "Titan One", cursive;
  font-size: 80px;
  color: black;
  text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
    0 5px 0 #ccc, 0 6px 0 transparent, 0 7px 0 transparent, 0 8px 0 transparent,
    0 9px 0 transparent, 0 10px 10px rgba(0, 0, 0, 0.4);
}

h1 span:nth-child(2) {
  animation-delay: 0.1s;
}
h1 span:nth-child(3) {
  animation-delay: 0.2s;
}
h1 span:nth-child(4) {
  animation-delay: 0.3s;
}
h1 span:nth-child(5) {
  animation-delay: 0.4s;
}
h1 span:nth-child(6) {
  animation-delay: 0.5s;
}
h1 span:nth-child(7) {
  animation-delay: 0.6s;
}
h1 span:nth-child(8) {
  animation-delay: 0.7s;
}

@keyframes bounce {
  100% {
    top: -20px;
    text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
      0 5px 0 #ccc, 0 6px 0 #ccc, 0 7px 0 #ccc, 0 8px 0 #ccc, 0 9px 0 #ccc,
      0 50px 25px rgba(0, 0, 0, 0.2);
  }
}

/* latin-ext */
@font-face {
  font-family: "Titan One";
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/titanone/v15/mFTzWbsGxbbS_J5cQcjCmjgm6Es.woff2)
    format("woff2");
  unicode-range: U+0100-02AF, U+0304, U+0308, U+0329, U+1E00-1E9F, U+1EF2-1EFF,
    U+2020, U+20A0-20AB, U+20AD-20C0, U+2113, U+2C60-2C7F, U+A720-A7FF;
}
/* latin */
@font-face {
  font-family: "Titan One";
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/titanone/v15/mFTzWbsGxbbS_J5cQcjClDgm.woff2)
    format("woff2");
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA,
    U+02DC, U+0304, U+0308, U+0329, U+2000-206F, U+2074, U+20AC, U+2122, U+2191,
    U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
}
</style>
