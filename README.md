# ChanGon
웹 소켓을 사용해 채팅이 가능한 여행지 조회 및 리뷰 커뮤니티 서비스

## 개발환경
- **Back-end**: Spring Boot 3.2.5
- **Front-end**: Vue.js
- **Database**: MySQL 8.0.33

## 주요 기능
- 소셜 및 일반 로그인
    - JWT, Spring Security, Oauth2 활용
- 여행지 일정 세우기
- 여행지 리뷰 커뮤니티
- 마이 페이지(정보 수정 및 친구 관리)
- 유저 간 채팅

## 페이지

### 1. 메인 페이지
<img src="https://github.com/user-attachments/assets/acfb6899-5063-42b6-a12e-5aa01a996789" width="600px">
<br>

- '좋아요'가 많이 불러진 순서대로 여행지 리뷰 출력
- 리뷰 별 좋아요, 댓글, 조회 수 출력
- 내 여행 계획 최신순으로 출력
<br><br>

### 2. 로그인/회원가입 페이지
<img src="https://github.com/user-attachments/assets/e75d4f1f-79c8-423e-aaec-c1b0a070f404" width="300px">
<img src="https://github.com/user-attachments/assets/6be3deec-b1f6-4642-95c2-8ebb638efbc3" width="300px">

- 소셜 로그인 구현(Kakao, Naver)
- 일반 로그인 구현
  <br>
  (Spring Security, JWT 활용)

<br><br>

### 3. 리뷰 관련 페이지
<img src="https://github.com/user-attachments/assets/9d1a6971-87b8-4e4c-bdfe-f2db36c00062" width="300px">
<img src="https://github.com/user-attachments/assets/a827a07a-47eb-4881-ac5e-f3ed2c6d8026" width="300px">
<img src="https://github.com/user-attachments/assets/045c0f37-dd61-4078-ad93-14a2aa52f75e" width="300px">

- 리뷰 페이징 및 조회 기능 구현
- 리뷰 작성 및 수정 기능 구현
- 리뷰 댓글 작성 및 좋아요 기능 구현

<br><br>

### 4. 여행지 조회 및 여행 일정 작성 페이지
