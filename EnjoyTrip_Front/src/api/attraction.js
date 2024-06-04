import { localAxios } from "@/util/http-commons";
import { getLocalStorage } from "@/util/localstorage/localstorage";

const local = localAxios();
// let accessToken = getLocalStorage("access_token");
// local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

function getListAttraction(param, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attraction/attraction`, { params: param })
    .then(success)
    .catch(fail);
}

function getListAccomodation(param, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/attraction/accomodation`, { params: param })
    .then(success)
    .catch(fail);
}

function getListSido(success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.get(`/attraction/sido`).then(success).catch(fail);
}

function getListGugun(sido, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.get(`/attraction/sido/${sido}`).then(success).catch(fail);
}

function getListContentType(success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.get(`/attraction/contenttype`).then(success).catch(fail);
}

function getDetailAttractionInfo(contentId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.get(`/attraction/attraction/${contentId}`).then(success).catch(fail);
}

function getAttractionLikeCnt(contentId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.get(`/attraction/like/${contentId}`).then(success).catch(fail);
}

function getAttractionDetailByContentId(contentId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.get(`/attraction/${contentId}`).then(success).catch(fail);
}

export {
  getListAttraction,
  getListAccomodation,
  getListSido,
  getListGugun,
  getListContentType,
  getDetailAttractionInfo,
  getAttractionLikeCnt,
  getAttractionDetailByContentId,
};
