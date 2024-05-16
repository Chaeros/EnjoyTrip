<script setup>
import { ref } from 'vue';
import { defineEmits } from 'vue';
import AttractionItem from '@/components/item/AttractionItem.vue';
const emit = defineEmits(['clickAttractionAdd']);

import {
  getListAttraction,
  getListAccomodation,
  getListSido,
  getListGugun,
  getListContentType,
} from '@/api/attraction';

const sidos = ref([]);
const guguns = ref([]);
const contentTypes = ref([]);
const attractions = ref([]);
const inputInformation = ref({
  sidoCode: '',
  gugunCode: '',
  contentTypeId: 0,
  keyword: '',
});

const clickAttractionAdd = (attraction) => {
  emit('clickAttractionAdd', attraction);
};

async function callSidos(sido) {
  getListSido(
    ({ data }) => {
      sidos.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
  callGuguns(sido);
}

async function callGuguns(sido) {
  getListGugun(
    sido,
    ({ data }) => {
      guguns.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
}

async function callContentTypes() {
  getListContentType(
    ({ data }) => {
      contentTypes.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
}

async function searchAttractions() {
  getListAttraction(
    inputInformation.value,
    ({ data }) => {
      attractions.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
}

callSidos(1);
callContentTypes();
</script>

<template>
  <div class="left-content">
    <div class="selects">
      <select
        class="custom-select w-100"
        v-model="inputInformation.sidoCode"
        @change="callGuguns(inputInformation.sidoCode)"
      >
        <option value="-1">시도</option>
        <option
          v-for="sido in sidos"
          :value="sido.sidoCode"
          :key="sido.sidoCode"
        >
          {{ sido.sidoName }}
        </option>
      </select>

      <select class="custom-select w-100" v-model="inputInformation.gugunCode">
        <option disabled value="-1">구군</option>
        <option
          v-for="gugun in guguns"
          :value="gugun.gugunCode"
          :key="gugun.gugunCode"
        >
          {{ gugun.gugunName }}
        </option>
      </select>

      <select
        class="custom-select w-100"
        v-model="inputInformation.contentTypeId"
      >
        <option value="-1">컨텐츠</option>
        <option
          v-for="content in contentTypes"
          :value="content.contentId"
          :key="content.contentId"
        >
          {{ content.contentName }}
        </option>
      </select>
    </div>

    <form class="d-flex">
      <input
        class="form-control me-2"
        type="search"
        placeholder="검색어를 입력하세요"
        aria-label="Search"
        v-model="inputInformation.keyword"
      />
      <button
        class="btn btn-outline-success"
        type="submit"
        @click.prevent="searchAttractions"
      >
        search
      </button>
    </form>

    <div class="attractions">
      <div class="attraction" v-for="attraction in attractions">
        <AttractionItem
          :attraction="attraction"
          @click-attraction-add="clickAttractionAdd"
        />
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

.select-content-button {
  height: 100%;
}

.right-content {
  flex: 1; /* 나머지 공간을 채우도록 설정 */
  background-color: blueviolet;
  width: 100%; /* right-content 영역을 전체 너비로 설정 */
  display: flex; /* 자식 요소들을 수직으로 배치하기 위해 추가 */
  position: relative;
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

<!-- <script setup>
import { ref } from 'vue';

import {
  getListAttraction,
  getListAccomodation,
  getListSido,
  getListGugun,
  getListContentType,
} from '@/api/attraction';

import AttractionItem from '@/components/item/AttractionItem.vue';

const sidos = ref([]);
const guguns = ref([]);
const contentTypes = ref([]);
const attractions = ref([]);
const inputInformation = ref({
  sidoCode: '',
  gugunCode: '',
  contentTypeId: '',
  keyword: '',
});

const selectedAttractions = ref([]);

const isExistSelectView = ref(true);

const toggleSelectContent = () => {
  isExistSelectView.value = !isExistSelectView.value;
};

const addAttraction = (attraction) => {
  selectedAttractions.value.push(attraction);
};

const clickAttractionAdd = (attraction) => {
  if (!selectedAttractions.value.includes(attraction)) {
    selectedAttractions.value.push(attraction);
    kakaoMapRef.value.updateMapLocation();
  } else {
    console.dir('중복 관광지');
  }
};

const deleteAttraction = (index) => {
  console.log(index);
  selectedAttractions.value.splice(index, 1);
  kakaoMapRef.value.updateMapLocation();
};

const selectAttractionReset = () => {
  selectedAttractions.value = [];
};

async function callSidos(sido) {
  getListSido(
    ({ data }) => {
      console.dir(data);
      sidos.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
  callGuguns(sido);
}

async function callGuguns(sido) {
  getListGugun(
    sido,
    ({ data }) => {
      guguns.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
}

async function callContentTypes() {
  getListContentType(
    ({ data }) => {
      console.dir(data);
      contentTypes.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
}

async function searchAttractions() {
  getListAttraction(
    inputInformation.value,
    ({ data }) => {
      console.dir(data);
      attractions.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
}

callSidos(1);
callContentTypes();
</script> -->

<!-- <template>
  <div class="left-content">
    <div class="selects">
      <select
        class="custom-select w-100"
        v-model="inputInformation.sidoCode"
        @change="callGuguns(inputInformation.sidoCode)"
      >
        <option value="-1">시도</option>
        <option
          v-for="sido in sidos"
          :value="sido.sidoCode"
          :key="sido.sidoCode"
        >
          {{ sido.sidoName }}
        </option>
      </select>

      <select class="custom-select w-100" v-model="inputInformation.gugunCode">
        <option disabled value="-1">구군</option>
        <option
          v-for="gugun in guguns"
          :value="gugun.gugunCode"
          :key="gugun.gugunCode"
        >
          {{ gugun.gugunName }}
        </option>
      </select>

      <select
        class="custom-select w-100"
        v-model="inputInformation.contentTypeId"
      >
        <option disabled value="-1">컨텐츠</option>
        <option
          v-for="content in contentTypes"
          :value="content.contentId"
          :key="content.contentId"
        >
          {{ content.contentName }}
        </option>
      </select>
    </div>

    <form class="d-flex">
      <input
        class="form-control me-2"
        type="search"
        placeholder="검색어를 입력하세요"
        aria-label="Search"
        v-model="inputInformation.keyword"
      />
      <button
        class="btn btn-outline-success"
        type="submit"
        @click.prevent="searchAttractions"
      >
        search
      </button>
    </form>

    <div class="attractions">
      <div class="attraction" v-for="attraction in attractions">
        <AttractionItem
          :attraction="attraction"
          @click-attraction-add="clickAttractionAdd"
        />
      </div>
    </div>
  </div>

  
  <div class="select-content-bar">
    <div class="select-content-wrap">
      <div class="select-content" v-if="isExistSelectView === true">
        <button @click="selectAttractionReset">초기화</button>
        <div
          class="select-attraction"
          v-for="(attraction, index) in selectedAttractions"
        >
          <img
            class="attraction-img"
            :src="attraction.attractInfo.firstImage"
          />
          <div class="select-attraction-content">
            <h3>{{ attraction.attractInfo.title }}</h3>
            <p>주소 : {{ attraction.attractInfo.addr1 }}</p>
            <div class="attraction-indicators">
              <p>좋아요 : {{ attraction.likeCnt }}</p>
            </div>
          </div>
          <button
            id="deleteBtn"
            type="button"
            class="btn btn-primary"
            @click="deleteAttraction(index)"
          >
            삭제
          </button>
        </div>
      </div>
      <button class="select-content-button" @click="toggleSelectContent">
        <>
      </button>
    </div>
  </div>
</template> -->

<!-- <style scoped>
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

.select-content-button {
  height: 100%;
}

.right-content {
  flex: 1; /* 나머지 공간을 채우도록 설정 */
  background-color: blueviolet;
  width: 100%; /* right-content 영역을 전체 너비로 설정 */
  display: flex; /* 자식 요소들을 수직으로 배치하기 위해 추가 */
  position: relative;
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
</style> -->
