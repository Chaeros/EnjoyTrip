<script setup>
import { defineEmits } from 'vue';
import AccomodationSearch from '@/components/AccomodationSearch.vue';
const emit = defineEmits([
  'accomodationAddModalToggle',
  'modalAccomodationAdd',
  'currentModalViewToggle',
]);
const accomodationAddModalToggle = () => {
  emit('accomodationAddModalToggle');
};
const modalAccomodationAdd = (attraction) => {
  emit('modalAccomodationAdd', attraction);
};
const currentModalViewToggle = (attraction) => {
  emit('currentModalViewToggle', attraction);
};
</script>

<template>
  <div class="attraction-add-modal-wrap">
    <div class="attraction-add-modal-container">
      <button @click.prevent="currentModalViewToggle">관광지 선택하기</button>
      <AccomodationSearch @modal-accomodation-add="modalAccomodationAdd" />
      <button
        class="attraction-add-modal-close-btn"
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
.attraction-add-modal-close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
}
</style>
