// src/main.js

import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import { useKakao } from "vue3-kakao-maps/@utils";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { createPinia } from "pinia";

useKakao("40760ed76b3f09175bb64a80868ef451");

const app = createApp(App);
const pinia = createPinia();

app.use(pinia); // Pinia를 먼저 사용하도록 설정합니다.
app.use(router);
app.mount("#app");
