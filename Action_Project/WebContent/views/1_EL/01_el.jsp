<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.model.vo.Person" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>EL 기본 구문</h1>
	
	<h3>1. 기존 방식대로 스크립틀릿과 표현식을 이용해서 각 영역에 담겨 있는 값을 뽑아서 화면에 출력</h3>
	<%--	
	<%
		// requestScope에 담긴 데이터 뽑기
		String classRoom = (String)request.getAttribute("classRoom");
		Person student = (Person)request.getAttribute("student");
		
		// sessionScope에 담긴 데이터 뽑기
		String academy = (String)session.getAttribute("academy");
		Person teacher = (Person)session.getAttribute("teacher");
	%>
	
	<p>
		학원명: <%= academy %> <br>
		강의장: <%= classRoom %> <br>
		강사 정보: <%= teacher.getName() %>, <%= teacher.getAge() %>, <%= teacher.getGender() %> <br>
		수강생 정보  <br>
		<ul>
			<li>이름: <%= student.getName() %></li>
			<li>나이: <%= student.getAge() %></li>
			<li>성별: <%= student.getGender() %></li>
		</ul>
	</p>
	 --%>
	
	<hr>
	
	<h3>2. EL을 이용해서 보다 쉽게 해당 Scope에 저장된 데이터들을 출력하기</h3>
	
	<p>
		EL은 기본적으로 getXXX을 통해 값을 빼 올 필요 없이 키값만 제시하면 바로 값에 접근 가능 <br>
		내부적으로 해당 Scope 영역에 해당 키값의 밸류값을 가지고 올 수 있음 <br>
		기본적으로 EL은 JSP의 내장객체를 구분하지 않고 자동적으로 모든 내장 객체에 키값을 검색해서 존재하는 경우 값을 가지고 옴 <br>
		단, 공유 범위가 작은 객체부터 찾아내기 시작함 <br>
		만약 찾는 값이 없다면 오류를 발생시키지 않고 출력을 안 함
	</p>
	
	<p>
		학원명 : ${ academy } <br>
		강의장 : ${ classRoom } <br>
		강사 정보 : ${ teacher.name }, ${ teacher.age }, ${ teacher.gender } <br>
		<%--
			teacher라는 키값에 딸린 밸류값은 Person 객체임
			해당 Person 객체의 각 필드에 담긴 값을 EL 표기법으로 출력하고자 한다면 .필드명으로 접근하면 됨
			=> 내부적으로 해당 객체에 대한 해당 필드의 getter 메소드를 알아서 찾아 실행하는 구조
			(즉, VO 클래스 생성 시 getter 메소드는 항상 만들어야 함)
		--%>
		
		수강생 정보 <br>
		<ul>
			<li>이름: ${ student.name }</li>
			<li>나이: ${ student.age }</li>
			<li>성별: ${ student.gender }</li>
		</ul>
	</p>
	
	<hr>
	
	<h3>3. 단, EL 사용 시 내장 객체들에 저장된 키값이 동일할 경우</h3>
	
	scope 값: ${ scope } <br> <!-- request가 출력됨 -->
	<!-- 
		EL은 공유 범위가 제일 작은 Scope에서부터 해당 키값을 검색함
		pageScope => requestScope => sessionScope => application 순으로 속성을 찾음
		
		만약 모든 영역에서 해당 키값을 못 찾는 경우?
		아무것도 출력되지 않음 (오류 발생하지 않음) => EL은 없으면 아무것도 출력되지 않으나 기본 방식은 키값 틀리면 500 오류 남!
	-->
	
	<h3>4. 직접 Scope 영역을 지정해서 접근하기</h3>
	
	<%
		// pageScope에 데이터 담기
		pageContext.setAttribute("scope", "page");
	%>
	
	pageScope에 담긴 값: ${ scope } 또는 ${ pageScope.scope } <br>
	requestScope에 담긴 값: ${ requestScope.scope } <br>
	sessionScope에 담긴 값 : ${ sessionScope.scope } <br>
	applicationScope에 담긴 값: ${ applicationScope.scope } <br><br>
	
	잘못된 접근 예시(session으로부터 classRoom 키값 찾기) : ${ sessionScope.classRoom } <br>
	<!-- request 객체에 담은 classRoom 객체를 session에서 찾고자 하니 오류가 발생하는 것이 아니라 출력이 안 됨! -->
	
	<!-- 
		EL 구문 내에서 키값만 제시한다기보다는 스코프객체.키값으로 표현하는 걸 더 권장함
		=> 가독성 때문에 (도 맞지만)
		=> 필요한 값을 찾기 위해 pageScope부터 순차적으로 탐색이 모두 일어나기 때문에 성능상의 문제를 방지하기 위함
	 -->

</body>
</html>