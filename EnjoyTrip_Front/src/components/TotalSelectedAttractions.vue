<script setup>
import { ref, defineProps } from 'vue';
// const { attraction } = defineProps({ attraction: Object });
// const emit = defineEmits(["clickAttractionAdd"]);

// const clickAttractionAdd = () => {
//   emit("clickAttractionAdd", attraction);
// };
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
      <h5>관광지</h5>
      <button type="button" @click="attractionAddModalToggle">추가</button>
    </div>

    <div
      class="select-attraction"
      v-for="(attraction, index) in selectedAttractions"
      draggable="true"
      @dragstart="startDrag($event, attraction)"
    >
      <img class="attraction-img" :src="attraction.attractionInfo.firstImage" />
      <div class="select-attraction-content">
        <h6>{{ attraction.attractionInfo.title }}</h6>
        <!-- <p>주소 : {{ attraction.attractionInfo.addr1 }}</p>
        <div class="attraction-indicators">
          <p>좋아요 : {{ attraction.likeCnt }}</p>
        </div> -->
      </div>
    </div>

    <h5>숙소</h5>
    <div
      class="select-attraction"
      v-for="(attraction, index) in selectedAccomodations"
      draggable="true"
      @dragstart="startDrag($event, attraction)"
    >
      <img class="attraction-img" :src="attraction.attractionInfo.firstImage" />
      <div class="select-attraction-content">
        <h6>{{ attraction.attractionInfo.title }}</h6>
        <!-- <p>주소 : {{ attraction.attractionInfo.addr1 }}</p>
        <div class="attraction-indicators">
          <p>좋아요 : {{ attraction.likeCnt }}</p>
        </div> -->
      </div>
    </div>
  </div>
</template>

<style scoped>
.select-attraction {
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
  border: 1px solid; /* 테두리 설정 */
  border-radius: 8px; /* 모서리 둥글게 만들기 */
  padding: 20px; /* 내부 여백 설정 */
  margin: 5px;
}

.attractions {
  overflow-y: auto; /* 세로 스크롤바 자동 표시 */
}

.attraction {
  border: 1px solid; /* 테두리 설정 */
  border-radius: 8px; /* 모서리 둥글게 만들기 */
  padding: 20px; /* 내부 여백 설정 */
  margin: 5px;
}

.attraction-img {
  width: 50px;
}

.container {
  width: 100%;
  height: 100vh;
  overflow-y: auto;
}

.select-attraction-content {
  font-size: 10px;
}

.title-button {
  display: flex;
  justify-content: space-between;
}
</style>
