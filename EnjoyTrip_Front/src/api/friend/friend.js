import { localAxios } from "@/util/http-commons";
import { getLocalStorage } from "@/util/localstorage/localstorage";
const userId = getLocalStorage("userId");
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

function removeFriendByDto(friendDto, success, fail) {
  local.delete(`/friend`, { data: friendDto }).then(success).catch(fail);
}

function bringFriendList(myId, success, fail) {
  console.log(userId);
  console.log(myId);
  local
    .get(`/friend/list/` + myId)
    .then(success)
    .catch(fail);
}

function isMyFriend(myId, friendId, success, fail) {
  local
    .get(`/friend`, { params: { myId, friendId } })
    .then(success)
    .catch(fail);
}

function searchFriendInfoByRoomIdAndMyId(myId, roomId, success, fail) {
  console.log(myId, roomId);
  const payload = {
    myId: myId,
    id: roomId,
  };

  local.get(`/friend/countinfo`, { params: payload }).then(success).catch(fail);
}

export {
  addFriend,
  removeFriend,
  removeFriendByDto,
  bringFriendList,
  isMyFriend,
  searchFriendInfoByRoomIdAndMyId,
};
