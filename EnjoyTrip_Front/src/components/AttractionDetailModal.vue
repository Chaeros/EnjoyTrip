<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';
import { getAttractionDetailByContentId } from '@/api/attraction';

const props = defineProps({ contentId: Number });
const emit = defineEmits(['attractionDetailModalClose']);
const attractionDetailModalClose = () => {
  emit('attractionDetailModalClose');
};

const title = ref('');
const addr1 = ref('');
const zipcode = ref('');
const tel = ref('');
const firstImage = ref('');
const overview = ref('');

async function getDetailByContentId(contentId) {
  await getAttractionDetailByContentId(
    contentId,
    ({ data }) => {
      console.dir(data);
      title.value = data.attractionInfo.title;
      addr1.value = data.attractionInfo.addr1;
      zipcode.value = data.attractionInfo.zipcode;
      tel.value = data.attractionInfo.tel;
      firstImage.value = data.attractionInfo.firstImage;
      overview.value = data.attractionDescription.overview;
    },
    ({ error }) => {
      console.dir(error);
    }
  );
}

// Watch for changes in contentId and call getDetailByContentId when it changes
watch(
  () => props.contentId,
  (newContentId) => {
    console.dir('contentId changed to:', newContentId);
    getDetailByContentId(newContentId);
  },
  { immediate: true } // Call the watcher immediately with the initial value
);
</script>

<template>
  <div class="wrap">
    <div class="container">
      <div><img :src="firstImage" /></div>
      <div>{{ title }}</div>
      <div>{{ addr1 }}</div>
      <div>{{ zipcode }}</div>
      <div>{{ tel }}</div>
      <div>{{ overview }}</div>
      <button @click.prevent="attractionDetailModalClose">닫기</button>
    </div>
  </div>
</template>

<style scoped>
.wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4); /* 354649 with opacity */
  z-index: 100;
}

.container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  height: 80%;
  overflow-y: auto;
  background: #fff; /* F1F5F6 */
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
  border: 2px solid #e0e7e9; /* E0E7E9 */
  color: #354649; /* 354649 */
}

button {
  display: inline-block;
  padding: 10px 20px;
  margin-top: 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #96b3b6; /* 96B3B6 */
  color: #fff; /* 흰색 텍스트 */
  font-size: 16px;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #88a0a7; /* 88A0A7 */
}

.close-button {
  background-color: #96b3b6; /* 닫기 버튼 배경색 */
}

.close-button:hover {
  background-color: #354649; /* 닫기 버튼 호버 색상 */
}

.plan-image {
  height: 180px;
  margin: 10px 0;
}
</style>
