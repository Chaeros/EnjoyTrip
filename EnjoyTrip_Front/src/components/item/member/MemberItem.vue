<template>
  <div class="member-box">
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
        @click="clickAddBtn"
      >
        <b>추가하기</b>
      </button>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";
import {
  addFriend,
  removeFriend,
  bringFriendList,
} from "@/api/friend/friend.js";
import {
  getLocalStorage,
  setLocalStorage,
} from "@/util/localstorage/localstorage.js";
import { defineEmits } from "vue";
const emit = defineEmits(["clickFriendAddBtn"]);
const { member } = defineProps({ member: Object });

const myFriends = ref();

const friendDto = ref({
  myId: getLocalStorage("userId"),
  friendId: member.id,
});

const getFriendList = () => {
  bringFriendList(
    getLocalStorage("userId"),
    (response) => {
      myFriends.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
};

console.log(friendDto.value);
const clickAddBtn = () => {
  addFriend(
    friendDto.value,
    (response) => {
      console.log(response.data);
      console.log(friendDto.value);
      emit("clickFriendAddBtn", friendDto.value);
    },
    (error) => {
      console.log(error);
    }
  );
};
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
