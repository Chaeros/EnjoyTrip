<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref } from "vue";

const { attraction } = defineProps({ attraction: Object });
const emit = defineEmits(["clickAttractionAdd"]);
// const route = useRoute();
// const router = useRouter();

const clickAttractionAdd = () => {
  console.dir(attraction);
  emit("clickAttractionAdd", attraction);
};
</script>

<template>
  <div class="attraction-flex">
    <img class="attraction-img" :src="attraction.img" />
    <div class="attraction-content">
      <h3>{{ attraction.title }}</h3>
      <p>장소 : {{ attraction.address }}</p>
      <div class="attraction-indicators">
        <p>좋아요 : {{ attraction.like }}</p>
        <p>즐겨찾기 : {{ attraction.bookmark }}</p>
      </div>
    </div>
    <button
      id="addBtn"
      type="button"
      class="btn btn-primary"
      @click="clickAttractionAdd"
    >
      Add
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
