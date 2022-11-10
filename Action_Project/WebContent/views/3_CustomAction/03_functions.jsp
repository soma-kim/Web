<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>JSTL Functions Library</h1>
	
	<!-- 테스트를 위한 변수 먼저 세팅 -->
	<c:set var="str" value="How are you?" />
	
	str: ${ str } <br>
	
	문자열의 길이: ${ fn:length(str) } 글자 <br>
	문자열의 길이: ${ str.length() } 글자<br><br> <!-- 자바 방식 -->
	
	<%
		java.util.ArrayList<String> list = new java.util.ArrayList<>();
		list.add("안녕");
		list.add("하세요");
		list.add("방가");
		list.add("방가햄토리");
		
		request.setAttribute("list", list);
	%>
	
	list의 길이: ${ fn:length(list) } <br>
	<!-- fn:length 라는 메소드는 문자열의 길이뿐 아니라 ArrayList도 매개변수로 제시하여 사이즈 리턴 가능 -->
	
	모두 대문자로 출력: ${ fn:toUpperCase(str) } <br>
	모두 소문자로 출력: ${ fn:toLowerCase(str) } <br>
	
	are의 시작 인덱스: ${ fn:indexOf(str, 'are') } <br>
	<!-- 문자열의 리터럴의 경우 쌍따옴표든 홑따옴표든 상관없이 표현 가능 -->
	
	are을 were로 변환: ${ fn:replace(str, "are", "were") } <br>
	
	str: ${ str } <br>
	<!-- fn:replace 메소드 실행 시 원본 문자열 값이 변경되지는 않음 -->
	
	str에 "are"이라는 문자열이 포함되어 있나? <br>
	
	<c:choose>
		<c:when test="${ fn:contains(str, 'are') }">
			포함되어 있어
		</c:when>
		<c:otherwise>
			포함되어 있지 않아
		</c:otherwise>
	</c:choose>

</body>
</html>