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
          <div class="card-group">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title"><b>덕수궁</b></h5>
                <p class="card-text">
                  조선시대의 궁궐이다. 원래 왕가의 별궁인 명례궁이었으나,
                  임진왜란 직후 행궁으로써 정궁 역할을 했으며, 광해군 때 정식
                  궁궐로 승격
                </p>
                <p class="card-text">
                  <small class="text-body-secondary"
                    >Last updated 3 mins ago</small
                  >
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Header from "@/components/Header.vue";
import { useRoute, useRouter } from "vue-router";
const router = useRouter();
const clickBoardWriteBtn = () => {
  router.push({ name: "reviewBoardWrite" });
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
  object-position: center; /* 이미지의 중앙 부분을 유지 */
}
.board-list-box {
  text-align: center;
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
}
.card-group {
  position: static;
}
.card {
  width: 400px;
  margin: 10px;
  border-radius: 10px;
}
</style>
