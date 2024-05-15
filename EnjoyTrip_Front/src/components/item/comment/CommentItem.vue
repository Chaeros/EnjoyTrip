<template>
  <div class="comment-wrap">
    <div class="comment-description">
      <div class="comment-description-detail">
        <img class="profile-img" src="" />
        <div class="description-top">
          <div class="description-top-left">
            <img class="userProfile" src="" />
            <div class="writer">{{ comment.id }}</div>
          </div>
          <div class="description-top-right">
            <button>수정</button>
            <button>삭제</button>
          </div>
        </div>
        <div class="date">2024년 5월 13일</div>
      </div>
    </div>
    <div class="comment-content">{{ comment.content }}</div>
  </div>
</template>

<script setup>
const { comment } = defineProps({ comment: Object });
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
</style>
