<script setup>
import { onMounted, ref, watch } from "vue";
import axios from "axios";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import Modal from "@/modal/SearchAttractionModal.vue";
import AttractionItem from "@/components/item/AttractionItem.vue";
import {
  getListAttraction,
  getListSido,
  getListGugun,
  getListContentType,
} from "@/api/attraction";
import { AttractionDetailByContentId } from "@/api/attraction/attraction.js";
import {
  addAttractionReview,
  modifyAttractionBoard,
} from "@/api/attraction-board/attraction-board.js";
import { useMemberStore } from "@/store/member";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { getAttractionReviewArticle } from "@/api/attraction-board/attraction-board.js";
import { getLocalStorage } from "@/util/localstorage/localstorage";
import Swal from "sweetalert2";

const router = useRouter();
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const showModal = ref(false);
const selectAttractionItem = ref();
const selectAttractTitle = ref();
const attractions = ref([]);
const keyword = ref("");
const sidos = ref([]);
const guguns = ref([]);
const contentTypes = ref([]);
const route = useRoute();
const attractionBoardReviewId = ref(null);
const isModify = ref(false);

const article = ref({
  id: "",
  title: "",
  content: "",
  memberId: userInfo.value?.id || "",
  attractionId: "",
  imageUrl: "",
});
const inputInformation = ref({
  sidoCode: 0,
  gugunCode: 0,
  contentTypeId: 0,
  keyword: "",
});
const fileName = ref("");

watch(userInfo, (newValue) => {
  if (newValue) {
    article.value.memberId = newValue.id;
  }
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
  attractionBoardReviewId.value = route.query.attractionBoardReviewId;
  isModify.value = route.query.isModify;

  if (isModify.value) {
    getAttractionReviewArticle(
      attractionBoardReviewId.value,
      (response) => {
        const data = response.data;
        article.value.id = attractionBoardReviewId.value;
        article.value.title = data.title;
        article.value.content = data.content;
        article.value.memberId = data.memberId;
        article.value.attractionId = data.attractionId;
        article.value.imageUrl = data.imageUrl;
        AttractionDetailByContentId(
          article.value.attractionId,
          (response) => {
            selectAttractionItem.value = response.data.attractionInfo;
            selectAttractTitle.value = response.data.attractionInfo.title;
          },
          (error) => {
            console.log(error);
          }
        );
      },
      (error) => {
        console.log(error);
      }
    );
  }

  if (editor) {
    editor.addEventListener("paste", async (event) => {
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
              const imageUrl = VITE_VUE_API_URL + response.data.url;
              document.execCommand(
                "insertHTML",
                false,
                `<img src="${imageUrl}" class="resizable" style="max-width: 100%; overflow: auto;" />`
              );
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

/* 무한 스크롤 */

const page = ref(1);
const size = ref(10);
const isLoading = ref(false);
const hasMore = ref(true);
const bottomElement = ref("");

const observer = new IntersectionObserver(
  (entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        console.dir("intersecting");
        loadMoreAttractions();
      }
    });
  },
  { rootMargin: "0px 0px 100px 0px" }
);

async function initSearchAttractions() {
  // 페이지 번호와 결과를 초기화
  page.value = 1;
  attractions.value = [];
  hasMore.value = true;
  isLoading.value = false;

  if (bottomElement.value) {
    observer.observe(bottomElement.value);
  }

  loadMoreAttractions();
}

async function loadMoreAttractions() {
  if (isLoading.value || !hasMore.value) return;

  isLoading.value = true;
  getListAttraction(
    {
      ...inputInformation.value,
      page: page.value,
      size: size.value,
    },
    ({ data }) => {
      if (data.length < size.value) {
        hasMore.value = false;
      }
      attractions.value.push(...data);
      page.value++;
      isLoading.value = false;
    },
    (error) => {
      console.log(error);
      isLoading.value = false;
    }
  );
}

callSidos(1);
callContentTypes();

const clickReturnList = () => {
  router.push({ name: "reviewBoardList" });
};

const clickPostArticle = () => {
  const editor = document.getElementById("editor");
  article.value.content = editor.innerHTML;
  addAttractionReview(
    article.value,
    (response) => {
      router.push({ name: "reviewBoardList" });
    },
    (error) => {
      console.log(error);
      Swal.fire({
        icon: "error",
        title: error.response.data.message,
      });
    }
  );
};

const clickModifyArticle = () => {
  const editor = document.getElementById("editor");
  article.value.content = editor.innerHTML;
  modifyAttractionBoard(
    article.value,
    (response) => {
      router.push({ name: "reviewBoardList" });
    },
    (error) => {
      console.log(error);
    }
  );
};

const handleFileUpload = async (event) => {
  const file = event.target.files[0];
  if (file) {
    fileName.value = file.name;
    const formData = new FormData();
    formData.append("file", file);

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
      article.value.imageUrl = response.data.url;
    } catch (error) {
      console.error("Error uploading file:", error);
    }
  }
};
</script>

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
      <div class="select-attraction-box line-height-center">
        [필수] 리뷰를 작성할 관광지를 선택해주세요!
        <div class="file-upload-wrapper" @click="openModal">
          <input id="attractionInput" type="text" class="file-input" />
          <label for="attractionInput" class="file-upload-button">
            <div class="fileInput-font">관광지선택</div>
          </label>
        </div>
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
      <div class="attach-represant-img-box">
        <span class="line-height-center"
          >[선택] 대표 이미지를 선택해주세요!</span
        >
        <div class="file-upload-wrapper">
          <input
            type="file"
            id="fileInput"
            @change="handleFileUpload"
            class="file-input"
          />
          <label for="fileInput" class="file-upload-button">
            <div class="fileInput-font">파일선택</div>
          </label>
          <span v-if="fileName">{{ fileName }}</span>
          <span v-if="isModify" class="modify-notice"
            >(수정시 이전에 등록한 이미지가 자동 업로드 되어있습니다)</span
          >
        </div>
      </div>
      <div
        id="editor"
        class="board-write-editor"
        contenteditable="true"
        style="border: 1px solid black; padding: 10px"
        v-html="article.content"
      ></div>
      <div class="board-write-buttons">
        <div>
          <img src="@/img/coldragon2-removebg.png" class="changon-img" />
          <span
            >글 작성 중간에 이미지를 Ctr+C, Ctr+V하여 붙여놓을 수
            있습니다.</span
          >
        </div>
        <div>
          <button
            type="button"
            class="btn btn-outline-dark user-button"
            @click="clickReturnList"
          >
            <div class="btn-inner-font">목록으로</div>
          </button>
          <template v-if="isModify">
            <button
              type="button"
              class="btn btn-outline-dark user-button"
              @click="clickModifyArticle"
            >
              <div class="btn-inner-font">수정하기</div>
            </button>
          </template>
          <template v-else>
            <button
              type="button"
              class="btn btn-outline-dark user-button"
              @click="clickPostArticle"
            >
              <div class="btn-inner-font">게시하기</div>
            </button>
          </template>
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
        <option value="0">시도</option>
        <option
          v-for="sido in sidos"
          :value="sido.sidoCode"
          :key="sido.sidoCode"
        >
          {{ sido.sidoName }}
        </option>
      </select>

      <select class="custom-select w-100" v-model="inputInformation.gugunCode">
        <option value="0">구군</option>
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
        <option value="0">컨텐츠</option>
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
        @click="initSearchAttractions"
      />
    </div>
    <div class="attraction-set">
      <AttractionItem
        v-for="attraction in attractions"
        :key="attraction.contentId"
        :attraction="attraction"
        @click-attraction-add="selectAttraction"
      ></AttractionItem>
      <div ref="bottomElement" class="bottom-element"></div>
    </div>
  </Modal>
</template>

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
  margin: 0;
}

.select-attraction-box {
  margin: 10px 0;
  width: 1200px;
  display: flex;
  line-height: 100%;
}

.line-height-center {
  line-height: 100%;
}

.select-attraction-btn {
}

.attach-represant-img-box {
  display: flex;
  margin: 10px 0;
  width: 1200px;
  line-height: 100%;
}

.modify-notice {
  color: green;
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
  margin-left: 5px;
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
  height: 600px;
  overflow-y: scroll;
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
.btn-inner-font {
  font-weight: bold;
}
.changon-img {
  width: 60px;
}
.file-upload-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
}

.file-input {
  display: none;
}

.file-upload-button {
  display: inline-block;
  padding: 5px 10px;
  border: 1px solid #ced4da;
  border-radius: 5px;
  color: #495057;
  font-size: 14px;
  text-align: center;
  cursor: pointer;
  transition: background-color 0.3s, border-color 0.3s;
  margin: 0 3px;
}
.fileInput-font {
  font-size: 15px;
  font-weight: bold;
}
.file-upload-button:hover {
  background-color: #f8f9fa;
  border-color: #80bdff;
}

.file-upload-filename {
  font-size: 14px;
  color: #333;
}

.bottom-element {
  height: 1px;
  width: 100%;
}
</style>
