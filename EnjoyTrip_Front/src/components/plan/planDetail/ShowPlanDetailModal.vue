<script setup>
import { ref, defineEmits, defineProps, watch } from "vue";
import axios from "axios";
import { getLocalStorage } from "@/util/localstorage/localstorage";
const { VITE_VUE_API_URL, VITE_VUE_IMAGE_SERVER_URL } = import.meta.env;

const props = defineProps({
  title: String,
  content: String,
  image: String,
  fileName: String,
});

const emit = defineEmits(["showPlanDetailModalToggle", "updateTripPlan"]);

const showPlanDetailModalToggle = () => {
  emit("showPlanDetailModalToggle");
};

const localFileName = ref(props.fileName);

const localTitle = ref(props.title);
const localContent = ref(props.content);
const localImage = ref(props.image);

// watch를 사용하여 props 변경 감지
watch(
  () => props.title,
  (newVal) => {
    localTitle.value = newVal;
  }
);

watch(
  () => props.content,
  (newVal) => {
    localContent.value = newVal;
  }
);

watch(
  () => props.image,
  (newVal) => {
    localImage.value = newVal;
  }
);

watch(
  () => props.fileName,
  (newVal) => {
    localFileName.value = newVal;
  }
);

const updateTripPlan = (event) => {
  if (localTitle.value === "") {
    window.alert("이름을 설정해주세요");
    return;
  }

  if (localContent.value === "") {
    window.alert("내용을 설정해주세요");
    return;
  }

  emit(
    "updateTripPlan",
    localTitle.value,
    localContent.value,
    localImage.value
  );
  window.alert("일정이 업데이트 되었습니다.");
  emit("showPlanDetailModalToggle");
};

const handleFileUpload = async (event) => {
  const file = event.target.files[0];
  if (file) {
    localFileName.value = file.name;
    const formData = new FormData();
    formData.append("file", file);

    try {
      let accessToken = getLocalStorage("access_token");
      axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
      const response = await axios.post(
        VITE_VUE_IMAGE_SERVER_URL + "/image/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      console.log("File uploaded successfully:", response.data);
      localImage.value = response.data.url;
      console.log(localImage.value);
      console.log(response.data.url);
      event.target.value = "";
    } catch (error) {
      console.error("Error uploading file:", error);
    }
  }
};
</script>

<template>
  <div class="show-plan-detail-modal-wrap">
    <div class="show-plan-detail-modal-container">
      <div class="attach-represant-img-box">
        <div class="attraction-represent">
          <div>대표 이미지를 선택해주세요!</div>
          <input type="file" @change="handleFileUpload" />
          <!-- <span v-if="localFileName">{{ localFileName }}</span> -->
        </div>
        <div v-if="localImage !== ''" class="plan-image-container">
          <img
            class="plan-image"
            :src="VITE_VUE_IMAGE_SERVER_URL + localImage"
          />
        </div>
      </div>
      <div class="plan-title">
        <label for="title">여행 이름</label>
        <input type="text" name="title" v-model="localTitle" />
      </div>
      <div class="plan-content">
        <label for="content">여행 설명</label>
        <textarea name="content" v-model="localContent" />
      </div>
      <div class="plan-btns">
        <button
          class="show-plan-detail-modal-close-btn"
          @click.prevent="showPlanDetailModalToggle"
        >
          닫기
        </button>
        <button class="plan-detail-confirm-btn" @click.prevent="updateTripPlan">
          일정 확정
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.show-plan-detail-modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4); /* 반투명 검정 배경 */
  z-index: 100;
}

.show-plan-detail-modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 650px;
  height: 70%;
  overflow-y: auto;
  background: #fff; /* F1F5F6 */
  border-radius: 10px;
  padding: 20px;
  padding-top: 35px;
  box-sizing: border-box;
  border: 2px solid #e0e7e9; /* E0E7E9 */
  color: #354649; /* 354649 */
}

.plan-title {
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
}

.plan-title label {
  font-weight: bold;
  color: #354649; /* 354649 */
}

.plan-title input {
  width: 80%;
  border: 2px solid #a3c6c4; /* A3C6C4 */
  border-radius: 5px;
  padding: 5px;
  background-color: #e0e7e9; /* E0E7E9 */
  color: #354649; /* 354649 */
}

.plan-content {
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
}

.plan-content label {
  font-weight: bold;
  color: #354649; /* 354649 */
}

.plan-content textarea {
  width: 80%;
  height: 100px;
  border: 2px solid #a3c6c4; /* A3C6C4 */
  border-radius: 5px;
  padding: 5px;
  background-color: #e0e7e9; /* E0E7E9 */
  color: #354649; /* 354649 */
}

.plan-btns {
  display: flex;
  justify-content: space-around;
  height: 40px;
}

.plan-btns button {
  width: 40%;
  height: 100%;
  background-color: #a3c6c4; /* A3C6C4 */
  border: none;
  border-radius: 3px;
  color: #f1f5f6; /* F1F5F6 */
  font-weight: bold;
  transition: background-color 0.3s;
}

.plan-btns button:hover {
  background-color: #96b3b6; /* 96B3B6 */
}

.plan-btns button:active {
  background-color: #88a0a7; /* 88A0A7 */
}

/* .local-image {
  width: 150px;
} */

.attraction-represent {
  margin-left: 30px;
  margin-bottom: 30px;
}

.attach-represant-img-box {
  width: 100%;
  display: flex;
}

.plan-image-container {
  width: 100%;
}

.plan-image {
  width: 200px;
  margin-bottom: 30px;
}
</style>
