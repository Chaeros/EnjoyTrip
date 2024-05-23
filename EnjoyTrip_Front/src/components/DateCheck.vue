<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';

const props = defineProps({
  showModal: Boolean,
  departureDate: String,
  arrivalDate: String,
});

const localDepartureDate = ref(props.departureDate);
const localArrivalDate = ref(props.arrivalDate);
const totalTripDates = ref(0);

watch(
  () => props.departureDate,
  (newVal) => {
    localDepartureDate.value = newVal;
  }
);

watch(
  () => props.arrivalDate,
  (newVal) => {
    localArrivalDate.value = newVal;
  }
);

function getTotalTripDates() {
  // Date 객체로 변환
  const firstDate = new Date(localDepartureDate.value);
  const secondDate = new Date(localArrivalDate.value);
  console.log(firstDate);
  console.log(secondDate);

  // 두 날짜의 차이를 밀리초 단위로 계산
  const timeDifference = secondDate - firstDate;
  console.log(timeDifference);

  // 밀리초를 일자로 변환
  // 하루는 24시간, 시간은 60분, 분은 60초, 초는 1000밀리초
  const daysDifference = timeDifference / (1000 * 60 * 60 * 24);

  // 소수점 아래는 버리고 정수 부분만 반환
  totalTripDates.value = Math.abs(Math.floor(daysDifference)) + 1;

  if (Math.abs(daysDifference) >= 7 || daysDifference < 0) {
    alert('여행 기간을 확인해주세요.');
    localDepartureDate.value = '';
    localArrivalDate.value = '';
  }
}

const emit = defineEmits(['toggleModal', 'updateDates']);

const toggleModal = () => {
  emit('toggleModal');
};

const updateDates = () => {
  if (localDepartureDate.value == '' || localArrivalDate.value == '') return;

  getTotalTripDates();
  emit(
    'updateDates',
    localDepartureDate.value,
    localArrivalDate.value,
    totalTripDates.value
  );
};
</script>

<template>
  <div
    v-if="showModal"
    class="modal show d-block"
    tabindex="-1"
    style="background-color: rgba(0, 0, 0, 0.5)"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">
            여행 일자를 선택하세요! (7일 이내로만 선택가능)
          </h5>
          <button type="button" class="btn-close" @click="toggleModal"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="departureDate" class="form-label">Start Date</label>
            <input
              id="departureDate"
              type="date"
              class="form-control"
              v-model="localDepartureDate"
              @change="updateDates"
            />
          </div>
          <div class="mb-3">
            <label for="arrivalDate" class="form-label">End Date</label>
            <input
              id="arrivalDate"
              type="date"
              class="form-control"
              v-model="localArrivalDate"
              @change="updateDates"
            />
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="toggleModal">
            Close
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.4); /* 354649 with opacity */
}

.modal-dialog {
  top: 15%;
}

.modal-content {
  width: 110%;
  background-color: #fff; /* F1F5F6 */
  /* border: 2px solid #e0e7e9; E0E7E9 */
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  color: #354649; /* 354649 */
}

.modal-header {
  background-color: #fff; /* 96B3B6 */
  color: #7c8a99;
  border-bottom: 2px solid #e0e7e9; /* E0E7E9 */
  border-radius: 10px 10px 0 0;
}

.modal-title {
  font-size: 19px;
  font-weight: bold;
}

.modal-footer {
  background-color: #fff; /* 96B3B6 */
  border-top: 2px solid #e0e7e9; /* E0E7E9 */
  border-radius: 0 0 10px 10px;
}

.btn-close {
  background-color: #96b3b6; /* 354649 */
  color: #f1f5f6; /* F1F5F6 */
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
}

.btn-close:hover {
  background-color: #6c7a89; /* 6C7A89 */
}

.btn-secondary {
  background-color: #a3c6c4; /* 88A0A7 */
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
}

.btn-secondary:hover {
  background-color: #6c7a89; /* 6C7A89 */
}

.form-label {
  font-weight: bold;
  color: #96b3b6; /* 354649 */
}

.form-control {
  background-color: #e0e7e9; /* E0E7E9 */
  border: 1px solid #a3c6c4; /* A3C6C4 */
  border-radius: 5px;
  padding: 10px;
  color: #354649; /* 354649 */
}

.form-control:focus {
  border-color: #96b3b6; /* 96B3B6 */
  box-shadow: none;
}
</style>
