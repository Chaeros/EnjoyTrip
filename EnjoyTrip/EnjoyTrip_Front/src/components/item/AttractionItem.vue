<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref } from "vue";
import { defineProps, defineEmits } from "vue";

const { attraction } = defineProps({ attraction: Object });
const emit = defineEmits(["clickAttractionAdd", "updateMapLocation"]);
const updateMapLocation = () => {
  emit("updateMapLocation", {
    lat: attraction.latitude,
    lng: attraction.longitude
  });
};
// const route = useRoute();
// const router = useRouter();

const clickAttractionAdd = () => {
  emit("clickAttractionAdd", attraction);
};
</script>

<template>
  <div @click="updateMapLocation" class="attraction-flex">
    <img class="attraction-img" :src="attraction.attractInfo.firstImage" />
    <div class="attraction-content">
      
      <h3>{{ attraction.attractInfo.title }}</h3>
      <p>장소 : {{ attraction.attractInfo.addr1 }}</p>
      <div class="attraction-indicators">
        <p>좋아요 : {{ attraction.likeCnt }}</p>
      </div>
    </div>
    <button
      id="addBtn"
      type="button"
      class="btn btn-primary"
      @click="clickAttractionAdd"
    >
      +
    </button>
  </div>
</template>

<style scoped>
.attraction-flex {
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
}
.attraction-content {
  display: flex;
  flex-direction: column;
}

.attraction-img {
  width: 100px;
  /* height: 150px; */
}

.attraction-indicators {
  display: flex;
  flex-direction: row;
}
</style>
