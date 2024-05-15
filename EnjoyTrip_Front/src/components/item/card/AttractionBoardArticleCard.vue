<template>
  <div class="card-outter-box" v-if="writerInfo">
    <div class="card-content-box">
      <div class="title-and-content-box">
        <template v-if="attractionBoardReview.imageUrl !== ''">
          <img
            class="article-title-image"
            :src="imageServerURL + attractionBoardReview.imageUrl"
          />
        </template>
        <div class="card-title">{{ attractionBoardReview.title }}</div>
        <div class="card-content">{{ attractionBoardReview.content }}</div>
      </div>
      <div class="attraction-board-regdate">
        {{ attractionBoardReview.regdate }}
      </div>
      <hr class="contour" />

      <div class="writer-info-box">
        <div class="writer-info-box-left">
          <div>
            <template v-if="writerInfo.image === null">
              <div>
                <img
                  class="writer-profile-img"
                  src="@/img/member/default_img.jpg"
                />
              </div>
            </template>
            <template v-else>
              <div>
                <img class="writer-profile-img" :src="writerInfo.image" />
              </div>
            </template>
          </div>
          <div>by {{ writerInfo.nickname }}</div>
        </div>
        <div class="writer-info-box-right">
          <img
            class="writer-info-box-right-comment-img"
            src="@/img/comment/comment.png"
          />
          <img
            class="writer-info-box-right-like-img"
            src="@/img/like/like.png"
          />
        </div>
      </div>
      <div class="card-writer">{{ attractionBoardReview.writer }}</div>
    </div>
  </div>
  <div v-else>
    <!-- 로딩 스피너나 로딩 메시지를 표시할 수 있습니다 -->
    <p>Loading...</p>
  </div>
</template>

<script setup>
import { getUserInfomationById } from "@/api/member/member.js";
import { ref, onMounted } from "vue";
const imageServerURL = import.meta.env.VITE_VUE_IMAGE_SERVER_URL;
const { attractionBoardReview } = defineProps({
  attractionBoardReview: Object,
});
console.log(attractionBoardReview);
const writerInfo = ref(null);

// 데이터 로드를 위한 함수를 onMounted 안으로 이동하여 컴포넌트가 마운트 된 후 호출되도록 함
onMounted(() => {
  getUserInfomationById(
    attractionBoardReview.memberId,
    (response) => {
      console.log(response);
      writerInfo.value = response.data;
      console.log(writerInfo.value);
    },
    (error) => {
      console.error(error);
      writerInfo.value = {}; // 에러가 발생했을 경우 빈 객체를 설정하여 v-if 조건을 false로 만들 수 있습니다.
    }
  );
});
</script>

<style scoped>
.card-outter-box {
  border: 1px solid;
  width: 100%;
  height: 350px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.card-content-box {
  display: flex;
  flex-direction: column;
  width: 100%;
}
.title-and-content-box {
  width: 100%;
  height: 260px;
}
.attraction-board-regdate {
  height: 30px;
  margin-left: 3px;
}
.card-title {
  font-size: 20px;
  font-weight: bold;
}
.card-content {
  flex-grow: 1; /* 컨테이너의 남은 공간을 모두 차지하도록 설정 */
  overflow: auto; /* 내용이 넘칠 경우 스크롤바 표시 */
}
.card-content-wrap {
}
.article-title-image {
  width: 100%;
  height: 200px;
}
.contour {
  margin: 1px;
}
.writer-info-box {
  display: flex;
  height: 60px;
  margin: 3px;
}
.writer-info-box-left {
  display: flex;
}
.writer-profile-img {
  border-radius: 100%;
  width: 50px;
}
.writer-info-box {
  display: flex;
  justify-content: space-between;
}
.writer-info-box-right-comment-img {
  width: 30px;
}
.writer-info-box-right-like-img {
  width: 30px;
}
</style>
