<script setup>
import { ref, defineEmits, defineProps, watch } from 'vue';

const { VITE_VUE_API_URL, VITE_VUE_IMAGE_SERVER_URL } = import.meta.env;
const { tripPlanRequest } = defineProps({
  tripPlanRequest: Object,
});
</script>

<template>
  <div class="wrap">
    <div class="container">
      <div class="plan-image-container">
        <img
          v-if="tripPlanRequest.tripPlan.image !== ''"
          class="plan-image"
          :src="VITE_VUE_IMAGE_SERVER_URL + tripPlanRequest.tripPlan.image"
        />
        <img
          v-else
          class="plan-image"
          :src="
            VITE_VUE_IMAGE_SERVER_URL +
            '/image/uploads/1716297494437_colddragon.png'
          "
        />
      </div>
    </div>
    <div class="trip-plan-container">
      <div class="trip-plan-item">
        <div class="trip-plan-title">
          {{ tripPlanRequest.tripPlan.title }}
        </div>
        <div class="trip-plan-content">
          {{ tripPlanRequest.tripPlan.content }}
        </div>
        <div class="trip-plan-dates">
          <span>출발: {{ tripPlanRequest.tripPlan.departureDate }}</span>
          <span>도착: {{ tripPlanRequest.tripPlan.arrivalDate }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.wrap {
  margin: 0 5px;
}

.container {
  width: 100%;
  background-color: #e0e7e9;
  display: flex;
  justify-content: center;
  align-items: center;
}

.plan-image-container {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #e0e7e9;
  padding: 10px;
  border-radius: 10px;
}

.plan-image {
  min-width: 320px;
  min-height: 300px;
  max-height: 300px;
  border-radius: 10px;
}

.trip-plan-container {
  background-color: #f1f5f6;
  padding: 10px 20px;
  border-radius: 10px;
  margin-top: 15px;
  margin-left: 7px;
  margin-right: 7px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.trip-plan-item {
  color: #354649;
}

.trip-plan-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #354649;
}

.trip-plan-content {
  font-size: 16px;
  color: #6c7a89;
  margin-bottom: 10px;
}

.trip-plan-dates {
  font-size: 14px;
  color: #88a0a7;
}

.trip-plan-dates span {
  display: block;
  margin-bottom: 5px;
}
</style>
