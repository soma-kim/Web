<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<!-- 제이쿼리 연결 온라인 방식 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<body>

	<h1>AJAX 개요</h1>
	
	<p>
		Asynchronous JavaScript And XML의 약자로 <br>
		서버로부터 데이터를 가져와 전체 페이지를 새로고침하지 않고 일부만 로드할 수 있게 하는 기법 <br>
		우리가 기존에 a 태그 또는 form 태그를 통해 요청했던 방식은 "동기식 요청"이었음 <br>
		=> 응답 페이지가 돌아와야 그 결과를 볼 수 있었음 (페이지 화면이 깜빡거림) <br>
		비동기식 요청을 보내기 위해서는 AJAX라는 기술이 필요함 <br><br>
		
		* 동기식 / 비동기식 <br>
		- 동기식: 요청 처리 후 그에 해당되는 응답페이지가 돌아와야만 그 다음 작업이 가능 <br>
				만약 서버에서 호출된 결과까지의 시간이 지연되면 무작정 기다려야 함
				=> 흰페이지로 보여짐 / 와이파이 잘 안 잡히는 곳에서 새로운 페이지 열 때처럼!
				전체 페이지가 리로드됨 (새로고침! 즉, 페이지가 기본적으로 깜빡거림)
		- 비동기식: 현재 페이지를 그대로 유지하면서 중간중간마다 추가적인 요청을 보내줄 수 있음 <br>
				   요청을 한다고 해서다른 페이지로 넘어가지 않음 (현재 페이지 그대로임) <br> 
				   요청을 보내 놓고 그에 해당되는 응답이 돌아올 때까지 현재 페이지에서 다른 작업도 할 수 있음
				 => 즉, 페이지가 깜빡거리지 않음!
				 예) 네이버 홈페이지의 아이디 중복 체크 기능 (다른 웹 사이트도 마찬가지),
					네이버 홈페이지의 검색어 자동오나성 기능 (다른 웹사이트들도 마찬가지),
					네이트 홈페이지의 인기 검색어 기능 (다른 웹사이트들도 마찬가지) 등등 <br><br>
					
		* 비동기식의 단점 <br>
		- 현재 페이지에 요청을 계속 보내고 응답을 계속 받는다면 지속적으로 리소스가 쌓임 <br>
		  => 페이지가 현저히 느려질 수 있음 <br>
		- 현재 페이지 내 복잡도가 기하급수적으로 증가 => 성능의 문제, 에러 발생 시 디버깅이 어려움 <br>
		- 요청 후 돌아온 응답 데이터를 가지고 현재 페이지에서 새로운 요소를 만들어서 출력해야 함 <br>
		  => DOM 요소를 새로이 만들어 내는 구문을 잘 익혀 둬야 함 <br><br>
		  
		  *AJAX 구현방식: JavaScript 방식 / jQuery 방식 (코드가 간결하고 사용하기 쉬움) <br><br>
	</p>
	
	<pre>
		* jQuery 방식에서의 AJAX 통신
		
		[ 표현법 ]
		$.ajax({
			속성명 : 속성값,
			속성명 : 속성값,
			...
		});
		
		* 주요 속성
		- url : 요청할 서블릿의 url 매핑값(필수로 작성) => form 태그의 action 속성과 같은 역할
		- type / method : 요청 전송 방식 (get / post, 생략 시 get 방식이 기본) => form 태그의 method 속성과 같은 역할
		- data : 요청 시 전달할 값들을 키-밸류 세트로 작성 => form 태그 내부의 input 태그들과 같은 역할
		- success : ajax 통신 성공 시 실행할 함수를 정의하는 메소드 속성
		- error : ajax 통신 실패 시 실행할 함수를 정의하는 메소드 속성
		- complete : ajax 통신에 성공했든 실패했든 무조건 한 번 실행할 함수를 정의하는 메소드 속성
		
		* 부수적인 속성
		- async : 서버와의 비동기 처리 방식 설정 여부 (기본값 true)
		- contentType : request 의 데이터 인코딩 방식 정의 (보내는 측의 데이터 인코딩)
		- dataType : 서버에서 response 로 오는 데이터의 데이터 형 설정, 값이 없다면 스마트하게 판단함
		                xml : 트리 형태의 구조
		                json : 맵 형태의 데이터 구조 (일반적인 데이터 구조)
		                script : javascript 및 일반 String 형태의 데이터
		                html : html 태그 자체를 return 하는 방식
		                text : String 데이터
		- accept : 파라미터의 타입을 설정 (사용자 특화 된 파라미터 타입 설정 가능)
		- beforeSend : ajax 요청을 하기 전 실행되는 이벤트 callback 함수 (데이터 가공 및 header 관련 설정)
		- cache : 요청 및 결과값을 scope 에서 갖고 있지 않도록 하는 것 (기본값 true)
		- contents : jQuery 에서 response 의 데이터를 파싱하는 방식 정의
		- context : ajax 메소드 내 모든 영역에서 파싱 방식 정의
		- crossDomain : 타 도메인 호출 가능 여부 설정 (기본값 false)
		- dataFilter : response 를 받았을 때 정상적인 값을 return 할 수 있도록 데이터와 데이터 타입 설정
		- global : 기본 이벤트 사용 여부 (ajaxStart, ajaxStop) (버퍼링 같이 시작과 끝을 나타낼 때, 선처리 작업)
		- password : 서버에 접속 권한 (비밀번호) 가 필요한 경우
		- processData : 서버로 보내는 값에 대한 형태 설정 여부 (기본 데이터를 원하는 경우 false 설정)
		- timeout : 서버 요청 시 응답 대기 시간 (milisecond)
	</pre>
	
	<hr>
	
	<h1>jQuery 방식을 이용한 AJAX 테스트</h1>
	
	<h3>1. 버튼 클릭 시 get 방식으로 서버에 데이터 전송 및 응답</h3>
	
	입력:<input type="text" id="input1">
	<button id="btn1">전송</button>
	<br><br>
	
	응답: <label id="output1">현재 응답 없음</label>
	
	<script>
		$(function() {
			
			// 전송 버튼이 클릭되는 순간 요청 보내기
			$("#btn1").click(function() {
				
				// 기존의 동기식 통신
				// location.href = "jqAjax1.do?input=xxx";
				
				// 비동기식 통신
				$.ajax({
					
					// 요청 보내기
					url : "jqAjax1.do", // 어느 url로 보낼 것인지
					data : {input : $("#input1").val()}, // 보낼 데이터, "키 : 밸류" 세트로 보내야 함
					type :  "get", // 요청 방식 지정
					
					// 응답을 받아 주기
					success : function(result) { // 성공 시 응답 데이터가 자동으로 매개변수로 넘어옴
						// console.log("ajax 통신 성공!");
						// console.log(result);
						$("#output1").text(result).css("color", "blue");
					},
					error : function() {
						console.log("ajax 통신 실패!");
					},
					complete : function () {
						console.log("ajax 통신 성공 여부와 상관없이 무조건 호출!");	
					}
				});
				
			});
		});
	</script>
	
	<h3>2. 버튼 클릭 시 post 방식으로 서버에 데이터 전송 및 응답</h3>
	이름: <input type="text" id="input2_1"> <br>
	나이: <input type="number" id="input2_2"> <br>
	<button onclick="test2();">전송</button>
	<!-- 내용이 길어질 것 같으니 선언적 함수 사용! -->
	<br><br>
	
	응답: <label id="output2">현재 응답 없음</label>
	
	<script>
		function test2() {
			
			$.ajax({
				url : "jqAjax2.do",
				data : {
					name : $("#input2_1").val(),
					age : $("#input2_2").val(),
				},
				type : "post",
				success : function(result) {
					
					// console.log(result);
					
					// json setContentType 바꾸기 전 테스트용
					// var arr = [1, 2, "abc"];
					// console.log(arr);
					
					// JSONArray로 응답받을 경우
					/*
					$("#output2").text("이름: " + result[0] + ", 나이: " + result[1]);
					
					// 초기화되는 효과 추가
					$("#input2_1").val("");
					$("#input2_2").val("");
					*/
					
					// JSONObject로 응답받을 경우
					// console.log(result);
					// $("#output2").text(result); // 응답: [object Object]
					$("#output2").text("이름: " + result.name + ", 나이: " + result.age);
					
					// 초기화되는 효과 추가
					$("#input2_1").val("");
					$("#input2_2").val("");
				},
				error : function () {
					console.log("ajax 통신 실패!");
				}
			});
		}
	</script> 
	
	<hr>
	
	<h3>3. 서버로 데이터 전송 후, 조회된 VO 객체를 응답 데이터로 받기</h3>
	
	회원번호 입력: <input type="number" id="input3">
	<button onclick="test3();">조회</button>
	<br><br>
	
	<div id="output3"></div>
	
	<script>
		function test3() {
			
			$.ajax({
				url : "jqAjax3.do",
				data : {no: $("#input3").val()},
				type : "get",
				success : function(result) {
					
					// console.log(result);
					
					var resultStr = "회원번호: " + result.memberNo + "<br>"
								  + "이름: " + result.memberName + "<br>"
								  + "나이: " + result.age + "<br>"
								  + "성별: " + result.gender + "<br>";
								  
					$("#output3").html(resultStr);
				},
				error : function() {
					console.log("ajax 통신 실패!");
				}
			});
			
		}
	</script>
	
	<hr>
	
	<h3>4. 응답 데이터로 여러 개의 객체들이 담겨 있는 ArrayList 받기</h3>
	
	<button onclick="test4();">회원 전체조회</button>
	<br><br>
	
	<table id="output4" border="1" style="text-align:center;">
		<thead>
			<tr>
				<th>회원번호</th>
				<th>회원명</th>
				<th>나이</th>
				<th>성별</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	
	<script>
		function test4() {
			
			$.ajax({
				url : "jqAjax4.do",
				success : function(result) {
					
					// console.log(result);
					// 현재 result에는 회원들의 정보가 담긴 자바 스크립트의 객체배열이 담겨 있음
					
					var str = "";
					
					for(var i = 0; i < result.length; i++) {
						
						// console.log(result[i]);
						// console.log(result[i].memberName);
						
						str += "<tr>"
							 +  	"<td>" + result[i].memberNo + "</td>"
							 +		"<td>" + result[i].memberName + "</td>"
							 +		"<td>" + result[i].age + "</td>"
							 +		"<td>" + result[i].gender + "</td>"
							 + "</tr>";
					}
					
					$("#output4 tbody").html(str)
					
				},
				error : function() {
					console.log("ajax 통신 실패!");
				}
			});
		}
	</script>

</body>
</html>