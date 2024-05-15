<template>
  <div class="comment-page">
    <div class="comment-box">
      <div class="comment-input-box">
        <div class="comment-count">
          <div class="comment-count-content">n개 댓글</div>
          <div />
        </div>
        <div class="comment-input">
          <input
            class="comment-input-text"
            type="text"
            v-model="commentDto.content"
          />
          <button @click="clickAddCommentBtn">댓글 작성</button>
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
import {
  addComment,
  getCommentList,
  removeComment,
  modifyComment,
} from "@/api/attraction-board-comment/attraction-board-comment.js";
import { useMemberStore } from "@/store/member";
import { storeToRefs } from "pinia";
const memberStore = useMemberStore();
const { userInfo, isLogin } = storeToRefs(memberStore);
const { attractionBoardReviewId } = defineProps({
  attractionBoardReviewId: Number,
});

console.log(userInfo.value);
const commentDto = ref({
  content: "",
  attractionBoardId: attractionBoardReviewId,
  memberId: userInfo.value.id,
});

const bringCommentList = () => {
  getCommentList(
    attractionBoardReviewId,
    (response) => {
      console.log("@@@@@@@@@@@@@@@@@@@@@@@@@");
      console.log(response.data);
    },
    (error) => {
      console.log(error);
    }
  );
};

bringCommentList();

const clickAddCommentBtn = () => {
  addComment(
    commentDto.value,
    (response) => {
      console.log(response);
    },
    (error) => {
      console.log(error);
    }
  );
};

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
