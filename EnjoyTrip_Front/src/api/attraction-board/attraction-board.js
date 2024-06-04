import { localAxios } from "@/util/http-commons";
import { getLocalStorage } from "@/util/localstorage/localstorage";
const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
  Authorization: "Bearer " + getLocalStorage("access_token"),
};
let accessToken = getLocalStorage("access_token");
local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

function addAttractionReview(article, success, fail) {
  console.log(article);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.post(`/attractionboard`, article, headers).then(success).catch(fail);
}

function getAttractionReviewList(success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.get(`/attractionboard`).then(success).catch(fail);
}

function getAttractionReviewArticle(id, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attractionboard/` + id)
    .then(success)
    .catch(fail);
}

function removeAttractionBoard(attractionBoardId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .delete(`/attractionboard/` + attractionBoardId)
    .then(success)
    .catch(fail);
}

function modifyAttractionBoard(article, success, fail) {
  const data = {
    id: article.id,
    attractionId: article.attractionId,
    content: article.content,
    imageUrl: article.imageUrl,
    memberId: article.memberId,
    title: article.title,
  };
  console.log(data);
  console.log(article);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.patch(`/attractionboard`, article, headers).then(success).catch(fail);
}

function addHitCount(id, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attractionboard/hit/` + id)
    .then(success)
    .catch(fail);
}

function getAttractionReviewArticles(id, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attractionboard/attractionId/` + id)
    .then(success)
    .catch(fail);
}

function findAttractionBoardsByTitle(title, success, fail) {
  console.log(title);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attractionboard/title/` + title)
    .then(success)
    .catch(fail);
}

function getTotalCountOfAttractionBoards(success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.get(`/attractionboard/attractionboardcount`).then(success).catch(fail);
}

function searchAttractionBoardsByPageNumber(pageNumber, success, fail) {
  // console.log("확인용**", local.defaults);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attractionboard/page/` + pageNumber)
    .then(success)
    .catch(fail);
}

function searchAttractionBoardSortByLike(pageNumber, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attractionboard/orderbylike/` + pageNumber)
    .then(success)
    .catch(fail);
}

export {
  addAttractionReview,
  getAttractionReviewList,
  getAttractionReviewArticle,
  removeAttractionBoard,
  modifyAttractionBoard,
  addHitCount,
  getAttractionReviewArticles,
  findAttractionBoardsByTitle,
  getTotalCountOfAttractionBoards,
  searchAttractionBoardsByPageNumber,
  searchAttractionBoardSortByLike,
};
