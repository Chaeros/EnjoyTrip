<script setup>
import { ref, defineEmits, defineProps } from 'vue';

const departureTime = ref('');
const arrivalTime = ref('');
const memo = ref('');
const moveTime = ref('');

const { modalDate, modalIndex } = defineProps({
  modalDate: Number,
  modalIndex: Number,
});

const emit = defineEmits(['submitPlanDetail', 'planDetailToggle']);

const submitPlanDetail = () => {
  console.dir('모달데이트');
  console.dir(modalDate);
  console.dir('모달인덱스');
  console.dir(modalIndex);
  emit(
    'submitPlanDetail',
    modalDate,
    modalIndex,
    departureTime.value,
    arrivalTime.value,
    memo.value,
    moveTime.value
  );
};
const planDetailToggle = () => {
  emit('planDetailToggle');
};
</script>

<template>
  <div class="wrap">
    <div class="container">
      <label for="departureTime">시작 시간</label>
      <input type="time" name="departureTime" v-model="departureTime" />
      <label for="arrivalTime">끝 시간</label>
      <input type="time" name="arrivalTime" v-model="arrivalTime" />
      <label for="memo">메모</label>
      <textarea name="memo" v-model="memo"></textarea>
      <label for="moveTime">이동 시간:</label>
      <input type="text" id="moveTime" name="moveTime" v-model="moveTime" />
      <button @click.prevent="submitPlanDetail" class="apply-button">
        적용
      </button>
      <button @click.prevent="planDetailToggle" class="close-button">
        닫기
      </button>
    </div>
  </div>
</template>

<style scoped>
.wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4); /* 354649 with opacity */
  z-index: 100;
}

.container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  height: 80%;
  overflow-y: auto;
  background: #fff; /* F1F5F6 */
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
  border: 2px solid #e0e7e9; /* E0E7E9 */
  color: #354649; /* 354649 */
}

label {
  display: block;
  color: #6c7a89; /* 6C7A89 */
  margin: 10px 0 5px;
}

input[type='time'],
input[type='text'],
textarea {
  width: calc(100% - 20px);
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #a3c6c4; /* A3C6C4 */
  border-radius: 5px;
  background-color: #e0e7e9; /* E0E7E9 */
  color: #354649; /* 354649 */
}

.apply-button,
.close-button {
  display: inline-block;
  padding: 10px 20px;
  margin-top: 10px;
  margin-right: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.apply-button {
  background-color: #96b3b6; /* 96B3B6 */
  color: #fff;
}

.close-button {
  background-color: #88a0a7; /* 88A0A7 */
  color: #fff;
}
</style>
