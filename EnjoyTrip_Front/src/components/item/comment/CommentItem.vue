<template>
  <div class="comment-wrap" v-if="writerInfo">
    <div class="comment-description">
      <div class="comment-description-detail">
        <img class="profile-img" src="" />
        <div class="description-top">
          <div class="description-top-left">
            <div class="writer-info-box">
              <template v-if="writerInfo.image === null">
                <div>
                  <img
                    class="writer-profile-img"
                    src="@/img/member/default_img.jpg"
                  />
                </div>
              </template>
              <template v-else>
                <div>
                  <img class="writer-profile-img" :src="writerInfo.image" />
                </div>
              </template>
              <div>{{ writerInfo.nickname }}</div>
            </div>
          </div>
          <template v-if="isMyComment">
            <div class="description-top-right">
              <template v-if="isModifyMode">
                <button
                  type="button"
                  class="btn btn-danger"
                  @click="clickModifyCommentBtn"
                >
                  수정
                </button>
                <button
                  type="button"
                  class="btn btn-danger"
                  @click="clickModifyCommentToggleBtn"
                >
                  취소
                </button>
              </template>
              <template v-else>
                <button
                  type="button"
                  class="btn btn-outline-secondary"
                  @click="clickModifyCommentToggleBtn"
                >
                  수정
                </button>
                <button
                  type="button"
                  class="btn btn-outline-secondary"
                  @click="clickRemoveCommentBtn"
                >
                  삭제
                </button>
              </template>
            </div>
          </template>
        </div>
        <div class="date">{{ comment.regdate }}</div>
      </div>
    </div>
    <template v-if="isModifyMode">
      <input
        type="text"
        v-model="modifyCommentContent"
        @keyup.enter="clickModifyCommentBtn"
      />
    </template>
    <template v-else>
      <div class="comment-content">{{ comment.content }}</div>
    </template>
  </div>
</template>

<script setup>
import { getUserInfomationById } from "@/api/member/member.js";
import { ref, onMounted } from "vue";
import { getLocalStorage } from "@/util/localstorage/localstorage.js";
import {
  addComment,
  getCommentList,
  removeComment,
  modifyComment,
} from "@/api/attraction-board-comment/attraction-board-comment.js";
import { defineEmits } from "vue";
const emit = defineEmits(["clickRemoveCommentBtn", "clickModifyCommentBtn"]);
const { comment } = defineProps({ comment: Object });
const writerInfo = ref(null);
const isMyComment = ref(true);
const isModifyMode = ref(false);
const modifyCommentContent = ref("");

const clickModifyCommentToggleBtn = () => {
  isModifyMode.value = !isModifyMode.value;
  if (isModifyMode.value == true) {
    modifyCommentContent.value = comment.content;
  }
};

const clickModifyCommentBtn = () => {
  modifyComment(
    {
      id: comment.id,
      content: modifyCommentContent.value,
    },
    (response) => {
      isModifyMode.value = false;
      emit("clickModifyCommentBtn");
    },
    (error) => {
      console.log(error);
    }
  );
};

const clickRemoveCommentBtn = () => {
  removeComment(
    comment.id,
    (response) => {
      console.log(response);
      emit("clickRemoveCommentBtn");
    },
    (error) => {
      console.log(error);
    }
  );
};

onMounted(() => {
  getWriterInfo();
});

console.log(comment);
const getWriterInfo = () => {
  getUserInfomationById(
    comment.memberId,
    (response) => {
      console.log(response);
      writerInfo.value = response.data;
      console.log(writerInfo.value);
      if (comment.memberId == getLocalStorage("userId")) {
        isMyComment.value = true;
      } else {
        isMyComment.value = false;
      }
    },
    (error) => {
      console.error(error);
      writerInfo.value = {}; // 에러가 발생했을 경우 빈 객체를 설정하여 v-if 조건을 false로 만들 수 있습니다.
    }
  );
};
</script>

<style scope>
.writer {
  font-size: 20px;
}
.comment-wrap {
  width: 100%;
}
.comment-description {
  display: flex;
  width: 100%;
}
.comment-description-detail {
  width: 100%;
}
.description-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.description-top-left {
  align-items: center;
}
.image {
  width: 100%;
}
.writer-info-box {
  display: flex;
}
.writer-profile-img {
  border-radius: 100%;
  width: 50px;
}
</style>
