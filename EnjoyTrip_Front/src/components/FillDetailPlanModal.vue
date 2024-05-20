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
      <div>{{ modalDate }}일차 {{ modalIndex }}번째</div>
      <label for="departureTime">시작 시간</label>
      <input type="time" name="departureTime" v-model="departureTime" />
      <label for="arrivalTime">끝 시간</label>
      <input type="time" name="arrivalTime" v-model="arrivalTime" />
      <label for="memo">메모</label>
      <textarea name="memo" v-model="memo"></textarea>
      <label for="moveTime">이동 시간:</label>
      <input type="text" id="moveTime" name="moveTime" v-model="moveTime" />
      <button @click.prevent="submitPlanDetail">적용</button>
      <button @click.prevent="planDetailToggle">닫기</button>
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
  background: rgba(0, 0, 0, 0.4);
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
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}
</style>
