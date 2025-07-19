<div align="center">
  
![danmuji_logo](https://github.com/user-attachments/assets/145ae7c2-fe19-42a2-8dbd-2e93c04d4371)

### 🌙 단무지 - 단계 별 무리없는 지원 시스템

[<img src="https://img.shields.io/badge/-readme.md-important?style=flat&logo=google-chrome&logoColor=white" />]()  [<img src="https://img.shields.io/badge/release-v1.0.0-yellow?style=flat&logo=google-chrome&logoColor=white" />]()
<br/> [<img src="https://img.shields.io/badge/프로젝트 기간-2025.05.14~2025.07.18-green?style=flat&logo=&logoColor=white" />]()

**단무지**는 웹 개발사와 고객사 간의 프로젝트 관리 전반에서 발생하는<br>비효율적인 커뮤니케이션과 데이터 관리 문제를 해결하기 위해 설계된 시스템입니다.

프로젝트의 **계약 → 진행 → 납품 → 하자보수**까지 이어지는 모든 과정을 단계적으로 체계화하여<br>정보 누락, 불명확한 기록, 진행 혼란을 방지하고 양측이 **프로젝트 상황을 직관적으로 파악**할 수 있도록 지원합니다.

</div>


## ⚙️ 기능 소개
### 대시보드
> 관리자와 고객사, 개발사는 각각 전용 대시보드에서 시스템 사용 현황을 한 눈에 파악할 수 있습니다. 

![관리자대시보드움짤](https://github.com/user-attachments/assets/7f234152-372d-4731-96e9-849db8cec88f)
![유저대시보드](https://github.com/user-attachments/assets/3ec696c8-3601-49c9-9931-4db3cc0a71d1)

### 프로젝트 & 단계

> 관리자는 프로젝트에 참여할 사용자와 업체를 직접 등록할 수 있습니다.

![회원등록](https://github.com/user-attachments/assets/a558df92-1e7e-4f95-b524-f01d2de608b0)
![회사등록움짤](https://github.com/user-attachments/assets/f8aa8d50-3a43-4067-ad98-a346f7c3e482)
![회사보기움짤](https://github.com/user-attachments/assets/4ad6f184-869c-4cf7-b50c-648e67ed9981)

> 관리자는 프로젝트를 생성할 때, 참여자와 업체들을 지정해줍니다.
> 담당자와 멤버는 언제든 변경이 가능합니다.

![플젝생성](https://github.com/user-attachments/assets/3f2cb4fd-a652-4c73-8d86-b8477645460a)
![플젝 담당자 수정](https://github.com/user-attachments/assets/628a4be2-8fda-4c57-af60-a14cd2c3fdbd)

> 프로젝트 참여자들은 프로젝트 목록에서 자신이 속한 프로젝트를 쉽게 찾을 수 있습니다.

![플젝목록보기](https://github.com/user-attachments/assets/4ed45218-5781-473e-bd49-1bb62643f44d)

> 각 프로젝트마다 고유하게 구성된 단계별 탭에서 업무를 구분하여 진행 및 관리할 수 있습니다.

![단계별필터링](https://github.com/user-attachments/assets/c23c07a7-4a0a-484d-bf55-ffa24719a0e1)

> 프로젝트 담당자 및 관리자는 기업과 프로젝트의 성격에 맞게 단계를 커스텀할 수 있습니다.

![단계상태바꾸기](https://github.com/user-attachments/assets/18db3c16-5c57-49c0-9c34-0e37e8f20382)
![단계순서바꾸기](https://github.com/user-attachments/assets/f68c2b4f-6a1f-4c86-9155-2dba6d0cf087)


### 게시글 & 체크리스트

> 게시판을 통해 파일 공유, 질문 답변, 업무 소통이 가능합니다.

![게시글작성](https://github.com/user-attachments/assets/db65969c-4baa-41db-953d-c8fcb332fc41)

> 프로젝트 내 참여하고 있는 다른 사용자를 멘션하여 빠르고 쉬운 의사 전달이 가능합니다.

![멘션기;능](https://github.com/user-attachments/assets/6a040811-ce0b-4b07-b2a5-81739500d55f)


> 담당자의 승인 절차 하에 단계별 필요한 업무 현황을 관리할 수 있습니다. 

</br>

</br>

</br>



## 📎배포 링크
https://www.danmuji.site <br>
### 🧪 테스트 계정
| 역할  | ID                   | PW           |
| --- | -------------------- | ------------ |
| 관리자 | `admin`  | `adminDanmuji11!!`  |
| 개발사 | `devuser` | `devDanmuji11!!` |
| 고객사 | `clientuser` | `clientDanmuji11!!` |

## ✨ 주요 기능
📊 프로젝트 대시보드 & 상태 관리  
✅ 게시글/체크리스트 기반 단계별 진행  
💬 요청/답변 커뮤니티 및 커뮤니케이션 로그  
🔔 실시간 알림 (SSE) 및 멘션 기능  
🗂️ 이력 관리, 사용자 권한 제어, 파일 첨부

## ⚙️ 사용 기술 스택

**⚙ Backend**
<p> 
  <img alt="Spring Boot" src="https://img.shields.io/badge/SpringBoot-6DB33F.svg?&style=for-the-badge&logo=spring-boot&logoColor=white"/>
  <img alt="Spring Security" src="https://img.shields.io/badge/Security-00758F.svg?&style=for-the-badge&logo=springsecurity&logoColor=white"/> 
  <img alt="JWT" src="https://img.shields.io/badge/JWT-000000.svg?&style=for-the-badge&logo=jsonwebtokens&logoColor=white"/> 
  <img alt="JPA" src="https://img.shields.io/badge/JPA-59666C.svg?&style=for-the-badge&logo=hibernate&logoColor=white"/>
  <img alt="SSE" src="https://img.shields.io/badge/SSE-444444.svg?&style=for-the-badge&logo=livejournal&logoColor=white"/>
  <img alt="Google SMTP" src="https://img.shields.io/badge/Gmail SMTP-EA4335.svg?&style=for-the-badge&logo=gmail&logoColor=white"/> 
</p>

**🖥 Frontend**
<p> 
  <img alt="React" src="https://img.shields.io/badge/React-61DAFB.svg?&style=for-the-badge&logo=React&logoColor=black"/> 
  <img alt="TypeScript" src="https://img.shields.io/badge/TypeScript-3178C6.svg?&style=for-the-badge&logo=TypeScript&logoColor=white"/>
  <img alt="TailwindCSS" src="https://img.shields.io/badge/TailwindCSS-06B6D4.svg?&style=for-the-badge&logo=TailwindCSS&logoColor=white"/> 
</p>

**🛢️ Database**
<p>
  <img alt="Redis" src="https://img.shields.io/badge/Redis-DC382D.svg?&style=for-the-badge&logo=Redis&logoColor=white"/> 
  <img alt="MySQL" src="https://img.shields.io/badge/MySQL-4479A1.svg?&style=for-the-badge&logo=MySQL&logoColor=white"/>
  <img alt="MongoDB" src="https://img.shields.io/badge/MongoDB-47A248.svg?&style=for-the-badge&logo=MongoDB&logoColor=white"/> 
</p>

**☁ Infra**
<p> 
  <img alt="AWS EC2" src="https://img.shields.io/badge/AWS EC2-FF9900.svg?&style=for-the-badge&logo=Amazon-AWS&logoColor=white"/>
  <img alt="Nginx" src="https://img.shields.io/badge/Nginx-009639.svg?&style=for-the-badge&logo=Nginx&logoColor=white"/> 
  <img alt="GitHub Actions" src="https://img.shields.io/badge/GitHub Actions-2088FF.svg?&style=for-the-badge&logo=GitHub-Actions&logoColor=white"/> 
</p>

## 🛠️ 프로젝트 아키텍처
<img width="1116" height="630" alt="image" src="https://github.com/user-attachments/assets/70283467-29ae-47ff-8ac5-1e0c0819b673" />

## 💻 실행 방법

### 🛠️ 사전 준비
- Java 17
- Node.js 18 
- MySQL, MongoDB DB 실행 (application.yml 참고)
- Redis 실행

### ⚙️ 환경 변수 설정

실행 전, `.env` 파일을 설정해야 합니다.  
`.env` 파일은 **프로젝트에 포함된 `.env.example`** 파일을 참고해서 작성해 주세요.
> 백엔드: [`.env.example`](./.env.example)
> 프론트엔드: [`.env.example`](https://github.com/Kernel360/KBE5_DANMUJI_FE/blob/develop/.env.example)
---
1️⃣ 레포지토리 클론

```
# 백엔드
git clone https://github.com/kernel360/KBE5_DANMUJI_BE.git
# 프론트엔드
git clone https://github.com/kernel360/KBE5_DANMUJI_FE.git
```

2️⃣ 백엔드 실행 (Spring Boot)
```
./gradlew build
java -jar build/libs/danmuji-0.0.1-SNAPSHOT.jar
```

3️⃣ 프론트엔드 실행 (React)
```
npm install
npm run dev
```
4️⃣ 로컬에서 단무지 접속 👏

---

## 👨‍👩‍👧‍👦 팀원 소개
| 프로필 | 역할 | GitHub | 주요 담당 |
|--------|-------------|--------|------------|
| <div align="center"><img src="https://avatars.githubusercontent.com/u/102000749?v=4" width="100"/><br><strong>곽유진</strong></div> | 💻 팀장/백엔드 개발자 | [@ooyniz](https://github.com/ooyniz) | - 백엔드 & 프론트엔드 배포 환경 구성 (Nginx, Spring Boot, React)<br>- 서버 배포 및 운영 (EC2)<br>- 로그인 인증 로직 구현 (Spring Security + JWT + Redis)<br>- 액세스 토큰 / 리프레시 토큰 발급 및 갱신 처리<br>- SSE 기반 실시간 알림 기능 구현 (Redis 저장소 연동)<br>- 비밀번호 변경 시 Google SMTP 기반 이메일 인증 기능<br>- 사용자, 할 일 리스트(TodoList) CRUD API<br>- 프로젝트 단계 별 체크리스트 CRUD API 및 승인/거절 처리<br>- Spring Schedule 기반 배치 작업 구현(삭제 및 갱신) |
| <div align="center"><img src="https://avatars.githubusercontent.com/u/127851510?v=4" width="100"/><br><strong>김정인</strong></div> | 💻 백엔드 개발자 | [@HakPyun](https://github.com/HakPyun) |  - 업체 / 문의사항 / 답변 도메인별 CRUD 및 필터 검색 기능 구현<br> - 문의사항 및 답변에 대한 JWT 'Role', 'id' 기반 권한 분리<br> - 담당 도메인별 커스텀 예외 처리 및 에러 응답 구조 설계<br> - 관리자 대시보드용 통계 / 조회 API 작성 |
| <div align="center"><img src="https://avatars.githubusercontent.com/u/174098989?v=4" width="100"/><br><strong>박진효</strong></div> | 💻 백엔드 개발자 | [@JinHy00](https://github.com/JinHy00) | - 프로젝트 CRUD API 구현<br>- 프로젝트별 단계 CRUD API 구현<br>- 프로젝트 멤버 할당 및 제외 로직 작성<br>- 조건별 필터링을 통한 프로젝트 검색 기능 구현 |
| <div align="center"><img src="https://avatars.githubusercontent.com/u/89715722?v=4" width="100"/><br><strong>이승우</strong></div> | 💻 백엔드 개발자 | [@2eungwoo](https://github.com/2eungwoo) | - 단계별 게시판 CRUD 기능 구현<br>- 필터 기반 검색 기능 구현<br>- Cloudflare R2 기반 파일 업로드/다운로드 기능 구현<br>- SSE 기반 실시간 '@멘션' 기능 구현<br>- MongoDB 기반 NoSQL 이력 관리 기능 구현<br>- 멀티모듈 및 헥사고날 아키텍처 환경 구축<br>- 운영 환경별 Logback 기반 로깅 시스템 구성<br>- EC2 기반 클라우드 배포 자동화 스크립트 작성 |



