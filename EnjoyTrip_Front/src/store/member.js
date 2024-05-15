import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";

import { getUserInfomation } from "@/api/member/member";
import { httpStatusCode } from "@/util/http-status";

export const useMemberStore = defineStore("memberStore", () => {
  const router = useRouter();

  const userId = ref(null);
  const userInfo = ref(null);
  const isLogin = ref(false);

  const getUserInfo = async (eamil) => {
    console.log("ooo", eamil);
    await getUserInfomation(
      eamil,
      (response) => {
        console.log(response);
        if (response.status === httpStatusCode.OK) {
          userInfo.value = response.data;
          console.log("저장됨", userInfo.value);
        } else {
          console.log("유저 정보 없음!!!!");
        }
      },
      async (error) => {
        console.error(error);
      }
    );
  };

  return {
    userInfo,
    isLogin,
    getUserInfo,
  };
});
