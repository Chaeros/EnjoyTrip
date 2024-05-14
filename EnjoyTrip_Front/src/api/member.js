import { localAxios } from "@/util/http-commons";
const kakaoRestAPIKey = import.meta.env.VITE_KAKAO_REST_API_KEY;
const local = localAxios();
local.defaults.withCredentials = true;

const headers2 = {
  "Content-Type": "application/json",
};

function addUserInformation(param, success, fail) {
  console.log(param);
  local
    .post(`/oauth2/sign-up`, JSON.stringify(param))
    .then(success)
    .catch(fail);
}

function kakaoLogin(success, fail) {
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
  axios
    .post(`https://kauth.kakao.com/oauth/token`, data, { headers })
    .then(success)
    .catch(fail);
}

function updateUserInfomation(data, success, fail) {
  local.post(`/oauth2/sign-up`, data).then(success).catch(fail);
}

function normalLogin(loginInfo, success, fail) {
  const headers = {
    "Content-Type": "application/json",
  };
  // local.post(`/login`,loginInfo,{headers}).then(success).catch(fail);
  local.post(`/login`, loginInfo, { headers }).then(success).catch(fail);
}

function normalSignUp(memberInfo, success, fail) {
  local.post(`/sign-up`, memberInfo).then(success).catch(fail);
}

function getUserInfomation(email, success, fail) {
  local.get(`/member/`+email).then(success).catch(fail);
}

export {
  addUserInformation,
  kakaoLogin,
  sendKakaoLoginCode,
  updateUserInfomation,
  normalLogin,
  normalSignUp,
  getUserInfomation
};
