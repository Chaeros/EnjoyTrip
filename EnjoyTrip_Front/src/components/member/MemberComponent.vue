<template>
  <div class="member-edit-page" v-if="userInfo">
    <div class="member-edit-box">
      <h1 class="member-edit-title"><b>기본정보</b></h1>
      <form>
        <div class="form-group">
          <label class="label-text" for="profileImage">사진</label>
          <div class="profile-image-wrapper">
            <img
              v-if="userInfo.image == null"
              src="@/img/member/default_img.jpg"
              class="profile-image"
            />
            <img
              v-else
              :src="memberInfo.image"
              alt="Profile Image"
              class="profile-image"
            />
            <div class="profile-image-btn">
              <input type="file" id="profileImage" @change="onImageChange" />
              <button class="default-profile-button" @click="removeImage">
                기본프로필 변경
              </button>
            </div>
          </div>
          <small>※ 사진은 자동으로 150x150 사이즈로 적용 됩니다.</small>
        </div>
        <div class="form-group">
          <label class="label-text" for="name">닉네임</label>
          <input
            type="text"
            id="name"
            v-model="memberInfo.nickname"
            class="form-control"
            v-if="mode === `Edit`"
          />
          <div v-if="mode === `Read`">{{ memberInfo.nickname }}</div>
        </div>
        <div class="form-group">
          <label class="label-text" for="city">사는 도시</label>
          <input
            type="text"
            id="city"
            v-model="memberInfo.city"
            class="form-control"
            v-if="mode === `Edit`"
          />
          <div v-if="mode === `Read`">
            <template v-if="memberInfo.city == `` || memberInfo.city == null">
              미입력 상태입니다.
            </template>
            <template v-else>
              {{ memberInfo.city }}
            </template>
          </div>
        </div>
        <div class="form-group">
          <label class="label-text" for="email">이메일</label>
          <div>{{ memberInfo.email }}</div>
        </div>
        <template
          v-if="userInfo.socialType != null && userInfo.socialType != ``"
        >
          <div class="form-group">
            <label class="label-text" for="social-type">소셜타입</label>
            <input
              type="text"
              id="social-type"
              v-model="memberInfo.socialType"
              class="form-control"
            />
          </div>
        </template>

        <template v-if="mode == `Read`">
          <button
            type="button"
            class="btn btn-dark submitBtn"
            @click="setUpdateMode"
          >
            수정하기
          </button></template
        >
        <template v-if="mode == `Edit`">
          <button
            type="button"
            class="btn btn-dark submitBtn"
            @click="setReadMode"
          >
            취소
          </button>
          <button
            type="button"
            class="btn btn-dark submitBtn"
            @click="resetMemberInfo"
          >
            초기화
          </button>
          <button
            type="button"
            class="btn btn-dark submitBtn"
            @click="updateMemberInfo"
          >
            수정
          </button>
        </template>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/store/member.js";
import { updateMember } from "@/api/member/member.js";
import axios from "axios";
const { VITE_VUE_API_URL, VITE_VUE_IMAGE_SERVER_URL } = import.meta.env;
const memberStore = useMemberStore();
const { userInfo, isLogin } = storeToRefs(memberStore);

const mode = ref("Read");

console.log(userInfo.value);

const memberInfo = ref({
  id: userInfo.value.id,
  nickname: userInfo.value.nickname,
  image: userInfo.value.image,
  city: userInfo.value.city,
  email: userInfo.value.email,
  socialType: userInfo.value.socialType,
});

console.log(memberInfo);

const setUpdateMode = () => {
  mode.value = "Edit";
};

const setReadMode = () => {
  mode.value = "Read";
  resetMemberInfo();
};

const resetMemberInfo = () => {
  (memberInfo.value.nickname = userInfo.value.nickname),
    (memberInfo.value.city = userInfo.value.city);
};

const profileImageUrl = ref("@/img/coldragon.png");

const onImageChange = async (event) => {
  const file = event.target.files[0];
  if (file) {
    // fileName.value = file.name;
    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await axios.post(
        VITE_VUE_IMAGE_SERVER_URL + "/image/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      console.log("File uploaded successfully:", response.data);
      userInfo.value.image = VITE_VUE_IMAGE_SERVER_URL + response.data.url;
      memberInfo.value.image = VITE_VUE_IMAGE_SERVER_URL + response.data.url;
      console.log(VITE_VUE_IMAGE_SERVER_URL + response.data.url);
      updateMember(
        memberInfo.value,
        (response) => {
          console.log(response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    } catch (error) {
      console.error("Error uploading file:", error);
    }
  }
};

// const removeImage = () => {
//   //   profileImageUrl.value = "@/assets/profile-placeholder.png";
//   memberInfo.value.image = null;
//   updateMember(
//     memberInfo.value,
//     (response) => {
//       console.log(response.data);
//     },
//     (error) => {
//       console.log(error);
//     }
//   );
// };

const updateMemberInfo = () => {
  // 회원 정보 저장 로직 추가
  console.log("회원 정보 저장:", memberInfo.value);
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap");

.submitBtn {
  margin-right: 10px;
}
.member-edit-page {
  display: flex;
  justify-content: center;
  align-items: center;
  /* height: 100vh; */
  background-color: #f8f9fa;
  font-family: "Noto Sans KR", sans-serif;
  overflow-y: auto;
}

.member-edit-box {
  background-color: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  /* max-width: 500px; */
}

.label-text {
  font-size: 20px;
}

.member-edit-title {
  margin-bottom: 30px;
  font-family: "Noto Sans KR", sans-serif;
  font-weight: 700;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group small {
  display: block;
  margin-top: 5px;
  color: #6c757d;
}

.form-control {
  width: 100%;
  padding: 10px;
  border: 1px solid #ced4da;
  border-radius: 5px;
}

.profile-image-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
  border-radius: 50%;
}

.profile-image {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
}
.profile-image-btn {
  display: flex;
  flex-direction: column;
}

.default-profile-button {
  background-color: #dc3545;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  width: 200px;
  margin: 5px 0;
}

.delete-button:hover {
  background-color: #c82333;
}

.btn {
  display: inline-block;
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}

.btn-primary:hover {
  background-color: #0056b3;
}
</style>
