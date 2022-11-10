<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>EL 연산</h2>
	
	<h3>1. 산술 연산</h3>
	
	<p>
		* 기존 방식 <br>
		10 + 3 = <%= (int)request.getAttribute("big") + (int)request.getAttribute("small") %>
	</p>
	
	<p>
		* EL 연산 <br>
		10 + 3 = ${ big + small } <br>
		10 + 3 = ${ big } + ${ small } <br> <!-- '10 + 3'으로 출력됨 -->
		<!--
			산술연산을 하고 싶다면 EL구문 내부에서 연산자를 써야 함
			따로 쓰면 연산이 안 되고 키값만 불러옴!
		-->
		
		10 - 3 = ${ big - small } <br>
		10 * 3 = ${ big * small } <br>
		10 / 3 = ${ big / small } 또는 ${ big div small } <br> <!-- 3.3333333333333335 소수점이 붙은 계산값이 나옴 -->
		10 % 3 = ${ big % small } 또는 ${ big mod small }
	</p>
	
	<hr>
	
	<h3>2. 숫자간 대소비교 연산</h3>
	
	<p>
		* 기존 방식 <br>
		10 > 3 : <%= (int)request.getAttribute("big") > (int)request.getAttribute("small") %>
	</p>
	<p>
		* EL 연산 <br>
		10 > 3 : ${ big > small } 또는  ${ big gt small } <br>
		10 < 3 : ${ big < small } 또는  ${ big lt small } <br>
		10 >= 3 : ${ big >= small } 또는  ${ big ge small } <br>
		10 <= 3 : ${ big <= small } 또는  ${ big le small }
	</p>
	
	<hr>
	
	<h3>3. 동등 비교 연산</h3>
	
	<p>
		* 기존 방식 <br>
		10 과 3이 일치합니까? : <%= (int)request.getAttribute("big") == (int)request.getAttribute("small") %> <br>
		sOne과 sTwo가 일치합니까? (주소값 비교): <%= request.getAttribute("sOne") == request.getAttribute("sTwo") %> <br>
		<!-- 문자열 비교를 ==으로 하게 되면 내용물이 아닌 주소값을 비교함 -->
		sOne과 sTwo가 일치합니까? (내용물 비교): <%= ((String)request.getAttribute("sOne")).equals((String)request.getAttribute("sTwo")) %> 
	</p>
	<p>
		* EL 연산 <br>
		10과 3이 일치합니까? : ${ big == small } 또는 ${ big eq small } <br>
		big에 담긴 값이 10과 일치합니까? : ${ big == 10 } 또는 ${ big eq 10 } <br>
		
		sOne과 sTwo가 일치합니까? : ${ sOne == sTwo } 또는 ${ sOne eq sTwo } <br>
		<!-- EL에서의 == 비교는 자바에서의 equals 메소드와 같은 동작을 함 (내용물 비교를 수행) -->
		sOne과 sTwo가 일치하지 않습니까? : ${ sOne != sTwo } 또는 ${ sOne ne sTwo } <br>
		<!-- ne : not equals -->
		sOne에 담긴 값이 "안녕"과 일치합니까? : ${ sOne == "안녕" } 또는 ${ sOne eq "안녕" } 
		또는 ${ sOne == '안녕' } 또는 ${ sOne eq '안녕' } <br>
		<!--  EL에서의 문자열 리터럴 제시 시 쌍따옴표, 홑따옴표 관계없이 동등비교가 가능함 -->
	</p>
	
	<hr>
	
	<h3>4. 객체가 null인지 또는 리스트가 비어 있는지 체크하는 연산</h3>
	
	<p>
		* EL 연산 <br>
		pTwo가 null입니까? : ${ pTwo == null } 또는 ${ pTwo eq null } 또는 ${ empty pTwo } <br>
		pOne이 null입니까? : ${ pOne == null } 또는 ${ pOne eq null } 또는 ${ empty pOne } <br>
		pOne이 null이 아닙니까? : ${ pOne != null } 또는 ${ pOne ne null } 또는 ${ !empty pOne } <br>
		
		lOne이 텅 비어 있습니까? : ${ empty lOne } <br>
		lTwo가 텅 비어 있습니까? : ${ empty lTwo } <br>
	</p>
	
	<hr>
	
	<h3>5. 논리연산자</h3>
	
	<p>
		* EL 연산 <br>
		AND 연산: ${ true && true } 또는 ${ true and true } <br>
		OR 연산: ${ true || false } 또는 ${ true or false } <br>
	</p>
	
	<hr>
	
	<h3>연습문제</h3>
	
	<p>
		* EL 연산에서 배운 키워드를 주로 가지고 써볼 것 <br>
		big이 small보다 크고 lOne이 텅 비어 있습니까? : ${ (big gt small) and (empty lOne) } <br>
		big 과 small의 곱은 4의 배수입니까? : ${ ((big * small) mod 4) eq 0 } <br>
		lTwo가 텅 비어 있지 않거나 sOne에 담긴 값이 "안녕하세요"와 일치합니까? : ${ (!empty lTwo) or (sOne eq "안녕하세요") } <br>
		
	</p>

</body>
</html>