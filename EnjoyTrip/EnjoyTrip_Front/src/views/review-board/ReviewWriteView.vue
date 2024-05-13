<template>
  <div
    id="editor"
    contenteditable="true"
    style="border: 1px solid black; padding: 10px"
  ></div>
</template>

<script setup>
import { onMounted } from "vue";
import axios from "axios";

const { VITE_VUE_API_URL } = import.meta.env;

onMounted(() => {
  const editor = document.getElementById("editor");

  if (editor) {
    editor.addEventListener("paste", async (event) => {
      console.log("occure event");
      const items = (event.clipboardData || event.originalEvent.clipboardData)
        .items;

      for (const item of items) {
        if (item.kind === "file") {
          const file = item.getAsFile();
          const formData = new FormData();
          formData.append("file", file);

          // 서버에 이미지 업로드
          await axios
            .post("http://localhost:8080/image/upload", formData, {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            })
            .then((response) => {
              console.log(response.data.url);
              const imageUrl = VITE_VUE_API_URL + response.data.url; // 서버에서 반환된 이미지 URL
              // 에디터에 이미지 URL 삽입
              document.execCommand(
                "insertHTML",
                false,
                `<img src="${imageUrl}" />`
              );
              console.log(imageUrl);
            })
            .catch((error) => {
              console.error("Error uploading image:", error);
            });
        }
      }
    });
  } else {
    console.error("Editor element not found");
  }
});
</script>

<style scoped></style>
