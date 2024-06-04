import { localAxios } from "@/util/http-commons";
import { getLocalStorage } from "@/util/localstorage/localstorage";

const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};
// let accessToken = getLocalStorage("access_token");
// local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

function addComment(comment, success, fail) {
  console.log(comment);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .post(`/attractionboardcommnet`, comment, headers)
    .then(success)
    .catch(fail);
}

function getCommentList(attractionBoardReviewId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attractionboardcommnet/` + attractionBoardReviewId)
    .then(success)
    .catch(fail);
}

function removeComment(commentId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .delete(`/attractionboardcommnet/` + commentId)
    .then(success)
    .catch(fail);
}

function modifyComment(comment, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.patch(`/attractionboardcommnet`, comment).then(success).catch(fail);
}

function getCommentCount(attractionBoardReviewId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attractionboardcommnet/count/` + attractionBoardReviewId)
    .then(success)
    .catch(fail);
}

export {
  addComment,
  getCommentList,
  removeComment,
  modifyComment,
  getCommentCount,
};
