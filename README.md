# FLO
- [프로그래머스 과제관 - FLO AOS](https://school.programmers.co.kr/skill_check_assignments/3) - FLO AOS 앱의 요구사항을 바탕으로 개발했습니다.
- email address : snrneh3@naver.com 
- blog : https://se0r1-tae27.tistory.com

## 🎬 Demo
<p align="center" dir="auto">
  <img src="https://user-images.githubusercontent.com/32188154/183321555-f6ef31bb-844d-4c59-bf49-31660a629a47.gif" width="270px" height="585px" title="Demo" alt="Demo"></img>
</p>

## 👀 화면 구성 요소
- 스플래시 스크린
- 음악 재생 화면
    - 재생 중인 음악 정보(제목, 가수, 앨범 커버 이미지, 앨범명)
    - 현재 재생 중인 부분의 가사 하이라이팅
    - Seekbar
    - Play/Stop 버튼
- 전체 가사 보기 화면
    - 특정 가사로 이동할 수 있는 토글 버튼
    - 전체 가사 화면 닫기 버튼
    - Seekbar
    - Play/Stop 버튼

## 🛠 사용 기술 및 주요 라이브러리

- Kotlin, Android
- Glide
- Retrofit2
- Hilt
- Data Binding
- MVVM Architecture
- Clean Architecture

## 👣 Dev History
### [음악 재생 화면]
- MVVM 구조로 구현 & DataBinding 을 이용해 UI 업데이트하기
- Retrofit2 - API 통신 (음악 정보를 받아오기)
- 재생 버튼을 누르면 음악이 재생되고, 일시정지 버튼을 누르면 음악이 일시정지
- 사용자가 seekbar 를 조작하면 재생 시작 시점을 이동
- 재생 시 현재 재생되고 있는 구간대의 가사와 바로 다음 가사가 실시간으로 표시
- 음악 재생 화면에서 현재 재생되고 있는 가사를 터치하면 가사 전체 화면으로 이동

### [전체 가사 보기 화면]
- 사용자가 seekbar 를 조작하면 재생 시작 시점을 이동
- 재생 시 현재 재생되고 있는 구간대의 가사에 하이라이팅
- 토글 버튼 on
  - 특정 가사 터치 시 해당 구간부터 재생
- 토글 버튼 off
  - 현재 재생되고 있는 구간대의 가사가 중앙에 오도록 자동 스크롤
  - 특정 가사 터치 시 전체 가사 화면 닫기
- 전체 가사 화면 닫기 버튼을 누르면, 음악 재생 화면으로 이동

## 📃 그 외
<details>
<summary>해결하지 못한 이슈</summary>
<ul dir="auto">
<li>ViewStub 을 사용해서 구현했을 때, ViewStub 의 visibility 를 GONE 시켜도 사라지지 않는 현상</li>
<li>전체 가사 보기 화면에서 현재 재생되고 있는 구간대의 가사가 중앙에 오도록 자동 스크롤을 할 때 화면이 깜빡거리는 현상</li>
</ul>
</details>
