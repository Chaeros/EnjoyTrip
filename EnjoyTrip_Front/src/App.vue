<script setup>
import { RouterLink, RouterView } from "vue-router";
</script>

<template>
  <body></body>
  <RouterLink to="/" />
  <RouterLink to="/oauth2/sign-up" />
  <RouterLink to="/searchattraction" />
  <RouterLink to="/normal/sign-up" />
  <RouterLink to="/reviewboard/write" />
  <RouterLink to="/reviewboard/list" />
  <RouterLink to="/reviewboard/detail" />
  <RouterView />
</template>

<style scoped></style>
