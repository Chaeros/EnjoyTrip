<template>
  <Header></Header>
  <div class="board-list-page">
    <!-- <img class="represent-img" src="@/img/Gyeongbokgung.jpg" /> -->
    <div class="board-list-box">
      <div class="board-list-box-top">
        <div class="board-list-description-and-write-btn">
          <div class="review-title">리뷰 목록</div>
          <button
            type="button"
            class="btn btn-outline-secondary select-attraction-btn"
            @click="clickBoardWriteBtn"
          >
            글 쓰기
          </button>
        </div>
        <div class="review-card-list">
          <template
            v-for="attractionBoardReview in attractionBoardReviews"
            :key="attractionBoardReview.id"
          >
            <AttractionBoardArticleCard
              class="article-card"
              :attractionBoardReview="attractionBoardReview"
              @click="clickAttractionBoardArticleCard(attractionBoardReview)"
            />
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Header from "@/components/Header.vue";
import AttractionBoardArticleCard from "@/components/item/card/AttractionBoardArticleCard.vue";
import { useRoute, useRouter } from "vue-router";
import { ref } from "vue";
import { useMemberStore } from "@/store/member";
import { storeToRefs } from "pinia";
import { getAttractionReviewList } from "@/api/attraction-board/attraction-board";
import { getUserInfomationById } from "@/api/member/member.js";
const router = useRouter();
const memberStore = useMemberStore();
const { isLogin } = storeToRefs(memberStore);
const clickBoardWriteBtn = () => {
  console.log(isLogin);
  if (isLogin.value === true) {
    router.push({ name: "reviewBoardWrite" });
  } else {
    alert("로그인 후에만 글 작성이 가능합니다!");
    router.push({ name: "login" });
  }
};
const attractionBoardReviews = ref([]);
getAttractionReviewList(
  (response) => {
    console.log(response);
    console.log(response.data);
    attractionBoardReviews.value = response.data;
  },
  (error) => {
    console.log(error);
  }
);

const clickAttractionBoardArticleCard = (attractionBoardReview) => {
  console.log(attractionBoardReview);
  console.log(attractionBoardReview.id);
  router.push({
    name: "reviewBoardDetail",
    params: { attractionBoardReviewId: attractionBoardReview.id },
  });
};
</script>

<style scoped>
.board-list-page {
  position: relative;
}

.represent-img {
  width: 100vw; /* 가로 전체를 차지하도록 설정 */
  height: 50vh; /* 세로를 화면의 20%만 차지하도록 설정 */
  object-fit: cover; /* 이미지를 잘라서라도 가로와 세로 비율을 맞춤 */
  object-position: center; /*이미지의 중앙 부분을 유지 */
}
.board-list-box {
  margin-top: 20px;
}
.board-list-box-top {
  width: 1200px;
}
.board-list-description-and-write-btn {
  display: flex;
  justify-content: space-between;
  margin: 10px;
}
.review-title {
  font-size: 25px;
  font-weight: bold;
}
.board-write-buttons {
  display: flex;
  justify-content: space-between;
  margin: 20px 0;
  font-size: 25px;
}
.button {
  font-size: 16px;
}
.board-list-box {
  width: 1200px;
  display: flex;
  margin: 0 auto;
}
.review-card-list {
  display: flex;
  flex-wrap: wrap; /* 카드가 넘치면 다음 줄로 넘김 */
  justify-content: flex-start; /* 왼쪽 정렬 */
}
.card-group {
  position: static;
}
.article-card {
  width: calc(25% - 20px); /* 전체 너비의 25%만큼 차지하고, 여백 고려 */
  margin: 10px; /* 상하좌우 여백 */
}
</style>
