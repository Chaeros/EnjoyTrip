<script setup>
import { ref, computed } from "vue";
import AttractionSearchBar from "@/components/AttractionSearchBar.vue";
import AccomodationSearchBar from "@/components/AccomodationSearchBar.vue";
import KakaoMap from "@/components/KakaoMap.vue";
import AttractionSearch from "@/components/AttractionSearch.vue";
import AccomodationSearch from "@/components/AccomodationSearch.vue";
import DateCheck from "@/components/DateCheck.vue";

const selectedAttractions = ref([]);
const selectedAccomodations = ref([]);

const activeTab = ref("attraction");
function setActiveTab(tab) {
  activeTab.value = tab;
  console.dir(activeTab.value);
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
</script>

<template>
  <div class="wrap">
    <div class="all-content">
      <header id="header">
        <h1>
          <a>로고</a>
        </h1>
        <nav>
          <div class="list-group">
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
        </nav>
      </header>

      <DateCheck v-if="activeTab === 'dateCheck'" />
      <AttractionSearch
        v-if="activeTab === 'attraction'"
        @click-attraction-add="clickAttractionAdd"
      />
      <AccomodationSearch
        v-if="activeTab === 'accomodation'"
        @click-accomodation-add="clickAccomodationAdd"
      />

      <div class="right-content">
        <div class="right-content-search">
          <AttractionSearchBar
            class="search-bar"
            v-if="activeTab === 'attraction' && isExistSelectView === true"
            @select-attraction-reset="selectAttractionReset"
            @delete-attraction="deleteAttraction"
            :selected-attractions="selectedAttractions"
          ></AttractionSearchBar>
          <AccomodationSearchBar
            class="search-bar"
            v-if="activeTab === 'accomodation' && isExistSelectView === true"
            @select-accomodation-reset="selectAccomodationReset"
            @delete-accomodation="deleteAccomodation"
            :selected-accomodations="selectedAccomodations"
          ></AccomodationSearchBar>
          <button class="select-content-button" @click="toggleSelectContent">
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
}

#header {
  display: flex;
  flex-direction: column;
  width: 80px;
  background-color: antiquewhite;
}

.left-content {
  display: flex;
  flex-direction: column;
  width: 400px;
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

/* */

.attraction-img {
  width: 100px;
}
</style>
