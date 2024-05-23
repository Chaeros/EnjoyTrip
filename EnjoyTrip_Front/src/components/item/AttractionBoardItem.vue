<script setup>
import { ref, computed, defineProps, defineEmits, onMounted } from 'vue';
const { board } = defineProps({ board: Object });

const { VITE_VUE_API_URL, VITE_VUE_IMAGE_SERVER_URL } = import.meta.env;

const truncatedContent = computed(() => {
  const content = board.content;
  console.dir(content.length);
  return content.length >= 51 ? content.substring(0, 51) + '...' : content;
});

const emit = defineEmits(['seeMore']);

const seeMore = () => {
  // console.dir(board.id);
  emit('seeMore', board.id);
};
</script>

<template>
  <div class="board-container" @click.prevent="seeMore">
    <div v-if="board.imageUrl !== ''" class="board-image">
      <img
        class="board-represent-image"
        :src="VITE_VUE_IMAGE_SERVER_URL + board.imageUrl"
      />
    </div>
    <div v-else class="board-image">
      <img
        class="board-represent-image"
        :src="
          VITE_VUE_IMAGE_SERVER_URL +
          '/image/uploads/1716297494437_colddragon.png'
        "
      />
    </div>
    <div class="board-detail">
      <div class="board-title">
        {{ board.title }}
      </div>
      <div v-html="truncatedContent" class="board-content"></div>
      <div class="board-hit">조회수: {{ board.hit }}</div>
      <div class="board-regdate">작성시간: {{ board.regdate }}</div>
    </div>
  </div>
</template>

<style scoped>
.board-container {
  display: flex;
  border: 2px solid #354649;
  background-color: #f1f5f6;
  padding: 20px;
  border-radius: 10px;
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
}

.board-container:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.board-title {
  font-size: 25px;
  color: #354649;
  margin-bottom: 10px;
}

.board-image {
  width: 150px;
  height: 150px;
  background-color: #e0e7e9;
  margin-right: 20px;
  border-radius: 10px;
  overflow: hidden;
  display: flex;
  align-items: center;
}

.board-represent-image {
  width: 100%;
  object-fit: cover;
}

.board-detail {
  flex: 1;
}

.board-content {
  font-size: 16px;
  color: #6c7a89;
  margin-bottom: 10px;
}

.board-hit {
  font-size: 14px;
  color: #88a0a7;
}

.board-regdate {
  font-size: 14px;
  color: #96b3b6;
}
</style>
