import { ref } from "vue"
import { useRouter } from "vue-router"
import { defineStore } from "pinia"
import { jwtDecode } from "jwt-decode"

import { userConfirm, findById, tokenRegeneration, logout } from "@/api/user"
import { httpStatusCode } from "@/util/http-status"

export const useMemberStore = defineStore("memberStore", () => {

    const router = useRouter()
    
    const userId = ref(null);
    const userInfo = ref(null);
    const isLogin = ref(false);

    const getUserInfo = async (token) => {
        let decodeToken = jwtDecode(token)
        console.log(decodeToken)
        await findById(
          decodeToken.userId,
          (response) => {
            if (response.status === httpStatusCode.OK) {
              userInfo.value = response.data.userInfo
            } else {
              console.log("유저 정보 없음!!!!")
            }
          },
          async (error) => {
            console.error(
              "g[토큰 만료되어 사용 불가능.] : ",
              error.response.status,
              error.response.statusText
            )
            isValidToken.value = false
    
            await tokenRegenerate()
          }
        )
      }

    return {

    }
})