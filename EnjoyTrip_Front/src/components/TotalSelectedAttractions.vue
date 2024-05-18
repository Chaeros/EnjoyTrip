<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const { selectedAttractions, selectedAccomodations } = defineProps({
  selectedAttractions: Object,
  selectedAccomodations: Object,
});

const emit = defineEmits(['dragged', 'attractionAddModalToggle']);
const attractionAddModalToggle = () => {
  emit('attractionAddModalToggle');
};

const startDrag = (event, item) => {
  event.dataTransfer.dropEffect = 'move';
  event.dataTransfer.effectAllowed = 'move';
  event.dataTransfer.setData('attraction', JSON.stringify(item));
  emit('dragged', item);
};
</script>

<template>
  <div class="container">
    <div class="title-button">
      <div class="container-title">장소 보관함</div>
      <button
        type="button"
        class="btn btn-light"
        @click="attractionAddModalToggle"
      >
        장소 추가
      </button>
    </div>

    <div class="total-section">
      <div class="section-title">관광지</div>
      <div
        class="select-attraction"
        v-for="(attraction, index) in selectedAttractions"
        :key="index"
        draggable="true"
        @dragstart="startDrag($event, attraction)"
      >
        <img
          class="attraction-img"
          :src="attraction.attractionInfo.firstImage"
        />
        <div class="select-attraction-content">
          <h6 class="attraction-title">
            {{ attraction.attractionInfo.title }}
          </h6>
        </div>
      </div>

      <div class="section-title">숙소</div>
      <div
        class="select-attraction"
        v-for="(accommodation, index) in selectedAccomodations"
        :key="index"
        draggable="true"
        @dragstart="startDrag($event, accommodation)"
      >
        <img
          class="attraction-img"
          :src="accommodation.attractionInfo.firstImage"
        />
        <div class="select-attraction-content">
          <h6 class="attraction-title">
            {{ accommodation.attractionInfo.title }}
          </h6>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100%;
  background-color: #e0e7e9;
  height: 100vh;
  /* overflow-y: auto; */
}

.container-title {
  text-align: center;
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 10px;
}

.title-button {
  padding: 20px;
  margin-bottom: 10px;
}

.btn-light {
  width: 100%;
  font-size: 14px;
  background-color: #a3c6c4;
  color: white;
}

.btn-light:hover {
  background-color: #96b3b6;
  color: white;
}

.select-attraction {
  display: flex;
  align-items: center;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 10px;
  margin-bottom: 10px;
  background-color: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.select-attraction:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.attraction-img {
  width: 40px;
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
  color: #333;
}

.section-title {
  font-size: 18px;
  font-weight: bold;
  color: #343a40;
}

.total-section {
  height: 80vh;
  overflow-y: auto;
  padding: 0 10px;
}

/* Custom Scrollbar for WebKit (Chrome, Safari) */
.total-section::-webkit-scrollbar {
  width: 7px;
}

.total-section::-webkit-scrollbar-track {
  background: #f8f9fa;
  border-radius: 10px;
}

.total-section::-webkit-scrollbar-thumb {
  background-color: #e0e7e9;
  border-radius: 10px;
  border: 2px solid #f8f9fa;
}

.total-section::-webkit-scrollbar-thumb:hover {
  background-color: #6c7a89;
}
</style>
