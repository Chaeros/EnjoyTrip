<template>
  <div class="sign-up-page">
    <div class="sign-up-box">
      <h1 class="sign-up-title"><b>회원 가입</b></h1>
      <form class="input-form">
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
            class="password-notcorrect"
            v-if="isPasswordLengthCorrect(signUpInfo.password) === false"
          >
            X 8자 이상 32자 이하 입력 (공백 제외)
          </p>
          <p
            class="password-iscorrect"
            v-if="isPasswordLengthCorrect(signUpInfo.password)"
          >
            V 8자 이상 32자 이하 입력 (공백 제외)
          </p>
        </div>
        <div class="password-strength-message">
          <p
            class="password-strength-notcorrect"
            v-if="isPasswordStrengthStrong(signUpInfo.password) === false"
          >
            X 영문/특수문자 각각 1개 이상 포함
          </p>
          <p
            class="password-strength-iscorrect"
            v-if="isPasswordStrengthStrong(signUpInfo.password)"
          >
            V 영문/특수문자 각각 1개 이상 포함
          </p>
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
        <div>
          <p
            v-if="signUpInfo.password !== signUpInfo.passwordConfirm"
            class="password-confirm-notsame"
          >
            비밀번호가 일치하지 않습니다
          </p>
          <p
            v-if="signUpInfo.password === signUpInfo.passwordConfirm"
            class="password-confirm-issame"
          >
            비밀번호가 일치합니다!
          </p>
        </div>
        <div class="mb-3">
          <input
            type="text"
            class="form-control"
            id="exampleInputNickname"
            aria-describedby="emailHelp"
            placeholder="닉네임"
            v-model="signUpInfo.nickname"
          />
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
import { normalSignUp } from "@/api/member/member.js";
import { useRoute, useRouter } from "vue-router";
import Swal from "sweetalert2";
const router = useRouter();
const signUpInfo = ref({
  email: "",
  password: "",
  passwordConfirm: "",
  nickname: "",
});

const isPasswordLengthCorrectValue = ref(false);
const isPasswordStrengthCorrectValue = ref(false);

const clickNormalSignUp = () => {
  if (isPasswordLengthCorrectValue.value === false) {
    Swal.fire({
      icon: "error",
      title: "비밀번호 길이가 짧아요!!",
    });
    return;
  }
  if (isPasswordStrengthCorrectValue.value === false) {
    Swal.fire({
      icon: "error",
      title: "비밀번호 강도가 약해요!!",
    });
    return;
  }
  if (signUpInfo.value.password != signUpInfo.value.passwordConfirm) {
    Swal.fire({
      icon: "error",
      title: "서로 다른 비밀번호를 입력했어요!!",
    });
    return;
  }
  normalSignUp(
    signUpInfo.value,
    (response) => {
      Swal.fire({
        title: "반가워요!!",
        text: "찬곤이가 당신과 함께하는 여행을 기다리고 있어요!!",
        imageUrl:
          "https://github.com/Chaeros/Problem_Solved/assets/91451735/7fe87e5b-e33a-4111-bfec-1d7831f16604",
        // imageWidth: 400,
        // imageHeight: 200,
        imageAlt: "Custom image",
      });
      router.push({ name: "login" });
    },
    (error) => {
      console.log(error);
      Swal.fire({
        icon: "error",
        title: error.response.data,
      });
    }
  );
};

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
  if (isPasswordLengthCorrect(signUpInfo.value.password) === false) {
    isPasswordLengthCorrectValue.value = false;
  } else {
    isPasswordLengthCorrectValue.value = true;
  }
};

const checkPasswordStrength = () => {
  if (isPasswordStrengthStrong(signUpInfo.value.password) === false) {
    isPasswordStrengthCorrectValue.value = false;
  } else {
    isPasswordStrengthCorrectValue.value = true;
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap");

.sign-up-page {
  display: flex;
  justify-content: center; /* 수평 중앙 정렬 */
  align-items: center; /* 수직 중앙 정렬 */
  height: 100vh; /* 전체 화면 높이 */
  background-image: url("@/img/coldragon.png"); /* 배경 이미지 추가 */
  background-size: cover; /* 배경 이미지 크기 */
  background-position: center; /* 배경 이미지 위치 */
  background-repeat: no-repeat; /* 배경 이미지 반복 제거 */
}

.sign-up-box {
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

.sign-up-title {
  margin-bottom: 30px;
  font-family: "Noto Sans KR", sans-serif;
  font-weight: 700;
}

.input-form {
  width: 100%;
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

.normal-sign-up-btn {
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

.normal-sign-up-btn:hover {
  background-color: #0056b3; /* 호버 시 버튼 배경색 */
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

.password-length-message,
.password-strength-message {
  display: flex;
  color: red;
}

.password-confirm-notsame,
.password-notcorrect,
.password-strength-notcorrect {
  color: red;
}

.password-confirm-issame,
.password-iscorrect,
.password-strength-iscorrect {
  padding: 0 10px;
  color: green;
}

.password-confirm-issame p:last-child {
  color: green;
}
</style>
