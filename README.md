# ChanGon ( 찬호 & 경곤 )
여행지 조회, 여행 일정 수립, 유저 간 채팅 기능을 제공하는 여행 커뮤니티 서비스입니다.
<br><br>

## 개발환경
- **Back-end**: Spring Boot 3.2.5
- **Front-end**: Vue.js
- **Database**: MySQL 8.0.33
- **SQL Mapper**: MyBatis
<br><br>

## 주요 기능
- 소셜/일반 로그인/회원가입
    - JWT, Spring Security, Oauth2 를 활용했습니다.
- 여행지 리뷰 커뮤니티를 제공합니다.
- 날짜별로 여행 일정을 세울 수 있습니다.
- 마이 페이지를 제공하여 정보 수정 및 친구 관리를 할 수 있습니다.
- 실시간으로 유저 간 채팅이 가능합니다.(친구인 유저와 채팅이 가능합니다.)
    - 웹 소켓을 활용했습니다.
<br><br>

## 페이지

### 1. 메인 페이지
<img src="https://github.com/user-attachments/assets/acfb6899-5063-42b6-a12e-5aa01a996789" width="400px">
<br>

- '좋아요'가 많은 순서대로 여행지 리뷰 목록을 출력합니다.
- 각 리뷰 마다 좋아요, 댓글, 조회 수 정보를 표시합니다.
- 내 여행 일정을 등록한 순서로 출력합니다.
<br><br>

### 2. 로그인/회원가입 페이지
<img src="https://github.com/user-attachments/assets/e75d4f1f-79c8-423e-aaec-c1b0a070f404" width="300px">
<img src="https://github.com/user-attachments/assets/6be3deec-b1f6-4642-95c2-8ebb638efbc3" width="300px">

- 소셜 로그인/회원가입 기능을 제공합니다.(Kakao, Naver)
- 일반 로그인/회원가입 기능을 제공합니다.
  <br>
  (Spring Security, JWT, OAuth를 활용했습니다.)

<br><br>

### 3. 리뷰 관련 페이지
<img src="https://github.com/user-attachments/assets/9d1a6971-87b8-4e4c-bdfe-f2db36c00062" width="250px">
<img src="https://github.com/user-attachments/assets/a827a07a-47eb-4881-ac5e-f3ed2c6d8026" width="250px">
<img src="https://github.com/user-attachments/assets/045c0f37-dd61-4078-ad93-14a2aa52f75e" width="250px">

- 여행지 리뷰 목록을 페이징을 사용해 조회 가능하도록 했습니다.
- 여행지 리뷰 작성 및 수정이 가능합니다.
- 여행지 리뷰 상세 페이지에서 댓글 작성 및 좋아요 기능을 구현했습니다.

<br><br>

### 4. 여행지 조회 및 여행 일정 작성 페이지
<img src="https://github.com/user-attachments/assets/b4532efe-ed90-47b3-b4d9-9b604fc8d3bf" width="400px">
<img src="https://github.com/user-attachments/assets/87714dea-65d3-4801-bcbf-fe61d7d95bd7" width="400px">
<img src="https://github.com/user-attachments/assets/866d1e95-1bc5-4ec8-9052-2fdd55ef692a" width="400px">
<img src="https://github.com/user-attachments/assets/0e5c9739-192e-4e71-b372-0390daa89458" width="400px">

- 입력한 지역/분류/검색어에 따라 여행지 목록을 조회합니다.
- 일자별 여행 일정 수립 및 여행지별 상세 일정 작성/수정/조회/삭제가 가능합니다.
- 내가 작성한 여행 일정들을 한 번에 조회할 수 있습니다.
<br><br>

### 5. 마이페이지
<img src="https://github.com/user-attachments/assets/0f9da2d9-92c0-4a15-8326-391943e7e95d" width="400px">
<img src="https://github.com/user-attachments/assets/3f2f799d-af84-45b4-ac10-c35d0b2123f5" width="400px">

- 내 정보 조회 및 수정을 할 수 있습니다.
- 내 친구를 조회/등록/삭제할 수 있습니다.
<br><br>

### 6. 채팅
<img src="https://github.com/user-attachments/assets/9121e0ef-f912-40b5-a8f0-152e44968a60" width="400px">
<img src="https://github.com/user-attachments/assets/70ae68a0-8337-4423-a7dc-d834c4f4dfc9" width="400px">
<img src="https://github.com/user-attachments/assets/d434d972-4fb8-49f5-90c0-e114a9a43340" width="400px">
<img src="https://github.com/user-attachments/assets/23f1e601-3f7f-44b4-9376-6cf8fc80abbd" width="400px">

- 화면의 '-' 토글 버튼 클릭을 통해 채팅창을 활성화 혹은 비활성화 할 수 있습니다.
- 친구인 유저와 실시간 채팅이 가능합니다.
- 아직 읽지 않은 메시지 개수가 상대 프로필 사진 옆에 표시됩니다.
- '친구' 탭은 내가 친구 추가한 유저의 목록을 보여주고, '채팅' 탭은 내 친구가 아니더라도 메시지를 주고받은 유저의 목록을 보여줍니다.
<br><br>

## 팀 구성
<table>
  <tr>
    <td align="center"><a href="https://github.com/Chaeros"><img src="https://github.com/SSAFY11thDaejeon7/cs_study/assets/91451735/6a691fe9-0a25-43f2-a650-a83b4f30aff8" width="120px;" alt=""/><br /><sub><b>박찬호</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/leeggon"><img src="https://avatars.githubusercontent.com/u/138864974?v=4" width="120px;" alt=""/><br /><sub><b>이경곤</b></sub></a><br /></td></td>
</table>
