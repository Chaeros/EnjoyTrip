<script setup>
import { defineProps, defineEmits } from 'vue';
const { selectedAttractionsByDate, date } = defineProps({
  selectedAttractionsByDate: Array,
  date: Number,
});
const emit = defineEmits(['removeAttraction']);
const removeAttraction = (date, index) => {
  emit('removeAttraction', date, index);
};

const startDrag = (event, item, index) => {
  event.dataTransfer.dropEffect = 'move';
  event.dataTransfer.effectAllowed = 'move';
  event.dataTransfer.setData('attraction', JSON.stringify(item));
  emit('dragged', item, index);
};
</script>

<template>
  <div class="container">
    <div class="date-title">{{ date }}일차</div>
    <div
      v-for="(attraction, index) in selectedAttractionsByDate[date - 1]"
      draggable="true"
      @dragstart="startDrag($event, attraction, index)"
    >
      <div class="attraction-element">
        <p>{{ attraction.attractionInfo.title }}</p>
        <button @click.prevent="removeAttraction(date - 1, index)">-</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100px;
  height: 100vh;
  overflow-y: auto;
  padding: 0;
  border: 1px solid black;
}

.attraction-element {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  min-height: 50px;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #fff;
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
  background-color: skyblue;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.attraction-element button:hover {
  background-color: #c9302c;
}

.date-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 0;
  text-align: center;
  color: #333;
}
</style>
