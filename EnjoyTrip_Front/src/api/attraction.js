import { localAxios } from '@/util/http-commons';

const local = localAxios();

function getListAttraction(param, success, fail) {
  local
    .get(`/attraction/attraction`, { params: param })
    .then(success)
    .catch(fail);
}

function getListAccomodation(param, success, fail) {
  local
    .get(`/attraction/accomodation`, { params: param })
    .then(success)
    .catch(fail);
}

function getListSido(success, fail) {
  local.get(`/attraction/sido`).then(success).catch(fail);
}

function getListGugun(sido, success, fail) {
  local.get(`/attraction/sido/${sido}`).then(success).catch(fail);
}

function getListContentType(success, fail) {
  local.get(`/attraction/contenttype`).then(success).catch(fail);
}

function getDetailAttractionInfo(contentId, success, fail) {
  local.get(`/attraction/attraction/${contentId}`).then(success).catch(fail);
}

function getAttractionLikeCnt(contentId, success, fail) {
  local.get(`/attraction/like/${contentId}`).then(success).catch(fail);
}

function getAttractionDetailByContentId(contentId, success, fail) {
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
