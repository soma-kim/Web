<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.model.vo.Person" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>JSTL Core Library</h1>
	
	<h3>1. 변수(속성)</h3>
	
	<pre>
		* 변수 선언 (&lt;c:set var="변수명" value="리터럴" scope="스코프영역지정(생략가능)"&gt;)
		<!-- c는 상단 스크립틀릿에서 taglib의 속성으로 내가 정한 접두어임! -->
		
		- 변수를 선언하고 초기값을 대입해 두는 기능을 제공
		- 해당 변수를 어떤 scope 영역에 담아둘 건지 지정 가능함(scope 속성을 생략 시 기본적으로 pageScope에 담김)
		=> 즉, 해당 scope 영역에 setAttribute라는 메소드를 이용해서 key + value 형태로 데이터를 담아놓는 개념이라고 생각하면 됨
		=> c:set 을 통해 선언된 변수는 EL로도 접근 가능해서 사용 가능(단, 스크립팅 원소로는 접근 불가)
		
		* 주의사항
		- 변수의 타입을 별도로 지정하지 않음
		- 반드시 해당 변수의 담아두고자 하는 초기값(value) 속성을 무조건 세팅해야 함 (항상 key + value 세트로 넣어야 함, 선언과 동시에 초기화)
	</pre>
	
	<c:set var="num1" value="10" /> <!-- pageScope에 담김 -->
	<!-- pageContext.setAttribute("num1", 10); 와 동일한 역할 -->
	
	<c:set var="num2" value="20" scope="request" /> <!-- requestScope에 담김 -->
	<!-- request.setAttribute("num2", 20); 와 동일한 역할 -->
	
	num1 변수값 : ${ num1 } <br>
	num2 변수값 : ${ num2 } <br>
	
	<c:set var="result" value="${ num1 + num2 }" scope="session" /> <!-- sessionScope에 담김 -->
	<!-- session.setAttribute("result", (int)pageContext.getAttribute("num1") + (int)request.getAttribute("num2")); 와 동일한 역할 -->
	
	result 변수값 : ${ result } <br><br>
	
	<!-- 
		EL 구문 사용 시 변수명만 제시하면 공유 범위가 가장 작은 곳부터 찾아지게 됨
		(티가 나지는 않지만 속도가 느려질 수 있으므로 "스코프영역.변수명"과 같은 틀로 기술하는 것을 권장
	-->
	
	스코프영역.변수명으로 값을 출력 <br>
	num1 : ${ pageScope.num1 } <br>
	num2 : ${ requestScope.num2 } <br>
	result : ${ requestScope.result } <br> <!-- 안 나옴! -->
	result : ${ sessionScope.result } <br><br>
	
	이련 표기법도 있음! <br>
	<c:set var="result" scope="request">
		9999
	</c:set> <!-- value 속성 말고 시작 태그와 종료 태그 사이에도 value값 기술 가능! -->
	<!-- request.setAttribute("result", 9999); 와 동일한 역할 -->
	
	result : ${ result } <br> <!-- 9999 출력 -->
	result : ${ requestScope.result } <br> <!-- 9999 출력 -->
	result : ${ sessionScope.result } <br> <!-- 30 출력 -->
	
	<hr>
	
	<pre>
		*변수 삭제 (&lt;c:remove var="제거하고자하는변수명" scope="스코프영역지정(생략가능)"&gt;)
		
		- 해당 변수를 scope에서 찾아서 제거하는 태그
		- scope 지정 생략 시 모든 scope에서 해당 변수를 다 찾아서 제거함
		- scope를 지정했다면 해당 scope에서 해당 키-밸류 세트를 찾아서 제거함
		=> 즉, 해당 scope로부터 .removeAttribute("키")라는 메소드를 이용해서 키-밸류 세트를 제거하는 거라고 생각하면 됨
	</pre>
	
	삭제 전 result : ${ result } <br><br> <!-- requestScope에 있는 result -->
	
	1) 특정 scope를 지정해서 삭제 <br>
	<c:remove var="result" scope="request" />
	<!-- request.removeAttribute("result1"); 와 동일한 역할 -->
	request로부터 삭제 후 result : ${ result } <br><br>
	
	2) 모든 scope를 삭제 <br>
	<c:remove var="result" />
	<!--
		pageContext.removeAttribute("result");
		request.removeAttibute("result");
		session.removeAttribute("result");
		application.removeAttribute("result"); 와 동일한 역할 
	-->
	삭제 후 result : ${ result } <br><br>
	
	<hr>
	
	<pre>
		* 변수 (데이터) 출력 (&lt;c:out value="출력하고자하는값" default="기본값(생략가능)" escapeXml="true(생략 시 기본값)/false"&gt;)
		
		- 데이터를 출력하고자 할 때 사용하는 태그
		- default : value에 기술한 출력하고자 하는 값이 없을 경우 기본값으로 대체해서 출력할 내용물을 기술하는 속성 (생략 가능)
		- escapeXml: 태그로써 해석해서 출력할지에 대한 여부 (생략 가능, 생략 시 true가 기본값)
	</pre>
	
	그냥 EL 구문으로 result 출력: ${ result } <br>
	c:out 태그로 result 출력(default 속성 생략): <c:out value="${ result }" /> <br>
	c:out 태그로 result 출력(default 속성 추가): <c:out value="${ result }" default="없음" /> <br><br>
	
	<!-- excapeXml 속성 테스트 -->
	<c:set var="outTest" value="<b>출력테스트</b>" />
	<!-- pageContext.setAttribute("outTest", "<b>출력테스트</b>"); 와 동일한 역할 -->
	escapeXml 속성이 true인 경우 (속성 생략): <c:out value="${ outTest }" /> <br>
	<!-- escapeXml 생략 시 기본값이 true == 태그가 해석되지 않음(문자열로 취급됨) -->
	escapeXml 속성이 false인 경우 (속성 추가): <c:out value="${ outTest }" escapeXml="false" />
	
	<hr>
	
	<h3>2. 조건문 - if (&lt;c:if test="조건식"&gt;)</h3>
	
	<pre>
		- JAVA의 단일 if문과 비슷한 역할을 하는 태그
		- 조건식은 test라는 속성에 작성하되 "반드시 EL 구문으로 작성"해야 함!
	</pre>
	
	<%-- 
		기존의 방식
		<% if(조건식) { %>
		<% } %>
	--%>
	
	<c:if test="${ num1 gt num2 }">
		<!-- 만약 조건이 true라면 이 if 태그 안쪽에 구문이 실행될 것 -->
		<b>num1이 num2보다 큽니다.</b>
	</c:if>
	
	<c:if test="${ num1 le num2 }">
		<b>num1이 num2보다 작거나 같습니다.</b>
	</c:if>
	
	<br>
	
	<c:set var="str" value="안녕하세요" />
	
	<%--
		기존의 방식
		<% if(str.equals("안녕하세요")) { %>
			~~~
		<% } %>
	--%>
	
	<c:if test="${ str eq '안녕하세요' }">
		<mark>메롱메롱 이제 수업 끝이지롱 -ㅠ-</mark>
	</c:if>
	
	<c:if test="${ str ne '안녕하세요' }">
		<mark>다들 잘 가요~!</mark>
	</c:if>
	
	<hr>
	
	<h3>3. 조건문 - choose, when, otherwise (&lt;c:choose&gt;, &lt;c:when test="조건식"&gt;, &lt;c:otherwise&gt;)</h3>
	
	<pre>
		- JAVA의 if-else, if-else if문 또는 switch문과 비슷한 역할을 하는 태그
		- 각 조건들은 c:choose 태그의 하위 요소로 묶으면 됨
		- 각 조건들은 c:when 태그를 통해서 작성
		- 작성했던 모든 조건들에 대해서 이도 저도 아닐 때 (else 블럭) c:otherwise로 표현
	</pre>
	
	<%-- 
	기존 방법
	<% if(num1 == 20) { %>
		
	<% } else if(num1 == 10) { %>
		
	<% } else { %>
		
	<% } %>
	--%>
	
	<c:choose>
		<c:when test="${ num1 eq 20 }">
			<b>처음 뵙겠습니다.</b>
		</c:when>
		<c:when test="${ num1 eq 10 }">
			<b>다시 봐서 반갑습니다.</b>
		</c:when>
		<c:otherwise>
			<b>안녕하세요</b>
		</c:otherwise>
	</c:choose>
	
		<hr>
	
	<h3>4. 반복문 - forEach</h3>
	
	<pre>
		for loop 에 대한 [표현법]
		&lt;c:forEach var="변수명" begin="초기값" end="끝값" step="증가시킬값(생략가능)"&gt;
			반복적으로 실행할 구문
		&lt;/c:forEach&gt;
		
		=> begin: JAVA for문의 초기식에 해당
		=> end: JAVA for문의 ~까지에 해당 
		=> step: 증감식에 해당, 생략 시 기본값은 1 (반복이 돌 때마다 1씩 증가)
		
		향상된 for loop 에 대한 [표현법]
		&lt;c:forEach var="변수명" items="순차적으로접근할배열또는컬렉션명" varStatus="현재접근된요소의상태값을보관할변수명(생략가능)"&gt;
			반복적으로 실행할 구문
		&lt;/c:forEach&gt;
		

		=> varStatus: 현재 접근한 요소의 상태값을 보관할 변수명(지정하기 나름), 부가적인 기능 제공(반복의 횟수나 현재 접근한 요소의 인덱스를 알려 줌)
	</pre>
	
	<!-- 기존 방식의 for loop문 -->
	<%-- 
	<% for(int i = 1; i <= 10; i++) { %>
		반복적으로 실행할 구문
	<% } %>
	--%>
	
	<c:forEach var="i" begin="1" end="10">
		반복 확인 : ${ i } <br>
	</c:forEach>
	
	<br>
	
	<%--
	<% for(int i = 1, i <= 10, i += 2) { %>
		반복적으로 실행할 구문
	<% } %>
	--%>
	
	<c:forEach var="i" begin="1" end="10" step="2">
		반복 확인: ${ i } <br>
	</c:forEach>
	
	<br>
	
	<!-- 태그 안에서도 사용 가능함 -->
	<c:forEach var="i" begin="1" end="6">
		<h${ i }>태그 안에서도 적용 가능함</h${ i }>
	</c:forEach>
	
	<br>
	
	<!-- 향상된 for loop -->
	<%-- 
	<% for(String s : list) { %>
		반복적으로 실행할 구문
	<% } %>
	--%>
	
	<c:set var="colors">
		red, yellow, green, pink
	</c:set> <!-- 배열과 같은 역할 -->
	
	colors 값: ${ colors } <br>
	
	<ul>
		<c:forEach var="c" items="${ colors }">
		<li style="color:${ c }">${ c }</li>
		</c:forEach>
	</ul>
	
	<br>
	
	<!-- 종합 응용 -->
	
	<%
		// DB로부터 조회 후 서블릿으로부터 넘겨받았다라는 가정하에 테스트 데이터 세팅
		ArrayList<Person> list = new ArrayList<>();
		list.add(new Person("홍길동", 20, "남자"));
		list.add(new Person("김맘순", 30, "여자"));
		list.add(new Person("박말똥", 40, "남자"));
		
		request.setAttribute("pList", list);
	%>
	
	<table border="1">
		<thead>
			<tr>
				<th>순번</th>
				<th>이름</th>
				<th>나이</th>
				<th>성별</th>
			</tr>
		</thead>
		<tbody>
			<%--
			기존 방식
			<% if(pList.isEmpty()) { %>
				~~~
			<% } else { %>
				<% for(Person p : pList) { %>
					~~~
				<% } %>
			<% } %>
			--%>
			
			<c:choose>
				<c:when test="${ empty pList }">
					<tr align="center">
						<td colspan="4">조회된 결과가 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="p" items="${ pList }" varStatus="status">
						<tr>
							<td>${ status.count }</td> <!-- .index: 0부터 시작, .count: 1부터 시작 -->
							<td>${ p.name }</td>
							<td>${ p.age }</td>
							<td>${ p.gender }</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	
	<hr>
	
	<h3>5. 반복문 - forTokens</h3>
	
	<pre>
		&lt;c:forTokens var="각값을보관할변수" items="분리시키고자하는문자열" delims="구분자"&gt;
		
		- 구분자를 통해서 분리된 각가의 문자열에 순차적으로 접근하면서 반복을 수행
		- JAVA의 StringTokenizer 또는 split(구분자)와 비슷한 역할
	</pre>
	
	<c:set var="device" value="컴퓨터,휴대폰,TV,에어컨/냉장고.세탁기" />
	
	<ul>
		<c:forTokens var="d" items="${ device }" delims=",./"> <!-- 내가 구분자로 쓰고 싶은 모든 것을 나열! -->
			<li>${ d }</li>
		</c:forTokens>
	</ul>
	
	<hr>
	
	<h3>6. 쿼리스트링 관련 - url, param</h3>
	
	<pre>
		&lt;c:url var="변수명" value="요청할url주소"&gt;
			&lt;c:param name="키값" value="밸류값" /&gt;
			&lt;c:param name="키값" value="밸류값" /&gt;
			&lt;c:param name="키값" value="밸류값" /&gt;
			...
		&lt;/c:url&gt;
		
		- url 경로를 생성하고, 쿼리스트링을 정의할 수 있는 태그
		- 넘겨야 할 쿼리스트링이 길 경우 사용하면 편함
	</pre>
	
	<a href="list.do?cPage=1&num1=2&city=서울&name=홍길동"></a> <br>
	
	<!-- url, param 태그를 이용한 방식 -->
	<c:url var="query" value="list.do">
		<c:param name="cPage" value="1" />
		<c:param name="cPage" value="2" />
		<c:param name="cPage" value="서울" />
		<c:param name="cPage" value="홍길동" />
	</c:url>
	
	<a href="${ query }">c:url을 이용한 방식</a>
	
</body>
</html>