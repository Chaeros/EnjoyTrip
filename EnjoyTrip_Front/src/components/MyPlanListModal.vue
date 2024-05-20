<script setup>
import { ref, defineEmits } from 'vue';

const { userId } = defineProps({ userId: Number });
const emit = defineEmits(['MyPlanListModalClose']);
const MyPlanListModalClose = () => {
  emit('MyPlanListModalClose');
};

const tripPlanRequest = ref([]);
async function getMyTripPlans(userId) {
  getListGugun(
    userId,
    ({ data }) => {
      tripPlanRequest.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
}
getMyTripPlans(userId);
console.dir(tripPlanRequest.value);
</script>

<template>
  <div class="wrap">
    <div class="container">
      <button @click.prevent="MyPlanListModalClose">닫기</button>
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
</style>
