<template>
  <div class="card-outter-box" v-if="writerInfo">
    <div class="card-image">
      <template v-if="attractionBoardReview.imageUrl !== ''">
        <img
          class="image"
          :src="imageServerURL + attractionBoardReview.imageUrl"
        />
      </template>
    </div>
    <div class="card-content">
      <div class="card-title">{{ attractionBoardReview.title }}</div>
      <div class="writer-info-box">
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
        <div>{{ writerInfo.nickname }}</div>
      </div>
      <div class="card-writer">{{ attractionBoardReview.writer }}</div>
      <div class="regdate">{{ attractionBoardReview.regdate }}</div>
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
  border-radius: 10px;
}
.image {
  width: 100%;
  border-radius: 10px;
}
.writer-info-box {
  display: flex;
}
.writer-profile-img {
  border-radius: 100%;
  width: 50px;
}
</style>
