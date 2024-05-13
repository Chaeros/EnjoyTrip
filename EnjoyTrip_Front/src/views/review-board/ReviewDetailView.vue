<template>
  <Header></Header>
  <div class="board-detail-page">
    <div class="board-detail-box">
      <div class="board-detail-info-box">
        <div class="board-title"><h1>제목----------</h1></div>
        <div class="board-post-detail-info">
          <div class="board-post-detail-info-left">
            <div class="writer">작성자</div>
            <div class="regdate">2024년 5월 13일</div>
          </div>
          <div class="post-detail-info-right">
            <button>팔로우</button>
          </div>
        </div>
      </div>
    </div>
    <CommentView class="comment"></CommentView>
  </div>
  <Footer></Footer>
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import CommentView from "@/components/comment/CommentView.vue";
</script>

<style scoped>
.board-detail-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
}
.board-detail-box {
  width: 1000px;
  border: 1px solid;
}
.board-title {
  width: 1000px;
}
.board-post-detail-info {
  display: flex;
}
.board-post-detail-info {
  widows: 1000px;
}
.board-post-detail-info-left {
  width: 100%;
  justify-content: space-between;
}
.comment {
  width: 1000px;
  margin: 0 auto;
}
</style>
