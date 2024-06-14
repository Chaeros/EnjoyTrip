import { localAxios } from "@/util/http-commons";
import { getLocalStorage } from "@/util/localstorage/localstorage";
const kakaoRestAPIKey = import.meta.env.VITE_KAKAO_REST_API_KEY;
const local = localAxios();
local.defaults.withCredentials = true; // 사용자 설정 쿠키를 넣을 때 사용해야함.
let accessToken = getLocalStorage("access_token");
local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

const headers2 = {
  "Content-Type": "application/json",
};

function addUserInformation(param, success, fail) {
  console.log(param);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .post(`/oauth2/sign-up`, JSON.stringify(param), { headers: headers2 })
    .then(success)
    .catch(fail);
}

function kakaoLogin(success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.get(`/oauth2/authorization/kakao`).then(success).catch(fail);
}

function sendKakaoLoginCode(code, success, fail) {
  console.log(local);
  const headers = {
    "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
  };

  const data = new URLSearchParams({
    grant_type: "authorization_code",
    client_id: kakaoRestAPIKey,
    redirect_uri: "http://localhost:5173/login/oauth2/code/kakao",
    code: code,
    client_secret: "FlbsB7u1AzcpV2eSLUYxbl84qGioY1L6",
  }).toString();
  console.log(data);
  let accessToken = getLocalStorage("access_token");
  axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  axios
    .post(`https://kauth.kakao.com/oauth/token`, data, { headers })
    .then(success)
    .catch(fail);
}

function updateUserInfomation(data, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.post(`/oauth2/sign-up`, data).then(success).catch(fail);
}

function normalLogin(loginInfo, success, fail) {
  const headers = {
    "Content-Type": "application/json",
  };
  let accessToken = getLocalStorage("access_token");
  // local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.post(`/login`, loginInfo, { headers }).then(success).catch(fail);
}

function normalSignUp(memberInfo, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.post(`/sign-up`, memberInfo).then(success).catch(fail);
}

function getUserInfomation(email, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/member/` + email)
    .then(success)
    .catch(fail);
}

function getUserInfomationById(id, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/member/id/` + id)
    .then(success)
    .catch(fail);
}

function searchMemberByKeyNicknameKeword(keyword, userId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/member/keyword`, { params: { keyword, userId } })
    .then(success)
    .catch(fail);
}

function getChattingMemberId(memberId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/member/chat/` + memberId)
    .then(success)
    .catch(fail);
}

function updateMember(memberDto, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.patch(`/member`, memberDto).then(success).catch(fail);
}

export {
  addUserInformation,
  kakaoLogin,
  sendKakaoLoginCode,
  updateUserInfomation,
  normalLogin,
  normalSignUp,
  getUserInfomation,
  getUserInfomationById,
  searchMemberByKeyNicknameKeword,
  getChattingMemberId,
  updateMember,
};
