<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';
import { getAttractionDetailByContentId } from '@/api/attraction';
const { VITE_VUE_API_URL, VITE_VUE_IMAGE_SERVER_URL } = import.meta.env;

const props = defineProps({ contentId: Number });
const emit = defineEmits(['attractionDetailModalClose']);
const attractionDetailModalClose = () => {
  emit('attractionDetailModalClose');
};

const title = ref('');
const addr1 = ref('');
const addr2 = ref('');
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
      addr2.value = data.attractionInfo.addr2;
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
      <div v-if="firstImage !== ''">
        <img class="plan-image" :src="firstImage" />
      </div>
      <div v-else>
        <img
          class="plan-image"
          :src="
            VITE_VUE_IMAGE_SERVER_URL +
            '/image/uploads/1716297494437_colddragon.png'
          "
        />
      </div>
      <div class="title">{{ title }}</div>
      <div class="address">주소: {{ addr1 }}{{ addr2 }}</div>
      <!-- <div class="zipcode">우편번호: {{ zipcode }}</div> -->
      <!-- <div class="tel">전화번호: {{ tel }}</div> -->
      <div class="overview">{{ overview }}</div>
      <button @click.prevent="attractionDetailModalClose" class="close-button">
        닫기
      </button>
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
  width: 600px;
  height: 80%;
  overflow-y: auto;
  background: #fff; /* F1F5F6 */
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
  border: 2px solid #e0e7e9; /* E0E7E9 */
  color: #354649; /* 354649 */
}

/* Custom Scrollbar for WebKit (Chrome, Safari) */
.container::-webkit-scrollbar {
  width: 8px;
}

.container::-webkit-scrollbar-track {
  background: #f8f9fa;
  border-radius: 10px;
}

.container::-webkit-scrollbar-thumb {
  background-color: #6c7a89;
  border-radius: 10px;
  border: 2px solid #f8f9fa;
}

.container::-webkit-scrollbar-thumb:hover {
  background-color: #6c7a89;
}

.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #354649; /* 6C7A89 */
}

.address,
.zipcode,
.tel {
  font-size: 18px;
  margin-bottom: 10px;
  color: #6c7a89; /* 354649 */
}

.overview {
  font-size: 14px;
  margin-bottom: 10px;
  color: #6c7a89;
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
  background-color: #a3c6c4; /* 닫기 버튼 배경색 */
}

.close-button:hover {
  background-color: #354649; /* 닫기 버튼 호버 색상 */
}

.plan-image {
  width: 100%;
  margin: 10px 0;
  border-radius: 5px;
  border: 2px solid #e0e7e9; /* E0E7E9 */
}
</style>
