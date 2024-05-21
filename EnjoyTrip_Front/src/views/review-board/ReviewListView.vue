<template>
  <Header></Header>
  <div class="board-list-page">
    <div class="board-list-box">
      <div class="board-list-box-top">
        <div class="board-list-description-and-write-btn">
          <div class="review-title">리뷰 목록</div>
          <button
            type="button"
            class="btn btn-outline-secondary user-button"
            @click="clickBoardWriteBtn"
          >
            글쓰기
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
  <Footer></Footer>
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import AttractionBoardArticleCard from "@/components/item/card/AttractionBoardArticleCard.vue";
import { useRouter } from "vue-router";
import { ref } from "vue";
import { useMemberStore } from "@/store/member";
import { storeToRefs } from "pinia";
import { getAttractionReviewList } from "@/api/attraction-board/attraction-board";

const router = useRouter();
const memberStore = useMemberStore();
const { isLogin } = storeToRefs(memberStore);

const clickBoardWriteBtn = () => {
  if (isLogin.value) {
    router.push({ name: "reviewBoardWrite" });
  } else {
    alert("로그인 후에만 글 작성이 가능합니다!");
    router.push({ name: "login" });
  }
};

const attractionBoardReviews = ref([]);
getAttractionReviewList(
  (response) => {
    attractionBoardReviews.value = response.data;
  },
  (error) => {
    console.error(error);
  }
);

const clickAttractionBoardArticleCard = (attractionBoardReview) => {
  router.push({
    name: "reviewBoardDetail",
    params: { attractionBoardReviewId: attractionBoardReview.id },
  });
};
</script>

<style scoped>
.board-list-page {
  padding: 20px;
  background-color: #f8f9fa; /* 밝은 배경색 추가 */
  min-height: 100vh; /* 최소 높이 설정 */
}

.board-list-box {
  max-width: 1200px;
  margin: 0 auto;
  background-color: #ffffff; /* 흰색 배경 추가 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
  border-radius: 8px; /* 모서리 둥글게 */
  padding: 20px;
}

.board-list-box-top {
  margin-bottom: 20px;
}

.board-list-description-and-write-btn {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.review-title {
  font-size: 32px;
  font-weight: 700;
  color: #333; /* 진한 텍스트 색상 */
}

.select-attraction-btn {
  padding: 10px 20px;
  font-size: 18px;
  border-radius: 5px;
  transition: background-color 0.3s, box-shadow 0.3s;
}

.select-attraction-btn:hover {
  background-color: #0056b3;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
}

.review-card-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px; /* 카드 간격 추가 */
}

.article-card {
  background-color: #ffffff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
  width: calc(33.333% - 20px); /* 3개씩 표시되도록 설정 */
  display: flex;
  flex-direction: column;
}

.article-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.article-card img {
  width: 100%;
  height: 180px;
  object-fit: cover; /* 이미지 비율을 유지하며 잘라내기 */
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
}

.article-card .content {
  padding: 15px;
  flex-grow: 1; /* 내용이 카드 높이를 채우도록 설정 */
}

.article-card .title {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.article-card .description {
  font-size: 16px;
  color: #666;
  line-height: 1.5;
  flex-grow: 1; /* 내용이 카드 높이를 채우도록 설정 */
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3; /* 최대 3줄까지만 표시 */
  -webkit-box-orient: vertical;
}
</style>
