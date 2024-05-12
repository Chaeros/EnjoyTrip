<script setup>
import Header from "@/components/Header.vue";
</script>

<template>
  <div>
    <Header></Header>
    <h1>Home</h1>
    <main>
      <nav>
        <ul>
          <li><router-link :to="{ name: 'home' }">Home</router-link></li>
          <li>
            <router-link :to="{ name: 'searchattraction' }"
              >Search Attraction</router-link
            >
          </li>
          <li><router-link :to="{ name: 'login' }">Login</router-link></li>
          <li>
            <router-link :to="{ name: 'normalSingUp' }"
              >Normal Sign Up</router-link
            >
          </li>
          <li>
            <router-link :to="{ name: 'reviewBoardWrite' }"
              >ReviewBoardWrite</router-link
            >
          </li>
        </ul>
      </nav>
    </main>
  </div>
</template>
