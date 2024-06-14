import { localAxios } from "@/util/http-commons";
import { storeToRefs } from "pinia";
import { useChatStore } from "@/store/chat/chat";
import { getLocalStorage } from "@/util/localstorage/localstorage";

const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};
let accessToken = getLocalStorage("access_token");
local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

function enterOrRegistPrivateChatRoom(privateChatRoom, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.post(`/privatechatroom`, privateChatRoom).then(success).catch(fail);
}

function bringPrivateChatRoomList(memberId, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/privatechatroom/` + memberId)
    .then(success)
    .catch(fail);
}

function searchPrivateChatRoom(myId, opponentId, success, fail) {
  console.log(myId, opponentId);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/privatechatroom`, { params: { myId: myId, opponentId: opponentId } })
    .then(success)
    .catch(fail);
}

function registChatMessage(chatMessage, success, fail) {
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local.post(`/chatmessage`, chatMessage).then(success).catch(fail);
}

function searchChatMessageList(success, fail) {
  const chatStore = useChatStore(); // 함수 내부에서 Pinia 스토어 호출
  const { currentSelectedRoomId } = storeToRefs(chatStore);
  let accessToken = getLocalStorage("access_token");
  local.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  local
    .get(`/chatmessage/` + currentSelectedRoomId.value)
    .then(success)
    .catch(fail);
}

export {
  enterOrRegistPrivateChatRoom,
  bringPrivateChatRoomList,
  registChatMessage,
  searchPrivateChatRoom,
  searchChatMessageList,
};
