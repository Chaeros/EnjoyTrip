import { localAxios } from "@/util/http-commons";
const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};

function AttractionDetailByContentId(contentId, success, fail) {
  local
    .get(`/attraction/` + contentId)
    .then(success)
    .catch(fail);
}

export { AttractionDetailByContentId };
