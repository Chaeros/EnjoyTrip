<script setup>
import { ref } from "vue";
import { normalLogin } from "@/api/member.js";
import { getCookie, setCookie } from "@/util/cookie";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/store/member";
import { storeToRefs } from "pinia";

const router = useRouter();
const isIdChecked = ref(false);
const loginInfo = ref({
  email: "",
  password: "",
});
const memberStore = useMemberStore();
const { userInfo, isLogin } = storeToRefs(memberStore);
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
        let accessToken = response.headers["authorization"];
        let refreshToken = response.headers["authorization-refresh"];
        console.log("refresh 토큰 :", refreshToken);
        console.log("access 토큰 :", accessToken);
        // setLocalStorage("access_token", accessToken); // 토큰 localStorage에 저장
        // axios.defaults.headers.common[
        //   "Authorization"
        // ] = `Bearer ${accessToken}`;
        console.log(getCookie("email"));
        remakeIdCheckCookie();
        console.log("!!", loginInfo.value.email);
        console.log("success");
        getUserInfo(loginInfo.value.email);
        router.push({ name: "home" });
        console.info(isLogin);
        isLogin.value = true;
      }
      // navigate('/');
    },
    (error) => {
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

<template>
  <div class="loginPage">
    <div class="loginBox">
      <h1 class="login-title"><b>ChanGon</b></h1>
      <form>
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

<style scoped>
.login-title {
  margin-bottom: 50px;
}
.loginPage {
  display: flex;
  justify-content: center; /* Horizontally center the content */
  align-items: center; /* Vertically center the content */
  height: 100vh;
}
.loginBox {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.form-control {
  width: 400px;
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
  width: 400px;
  height: 50px;
  font-weight: bold;
}
.user-management {
  padding-top: 10px;
  text-align: center;
}
.user-management a {
  cursor: pointer; /* 마우스 모양을 선택 모양으로 변경 */
}
.hr-with-text {
  display: flex;
  align-items: center;
}
.hr-with-text hr {
  flex-grow: 1;
  border: 1px solid gray;
}
.hr-with-text .text {
  margin: 10px 10px;
  font-size: 12px;
  color: gray;
}
.social-login-btn {
  width: 400px;
  height: 55px;
  cursor: pointer;
}
</style>
