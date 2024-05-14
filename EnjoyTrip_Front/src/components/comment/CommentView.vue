<template>
  <div class="comment-page">
    <div class="comment-box">
      <div class="comment-input-box">
        <div class="comment-count">
          <div class="comment-count-content">n개 댓글</div>
          <div />
        </div>
        <div class="comment-input">
          <input class="comment-input-text" type="text" />
          <button>댓글 작성</button>
        </div>
        <div class="comment-btn-box">
          <div />
          <button>댓글 작성</button>
        </div>
      </div>
      <div class="comment-list">
        <template v-for="comment in comments" :key="comment.id">
          <CommentItem :comment="comment" />
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import CommentItem from "@/components/item/comment/CommentItem.vue";
import { ref } from "vue";
const comments = ref([
  {
    id: "1",
    content: "dafasdfasdf",
    attraction_board_id: "99",
    member_id: "12",
  },
  {
    id: "1",
    content: "dafasdfasdf",
    attraction_board_id: "99",
    member_id: "12",
  },
  {
    id: "1",
    content: "dafasdfasdf",
    attraction_board_id: "99",
    member_id: "12",
  },
]);
</script>

<style scoped>
.comment-count {
  display: flex;
  justify-content: space-between;
}
.comment-input {
  display: flex;
}
.comment-input-text {
  width: 100%;
}
.comment-btn-box {
  display: flex;
  justify-content: space-between;
}
</style>
