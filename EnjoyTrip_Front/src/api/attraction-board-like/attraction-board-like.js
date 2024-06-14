import { localAxios } from "@/util/http-commons";
import { getLocalStorage } from "@/util/localstorage/localstorage";

const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};
// let accessToken = getLocalStorage("access_token");
// local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

function clickAttractionBoardLikeBtn(likeDto, success, fail) {
  console.log(likeDto);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.post(`/attractionboardlike`, likeDto).then(success).catch(fail);
}

function removeAttractionBoardLike(memberId, attractionBoardId, success, fail) {
  console.log(memberId, attractionBoardId);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .delete(`/attractionboardlike`, {
      params: { memberId: memberId, attractionBoardId: attractionBoardId },
    })
    .then(success)
    .catch(fail);
}

function getAttractionBoardLikeCount(attractionBoardId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attractionboardlike/count/` + attractionBoardId, { headers })
    .then(success)
    .catch(fail);
}

function isMyLikeReview(memberId, attractionBoardId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attractionboardlike/isLikeReview`, {
      params: { memberId, attractionBoardId },
    })
    .then(success)
    .catch(fail);
}

export {
  clickAttractionBoardLikeBtn,
  removeAttractionBoardLike,
  getAttractionBoardLikeCount,
  isMyLikeReview,
};
