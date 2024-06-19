<template>
  <div class="card-outter-box" v-if="writerInfo && attractionInfo">
    <div class="attraction-explain-box">{{ attractionInfo.title }}</div>
    <div class="card-content-box">
      <div class="title-and-content-box">
        <template v-if="attractionBoardReview.imageUrl !== ''">
          <img
            class="article-title-image"
            :src="imageServerURL + attractionBoardReview.imageUrl"
          />
        </template>
        <div class="card-title">{{ truncatedTitle }}</div>
        <div class="card-content">
          <div class="card-content-inner" v-html="truncatedContent"></div>
        </div>
      </div>
      <div class="attraction-board-regdate inner-content-left-margin">
        {{ formattedRegdate }}
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
        <div class="writer-info-box-right">
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
          <img
            class="writer-info-box-right-comment-img"
            src="@/img/attractionboard/eye_icon.png"
          />
          <div class="indicator">{{ attractionBoardReview.hit }}</div>
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
import { getUserInfomationById } from '@/api/member/member.js';
import { ref, onMounted, computed } from 'vue';
import { getAttractionBoardLikeCount } from '@/api/attraction-board-like/attraction-board-like.js';
import { getCommentCount } from '@/api/attraction-board-comment/attraction-board-comment.js';
import { getAttractionDetailByContentId } from '@/api/attraction/attraction';
const imageServerURL = import.meta.env.VITE_VUE_IMAGE_SERVER_URL;
const { attractionBoardReview } = defineProps({
  attractionBoardReview: Object,
});
console.log('tttt');
console.log(attractionBoardReview);
const writerInfo = ref(null);
const commentCount = ref(0);
const likeCount = ref(0);
const attractionInfo = ref();

const truncatedTitle = computed(() => {
  const title = attractionBoardReview.title;
  return title.length > 10 ? title.slice(0, 10) + '...' : title;
});

const truncatedContent = computed(() => {
  const content = attractionBoardReview.content;
  return content.length > 20 ? content.slice(0, 20) + '...' : content;
});

const formattedRegdate = computed(() => {
  const date = new Date(attractionBoardReview.regdate);
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

  console.log(attractionBoardReview);
  getAttractionBoardLikeCount(
    attractionBoardReview.id,
    (response) => {
      console.log('dddddddddddddddddddddddddd');
      console.log(response.data);
      likeCount.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );

  getCommentCount(
    attractionBoardReview.id,
    (response) => {
      commentCount.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );

  console.log(attractionBoardReview.attractionId);
  getAttractionDetailByContentId(
    attractionBoardReview.attractionId,
    (response) => {
      console.log('ggggg', response.data);
      attractionInfo.value = response.data.attractionInfo;
      console.log(attractionInfo.value);
    },
    (error) => {
      console.log(error);
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
  position: relative;
}
.attraction-explain-box {
  position: absolute;
  background-color: black;
  border-radius: 10px;
  padding: 2px 10px;
  margin: 10px 0;
  font-size: 15px;
  font-weight: bold;
  color: white;
  right: 10px;
  max-width: calc(100% - 20px);
  text-align: right;
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
</style>
