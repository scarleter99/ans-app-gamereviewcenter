# 팀프로젝트 8조 - Readme 파일
- 이름: 김태범(20181602), 강동우(), 김진명(), 문민철()

- MainActivity 구현 내용:
	- a
	
- SecondActivity 구현 내용:
	- 로그인 성공 시 첫 출력 화면을 게시판 페이지(BoardFragment)로 설정 
	- bottomNavigationBar 기능 구현 (게시판, 글 작성, 마이 페이지 각각의 아이콘 클릭시 화면 전환)
	
- SignUpActivity 구현 내용:
	- 아이디 형식을 5~20자, 숫자와 소문자 조합으로 설정
	- 아이디 중복 확인 버튼을 통해 아이디의 중복과 형식 확인
	- 아이디가 중복이거나 형식에 맞지 않거나 알림메시지 출력과 회원가입 불가
	- 비밀번호 형식을 숫자,문자,특수문자 최소 한 개 포함 8~15자 이내로 설정
	- 비밀번호 형식에 맞지 않은 경우 알림 메시지 출력, 회원가입 불가
	- 비밀번호와 비밀번호 재입력 값이 같지 않은 경우 알림 메시지 출력, 회원가입 불가
	- 닉네임 형식을 2~8자, 한글,영어,숫자 조합으로 설정
	- 닉네임 중복 확인 버튼을 통해 닉네임 중복과 형식 확인
	- 닉네임이 중복이거나 형식에 맞지 않으면 알림 메시지 출력, 회원가입 불가
	- 이메일 형식은  @ 과 . 이 포함되어야함
	- 이메일 형식에 맞지 않은 경우 알림 메시지 출력, 회원가입 불가
	- 회원가입 버튼 클릭시 아이디, 비밀번호, 닉네임, 이메일이 모두 중복이 아니고 형식이 맞는지 검사 후
	  이상이 없으면 회원가입 성공 메시지 출력, 이상이 있으면 회원가입 실패 메시지 출력
	- 회원가입 성공시 firebaseDB중 user에 각각의 입력 정보를 저장
	
- BoardFragment 구현 내용:
	- a
	
- BoardPlatformActivity 구현 내용:
	- a
	
- BoardPlatformReviewActivity 구현 내용:
	- a
	
- WritingFragment 구현 내용:
	- a
	
- MyPageFragment 구현 내용:
	- a

- DB 구현 내용:
	- Firebase의 Firestore 이용
	- putData(): Firestore에 최초 데이터 추가
	- updateData(): Firestore에 데이터를 덮어쓰지않고 업데이트
	- getData(): Firestore에서 해당 문서 데이터 읽기
	- getAllData(): Firestore에서 해당 컬렉션의 모든 문서 데이터 읽기
	- useData(): 읽은 데이터를 변환/처리하여 사용
	
- Cloud Storage 구현 내용:
	- Firebase의 Storage 이용
	- putImage(): Storage에 최초 이미지 추가
	- getImage(): Storage에서 해당 이미지를 읽어서 ImageView에 입력 - Glide 리이브러리 사용
	
- 실행환경:
	- SDK 버전: Android 11(R)
	- AVD: Pixel 2 API 30(Target: Android 11.0(Google Play), CPU/ABI: x86/64)
