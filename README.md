<div align="center">
  
![danmuji_logo](https://github.com/user-attachments/assets/145ae7c2-fe19-42a2-8dbd-2e93c04d4371)

### 🌙 단무지 - 단계 별 무리없는 지원 시스템

[<img src="https://img.shields.io/badge/-readme.md-important?style=flat&logo=google-chrome&logoColor=white" />]()  [<img src="https://img.shields.io/badge/release-v1.0.0-yellow?style=flat&logo=google-chrome&logoColor=white" />]()
<br/> [<img src="https://img.shields.io/badge/프로젝트 기간-2025.05.14~2025.07.18-green?style=flat&logo=&logoColor=white" />]()

**단무지**는 웹 개발사와 고객사 간의 프로젝트 관리 전반에서 발생하는<br>비효율적인 커뮤니케이션과 데이터 관리 문제를 해결하기 위해 설계된 시스템입니다.

프로젝트의 **계약 → 진행 → 납품 → 하자보수**까지 이어지는 모든 과정을 단계적으로 체계화하여<br>정보 누락, 불명확한 기록, 진행 혼란을 방지하고 양측이 **프로젝트 상황을 직관적으로 파악**할 수 있도록 지원합니다.

</div>


## 🎥 실행 영상
> Todo 😺

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
✅ 체크리스트 기반 단계별 승인 흐름  
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
> Todo 😺

## 🗂️ 기술 문서
> Todo 😺

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
| <div align="center"><img src="https://avatars.githubusercontent.com/u/102000749?v=4" width="100"/><br><strong>곽유진</strong></div> | 💻 팀장/백엔드 개발자 | [@ooyniz](https://github.com/ooyniz) | - 백엔드 & 프론트엔드 배포 환경 구성 (Nginx, Spring Boot, React)<br>- 서버 배포 및 운영 (EC2)<br>- 로그인 인증 로직 구현 (Spring Security + JWT + Redis)<br>- 액세스 토큰 / 리프레시 토큰 발급 및 갱신 처리<br>- SSE 기반 실시간 알림 기능 구현 (Redis 저장소 연동)<br>- 비밀번호 변경 시 Google SMTP 기반 이메일 인증 기능<br>- 사용자, 할 일 리스트(TodoList) CRUD API<br>- 프로젝트 단계 별 체크리스트 CRUD API 및 승인/거절 처리 |
| <div align="center"><img src="https://avatars.githubusercontent.com/u/127851510?v=4" width="100"/><br><strong>김정인</strong></div> | 💻 백엔드 개발자 | [@HakPyun](https://github.com/HakPyun) |  - 업체 / 문의사항 / 답변 도메인별 CRUD 및 필터 검색 기능 구현<br> 문의사항 및 답변에 대한 JWT 'Role', 'id' 기반 권한 분리<br> - 담당 도메인별 커스텀 예외 처리 및 에러 응답 구조 설계<br> - 관리자 대시보드용 통계 / 조회 API 작성 |
| <div align="center"><img src="https://avatars.githubusercontent.com/u/174098989?v=4" width="100"/><br><strong>박진효</strong></div> | 💻 백엔드 개발자 | [@JinHy00](https://github.com/JinHy00) | - A<br> - B<br> |
| <div align="center"><img src="https://avatars.githubusercontent.com/u/89715722?v=4" width="100"/><br><strong>이승우</strong></div> | 💻 백엔드 개발자 | [@2eungwoo](https://github.com/2eungwoo) | - 단계별 게시판 CRUD 기능 구현<br>- 필터 기반 검색 기능 구현<br>- Cloudflare R2 기반 파일 업로드/다운로드 기능 구현<br>- SSE 기반 실시간 '@멘션' 기능 구현<br>- MongoDB 기반 NoSQL 이력 관리 기능 구현<br>- 멀티모듈 및 헥사고날 아키텍처 환경 구축<br>- 운영 환경별 Logback 기반 로깅 시스템 구성<br>- EC2 기반 클라우드 배포 자동화 스크립트 작성 |



