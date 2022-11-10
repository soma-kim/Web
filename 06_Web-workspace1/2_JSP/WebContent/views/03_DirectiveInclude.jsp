<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h1>include 지시어</h1>
	
	<h2>01_ScriptingElement.jsp 파일 include 하기</h2>
	
	<%@ include file="01_ScriptingElement.jsp" %>
	
	포함한 jsp 파일 상에 존재하는 자바 변수, 메소드를 가져다 쓸 수 있음 <br>
	1부터 100까지의 총 합계 : <%= sum %> <br>
	test 메소드 호출 결과 : <%= test() %> <br>
	
	<h2>오늘 날짜</h2>
	
	<%@ include file="datePrint.jsp" %>
	
	포함한 jsp 파일 상에 존재하는 style 도 가져다 쓸 수 있음 <br>
	
</body>
</html>