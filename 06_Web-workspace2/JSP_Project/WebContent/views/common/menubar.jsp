<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.member.model.vo.Member"%>
<%
	// 로그인한 사용자의 정보를 session에 담았기 때문에
	// 이 웹 애플리케이션의 어디서든지 해당 키값을 제시해서 로그인한 사용자의 정보를 꺼내 올 수 있음
	Member loginUser = (Member)session.getAttribute("loginUser");
	
	// System.out.println(loginUser);
	// 로그인 전 menubar.jsp 로딩 시: null
	// 로그인 후 menubar.jsp 로딩 시: 로그인한 회원의 정보가 담긴 Member 객체
	
	// 동적 코딩으로 바꾸기
	// System.out.println(request.getContextPath()); // /jsp
	String contextPath = request.getContextPath();
	
	// 성공 시 알람 문구 또한 session에 담았기 때문에
	// session으로부터 뽑기
	String alertMsg = (String)session.getAttribute("alertMsg");
	// System.out.println(alertMsg);
	// 로그인 성공 전 menubar.jsp 로딩 시: null
	// 로그인 성공 후 menuber.jsp 로딩 시: alert로 띄워 주고자 하는 문구 출력
	
	// 쿠키 불러오기
	// => request.getCookies() 메소드 => Cookie 타입의 배열로 리턴
	Cookie[] cookies = request.getCookies();
	
	// 배열에 담긴 여러 개의 쿠키 세트들 중에 내가 원하는 쿠키만 골라내는 작업 진행
	String saveId = "";
	if(cookies != null) {
		
		for(int i = 0; i < cookies.length; i++) {
			
			// System.out.println(i + " : " + cookies[i].getName() + " / " + cookies[i].getValue());
			// 서버는 기본적으로 JSSESIONID라는 쿠키를 만들어 줌
			// 쿠키로부터 name(키값)을 뽑아내려면 getName(), value(밸류값)을 뽑아내려면 getValue() 메소드 이용
			
			if(cookies[i].getName().equals("saveId")) {
				saveId = cookies[i].getValue();
				break; // 안 걸어도 상관없지만 쿠키가 많은 경우 해당 쿠키를 찾은 이후로는 굳이 계속 반복 돌면서 낭비할 필요가 없음!
			}
		}
	}
	
	// 이 시점 기준으로 "saveId"라는 키값을 가진 쿠키가 있었다면 String 타입으로 saveId라는 변수에 해당 아이디값 자체가 담겨 있을 것!
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    #login-form, #user-info { float : right; }
    /* 오른손잡이가 많은 사용자 편의상 오른쪽이 좋다고 함 */
    
    #user-info a {
        color : black;
        text-decoration: none;
        font-size : 12px;
    }

    .nav-area { background-color : black; }

    .menu {
        display : table-cell; /* 인라인 요소처럼 배치 가능 */
        height : 50px;
        width : 150px;
    }

    .menu a {
        text-decoration : none;
        color : white;
        font-size : 20px;
        font-weight : bold;
        display : block;
        /* 원래 글자에만 마우스 클릭이 먹혔는데 얘 하면 그 공간 자체를 클릭 가능해짐 */
        width : 100%; 
        height : 100%;
        line-height : 50px;
    }

    .menu a:hover {
        background-color : darkgray;
    }

    button {
        background-color: black;
        color : white;
        border-color : white;
    }

</style>

 <!-- 제이쿼리 연결 온라인 방식 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

<!-- 부트스트랩 링크 3개 -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<script>
	
		// script 태그 내에도 스크립틀릿과 같은 jsp 요소를 쓸 수 있음
		// var msg = <%= alertMsg %>;
		// var msg = 성공적으로 로그인이 되었습니다.; // 현재 이런 꼴임!! 따옴표 꼭! 넣어 주기
		
		var msg = "<%= alertMsg %>"; // "성공적으로 로그인이 되었습니다." / "null"
		
		if(msg != "null") {
			alert(msg);
			// 알림창을 띄워 준 후 session에 담긴 해당 메시지를 지워 줘야 함
			// 안 그러면 menubar.jsp가 로딩될 때마다 매번 alert가 뜸!
			
			<% session.removeAttribute("alertMsg"); %>
		}
	
	</script>

	<h1 align="center">Welcome D Class</h1>

    <div class="lonin-area">
        <!-- 로그인 전에 보여지는 로그인 form -->
        <!-- action 값은 로그인 화면 처리 기능 만든 후 url 넣을 예정 -->
        <!-- 
        	현재 나의 위치: http://localhost:8888/jsp/
        	로그인 버튼을 클릭했을 때 이동하고자 하는 위치: http://localhost:8888/jsp/login.me
        	
        	절대경로: /jsp/login.me
        	상대경로(마지막 슬래시 기준 문구가 붙는 것):login.me 
         -->
         <% if(loginUser == null) { %>
        <form id="login-form" action="<%= contextPath %>/login.me" method="post">

            <table>
                <tr>
                    <th>아이디: </th>
                    <td><input type="text" name="userId" required></td>
                </tr>
                <tr>
                    <th>비밀번호: </th>
                    <td><input type="password" name="userPwd" required></td>
                </tr>
                <tr align="right">
                	<th colspan="2">
                		<input type="checkbox" id="saveId" name="saveId" value="y">
                		<label for="saveId">아이디 저장</label>
                	</th>
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">로그인</button>
                        <button type="button" onclick="enrollPage();">회원가입</button>
                    </th>
                </tr>
            </table>
        </form>
        <script>
        	// 모든 요소들이 화면에 다 로딩된 후 saveId라는 자바 변수에 저장된 값을 불러와서
        	// 아이디 입력창에 value 속성으로 설정해 둘 것 & 아이디 저장하기 체크박스에 체크 수행
        	$(function() {
        		
        		var saveId = "<%= saveId %>"; // "user01" / ""
        		
        		if(saveId != "") { // 쿠키가 있는 경우
        			
        			$("#login-form input[name=userId]").val(saveId);
        			$("#saveId").attr("checked", true);
        		} // 쿠키가 없는 경우에는 해당 if문을 타지 않으므로 애초에 checked 되지 않음! 즉, else문 필요 없음!
        		
        	});
        	
        	function enrollPage() {
        		
        		// location.href= "/jsp/views/member/memberEnrollForm.jsp";
        		// 웹 애플리케이션의 디렉토리 구조가 url에 노출되면 보안에 취약
        		
        		// 단순히 페이지 요청이라고 하더라도 반드시 Servlet을 거쳐 갈 것!
        		location.href = "<%= contextPath %>/enrollForm.me";

        	}
        </script>
        
        <% } else { %>

        <!-- 로그인 성공 후에 보여지는 프로필 화면 -->
        <div id="user-info">
            <b><%= loginUser.getUserName() %>님!</b> 환영합니다요 ^0^ <br><br>
            <div align="center">
                <a href="<%= contextPath %>/myPage.me">마이페이지</a>
                <a href="<%= contextPath %>/logout.me">로그아웃</a>
            </div>
        </div>
        <% } %>
    </div>

    <br clear="both"> <!-- float 속성 해제 -->
    <br>

    <div class="nav-area" align="center">
        <!-- (div.menu>a)*4 + Enter -->
        <div class="menu"><a href="<%= contextPath %>">HOME</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.no">공지사항</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.bo?currentPage=1">일반게시판</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.th">사진게시판</a></div>
    </div>

</body>
</html>