import { localAxios } from "@/util/http-commons";
const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};

function clickAttractionBoardLikeBtn(likeDto, success, fail) {
  local
    .post(`/attractionboardlike`, likeDto, { headers })
    .then(success)
    .catch(fail);
}

function getAttractionBoardLikeCount(attractionBoardId, success, fail) {
  local
    .get(`/attractionboardlike/count/` + attractionBoardId, { headers })
    .then(success)
    .catch(fail);
}

export { clickAttractionBoardLikeBtn, getAttractionBoardLikeCount };
