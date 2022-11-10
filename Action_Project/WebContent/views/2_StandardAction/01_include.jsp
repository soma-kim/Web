<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>include</h3>
	
	<p>
		또 다른 페이지를 포함하고자 할 때 쓰이는 태그
	</p>
	
	<%--
	<h4>1. 기존의 include 지시어를 이용한 방식</h4>
	
	<%@ include file="footer.jsp" %>
	<!-- 같은 폴더 내에 있으므로 파일명만 입력해도 됨! -->
	<br><br>
	
	특징: include 하고 있는 페이지상에 선언되어 있는 변수를 현재 이 페이지에서도 사용 가능함 <br>
	include한 페이지의 year 변수값 : <%= year %>
	
	단, 현재 이 페이지에서 동일한 이름의 변수를 추가적으로 선언할 수 없음 <br>
	
	<%
		String year = "2022";
		// Duplicate local variable year : 변수명 중복 오류
	%>
 	--%>
 	
 	<hr>
 	
 	<h4>2. JSP 표준 액션 태그를 이용한 방식 (동적 include 방식 == 런타임 시(실행되는 도중에) 포함되는 형태 )</h4>
 	
 	<!-- 
 		반드시 시작 태그와 종료 태그를 같이 써야만 함 (XML 기술을 이용했으므로)
 	 -->
 	 
 	 <!-- <jsp:include page="footer.jsp"></jsp:include> -->
 	 <jsp:include page="footer.jsp" />
 	 <!-- 중간 내용물이 없을 경우 슬래시로 종료 태그로 만들 수도 있음! -->
 	 <br><br>
 	 
 	  특징1: include 하고 있는 페이지에 선언된 변수를 공유하지 않음 (즉, 동일한 이름의 변수 선언 가능) <br>
 	 
 	 <%
 	 	String year = "2022";
 	 %>
 	 
 	 특징 2: 페이지 포함 시 include 하는 페이지로 값을 전달할 수 있음 <br><br>
 	 
 	 <jsp:include page="footer.jsp">
 	 	<jsp:param name="test" value="Hello...?" />
 	 </jsp:include>
 	 
 	 <br>
 	 
 	 <jsp:include page="footer.jsp">
	 	<jsp:param name="test" value="GoodBye!" />
	 </jsp:include>
 	
</body>
</html>