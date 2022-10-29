<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- 
		[ CRUD ]
		웹사이트 입장에서 기본적으로 갖춰야 할 데이터 처리 기능들을 의미함 (데이터 추가, 조회, 수정, 삭제)
		C : Create(생성) 의 약자 => INSERT 구문 (데이터를 생성, 추가의 의미)
		R : Read(읽기) 의 약자 => SELECT 구문 (데이터를 조회의 의미)
		U : Update(갱신) 의 약자 => UPDATE 구문 (데이터를 수정의 의미)
		D : Delete(삭제) 의 약자 => DELETE 구문 (데이터를 삭제, 제거의 의미)
		
		   * 회원서비스		| 	C (INSERT) 	| 	R (SELECT) 	| 	U (UPDATE) 	| 	D (DELETE)
		======================================================================================
		           로그인		    |				|		O		|				|
		          회원가입			|	   O		|			    |				|
		  [아이디 중복확인]		|			    |		O		|				|
		         마이페이지		|				|	    O		|				|
		        회원정보변경		|				|				|		O		|
		          회원탈퇴			|				|				|  O (문맥상 DELETE 는 맞지만 실제 구현은 UPDATE 로)
		          
		   * 공지사항서비스 - 공지사항리스트조회(R) / 공지사항상세조회(R) /
		   				    공지사항작성(C) / 공지사항수정(U) / 공지사항삭제(U-D)
		   				    
		   * 일반게시판서비스 - 게시판리스트조회(R)-페이징처리 / 게시판상세조회(R) /
		   					게시판작성(C)-첨부파일1개업로드 / 게시판수정(U) / 게시판삭제(U-D) / 
		   					[댓글리스트조회(R) / 댓글작성(C)]
		  
		   * 사진게시판서비스 - 게시판리스트조회(R)-썸네일 / 게시판상세조회(R) / 
							게시판작성(C)-다중첨부파일업로드
	-->
	
	<!-- 메인페이지의 상단에 항상 menubar.jsp 가 존재하도록 include -->
	<%@ include file="views/common/menubar.jsp" %>
	
	<!-- 메인 페이지에 광고 팝업이 뜰 수 있게끔, 부트스트랩 모달창 복붙 -->
	<!-- The Modal -->
	<div class="modal" id="adModal">
	  <div class="modal-dialog">
	    <div class="modal-content">

		      <!-- Modal Header -->
		      <div class="modal-header">
		        <h4 class="modal-title">오늘 하루 특가!</h4>
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		      </div>
		
		      <!-- Modal body -->
		      <div class="modal-body">
		        <img src="<%= contextPath %>/resources/images/ad.jpg" width="400">
		      </div>
		
		      <!-- Modal footer -->
		      <div class="modal-footer">
		      	<input type="checkbox" id="mainPop" name="mainPop">
		      	<label for="mainPop">24시간 동안 보지 않기</label>
		      </div>
	      </div>
        </div>
      </div>
      
      <script>
      	$(function() {
      		
      		// 메인 페이지(index.jsp)를 응답해 주는 서블릿이 존재하지 않기 때문에
      		// 서버단에서 쿠키를 생성해서 첨부할 수가 없음 => 프론트단에서 자바 스크립트로 쿠키를 만들어 줘야 함      		
    		
      		// 24시간 동안 보지 않기 체크박스가 클릭되는 순간 mainPop이라는 키값을 가진 쿠키 생성
      		$("#mainPop").click(function() {
      			
      			// 광고창 닫기
      			$("#adModal").modal("hide");
      			
      			// 쿠키 생성 => mainPop의 여부만 검사할 것이기 때문에 쿠키의 value는 그냥 형태에 맞게 하드코딩 할 것
      			
      			// 현재 시간 불러오기 => 하루를 더해 주기 (setDate() 메소드 활용)
      			var today = new Date();
      			today.setDate(today.getDate() + 1); // 날짜를 세팅해 주는 메소드
      			
      			// 자바스크립트에서 쿠키를 생성하려면
      			// document 객체에서 제공하는 cookie 속성에 접근해야 함
      			// 단순히 문자열 대입만으로 쿠키가 생성될 수 있음
      			// 단, 형식에 맞는 문자열을 대입해야 함! => "키=밸류"; expires=만료시간"
      			// (이때 만료시간은 UTC 기준으로 잡아 줘야 함 - 세계 표준시)
      			document.cookie = "mainPop=n; expires=" + today.toUTCString();
      			
      			console.log(document.cookie); // "mainPop=n"
      		});
      		
      		// 클릭 이벤트 밖에 mainPop 이라는 쿠키가 있나 검사
      		// => document.cookie 속성값에 "mainPop=n"이 포함되었나 검사해야 함
      		// 자바 스크립트에서 문자열 포함 관계를 나타내 주는 메소드 : 문자열.inclues("문자열")
      		if(document.cookie.includes("mainPop=n")) {
      			
      			// mainPop 이라는 쿠키가 있다. == 24시간 동안 광고창을 보지 않겠다
      			$("#adModal").modal("hide");      			
      		} else {
      		
      			// mainPop이라는 쿠키가 없다 == 광고창이 보여도 상관없음

          		// 모달창을 닫아 주는 부트스트랩 메소드: .modal("hide");
          		// 모달창을 열어 주는 부트스트랩 메소드: .modal("show");
          		$("#adModal").modal("show");     
      			
      		}
		      		
      	});
      </script>

</body>
</html>