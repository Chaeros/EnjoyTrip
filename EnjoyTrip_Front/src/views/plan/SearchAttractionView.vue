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
import AccomodationAddModal from '@/components/AccomodationAddModal.vue';
import ShowPlanDetailModal from '@/components/ShowPlanDetailModal.vue';

import { useRouter } from 'vue-router';
import { registTripPlan, editTripPlan } from '@/api/plan/plan';

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

const isExistSelectView = ref(false);
const toggleSelectContent = () => {
  isExistSelectView.value = !isExistSelectView.value;
};

const selectAttractionReset = () => {
  selectedAttractions.value.length = 0;
};
const selectAccomodationReset = () => {
  selectedAccomodations.value.length = 0;
};

const deleteAttraction = (index) => {
  selectedAttractions.value.splice(index, 1);
};
const deleteAccomodation = (index) => {
  selectedAccomodations.value.splice(index, 1);
};

const clickAttractionAdd = (attraction) => {
  if (
    !selectedAttractions.value.some(
      (e) => e.attractionInfo.contentId === attraction.attractionInfo.contentId
    )
  ) {
    selectedAttractions.value.push(attraction);
  }
};
const clickAccomodationAdd = (accomodation) => {
  if (
    !selectedAccomodations.value.some(
      (e) =>
        e.attractionInfo.contentId === accomodation.attractionInfo.contentId
    )
  ) {
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

async function registerTripPlan(param) {
  registTripPlan(
    param,
    (maxPlanId) => {
      console.dir(maxPlanId.data);
      tripPlanId.value = maxPlanId.data;
    },
    (error) => {
      console.log(error);
    }
  );
}

const createPlan = () => {
  console.dir('예외 처리');
  // 예외 처리
  if (!departureDate.value || !arrivalDate.value) {
    window.alert('날짜를 정확히 입력해주세요');
    return;
  }

  if (selectedAttractions.value.length === 0) {
    window.alert('1개 이상의 관광지를 선택해주세요');
    return;
  }

  if (selectedAccomodations.value.length === 0) {
    window.alert('1개 이상의 숙소를 선택해주세요');
    return;
  }

  tripPlanRequest.value.tripPlan.departureDate = departureDate.value;
  tripPlanRequest.value.tripPlan.arrivalDate = arrivalDate.value;

  for (let i = 0; i < selectedAttractions.value.length; i++) {
    const attraction = selectedAttractions.value[i];
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

  registerTripPlan(tripPlanRequest.value);

  for (let i = 0; i < totalTripDates.value; i++) {
    selectedAttractionsByDate.value[i] = [];
    selectedAccomodationsByDate.value[i] = [];
  }

  currentView.value = 'plan';
};

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

const dateContainerDragged = (date, index) => {
  console.dir('dateContainer 드래그 처리');
};

const onDrop = (event, date) => {
  const attraction = JSON.parse(event.dataTransfer.getData('attraction'));
  selectedAttractionsByDate.value[date - 1].push(attraction);
};

const onDateDrop = (event, date, index) => {
  const fromDate = event.dataTransfer.getData('date');
  const fromIndex = event.dataTransfer.getData('index');
  const temp = selectedAttractionsByDate.value[date - 1][index];
  selectedAttractionsByDate.value[date - 1][index] =
    selectedAttractionsByDate.value[fromDate - 1][fromIndex];
  selectedAttractionsByDate.value[fromDate - 1][fromIndex] = temp;
};

const onBetweenDrop = (event, date, index) => {
  try {
    const attraction = JSON.parse(attractionData);
    // 유효한 JSON 문자열인 경우 처리
    console.log('Valid JSON:', attraction);
  } catch (error) {
    // 유효하지 않은 JSON 문자열인 경우 처리
    console.error('Invalid JSON:', error);
    // 특정 로직 작성
  }
};

const attractionAddModalOpen = ref(false);
const attractionAddModalToggle = () => {
  attractionAddModalOpen.value = !attractionAddModalOpen.value;
};

const accomodationAddModalOpen = ref(false);
const accomodationAddModalToggle = () => {
  accomodationAddModalOpen.value = !accomodationAddModalOpen.value;
};

const showPlanDetailModalOpen = ref(false);
const showPlanDetailModalToggle = () => {
  showPlanDetailModalOpen.value = !showPlanDetailModalOpen.value;
};

const updateTripPlan = (title, content) => {
  console.dir('title');
  console.dir(title);
  console.dir('content');
  console.dir(content);
  // dto 초기화
  const tripPlanRequest = ref({
    tripPlan: {
      id: tripPlanId.value,
      title: '',
      content: '',
      departureDate: '',
      arrivalDate: '',
      image: '',
      memberId: 1,
    },
    makeTripPlans: [],
  });

  tripPlanRequest.value.tripPlan.departureDate = departureDate.value;
  tripPlanRequest.value.tripPlan.arrivalDate = arrivalDate.value;
  tripPlanRequest.value.tripPlan.title = title;
  tripPlanRequest.value.tripPlan.content = content;

  async function updateTripPlan(param) {
    editTripPlan(
      param,
      (result) => {
        console.dir(result.data);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  for (let i = 0; i < selectedAttractionsByDate.value.length; i++) {
    for (let j = 0; j < selectedAttractionsByDate.value[i].length; j++) {
      const attraction = selectedAttractionsByDate.value[i][j];
      const mtp = {
        sequence: j,
        departureTime: null,
        arrivalTime: null,
        memo: '',
        moveTime: null,
        tripDate: i + 1,
        memberId: 1,
        tripPlanId: tripPlanId.value,
        attractionId: attraction.attractionInfo.contentId,
      };
      tripPlanRequest.value.makeTripPlans.push(mtp);
    }
  }

  console.dir('tripPlanRequest는');
  console.dir(tripPlanRequest.value);
  updateTripPlan(tripPlanRequest.value);
  console.dir('업데이트 완료');
};

const removeAttraction = (date, index) => {
  selectedAttractionsByDate.value[date].splice(index, 1);
};

const modalAttractionAdd = (attraction) => {
  if (!selectedAttractions.value.includes(attraction)) {
    selectedAttractions.value.push(attraction);
  }
};

const modalAccomodationAdd = (attraction) => {
  if (!selectedAccomodations.value.includes(attraction)) {
    selectedAccomodations.value.push(attraction);
  }
};

const currentModalViewAttraction = () => {
  attractionAddModalOpen.value = true;
  accomodationAddModalOpen.value = false;
};
const currentModalViewAccomodation = () => {
  attractionAddModalOpen.value = false;
  accomodationAddModalOpen.value = true;
};

/* 드래그로 화면 분할 처리*/
const dateContainerMaxWidth = ref('500px');
const isDragging = ref(false);

const startDragging = () => {
  isDragging.value = true;
};

const stopDragging = () => {
  isDragging.value = false;
};

const handleMouseMove = (event) => {
  if (!isDragging.value) return;
  const container = document.querySelector('.date-container-container');
  const containerOffsetLeft = container.getBoundingClientRect().left;
  const newWidth = event.clientX - containerOffsetLeft;
  dateContainerMaxWidth.value = `${newWidth}px`;
};

onMounted(() => {
  window.addEventListener('mousemove', handleMouseMove);
  window.addEventListener('mouseup', stopDragging);
});

onUnmounted(() => {
  window.removeEventListener('mousemove', handleMouseMove);
  window.removeEventListener('mouseup', stopDragging);
});
</script>

<template>
  <div class="wrap">
    <div class="all-content" v-show="currentView === 'search'">
      <div class="left-tab">
        <header class="search-header">
          <div class="logo-nav-search">
            <img src="@/img/coldragon.png" class="coldragon-img-search" />
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
              class="btn btn-dark make-plan-btn"
              style="
                --bs-btn-padding-y: 1rem;
                --bs-btn-padding-x: 0.25rem;
                --bs-btn-font-size: 0.8rem;
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
          @toggle-select-content="toggleSelectContent"
        />
        <AccomodationSearch
          v-if="activeTab === 'accomodation'"
          @click-accomodation-add="clickAccomodationAdd"
          @toggle-select-content="toggleSelectContent"
        />
      </div>
    </div>
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

    <AttractionAddModal
      @current-modal-view-attraction="currentModalViewAttraction"
      @current-modal-view-accomodation="currentModalViewAccomodation"
      @attraction-add-modal-toggle="attractionAddModalToggle"
      @modal-attraction-add="modalAttractionAdd"
      v-show="attractionAddModalOpen"
    />
    <AccomodationAddModal
      @current-modal-view-attraction="currentModalViewAttraction"
      @current-modal-view-accomodation="currentModalViewAccomodation"
      @accomodation-add-modal-toggle="accomodationAddModalToggle"
      @modal-accomodation-add="modalAccomodationAdd"
      v-show="accomodationAddModalOpen"
    />
    <ShowPlanDetailModal
      @update-trip-plan="updateTripPlan"
      @show-plan-detail-modal-toggle="showPlanDetailModalToggle"
      v-show="showPlanDetailModalOpen"
    />

    <div class="all-content-plan" v-show="currentView === 'plan'">
      <div class="left-tab-plan">
        <header id="header" class="plan-header">
          <div class="logo-nav-plan">
            <img src="@/img/coldragon.png" class="coldragon-img-plan" />
            <nav class="select-group-plan">
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
            </nav>
          </div>
          <div class="make-plan">
            <button
              type="button"
              id="save-btn"
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
          <TotalSelectedAttractions
            @attraction-add-modal-toggle="attractionAddModalToggle"
            @dragged="handleDragged"
            :selected-attractions="selectedAttractions"
            :selected-accomodations="selectedAccomodations"
          />
        </div>
      </div>
    </div>

    <div
      class="date-container-container"
      v-if="currentView === 'plan'"
      :style="{ maxWidth: dateContainerMaxWidth }"
    >
      <!-- <DateContainer
        v-for="number in totalTripDates"
        v-show="activeDate === 0 || activeDate === number"
        :selected-attractions-by-date="selectedAttractionsByDate"
        :date="number"
        @drop="onDrop($event, number)"
        @dragenter.prevent
        @dragover.prevent
        @date-container-dragged="dateContainerDragged"
        @remove-attraction="removeAttraction"
        @on-date-drop="onDateDrop"
        @on-between-drop="onBetweenDrop"
      ></DateContainer> -->
      <DateContainer
        v-for="number in totalTripDates"
        v-show="activeDate === 0 || activeDate === number"
        :selected-attractions-by-date="selectedAttractionsByDate"
        :date="number"
        @dragenter.prevent
        @dragover.prevent
        @date-container-dragged="dateContainerDragged"
        @remove-attraction="removeAttraction"
        @on-date-drop="onDateDrop"
        @on-between-drop="onBetweenDrop"
      ></DateContainer>
      <div class="splitter" @mousedown="startDragging"></div>
    </div>

    <KakaoMap
      class="kakao-map-container"
      :selectedAttractions="selectedAttractions"
      :selectedAccomodations="selectedAccomodations"
    ></KakaoMap>
  </div>
</template>

<style scoped>
.wrap {
  margin: 0 auto;
  width: 100%; /* 부모 요소 너비 100% */
  display: flex;
  justify-content: center; /* 내부 컨텐츠 가운데 정렬 */
  font-family: 'Poppins', sans-serif;
  font-weight: 600;
  font-style: normal;
}

.all-content {
  display: flex;
  height: 100vh;
  width: 40%; /* 내부 요소 전체 너비 사용 */
  padding: 0;
}

.all-content-plan {
  display: flex;
  height: 100vh;
  width: 320px; /* 내부 요소 전체 너비 사용 */
  padding: 0;
}

.search-header {
  display: flex;
  flex-direction: column;
  width: 17%;
  background-color: #f1f5f6;
  justify-content: space-between;
  padding: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.plan-header {
  width: 120px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f1f5f6;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  align-items: center;
}

.logo-nav-plan {
  width: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.select-group {
  width: 100%;
  padding: 0 10px;
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.select-group-plan {
  width: 100%;
  height: 72vh;
  padding: 0 10px;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  overflow-y: auto;
}

/* Custom Scrollbar for WebKit (Chrome, Safari) */
.select-group-plan::-webkit-scrollbar {
  width: 7px;
}

.select-group-plan::-webkit-scrollbar-track {
  background: #f8f9fa;
  border-radius: 10px;
}

.select-group-plan::-webkit-scrollbar-thumb {
  background-color: #e0e7e9;
  border-radius: 10px;
  border: 2px solid #f8f9fa;
}

.select-group-plan::-webkit-scrollbar-thumb:hover {
  background-color: #6c7a89;
}

.list-group-item {
  padding: 10px 10px;
  font-size: 14px;
  text-align: center;
  text-decoration: none;
  color: #333;
  border: 1px solid #a3c6c4;
  border-radius: 4px;
  background-color: #fff;
  transition: background-color 0.3s, color 0.3s;
}

.list-group-item:hover,
.list-group-item.active {
  background-color: #a3c6c4;
  color: #fff;
  border-color: #a3c6c4;
}

.make-plan {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.btn.btn-dark {
  padding: 15px 8px;
  font-size: 15px;
  background-color: #354649;
  border-color: #354649;
  color: #fff;
  border-radius: 4px;
  transition: background-color 0.3s, border-color 0.3s;
}

.btn.btn-dark:hover {
  background-color: #23272b;
  border-color: #1d2124;
}

.left-tab {
  display: flex;
  width: 100%;
}

.left-tab-plan {
  display: flex;
  width: 320px;
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

.selects {
  display: flex;
}

.kakao-map-container {
  height: 100vh;
  flex-grow: 1;
  z-index: 10;
}

.attractions {
  width: 50%;
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

.date-container-container {
  display: flex;
  max-width: 500px;
  overflow-x: auto;
  border-right: 1px solid #6c7a89;
  position: relative;
}

.splitter {
  width: 5px;
  background-color: yellow;
  cursor: col-resize;
  position: absolute;
  top: 0;
  right: 0;
  height: 100%;
}

/* Custom Scrollbar for WebKit (Chrome, Safari) */
.date-container-container::-webkit-scrollbar {
  width: 7px;
}

.date-container-container::-webkit-scrollbar-track {
  background: #f8f9fa;
  border-radius: 10px;
}

.date-container-container::-webkit-scrollbar-thumb {
  background-color: #e0e7e9;
  border-radius: 10px;
  border: 2px solid #f8f9fa;
}

.date-container-container::-webkit-scrollbar-thumb:hover {
  background-color: #6c7a89;
}

.coldragon-img-search {
  width: 90px;
}

.coldragon-img-plan {
  width: 100px;
  padding: 0 10px;
  padding-top: 5px;
}

.place-save {
  width: 200px;
}

#save-btn {
  margin-top: 5px;
}
</style>
