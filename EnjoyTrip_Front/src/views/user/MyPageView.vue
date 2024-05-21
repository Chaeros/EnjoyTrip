<template>
  <div>
    <Header></Header>
    <!-- <div class="mypage-wrap">
      <div class="my-firends-box"></div>
      <button @click="openModal">내 친구</button>
    </div> -->
    <div class="my-page">
      <div class="sidebar">
        <ul class="menu">
          <li class="menu-item active">
            <a href="#" @click="clickInformation">정보</a>
          </li>
          <li class="menu-item">
            <a href="#" @click="clickFriendManagement">친구관리</a>
          </li>
          <li class="menu-item">
            <a href="#" @click="clickTripPlan">여행계획 </a>
          </li>
        </ul>
      </div>
      <div class="content">
        <template v-if="myPageMode == `Information`">
          <MemberComponent> </MemberComponent
        ></template>

        <template v-if="myPageMode == `Friend`">
          <div class="friend-component">
            <h1 class="member-edit-title"><b>친구정보</b></h1>
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
                  <template v-for="friend in friends" :key="friend.id">
                    <FriendItem
                      :friend="friend"
                      @click-friend-remove-btn="clickFriendRemoveBtn"
                    ></FriendItem>
                  </template>
                </div>
              </div>
            </div>
          </div>
        </template>

        <template v-if="myPageMode == `TripPlan`"> </template>
      </div>
    </div>
    <Footer></Footer>
  </div>

  <!-- <Modal :isVisible="showModal" title="친구 관리" @close="closeModal">
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
  </Modal> -->
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
import MemberComponent from "@/components/member/MemberComponent.vue";
import { storeToRefs } from "pinia";
import { useFriendManagementStore } from "@/store/friend-management/friend-management.js";
const friendManagementStore = useFriendManagementStore();
const { friends } = storeToRefs(friendManagementStore);
const { bringMyFriendsList } = friendManagementStore;
const showModal = ref(false);
const keyword = ref("");
const members = ref();
const myFriends = ref();
const myPageMode = ref("Information");

bringMyFriendsList();
if (getLocalStorage("userId") === null) {
  alert("다시 로그인 해주세요!");
}

const callFriendManagement = () => {};

const clickInformation = () => {
  myPageMode.value = "Information";
};

const clickFriendManagement = () => {
  myPageMode.value = "Friend";
};

const clickTripPlan = () => {
  myPageMode.value = "TripPlan";
};

const clickFriendAddBtn = (friendDto) => {
  // console.log(friendDto);
  // friends.value.push(friendDto.friendId);

  bringFriendList(
    getLocalStorage("userId"),
    (response) => {
      console.log(response.data);
      friends.value = response.data;
      members.value = members.value.filter(
        (member) => member.id !== friendDto.friendId
      );
    },
    (error) => {
      console.log(error);
    }
  );
};

const clickFriendRemoveBtn = (friendDto) => {
  console.log(friendDto);
  friends.value = friends.value.filter((friend) => friend.id !== friendDto.id); // 친구 제거
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
      friends.value = response.data;
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
  width: 50%;
  /* height: 680px; */
  margin-right: 5px;
  overflow-y: scroll; /* 상하 스크롤을 항상 표시 */
}
.modal-box {
  display: flex;
  width: 100%;
  height: 550px;
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
  width: 50%;
  height: 100%;
  margin-left: 5px;
  overflow-y: scroll; /* 상하 스크롤을 항상 표시 */
}
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap");

.my-page {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  /* height: 100vh; */
  font-family: "Noto Sans KR", sans-serif;
}

.sidebar {
  width: 250px;
  background-color: #f8f9fa;
  padding: 20px;
  border-right: 1px solid #e9ecef;
}

.friend-component {
  padding: 40px;
  height: 650px;
}

.profile {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}

.profile-info {
  text-align: center;
}

.member-edit-title {
  margin-bottom: 30px;
  font-family: "Noto Sans KR", sans-serif;
  font-weight: 700;
}

.profile-image {
  position: relative;
  width: 100px;
  height: 100px;
  margin-top: 10px;
}

.profile-image img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}

.edit-button {
  position: absolute;
  bottom: 0;
  right: 0;
  background-color: white;
  border: none;
  cursor: pointer;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.menu {
  list-style: none;
  padding: 0;
}

.menu-item {
  margin-bottom: 10px;
}

.menu-item a {
  color: #343a40;
  text-decoration: none;
  display: block;
  padding: 10px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.menu-item a:hover {
  background-color: #e9ecef;
}

.menu-item.active a {
  font-weight: bold;
  background-color: #e9ecef;
}

.new-badge {
  background-color: #28a745;
  color: white;
  padding: 2px 5px;
  border-radius: 3px;
  font-size: 12px;
  margin-left: 5px;
}

.content {
  flex-grow: 1;
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header h1 {
  margin: 0;
}

.write-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.write-button:hover {
  background-color: #0056b3;
}

.intro {
  text-align: center;
  color: #6c757d;
}
</style>
