<script setup></script>

<template>
  <body>
    <div class="footer">
      <div class="footer-content">
        <div class="footer-logo-info-wrap">
          <div class="footer-logo">
            <img src="@/img/coldragon.png" alt="CHANGON Logo" />
          </div>
          <div class="footer-info">
            SSAFY 대전 7반 | 박찬호 이경곤<br />
            사업자 등록번호: 010-8938-xxxx<br />
            서울특별시 강남구 역삼로 217, 4F<br />
            contact@myro.co.kr
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
  background-color: #f8f8f8;
  padding: 20px;
  text-align: center;
  border-top: 1px solid #ccc;
}
.footer .footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}
.footer .footer-content .footer-logo img {
  max-height: 50px;
}
.footer .footer-content .footer-info {
  color: #666;
  font-size: 14px;
  line-height: 1.6;
}
.footer .footer-content .footer-links {
  display: flex;
  gap: 20px;
}
.footer .footer-content .footer-links a {
  text-decoration: none;
  color: #666;
  font-size: 14px;
}
.footer .footer-content .footer-icons {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}
.footer .footer-content .footer-icons img {
  max-height: 30px;
}
</style>
