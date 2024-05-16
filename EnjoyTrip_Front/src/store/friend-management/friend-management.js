import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";

export const useFriendManagementStore = defineStore(
  "friendManagementStore",
  () => {
    const friends = ref(null);

    return { friends };
  }
);
