<template>
  <div class="review-card-list">
    <template
      v-for="attractionBoardReview in props.attractionBoardReviews"
      :key="attractionBoardReview.id"
    >
      <AttractionBoardArticleCard
        class="article-card"
        :attractionBoardReview="attractionBoardReview"
        @click="clickAttractionBoardArticleCard(attractionBoardReview)"
      />
    </template>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import AttractionBoardArticleCard from "@/components/item/card/AttractionBoardArticleCard.vue";
import { ref } from "vue";
const router = useRouter();

const props = defineProps({
  attractionBoardReviews: {
    type: Array,
  },
});

const clickAttractionBoardArticleCard = (attractionBoardReview) => {
  router.push({
    name: "reviewBoardDetail",
    params: { attractionBoardReviewId: attractionBoardReview.id },
  });
};
</script>

<style scoped>
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
