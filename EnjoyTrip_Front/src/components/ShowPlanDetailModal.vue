<script setup>
import { ref, defineEmits } from 'vue';
const emit = defineEmits(['showPlanDetailModalToggle', 'updateTripPlan']);
const showPlanDetailModalToggle = () => {
  emit('showPlanDetailModalToggle');
};

const title = ref('');
const content = ref('');
const updateTripPlan = () => {
  emit('updateTripPlan', title.value, content.value);
  window.alert('일정이 업데이트 되었습니다.');
  emit('showPlanDetailModalToggle');
};
</script>

<template>
  <div class="show-plan-detail-modal-wrap">
    <div class="show-plan-detail-modal-container">
      <div class="plan-title">
        <label for="title">계획 이름</label>
        <input type="text" name="title" v-model="title" />
      </div>
      <div class="plan-content">
        <label for="content">계획 설명</label>
        <textarea name="content" v-model="content" />
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
  background: rgba(0, 0, 0, 0.4);
  z-index: 100;
}

.show-plan-detail-modal-container {
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

.plan-title {
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
}
.plan-title input {
  width: 80%;
}
.plan-content {
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
}

.plan-content textarea {
  width: 80%;
}

.plan-btns {
  display: flex;
  justify-content: space-around;
  height: 40px;
}

.plan-btns button {
  width: 40%;
  height: 100%;
  background-color: antiquewhite;
  border: none;
  border-radius: 3px;
}
</style>
