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
// const departureDate = ref('');
// const arrivalDate = ref('');

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
            여행 일자를 선택하세요!(7일 이내로만 선택가능)
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
}

.modal-dialog {
  top: 20%;
}
</style>
