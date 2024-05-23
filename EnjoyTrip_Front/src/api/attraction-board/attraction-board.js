import { localAxios } from "@/util/http-commons";
const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};

function addAttractionReview(article, success, fail) {
  console.log(article);
  local.post(`/attractionboard`, article, headers).then(success).catch(fail);
}

function getAttractionReviewList(success, fail) {
  local.get(`/attractionboard`).then(success).catch(fail);
}

function getAttractionReviewArticle(id, success, fail) {
  local
    .get(`/attractionboard/` + id)
    .then(success)
    .catch(fail);
}

function removeAttractionBoard(attractionBoardId, success, fail) {
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
  local.patch(`/attractionboard`, article, headers).then(success).catch(fail);
}

function getAttractionReviewArticles(id, success, fail) {
  local
    .get(`/attractionboard/attractionId/` + id)
    .then(success)
    .catch(fail);
}

function findAttractionBoardsByTitle(title, success, fail) {
  console.log(title);
  local
    .get(`/attractionboard/title/` + title)
    .then(success)
    .catch(fail);
}

export {
  addAttractionReview,
  getAttractionReviewList,
  getAttractionReviewArticle,
  removeAttractionBoard,
  modifyAttractionBoard,
  getAttractionReviewArticles,
  findAttractionBoardsByTitle,
};
