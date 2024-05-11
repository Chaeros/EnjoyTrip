import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import SearchAttractionView from "@/views/plan/SearchAttractionView.vue";
import AddUserInfoView from "@/views/user/AddUserInfoView.vue";
import LoginView from "@/views/user/LoginView.vue";
import SignUpView from "@/views/user/SignUpView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/oauth2/sign-up",
      name: "oauth2SignUp",
      component: AddUserInfoView,
    },
    {
      path: "/searchattraction",
      name: "searchattraction",
      component: SearchAttractionView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/login/oauth2/code/kakao",
      name: "kakaoLoginSuccess",
      component: AddUserInfoView,
    },
    {
      path: "/normal/sign-up",
      name: "normalSingUp",
      component: SignUpView,
    },
    // {
    //   path: '/board',
    //   name: 'board',
    //   component: () => import('../view/BoardView.vue'),
    //   redirect: { name : 'boardlist' },
    //   children: [
    //     {
    //       path: 'list',  // 자식은 경로 앞에 '/'를 붙이지 않는다.
    //       name: 'boardlist',
    //       component: () => import('../components/board/BoardList.vue')
    //     },
    //     {
    //       path: 'detail/:no',
    //       name: 'boarddetail',
    //       component: () => import('../components/board/BoardDetail.vue')
    //     },
    //     {
    //       path: 'write',
    //       name: 'boardwrite',
    //       component: () => import('../components/board/BoardWrite.vue')
    //     }
    //   ]
    // }
  ],
});

export default router;
