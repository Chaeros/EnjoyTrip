<script setup>
import { onMounted, ref } from 'vue';
import { defineEmits } from 'vue';
import AttractionItem from '@/components/item/AttractionItem.vue';
const emit = defineEmits(['clickAttractionAdd', 'modalAttractionAdd']);

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
  sidoCode: 0,
  gugunCode: 0,
  contentTypeId: 0,
  keyword: '',
});

const clickAttractionAdd = (attraction) => {
  emit('clickAttractionAdd', attraction);
  emit('modalAttractionAdd', attraction);
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

callSidos(1);
callContentTypes();

/* 무한 스크롤 */

const page = ref(1);
const size = ref(10);
const isLoading = ref(false);
const hasMore = ref(true);
const bottomElement = ref('');

const observer = new IntersectionObserver(
  (entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        console.dir('intersecting');
        loadMoreAttractions();
      }
    });
  },
  { rootMargin: '0px 0px 100px 0px' }
);

async function initSearchAttractions() {
  attractions.value = [];

  if (bottomElement.value) {
    observer.observe(bottomElement.value);
  }

  getListAttraction(
    {
      ...inputInformation.value,
      page: page.value,
      size: size.value,
    },
    ({ data }) => {
      if (data.length < size.value) {
        hasMore.value = false;
      }
      attractions.value.push(...data);
      page.value++;
      isLoading.value = false;
    },
    (error) => {
      console.log(error);
      isLoading.value = false;
    }
  );
}

async function loadMoreAttractions() {
  if (isLoading.value || !hasMore.value) return;

  isLoading.value = true;
  getListAttraction(
    {
      ...inputInformation.value,
      page: page.value,
      size: size.value,
    },
    ({ data }) => {
      if (data.length < size.value) {
        hasMore.value = false;
      }
      attractions.value.push(...data);
      page.value++;
      isLoading.value = false;
    },
    (error) => {
      console.log(error);
      isLoading.value = false;
    }
  );
}
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
        @click.prevent="initSearchAttractions"
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
      <div ref="bottomElement" class="bottom-element"></div>
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
  flex-grow: 1;
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

.bottom-element {
  height: 1px;
  width: 100%;
}
</style>
