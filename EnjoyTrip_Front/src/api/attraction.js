import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getListAttraction(param, success, fail) {
    local.get(`/attraction/attraction`, {  }).then(success).catch(fail);
}

function getListSido(success, fail) {
    local.get(`/attraction/sido`).then(success).catch(fail);
}

function getListGugun(sido, success, fail) {
    local.get(`/attraction/sido/${sido}`).then(success).catch(fail);
}

function getListContentType(success, fail) {
    local.get(`/attraction/contentType`).then(success).catch(fail);
}

export {
    getListAttraction,
    getListSido,
    getListGugun,
    getListContentType
};