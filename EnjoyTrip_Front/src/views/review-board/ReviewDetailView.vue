<template>
  <Header></Header>
  <img
    class="board-detail-title-img"
    :src="imageServerURL + article.imageUrl"
    v-if="article"
  />
  <div class="board-detail-page" v-if="writerInfo">
    <div class="board-detail-left">
      <template v-if="isMyLikeArticle">
        <button class="like-button" @click="clickUnLikeBtn">
          ♥ 좋아요({{ totalLikeCount }}) 취소
        </button>
      </template>
      <template v-else>
        <button class="like-button" @click="clickLikeBtn">
          ♡ 좋아요({{ totalLikeCount }}) 하기
        </button>
      </template>
    </div>
    <div class="board-detail-center">
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
              <template v-if="isMyArticle"> </template>
              <template v-else-if="isItMyFriend === false">
                <button
                  type="button"
                  class="btn btn-outline-secondary"
                  @click="clickFollow"
                >
                  <b> Follow </b>
                </button>
              </template>
              <template v-else>
                <button
                  type="button"
                  class="btn btn-outline-secondary"
                  @click="clickUnFollow"
                >
                  <b> UnFollow </b>
                </button>
              </template>
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
      <CommentView
        class="comment"
        :attractionBoardReviewId="attractionBoardReviewId"
      ></CommentView>
    </div>
    <div class="board-detail-right"></div>
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
import {
  getLocalStorage,
  setLocalStorage,
} from "@/util/localstorage/localstorage.js";
import {
  addFriend,
  removeFriend,
  removeFriendByDto,
  bringFriendList,
  isMyFriend,
} from "@/api/friend/friend.js";
import {
  clickAttractionBoardLikeBtn,
  removeAttractionBoardLike,
  getAttractionBoardLikeCount,
  isMyLikeReview,
} from "@/api/attraction-board-like/attraction-board-like.js";
const imageServerURL = ref(import.meta.env.VITE_VUE_IMAGE_SERVER_URL);
const route = useRoute(); // useRoute 훅을 사용해서 현재 라우트 객체에 접근합니다.
const attractionBoardReviewId = route.params.attractionBoardReviewId; // params로 전달받은 데이터를 가져옵니다.
const article = ref();
const writerInfo = ref(null);
const isMyArticle = ref(true);
const isItMyFriend = ref(false);
const isMyLikeArticle = ref(false);
const totalLikeCount = ref(0);
const friendDto = ref({
  myId: getLocalStorage("userId"),
  friendId: "",
});

const refreshLikeCount = () => {
  getAttractionBoardLikeCount(
    attractionBoardReviewId,
    (response) => {
      totalLikeCount.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const clickUnLikeBtn = () => {
  console.log("unlike");
  removeAttractionBoardLike(
    getLocalStorage("userId"),
    attractionBoardReviewId,
    (response) => {
      isMyLikeArticle.value = false;
      refreshLikeCount();
    },
    (error) => {
      console.log(error);
      refreshLikeCount();
    }
  );
};

const clickLikeBtn = () => {
  console.log("like");
  clickAttractionBoardLikeBtn(
    {
      memberId: getLocalStorage("userId"),
      attractionBoardId: attractionBoardReviewId,
    },
    (response) => {
      console.log(response.data);
      isMyLikeArticle.value = true;
      refreshLikeCount();
    },
    (error) => {
      console.log(error);
      refreshLikeCount();
    }
  );
};

onMounted(() => {
  getAttractionReviewArticle(
    attractionBoardReviewId,
    (response) => {
      article.value = response.data;
      friendDto.value.friendId = article.value.memberId;
      getWriterInfo();
      refreshLikeCount();
      if (article.value.memberId == getLocalStorage("userId")) {
        isMyArticle.value = true;
      } else {
        isMyArticle.value = false;
        isFriend();
      }
    },
    (error) => {
      console.log(error);
    }
  );
});

isMyLikeReview(
  getLocalStorage("userId"),
  attractionBoardReviewId,
  (response) => {
    console.log(response.data);
    isMyLikeArticle.value = true;
    refreshLikeCount();
  },
  (error) => {
    console.log(error);
    isMyLikeArticle.value = false;
  }
);

const isFriend = () => {
  isMyFriend(
    getLocalStorage("userId"),
    article.value.memberId,
    (response) => {
      isItMyFriend.value = true;
    },
    (error) => {
      isItMyFriend.value = false;
    }
  );
};

const clickFollow = () => {
  console.log(friendDto.value);
  addFriend(
    friendDto.value,
    (response) => {
      console.log(response.data);
      isItMyFriend.value = true;
    },
    (error) => {
      console.log(error);
      isItMyFriend.value = false;
    }
  );
};

const clickUnFollow = () => {
  removeFriendByDto(
    friendDto.value,
    (response) => {
      isItMyFriend.value = false;
    },
    (error) => {
      isItMyFriend.value = true;
    }
  );
};

const getWriterInfo = () => {
  getUserInfomationById(
    article.value.memberId,
    (response) => {
      writerInfo.value = response.data;
    },
    (error) => {
      console.error(error);
      writerInfo.value = {}; // 에러가 발생했을 경우 빈 객체를 설정하여 v-if 조건을 false로 만들 수 있습니다.
    }
  );
};
</script>

<style scoped>
.board-detail-page {
  display: grid;
  grid-template-columns: 1fr 4fr 1fr;
  gap: 10px;
}

.board-detail-left {
  display: flex;
  align-items: center;
  background-color: #f0f0f0;
  padding: 10px;
  position: relative;
}

.like-button {
  background-color: #f00; /* 배경 색상 */
  color: white; /* 글자 색상 */
  border: none; /* 테두리 제거 */
  padding: 10px 20px; /* 패딩 */
  cursor: pointer; /* 클릭 가능한 커서 모양으로 변경 */
  border-radius: 5px; /* 버튼의 모서리 둥글게 */
  margin-left: auto; /* 버튼을 우측 정렬 */
}

.board-detail-center {
  background-color: #ffffff;
  padding: 10px;
}

.board-detail-right {
  background-color: #f0f0f0;
  padding: 10px;
}

.board-detail-title-img {
  width: 100%;
  height: 300px;
  object-fit: cover; /* 비율 유지하면서 요소의 크기에 맞게 조정 */
  object-position: center; /* 중심을 기준으로 이미지 위치 조정 */
}
.board-detail-box {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.board-detail-info-box {
  width: 100%;
}
.board-detail-box {
  width: 100%;
  border: 1px solid;
}
.board-title {
  width: 100%;
}
.board-post-detail-info {
  display: flex;
}
.board-post-detail-info {
  width: 100%;
}
.board-post-detail-info-left {
  width: 100%;
  justify-content: space-between;
}
.article-content {
  width: 100%;
  margin: 0 auto;
}
.article-content img {
  justify-content: center;
}
.comment {
  width: 100%;
  margin: 0 auto;
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
