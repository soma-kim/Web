<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    }

</style>
</head>
<body>

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
        <form id="login-form" action="/jsp/login.me" method="post">

            <table>
                <tr>
                    <th>아이디: </th>
                    <td><input type="text" name="userId" required></td>
                </tr>
                <tr>
                    <th>비밀번호: </th>
                    <td><input type="password" name="userPwd" required></td>
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">로그인</button>
                        <button type="button">회원가입</button>
                    </th>
                </tr>
            </table>

        </form>

        <!-- 로그인 성공 후에 보여지는 프로필 화면 -->
        <!--
        <div id="user-info">
            <b>xxx님!</b> 환영합니다요 ^0^ <br><br>
            <div align="center">
                <a href="">마이페이지</a>
                <a href="">로그아웃</a>
            </div>
        </div>
        -->
    </div>

    <br clear="both"> <!-- float 속성 해제 -->
    <br>

    <div class="nav-area" align="center">
        <!-- (div.menu>a)*4 + Enter -->
        <div class="menu"><a href="">HOME</a></div>
        <div class="menu"><a href="">공지사항</a></div>
        <div class="menu"><a href="">일반게시판</a></div>
        <div class="menu"><a href="">사진게시판</a></div>
    </div>


</body>
</html>