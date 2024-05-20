import { localAxios } from "@/util/http-commons";
import { storeToRefs } from "pinia";
import { useChatStore } from "@/store/chat/chat";

const local = localAxios();
const headers = {
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
};

function enterOrRegistPrivateChatRoom(privateChatRoom, success, fail) {
  local.post(`/privatechatroom`, privateChatRoom).then(success).catch(fail);
}

function bringPrivateChatRoomList(memberId, success, fail) {
  local
    .get(`/privatechatroom/` + memberId)
    .then(success)
    .catch(fail);
}

function searchPrivateChatRoom(myId, opponentId, success, fail) {
  console.log(myId, opponentId);
  local
    .get(`/privatechatroom`, { params: { myId, opponentId } })
    .then(success)
    .catch(fail);
}

function registChatMessage(chatMessage, success, fail) {
  local.post(`/chatmessage`, chatMessage).then(success).catch(fail);
}

function searchChatMessageList(success, fail) {
  const chatStore = useChatStore(); // 함수 내부에서 Pinia 스토어 호출
  const { currentSelectedRoomId } = storeToRefs(chatStore);

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
