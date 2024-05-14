import App from './App.vue';
import router from './router';
import { createApp } from 'vue';
import { useKakao } from 'vue3-kakao-maps/@utils';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

useKakao('40760ed76b3f09175bb64a80868ef451');
const app = createApp(App);
app.use(router);
app.mount('#app');
