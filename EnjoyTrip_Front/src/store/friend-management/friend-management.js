import { ref } from "vue";
import { defineStore } from "pinia";
import {
  addFriend,
  removeFriend,
  bringFriendList,
} from "@/api/friend/friend.js";
import { getLocalStorage } from "@/util/localstorage/localstorage";

const userId = getLocalStorage("userId");

export const useFriendManagementStore = defineStore(
  "friendManagementStore",
  () => {
    const friends = ref(null);
    // bringMyFriendsList();

    const bringMyFriendsList = () => {
      bringFriendList(
        getLocalStorage("userId"),
        (response) => {
          friends.value = response.data;
          console.log("friend목록 받아오기 완료");
        },
        (error) => {
          console.log(error);
        }
      );
    };

    return { friends, bringMyFriendsList };
  }
);
