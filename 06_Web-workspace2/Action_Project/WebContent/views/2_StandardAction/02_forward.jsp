<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>여기는 02_forward.jsp 페이지야</h1>
	<!-- 이 페이지의 url: http://localhost:8888/action/views/2_StandardAction/02_forward.jsp -->

	<jsp:forward page="footer.jsp" />
	<!-- forward의 특징상 url 주소는 같으나 화면만 바꿔치기되어 footer.jsp로 나옴! -->
	
</body>
</html>