<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref } from "vue";
import { defineProps, defineEmits } from "vue";

const { attraction } = defineProps({ attraction: Object });
const emit = defineEmits(["clickAttractionAdd"]);

const clickAttractionAdd = () => {
  emit("clickAttractionAdd", attraction);
};
</script>

<template>
  <div class="space-between">
    <div class="attraction-flex">
      <img class="attraction-img" :src="attraction.attractionInfo.firstImage" />
      <div class="attraction-content">
        <div class="attraction-title">
          {{ attraction.attractionInfo.title }}
        </div>
        <p>장소 : {{ attraction.attractionInfo.addr1 }}</p>
        <div class="attraction-indicators">
          <p>좋아요 : {{ attraction.likeCnt }}</p>
        </div>
      </div>
    </div>
    <button
      id="addBtn"
      type="button"
      class="btn btn-outline-secondary btn-css"
      @click="clickAttractionAdd"
    >
      +
    </button>
  </div>
</template>

<style scoped>
.space-between {
  display: flex;
  justify-content: space-between;
}
.attraction-flex {
  display: flex;
  /* justify-content: center; 수평 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
  /* border: 1px solid; */
  border-radius: 10px;
  width: 100%;
  box-shadow: 0 4px 6px rgba(0.1, 0.1, 0.1, 0.1);
}
.attraction-content {
  display: flex;
  flex-direction: column;
}

.attraction-img {
  width: 100px;
  height: 100%;
  margin: 0 5px 0 0;
  /* text-align: center; */
}

.attraction-title {
  font-size: 20px;
  font-weight: bold;
}

.attraction-indicators {
  display: flex;
  flex-direction: row;
}
.btn-css {
  font-size: 20px;
  font-weight: bold;
}
</style>
