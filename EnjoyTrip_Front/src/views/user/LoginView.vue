<script setup>
import { ref } from "vue";
import { normalLogin } from "@/api/member.js";
const clickKakaoLogin = () => {
  window.location.href = `http://localhost:8080/oauth2/authorization/kakao`;
};

const loginInfo = ref({
  email: "",
  password: "",
});

const clickNormalLogin = () => {
  console.log(loginInfo.value);
  normalLogin(
    loginInfo.value,
    (response) => {
      if (response.status === 200) {
        let accessToken = response.headers["authorization"];
        let refreshToken = response.headers["authorization-refresh"];
        console.log("refresh 토큰 :", refreshToken);
        console.log("access 토큰 :", accessToken);
        setLocalStorage("access_token", accessToken); // 토큰 localStorage에 저장
        axios.defaults.headers.common[
          "Authorization"
        ] = `Bearer ${accessToken}`;
      }
      // navigate('/');
    },
    ({ error }) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div>
    <form>
      <div class="mb-3">
        <label for="exampleInputEmail1" class="form-label">Email address</label>
        <input
          type="email"
          class="form-control"
          id="exampleInputEmail1"
          aria-describedby="emailHelp"
          v-model="loginInfo.email"
        />
        <div id="emailHelp" class="form-text">
          We'll never share your email with anyone else.
        </div>
      </div>
      <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">Password</label>
        <input
          type="password"
          class="form-control"
          id="exampleInputPassword1"
          v-model="loginInfo.password"
        />
      </div>
      <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="exampleCheck1" />
        <label class="form-check-label" for="exampleCheck1">Check me out</label>
      </div>
      <button
        type="submit"
        class="btn btn-primary"
        @click.prevent="clickNormalLogin"
      >
        로그인
      </button>
    </form>
    <button @click="clickKakaoLogin">카카오 로그인</button>
  </div>
</template>

<style scoped></style>
