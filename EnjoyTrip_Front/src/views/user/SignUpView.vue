<template>
  <div class="sign-up-page">
    <div class="sign-up-box">
      <h1 class="sign-up-title"><b>회원 가입</b></h1>
      <form>
        <div class="mb-3">
          <input
            type="email"
            class="form-control"
            id="exampleInputEmail1"
            aria-describedby="emailHelp"
            placeholder="이메일"
            v-model="signUpInfo.email"
          />
        </div>
        <div class="mb-3" style="position: relative">
          <input
            type="password"
            class="form-control"
            id="exampleInputPassword1"
            placeholder="비밀번호"
            v-model="signUpInfo.password"
            @input="handlePasswordInput"
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
        <!-- 글자 수에 따라 메시지 표시 -->
        <div class="password-length-message">
          <p
            class="password-iscorrect"
            v-if="isPasswordLengthCorrect(signUpInfo.password) === false"
          >
            X
          </p>
          <p
            class="password-iscorrect"
            v-if="isPasswordLengthCorrect(signUpInfo.password)"
          >
            V
          </p>
          8자 이상 32자 이하 입력 (공백 제외)
        </div>
        <div class="password-strength-message">
          <p
            class="password-strength-iscorrect"
            v-if="isPasswordStrengthStrong(signUpInfo.password) === false"
          >
            X
          </p>
          <p
            class="password-strength-iscorrect"
            v-if="isPasswordStrengthStrong(signUpInfo.password)"
          >
            V
          </p>
          영문/특수문자 각각 1개 이상 포함
        </div>

        <div class="mb-3" style="position: relative">
          <input
            type="password"
            class="form-control"
            id="confirmInputPassword"
            placeholder="비밀번호 확인"
            v-model="signUpInfo.passwordConfirm"
          />
          <!-- 눈모양 아이콘 -->
          <img
            src="@/img/eye_icon.png"
            alt="Show Password"
            title="Show Password"
            class="eye_icon"
            @mousedown="showConfirmPassword(true)"
            @mouseup="showConfirmPassword(false)"
          />
        </div>
        <div class="password-confirm-issame">
          <p v-if="signUpInfo.password !== signUpInfo.passwordConfirm">
            비밀번호가 일치하지 않습니다
          </p>
          <p v-if="signUpInfo.password === signUpInfo.passwordConfirm">
            &nbsp;
          </p>
        </div>
        <button
          type="submit"
          class="btn btn-primary normal-sign-up-btn"
          @click.prevent="clickNormalSignUp"
        >
          가입하기
        </button>
        <div class="hr-with-text">
          <hr />
          <div class="text">간편 회원가입</div>
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
import {
  isPasswordLengthCorrect,
  isPasswordStrengthStrong,
} from "@/util/password-validator";
import { normalSignUp } from "@/api/member.js";

const signUpInfo = ref({
  email: "",
  password: "",
  passwordConfirm: "",
});

const clickNormalSignUp = () => {};

const handlePasswordInput = () => {
  // 비밀번호 입력 시 호출되는 핸들러
  checkPasswordLength(); // 비밀번호 길이 유효성 검사
  checkPasswordStrength(); // 특수문자와 일반 문자 사용 여부 검사
};

const showPassword = (show) => {
  const passwordInput = document.getElementById("exampleInputPassword1");
  if (show) {
    passwordInput.type = "text"; // Show password
  } else {
    passwordInput.type = "password"; // Hide password
  }
};

const showConfirmPassword = (show) => {
  const passwordInput = document.getElementById("confirmInputPassword");
  if (show) {
    passwordInput.type = "text"; // Show password
  } else {
    passwordInput.type = "password"; // Hide password
  }
};

const checkPasswordLength = () => {
  const passwordLengthMessage = document.querySelector(
    ".password-length-message"
  );
  if (isPasswordLengthCorrect(signUpInfo.value.password) === false) {
    // passwordLengthMessage.style.display = "block";
    passwordLengthMessage.style.color = "red"; // 8글자 미만일 때 빨간색
  } else {
    // passwordLengthMessage.style.display = "none";
    passwordLengthMessage.style.color = "green"; // 8글자 미만일 때 빨간색
  }
};

const checkPasswordStrength = () => {
  const passwordStrengthMessage = document.querySelector(
    ".password-strength-message"
  );
  if (isPasswordStrengthStrong(signUpInfo.value.password) === false) {
    passwordStrengthMessage.style.color = "red";
  } else {
    passwordStrengthMessage.style.color = "green";
  }
};
</script>

<style scoped>
.sign-up-page {
  display: flex;
  justify-content: center; /* Horizontally center the content */
  align-items: center; /* Vertically center the content */
  height: 100vh;
}
.sign-up-box {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.sign-up-title {
  padding: 30px;
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
.normal-sign-up-btn {
  width: 400px;
  height: 50px;
  font-weight: bold;
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
.password-length-message {
  display: flex;
  color: red;
}
.password-strength-message {
  display: flex;
  color: red;
}
.password-iscorrect {
  padding: 0 10px;
}
.password-strength-iscorrect {
  padding: 0 10px;
}
</style>
