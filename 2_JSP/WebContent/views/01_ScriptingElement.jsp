<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- HTML 주석: 개발자 도구에 노출됨 -->
	<%-- JSP 주석: 개발자 도구에 노출되지 않음 --%>
	
	<h1>스크립팅 원소</h1>
	
	<%!
		// 선언문: 이 안에 멤버변수나 메소드를 선언할 수 있음
		public String test() {
			return "메소드 호출됨";
		}
	%>
	
	<%
		// 스크립틀릿: 이 안에 일반적인 자바 코드를 작성할 수 있음 (변수 선언 및 초기화, 메소드 호출 등)
		// => 자바 코드 문법에 맞게 작성해야 함
		
		// 누적 합 구하는 코드
		int sum = 0;
		for(int i = 1; i <= 100; i++) {
			sum += i;
		}
		System.out.println("덧셈 끝! 결과는 " + sum + "입니다.");
		System.out.println("테스트 메소드 호출 결과: " + test());
	%>
	
	<p>
		화면으로 출력하고자 한다면 <br>
		표현식 (출력식)으로 출력 가능: <%= sum %> <br>
		메소드의 반환값도 출력 가능: <%= test() %> <br>
		<!-- 뒤에 세미콜론 찍으면 안 됨! 표현식은 내부적으로 아래의 스크립틀릿과 같기 때문에 매개변수를 제시하는 느낌으로 변수명/메소드만 적어 줘야 함  -->
		스크립틀릿을 이용해서 출력 가능: <% out.println(sum); %>
	</p>
	
	<%
		// 배열 사용
		String[] name = {"김말똥", "홍길동", "이순신", "김갑생"};
	%>
	
	<h5>배열명 출력: <%= name %> </h5> <!-- name이라는 변수에 담긴 주소값이 그대로 출력됨  -->
	<h5>배열의 길이: <%= name.length %></h5>
	<h5>하나의 문자열로 연이어서 출력: <%= String.join(", ", name) %></h5>
	
	<h4>반복문을 통해 html 요소를 반복적으로 화면에 출력 가능</h4>
	<%-- 이 구문을 반복문으로 표현해 보자
		<ul>
			<li><%= name[0] %></li>
			<li><%= name[1] %></li>
			<li><%= name[2] %></li>
			<li><%= name[3] %></li>
		</ul>
	--%>
	
	<ul>
		<% for (int i = 0; i <name.length; i++) { %>
			<li><%= name[i] %></li>
		<% } %>
	</ul>
	



</body>
</html>