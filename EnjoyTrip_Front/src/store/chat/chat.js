import { ref } from "vue";
import { defineStore } from "pinia";
import {
  enterOrRegistPrivateChatRoom,
  bringPrivateChatRoomList,
} from "@/api/chat/chat.js";
import { getLocalStorage } from "@/util/localstorage/localstorage";

export const useChatStore = defineStore("chatStore", () => {
  const userId = getLocalStorage("userId");
  const myChatRoom = ref(null);
  const currentSelectedRoomId = ref(null);
  const unreadMessageCountList = ref([]);
  // bringMyFriendsList();

  const enterOrRegistChatRoom = (roomInfoDto) => {
    console.log(roomInfoDto);
    enterOrRegistPrivateChatRoom(
      roomInfoDto,
      (response) => {
        currentSelectedRoomId.value = response.data;
        console.log(currentSelectedRoomId.value);
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const bringMyChatRoomList = () => {
    console.log("진입2");
    bringPrivateChatRoomList(
      getLocalStorage("userId"),
      (response) => {
        myChatRoom.value = response.data;
        console.log(myChatRoom.value);
      },
      (error) => {
        console.log("에러낫다고");
        console.log(error);
      }
    );
  };

  return {
    myChatRoom,
    currentSelectedRoomId,
    enterOrRegistChatRoom,
    bringMyChatRoomList,
    unreadMessageCountList,
  };
});
