<template>
  <v-app>
    <v-app-bar :elevation="2">
      <template v-slot:prepend>
        <v-app-bar-nav-icon></v-app-bar-nav-icon>
      </template>

      <v-app-bar-title>Application Bar</v-app-bar-title>

      <template v-slot:append>
        <v-btn icon="mdi-heart"></v-btn>

        <v-btn icon="mdi-magnify"></v-btn>

        <v-btn icon="mdi-dots-vertical"></v-btn>
      </template>
    </v-app-bar>
    <div class="board-write-page">
      <div class="board-write-box">
        <input
          class="board-write-title"
          type="text"
          placeholder="제목을 입력하세요"
        />
        <div
          id="editor"
          class="board-write-editer"
          contenteditable="true"
          style="border: 1px solid black; padding: 10px"
        ></div>
        <button>게시하기</button>
      </div>
    </div>
  </v-app>
</template>

<script setup>
import { onMounted } from "vue";
import axios from "axios";
import interact from "interactjs";

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
                `<img src="${imageUrl}" class="resizable" style="max-width: 100%; overflow: auto;" />`
              );
              console.log(imageUrl);
              addImageResizeFunctionality();
            })
            .catch((error) => {
              console.error("Error uploading image:", error);
            });
        }
      }
    });

    function addImageResizeFunctionality() {
      const imgs = editor.getElementsByTagName("img");
      for (const img of imgs) {
        interact(img).resizable({
          edges: { left: true, right: true, bottom: true, top: true },
          listeners: {
            move(event) {
              const { target } = event;
              let x = parseFloat(target.getAttribute("data-x")) || 0;
              let y = parseFloat(target.getAttribute("data-y")) || 0;

              // update the element's style
              target.style.width = event.rect.width + "px";
              target.style.height = event.rect.height + "px";

              // translate when resizing from top or left edges
              x += event.deltaRect.left;
              y += event.deltaRect.top;

              target.style.transform = `translate(${x}px, ${y}px)`;

              target.setAttribute("data-x", x);
              target.setAttribute("data-y", y);
            },
          },
          modifiers: [
            interact.modifiers.restrictEdges({
              outer: "parent",
            }),
          ],
          inertia: true,
        });
      }
    }

    // 기존 이미지에 크기 조절 기능 적용
    addImageResizeFunctionality();
  } else {
    console.error("Editor element not found");
  }
});
</script>

<style scoped>
/* 크기 조절 핸들을 처리하기 위한 스타일 추가 */
img.resizable {
  display: block;
  max-width: 100%;
  overflow: auto;
}
.board-write-page {
  display: flex;
  height: 100vh;
}
.board-write-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
}
.board-write-title {
  margin: 70px 0 0 0;
  width: 800px;
  height: 100px;
  font-size: 30px;
}
.board-write-editer {
  width: 800px;
  height: 100vh;
  overflow-y: auto;
}
</style>
