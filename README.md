# ChanGon ( 찬호 & 경곤 )
여행지 조회, 여행 일정 수립, 유저 간 채팅 기능을 제공하는 여행 커뮤니티 서비스
<br><br>

## 개발환경
- **Back-end**: Spring Boot 3.2.5
- **Front-end**: Vue.js
- **Database**: MySQL 8.0.33
<br><br>

## 주요 기능
- 소셜 및 일반 로그인
    - JWT, Spring Security, Oauth2 활용
- 여행지 리뷰 커뮤니티
- 여행지 일정 세우기
- 마이 페이지(정보 수정 및 친구 관리)
- 유저 간 채팅
    - 웹 소켓 활용 
<br><br>

## 페이지

### 1. 메인 페이지
<img src="https://github.com/user-attachments/assets/acfb6899-5063-42b6-a12e-5aa01a996789" width="600px">
<br>

- '좋아요'가 많은 순서대로 여행지 리뷰 출력
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
<img src="https://github.com/user-attachments/assets/b4532efe-ed90-47b3-b4d9-9b604fc8d3bf" width="400px">
<img src="https://github.com/user-attachments/assets/87714dea-65d3-4801-bcbf-fe61d7d95bd7" width="400px">
<img src="https://github.com/user-attachments/assets/866d1e95-1bc5-4ec8-9052-2fdd55ef692a" width="400px">
<img src="https://github.com/user-attachments/assets/0e5c9739-192e-4e71-b372-0390daa89458" width="400px">

- 지역/분류/검색어 분류를 통한 여행지 조회
- 일자별 여행 일정 작성 및 상세 일정 작성/수정/조회/삭제 기능
- 내가 작성한 여행 일정 목록 조회
<br><br>

### 5. 마이페이지
<img src="https://github.com/user-attachments/assets/0f9da2d9-92c0-4a15-8326-391943e7e95d" width="400px">
<img src="https://github.com/user-attachments/assets/3f2f799d-af84-45b4-ac10-c35d0b2123f5" width="400px">

- 내 정보 조회/수정
- 친구 조회/등록/삭제
<br><br>

### 6. 채팅
<img src="https://github.com/user-attachments/assets/9121e0ef-f912-40b5-a8f0-152e44968a60" width="400px">
<img src="https://github.com/user-attachments/assets/70ae68a0-8337-4423-a7dc-d834c4f4dfc9" width="400px">
<img src="https://github.com/user-attachments/assets/d434d972-4fb8-49f5-90c0-e114a9a43340" width="400px">
<img src="https://github.com/user-attachments/assets/23f1e601-3f7f-44b4-9376-6cf8fc80abbd" width="400px">

- '-' 토글 버튼 클릭을 통한 채팅창 활성/비활성 기능
- 친구인 유저와 실시간 채팅 기능 구현
- 아직 읽지 않은 메시지 개수 출력 기능
- '친구' 탭은 내가 친구 추가한 유저를 표시해주고, '채팅' 탭은 나와 친구가 아니더라도 주고받은 메시지 목록을 볼 수 있음
<br><br>

## 팀 구성
<table>
  <tr>
    <td align="center"><a href="https://github.com/Chaeros"><img src="https://github.com/SSAFY11thDaejeon7/cs_study/assets/91451735/6a691fe9-0a25-43f2-a650-a83b4f30aff8" width="120px;" alt=""/><br /><sub><b>박찬호</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/leeggon"><img src="https://avatars.githubusercontent.com/u/138864974?v=4" width="120px;" alt=""/><br /><sub><b>이경곤</b></sub></a><br /></td></td>
</table>
