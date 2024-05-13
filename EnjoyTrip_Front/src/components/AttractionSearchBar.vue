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
  <div class="select-content-bar">
    <div class="select-content-wrap">
      <div class="select-content">
        <button @click="selectAttractionReset">초기화</button>
        <div
          class="select-attraction"
          v-for="(attraction, index) in selectedAttractions"
        >
          <img
            class="attraction-img"
            :src="attraction.attractionInfo.firstImage"
          />
          <div class="select-attraction-content">
            <h3>{{ attraction.attractionInfo.title }}</h3>
            <p>주소 : {{ attraction.attractionInfo.addr1 }}</p>
            <div class="attraction-indicators">
              <p>좋아요 : {{ attraction.likeCnt }}</p>
            </div>
          </div>
          <button
            id="deleteBtn"
            type="button"
            class="btn btn-primary"
            @click="deleteAttraction(index)"
          >
            삭제
          </button>
        </div>
      </div>
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

.all-content {
  display: flex;
  height: 100vh;
  max-width: 100%; /* 최대 너비 설정 */
  width: 100%; /* 내부 요소 전체 너비 사용 */
}

#header {
  display: flex;
  flex-direction: column;
  width: 80px;
  background-color: antiquewhite;
}

.left-content {
  display: flex;
  flex-direction: column;
  width: 400px;
}

.select-content-bar {
  z-index: 10;
  position: absolute;
}

.select-content-wrap {
  position: static;
  display: flex;
}

.select-content {
  width: 400px;
  height: 100vh;
  background-color: white;
  z-index: 1000;
  overflow-y: auto; /* 세로 스크롤바 자동 표시 */
  /* display: none; */
}

.select-attraction {
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
  border: 1px solid; /* 테두리 설정 */
  border-radius: 8px; /* 모서리 둥글게 만들기 */
  padding: 20px; /* 내부 여백 설정 */
  margin: 5px;
}

.select-content-button {
  height: 100%;
}

.right-content {
  flex: 1; /* 나머지 공간을 채우도록 설정 */
  background-color: blueviolet;
  width: 100%; /* right-content 영역을 전체 너비로 설정 */
  display: flex; /* 자식 요소들을 수직으로 배치하기 위해 추가 */
  position: relative;
}

.selects {
  display: flex;
}

.kakao-map-container {
  width: 100%;
  height: 100%;
  position: absolute;
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

/* */

.attraction-img {
  width: 100px;
}
</style>
