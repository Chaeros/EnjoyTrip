<template>
  <div class="member-edit-page">
    <div class="member-edit-box">
      <h1 class="member-edit-title"><b>기본정보</b></h1>
      <form @submit.prevent="updateMemberInfo">
        <div class="form-group">
          <label for="profileImage">사진</label>
          <div class="profile-image-wrapper">
            <img
              :src="profileImageUrl"
              alt="Profile Image"
              class="profile-image"
            />
            <input type="file" id="profileImage" @change="onImageChange" />
            <button class="delete-button" @click="removeImage">삭제</button>
          </div>
          <small>※ 사진은 자동으로 150x150 사이즈로 적용 됩니다.</small>
        </div>
        <div class="form-group">
          <label for="name">이름</label>
          <input
            type="text"
            id="name"
            v-model="memberInfo.name"
            class="form-control"
          />
        </div>
        <div class="form-group">
          <label for="password">비밀번호 변경</label>
          <input
            type="password"
            id="password"
            v-model="memberInfo.password"
            class="form-control"
          />
        </div>
        <div class="form-group">
          <label for="company">회사</label>
          <input
            type="text"
            id="company"
            v-model="memberInfo.company"
            class="form-control"
          />
        </div>
        <div class="form-group">
          <label for="department">부서</label>
          <input
            type="text"
            id="department"
            v-model="memberInfo.department"
            class="form-control"
          />
        </div>
        <div class="form-group">
          <label for="position">직무</label>
          <input
            type="text"
            id="position"
            v-model="memberInfo.position"
            class="form-control"
          />
        </div>
        <div class="form-group">
          <label for="rank">직위</label>
          <input
            type="text"
            id="rank"
            v-model="memberInfo.rank"
            class="form-control"
          />
        </div>
        <div class="form-group">
          <label for="email">이메일</label>
          <input
            type="email"
            id="email"
            v-model="memberInfo.email"
            class="form-control"
          />
        </div>
        <div class="form-group">
          <label for="externalEmail">외부 메일</label>
          <input
            type="email"
            id="externalEmail"
            v-model="memberInfo.externalEmail"
            class="form-control"
          />
        </div>
        <div class="form-group">
          <label for="cell">Cell.</label>
          <input
            type="tel"
            id="cell"
            v-model="memberInfo.cell"
            class="form-control"
          />
        </div>
        <div class="form-group">
          <label for="dir">Dir.</label>
          <input
            type="tel"
            id="dir"
            v-model="memberInfo.dir"
            class="form-control"
          />
        </div>
        <button type="submit" class="btn btn-primary">저장</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const memberInfo = ref({
  profileImage: "",
  name: "",
  password: "",
  company: "",
  department: "",
  position: "",
  rank: "",
  email: "",
  externalEmail: "",
  cell: "",
  dir: "",
});

const profileImageUrl = ref("@/assets/profile-placeholder.png");

const onImageChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      profileImageUrl.value = e.target.result;
    };
    reader.readAsDataURL(file);
  }
};

const removeImage = () => {
  profileImageUrl.value = "@/assets/profile-placeholder.png";
};

const updateMemberInfo = () => {
  // 회원 정보 저장 로직 추가
  console.log("회원 정보 저장:", memberInfo.value);
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap");

.member-edit-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
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
}

.profile-image {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
}

.delete-button {
  background-color: #dc3545;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
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
