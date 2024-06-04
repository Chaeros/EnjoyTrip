import { localAxios } from "@/util/http-commons";
import { getLocalStorage } from "@/util/localstorage/localstorage";

const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};

function AttractionDetailByContentId(contentId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attraction/` + contentId)
    .then(success)
    .catch(fail);
}

export { AttractionDetailByContentId };
