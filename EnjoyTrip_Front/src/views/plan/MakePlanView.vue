<script setup>
import { useRoute } from 'vue-router';

const route = useRoute();
const planId = route.params.planId;
console.dir(route.params);
console.dir(planId);
</script>

<template>
  <div>
    <h1>여행 계획 만들기</h1>
  </div>
</template>

<style scoped></style>
