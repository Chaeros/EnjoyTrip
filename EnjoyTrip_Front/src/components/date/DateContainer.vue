<script setup>
import { defineProps, defineEmits, onMounted } from 'vue';
const { selectedAttractionsByDate, date } = defineProps({
  selectedAttractionsByDate: Array,
  date: Number,
});

console.log(selectedAttractionsByDate);

const emit = defineEmits([
  'removeAttraction',
  'dateContainerDragged',
  'onDateDrop',
  'onSpaceDrop',
  'detailPlanModalOpen',
]);
const removeAttraction = (date, index) => {
  emit('removeAttraction', date, index);
};

const startDrag = (event, date, index) => {
  event.dataTransfer.dropEffect = 'move';
  event.dataTransfer.effectAllowed = 'move';
  event.dataTransfer.setData('date', date);
  event.dataTransfer.setData('index', index);
  emit('dateContainerDragged', date, index);
};

const onDateDrop = (event, date, index) => {
  emit('onDateDrop', event, date, index);
};

const onSpaceDrop = (event, date) => {
  emit('onSpaceDrop', event, date);
};

const detailPlanModalOpen = (date, index) => {
  emit('detailPlanModalOpen', date, index);
};

onMounted(() => {
  console.dir('DateContainer');
  console.dir(selectedAttractionsByDate.value);
});
</script>

<template v-if="selectedAttractionsByDate">
  <div class="container">
    <div class="date-title">{{ date }}일차</div>
    <div
      class="attraction-container"
      v-for="(attraction, index) in selectedAttractionsByDate[date - 1]"
    >
      <div
        class="attraction-element"
        draggable="true"
        @dragstart="startDrag($event, date, index)"
        @drop="onDateDrop($event, date, index)"
        @dragenter.prevent
        @dragover.prevent
      >
        <div class="attraction-element-title-container">
          <div class="attraction-element-title">
            {{ attraction.attractionInfo.title }}
          </div>
          <div class="remove-btn-container">
            <button
              class="remove-btn"
              @click.prevent="removeAttraction(date - 1, index)"
            >
              -
            </button>
          </div>
        </div>
        <div class="attraction-element-detail">
          <button @click.prevent="detailPlanModalOpen(date, index)">
            상세 계획
          </button>
        </div>
      </div>
    </div>
    <div
      class="attraction-space"
      @drop="onSpaceDrop($event, date, index)"
    ></div>
  </div>
</template>

<style scoped>
.container {
  min-width: 170px;
  max-width: 170px;
  height: 100vh;
  max-height: 100vh;
  overflow-y: auto;
  padding: 0;
  border: 1px solid #354649;
}

/* Custom Scrollbar for WebKit (Chrome, Safari) */
.container::-webkit-scrollbar {
  width: 7px;
}

.container::-webkit-scrollbar-track {
  background: #f8f9fa;
  border-radius: 10px;
}

.container::-webkit-scrollbar-thumb {
  background-color: #e0e7e9;
  border-radius: 10px;
  border: 2px solid #f8f9fa;
}

.container::-webkit-scrollbar-thumb:hover {
  background-color: #6c7a89;
}

.attraction-container {
  background-color: #fff;
  z-index: 0;
}

.attraction-element {
  z-index: 10;
  /* display: flex; */
  font-size: 13px;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  min-height: 50px;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f1f5f6;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.attraction-element p {
  font-size: 12px;
  margin: 0;
  padding-right: 10px;
  color: #333;
  flex-grow: 1;
}

.attraction-element button {
  padding: 5px 10px;
  font-size: 10px;
  color: #fff;
  background-color: #a3c6c4;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.attraction-element button:hover {
  background-color: #6c7a89;
}

.date-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #e0e7e9;
  border: 1px solid #ddd;
  border-radius: 0;
  text-align: center;
  color: #333;
}

.attraction-between {
  height: 0;
  background-color: skyblue;
}

.attraction-element-title-container {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.attraction-element-detail button {
  background-color: #d1dfe0;
  width: 100%;
  height: 20px;
  padding: 0;
}

.attraction-element-title {
  font-size: 12px;
  line-height: 18px;
  min-width: 110px;
  max-width: 110px;
}

.attraction-space {
  height: 92%;
  background-color: #fff;
}

.remove-btn-container {
  display: flex;
  align-items: center;
}

.remove-btn {
  min-height: 22px;
  max-height: 22px;
}
</style>
