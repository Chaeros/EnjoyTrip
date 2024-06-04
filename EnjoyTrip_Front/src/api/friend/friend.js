import { localAxios } from "@/util/http-commons";
import { getLocalStorage } from "@/util/localstorage/localstorage";
const userId = getLocalStorage("userId");
const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};
// let accessToken = getLocalStorage("access_token");
// local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

function addFriend(friendDto, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.post(`/friend`, friendDto).then(success).catch(fail);
}

function removeFriend(id, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .delete(`/friend/` + id)
    .then(success)
    .catch(fail);
}

function removeFriendByDto(friendDto, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.delete(`/friend`, { data: friendDto }).then(success).catch(fail);
}

function bringFriendList(myId, success, fail) {
  console.log(getLocalStorage("userId"));
  console.log(myId);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/friend/list/` + myId)
    .then(success)
    .catch(fail);
}

function isMyFriend(myId, friendId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
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
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
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
