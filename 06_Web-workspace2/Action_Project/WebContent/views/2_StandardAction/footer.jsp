<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.text.SimpleDateFormat, java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		String year = new SimpleDateFormat("yyyy").format(new Date());
	%>

	Copyright © 1998-<%= year %> KINGODOG Information Educational Institute All Right Reserved
	<!-- 매년 해가 바뀔 때마다 직접적으로 년도를 바꾸는 것은 너무 귀찮음 -->
	
	<br>
	
	test: ${ param.test }

</body>
</html>