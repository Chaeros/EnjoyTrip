<template>
  <Header></Header>
  <img
    class="board-detail-title-img"
    :src="imageServerURL + article.imageUrl"
    v-if="article"
  />
  <div class="board-detail-page" v-if="writerInfo">
    <div class="board-detail-left"></div>
    <div class="board-detail-center">
      <div class="board-detail-box">
        <div class="board-detail-info-box">
          <template v-if="article.title !== ''">
            <div class="board-title-font">{{ article.title }}</div>
          </template>
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
                <div class="writer-profile-nickname">
                  {{ writerInfo.nickname }}
                </div>
                <div class="regdate">{{ formattedRegdate }}</div>
                <div class="board-detail-hit-box">조회 {{ article.hit }}</div>
              </div>
            </div>
            <div class="post-detail-info-right">
              <template v-if="isMyArticle">
                <button
                  type="button"
                  class="btn btn-outline-secondary manage-btn"
                  @click="clickArticleModifyButton"
                >
                  <b> 수정 </b>
                </button>
                <button
                  type="button"
                  class="btn btn-outline-secondary manage-btn"
                  @click="clickArticleRemoveButton"
                >
                  <b> 삭제 </b>
                </button>
              </template>
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
        v-html="article.content"
      ></div>
      <div class="like-button-box">
        <template v-if="isMyLikeArticle">
          <button class="like-button" @click="clickLikeBtn">
            <div class="like-button-font">♥ {{ totalLikeCount }}</div>
          </button>
        </template>
        <template v-else>
          <button class="like-button" @click="clickLikeBtn">
            <div class="like-button-font">♡ {{ totalLikeCount }}</div>
          </button>
        </template>
      </div>
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
import {
  getAttractionReviewArticle,
  removeAttractionBoard,
} from "@/api/attraction-board/attraction-board.js";
import { getUserInfomationById } from "@/api/member/member.js";
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted, computed } from "vue";
import { getLocalStorage } from "@/util/localstorage/localstorage.js";
import {
  addFriend,
  removeFriendByDto,
  isMyFriend,
} from "@/api/friend/friend.js";
import {
  clickAttractionBoardLikeBtn,
  removeAttractionBoardLike,
  getAttractionBoardLikeCount,
  isMyLikeReview,
} from "@/api/attraction-board-like/attraction-board-like.js";
import Swal from "sweetalert2";
import { enterOrRegistPrivateChatRoom } from "@/api/chat/chat.js";

const imageServerURL = ref(import.meta.env.VITE_VUE_IMAGE_SERVER_URL);
const route = useRoute();
const attractionBoardReviewId = route.params.attractionBoardReviewId;
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
const router = useRouter();

const formattedRegdate = computed(() => {
  if (!article.value || !article.value.regdate) {
    return "";
  }
  const date = new Date(article.value.regdate);
  return date.toLocaleString("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
    hour12: true,
  });
});

const clickArticleModifyButton = () => {
  router.push({
    name: "reviewBoardWrite",
    query: {
      attractionBoardReviewId: attractionBoardReviewId,
      isModify: true,
    },
  });
};

const clickArticleRemoveButton = () => {
  Swal.fire({
    title: "정말 삭제하시겠어요?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "삭제",
    cancelButtonText: "취소",
  }).then((result) => {
    if (result.isConfirmed) {
      removeAttractionBoard(
        attractionBoardReviewId,
        (response) => {
          console.log("ok");
          router.push({ name: "reviewBoardList" });
        },
        (error) => {
          console.log(error);
        }
      );
    }
  });
};

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

const clickLikeBtn = () => {
  clickAttractionBoardLikeBtn(
    {
      memberId: getLocalStorage("userId"),
      attractionBoardId: attractionBoardReviewId,
    },
    (response) => {
      isMyLikeArticle.value = !isMyLikeArticle.value;
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
      isMyArticle.value = article.value.memberId == getLocalStorage("userId");
      if (!isMyArticle.value) {
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
    isMyLikeArticle.value = true;
    refreshLikeCount();
  },
  (error) => {
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
  addFriend(
    friendDto.value,
    (response) => {
      isItMyFriend.value = true;
      enterOrRegistPrivateChatRoom(
        {
          myId: getLocalStorage("userId"),
          opponentId: friendDto.value.friendId,
        },
        (response) => {
          console.log(response.data);
        },
        (error) => {
          console.log(error);
        }
      );
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
      writerInfo.value = {};
    }
  );
};
</script>

<style scoped>
.board-detail-page {
  display: grid;
  grid-template-columns: 1fr 3fr 1fr;
  gap: 20px;
  padding: 20px;
  min-height: 100vh;
}

.board-detail-left,
.board-detail-right {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  border-radius: 8px;
}

.like-button {
  background-color: #ff4757; /* 배경 색상 */
  color: white; /* 글자 색상 */
  border: none; /* 테두리 제거 */
  padding: 10px 20px; /* 패딩 */
  cursor: pointer; /* 클릭 가능한 커서 모양으로 변경 */
  border-radius: 5px; /* 버튼의 모서리 둥글게 */
  transition: background-color 0.3s, transform 0.3s;
}

.like-button:hover {
  background-color: #ff6b81;
  transform: translateY(-2px);
}

.board-detail-center {
  background-color: #ffffff;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  width: 1200px;
  margin: 0 auto;
}

.board-detail-title-img {
  width: 100%;
  height: 400px;
  object-fit: cover;
  object-position: center;
  border-bottom: 1px solid #e0e0e0;
}

.board-detail-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
  margin-bottom: 20px;
}

.board-detail-info-box {
  width: 100%;
  margin-bottom: 20px;
}

.board-title-font {
  font-size: 70px;
  font-weight: 700;
  color: #333;
  word-break: break-all; /* 단어가 넘칠 경우 줄 바꿈 */
  overflow-wrap: break-word;
  padding: 0;
  margin-bottom: 20px;
}

.board-detail-hit-box {
  margin-left: 20px;
  font-size: 15px;
  color: #777;
}

.board-post-detail-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.writer-info-box {
  display: flex;
  align-items: center;
}

.writer-profile-img {
  border-radius: 100%;
  width: 50px;
  height: 50px;
  margin-right: 10px;
}

.writer-profile-nickname {
  font-size: 25px;
  font-weight: bold;
}

.regdate {
  font-size: 25px;
  color: #777;
  margin-left: 40px;
}

.manage-btn {
  margin-left: 10px;
  /* border: none; */
  border-radius: 20px;
}

.manage-btn > b {
  font-size: 20px;
  font-weight: bold;
}

.article-content {
  width: 100%;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 8px;
  /* box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); */
  margin-bottom: 20px;
  overflow: hidden;
  word-break: break-word;
  font-size: 25px;
}

.like-button-box {
  margin: 0 auto;
  text-align: center;
  height: 70px;
}

.like-button {
  height: 100%;
}

.like-button-font {
  font-size: 30px;
}

.comment {
  width: 100%;
}
</style>
