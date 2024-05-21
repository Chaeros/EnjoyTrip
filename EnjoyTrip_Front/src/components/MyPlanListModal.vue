<script setup>
import { ref, defineEmits, defineProps } from 'vue';

import { getListMyTripPlan } from '@/api/plan/plan';

const { VITE_VUE_API_URL, VITE_VUE_IMAGE_SERVER_URL } = import.meta.env;

const props = defineProps({ userId: Number });
const emit = defineEmits(['MyPlanListModalClose', 'modifyPlanDetail']);
const MyPlanListModalClose = () => {
  emit('MyPlanListModalClose');
};
const modifyPlanDetail = (tripPlanId) => {
  emit('modifyPlanDetail', tripPlanId);
};

const tripPlanRequests = ref([]);
async function getMyTripPlans(userId) {
  getListMyTripPlan(
    userId,
    ({ data }) => {
      console.dir('data');
      console.dir(data);
      tripPlanRequests.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
}
getMyTripPlans(props.userId);
</script>

<template>
  <div class="wrap">
    <div class="container">
      <div
        v-for="tripPlanRequest in tripPlanRequests"
        :key="tripPlanRequest.id"
        class="request-container"
      >
        <div class="trip-plan-container">
          <div class="trip-plan-item">
            <div class="trip-plan-title">
              제목: {{ tripPlanRequest.tripPlan.title }}
            </div>
            <div>내용: {{ tripPlanRequest.tripPlan.content }}</div>
            <div>출발일자: {{ tripPlanRequest.tripPlan.departureDate }}</div>
            <div>도착일자: {{ tripPlanRequest.tripPlan.arrivalDate }}</div>
          </div>
          <button
            @click.prevent="modifyPlanDetail(tripPlanRequest.tripPlan.id)"
          >
            상세보기 & 수정
          </button>
        </div>
        <div class="plan-image-container">
          <div v-if="tripPlanRequest.tripPlan.image !== ''">
            <img
              class="plan-image"
              :src="VITE_VUE_IMAGE_SERVER_URL + tripPlanRequest.tripPlan.image"
            />
          </div>
          <div v-else>
            <img
              :src="
                VITE_VUE_IMAGE_SERVER_URL +
                '/image/uploads/1716297494437_colddragon.png'
              "
              class="plan-image"
            />
          </div>
        </div>
      </div>
      <button @click.prevent="MyPlanListModalClose" class="close-button">
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

.request-container {
  width: 100%;
  margin-bottom: 20px;
  padding: 10px;
  background-color: #e0e7e9; /* E0E7E9 */
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.trip-plan-container {
  max-width: 250px;
  width: 100%;
}

.trip-plan-item {
  margin-bottom: 10px;
}

.trip-plan-title {
  font-size: 22px;
  font-weight: bold;
  color: #354649; /* 6C7A89 */
  margin-bottom: 5px;
}

button {
  display: inline-block;
  padding: 10px 20px;
  margin-top: 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #96b3b6; /* 96B3B6 */
  color: #fff; /* 흰색 텍스트 */
  font-size: 16px;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #88a0a7; /* 88A0A7 */
}

.close-button {
  background-color: #96b3b6; /* 닫기 버튼 배경색 */
}

.close-button:hover {
  background-color: #354649; /* 닫기 버튼 호버 색상 */
}

.plan-image {
  height: 180px;
  margin: 10px 0;
}
</style>
