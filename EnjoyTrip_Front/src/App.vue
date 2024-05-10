<script setup>
import { RouterLink, RouterView } from "vue-router";
</script>

<template>
  <body></body>
  <RouterLink to="/" />
  <RouterLink to="/oauth2/sign-up" />
  <RouterLink to="/searchattraction" />
  <RouterView />
</template>

<style scoped></style>
