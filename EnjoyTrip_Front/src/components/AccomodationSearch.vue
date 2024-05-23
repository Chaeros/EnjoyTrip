<script setup>
import { ref } from 'vue';
import { defineEmits } from 'vue';
import AccomodationItem from '@/components/item/AccomodationItem.vue';
const emit = defineEmits([
  'clickAccomodationAdd',
  'modalAccomodationAdd',
  'toggleSelectContent',
  'openAttractionDetailModal',
]);

import {
  getListAccomodation,
  getListSido,
  getListGugun,
} from '@/api/attraction';

const sidos = ref([]);
const guguns = ref([]);
const attractions = ref([]);
const inputInformation = ref({
  sidoCode: 0,
  gugunCode: 0,
  keyword: '',
});

const clickAccomodationAdd = (attraction) => {
  emit('clickAccomodationAdd', attraction);
  emit('modalAccomodationAdd', attraction);
};

const toggleSelectContent = () => {
  emit('toggleSelectContent');
};

const openAttractionDetailModal = (contentId) => {
  emit('openAttractionDetailModal', contentId);
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

callSidos(1);

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
        loadMoreAccomodations();
      }
    });
  },
  { rootMargin: '0px 0px 100px 0px' }
);

async function initSearchAccomodations() {
  // 페이지 번호와 결과를 초기화
  page.value = 1;
  attractions.value = [];
  hasMore.value = true;
  isLoading.value = false;

  if (bottomElement.value) {
    observer.observe(bottomElement.value);
  }

  loadMoreAccomodations();
}

async function loadMoreAccomodations() {
  if (isLoading.value || !hasMore.value) return;

  isLoading.value = true;
  getListAccomodation(
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
    <div class="left-content-container">
      <div class="select-title-container">
        <div class="select-title">숙소를 검색하세요!</div>
        <button
          type="button"
          class="btn btn-outline-light toggle-btn"
          @click.prevent="toggleSelectContent"
        >
          <>
        </button>
      </div>
      <div class="selects">
        <select
          class="custom-select w-100"
          v-model="inputInformation.sidoCode"
          @change="callGuguns(inputInformation.sidoCode)"
        >
          <option value="0">전체</option>
          <option
            v-for="sido in sidos"
            :value="sido.sidoCode"
            :key="sido.sidoCode"
          >
            {{ sido.sidoName }}
          </option>
        </select>

        <select
          class="custom-select w-100"
          v-model="inputInformation.gugunCode"
        >
          <option value="0">전체</option>
          <option
            v-for="gugun in guguns"
            :value="gugun.gugunCode"
            :key="gugun.gugunCode"
          >
            {{ gugun.gugunName }}
          </option>
        </select>
      </div>

      <form class="d-flex search-form">
        <input
          class="form-control"
          type="search"
          placeholder="검색어를 입력하세요"
          aria-label="Search"
          v-model="inputInformation.keyword"
        />
        <button
          class="btn btn-outline-success"
          type="submit"
          @click.prevent="initSearchAccomodations"
        >
          검색
        </button>
      </form>
    </div>

    <div class="attractions">
      <AccomodationItem
        v-for="attraction in attractions"
        :attraction="attraction"
        @click-accomodation-add="clickAccomodationAdd"
        @open-attraction-detail-modal="openAttractionDetailModal"
      />
      <div ref="bottomElement" class="bottom-element"></div>
    </div>
  </div>
</template>

<style scoped>
.left-content {
  width: 100%;
  padding: 20px;
  background-color: #e0e7e9;
  border-right: 1px solid #adb5bd;
  overflow-y: auto;
  overflow-x: hidden;
}

/* Custom Scrollbar for WebKit (Chrome, Safari) */
.left-content::-webkit-scrollbar {
  width: 8px;
}

.left-content::-webkit-scrollbar-track {
  background: #f8f9fa;
  border-radius: 10px;
}

.left-content::-webkit-scrollbar-thumb {
  background-color: #6c7a89;
  border-radius: 10px;
  border: 2px solid #f8f9fa;
}

.left-content::-webkit-scrollbar-thumb:hover {
  background-color: #6c7a89;
}

.left-content-container {
  margin-bottom: 20px;
}

.select-title {
  height: 50px;
  line-height: 50px;
  font-size: 24px;
  font-weight: bold;
}

.selects {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.custom-select {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #adb5bd;
  border-radius: 4px;
  background-color: #f8f9fa;
}

.search-form {
  display: flex;
  gap: 10px;
}

.form-control {
  flex: 1;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #adb5bd;
  border-radius: 4px;
  background-color: #f8f9fa;
}

.btn-outline-success {
  padding: 10px 20px;
  font-size: 16px;
  border: 1px solid #218838;
  color: #218838;
  background-color: transparent;
  border-radius: 4px;
  cursor: pointer;
}

.btn-outline-success:hover {
  background-color: #218838;
  color: #fff;
}

.attractions {
  display: flex;
  flex-direction: column;
  gap: 20px;
  overflow-y: auto;
  padding: 10px 0;
}

.attraction {
  border: 1px solid #adb5bd;
  border-radius: 8px;
  padding: 20px;
  background-color: #f8f9fa;
  display: flex;
  align-items: center;
  gap: 20px;
}

.attraction-img {
  width: 100px;
  border-radius: 8px;
  object-fit: cover;
}

.bottom-element {
  height: 1px;
  width: 100%;
}

.select-title-container {
  align-items: center;
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

.toggle-btn {
  width: 60px;
  height: 50px;
  font-size: 20px;
  background-color: #a3c6c4;
}
</style>
