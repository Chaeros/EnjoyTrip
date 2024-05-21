<script setup>
import { ref, defineEmits, defineProps, watchEffect } from 'vue';

const props = defineProps({ title: String, content: String });
const emit = defineEmits(['showPlanDetailModalToggle', 'updateTripPlan']);
const showPlanDetailModalToggle = () => {
  emit('showPlanDetailModalToggle');
};

const localTitle = ref(props.title);
const localContent = ref(props.content);

// watchEffect를 사용하여 props 변경 감지
watchEffect(() => {
  localTitle.value = props.title;
});

watchEffect(() => {
  localContent.value = props.content;
});

const updateTripPlan = () => {
  if (localTitle.value === '') {
    window.alert('이름을 설정해주세요');
    return;
  }

  if (localContent.value === '') {
    window.alert('내용을 설정해주세요');
    return;
  }

  emit('updateTripPlan', localTitle.value, localContent.value);
  window.alert('일정이 업데이트 되었습니다.');
  emit('showPlanDetailModalToggle');
};
</script>

<template>
  <div class="show-plan-detail-modal-wrap">
    <div class="show-plan-detail-modal-container">
      <div class="plan-title">
        <label for="title">여행 이름</label>
        <input type="text" name="title" v-model="localTitle" />
      </div>
      <div class="plan-content">
        <label for="content">여행 설명</label>
        <textarea name="content" v-model="localContent" />
      </div>
      <div class="plan-btns">
        <button
          class="show-plan-detail-modal-close-btn"
          @click.prevent="showPlanDetailModalToggle"
        >
          닫기
        </button>
        <button class="plan-detail-confirm-btn" @click.prevent="updateTripPlan">
          일정 확정
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.show-plan-detail-modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4); /* 반투명 검정 배경 */
  z-index: 100;
}

.show-plan-detail-modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  height: 45%;
  overflow-y: auto;
  background: #fff; /* F1F5F6 */
  border-radius: 10px;
  padding: 20px;
  padding-top: 35px;
  box-sizing: border-box;
  border: 2px solid #e0e7e9; /* E0E7E9 */
  color: #354649; /* 354649 */
}

.plan-title {
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
}

.plan-title label {
  font-weight: bold;
  color: #354649; /* 354649 */
}

.plan-title input {
  width: 80%;
  border: 2px solid #a3c6c4; /* A3C6C4 */
  border-radius: 5px;
  padding: 5px;
  background-color: #e0e7e9; /* E0E7E9 */
  color: #354649; /* 354649 */
}

.plan-content {
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
}

.plan-content label {
  font-weight: bold;
  color: #354649; /* 354649 */
}

.plan-content textarea {
  width: 80%;
  height: 100px;
  border: 2px solid #a3c6c4; /* A3C6C4 */
  border-radius: 5px;
  padding: 5px;
  background-color: #e0e7e9; /* E0E7E9 */
  color: #354649; /* 354649 */
}

.plan-btns {
  display: flex;
  justify-content: space-around;
  height: 40px;
}

.plan-btns button {
  width: 40%;
  height: 100%;
  background-color: #a3c6c4; /* A3C6C4 */
  border: none;
  border-radius: 3px;
  color: #f1f5f6; /* F1F5F6 */
  font-weight: bold;
  transition: background-color 0.3s;
}

.plan-btns button:hover {
  background-color: #96b3b6; /* 96B3B6 */
}

.plan-btns button:active {
  background-color: #88a0a7; /* 88A0A7 */
}
</style>
