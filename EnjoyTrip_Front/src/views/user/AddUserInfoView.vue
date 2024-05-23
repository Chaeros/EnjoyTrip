<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { updateUserInfomation } from "@/api/member/member.js";

const router = useRouter();
const route = useRoute();
const userInfo = ref({
  email: "",
  name: "",
  tel: "",
  city: "",
  confirmEmail: "",
});

function submitUserInfo() {
  console.log(userInfo.value);
  userInfo.value.confirmEmail = route.query.email;
  updateUserInfomation(
    userInfo.value,
    (response) => {
      alert("유저 수정이 완료되었습니다.");
      console.log(response);
      moveMainPage(response);
    },
    (error) => {
      alert("유저 수정이 실패했습니다.");
      console.log(error);
    }
  );
}

function moveMainPage() {
  router.put({ name: "searchattraction" });
}
</script>

<template>
  <div>
    <form>
      <!-- <div class="mb-3">
        <label for="exampleInputEmail1" class="form-label">Email 주소</label>
        <input
          type="email"
          class="form-control"
          id="email"
          v-model="userInfo.email"
        />
      </div> -->
      <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">이름</label>
        <input
          type="text"
          class="form-control"
          id="name"
          v-model="userInfo.name"
        />
      </div>
      <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">전화번호</label>
        <input
          type="text"
          class="form-control"
          id="tel"
          v-model="userInfo.tel"
        />
      </div>
      <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">도시</label>
        <input
          type="text"
          class="form-control"
          id="city"
          v-model="userInfo.city"
        />
      </div>
      <button
        type="submit"
        class="btn btn-primary"
        @click.prevent="submitUserInfo"
      >
        Submit
      </button>
    </form>
  </div>
</template>

<style scoped></style>
