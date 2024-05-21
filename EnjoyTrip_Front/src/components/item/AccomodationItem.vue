<script setup>
import { defineProps, defineEmits } from 'vue';

const { attraction } = defineProps({ attraction: Object });
const emit = defineEmits(['clickAccomodationAdd', 'openAttractionDetailModal']);

const clickAccomodationAdd = () => {
  emit('clickAccomodationAdd', attraction);
};

const openAttractionDetailModal = (contentId) => {
  emit('openAttractionDetailModal', contentId);
};
</script>

<template>
  <div class="attraction-card">
    <div
      class="attraction-flex"
      @click.prevent="
        openAttractionDetailModal(attraction.attractionInfo.contentId)
      "
    >
      <img class="attraction-img" :src="attraction.attractionInfo.firstImage" />
      <div class="attraction-content">
        <div class="attraction-title">
          {{ attraction.attractionInfo.title }}
        </div>
        <p class="attraction-address">
          주소 : {{ attraction.attractionInfo.addr1 }}
        </p>
        <div class="attraction-indicators">
          <p class="attraction-likes">좋아요 : {{ attraction.likeCnt }}</p>
        </div>
      </div>
    </div>
    <button
      id="addBtn"
      type="button"
      class="btn btn-outline-secondary btn-css"
      @click="clickAccomodationAdd"
    >
      +
    </button>
  </div>
</template>

<style scoped>
.attraction-card {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  padding: 16px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s ease;
}

.attraction-card:hover {
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.2);
}

.attraction-flex {
  display: flex;
  align-items: center;
  gap: 16px;
  cursor: pointer;
}

.attraction-content {
  display: flex;
  flex-direction: column;
}

.attraction-img {
  width: 50px;
  height: 70px;
  object-fit: cover;
  border-radius: 10px;
}

.attraction-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}

.attraction-address,
.attraction-likes {
  font-size: 14px;
  color: #666;
  margin: 4px 0;
}

.btn-css {
  margin: 5px;
  width: 40px;
  height: 80px;
  font-size: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 5%;
  border: 2px solid #6c757d;
  color: #6c757d;
  background-color: transparent;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.btn-css:hover {
  background-color: #a3c6c4;
  color: #fff;
}
</style>
