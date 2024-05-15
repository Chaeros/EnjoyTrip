<template>
  <Header></Header>
  <div class="board-detail-page" v-if="writerInfo">
    <div class="board-detail-box">
      <div class="board-detail-info-box">
        <div class="board-title">
          <template v-if="article.title !== ''">
            <h1>{{ article.title }}</h1>
          </template>
        </div>
        <div class="board-post-detail-info">
          <div class="board-post-detail-info-left">
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
            <div class="regdate">{{ article.regdate }}</div>
          </div>
          <div class="post-detail-info-right">
            <button>팔로우</button>
          </div>
        </div>
      </div>
    </div>
    <div
      id="article-content"
      class="article-content"
      style="border: 1px solid black; padding: 10px"
      v-html="article.content"
    ></div>
    <CommentView class="comment"></CommentView>
  </div>
  <Footer></Footer>
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import CommentView from "@/components/comment/CommentView.vue";
import { getAttractionReviewArticle } from "@/api/attraction-board/attraction-board.js";
import { getUserInfomationById } from "@/api/member/member.js";
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";

const route = useRoute(); // useRoute 훅을 사용해서 현재 라우트 객체에 접근합니다.
const attractionBoardReviewId = route.params.attractionBoardReviewId; // params로 전달받은 데이터를 가져옵니다.
const article = ref();
const writerInfo = ref(null);

onMounted(() => {
  getAttractionReviewArticle(
    attractionBoardReviewId,
    (response) => {
      console.log(response);
      article.value = response.data;
      getWriterInfo();
    },
    (error) => {
      console.log(error);
    }
  );
});

const getWriterInfo = () => {
  getUserInfomationById(
    article.value.memberId,
    (response) => {
      console.log(response);
      writerInfo.value = response.data;
      console.log(writerInfo.value);
    },
    (error) => {
      console.error(error);
      writerInfo.value = {}; // 에러가 발생했을 경우 빈 객체를 설정하여 v-if 조건을 false로 만들 수 있습니다.
    }
  );
};
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
.article-content {
  width: 1000px;
  margin: 0 auto;
}
.article-content img {
  justify-content: center;
}
.comment {
  width: 1000px;
  margin: 0 auto;
}
.image {
  width: 100%;
  border-radius: 10px;
}
.writer-info-box {
  display: flex;
}
.writer-profile-img {
  border-radius: 100%;
  width: 50px;
}
</style>
