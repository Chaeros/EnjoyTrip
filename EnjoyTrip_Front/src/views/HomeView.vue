<template>
  <div>
    <Header></Header>
    <div id="index_01">
      <div id="slide1">
        <ul>
          <li>
            <a href="#"
              ><img
                src="@/img/mainpage/mainpage_background1.jpg"
                class="main-slide-image"
                alt="슬라이드1"
            /></a>
          </li>
          <!-- <li>
            <a href="#"
              ><img
                src="@/img/mainpage/mainpage_background1.jpg"
                alt="슬라이드2"
            /></a>
          </li>
          <li>
            <a href="#"
              ><img
                src="@/img/mainpage/mainpage_background1.jpg"
                alt="슬라이드3"
            /></a>
          </li> -->
        </ul>
      </div>
    </div>
    <div class="welcome-text">
      <h1>
        <span>W</span>
        <span>E</span>
        <span>L</span>
        <span>C</span>
        <span>O</span>
        <span>M</span>
        <span>E</span>
        <span>!</span>
        <span>!</span>
      </h1>
    </div>
    <div class="board-list">
      <div class="board-list-top-bar">
        <div class="board-list-top-bar-left">
          <img src="@/img/coldragon2-removebg.png" class="main-sub-title-img" />
          <div class="main-sub-title-font">HOT 리뷰</div>
        </div>
        <div class="board-list-top-bar-right">
          <button
            type="button"
            class="btn btn-outline-dark no-outline"
            @click="toggleHotReviewMode"
          >
            <div class="moreInfoButton">
              <template v-if="isOpenHotReviewComponent"> 접기 </template>
              <template v-if="!isOpenHotReviewComponent"> 펼치기 </template>
            </div>
          </button>
          <button
            type="button"
            class="btn btn-outline-dark no-outline"
            @click="moveReviewList"
          >
            <div class="moreInfoButton">더보기</div>
          </button>
        </div>
      </div>
      <!-- 관광지 리뷰 Component 장착 -->
      <template v-if="isOpenHotReviewComponent">
        <AttractionBoardComponent
          :attractionBoardReviews="attractionBoardReviews"
        ></AttractionBoardComponent>
        <div class="y-diff"></div>
      </template>

      <div class="board-list-top-bar">
        <div class="board-list-top-bar-left">
          <img src="@/img/coldragon2-removebg.png" class="main-sub-title-img" />
          <div class="main-sub-title-font">내 여행계획</div>
        </div>
        <div class="board-list-top-bar-right">
          <button
            type="button"
            class="btn btn-outline-dark no-outline"
            @click="toggleMyPlanMode"
          >
            <div class="moreInfoButton">
              <template v-if="isOpenMyPlanComponent"> 접기 </template>
              <template v-if="!isOpenMyPlanComponent"> 펼치기 </template>
            </div>
          </button>
          <button type="button" class="btn btn-outline-dark no-outline">
            <div class="moreInfoButton">더보기</div>
          </button>
        </div>
      </div>
      <!-- 내 여행계획 Component 장착 -->
      <div
        v-if="isOpenMyPlanComponent"
        class="my-plan-list-modal-home-view-container"
      >
        <!-- class="my-plan-list-modal-home-view" -->
        <PlanCard
          v-for="tripPlanRequest in tripPlanRequests"
          :trip-plan-request="tripPlanRequest"
        ></PlanCard>
      </div>
      <div class="y-diff"></div>

      <div class="board-list-top-bar">
        <div class="board-list-top-bar-left">
          <img src="@/img/coldragon2-removebg.png" class="main-sub-title-img" />
          <div class="main-sub-title-font">찬곤이 소개</div>
        </div>
        <div class="board-list-top-bar-right">
          <button
            type="button"
            class="btn btn-outline-dark no-outline"
            @click="toggleIntroduceChangonMode"
          >
            <div class="moreInfoButton">
              <template v-if="isOpenIntroduceChangonComponent"> 접기 </template>
              <template v-if="!isOpenIntroduceChangonComponent">
                펼치기
              </template>
            </div>
          </button>
          <button type="button" class="btn btn-outline-dark no-outline">
            <div class="moreInfoButton">더보기</div>
          </button>
        </div>
      </div>
      <!-- 찬곤이 소개 Component 장착 -->
    </div>
    <Footer></Footer>
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getUserInfomation } from '@/api/member/member.js';

// 'default' 대신 '*'를 사용하여 CommonJS 형식으로 가져오기
import VueJwtDecode from 'vue-jwt-decode';
import {
  getLocalStorage,
  setLocalStorage,
} from '@/util/localstorage/localstorage.js';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/store/member';
import { getAttractionReviewList } from '@/api/attraction-board/attraction-board';
import AttractionBoardComponent from '@/components/board/AttractionBoardComponent.vue';

import { getListMyTripPlan } from '@/api/plan/plan';
import PlanCard from '@/components/item/card/PlanCard.vue';
const userId = parseInt(getLocalStorage('userId'));
const tripPlanRequests = ref([]);

const memberStore = useMemberStore();
const accessToken = ref('');
const email = ref('');
const route = useRoute();
const { userInfo, isLogin } = storeToRefs(memberStore);
const attractionBoardReviews = ref([]);
const router = useRouter();

// Mode 관련 변수
const isOpenHotReviewComponent = ref(true);
const isOpenMyPlanComponent = ref(true);
const isOpenIntroduceChangonComponent = ref(false);

const toggleHotReviewMode = () => {
  isOpenHotReviewComponent.value = !isOpenHotReviewComponent.value;
};

const toggleMyPlanMode = () => {
  isOpenMyPlanComponent.value = !isOpenMyPlanComponent.value;
};

const toggleIntroduceChangonMode = () => {
  isOpenIntroduceChangonComponent.value =
    !isOpenIntroduceChangonComponent.value;
};

const moveReviewList = () => {
  router.push({
    name: 'reviewBoardList',
  });
};

onMounted(() => {
  accessToken.value = route.query.accessToken;
  console.log(accessToken.value);
  getReviews();
  getMyPlans();

  if (accessToken.value) {
    try {
      // CommonJS 형식으로 가져온 모듈에서 함수를 호출
      const decodedToken = VueJwtDecode.decode(accessToken.value);
      email.value = decodedToken.email;
      console.log(decodedToken);
      getUserInfomation(
        email.value,
        (response) => {
          console.log(response.data);
          setLocalStorage('userId', response.data.id);
          setLocalStorage('access_token', accessToken.value);
          console.log(getLocalStorage('userId'));
          isLogin.value = true;
          userInfo.value = response.data;
        },
        (error) => {
          console.log(error);
        }
      );
    } catch (error) {
      console.error('Invalid token:', error);
    }
  }
});

const getReviews = () => {
  getAttractionReviewList(
    (response) => {
      attractionBoardReviews.value = response.data;
    },
    (error) => {
      console.error(error);
    }
  );
};

const getMyPlans = () => {
  getListMyTripPlan(
    userId,
    (data) => {
      console.dir('data 까지는 잘받아옴.그리고 뒤에  data 붙여줘야함');
      tripPlanRequests.value = data.data;
      console.dir('내 tripPlanRequests들');
      console.dir(tripPlanRequests.value);
    },
    (error) => {
      console.error(error);
    }
  );
};
</script>

<style scoped>
@keyframes bounce {
  100% {
    top: -20px;
    text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
      0 5px 0 #ccc, 0 6px 0 #ccc, 0 7px 0 #ccc, 0 8px 0 #ccc, 0 9px 0 #ccc,
      0 50px 25px rgba(0, 0, 0, 0.2);
  }
}

/* latin-ext */
@font-face {
  font-family: 'Titan One';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/titanone/v15/mFTzWbsGxbbS_J5cQcjCmjgm6Es.woff2)
    format('woff2');
  unicode-range: U+0100-02AF, U+0304, U+0308, U+0329, U+1E00-1E9F, U+1EF2-1EFF,
    U+2020, U+20A0-20AB, U+20AD-20C0, U+2113, U+2C60-2C7F, U+A720-A7FF;
}
/* latin */
@font-face {
  font-family: 'Titan One';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/titanone/v15/mFTzWbsGxbbS_J5cQcjClDgm.woff2)
    format('woff2');
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA,
    U+02DC, U+0304, U+0308, U+0329, U+2000-206F, U+2074, U+20AC, U+2122, U+2191,
    U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
}
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap');
/* 스타일은 필요에 따라 추가 */
#index_01 {
  width: 1200px;
  height: 100%;
  margin: 0 auto;
}

/* #header1 {
  height: 100px;
  border-bottom: 1px solid dimgrey;
  box-sizing: border-box;
  text-align: center;
  line-height: 100px;
  font-size: 1.5rem;
} */

/* #slide1 {
  height: 100%;
  overflow: hidden;
  border-bottom: 1px solid dimgrey;
}
#slide1 > ul > li {
  display: inline-block;
  font-size: 0;
} */
#slide1 > ul {
  font-size: 0;
  list-style: none; /* 점을 제거합니다 */
  margin: 0;
  padding: 0;
}
.main-slide-image {
  width: 1200px;
  height: 500px;
  border-radius: 15px;
  margin-top: 20px;
}

.welcome-text {
  width: 1200px;
  margin: 0 auto;
  padding-bottom: 50px;
  display: flex;
  justify-content: center;
}

h1 span {
  position: relative;
  top: 20px;
  display: inline-block;
  animation: bounce 0.3s ease infinite alternate;
  font-family: 'Titan One', cursive;
  font-size: 80px;
  color: black;
  text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
    0 5px 0 #ccc, 0 6px 0 transparent, 0 7px 0 transparent, 0 8px 0 transparent,
    0 9px 0 transparent, 0 10px 10px rgba(0, 0, 0, 0.4);
}

h1 span:nth-child(2) {
  animation-delay: 0.1s;
}
h1 span:nth-child(3) {
  animation-delay: 0.2s;
}
h1 span:nth-child(4) {
  animation-delay: 0.3s;
}
h1 span:nth-child(5) {
  animation-delay: 0.4s;
}
h1 span:nth-child(6) {
  animation-delay: 0.5s;
}
h1 span:nth-child(7) {
  animation-delay: 0.6s;
}
h1 span:nth-child(8) {
  animation-delay: 0.7s;
}

.board-list {
  width: 1200px;
  margin: 0 auto;
}
.board-list-top-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 15px;
  border-bottom: 1px solid #eaeaea;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
/* .board-list-top-bar:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
} */
.board-list-top-bar-left {
  display: flex;
}
.board-list-top-bar-right {
  display: flex;
  margin-right: 20px;
}
.no-outline {
  border: none;
}
.moreInfoButton {
  cursor: pointer;
  font-size: 17px;
  font-weight: bold;
  font-family: 'Noto Sans KR', sans-serif;
}
.main-sub-title-img {
  width: 70px;
}
.main-sub-title-font {
  font-size: 35px;
  font-weight: bold;
  font-family: 'Noto Sans KR', sans-serif;
}
.separate-line {
  margin-top: 0px;
  margin-bottom: 20px;
}
.y-diff {
  height: 50px;
}

.my-plan-list-modal-home-view-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 한 줄에 3개의 열을 만듭니다. */
  gap: 20px; /* 각 요소 사이에 20px의 간격을 줍니다. */
}

.my-plan-list-modal-home-view {
  background-color: #e0e7e9;
  padding: 10px;
  box-sizing: border-box;
  border-radius: 5px;
}
</style>
