<template>
  <div class="header-wrap">
    <div class="header">
      <div class="left-side-header">
        <a class="header-logo">ChanGon</a>
        <a>관광지찾기</a>
        <a>커뮤니티</a>
      </div>
      <div class="right-side-header">
        <div class="search-box">
          <input
            class="form-control me-2 search-text"
            type="search"
            placeholder="Search"
            aria-label="Search"
          />
          <img
            src="@/img/search_icon.png"
            alt="Show Password"
            title="Show Password"
            class="search_icon"
            @mousedown="showPassword(true)"
            @mouseup="showPassword(false)"
          />
        </div>
        <button class="login-btn">로그인</button>
        <button class="sign-up-btn">회원가입</button>
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.header-wrap {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /*그림자 효과 */
  height: 80px;
  align-content: center;
}
.header {
  display: flex;
  justify-content: space-between;
  margin: 0 auto;
  width: 1200px;
}
.header-logo {
  font-size: 30px;
}
.header > * > * {
  margin: 0 10px;
}
.left-side-header {
  display: flex;
  align-items: center;
  width: 500px;
}
.right-side-header {
  display: flex;
  align-items: center;
  width: 700px;
}
.search-box {
  position: relative;
}
.search-text {
  width: 400px;
}
.search_icon {
  width: 20px;
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}
.login-btn {
  width: 100px;
}
.sign-up-btn {
  width: 100px;
}
</style>
