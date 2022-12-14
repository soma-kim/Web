<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>* EL (Expression Language) 표현 언어</h1>
	
	<p>
		기존에 사용했던 표현식(출력식) &lt;%= name %&gt; 과 같이 <br>
		JSP 상에서 표현하고자 하는 값을 ${ name } 의 형식으로 표현해서 작성하는 것
	</p>
	
	<h3>1. EL 기본 구문에 대해서 먼저 배워 보자!</h3>
	<a href="/action/el.do">01_EL</a>
	
	<h3>2. EL의 연산자에 대해서 배워 보자!</h3>
	<a href="/action/operation.do">02_EL의 연산자</a>
	
	<hr>
	
	<!-- 
		*JSP를 이루는 구성인자
		1. JSP 스크립팅 원소: JSP 페이지에서 자바 코드를 직접 기술할 수 있게 하는 기술
			예) 선언문, 스크립틀릿, 표현식(출력식)
		2. 지시어: JSP 페이지 정보에 대한 내용을 표현한다거나 또 다른 페이지를 포함할 때 사용
			예) page 지시어, include 지시어, taglib 지시어(라이브러리를 추가할 때 사용)
		3. JSP 액션 태그: XML 기술을 이용해서 기존의 JSP 문법을 확장하는 기술을 제공하는 태그
						(XML 태그는 반드시 시작 태그와 종료태그가 한 세트로 있어야만 함)
			- 표준 액션 태그(Standard Action Tag)
			  : JSP 페이지에서 바로 사용 가능한 태그 (별도의 라이브러리 연동이 필요 없음 == tablib 지시어 사용할 필요 없음)
			   	표준 액션 태그는 모든 태그명 앞에 jsp: 이라는 접두어가 붙음 
			- 커스텀 액션 태그(Custom Action Tag)
			  : JSP 페이지에서 바로 사용 불가능한 태그 (별도의 라이브러리 연동이 필요한 => taglib 지시어 필요함)
			  	커스텀 액션 태그는 모든 태그명 앞에 jsp: 이외의 다른 접두어가 붙음 (내가 지정한 접두어, 종류는 다양)
			  	제공되고 있는 대표적인 유용한 라이브러리인 JSTL 가 있음
	 -->
	 
	 <h1>* JSP Action Tag</h1>
	 
	 <p>
	 	XML 기술을 이용해서 기존의 JSP 문법을 확장시키는 기술을 제공하는 태그들
	 </p>
	 
	 <h3>1. 표준 액션 태그</H3>
	 
	 <p>
	 	JSP 페이지에서 별도의 라이브러리 연동 없이 곧바로 사용 가능한 태그들 <br>
	 	태그명 앞에 jsp: 이라는 접두어가 붙음
	 </p>
	 
	 <a href="views/2_StandardAction/01_include.jsp">01_jsp:include</a>
	 
	 <br>
	 
	 <a href="views/2_StandardAction/02_forward.jsp">02_jsp:forward</a>
	 
	 <br>
	 
	 <h3>2. 커스텀 액션 태그</h3>
	 
	 <a href="views/3_CustomAction/jstl.jsp">JSTL</a>
	 
	 

</body>
</html>