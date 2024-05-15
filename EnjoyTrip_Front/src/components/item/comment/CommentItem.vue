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
          <div class="description-top-right">
            <button>수정</button>
            <button>삭제</button>
          </div>
        </div>
        <div class="date">{{ comment.regdate }}</div>
      </div>
    </div>
    <div class="comment-content">{{ comment.content }}</div>
  </div>
</template>

<script setup>
import { getUserInfomationById } from "@/api/member/member.js";
import { ref, onMounted } from "vue";
const { comment } = defineProps({ comment: Object });
const writerInfo = ref(null);

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
