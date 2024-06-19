<script setup>
import { defineEmits } from 'vue';
import AccomodationSearch from '@/components/accomodation/accomodationSearch/AccomodationSearch.vue';

const emit = defineEmits([
  'accomodationAddModalToggle',
  'modalAccomodationAdd',
  'currentModalViewAttraction',
  'currentModalViewAccomodation',
]);

const accomodationAddModalToggle = () => {
  emit('accomodationAddModalToggle');
};

const modalAccomodationAdd = (attraction) => {
  emit('modalAccomodationAdd', attraction);
};

const currentModalViewAttraction = (attraction) => {
  emit('currentModalViewAttraction', attraction);
};

const currentModalViewAccomodation = (attraction) => {
  emit('currentModalViewAccomodation', attraction);
};
</script>

<template>
  <div class="attraction-add-modal-wrap">
    <div class="attraction-add-modal-container">
      <button @click.prevent="currentModalViewAttraction" class="view-button">
        관광지
      </button>
      <button @click.prevent="currentModalViewAccomodation" class="view-button">
        숙소
      </button>
      <AccomodationSearch @modal-accomodation-add="modalAccomodationAdd" />
      <button
        class="attraction-add-modal-close-btn close-button"
        @click.prevent="accomodationAddModalToggle"
      >
        닫기
      </button>
    </div>
  </div>
</template>

<style scoped>
.attraction-add-modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  z-index: 100;
}

.attraction-add-modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  height: 80%;
  overflow-y: auto;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}

.view-button {
  display: inline-block;
  margin: 5px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #88a0a7; /* 버튼 배경색 */
  color: #fff; /* 텍스트 색상 */
  font-size: 16px;
  transition: background-color 0.3s ease;
}

.view-button:hover {
  background-color: #6c7a89; /* 버튼 호버 색상 */
}

.attraction-add-modal-close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
}

.close-button {
  background-color: #96b3b6; /* 닫기 버튼 배경색 */
  color: #fff; /* 텍스트 색상 */
  font-size: 14px;
  padding: 8px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.close-button:hover {
  background-color: #354649; /* 닫기 버튼 호버 색상 */
}
</style>
