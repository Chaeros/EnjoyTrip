<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import AttractionSearchBar from '@/components/AttractionSearchBar.vue';
import AccomodationSearchBar from '@/components/AccomodationSearchBar.vue';
import KakaoMap from '@/components/KakaoMap.vue';
import AttractionSearch from '@/components/AttractionSearch.vue';
import AccomodationSearch from '@/components/AccomodationSearch.vue';
import DateCheck from '@/components/DateCheck.vue';
import TotalSelectedAttractions from '@/components/TotalSelectedAttractions.vue';
import DateContainer from '@/components/DateContainer.vue';
import AttractionAddModal from '@/components/AttractionAddModal.vue';
import ShowPlanDetailModal from '@/components/ShowPlanDetailModal.vue';

import { useRouter } from 'vue-router';
import { registTripPlan } from '@/api/plan/plan';

const router = useRouter();
const currentView = ref('search');

const departureDate = ref('');
const arrivalDate = ref('');
const totalTripDates = ref(0);
const selectedAttractions = ref([]);
const selectedAccomodations = ref([]);
const selectedAttractionsByDate = ref([]);
const selectedAccomodationsByDate = ref([]);

const showModal = ref(false);

const activeTab = ref('attraction');
function setActiveTab(tab) {
  activeTab.value = tab;
  if (activeTab.value === 'dateCheck') {
    toggleModal();
  }
}

const activeDate = ref(0);
function setActiveDate(date) {
  activeDate.value = date;
  console.dir(activeDate.value);
}

function toggleModal() {
  showModal.value = !showModal.value;
}

const isExistSelectView = ref(true);
const toggleSelectContent = () => {
  isExistSelectView.value = !isExistSelectView.value;
};

const selectAttractionReset = () => {
  selectedAttractions.value = [];
};
const selectAccomodationReset = () => {
  selectedAccomodations.value = [];
};

const deleteAttraction = (index) => {
  selectedAttractions.value.splice(index, 1);
};
const deleteAccomodation = (index) => {
  selectedAccomodations.value.splice(index, 1);
};

const clickAttractionAdd = (attraction) => {
  if (!selectedAttractions.value.includes(attraction)) {
    selectedAttractions.value.push(attraction);
  }
};
const clickAccomodationAdd = (accomodation) => {
  if (!selectedAccomodations.value.includes(accomodation)) {
    selectedAccomodations.value.push(accomodation);
  }
};

const date = ref('');

function handleDateUpdate(updatedDate) {
  date.value = updatedDate;
}

const tripPlanId = ref('');
const tripPlanRequest = ref({
  tripPlan: {
    title: '',
    content: '',
    departureDate: '',
    arrivalDate: '',
    image: '',
    memberId: 1,
  },
  makeTripPlans: [],
});
const createPlan = () => {
  console.dir('하이');
  tripPlanRequest.value.tripPlan.departureDate = departureDate.value;
  tripPlanRequest.value.tripPlan.arrivalDate = arrivalDate.value;

  for (let i = 0; i < selectedAttractions.value.length; i++) {
    const attraction = selectedAttractions.value[i];
    // console.dir(attraction);
    const mtp = {
      sequence: 0,
      departureTime: null,
      arrivalTime: null,
      memo: '',
      moveTime: null,
      tripDate: 0,
      memberId: 1,
      tripPlanId: 0,
      attractionId: attraction.attractionInfo.contentId,
    };
    tripPlanRequest.value.makeTripPlans.push(mtp);
  }

  for (let i = 0; i < selectedAccomodations.value.length; i++) {
    const attraction = selectedAccomodations.value[i];
    // console.dir(attraction);
    const mtp = {
      sequence: 0,
      departureTime: null,
      arrivalTime: null,
      memo: '',
      moveTime: null,
      tripDate: 0,
      memberId: 1,
      tripPlanId: 0,
      attractionId: attraction.attractionInfo.contentId,
    };
    tripPlanRequest.value.makeTripPlans.push(mtp);
  }

  // console.dir('트립플랜리퀘스트');
  // console.dir(tripPlanRequest.value);
  registerTripPlan(tripPlanRequest.value);
  console.dir('트립플랜아이디');
  console.dir(tripPlanId.value);
  currentView.value = 'plan';
};

async function registerTripPlan(param) {
  registTripPlan(
    param,
    (data) => {
      tripPlanId.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
}

const updateDates = (startDate, endDate, totalDates) => {
  departureDate.value = startDate;
  arrivalDate.value = endDate;
  totalTripDates.value = totalDates;

  for (let i = 0; i < totalDates; i++) {
    selectedAttractionsByDate.value[i] = [];
    selectedAccomodationsByDate.value[i] = [];
  }
};

const handleDragged = (attraction) => {
  console.dir('드래그 됨');
};

const onDrop = (event, date) => {
  const attraction = JSON.parse(event.dataTransfer.getData('attraction'));
  console.dir(date);
  selectedAttractionsByDate.value[date - 1].push(attraction);
};

const attractionAddModalOpen = ref(false);
const attractionAddModalToggle = () => {
  attractionAddModalOpen.value = !attractionAddModalOpen.value;
};

const showPlanDetailModalOpen = ref(false);
const showPlanDetailModalToggle = () => {
  showPlanDetailModalOpen.value = !showPlanDetailModalOpen.value;
};
</script>

<template>
  <div class="wrap">
    <div class="all-content" v-if="currentView === 'search'">
      <div class="left-tab">
        <header id="header">
          <div class="logo-nav">
            <h1>
              <img src="@/img/coldragon.png" width="80px" />
            </h1>
            <nav>
              <div class="list-group">
                <div class="select-group">
                  <a
                    href="#"
                    class="list-group-item list-group-item-action"
                    :class="{ active: activeTab === 'dateCheck' }"
                    @click.prevent="setActiveTab('dateCheck')"
                  >
                    날짜 확인
                  </a>
                  <a
                    href="#"
                    class="list-group-item list-group-item-action"
                    :class="{ active: activeTab === 'attraction' }"
                    @click.prevent="setActiveTab('attraction')"
                    >장소 선택</a
                  >
                  <a
                    href="#"
                    class="list-group-item list-group-item-action"
                    :class="{ active: activeTab === 'accomodation' }"
                    @click.prevent="setActiveTab('accomodation')"
                    >숙소 선택</a
                  >
                </div>
              </div>
            </nav>
          </div>
          <div class="make-plan">
            <button
              type="button"
              class="btn btn-dark"
              style="
                --bs-btn-padding-y: 1rem;
                --bs-btn-padding-x: 0.25rem;
                --bs-btn-font-size: 1rem;
              "
              @click="createPlan"
            >
              일정 생성
            </button>
          </div>
        </header>

        <DateCheck
          :show-modal="showModal"
          @toggle-modal="toggleModal"
          @update-dates="updateDates"
        />
        <AttractionSearch
          v-if="activeTab === 'attraction'"
          @click-attraction-add="clickAttractionAdd"
        />
        <AccomodationSearch
          v-if="activeTab === 'accomodation'"
          @click-accomodation-add="clickAccomodationAdd"
        />
      </div>

      <div class="right-content">
        <div class="right-content-search">
          <AttractionSearchBar
            class="search-bar"
            v-if="
              activeTab === 'attraction' &&
              currentView === 'search' &&
              isExistSelectView === true
            "
            @select-attraction-reset="selectAttractionReset"
            @delete-attraction="deleteAttraction"
            :selected-attractions="selectedAttractions"
          ></AttractionSearchBar>
          <AccomodationSearchBar
            class="search-bar"
            v-if="
              activeTab === 'accomodation' &&
              currentView === 'search' &&
              isExistSelectView === true
            "
            @select-accomodation-reset="selectAccomodationReset"
            @delete-accomodation="deleteAccomodation"
            :selected-accomodations="selectedAccomodations"
          ></AccomodationSearchBar>
          <button
            class="select-content-button"
            v-if="currentView === 'search'"
            @click="toggleSelectContent"
          >
            <>
          </button>
        </div>
        <KakaoMap
          ref="kakaoMapRef"
          class="kakao-map-container"
          :selectedAttractions="selectedAttractions"
          :selectedAccomodations="selectedAccomodations"
        ></KakaoMap>
      </div>
    </div>

    <div class="all-content" v-if="currentView === 'plan'">
      <AttractionAddModal
        @attraction-add-modal-toggle="attractionAddModalToggle"
        v-show="attractionAddModalOpen"
      />
      <ShowPlanDetailModal
        @show-plan-detail-modal-toggle="showPlanDetailModalToggle"
        v-show="showPlanDetailModalOpen"
      />
      <div class="left-tab-plan">
        <header id="header">
          <div class="logo-nav">
            <h1>
              <img src="@/img/coldragon.png" width="80px" />
            </h1>
            <nav>
              <div class="list-group">
                <div class="select-group">
                  <a
                    href="#"
                    class="list-group-item list-group-item-action"
                    :class="{ active: activeDate === 0 }"
                    @click.prevent="setActiveDate(0)"
                    >전체 일정</a
                  >
                  <a
                    href="#"
                    v-for="number in totalTripDates"
                    class="list-group-item list-group-item-action"
                    :class="{ active: activeDate === number }"
                    @click.prevent="setActiveDate(number)"
                    >{{ number }}일차</a
                  >
                </div>
              </div>
            </nav>
          </div>
          <div class="make-plan">
            <button
              type="button"
              class="btn btn-dark"
              style="
                --bs-btn-padding-y: 1rem;
                --bs-btn-padding-x: 0.25rem;
                --bs-btn-font-size: 1.1rem;
              "
              @click="showPlanDetailModalToggle"
            >
              저장하기
            </button>
          </div>
        </header>

        <div class="place-save">
          <div><h3>장소 보관함</h3></div>
          <TotalSelectedAttractions
            @attraction-add-modal-toggle="attractionAddModalToggle"
            @dragged="handleDragged"
            :selected-attractions="selectedAttractions"
            :selected-accomodations="selectedAccomodations"
          />
        </div>
      </div>

      <DateContainer
        v-for="number in totalTripDates"
        v-show="activeDate === 0 || activeDate === number"
        :selected-attractions-by-date="selectedAttractionsByDate"
        :date="number"
        @drop="onDrop($event, number)"
        @dragenter.prevent
        @dragover.prevent
      ></DateContainer>

      <div class="right-content">
        <div class="right-content-plan"></div>
        <KakaoMap
          :key="currentView"
          ref="kakaoMapRef"
          class="kakao-map-container"
          :selectedAttractions="selectedAttractions"
          :selectedAccomodations="selectedAccomodations"
        ></KakaoMap>
      </div>
    </div>
  </div>
</template>

<style scoped>
.wrap {
  margin: 0 auto;
  width: 100%; /* 부모 요소 너비 100% */
  display: flex;
  justify-content: center; /* 내부 컨텐츠 가운데 정렬 */
}

.all-content {
  display: flex;
  height: 100vh;
  max-width: 100%; /* 최대 너비 설정 */
  width: 100%; /* 내부 요소 전체 너비 사용 */
  padding: 0;
}

#header {
  display: flex;
  flex-direction: column;
  width: 80px;
  background-color: antiquewhite;
  justify-content: space-between;
}

.left-tab {
  display: flex;
  width: 400px;
}

.left-content {
  display: flex;
  flex-direction: column;
  width: 400px;
}

.resizer {
  background-color: #cbd5e0;
  cursor: ew-resize;
  height: 100%;
  width: 2px;
}

.select-content-bar {
  z-index: 10;
  position: absolute;
}

.select-content-wrap {
  position: static;
  display: flex;
}

.select-content {
  width: 400px;
  height: 100vh;
  background-color: white;
  z-index: 1000;
  overflow-y: auto; /* 세로 스크롤바 자동 표시 */
  /* display: none; */
}

.select-attraction {
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
  border: 1px solid; /* 테두리 설정 */
  border-radius: 8px; /* 모서리 둥글게 만들기 */
  padding: 20px; /* 내부 여백 설정 */
  margin: 5px;
}

.search-bar {
  position: static;
}

.select-content-button {
  position: static;
  height: 100%;
  z-index: 100;
}

.right-content {
  flex: 1; /* 나머지 공간을 채우도록 설정 */
  background-color: blueviolet;
  width: 100%; /* right-content 영역을 전체 너비로 설정 */
  display: flex; /* 자식 요소들을 수직으로 배치하기 위해 추가 */
  position: relative;
}

.right-content-search {
  display: flex;
  position: absolute;
}

.selects {
  display: flex;
}

.kakao-map-container {
  width: 100%;
  height: 100%;
  position: absolute;
}

.attractions {
  overflow-y: auto; /* 세로 스크롤바 자동 표시 */
}

.attraction {
  border: 1px solid; /* 테두리 설정 */
  border-radius: 8px; /* 모서리 둥글게 만들기 */
  padding: 20px; /* 내부 여백 설정 */
  margin: 5px;
}

.attraction-img {
  width: 100px;
}

.list-group {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.left-tab-plan {
  display: flex;
  width: 250px;
}
</style>
