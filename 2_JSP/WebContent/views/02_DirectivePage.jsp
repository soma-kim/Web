<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %> <%-- errorPage="error500.jsp" --%> <%-- import="java.util.ArrayList, java.util.Date" --%>
   <%--
   	import 할 클래스들이 여러 개인 경우 ,로 나열
   	보통은 page 지시어는 하나만 쓰는 걸 권장하지만
   	import 속성 같은 경우는 따로 빼서 작성하는 것을 더 권장
   --%>
<%@ page import="java.util.ArrayList, java.util.Date" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>page 지시어</h1>
	
	<%
		// import java.util.ArrayList; // jsp에서 import 구문 작성 불가
		// import java.util.Date;
		// => page 지시어 부분에 import 속성 추가
	
		// ArrayList 사용
		ArrayList<String> list = new ArrayList<>();
		
		list.add("Servlet"); // 0번 인덱스
		list.add("JSP"); // 1번 인덱스
		
		// Date 사용
		Date today = new Date(); // 현재 날짜
	%>
	
		
	<p>
		어레이리스트의 길이: <%= list.size() %> <br>
		0번 인덱스 값: <%= list.get(0) %> <br>
		1번 인덱스 값: <%= list.get(1) %> <br>
		현재 날짜 및 시간: <%= today %> <br>
	</p>
	
	<!-- 참고 -->
	<%= list.get(10) %>
	<!-- list의 크기 2, 접근하고자 하는 인덱스 10 : java.lang.IndexOutOfBoundsException: Index: 10, Size: 2 (500 오류 발생) -->

</body>
</html>