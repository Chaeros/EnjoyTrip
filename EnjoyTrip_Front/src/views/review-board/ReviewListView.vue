<template>
  <Header />
  <div class="board-list-page">
    <div class="board-list-box">
      <div class="board-list-box-top">
        <div class="board-list-description-and-write-btn">
          <div class="review-title">리뷰 목록</div>
          <div class="board-list-top-right-input-box">
            <div class="search-box">
              <input
                class="form-control me-2 search-text"
                type="search"
                placeholder="Search"
                aria-label="Search"
                @keydown.enter="callAttractionBoardByTitle"
                v-model="keyword"
              />
              <img
                src="@/img/search_icon.png"
                alt="Show Password"
                title="Show Password"
                class="search_icon"
                @click="callAttractionBoardByTitle"
              />
            </div>
            <button
              type="button"
              class="btn btn-outline-secondary user-button"
              @click="clickBoardWriteBtn"
            >
              글쓰기
            </button>
          </div>
        </div>
        <div class="review-card-list">
          <template
            v-for="attractionBoardReview in attractionBoardReviews"
            :key="attractionBoardReview.id"
          >
            <AttractionBoardArticleCard
              class="article-card"
              :attractionBoardReview="attractionBoardReview"
              @click="clickAttractionBoardArticleCard(attractionBoardReview)"
            />
          </template>
        </div>
      </div>
      <div class="paging-box">
        <p
          v-for="page in pageCount"
          :key="page"
          @click="clickPage(page)"
          :class="{ 'bold-page': page == currentPage }"
        >
          {{ page }}
        </p>
      </div>
    </div>
  </div>
  <button @click="scrollToTop" class="scroll-to-top" v-if="showScrollButton">
    ↑
  </button>
  <Footer />
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import AttractionBoardArticleCard from "@/components/item/card/AttractionBoardArticleCard.vue";
import { useRouter, useRoute } from "vue-router";
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useMemberStore } from "@/store/member";
import { storeToRefs } from "pinia";
import {
  getAttractionReviewList,
  findAttractionBoardsByTitle,
  getTotalCountOfAttractionBoards,
  searchAttractionBoardsByPageNumber,
} from "@/api/attraction-board/attraction-board";

const router = useRouter();
const route = useRoute();
const memberStore = useMemberStore();
const { isLogin } = storeToRefs(memberStore);
const attractionBoardReviews = ref([]);
const showScrollButton = ref(false);

const keyword = ref("");
const pageCount = ref(0);
const currentPage = ref(1);

getTotalCountOfAttractionBoards(
  (response) => {
    console.log("관광지리뷰 개수=", response.data);
    pageCount.value = Math.floor(response.data / 6) + 1;
    console.log("pagine count = ", pageCount.value);
  },
  (error) => {
    console.log(error);
  }
);

const clickPage = (page) => {
  currentPage.value = page;
  searchAttractionBoardsByPageNumber(
    page,
    (response) => {
      console.log("page 기능", response.data);
      attractionBoardReviews.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const callAttractionBoardByTitle = () => {
  findAttractionBoardsByTitle(
    keyword.value,
    (response) => {
      attractionBoardReviews.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const clickBoardWriteBtn = () => {
  if (isLogin.value) {
    router.push({ name: "reviewBoardWrite" });
  } else {
    alert("로그인 후에만 글 작성이 가능합니다!");
    router.push({ name: "login" });
  }
};

const getReviews = () => {
  getAttractionReviewList(
    (response) => {
      attractionBoardReviews.value = response.data;
    },
    (error) => {
      console.error(error);
    }
  );
};

const handleScroll = () => {
  showScrollButton.value = window.scrollY > 200;
};

const scrollToTop = () => {
  window.scrollTo({ top: 0, behavior: "smooth" });
};

onMounted(() => {
  // getReviews();
  clickPage(1);
  window.addEventListener("scroll", handleScroll);
  console.log(route.query.sendKeyword);
  if (route.query.sendKeyword) {
    findAttractionBoardsByTitle(
      route.query.sendKeyword,
      (response) => {
        attractionBoardReviews.value = response.data;
      },
      (error) => {
        console.log(error);
      }
    );
  }
});

onBeforeUnmount(() => {
  window.removeEventListener("scroll", handleScroll);
});

const clickAttractionBoardArticleCard = (attractionBoardReview) => {
  router.push({
    name: "reviewBoardDetail",
    params: { attractionBoardReviewId: attractionBoardReview.id },
  });
};
</script>

<style scoped>
body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    "Helvetica Neue", Arial, "Noto Sans", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.board-list-page {
  padding: 20px;
}

.board-list-box {
  max-width: 1200px;
  margin: 0 auto;
  background-color: #ffffff; /* 흰색 배경 추가 */
  border-radius: 8px; /* 모서리 둥글게 */
  padding: 20px;
}

.board-list-box-top {
  margin-bottom: 20px;
}

.board-list-description-and-write-btn {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.review-title {
  font-size: 32px;
  font-weight: 700;
  color: #333; /* 진한 텍스트 색상 */
}

.select-attraction-btn {
  padding: 10px 20px;
  font-size: 18px;
  border-radius: 5px;
  transition: background-color 0.3s, box-shadow 0.3s;
}

.select-attraction-btn:hover {
  background-color: #0056b3;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
}

.review-card-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px; /* 카드 간격 추가 */
}

.article-card {
  background-color: #ffffff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
  width: calc(33.333% - 20px); /* 3개씩 표시되도록 설정 */
  display: flex;
  flex-direction: column;
}

.article-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.article-card img {
  width: 100%;
  height: 180px;
  object-fit: cover; /* 이미지 비율을 유지하며 잘라내기 */
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
}

.article-card .content {
  padding: 15px;
  flex-grow: 1; /* 내용이 카드 높이를 채우도록 설정 */
}

.article-card .title {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.article-card .description {
  font-size: 16px;
  color: #666;
  line-height: 1.5;
  flex-grow: 1; /* 내용이 카드 높이를 채우도록 설정 */
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3; /* 최대 3줄까지만 표시 */
  -webkit-box-orient: vertical;
}

.scroll-to-top {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 50px;
  height: 50px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  cursor: pointer;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  transition: opacity 0.3s ease-in-out;
  opacity: 0;
  visibility: hidden;
}

.scroll-to-top.show {
  opacity: 1;
  visibility: visible;
}
.search-box {
  position: relative;
}

.search-text {
  width: 300px;
  padding: 8px 40px 8px 12px;
  border: 1px solid #ccc;
  border-radius: 20px;
  transition: border-color 0.3s;
}

.search-text:focus {
  border-color: #007bff;
  outline: none;
}

.search_icon {
  width: 20px;
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}
.board-list-top-right-input-box {
  display: flex;
}

.paging-box {
  display: flex;
  align-items: center;
  justify-content: center;
}

.paging-box > p {
  font-size: 20px;
  padding: 8px;
}

.bold-page {
  font-weight: bold;
}
</style>
