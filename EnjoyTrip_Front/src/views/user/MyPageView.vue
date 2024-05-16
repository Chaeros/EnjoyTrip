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
            placeholder="Search"
            aria-label="Search"
            v-model="keyword"
            @keyup.enter="searchAttractions"
          />
          <img
            src="@/img/search_icon.png"
            class="search_icon"
            @click="searchAttractions"
          />
        </div>
        <template v-for="member in members" :key="member.id">
          <MemberItem :member="member"></MemberItem>
        </template>
      </div>
      <div class="right-current-my-friend-box"></div>
    </div>
  </Modal>
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import Modal from "@/modal/FriendManagementModal.vue";
import MemberItem from "@/components/item/member/MemberItem.vue";
import { ref } from "vue";
const showModal = ref(false);
const callFriendManagement = () => {};

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};
</script>

<style scoped>
.left-search-friend-box {
  width: 380px;
  height: 680px;
  background-color: red;
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
  background-color: blue;
}
</style>
