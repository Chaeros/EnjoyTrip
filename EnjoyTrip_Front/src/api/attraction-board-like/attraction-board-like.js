import { localAxios } from "@/util/http-commons";
const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};

function clickAttractionBoardLikeBtn(likeDto, success, fail) {
  console.log(likeDto);
  local.post(`/attractionboardlike`, likeDto).then(success).catch(fail);
}

function removeAttractionBoardLike(memberId, attractionBoardId, success, fail) {
  console.log(memberId, attractionBoardId);
  local
    .delete(`/attractionboardlike`, {
      params: { memberId: memberId, attractionBoardId: attractionBoardId },
    })
    .then(success)
    .catch(fail);
}

function getAttractionBoardLikeCount(attractionBoardId, success, fail) {
  local
    .get(`/attractionboardlike/count/` + attractionBoardId, { headers })
    .then(success)
    .catch(fail);
}

function isMyLikeReview(memberId, attractionBoardId, success, fail) {
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
