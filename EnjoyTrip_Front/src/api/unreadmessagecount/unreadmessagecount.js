import { localAxios } from "@/util/http-commons";

const local = localAxios();

function registUnreadMessageCount(payload, success, fail) {
  console.dir(payload);
  local.post(`/unreadmessagecount`, payload).then(success).catch(fail);
}

function countAddUnreadMessageCount(payload, success, fail) {
  console.dir(payload);
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
  local
    .patch(`/unreadmessagecount/resetcount`, payload)
    .then(success)
    .catch(fail);
}

function searchUnreadMessageCount(roomId, receiverId, success, fail) {
  local
    .get(`/unreadmessagecount`, { params: { roomId, receiverId } })
    .then(success)
    .catch(fail);
}

export {
  registUnreadMessageCount,
  countAddUnreadMessageCount,
  countResetUnreadMessageCount,
  searchUnreadMessageCount,
};
