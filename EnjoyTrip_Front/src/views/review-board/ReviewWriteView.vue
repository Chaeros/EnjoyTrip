<template>
  <Header></Header>
  <div class="board-write-page">
    <div class="board-write-box">
      <input
        class="board-write-title"
        type="text"
        placeholder="제목을 입력하세요"
        v-model="article.title"
      />
      <div class="horizontal-line"></div>
      <div class="select-attraction-box">
        리뷰를 작성할 관광지를 선택해주세요!
        <button
          type="button"
          class="btn btn-outline-secondary select-attraction-btn"
          @click="openModal"
        >
          선택하기
        </button>
        <span
          class="attraction-unselected"
          v-show="selectAttractionItem === undefined"
        >
          아직 선택되지 않았어요!
        </span>
        <span
          class="attraction-selected"
          v-show="selectAttractionItem !== undefined"
        >
          선택되었어요! [ {{ selectAttractTitle }} ]
        </span>
      </div>
      <div
        id="editor"
        class="board-write-editor"
        contenteditable="true"
        style="border: 1px solid black; padding: 10px"
      ></div>
      <div class="board-write-buttons">
        <div>
          <button type="button" class="btn btn-outline-secondary user-button">
            목록으로
          </button>
        </div>
        <div>
          <button
            type="button"
            class="btn btn-outline-secondary user-button"
            @click="clickPostArticle"
          >
            게시하기
          </button>
        </div>
      </div>
    </div>
  </div>
  <div class="footer">
    <Footer></Footer>
  </div>
  <Modal :isVisible="showModal" title="관광지 선택" @close="closeModal">
    <div class="dropdowns">
      <select
        class="custom-select w-100"
        v-model="inputInformation.sidoCode"
        @change="callGuguns(inputInformation.sidoCode)"
      >
        <option value="-1">시도</option>
        <option
          v-for="sido in sidos"
          :value="sido.sidoCode"
          :key="sido.sidoCode"
        >
          {{ sido.sidoName }}
        </option>
      </select>

      <select class="custom-select w-100" v-model="inputInformation.gugunCode">
        <option disabled value="-1">구군</option>
        <option
          v-for="gugun in guguns"
          :value="gugun.gugunCode"
          :key="gugun.gugunCode"
        >
          {{ gugun.gugunName }}
        </option>
      </select>

      <select
        class="custom-select w-100"
        v-model="inputInformation.contentTypeId"
      >
        <option value="-1">컨텐츠</option>
        <option
          v-for="content in contentTypes"
          :value="content.contentId"
          :key="content.contentId"
        >
          {{ content.contentName }}
        </option>
      </select>
    </div>
    <div class="search-box">
      <input
        class="form-control me-2 search-text"
        type="search"
        placeholder="Search"
        aria-label="Search"
        v-model="keyword"
        @keyup.enter="searchAttractions"
      />
      <img
        src="@/img/search_icon.png"
        class="search_icon"
        @click="searchAttractions"
      />
    </div>
    <div class="attraction-set">
      <AttractionItem
        v-for="attraction in attractions"
        :key="attraction.contentId"
        :attraction="attraction"
        @click-attraction-add="selectAttraction"
      ></AttractionItem>
    </div>
  </Modal>
</template>

<script setup>
import { onMounted } from "vue";
import axios from "axios";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import Modal from "@/modal/SearchAttractionModal.vue";
import AttractionItem from "@/components/item/AttractionItem.vue";
import { ref } from "vue";
import {
  getListAttraction,
  getListSido,
  getListGugun,
  getListContentType,
} from "@/api/attraction";
import { addAttractionReview } from "@/api/attraction-board/attraction-board.js";
import { useMemberStore } from "@/store/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const showModal = ref(false);
const selectAttractionItem = ref();
const selectAttractTitle = ref();
const attractions = ref();
const keyword = ref();
const sidos = ref([]);
const guguns = ref([]);
const contentTypes = ref([]);
const article = ref({
  title: "",
  content: "",
  memberId: userInfo._value.id,
  attractionId: "",
});
const inputInformation = ref({
  sidoCode: "",
  gugunCode: "",
  contentTypeId: "",
  keyword: "",
});

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

const selectAttraction = (attractionItem) => {
  selectAttractionItem.value = attractionItem;
  article.value.attractionId = attractionItem.attractionInfo.contentId;
  selectAttractTitle.value = attractionItem.attractionInfo.title;
  closeModal();
};

const { VITE_VUE_API_URL } = import.meta.env;

onMounted(() => {
  const editor = document.getElementById("editor");

  if (editor) {
    editor.addEventListener("paste", async (event) => {
      console.log("occur event");
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
            interact.modifiers.restrictSize({
              min: { width: 50, height: 50 },
            }),
          ],
          inertia: true,
          autoScroll: {
            container: editor,
            margin: 50,
            distance: 5,
            interval: 10,
          },
        });

        img.addEventListener("mousedown", (event) => {
          event.preventDefault();
        });
      }
    }

    // 기존 이미지에 크기 조절 기능 적용
    addImageResizeFunctionality();
  } else {
    console.error("Editor element not found");
  }
});

async function callSidos(sido) {
  getListSido(
    ({ data }) => {
      sidos.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
  callGuguns(sido);
}

async function callGuguns(sido) {
  getListGugun(
    sido,
    ({ data }) => {
      guguns.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
}

async function callContentTypes() {
  getListContentType(
    ({ data }) => {
      contentTypes.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  );
}

async function searchAttractions() {
  console.log("shoot api");
  console.log(inputInformation.value);
  getListAttraction(
    inputInformation.value,
    ({ data }) => {
      attractions.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
}
callSidos(1);
callContentTypes();

const clickPostArticle = () => {
  const editor = document.getElementById("editor");
  console.log(editor);
  article.value.content = editor.innerHTML;
  addAttractionReview(article.value);
};
</script>

<style scoped>
/* 크기 조절 핸들을 처리하기 위한 스타일 추가 */
img.resizable {
  display: block;
  max-width: 100%;
  overflow: auto;
  cursor: nwse-resize; /* 크기 조절 가능할 때 커서 모양 */
  resize: both; /* CSS를 통해 크기 조절 가능하게 설정 */
}

.board-write-page {
  display: flex;
  /* height: 100vh; */
}

.board-write-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
}

.board-write-title {
  width: 1200px;
  height: 80px;
  font-size: 40px;
  margin-top: 15px;
  border: none;
}

.horizontal-line {
  width: 1200px;
  height: 2px;
  background-color: black;
  margin: 0; /* 선과 위 요소 사이에 간격을 줄 수 있습니다 */
}

.select-attraction-box {
  margin: 10px 0;
  width: 1200px;
}

.select-attraction-btn {
}

.board-write-editor {
  width: 1200px;
  height: 60vh;
  overflow-y: auto;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /*그림자 효과 */
}

.postButton {
  margin: 20px;
  width: 200px;
}
.board-write-buttons {
  margin: 10px 0;
  width: 1200px;
  display: flex;
  justify-content: space-between;
}
.user-button {
  font-size: 20px;
  text-align: center;
}
.footer {
  margin-top: 25px;
}
.dropdowns {
  display: flex;
}
.dropdown {
  margin: 3px;
}
.search-box {
  position: relative;
}
.search_icon {
  width: 20px;
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}
.attraction-set {
  height: 600px; /* 높이를 필요에 맞게 조정하세요 */
  overflow-y: scroll; /* 상하 스크롤을 항상 표시 */
  /* 또는 overflow-y: auto; /* 필요할 때만 상하 스크롤을 표시 */
}
.custom-select {
  border-radius: 8px;
  font-size: 15px;
  font-weight: bold;
  margin: 3px;
}
.attraction-selected {
  color: green;
}
.attraction-unselected {
  color: red;
}
</style>
