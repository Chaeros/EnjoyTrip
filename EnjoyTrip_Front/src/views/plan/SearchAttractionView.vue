<script setup>
import axios from "axios";
import { ref } from "vue";
import KakaoMap from "@/components/KakaoMap.vue";
import AttractionItem from "@/components/item/AttractionItem.vue";
import {
  getListAttraction,
  getListSido,
  getListGugun,
  getListContentType,
} from "@/api/attraction";

const sidos = ref([]);
const guguns = ref([]);
const contentTypes = ref([]);


const attractions = ref([]);

const inputInformation = ref({
  sidoCode: "-1",
  gugunCode: "-1",
  contentTypeId: "-1",
  keyword: "",
});

const selectedAttractions = ref([]);

const isExistSelectView = ref(true);

const toggleSelectContent = () => {
  isExistSelectView.value = !isExistSelectView.value;
};

const addAttraction = (attraction) => {
  selectedAttractions.value.push(attraction);
};

const clickAttractionAdd = (attraction) => {
  selectedAttractions.value.push(attraction);
};

const deleteAttraction = (index) => {
  console.log(index);
  selectedAttractions.value.splice(index, 1);
};

const selectAttractionReset = () => {
  selectedAttractions.value = [];
};

async function callSidos(sido) {
  getListSido(
    ({ data }) => {
      console.dir(data);
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
      console.dir(data);
      contentTypes.value = data;
    },
    ({ error }) => {
      console.log(error);
    }
  )
}

// async function callContentType(){
//   let response = await axios.get("http://localhost:8/attraction/contenttype");
//   console.dir(response.data);
//   contentTypes.value = response.data;
// }
// callContentType();

async function searchAttractions() {
  console.dir(inputInformation.value);
  let response = await axios.get(
    "http://localhost:8/attraction/attraction/" +
      inputInformation.value.sidoCode +
      "/" +
      inputInformation.value.gugunCode +
      "/" +
      inputInformation.value.contentTypeId +
      "/" +
      inputInformation.value.keyword
  );
  console.dir(
    "http://localhost:8/attraction/attraction/" +
      inputInformation.value.sidoCode +
      "/" +
      inputInformation.value.gugunCode +
      "/" +
      inputInformation.value.contentTypeId +
      "/" +
      inputInformation.value.keyword
  );
  console.dir(response.data);
  attractions.value = response.data;
}


callSidos(1);
callContentTypes();
</script>

<template>
  <div class="wrap">
    <div class="all-content">
      <header id="header">
        <h1>
          <a>로고</a>
        </h1>
        <nav>
          <div class="list-group">
            <a
              href="#"
              class="list-group-item list-group-item-action active"
              aria-current="true"
            >
              날짜 확인
            </a>
            <a href="#" class="list-group-item list-group-item-action"
              >장소 선택</a
            >
            <a href="#" class="list-group-item list-group-item-action"
              >숙소 선택</a
            >
          </div>
          <div>
            <b-button disabled size="lg" variant="primary">Disabled</b-button>
            <b-button disabled size="lg">Also Disabled</b-button>
          </div>
        </nav>
      </header>
      <div class="left-content">
        <div class="selects">
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

          <select
            class="custom-select w-100"
            v-model="inputInformation.gugunCode"
          >
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
            <option disabled value="-1">컨텐츠</option>
            <option
              v-for="content in contentTypes"
              :value="content.contentId"
              :key="content.contentId"
            >
              {{ content.contentName }}
            </option>
          </select>
        </div>

        <form class="d-flex">
          <input
            class="form-control me-2"
            type="search"
            placeholder="Search"
            aria-label="Search"
            v-model="inputInformation.keyword"
          />
          <button
            class="btn btn-outline-success"
            type="submit"
            @click.prevent="searchAttractions"
          >
            Search
          </button>
        </form>

        <div class="attractions">
          <div class="attraction" v-for="attraction in attractions">
            <AttractionItem
              :attraction="attraction"
              @click-attraction-add="clickAttractionAdd"
            />
          </div>
        </div>
      </div>

      <div class="right-content">
        <div class="select-content-bar">
          <div class="select-content-wrap">
            <div class="select-content" v-if="isExistSelectView === true">
              <button @click="selectAttractionReset">초기화</button>
              <div
                class="select-attraction"
                v-for="(attraction, index) in selectedAttractions"
              >
                <img class="attraction-img" :src="attraction.firstImage" />
                <div class="select-attraction-content">
                  <h3>{{ attraction.title }}</h3>
                  <p>장소 : {{ attraction.addr1 }}</p>
                  <div class="attraction-indicators">
                    <p>좋아요 : {{ attraction.like }}</p>
                    <p>즐겨찾기 : {{ attraction.bookmark }}</p>
                  </div>
                </div>
                <button
                  id="deleteBtn"
                  type="button"
                  class="btn btn-primary"
                  @click="deleteAttraction(index)"
                >
                  삭제
                </button>
              </div>
            </div>
            <button class="select-content-button" @click="toggleSelectContent">
              <>
            </button>
          </div>
        </div>
        <KakaoMap class="kakao-map-container"></KakaoMap>
        <!-- <KakaoMap2></KakaoMap2> -->
        <!-- <KakaoMap class="kakao-map-container" /> -->
      </div>
    </div>
  </div>
</template>

<style scoped>
.wrap {
  margin: 0 auto;
  width: 100%; /* 부모 요소 너비 100% */
  display: flex;
  justify-content: center; /* 내부 컨텐츠 가운데 정렬 */
}

.all-content {
  display: flex;
  height: 100vh;
  max-width: 100%; /* 최대 너비 설정 */
  width: 100%; /* 내부 요소 전체 너비 사용 */
}

#header {
  display: flex;
  flex-direction: column;
  width: 80px;
  background-color: antiquewhite;
}

.left-content {
  display: flex;
  flex-direction: column;
  width: 400px;
}

.select-content-bar {
  z-index: 10;
  position: absolute;
}

.select-content-wrap {
  position: static;
  display: flex;
}

.select-content {
  width: 400px;
  height: 100vh;
  background-color: white;
  z-index: 1000;
  overflow-y: auto; /* 세로 스크롤바 자동 표시 */
  /* display: none; */
}

.select-attraction {
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
  border: 1px solid; /* 테두리 설정 */
  border-radius: 8px; /* 모서리 둥글게 만들기 */
  padding: 20px; /* 내부 여백 설정 */
  margin: 5px;
}

.select-content-button {
  height: 100%;
}

.right-content {
  flex: 1; /* 나머지 공간을 채우도록 설정 */
  background-color: blueviolet;
  width: 100%; /* right-content 영역을 전체 너비로 설정 */
  display: flex; /* 자식 요소들을 수직으로 배치하기 위해 추가 */
  position: relative;
}

.selects {
  display: flex;
}

.kakao-map-container {
  width: 100%;
  height: 100%;
  positoin: absolute;
}

.attractions {
  overflow-y: auto; /* 세로 스크롤바 자동 표시 */
}

.attraction {
  border: 1px solid; /* 테두리 설정 */
  border-radius: 8px; /* 모서리 둥글게 만들기 */
  padding: 20px; /* 내부 여백 설정 */
  margin: 5px;
}
</style>
