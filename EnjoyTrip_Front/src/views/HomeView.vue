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
    <h1>Home</h1>
    <main>
      <nav>
        <ul>
          <li><router-link :to="{ name: 'home' }">Home</router-link></li>
          <li>
            <router-link :to="{ name: 'searchattraction' }"
              >Search Attraction</router-link
            >
          </li>
          <li><router-link :to="{ name: 'login' }">Login</router-link></li>
          <li>
            <router-link :to="{ name: 'normalSingUp' }"
              >Normal Sign Up</router-link
            >
          </li>
          <li>
            <router-link :to="{ name: 'reviewBoardWrite' }"
              >ReviewBoardWrite</router-link
            >
          </li>
          <li>
            <router-link :to="{ name: 'reviewBoardList' }"
              >ReviewBoardList</router-link
            >
          </li>
        </ul>
      </nav>
    </main>
    <div v-if="email">
      <p>Welcome, {{ email }}!</p>
    </div>
    <Footer></Footer>
  </div>
</template>

<style scoped>
/* 스타일은 필요에 따라 추가 */
</style>
