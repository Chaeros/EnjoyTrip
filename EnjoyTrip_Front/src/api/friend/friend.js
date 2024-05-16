import { localAxios } from "@/util/http-commons";
const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};

function addFriend(friendDto, success, fail) {
  local.post(`/friend`, friendDto).then(success).catch(fail);
}

function removeFriend(id, success, fail) {
  local
    .delete(`/friend/` + id)
    .then(success)
    .catch(fail);
}

function bringFriendList(myId, success, fail) {
  local
    .get(`/friend/list/` + myId)
    .then(success)
    .catch(fail);
}

export { addFriend, removeFriend, bringFriendList };
