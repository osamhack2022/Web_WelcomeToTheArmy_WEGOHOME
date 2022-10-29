# WELCOME TO ARMY Front End
## 기능 명세 - 사용자(훈련병) 페이지
- 로그인
- 대대별 일정 (주간, 일간)
- 설문조사 기능
- 개인정보수집동의서                              <-- 개발 1순위
- 소대장 / 상담 장교와의 대화 (비공개게시판)       <-- 개발 2순위
- 훈련 사진 보기
- 가감점
- 모바일 교육
- 공지 게시판                                    <-- 개발 3순위

## 기능 명세 - 훈육관 페이지
- 관리자 로그인
- 관리자 페이지 nav 바
- 병사 목록 및 검색
- 병사 생성 (from 명단 csv 파일)
- 병사 관리 (특이사항)
    - 가감점 관리
- 훈련 일정 관리
- 설문 작성 / 결과보기                            <-- 개발 1순위
- 상담 게시판 / 상담 기록                         <-- 개발 2순위
- 훈련 사진 업로드
- 공지 게시판
- 간부, 훈육요원용 게시판                         <-- 개발 3순위

## API 명세 - 훈육관 페이지
- 훈육관 로그인 시도 `POST /instructor/login`
- 병사 관리
    - 병사 목록 `GET /trainee`
    - 병사 추가 `POST /trainee`
    - 병사 추가(엑셀파일) `POST /trainee`
    - 병사 정보 수정 `PUT /trainee`
    - 병사 삭제 `DELETE /trainee`
- 훈련 일정
    - 일정 목록 `GET /schedule?year&month&range`
    - 일정 추가 `POST /schedule`
    - 일정 보기 `GET /schedule?id`
    - 일정 수정 `PUT /schedule`
    - 일정 삭제 `DELETE /schedule`
- 조사전달
    - 조사전달 목록 `GET /survey`
    - 조사전달 보기 `GET /survey?id`
    - 조사전달 추가 `POST /survey`
    - 조사전달 삭제 `DELETE /survey`
    - 조사전달 결과 조회 `GET /survey/result?id`
- 훈육관 관리
    - 훈육관 목록 `GET /instructor`
    - 훈육관 추가 `POST /instructor`
    - 훈육관 수정 `PUT /instructor`
    - 훈육관 삭제 `DELETE /instructor`

## API 명세 - 훈련병 페이지
- 훈련병 로그인 시도 `POST /trainee/login`
- 수료일 조회 `논의필요`
- 훈련 일정
    - 일정 목록 `GET /schedule?year&month&range` (훈육관과 동일)
    - 일간 일정 조회 `GET /sechedule?year&month&day` (특정 날짜의 일정만 조회)
- 조사전달
    - 조사전달 목록 `GET /survey?platoon_num` (소대번호로 해당하는 조사만 불러오기)
    - 조사전달 보기 `GET /survey?id` (특정 조사전달만 보기)
    - 조사전달 응답 `POST /answer`


## 요구사항
- 이 웹페이지의 일부 내용을 표시하는데 [공군서체](https://afplay.kr/2449)가 필요합니다.
- 서체가 없어도 기능에는 문제가 없지만, 설치하여 보는 것을 권장합니다.
- 서체를 다운로드 받아, `.ttf` 파일을 모두 `src/assets/fonts`에 넣어주시면 됩니다.