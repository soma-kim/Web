<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>1. formatNumber</h1>
	
	<p>
		숫자 데이터의 포맷(형식) 지정 <br>
		- 표현하고자 하는 숫자 데이터의 형식을 통화 기호, % 등의 원하는 쓰임에맞게 지정하는 태그 <br><br>
		
		&lt;ftm;formatNumber value="출력할값" groupingUsed="true/false" type="percent/currencySymbol="$">&gt; <br>
		
		=> groupingUsed, type, currncySymbol 속성은 생략 가능 <br>
		=> groupingUsed : 숫자 단위의 구분자 (,) 표시 여부를 지정하는 속성(생략 가능, 생략 시 기본값이 true => 구분자를 찍겠다) <br>
		=> type: "percent" (백분율 단위로 보여지겠다) / "currency" (돈 단위로 보여지겠다 => 로컬 컴퓨터 기준) <br>
		=> currencySymbol : type 속성이 "currency"일 경우 통화 기호 문자의 종류를 지정<br>
	</p>
	
	<!-- 포맷팅 테스트를 위한 변수 먼제 세팅 -->
	<c:set var="num1" value="123456789" />
	<c:set var="num2" value="0.75" />
	<c:set var="num3" value="50000" />
	
	그냥 출력: ${ num1 } <br>
	formattingNumber 태그를 이용해서 출력: <fmt:formatNumber value="${ num1 }" /> <br>
	숫자 그대로 출력: <fmt:formatNumber value="${ num1 }" groupingUsed="false" /> <br>
	<!-- 
		3자리마다 , 가 찍혀 있음
		- groupingUsed 기본값: true(생략 시 기본값) => 3자리마다 , 가 찍혀 있음
		즉, groupingUsed는 숫자 단위의 구분자 (,) 표시 여부 지정
	 -->
	 
	그냥 출력: ${ num2 } <br>
	percent : <fmt:formatNumber value="${ num2 }" type="percent" /> <br>
	<!-- 
		type: "percent" : 소숫점을 백분율로 변경해서 보여 줌
	-->
	 
	 그냥 출력: ${ num3 } <br>
	 currency : <fmt:formatNumber value="${ num3 }" type="currency" /> <br>
	 <!-- 
	 	type="currnecy" : 통화(돈)의 단위로 보여짐
	 					     현재 내가 쓰고 있는 컴퓨터의 로컬 정보에 따라서 통화 단위가 달라짐
	 	(groupingUsed 속성의 기본값이 true이기 때문에 3자리마다 , 도 찍혀 있음)
	 -->
	 
	 currency $ : <fmt:formatNumber value="${ num3 }" type="currency" currencySymbol="$" /> <br>
	 <!-- 
	 	currencySymbol : 통화 기호 문자의 종류를 지정
	 -->
	 
	 <hr>
	 
	 <h3>2. formatDate</h3>
	 
	 <p>
	 	날짜 및 시간 데이터의 포맷(형식) 지정<br>
	 	- 단, java.util.Date 객체를 사용해야 함
	 </p>
	 
	 <!-- 테스트를 위한 날짜 변수 세팅 -->
	 <c:set var="current" value="<%= new java.util.Date() %>" />
	 
	 그냥 출력: ${ current } <br>
	 
	 <ul>
	 	<li>
	 		현재 날짜: <fmt:formatDate value="${ current }" type="date" />
	 		<!-- type="date" 는 생략 가능! 생략 시 기본값이 "date" : 날짜를 출력 -->
	 	<li>
	 		현재 시간: <fmt:formatDate value="${ current }" type="time" />
	 		<!--  type="time": 시간만 출력 -->
	 	</li>
	 	<li>
	 		현재 날짜 및 시간: <fmt:formatDate value="${ current }" type="both" />
	 		<!--  type="both" : 날짜와 시간 둘 다 출력 -->
	 	</li>
	 	<li>
	 		medium: <fmt:formatDate value="${ current }" type="both" dateStyle="medium" timeStyle="medium" />
	 		<!-- medium: 기본 길이 형식 -->
	 	</li>
	 	<li>
	 		long: <fmt:formatDate value="${ current }" type="both" dateStyle="Long" timeStyle="Long" />
	 	</li>
	 	<li>
	 		full : <fmt:formatDate value="${ current }" type="both" dateStyle="full" timeStyle="full" />
	 		<!-- long보다도 더 긴 형식 -->
	 	</li>
	 	<li>
	 		short : <fmt:formatDate value="${ current }" type="both" dateStyle="short" timeStyle="short" />
	 	</li>
	 	<li>
	 		내 입맛대로 섞어서 지정 : <fmt:formatDate value="${ current }" type="both" dateStyle="full" timeStyle="short" />
	 		<!-- dateStyle 속성값과 timeStyle 속성값을 굳이 일치시킬 필요는 없음! -->
	 	</li>
	 	<li>
	 		customizing : <fmt:formatDate value="${ current }" type="both" pattern="yyyy-MM-dd(E) a HH:mm:ss" />
	 		<!--
	 			pattern 속성을 통해 커스터마이징 가능
	 			- customizing 시 의미하는 것들
	 			yyyy: 년도
	 			MM: 월
	 			dd: 일
	 			E: 요일
	 			a: 오전/오후
	 			HH: 시
	 			mm: 분
	 			ss: 초	
	 		-->
	 	</li>
	 </ul>

</body>
</html>