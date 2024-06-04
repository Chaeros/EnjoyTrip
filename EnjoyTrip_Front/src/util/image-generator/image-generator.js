import { registChatMessage } from "@/api/chat/chat.js";

export const sendImage = async () => {
  if (!selectedFile.value) return;

  const formData = new FormData();
  formData.append("file", selectedFile.value);
  formData.append("senderId", userId); // 필요한 경우 추가 데이터

  try {
    let accessToken = getLocalStorage("access_token");
    axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    const response = await axios.post(
      "http://localhost:8080/image/upload",
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );
    console.log(response.data);
    // 성공 시 처리
    sendMsg(response.data.imageUrl, userId, "IMAGE"); // 이미지 URL과 함께 메시지 전송
    registChatMessage(
      {
        roomId: currentSelectedRoomId.value,
        memberId: getLocalStorage("userId"),
        message: response.data.imageUrl,
        messageType: "IMAGE", // 이미지 타입 메시지
      },
      (response) => {
        console.log(response.data);
      },
      (error) => {
        console.log(error);
      }
    );
  } catch (error) {
    console.error(error);
  }
};
