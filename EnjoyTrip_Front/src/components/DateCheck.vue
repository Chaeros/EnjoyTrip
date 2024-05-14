<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
  showModal: Boolean,
});

const emit = defineEmits(['toggleModal', 'updateDates']);
const startDate = ref('');
const endDate = ref('');

const toggleModal = () => {
  emit('toggleModal');
};

const updateDates = () => {
  emit('updateDates', { startDate: startDate.value, endDate: endDate.value });
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
          <h5 class="modal-title">Select Travel Dates</h5>
          <button type="button" class="btn-close" @click="toggleModal"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="startDate" class="form-label">Start Date</label>
            <input
              id="startDate"
              type="date"
              class="form-control"
              v-model="startDate"
              @change="updateDates"
            />
          </div>
          <div class="mb-3">
            <label for="endDate" class="form-label">End Date</label>
            <input
              id="endDate"
              type="date"
              class="form-control"
              v-model="endDate"
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
</style>
