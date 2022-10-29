<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer {
        background-color: black;
        color : white;
        width : 1000px;
        height : 500px;
        margin : auto;
        margin-top : 50px;
    }

    #enroll-form>table { border : 1px solid white; }
    #enroll-form input, #enroll-form textarea {
        width : 100%;
        box-sizing : border-box;
    }
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>

    <div class="outer">
        <br>
        <h2 align="center">공지사항 작성하기</h2>
        <br>
		
		<!-- 
			현재 나의 위치: http:// localhost:8888/jsp/enrollForm.no
			공지사항 작성 요청을 보낼 url: http://localhost:8888/jsp/insert.no
			
			절대 경로: /jsp/insert.no
			상대 경로: insert.no
		 -->
        <form id="enroll-form" action="<%= contextPath %>/insert.no" method="post">
        
        	<!--
        		 현재 로그인한 사용자의 정보를 알아내는 방법
        		 1. input type="hidden"으로 애초에 넘기기
        		 2. session으로부터 얻어내기
        	 -->
        	 <input type="hidden" name="userNo" value="<%= loginUser.getUserNo() %>">
        	
            <table align="center">
                <tr>
                    <th width="50">제목</th>
                    <td width="350"><input type="text" name="title" required></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea name="content" rows="10" name="content" style="resize:none;" required></textarea>
                    </td>
                </tr>
                
            </table>
            <br><br>

            <div align="center">
                <button type="submit">등록하기</button>
                <button type="button" onclick="history.back();">뒤로가기</button>
                <!-- history.back(): 이전 페이지로 돌아가기-->
            </div>

        </form>
    </div>

</body>
</html>