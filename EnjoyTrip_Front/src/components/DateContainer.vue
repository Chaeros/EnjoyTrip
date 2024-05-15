<script setup>
import { defineProps } from 'vue';
const { selectedAttractionsByDate, date } = defineProps({
  selectedAttractionsByDate: Array,
  date: Number,
});
console.dir('date는');
console.dir(date);
</script>

<template>
  <div class="container">
    <div v-for="(attraction, index) in selectedAttractionsByDate[date - 1]">
      <p>{{ attraction.attractionInfo.title }}</p>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100px;
  height: 100vh;
  overflow-y: auto;
  padding: 0;
  border: 0.2px solid black;
}
</style>
