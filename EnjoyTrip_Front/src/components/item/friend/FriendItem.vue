<template>
  <div class="member-box" v-if="member !== null">
    <div class="member-info">
      <template v-if="member.image === null">
        <img
          class="member-info-profile-img"
          src="@/img/member/default_img.jpg"
        />
      </template>
      <template v-else>
        <img class="member-info-profile-img" :src="member.image" />
      </template>
      <div class="member-info-nickname">{{ member.nickname }}</div>
    </div>
    <div class="add-btn click-add-friend">
      <button
        type="button"
        class="btn btn-outline-primary click-add-friend-btn"
      >
        <b>삭제히기</b>
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { getUserInfomationById } from "@/api/member/member.js";
import { getLocalStorage } from "@/util/localstorage/localstorage.js";
const { friend } = defineProps({ friend: Object });

const myFriends = ref();
const member = ref(null);

const getUserInfomation = () => {
  console.log(friend);
  getUserInfomationById(
    friend.friendId,
    (response) => {
      console.log(response.data);
      member.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
};
getUserInfomation();
</script>

<style>
.member-box {
  display: flex;
  margin: 10px 0;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  align-items: center;
  justify-content: space-between;
}
.member-info {
  display: flex;
  margin: 10px;
  align-content: center;
  height: 50px;
}
.member-info-nickname {
  font-size: 25px;
  margin: 10px;
}
.member-info-profile-img {
  border-radius: 100%;
  width: 50px;
}
.click-add-friend {
  margin: 10px;
}
.click-add-friend-btn {
  font-weight: bold;
}
</style>
