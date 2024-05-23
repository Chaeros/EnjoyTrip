<script setup>
import { getLocalStorage } from '@/util/localstorage/localstorage';
const userId = parseInt(getLocalStorage('userId'));
console.dir('유저아이디');
console.dir(userId);

import { ref, watch } from 'vue';
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
import fillDetailPlanModal from '@/components/FillDetailPlanModal.vue';
import MyPlanListModal from '@/components/MyPlanListModal.vue';
import AttractionDetailModal from '@/components/AttractionDetailModal.vue';

import { useRouter } from 'vue-router';
import {
  registTripPlan,
  editTripPlan,
  getListMyTripPlan,
  getDetailTripPlan,
  removeTripPlan,
  getDetailMakeTripPlan,
} from '@/api/plan/plan';

import {
  getDetailAttractionInfo,
  getAttractionLikeCnt,
} from '@/api/attraction';

const router = useRouter();
const currentView = ref('search');

const departureDate = ref('');
const arrivalDate = ref('');
const totalTripDates = ref(0);
const selectedAttractions = ref([]);
const selectedAccomodations = ref([]);
const selectedAttractionsByDate = ref([]);
const selectedAccomodationsByDate = ref([]);
const selectedAttractionDetailsByDate = ref([]);
const title = ref('');
const content = ref('');
const image = ref('');
const fileName = ref('');

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

// const date = ref('');
const tripPlanId = ref('');
const tripPlanRequest = ref({
  tripPlan: {
    title: '',
    content: '',
    departureDate: '',
    arrivalDate: '',
    image: '',
    memberId: userId,
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

  registerTripPlan(tripPlanRequest.value);
  // 여기서 tripPlanId 업데이트

  for (let i = 0; i < totalTripDates.value; i++) {
    selectedAttractionsByDate.value[i] = [];
    selectedAttractionDetailsByDate.value[i] = [];
  }

  title.value = '';
  content.value = '';
  image.value = '';
  fileName.value = '';

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

const onSpaceDrop = (event, date) => {
  try {
    const attraction = JSON.parse(event.dataTransfer.getData('attraction'));
    console.dir('어트랙션 아이디');
    console.dir(attraction.contentId);
    selectedAttractionsByDate.value[date - 1].push(attraction);
    const makeTripPlan = {
      sequence: selectedAttractionsByDate.value[date - 1].length - 1,
      departureTime: '',
      arrivalTime: '',
      memo: '',
      moveTime: '',
      tripDate: date,
      memberId: userId,
      tripPlanId: tripPlanId.value,
      attractionId: attraction.attractionInfo.contentId,
    };
    selectedAttractionDetailsByDate.value[date - 1].push(makeTripPlan);
    console.dir(selectedAttractionsByDate);
  } catch (error) {
    console.log('Invalid JSON:', error);
  }
};

const onDateDrop = (event, date, index) => {
  const fromDate = event.dataTransfer.getData('date');
  const fromIndex = event.dataTransfer.getData('index');

  const temp = selectedAttractionsByDate.value[date - 1][index];
  selectedAttractionsByDate.value[date - 1][index] =
    selectedAttractionsByDate.value[fromDate - 1][fromIndex];
  selectedAttractionsByDate.value[fromDate - 1][fromIndex] = temp;

  const tripDateTemp =
    selectedAttractionDetailsByDate.value[date - 1][index].tripDate;
  const sequenceTemp =
    selectedAttractionDetailsByDate.value[date - 1][index].sequence;

  selectedAttractionDetailsByDate.value[date - 1][index].tripDate =
    selectedAttractionDetailsByDate.value[fromDate - 1][fromIndex].tripDate;
  selectedAttractionDetailsByDate.value[date - 1][index].sequence =
    selectedAttractionDetailsByDate.value[fromDate - 1][fromIndex].sequence;

  selectedAttractionDetailsByDate.value[fromDate - 1][fromIndex].tripDate =
    tripDateTemp;
  selectedAttractionDetailsByDate.value[fromDate - 1][fromIndex].sequence =
    sequenceTemp;

  const detailTemp = selectedAttractionDetailsByDate.value[date - 1][index];
  selectedAttractionDetailsByDate.value[date - 1][index] =
    selectedAttractionDetailsByDate.value[fromDate - 1][fromIndex];
  selectedAttractionDetailsByDate.value[fromDate - 1][fromIndex] = detailTemp;

  console.dir('데이트 드롭');
  console.dir(selectedAttractionsByDate.value);
  console.dir(selectedAttractionDetailsByDate.value);
};

const attractionAddModalOpen = ref(false);
const attractionAddModalToggle = () => {
  attractionAddModalOpen.value = !attractionAddModalOpen.value;
};

const accomodationAddModalOpen = ref(false);
const accomodationAddModalToggle = () => {
  accomodationAddModalOpen.value = !accomodationAddModalOpen.value;
};

async function updateTripPlanImageTitleContent(planId) {
  await getDetailTripPlan(
    planId,
    ({ data }) => {
      console.dir('타입');
      console.dir(typeof data);
      console.dir(data);
      title.value = data.tripPlan.title;
      content.value = data.tripPlan.content;
      image.value = data.tripPlan.image;
      fileName.value = '';
    },
    ({ error }) => {
      console.dir(error);
    }
  );
}

const showPlanDetailModalOpen = ref(false);
async function showPlanDetailModalToggle() {
  await updateTripPlanImageTitleContent(tripPlanId.value);
  console.dir('트립플랜아이디');
  console.dir(tripPlanId.value);
  showPlanDetailModalOpen.value = !showPlanDetailModalOpen.value;
}

function editMyTripPlan(param) {
  return new Promise((resolve, reject) => {
    editTripPlan(
      param,
      (result) => {
        console.dir(result.data);
        resolve(result);
      },
      (error) => {
        console.log(error);
        reject(error);
      }
    );
  });
}

async function updateTripPlan(title, content, imageUrl) {
  // dto 초기화
  const tripPlanRequest = {
    tripPlan: {
      id: tripPlanId.value,
      title: '',
      content: '',
      departureDate: '',
      arrivalDate: '',
      image: '',
      memberId: userId,
    },
    makeTripPlans: [],
  };

  tripPlanRequest.tripPlan.departureDate = departureDate.value;
  tripPlanRequest.tripPlan.arrivalDate = arrivalDate.value;
  tripPlanRequest.tripPlan.title = title;
  tripPlanRequest.tripPlan.content = content;
  tripPlanRequest.tripPlan.image = imageUrl;
  console.dir(imageUrl);

  for (let i = 0; i < selectedAttractionsByDate.value.length; i++) {
    for (let j = 0; j < selectedAttractionsByDate.value[i].length; j++) {
      const attraction = selectedAttractionsByDate.value[i][j];
      const attractionDetailInfo = selectedAttractionDetailsByDate.value[i][j];
      tripPlanRequest.makeTripPlans.push(attractionDetailInfo);
    }
  }

  console.dir('tripPlanRequest는');
  console.dir(tripPlanRequest);

  try {
    await editMyTripPlan(tripPlanRequest);
    console.dir('업데이트 완료');
  } catch (error) {
    console.log('업데이트 실패:', error);
  }
}

const removeAttraction = (date, index) => {
  console.dir('자 짜르자');
  console.dir(date);
  console.dir(index);
  console.dir(selectedAttractionsByDate.value);
  console.dir(selectedAttractionDetailsByDate.value);
  selectedAttractionsByDate.value[date].splice(index, 1);
  selectedAttractionDetailsByDate.value[date].splice(index, 1);
  console.dir(selectedAttractionsByDate.value);
  console.dir(selectedAttractionDetailsByDate.value);
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

const dateContainerMaxWidth = ref('');
watch(totalTripDates, (newTotalTripDates) => {
  dateContainerMaxWidth.value = `${newTotalTripDates * 170}px`;
});

const departureTime = ref('');
const arrivalTime = ref('');
const memo = ref('');
const moveTime = ref('');
const fetchMakeTripPlanDetails = async (tripPlanId, tripDate, sequence) => {
  const param = {
    tripPlanId: tripPlanId,
    tripDate: tripDate,
    sequence: sequence,
  };

  await getDetailMakeTripPlan(
    param,
    ({ data }) => {
      console.dir('axios받은 데이타');
      console.dir(data);
      console.dir(typeof data);
      if (data && typeof data === 'object') {
        // axios랑 쓴 내용이랑 겹칠 때 쓴 내용을 우선적으로 보여줌
        if (
          selectedAttractionDetailsByDate.value[tripDate - 1][sequence]
            .departureTime !== ''
        ) {
          departureTime.value =
            selectedAttractionDetailsByDate.value[tripDate - 1][
              sequence
            ].departureTime;
        } else {
          departureTime.value = data.departureTime;
        }
        if (
          selectedAttractionDetailsByDate.value[tripDate - 1][sequence]
            .arrivalTime !== ''
        ) {
          arrivalTime.value =
            selectedAttractionDetailsByDate.value[tripDate - 1][
              sequence
            ].arrivalTime;
        } else {
          arrivalTime.value = data.arrivalTime;
        }
        if (
          selectedAttractionDetailsByDate.value[tripDate - 1][sequence].memo !==
          ''
        ) {
          memo.value =
            selectedAttractionDetailsByDate.value[tripDate - 1][sequence].memo;
        } else {
          memo.value = data.memo;
        }
        if (
          selectedAttractionDetailsByDate.value[tripDate - 1][sequence]
            .moveTime !== ''
        ) {
          moveTime.value =
            selectedAttractionDetailsByDate.value[tripDate - 1][
              sequence
            ].moveTime;
        } else {
          moveTime.value = data.moveTime;
        }
        console.dir('굿. maketripplan detail 잘받아옴.');
        console.dir(departureTime.value);
        console.dir(arrivalTime.value);
        console.dir(memo.value);
        console.dir(moveTime.value);
      } else {
        // departureTime.value = '';
        // arrivalTime.value = '';
        // memo.value = '';
        // moveTime.value = '';
        console.dir('maketripplan detail 못받아옴. 셀렉트 디테일은');
        console.dir(tripDate);
        console.dir(selectedAttractionDetailsByDate.value);
        departureTime.value =
          selectedAttractionDetailsByDate.value[tripDate - 1][
            sequence
          ].departureTime;
        arrivalTime.value =
          selectedAttractionDetailsByDate.value[tripDate - 1][
            sequence
          ].arrivalTime;
        memo.value =
          selectedAttractionDetailsByDate.value[tripDate - 1][sequence].memo;
        moveTime.value =
          selectedAttractionDetailsByDate.value[tripDate - 1][
            sequence
          ].moveTime;
      }
    },
    ({ error }) => {
      console.log(error);
    }
  );
};

const modalDate = ref('');
const modalIndex = ref('');
const detailPlanModalOpen = async (date, index) => {
  modalDate.value = date;
  modalIndex.value = index;
  console.dir('열리는 modalDate : ');
  console.dir(modalDate.value);
  console.dir('열리는 modalIndex : ');
  console.dir(modalIndex.value);

  console.dir(selectedAttractionDetailsByDate.value);

  await fetchMakeTripPlanDetails(
    tripPlanId.value,
    modalDate.value,
    modalIndex.value
  );

  fillDetailPlanModalOpen.value = true;
  console.dir(fillDetailPlanModalOpen.value);
};

const fillDetailPlanModalOpen = ref(false);
const planDetailToggle = () => {
  console.dir(fillDetailPlanModalOpen.value);
  fillDetailPlanModalOpen.value = false;
};

const submitPlanDetail = (departureTime, arrivalTime, memo, moveTime) => {
  console.dir('받은 모달');
  console.dir(modalDate.value);
  console.dir(modalIndex.value);

  selectedAttractionDetailsByDate.value[modalDate.value - 1][
    modalIndex.value
  ].departureTime = departureTime;
  selectedAttractionDetailsByDate.value[modalDate.value - 1][
    modalIndex.value
  ].arrivalTime = arrivalTime;
  selectedAttractionDetailsByDate.value[modalDate.value - 1][
    modalIndex.value
  ].memo = memo;
  selectedAttractionDetailsByDate.value[modalDate.value - 1][
    modalIndex.value
  ].moveTime = moveTime;
  console.dir('상세설명');
  console.dir(selectedAttractionDetailsByDate.value);
};

const myPlanListModalOpen = ref(false);
const MyPlanListModalClose = () => {
  myPlanListModalOpen.value = false;
};
const OpenMyPlanListModal = () => {
  updateMyTripPlanList();
  myPlanListModalOpen.value = true;
};

const goHomePage = () => {
  router.push({ name: 'home' });
};

const goSearchPage = () => {
  departureDate.value = '';
  arrivalDate.value = '';
  totalTripDates.value = 0;
  selectedAttractions.value.length = 0;
  selectedAccomodations.value.length = 0;
  selectedAttractionsByDate.value.length = 0;
  title.value = '';
  content.value = '';
  image.value = '';
  fileName.value = '';

  activeTab.value = 'attraction';
  activeDate.value = 0;
  isExistSelectView.value = false;
  tripPlanId.value = 0;
  tripPlanRequest.value = {
    tripPlan: {
      title: '',
      content: '',
      departureDate: '',
      arrivalDate: '',
      image: '',
      memberId: userId,
    },
    makeTripPlans: [],
  };

  console.dir(image.value);
  console.dir(title.value);
  console.dir(content.value);
  console.dir(fileName.value);
  currentView.value = 'search';
};

function getTotalTripDates(startDate, endDate) {
  // Date 객체로 변환
  const firstDate = new Date(startDate);
  const secondDate = new Date(endDate);
  console.log(firstDate);
  console.log(secondDate);

  // 두 날짜의 차이를 밀리초 단위로 계산
  const timeDifference = secondDate - firstDate;
  console.log(timeDifference);

  // 밀리초를 일자로 변환
  // 하루는 24시간, 시간은 60분, 분은 60초, 초는 1000밀리초
  const daysDifference = timeDifference / (1000 * 60 * 60 * 24);

  // 소수점 아래는 버리고 정수 부분만 반환
  totalTripDates.value = Math.abs(Math.floor(daysDifference)) + 1;
}

const indexes = new Map();

async function detailAttractionInfo(contentId, i, j) {
  return new Promise((resolve, reject) => {
    getDetailAttractionInfo(
      contentId,
      ({ data }) => {
        console.dir('어트랙션인포');
        console.dir(data);
        selectedAttractionsByDate.value[i][j] = { attractionInfo: data };
        resolve();
      },
      ({ error }) => {
        console.log(error);
        reject(error);
      }
    );
  });
}

async function getMyTripPlans(planId) {
  await getDetailTripPlan(
    planId,
    ({ data }) => {
      getTotalTripDates(data.tripPlan.departureDate, data.tripPlan.arrivalDate);
      console.dir('데이타');
      console.dir(data);
      console.log('test1 start', totalTripDates.value);
      for (let i = 0; i < totalTripDates.value; i++) {
        selectedAttractionsByDate.value[i] = [];
        selectedAttractionDetailsByDate.value[i] = [];
      }

      console.log('test2 start', data.makeTripPlans.length);
      for (let i = 0; i < data.makeTripPlans.length; i++) {
        const makeTripPlan = data.makeTripPlans[i];
        console.dir('메이크트립');
        console.dir(makeTripPlan);
        selectedAttractionsByDate.value[
          parseInt(makeTripPlan.tripDate) - 1
        ].push(makeTripPlan.attractionId);
        selectedAttractionDetailsByDate.value[
          parseInt(makeTripPlan.tripDate) - 1
        ].push({
          sequence: data.makeTripPlans[i].sequence,
          departureTime: data.makeTripPlans[i].departureTime,
          arrivalTime: data.makeTripPlans[i].arrivalTime,
          memo: data.makeTripPlans[i].memo,
          moveTime: data.makeTripPlans[i].moveTime,
          tripDate: data.makeTripPlans[i].tripDate,
          memberId: userId,
          tripPlanId: tripPlanId.value,
          attractionId: data.makeTripPlans[i].attractionId,
        });
        indexes.set(
          [parseInt(makeTripPlan.tripDate, 10) - 1, makeTripPlan.sequence],
          makeTripPlan.attractionId
        );
      }

      console.dir('selectedAttractionDetailsByDAte');
      console.dir(selectedAttractionDetailsByDate);
      console.dir('인덱시스');
      console.dir(indexes);
      console.log('resolve 실행');
    },
    ({ error }) => {
      console.log(error);
    }
  );

  // 모든 detailAttractionInfo 호출을 기다림
  await Promise.all(
    Array.from(indexes.entries()).map(([key, value]) =>
      detailAttractionInfo(value, key[0], key[1])
    )
  );
}

const modifyPlanDetail = async (planId) => {
  departureDate.value = '';
  arrivalDate.value = '';
  totalTripDates.value = 0;
  selectedAttractions.value.length = 0;
  selectedAccomodations.value.length = 0;
  selectedAttractionsByDate.value.length = 0;
  title.value = '';
  content.value = '';
  tripPlanId.value = planId;

  await getDetailTripPlan(
    planId,
    ({ data }) => {
      console.dir('템프');
      console.dir(data);
      title.value = data.tripPlan.title;
      content.value = data.tripPlan.content;
      image.value = data.tripPlan.image;
      departureDate.value = data.tripPlan.departureDate;
      arrivalDate.value = data.tripPlan.arrivalDate;
    },
    ({ error }) => {
      console.log(error);
    }
  );

  tripPlanId.value = planId;
  tripPlanRequest.value = {
    tripPlan: {
      title: '',
      content: '',
      departureDate: departureDate.value,
      arrivalDate: arrivalDate.value,
      image: '',
      memberId: userId,
    },
    makeTripPlans: [],
  };

  await getMyTripPlans(planId);

  myPlanListModalOpen.value = false;
  currentView.value = 'plan';
};

const contentId = ref(0);
const attractionDetailModalOpen = ref(false);
const attractionDetailModalClose = () => {
  attractionDetailModalOpen.value = false;
};
const openAttractionDetailModal = (id) => {
  attractionDetailModalOpen.value = true;
  contentId.value = id;
  console.dir(id);
  console.dir(contentId.value);
};

async function deletePlanDetail(planId) {
  await removeTripPlan(
    planId,
    ({ data }) => {
      console.dir('삭제 data');
      console.dir(data);
    },
    ({ error }) => {
      console.log(error);
    }
  );
  updateMyTripPlanList();
}

const tripPlanRequests = ref([]);
async function getMyTripPlanList(userId) {
  await getListMyTripPlan(
    userId,
    ({ data }) => {
      console.dir('data');
      console.dir(data);
      tripPlanRequests.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
}

const updateMyTripPlanList = () => {
  getMyTripPlanList(userId);
};
</script>

<template>
  <div class="wrap">
    <div class="all-content" v-show="currentView === 'search'">
      <div class="left-tab">
        <header class="search-header">
          <div class="logo-nav-search">
            <img
              @click="goHomePage"
              src="@/img/coldragon.png"
              class="coldragon-img-search"
            />
            <nav>
              <div class="list-group">
                <div class="select-group">
                  <a
                    href="#"
                    class="list-group-item list-group-item-action"
                    :class="{ active: activeTab === 'dateCheck' }"
                    @click.prevent="setActiveTab('dateCheck')"
                  >
                    여행 일자
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
          <div class="black-btns-search">
            <div class="show-my-plans">
              <button
                id="my-plan-btn"
                type="button"
                class="btn btn-dark make-plan-btn show-my-plans-btn"
                style="
                  --bs-btn-padding-y: 1rem;
                  --bs-btn-padding-x: 0.5rem;
                  --bs-btn-font-size: 0.8rem;
                "
                @click="OpenMyPlanListModal"
              >
                내 계획
              </button>
            </div>
            <div class="make-plan">
              <button
                id="make-planid-btn"
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
          </div>
        </header>

        <DateCheck
          :show-modal="showModal"
          :departure-date="departureDate"
          :arrival-date="arrivalDate"
          @toggle-modal="toggleModal"
          @update-dates="updateDates"
        />
        <AttractionSearch
          v-if="activeTab === 'attraction'"
          @open-attraction-detail-modal="openAttractionDetailModal"
          @click-attraction-add="clickAttractionAdd"
          @toggle-select-content="toggleSelectContent"
        />
        <AccomodationSearch
          v-if="activeTab === 'accomodation'"
          @open-attraction-detail-modal="openAttractionDetailModal"
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
      :title="title"
      :content="content"
      :image="image"
      :file-name="fileName"
      @update-trip-plan="updateTripPlan"
      @show-plan-detail-modal-toggle="showPlanDetailModalToggle"
      v-show="showPlanDetailModalOpen"
    />

    <fillDetailPlanModal
      v-if="fillDetailPlanModalOpen"
      :departure-time="departureTime"
      :arrival-time="arrivalTime"
      :memo="memo"
      :move-time="moveTime"
      :trip-plan-id="parseInt(tripPlanId)"
      @submit-plan-detail="submitPlanDetail"
      @plan-detail-toggle="planDetailToggle"
    ></fillDetailPlanModal>

    <MyPlanListModal
      v-show="myPlanListModalOpen"
      @modify-plan-detail="modifyPlanDetail"
      @delete-plan-detail="deletePlanDetail"
      @my-plan-list-modal-close="MyPlanListModalClose"
      :trip-plan-requests="tripPlanRequests"
      :user-id="userId"
    ></MyPlanListModal>

    <AttractionDetailModal
      v-show="attractionDetailModalOpen"
      :content-id="contentId"
      @attraction-detail-modal-close="attractionDetailModalClose"
    ></AttractionDetailModal>

    <div class="all-content-plan" v-show="currentView === 'plan'">
      <div class="left-tab-plan">
        <header id="header" class="plan-header">
          <div class="logo-nav-plan">
            <img
              @click="goHomePage"
              src="@/img/coldragon.png"
              class="coldragon-img-plan"
            />
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
            <div class="black-btns">
              <div class="make-plan">
                <button
                  type="button"
                  id="back-btn"
                  class="btn btn-dark"
                  style="
                    --bs-btn-padding-y: 1rem;
                    --bs-btn-padding-x: 0.25rem;
                    --bs-btn-font-size: 1.1rem;
                  "
                  @click="goSearchPage"
                >
                  뒤로
                </button>
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
            </div>
          </div>
        </header>

        <div class="place-save">
          <TotalSelectedAttractions
            @attraction-add-modal-toggle="attractionAddModalToggle"
            @dragged="handleDragged"
            @open-attraction-detail-modal="openAttractionDetailModal"
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
        @on-space-drop="onSpaceDrop"
        @detail-plan-modal-open="detailPlanModalOpen"
      ></DateContainer>
    </div>

    <KakaoMap
      class="kakao-map-container"
      :selectedAttractions="selectedAttractions"
      :selectedAccomodations="selectedAccomodations"
    ></KakaoMap>

    <!-- <div class="plan-buttons">
      <button type="button">댓글</button>
      <button type="button">공유</button>
    </div> -->
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
  /* position: relative; */
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
  height: 98%;
  /* margin-bottom: 10px; */
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
  position: relative;
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
  /* overflow-x: auto; */
  position: relative;
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

.coldragon-img-search:hover {
  cursor: pointer;
}

.coldragon-img-plan {
  width: 100px;
  padding: 0 10px;
  padding-top: 5px;
}

.coldragon-img-plan:hover {
  cursor: pointer;
}

.place-save {
  width: 200px;
}

#save-btn {
  margin-top: 5px;
}

#back-btn {
  width: 100%;
  margin: 0;
  height: 100%;
  line-height: 100%;
}

.plan-buttons {
  position: absolute;
  display: flex;
  justify-content: space-between;
  top: 5px;
  right: 5px;
  width: 100px;
  height: 35px;
  z-index: 30;
}

.black-btns-search {
  width: 100%;
  align-items: center;
}

#my-plan-btn {
  width: 100%;
  height: 50px;
}

#make-planid-btn {
  width: 100%;
  height: 50px;
}
</style>
