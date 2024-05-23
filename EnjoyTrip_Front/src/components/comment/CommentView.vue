<template>
  <div class="comment-page">
    <div class="comment-box">
      <div class="comment-input-box">
        <div class="comment-count">
          <div class="comment-count-content">
            {{ commentTotalCount }} 개 댓글
          </div>
        </div>
        <div class="comment-input">
          <input
            class="comment-input-text"
            type="text"
            v-model="commentDto.content"
            @keyup.enter="clickAddCommentBtn"
            placeholder="댓글을 입력하세요"
          />
          <button
            type="button"
            class="btn btn-outline-secondary comment-submit-btn"
            @click="clickAddCommentBtn"
          >
            <b>댓글 작성</b>
          </button>
        </div>
      </div>
      <div class="comment-list">
        <template v-for="comment in comments" :key="comment.id">
          <CommentItem
            :comment="comment"
            @click-remove-commentBtn="clickRemoveCommentBtn"
            @click-modify-commentBtn="clickModifyCommentBtn"
          />
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

const clickRemoveCommentBtn = () => {
  bringCommentList();
  bringCommentCount();
};

const clickModifyCommentBtn = () => {
  bringCommentList();
};

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
      commentTotalCount.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
};

bringCommentCount();
</script>

<style scoped>
.comment-page {
  display: flex;
  justify-content: center;
  /* padding: 20px; */
  background-color: #f9f9f9;
}

.comment-box {
  width: 100%;
  max-width: 1200px;
  background-color: #ffffff;
  /* padding: 20px; */
  /* border-radius: 8px; */
  /* box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); */
}

.comment-input-box {
  margin-bottom: 20px;
}

.comment-count-content {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.comment-input {
  display: flex;
  align-items: center;
  gap: 10px;
}

.comment-input-text {
  flex-grow: 1;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ced4da;
  border-radius: 4px;
}

.comment-submit-btn {
  padding: 10px 20px;
  font-size: 16px;
  /* border-radius: 20px; */
}

.manage-btn {
  margin-left: 10px;
  /* border: none; */
}

.comment-submit-btn > b {
  font-size: 20px;
  font-weight: bold;
}

.comment-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>
