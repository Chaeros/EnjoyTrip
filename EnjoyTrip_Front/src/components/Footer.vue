<script setup></script>

<template>
  <body>
    <div class="footer">
      <div class="footer-content">
        <div class="footer-logo-info-wrap">
          <div class="footer-info">
            <div class="footer-info-element">
              SSAFY 대전 7반 | 박찬호 이경곤
            </div>
            <div class="footer-info-element">
              사업자 등록번호: 010-8938-xxxx
            </div>
            <div class="footer-info-element">contact@myro.co.kr</div>
          </div>
        </div>
        <div class="footer-links">
          <a href="#">이용약관</a>
          <a href="#">개인정보처리방침</a>
          <a href="#">고객지원</a>
          <a href="#">1:1 문의</a>
        </div>
        <div class="footer-icons">
          <a href="#"><img src="" alt="SSAFY Icon" /></a>
          <a href="#"><img src="" alt="관광데이터 Icon" /></a>
          <a href="#"><img src="" alt="KAKAO Icon" /></a>
        </div>
        <div class="footer-app-links">
          <a href="#"><img src="" alt="App Store" /></a>
          <a href="#"><img src="" alt="Google Play" /></a>
        </div>
      </div>
    </div>
  </body>
</template>

<style scoped>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
}
.footer-logo-info-wrap {
  display: flex;
}
.footer {
  background-color: #000;
  color: #fff;
  padding: 40px 0;
  text-align: center;
  border-top: 1px solid #ccc;
}
.footer .footer-content {
  max-width: 1200px;
  /* margin: 0 auto; */
  display: flex;
  align-items: center;
  gap: 80px;
}
.footer .footer-content .footer-logo img {
  max-height: 50px;
}
.footer .footer-content .footer-info {
  color: #f1f5f6;
  font-size: 15px;
  line-height: 1.6;
  padding: 10px;
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-left: 35px;
}
.footer .footer-content .footer-links {
  display: flex;
  flex-direction: column;
  gap: 20px;
}
.footer-links a {
  text-decoration: none;
  color: #f1f5f6;
  font-size: 14px;
}
.footer .footer-content .footer-icons {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 10px;
}

.footer-app-links {
  display: flex;
  flex-direction: column;
}

.footer .footer-content .footer-icons img {
  max-height: 30px;
}

.footer-icons a {
  text-decoration: none;
  color: #f1f5f6;
}

.footer-app-links a {
  text-decoration: none;
  color: #f1f5f6;
}
</style>
