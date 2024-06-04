<template>
  <div class="loginPage">
    <div class="loginBox">
      <h1 class="login-title"><b>ChanGon</b></h1>
      <form class="input-form">
        <div class="mb-3">
          <input
            type="email"
            class="form-control"
            id="exampleInputEmail1"
            aria-describedby="emailHelp"
            placeholder="이메일"
            v-model="loginInfo.email"
          />
        </div>
        <div class="mb-3" style="position: relative">
          <input
            type="password"
            class="form-control"
            id="exampleInputPassword1"
            placeholder="비밀번호"
            v-model="loginInfo.password"
          />
          <!-- 눈모양 아이콘 -->
          <img
            src="@/img/eye_icon.png"
            alt="Show Password"
            title="Show Password"
            class="eye_icon"
            @mousedown="showPassword(true)"
            @mouseup="showPassword(false)"
          />
        </div>
        <div class="mb-3 form-check">
          <input
            type="checkbox"
            class="form-check-input"
            id="exampleCheck1"
            v-model="isIdChecked"
          />
          <label class="form-check-label" for="exampleCheck1"
            >아이디 저장</label
          >
        </div>
        <button
          type="submit"
          class="btn btn-primary normal-login-btn"
          @click.prevent="clickNormalLogin"
        >
          로그인
        </button>
        <div class="user-management">
          <a>아이디(이메일)찾기</a> | <a>비밀번호 찾기</a> |
          <a @click.prevent="navigateToSignUp">회원가입</a>
        </div>
        <div class="hr-with-text">
          <hr />
          <div class="text">간편 로그인</div>
          <hr />
        </div>
      </form>
      <img
        class="social-login-btn"
        @click="clickKakaoLogin"
        src="@/img/kakao_login_medium_wide.png"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { normalLogin } from "@/api/member/member.js";
import { getCookie, setCookie } from "@/util/cookie";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/store/member";
import { getUserInfomation } from "@/api/member/member.js";
import { useFriendManagementStore } from "@/store/friend-management/friend-management.js";
import { storeToRefs } from "pinia";
import {
  setLocalStorage,
  getLocalStorage,
} from "@/util/localstorage/localstorage.js";
import Swal from "sweetalert2";

const router = useRouter();
const isIdChecked = ref(false);
const loginInfo = ref({
  email: "",
  password: "",
});
const memberStore = useMemberStore();
const friendManagementStore = useFriendManagementStore();
const { userInfo, isLogin } = storeToRefs(memberStore);
const { bringMyFriendsList } = friendManagementStore;
const { getUserInfo } = memberStore;

const clickKakaoLogin = () => {
  window.location.href = `http://localhost:8080/oauth2/authorization/kakao`;
};

function isSavedId() {
  if (getCookie("idChecked") === "check") {
    loginInfo.value.email = getCookie("email");
    isIdChecked.value = true;
  }
}

function remakeIdCheckCookie() {
  if (isIdChecked.value === true) {
    setCookie("idChecked", "check", "7d");
  } else {
    setCookie("idChecked", "unCheck", "7d");
  }
}

const clickNormalLogin = () => {
  const headers = {
    "Content-Type": "application/json",
  };
  console.log(loginInfo.value.email);
  normalLogin(
    loginInfo.value,
    (response) => {
      if (response.status === 200) {
        console.log("일반 로그인 성공", response.data);
        let accessToken = response.headers["Authorization"];
        let refreshToken = response.headers["Authorization-refresh"];
        console.log("access 토큰 :", accessToken);
        console.log("refresh 토큰 :", refreshToken);
        console.log("access 토큰 by 쿠키:", getCookie("accessToken"));
        console.log("refresh 토큰 by 쿠키:", getCookie("refreshToken"));
        // 토큰 localStorage에 저장
        setLocalStorage("access_token", getCookie("accessToken"));
        setLocalStorage("refresh_token", getCookie("refreshToken"));
        // setLocalStorage("myId",myId)
        //axios.defaults.headers.common[
        //  "Authorization"
        //] = `Bearer ${accessToken}`;
        console.log(getCookie("email"));
        remakeIdCheckCookie();
        console.log("!!", loginInfo.value.email);
        console.log("success");
        getUserInfo(loginInfo.value.email);
        router.push({ name: "home" });
        console.info(isLogin);
        console.log(getCookie("accessToken"));
        isLogin.value = true;
        getUserInfomation(
          loginInfo.value.email,
          (response2) => {
            setLocalStorage("userId", response2.data.id);
            console.log("##user정보 받아오기", getLocalStorage("userId"));
          },
          (error) => {
            console.log(error);
          }
        );
        bringMyFriendsList();
      }
    },
    (error) => {
      Swal.fire({
        icon: "error",
        title: "아이디 또는 패스워드가 잘못되었습니다!",
      });
      console.log(error);
    }
  );
};

const navigateToSignUp = () => {
  router.push({ name: "normalSingUp" });
};

const showPassword = (show) => {
  const passwordInput = document.getElementById("exampleInputPassword1");
  if (show) {
    passwordInput.type = "text"; // Show password
  } else {
    passwordInput.type = "password"; // Hide password
  }
};
isSavedId();
console.log(isIdChecked.value);
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap");

.loginPage {
  display: flex;
  justify-content: center; /* 수평 중앙 정렬 */
  align-items: center; /* 수직 중앙 정렬 */
  height: 100vh; /* 전체 화면 높이 */
  background-size: cover; /* 배경 이미지 크기 */
  background-position: center; /* 배경 이미지 위치 */
  background-repeat: no-repeat; /* 배경 이미지 반복 제거 */
}

.loginBox {
  background-color: white;
  padding: 40px; /* 내부 여백 */
  border-radius: 10px; /* 둥근 모서리 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 500px; /* 최대 너비 */
}

.input-form {
  width: 100%;
}

.login-title {
  margin-bottom: 30px;
  font-family: "Noto Sans KR", sans-serif;
  font-weight: 700;
}

.form-control {
  width: 100%; /* 입력창 너비 */
  margin-bottom: 15px; /* 입력창 간격 */
}

.eye_icon {
  width: 25px;
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}

.normal-login-btn {
  width: 100%; /* 버튼 너비 */
  height: 50px; /* 버튼 높이 */
  font-weight: bold;
  margin-bottom: 20px; /* 버튼 간격 */
  background-color: #007bff; /* 버튼 배경색 */
  border: none; /* 버튼 테두리 제거 */
  border-radius: 5px; /* 버튼 둥근 모서리 */
  color: white; /* 버튼 텍스트 색상 */
  cursor: pointer;
  transition: background-color 0.3s;
}

.normal-login-btn:hover {
  background-color: #0056b3; /* 호버 시 버튼 배경색 */
}

.user-management {
  text-align: center;
  margin-bottom: 20px; /* 간격 조정 */
}

.user-management a {
  cursor: pointer; /* 마우스 모양을 선택 모양으로 변경 */
  margin: 0 5px; /* 링크 간격 */
  color: #007bff; /* 링크 색상 */
  text-decoration: none; /* 밑줄 제거 */
  transition: color 0.3s;
}

.user-management a:hover {
  color: #0056b3; /* 호버 시 링크 색상 */
}

.hr-with-text {
  display: flex;
  align-items: center;
  width: 100%;
  margin: 20px 0;
}

.hr-with-text hr {
  flex-grow: 1;
  border: none;
  border-top: 1px solid #ccc;
}

.hr-with-text .text {
  margin: 0 10px;
  font-size: 12px;
  color: gray;
}

.social-login-btn {
  width: 100%;
  cursor: pointer;
}
</style>
