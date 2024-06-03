<script setup></script>

<template class="footer-box">
  <body>
    <div class="footer">
      <div class="footer-content">
        <div class="footer-logo-info-wrap">
          <div class="footer-info">
            <div class="footer-info-element">
              SSAFY 대전 7반 | 박찬호 이경곤
            </div>
            <div class="footer-info-element">문의 : 010-8938-xxxx</div>
            <div class="footer-info-element">chpark6851@naver.com</div>
          </div>
        </div>
        <!-- <div class="footer-links">
          <a href="#">이용약관</a>
          <a href="#">개인정보처리방침</a>
          <a href="#">고객지원</a>
          <a href="#">1:1 문의</a>
        </div> -->
        <div class="footer-icons-box">
          <div class="footer-icons">
            <img
              class="ssafy-icon-img"
              src="@/img/icon/ssafy_icon.png"
              alt="SSAFY Icon"
            />
            <img
              class="korea-tourism-organization-icon-img"
              src="@/img/icon/korea_tourism_organization_icon.png"
              alt="관광데이터 Icon"
            />
          </div>
          <!-- <div class="footer-app-links">
            <a href="#"><img src="" alt="App Store" /></a>
            <a href="#"><img src="" alt="Google Play" /></a>
          </div> -->
        </div>
      </div>
    </div>
  </body>
</template>

<style scoped>
.footer-box {
  width: 1200px;
  margin: 0 auto;
}
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
  display: flex;
  align-items: center;
  gap: 80px;
  margin: 0 auto;
  justify-content: space-between;
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
.footer-icons-box {
  margin-right: 50px;
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

.ssafy-icon-img {
  width: 200px;
  height: 80px;
}

.korea-tourism-organization-icon-img {
  width: 200px;
  height: 50px;
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
