import { ref } from "vue";
import { defineStore } from "pinia";
import {
  enterOrRegistPrivateChatRoom,
  bringPrivateChatRoomList,
} from "@/api/chat/chat.js";
import { getLocalStorage } from "@/util/localstorage/localstorage";

const userId = getLocalStorage("userId");

export const useChatStore = defineStore("chatStore", () => {
  const myChatRoom = ref(null);
  const currentSelectedRoomId = ref(null);
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
    bringPrivateChatRoomList(
      userId,
      (response) => {
        myChatRoom.value = response.data;
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return {
    myChatRoom,
    currentSelectedRoomId,
    enterOrRegistChatRoom,
    bringMyChatRoomList,
  };
});
