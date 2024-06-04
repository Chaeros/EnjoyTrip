import { localAxios } from "@/util/http-commons";
import { getLocalStorage } from "@/util/localstorage/localstorage";

const local = localAxios();
let accessToken = getLocalStorage("access_token");
local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

function registUnreadMessageCount(payload, success, fail) {
  console.dir(payload);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.post(`/unreadmessagecount`, payload).then(success).catch(fail);
}

function countAddUnreadMessageCount(payload, success, fail) {
  console.dir(payload);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .patch(`/unreadmessagecount/addcount`, payload)
    .then(success)
    .catch(fail);
}

function countResetUnreadMessageCount(roomId, receiverId, success, fail) {
  const payload = {
    roomId: roomId,
    receiverId: receiverId,
  };
  console.log(roomId);
  console.log(receiverId);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .patch(`/unreadmessagecount/resetcount`, payload)
    .then(success)
    .catch(fail);
}

function searchUnreadMessageCount(roomId, receiverId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/unreadmessagecount`, { params: { roomId, receiverId } })
    .then(success)
    .catch(fail);
}

function searchUnreadMessageCountListById(id, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/unreadmessagecount/` + id)
    .then(success)
    .catch(fail);
}

export {
  registUnreadMessageCount,
  countAddUnreadMessageCount,
  countResetUnreadMessageCount,
  searchUnreadMessageCount,
  searchUnreadMessageCountListById,
};
