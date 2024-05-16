<template>
  <div>
    <Header></Header>
    <div class="mypage-wrap">
      <div class="my-firends-box"></div>
      <button @click="openModal">내 친구</button>
    </div>
    <Footer></Footer>
  </div>

  <Modal :isVisible="showModal" title="친구 관리" @close="closeModal">
    <div class="modal-box">
      <div class="left-search-friend-box">
        <div class="search-box">
          <input
            class="form-control me-2 search-text"
            type="search"
            placeholder="친구의 닉네임을 입력하세요!"
            aria-label="Search"
            v-model="keyword"
            @keyup.enter="callMemberByKeyword"
          />
          <img
            src="@/img/search_icon.png"
            class="search_icon"
            @click="callMemberByKeyword"
          />
        </div>
        <template v-for="member in members" :key="member.id">
          <MemberItem
            :member="member"
            @click-friend-addBtn="clickFriendAddBtn"
          ></MemberItem>
        </template>
      </div>
      <div class="right-current-my-friend-box">
        <div class="myfriend-sub-title">현재 내 친구 목록</div>
        <div calss="my-friend-list-box">
          <template v-for="friend in myFriends" :key="friend.id">
            <FriendItem
              :friend="friend"
              @click-friend-remove-btn="clickFriendRemoveBtn"
            ></FriendItem>
          </template>
        </div>
      </div>
    </div>
  </Modal>
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import Modal from "@/modal/FriendManagementModal.vue";
import MemberItem from "@/components/item/member/MemberItem.vue";
import FriendItem from "@/components/item/friend/FriendItem.vue";
import {
  getUserInfomationById,
  searchMemberByKeyNicknameKeword,
} from "@/api/member/member.js";
import { bringFriendList } from "@/api/friend/friend.js";
import {
  getLocalStorage,
  setLocalStorage,
} from "@/util/localstorage/localstorage.js";
import { ref } from "vue";
const showModal = ref(false);
const keyword = ref("");
const members = ref();
const myFriends = ref();

if (getLocalStorage("userId") === null) {
  alert("다시 로그인 해주세요!");
}

const callFriendManagement = () => {};

const clickFriendAddBtn = (friendDto) => {
  console.log(friendDto);
  bringFriendList(
    getLocalStorage("userId"),
    (response) => {
      console.log(response.data);
      myFriends.value = response.data;
      members.value = members.value.filter(
        (member) => member.id !== friendDto.friendId
      ); // 친구 제거
    },
    (error) => {
      console.log(error);
    }
  );
};

const clickFriendRemoveBtn = (friendDto) => {
  console.log(friendDto);
  myFriends.value = myFriends.value.filter(
    (friend) => friend.id !== friendDto.id
  ); // 친구 제거
};

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

const callMemberByKeyword = () => {
  searchMemberByKeyNicknameKeword(
    keyword.value,
    getLocalStorage("userId"),
    (response) => {
      members.value = response.data;
      keyword.value = "";
    },
    (error) => {
      console.log(error);
    }
  );
};

console.log(getLocalStorage("userId"));
const getFriendList = () => {
  bringFriendList(
    getLocalStorage("userId"),
    (response) => {
      console.log(response.data);
      myFriends.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
};

getFriendList();
</script>

<style scoped>
.left-search-friend-box {
  width: 380px;
  height: 680px;
  margin-right: 5px;
  overflow-y: scroll; /* 상하 스크롤을 항상 표시 */
}
.modal-box {
  display: flex;
}
.search-box {
  position: relative;
}
.search_icon {
  width: 20px;
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}
.right-current-my-friend-box {
  width: 380px;
  height: 680px;
  margin-left: 5px;
  overflow-y: scroll; /* 상하 스크롤을 항상 표시 */
}
</style>
