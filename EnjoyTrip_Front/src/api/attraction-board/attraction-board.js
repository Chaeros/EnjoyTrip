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

export {
  addAttractionReview,
  getAttractionReviewList,
  getAttractionReviewArticle,
};
