# 팀프로젝트 8조 - Readme 파일
- 이름: 김태범(20181602), 강동우(20181566), 김진명(20181599), 문민철(20181607)

- MainActivity 구현 내용:
	- login(): 아이디와 비밀번호를 입력받고, FireStoreDB에 "user" doucument에서 아이디가 존재하는지 확인, 없으면 알림 메시지 출력
	- 아이디가 존재할 경우, "pw"키 값이 입력받은 비밀번호와 일치하는지 확인, 일치하지 않으면 알림 메시지 출력
	- 아이디, 비밀번호 모두 일치할 경우 로그인 성공, SecondActivity로 화면 이동
	- 로그인 완료시 글쓰기 및 마이페이지에서 활용하기 위한 목적으로 로그인한 사람의 아이디를 프래퍼런스로 저장 
	 	   
	
- SecondActivity 구현 내용:
	- 로그인 성공 시 첫 출력 화면을 게시판 페이지(BoardFragment)로 설정 
	- bottomNavigationBar 기능 구현 (게시판, 글 작성, 마이 페이지 각각의 아이콘 클릭시 화면 전환)
	- onBackPressed(): 뒤로가기 버튼 클릭 시 "로그아웃 하시겠습니까?" 알림 출력, '예' 클릭 시 로그아웃 메시지 출력과 함께 로그인 페이지로 이동
	
- SignUpActivity 구현 내용:
	- idCheck(): 아이디 형식을 5~20자, 숫자와 소문자 조합으로 설정
	- doubleCheck(): 아이디가 중복이거나 형식에 맞지 않거나 알림 메시지 출력, 회원가입 불가
	 		 닉네임이 중복이거나 형식에 맞지 않으면 알림 메시지 출력, 회원가입 불가	
	- passCheck(): 비밀번호 형식을 숫자,문자,특수문자 최소 한 개 포함 8~15자 이내로 설정
	- isequalPassword(): 비밀번호와 비밀번호 재입력 값이 같지 않은 경우 알림 메시지 출력, 회원가입 불가
	- nicknameCheck: 닉네임 형식을 2~8자, 한글,영어,숫자 조합으로 설정
	- emailCheck(): 이메일 형식은  @ 과 . 이 포함되어야함
			이메일 형식에 맞지 않은 경우 알림 메시지 출력, 회원가입 불가
	- buttonClicked(): 각 버튼 클릭 시 실행되는 함수
		- 회원가입 버튼 클릭시 아이디, 비밀번호, 닉네임, 이메일이 모두 중복이 아니고 형식이 맞는지 검사 후
		  이상이 없으면 회원가입 성공 메시지 출력, 이상이 있으면 회원가입 실패 메시지 출력
		- 아이디 중복 확인 버튼 클릭시 doubleCheck() 호출
		- 닉네임 중복 확인 버튼 클릭시 doubleCheck() 호출
	- 회원가입 성공시 firebaseDB중 user에 각각의 입력 정보를 저장
	
- BoardFragment 구현 내용:
	- 로그인 성공 시 첫 출력 화면
	- 6개의 각각의 버튼(PC/Mobile/Nintendo/PS/Xbox/Etc)을 통해 클릭 시 BoardPlatformActivity 6페이지 중 한 페이지로 이동
	
- BoardPlatformActivity(BoardPlatformPC/Mobile/Nintendo/PS/Xbox/EtcActivity) 구현 내용:
	- firstButton: 아래의 글이 게임이름, 작성자, 제목 순으로 작성돼있다는 것을 알려주기 위해 생성한 버튼(눌러도 특별한 이벤트가 발생되지 않습니다.)
	- btn_search: 검색버튼, setOnClickListener를 통해 누르면 et_search에 작성된 내용을 바탕으로 검색
	- getAllData()를 통해 각 플랫폼에 작성된 모든 글을 불러와 useData()를 통해 글의 갯수만큼 그 글에 해당하는 리뷰 페이지로 연결되는 동적 버튼 생성
	- useData(): case 1: 각 플랫폼 페이지 접속 시 사용 case2: 검색기능 사용시 사용
	- putDatas(): useData() case 1,2의 btn에 쓰여진 text를 저장하여 이 정보를 활용하여 리뷰 페이지에 이에 해당되는 글을 작성하기 위한 메소드

- BoardPlatformReviewActivity 구현 내용:
	- setPage(): 눌려진 버튼에 해당하는 데이터를 출력하기 위한 메소드
	- getData(): tv_pl_re_platform에 쓰여진 플랫폼을 통해 데이터베이스의 collection에 접근, tv_platform_review_title을 통해 데이터베이스의 document에 접근
	- getImage(): Storage에서 해당 이미지를 읽어서 ImageView에 입력 - Glide 리이브러리 사용
	
- WritingFragment 구현 내용:
	- 프래퍼런스에 저장한 아이디를 바탕으로 사용자의 닉네임을 불러와서 글쓴이로 설정
	- platform_initspinner() : 스피너로 플랫폼을 설정하고 그 값을 가져와서 저장할 위치를 설정
	- 각각의 리뷰칸을 1칸이라도 작성해야 글쓰기가 완료가 됨, 빈칸이 있다면 emptycontent로 내용을 대체해서 저장
	- attribute : 각각의 리뷰의 종류에 맞는 내용을 저장하는 ArrayList
	- title : 글의 제목을 저장하는 String
	- gametitle : 게임의 이름을 저장하는 String
	- rating : 게임의 전체적인 평점을 저장하는 float
	- writer : 글의 작성자의 이름(닉네임)을 저장하는 String
	- writer은 프래퍼런스의 아이디를 바탕으로 DB에서 가져오도록 설정함
	- 이 모든것을 putData를 통헤 DB에 저장
	
- MyPageFragment 구현 내용:
	- 프래퍼런스에 저장한 아이디를 바탕으로 사용자의 정보를 불러와서 출력
	- 마이페이지에서 정보를 열람 및 수정 가능(비밀번호는 ****** 로 표시 )
	- 수정 가능한 항목은 이메일과 비밀번호로 한정 (닉네임과 아이디는 중복을 막기위해 불가능)
	- SignUpActivity의 함수를 가져와서 이메일과 비밀번호 변경시 형식에 맞지 않으면 수정불가 메시지 출력, 변경되지않음
	- 형식에 맞을경우 수정이 됨

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
