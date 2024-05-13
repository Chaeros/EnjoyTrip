<template>
  <div class="comment-page">
    <div class="comment-box">
      <div class="comment-input-box">
        <div class="comment-count">
          <div class="comment-count-content">n개 댓글</div>
          <div />
        </div>
        <div>
          <input class="comment-input-text" type="text" />
        </div>
        <div class="comment-btn-box">
          <div />
          <button>댓글 작성</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup></script>

<style scoped>
.comment-count {
  display: flex;
  justify-content: space-between;
}
.comment-input-text {
  width: 100%;
}
.comment-btn-box {
  display: flex;
  justify-content: space-between;
}
</style>
