<template>
  <div
    class="card-outter-box"
    v-if="writerInfo"
    @click.prevent="routeSearchAttraction"
  >
    <div class="card-content-box">
      <div class="title-and-content-box">
        <template v-if="tripPlanRequest.tripPlan.image !== ''">
          <img
            class="article-title-image"
            :src="VITE_VUE_IMAGE_SERVER_URL + tripPlanRequest.tripPlan.image"
          />
        </template>
        <template v-else>
          <img
            :src="
              VITE_VUE_IMAGE_SERVER_URL +
              '/image/uploads/1716297494437_colddragon.png'
            "
            class="article-title-image"
          />
        </template>
        <div class="card-title">{{ tripPlanRequest.tripPlan.title }}</div>
        <div class="card-content">
          <div class="card-content-inner">
            {{ tripPlanRequest.tripPlan.content }}
          </div>
        </div>
      </div>
      <div class="date-container">
        <div class="attraction-board-regdate inner-content-left-margin">
          출발일자: {{ tripPlanRequest.tripPlan.departureDate }}
        </div>
        <div class="attraction-board-regdate inner-content-left-margin">
          도착일자: {{ tripPlanRequest.tripPlan.arrivalDate }}
        </div>
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
          <div class="writer-nickname-font">by {{ writerInfo.nickname }}</div>
        </div>
        <!-- <div class="writer-info-box-right">
          <img
            class="writer-info-box-right-like-img"
            src="@/img/like/like.png"
          />
          <div class="indicator">{{ likeCount }}</div>
          <img
            class="writer-info-box-right-comment-img"
            src="@/img/comment/comment.png"
          />
          <div class="indicator">{{ commentCount }}</div>
        </div> -->
      </div>
      <div class="card-writer">{{ tripPlanRequest.writer }}</div>
    </div>
  </div>
  <div v-else>
    <!-- 로딩 스피너나 로딩 메시지를 표시할 수 있습니다 -->
    <p>Loading...</p>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { getLocalStorage } from '@/util/localstorage/localstorage';
const userId = parseInt(getLocalStorage('userId'));
const { VITE_VUE_API_URL, VITE_VUE_IMAGE_SERVER_URL } = import.meta.env;

import { getUserInfomationById } from '@/api/member/member.js';
import { ref, onMounted, computed } from 'vue';
import { getAttractionBoardLikeCount } from '@/api/attraction-board-like/attraction-board-like.js';
import { getCommentCount } from '@/api/attraction-board-comment/attraction-board-comment.js';
const router = useRouter();
const imageServerURL = import.meta.env.VITE_VUE_IMAGE_SERVER_URL;
const { tripPlanRequest } = defineProps({
  tripPlanRequest: Object,
});
console.log(tripPlanRequest);
const writerInfo = ref(null);
const commentCount = ref(0);
const likeCount = ref(0);

const truncatedTitle = computed(() => {
  const title = tripPlanRequest.title;
  return title.length > 10 ? title.slice(0, 10) + '...' : title;
});

const truncatedContent = computed(() => {
  const content = tripPlanRequest.content;
  return content.length > 20 ? content.slice(0, 20) + '...' : content;
});

const formattedRegdate = computed(() => {
  const date = new Date(tripPlanRequest.regdate);
  return date.toLocaleString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
  });
});

// 데이터 로드를 위한 함수를 onMounted 안으로 이동하여 컴포넌트가 마운트 된 후 호출되도록 함
onMounted(() => {
  getUserInfomationById(
    userId,
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

const routeSearchAttraction = () => {
  router.push({ name: 'searchattraction' });
};
</script>

<style scoped>
.card-outter-box {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
  width: 100%;
  height: 350px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.card-outter-box:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
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
  margin-left: 10px;
  margin-top: 3px;
}
.card-title {
  font-size: 20px;
  font-weight: bold;
  margin-top: 5px;
  margin-left: 10px;
  margin-bottom: 0;
}
.card-content {
  flex-grow: 1; /* 컨테이너의 남은 공간을 모두 차지하도록 설정 */
  overflow: hidden; /* 내용이 넘칠 경우 숨기기 */
}

.card-content-inner {
  display: -webkit-box;
  -webkit-line-clamp: 3; /* 최대 라인 수 */
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal; /* 여러 줄을 허용 */
  height: 20px;
  margin: 3px 10px;
}

.card-content-wrap {
}
.article-title-image {
  width: 100%;
  height: 200px;
}
.contour {
  margin: 0px;
}
.writer-info-box {
  display: flex;
  height: 60px;
  align-items: center;
  margin-left: 10px;
}
.writer-info-box-left {
  display: flex;
}
.writer-profile-img {
  border-radius: 100%;
  width: 40px;
  height: 40px;
  margin: 5px;
  margin-top: 0;
}
.writer-nickname-font {
  font-size: 18px;
  font-weight: bold;
  line-height: 35px;
}
.writer-info-box {
  display: flex;
  justify-content: space-between;
}
.writer-info-box-right {
  margin-bottom: 10px;
  display: flex;
  margin-right: 10px;
}
.writer-info-box-right-comment-img {
  width: 18px;
  height: 23px;
  margin-right: 5px;
}
.writer-info-box-right-like-img {
  width: 18px;
  height: 19px;
  margin-right: 5px;
}
.indicator {
  font-size: 15px;
  margin-right: 8px;
}
.date-container {
  display: flex;
  gap: 30px;
}
</style>
