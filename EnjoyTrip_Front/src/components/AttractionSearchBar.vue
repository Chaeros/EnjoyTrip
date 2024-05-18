<script setup>
import { ref, defineEmits } from 'vue';
const { selectedAttractions } = defineProps({ selectedAttractions: Object });

const emit = defineEmits(['selectAttractionReset', 'deleteAttraction']);
const selectAttractionReset = () => {
  emit('selectAttractionReset');
};
const deleteAttraction = (index) => {
  emit('deleteAttraction', index);
};
</script>

<template>
  <div class="select-content">
    <button class="reset-button" @click="selectAttractionReset">
      전체 삭제
    </button>
    <div
      class="select-attraction"
      v-for="(attraction, index) in selectedAttractions"
      :key="index"
    >
      <img class="attraction-img" :src="attraction.attractionInfo.firstImage" />
      <div class="select-attraction-content">
        <div class="attraction-title">
          {{ attraction.attractionInfo.title }}
        </div>
      </div>
      <button
        id="deleteBtn"
        type="button"
        class="delete-button"
        @click="deleteAttraction(index)"
      >
        -
      </button>
    </div>
  </div>
</template>

<style scoped>
.wrap {
  margin: 0 auto;
  width: 100%; /* 부모 요소 너비 100% */
  display: flex;
  justify-content: center; /* 내부 컨텐츠 가운데 정렬 */
}

.select-content {
  width: 12%;
  height: 100vh;
  background-color: white;
  z-index: 1;
  overflow-y: auto; /* 세로 스크롤바 자동 표시 */
  overflow-x: hidden;
  padding: 10px;
}

/* Custom Scrollbar for WebKit (Chrome, Safari) */
.select-content::-webkit-scrollbar {
  width: 8px;
}

.select-content::-webkit-scrollbar-track {
  background: #f8f9fa;
  border-radius: 10px;
}

.select-content::-webkit-scrollbar-thumb {
  background-color: #6c7a89;
  border-radius: 10px;
  border: 2px solid #f8f9fa;
}

.select-content::-webkit-scrollbar-thumb:hover {
  background-color: #6c7a89;
}

.left-content-container {
  margin-bottom: 20px;
}

.select-attraction {
  display: flex;
  justify-content: space-between;
  align-items: center; /* 수직 가운데 정렬 */
  border: 1px solid #ddd; /* 테두리 설정 */
  border-radius: 8px; /* 모서리 둥글게 만들기 */
  padding: 10px; /* 내부 여백 설정 */
  margin: 10px 0;
  background-color: #f9f9f9;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.select-attraction:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.attraction-img {
  width: 30px;
  height: 50px;
  border-radius: 8px;
  margin-right: 10px;
  object-fit: cover;
}

.select-attraction-content {
  flex-grow: 1;
}

.attraction-title {
  font-size: 12px;
  margin: 0;
  color: #333;
}

.attraction-address {
  font-size: 14px;
  color: #666;
  margin: 5px 0;
}

.attraction-indicators p {
  font-size: 14px;
  color: #888;
  margin: 0;
}

.reset-button {
  background-color: #a3c6c4;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.delete-button {
  min-width: 25px;
  max-width: 25px;
  height: 35px;
  padding: 5px;
  margin-left: 10px;
  background-color: #a3c6c4;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.reset-button:hover,
.delete-button:hover {
  background-color: rgb(13, 202, 240);
}

.reset-button {
  width: 100%;
  margin-bottom: 10px;
}
</style>
