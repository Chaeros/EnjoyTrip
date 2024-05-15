import { localAxios } from "@/util/http-commons";
const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};

function addComment(comment, success, fail) {
  console.log(comment);
  local
    .post(`/attractionboardcommnet`, comment, headers)
    .then(success)
    .catch(fail);
}

function getCommentList(attractionBoardReviewId, success, fail) {
  local
    .get(`/attractionboardcommnet/` + attractionBoardReviewId)
    .then(success)
    .catch(fail);
}

function removeComment(attractionBoardId, success, fail) {
  local
    .delete(`/attractionboardcommnet/` + attractionBoardId)
    .then(success)
    .catch(fail);
}

function modifyComment(comment, success, fail) {
  local.patch(`/attractionboardcommnet`, comment).then(success).catch(fail);
}

export { addComment, getCommentList, removeComment, modifyComment };
