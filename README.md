# <div align=center>WELCOME TO ARMY</div>
> 스마트폰과 함께하는 스마트한 기본군사훈련

군인이라면 누구나 거치는 곳, 훈련소.    
군의 장병 스마트폰 허용 확대 정책에 따라 이제 훈련병들도 스마트폰을 사용할 수 있게 되었다는 사실 알고 계셨나요?

고되고 힘든 훈련소 생활을 스마트하게 개선해주는 WELCOME TO ARMY와 함께라면 훈련소 생활이 한층 더 스마트해집니다.

## 💻프로젝트 데모
<p align="center">
<a href="https://youtu.be/WVfTsbIJDVE" target="_blank"><img src="docs/buttons/시연영상버튼.png"></a>
<a href="https://vue.giopaik.me/" target="_blank"><img src="docs/buttons/데모페이지버튼.png"></a>
</p>

<p align="center">
	<span>데모 관리자 ID : <code>admin</code></span><br/>
	<span>데모 관리자 비밀번호 : <code>1q2w1q2w!</code></span>
</p>
<p align="center">
	<span>데모 로그인 ID : <code>11101</code></span><br/>
	<span>데모 로그인 비밀번호 : <code>000101</code></span>
</p><br/>


## 📌프로젝트 소개
매월 최소 1천여명에서 만여명의 훈련병이 민간인에서 군인으로 거듭나기 위해 훈련소를 거쳐 갑니다.    
군대라는 조직을 처음 경험한 훈련병들에게 훈련소에서의 시간은 바깥세상과는 완전히 다른, 그야말로 혼란과 적응의 시간입니다.

한편, 이런 훈련병들을 올바르게 군인의 길로 이끌어줘야 하는 훈육관들에게도 훈련 기간은 도전의 시간입니다.

훈육관 한 명당 수백명에 달하는 각기 다른 배경의 성인들을 관리하고 훈육하는 것은 쉬운 일이 아닙니다.

WELCOME TO ARMY는 **훈련병에게는 쉽고 스마트한 훈련과정을, 훈육관에게는 효율적이고 편리한 훈련과정을 만들어주는 서비스**입니다.

## 💡주요 기능
### 👦훈련병
- 캘린더로 보는 훈련 일정
- 소대장 / 상담장교와의 대화 (상담/건의 게시판)
- 내 훈련 사진보기
- 내 훈련 결과 보기 (가감점 / 점수 보기)
- 모바일 교육 영상
- 훈련 공지사항 보기

### 😎훈육관 (조교, 상담장교 등)
- 병사 관리 전산화
- 설문 작성, 취합 전산화 및 자동화
- 훈련 일정 관리
- 상담 기록 전산화

## 미리보기
<table align="center">
	<tr>
		<td>
			<img style="width:450px;" src="/docs/screenshots/instructorTraineePage.png">
		</td>
		<td>
			<img style="width:450px;" src="/docs/screenshots/instructorSurveyPage.png">
		</td>
	</tr>
	<tr>
		<td align="center">
			<b>훈육관 훈련병 관리 페이지</b>
		</td>
		<td align="center">
			<b>조사전달 관리 페이지</b>
		</td>
	</tr>
	<tr>
		<td>
			<img style="width:450px;" src="/docs/screenshots/traineeMainPage.png">
		</td>
		<td>
			<img style="width:450px;" src="/docs/screenshots/traineeGalleryPage.png">
		</td>
	</tr>
	<tr>
		<td align="center">
			<b>훈련병 메인 페이지</b>
		</td>
		<td align="center">
			<b>훈련사진 페이지</b>
		</td>
	</tr>
</table>

## 기술 스택 (Technique Used) 
### Server(back-end)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostgreSQL](https://www.postgresql.org/)
- [Swagger](https://swagger.io/)

### Front-end
- [Vuejs3](https://vuejs.org/)
- [Bootstrap](https://getbootstrap.com/)
- [v-calendar](https://vcalendar.io/)

## 컴퓨터 구성 / 필수 조건 안내 (Prerequisites)
- ECMAScript 6 지원 브라우저 사용
- 권장: Google Chrome 버젼 77 이상

## 설치 안내 (Installation Process)
### Front-end
```bash
$ git clone https://github.com/osamhack2022/Web_WelcomeToTheArmy_WEGOHOME
$ cd frontend
$ npm install
$ npm run dev
```
### Back-end
```bash
$ git clone https://github.com/osamhack2022/Web_WelcomeToTheArmy_WEGOHOME
$ cd backend
$ gradle bootRun
```

## 프로젝트 사용법 (Getting Started)
- 처음 프로젝트를 실행하면 다음과 같이 관리자 계정이 발급됩니다.
	- ID: `admin`
	- PW: `1q2w1q2w!`
- 서버를 실행하고 `/` 경로로 접근하여, 훈육관 페이지나 훈련병 페이지에 접속할 수 있습니다.
- 우선 관리자 페이지에 접속하여 훈련병 계정을 만들어야 훈련병 페이지에 로그인이 가능합니다.
- 훈련병 계정은 다음과 같이 생성됩니다.
	- ID: `(소대번호)`
	- PW: `(생년월일 yymmdd)`
- 소대번호는 5자리 숫자이며, 다음과 같은 의미를 가집니다.
	- `BCPNN`이면 `B`대대 `C`중대 `P`소대 `NN`번 훈련병
	- 예를들어, `43124`는 4대대 3중대 1소대 24번 훈련병 입니다.

## 😎 팀 정보 (TEAM INFORMATION)
<table align="center">
<tr>
	<th>Name</th>
	<th>Role</th>
	<th>Contact</th>
	<th>GITHUB</th>
</tr>
<tr>
	<td>백지오</td>
	<td>Project Manager, Front-end / UX Developer</td>
	<td>giopaik@naver.com</td>
	<td><a href="https://github.com/skyil7">skyil7</a></td>
</tr>
<tr>
	<td>이동규</td>
	<td>Back-end Development</td>
	<td>lsdong1003@naver.com</td>
	<td><a href="https://github.com/leedongkyu2019">leedongkyu2019</a></td>
</tr>
</table>


## 저작권 및 사용권 정보 (Copyleft / End User License)
This project is licensed under the terms of the MIT license.
