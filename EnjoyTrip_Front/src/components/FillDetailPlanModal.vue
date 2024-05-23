<script setup>
import { ref, watch, defineEmits, defineProps } from 'vue';

const localDepartureTime = ref('');
const localArrivalTime = ref('');
const localMemo = ref('');
const localMoveTime = ref('');
const props = defineProps({
  departureTime: String,
  arrivalTime: String,
  memo: String,
  moveTime: String,
});

const emit = defineEmits(['submitPlanDetail', 'planDetailToggle']);

// Reset local state
const resetLocalState = () => {
  localDepartureTime.value = '';
  localArrivalTime.value = '';
  localMemo.value = '';
  localMoveTime.value = '';
};

// Watch props and update local state
watch(
  () => props.departureTime,
  (newVal) => {
    localDepartureTime.value = newVal;
  },
  { immediate: true }
);

watch(
  () => props.arrivalTime,
  (newVal) => {
    localArrivalTime.value = newVal;
  },
  { immediate: true }
);

watch(
  () => props.memo,
  (newVal) => {
    localMemo.value = newVal;
  },
  { immediate: true }
);

watch(
  () => props.moveTime,
  (newVal) => {
    localMoveTime.value = newVal;
  },
  { immediate: true }
);

// watch(
//   () => props.fillDetailPlanModalOpen,
//   (newVal) => {
//     resetLocalState();
//   },
//   { immediate: true }
// );

const submitPlanDetail = () => {
  emit(
    'submitPlanDetail',
    localDepartureTime.value,
    localArrivalTime.value,
    localMemo.value,
    localMoveTime.value
  );
  window.alert('적용되었습니다.');
};

const planDetailToggle = () => {
  emit('planDetailToggle');
  resetLocalState();
};
</script>

<template>
  <div class="wrap">
    <div class="container">
      <label for="departureTime">시작 시간</label>
      <input type="time" name="departureTime" v-model="localDepartureTime" />
      <label for="arrivalTime">끝 시간</label>
      <input type="time" name="arrivalTime" v-model="localArrivalTime" />
      <label for="memo">메모</label>
      <textarea name="memo" v-model="localMemo"></textarea>
      <label for="moveTime">다음 목적지로 이동 시간:</label>
      <input
        type="text"
        id="moveTime"
        name="moveTime"
        v-model="localMoveTime"
      />
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
  height: 57%;
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
