import { localAxios } from "@/util/http-commons";
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

export { enterOrRegistPrivateChatRoom, bringPrivateChatRoomList };
