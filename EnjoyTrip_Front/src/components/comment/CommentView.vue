<template>
  <div class="comment-page">
    <div class="comment-box">
      <div class="comment-input-box">
        <div class="comment-count">
          <div class="comment-count-content">
            {{ commentTotalCount }} 개 댓글
          </div>
          <div />
        </div>
        <div class="comment-input">
          <input
            class="comment-input-text"
            type="text"
            v-model="commentDto.content"
            @keyup.enter="clickAddCommentBtn"
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
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/store/member";
import { storeToRefs } from "pinia";
import { getCommentCount } from "@/api/attraction-board-comment/attraction-board-comment.js";
const memberStore = useMemberStore();
const { userInfo, isLogin } = storeToRefs(memberStore);
const { attractionBoardReviewId } = defineProps({
  attractionBoardReviewId: Number,
});
const comments = ref([]);
const commentTotalCount = ref();
const commentDto = ref({
  content: "",
  attractionBoardId: attractionBoardReviewId,
  memberId: "",
});
const router = useRouter();

console.log(userInfo);
if (userInfo.value !== null) {
  commentDto.value.memberId = userInfo.value.id;
}

const bringCommentList = () => {
  getCommentList(
    attractionBoardReviewId,
    (response) => {
      comments.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
};

bringCommentList();

const clickAddCommentBtn = () => {
  if (isLogin.value === false) {
    alert("로그인 후 댓글 작성이 가능합니다!");
    router.push({ name: "login" });
  } else {
    addComment(
      commentDto.value,
      (response) => {
        console.log(response);
        bringCommentList();
        bringCommentCount();
        commentDto.value.content = "";
      },
      (error) => {
        console.log(error);
      }
    );
  }
};

const bringCommentCount = () => {
  getCommentCount(
    attractionBoardReviewId,
    (response) => {
      console.log(response.data);
      commentTotalCount.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
};
bringCommentCount(commentTotalCount.value);
console.log();
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
