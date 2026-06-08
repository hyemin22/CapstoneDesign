# 기억담아 Android

가족 구성원이 추억, 일정, 앨범, 게시글을 함께 기록하고 공유할 수 있는 가족 커뮤니티 앱 **기억담아**의 Android 클라이언트입니다.

## 프로젝트 개요

기억담아는 가족 구성원이 하나의 공간에서 가족 기록을 공유할 수 있도록 돕는 커뮤니티 서비스입니다.
사용자는 가족방을 기반으로 게시글, 앨범, 일정 등을 확인하고 관리할 수 있으며, 가족 구성원 간의 추억과 일정을 함께 기록할 수 있습니다.

본 저장소는 Android 클라이언트 코드이며, Spring Boot 기반 백엔드 서버와 REST API 방식으로 통신합니다.

## 주요 기능

* 사용자 로그인
* 가족방 기반 커뮤니티 기능
* 게시글 작성 및 조회
* 앨범 및 이미지 조회
* 일정 확인 및 관리
* 지도 기반 기능 연동
* 서버 API 연동
* 이미지 로딩 및 화면 표시

## 기술 스택

* Java
* Android
* Retrofit
* Gson Converter
* Glide
* Volley
* Kakao SDK
* Naver Map SDK
* Google Location API

## 관련 저장소

* Android Repository: https://github.com/hyemin22/CapstoneDesign
* Backend Repository: https://github.com/hyemin22/CapstoneDesignSpringServer

## 프로젝트 구조

```text
CapstoneDesign/
├── app/
│   ├── src/
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
```

## 실행 방법

1. 저장소를 클론합니다.

```bash
git clone https://github.com/hyemin22/CapstoneDesign.git
cd CapstoneDesign
```

2. Android Studio에서 프로젝트를 엽니다.

3. 필요한 API Key 및 서버 주소를 설정합니다.

```properties
KAKAO_NATIVE_APP_KEY=your_kakao_key
NAVER_MAP_CLIENT_ID=your_naver_client_id
SERVER_BASE_URL=your_server_url
```

4. 앱을 실행합니다.
