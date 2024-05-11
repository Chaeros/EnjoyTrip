<template>
  <div
    id="editor"
    contenteditable="true"
    style="border: 1px solid black; padding: 10px"
  ></div>
</template>

<script setup>
import { onMounted } from "vue";

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
          const response = await fetch("/upload", {
            method: "POST",
            body: formData,
          });

          const data = await response.json();
          const imageUrl = data.url; // 서버에서 반환된 이미지 URL

          // 에디터에 이미지 URL 삽입
          document.execCommand(
            "insertHTML",
            false,
            `<img src="${imageUrl}" />`
          );
        }
      }
    });
  } else {
    console.error("Editor element not found");
  }
});
</script>

<style scoped></style>
