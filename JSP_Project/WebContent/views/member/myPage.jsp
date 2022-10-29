<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer {
        background-color : black;
        color : white;
        width : 1000px;
        margin : auto;
        margin-top : 50px;
    }

    #mypage-form>table { margin : auto; } /* 테이블 가운데 정렬 */
    #mypage-form input { margin : 5px; } /* 테이블 줄간격 */

</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>
	
	<%
	// 현재 로그인한 사용자의 정보를 자바 변수로 담기
	// => 아이디, 이름, 휴대폰, 이메일, 주소, 관심분야
	// 보통 스크립틀릿은 상단에 기술하지만,
	// 위에서 아래로 흐르는 코드 순서상 가장 위에 해당 구문을 기술하면 loginUser가 정의되지 않은 상태이므로 빨간 줄 뜸 
	// 그래서 여기로 옮겨서 씀!
	String userId = loginUser.getUserId();
	String userName = loginUser.getUserName();
	// String phone = loginUser.getPhone(); // "010-1111-2222" / null => ""
	// null값이 나와서는 안 됨(사용자가 알아듣지 못함), null이라면 빈 문자열을 보여 줘야 함
	String phone = (loginUser.getPhone() == null) ? "" : loginUser.getPhone(); // 삼항연산자 사용
	String email = (loginUser.getEmail() == null) ? "" : loginUser.getEmail();
	String address = (loginUser.getAddress() == null) ? "" : loginUser.getAddress();
	String interest = (loginUser.getInterest() == null) ? "" : loginUser.getInterest();

	%> 

    <div class="outer">
        
        <br>
        <h2 align="center">마이페이지</h2>
        
        <!--
        	현재 나의 주소: http://localhost:8888/jsp/myPage.me
        	내가 요청을 보내고자 하는 주소: http://localhost:8888/jsp/insert.me
        	
        	절대경로: /jsp/insert.me
        	상대경로: insert.me
         -->
         
         <!-- 정보 변경
        	현재 나의 주소: http://localhost:8888/jsp/myPage.me
        	내가 요청을 보내고자 하는 주소: http://localhost:8888/jsp/update.me
        	
        	절대경로: /jsp/update.me
        	상대경로: update.me
         -->

        <form id="mypage-form" action="<%= contextPath %>/update.me" method="post">

            <!-- 아이디, 비밀번호, (비밀번호 확인), 전화번호, 이메일, 주소, 취미 -->
            <table>
                <!-- (tr>td*3)*8 + Enter -->
                <tr>
                    <td>* 아이디</td>
                    <td><input type="text" name="userId" maxlength="12" required value="<%= userId %>" readonly></td>
                    <!-- 아이디의 수정을 막기 위해 readonly 속성 부여 -->
                    <td></td>
                    <!-- 아이디 중복 확인은 나중에 AJAX 라는 기술을 배운 뒤 할 것 -->
                </tr>
                <tr>
                    <td>* 이름</td>
                    <td><input type="text" name="userName" maxlength="6" required value="<%= userName %>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;전화번호</td>
                    <td><input type="text" name="phone" placeholder="- 포함해서 입력" value="<%= phone %>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;이메일</td>
                    <td><input type="email" name="email" value="<%= email %>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;주소</td>
                    <td><input type="text" name="address" value="<%= address %>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;관심분야</td>
                    <td colspan="2">
                        <!-- (input[type=checkbox name=interest id= value=]+label)*6 + Enter -->
                        <input type="checkbox" name="interest" id="sports" value="운동">
                        <label for="sports">운동</label>
                        <input type="checkbox" name="interest" id="hiking" value="등산">
                        <label for="hiking">등산</label>
                        <input type="checkbox" name="interest" id="fishing" value="낚시">
                        <label for="fishing">낚시</label>
                        <br>
                        <input type="checkbox" name="interest" id="cooking" value="요리">
                        <label for="cooking">요리</label>
                        <input type="checkbox" name="interest" id="game" value="게임">
                        <label for="game">게임</label>
                        <input type="checkbox" name="interest" id="movie" value="영화">
                        <label for="movie">영화</label>
                    </td>
                </tr>
            </table>
            
            <script>
            	$(function() {
            		var interest = "<%= interest %>"; // "" / "운동, 등산"
            		
            		// [input, input, input, input, input, input]
            		$("input[type=checkbox]").each(function() {
            			
            			// 순차적으로 접근한 input 요소에 value 속성값이 interest라는 변수에 포함되었을 경우
            			// => 해당 input 요소에 checked라는 속성 부여
            			if(interest.search($(this).val()) != -1) { // 포함됐다면
            				// interest라는 문자열로부터 현재 체크박스의 value 속성이 포함되지 않으면 -1 반환
            				$(this).attr("checked", true);
            			}
            			
            		});
            	});
            </script>

            <br><br>

            <div align="center">
                <button type="submit" class="btn btn-secondary btn-sm">정보 변경</button>
                <button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#updatePwdForm">비밀번호 변경</button> <!-- 추후 모달창 띄워서 비밀번호 변경할 수 있게끔 할 것-->
                              <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#deleteForm">회원 탈퇴</button>
                <!-- 부트스트랩 사용해서 버튼 색상을 주면 더 좋을 것 같음! 각종 링크를 menubar.jsp에 붙여 놓고 함께 사용하기로 함 -->
                <!-- 만들고 나서 VSCode 프리뷰어에는 바로 적용 안 되는 게 정상! 브라우저로 열어보면 적용될 것 -->
            </div>
            <br>
        </form>
    </div>

    <!-- 회원 탈퇴용 모달창 - 버튼 클릭 시 뜨도록 -->
    <!-- The Modal -->
	<div class="modal" id="deleteForm">
        <div class="modal-dialog">
          <div class="modal-content">
      
            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">회원 탈퇴</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
      
            <!-- Modal body -->
            <div class="modal-body">
                <b>탈퇴 후 복구가 불가능합니다. <br>
                정말로 탈퇴하시겠습니까? <br><br></b>
                
                <!-- 
                	현재 나의 위치: http://localhost:8888/jsp/myPage.me
                	회원 탈퇴 요청 시 요청을 보내고자 하는 url 위치: http://localhost:8888/jsp/delete.me
                	
                	절대경로: /jsp/delete.me
                	상대경로: delete.me
                 -->

                <form action="<%= contextPath %>/delete.me" method="post">
                    <table>
                        <tr>
                            <td>비밀번호</td>
                            <td><input type="password" name="userPwd" required></td>
                        </tr>
                    </table>
                    <br>
                    <button type="submit" class="btn btn-danger btn-sm">탈퇴하기</button>
                </form>
  
            </div>     
          </div>
        </div>
    </div>
    
    <!-- 비밀번호 변경용 모달창(부트스트랩) -->
    <!-- The Modal -->
	<div class="modal" id="updatePwdForm">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">비밀번호 변경</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">

            <!--
		                현재 나의 위치: localhost:8888/jsp/myPage.me
		                비밀번호 변경 요청 시 url: localhost:8888/jsp/updatePwd.me
                
               	절대 경로: /jsp/updatePwd.me
               	상대 경로: updatePwd.me
                
            -->
	        
			<form action="<%= contextPath %>/updatePwd.me" method="post">
				<!-- 현재 비밀번호, 변경할 비밀번호, 변경할 비밀번호 확인(재입력) -->
				<!-- 해당 회원의 아이디값을 애초에 숨겨서 보내기  -->
				<input type="hidden" name="userId" value="<%= userId %>">
				<table>
					<tr>
						<td>현재 비밀번호</td>
						<td><input type="password" name="userPwd" required></td>
					</tr>
                    <tr>
                        <td>변경할 비밀번호</td>
                        <td><input type="password" name="updatePwd" required></td>
                    </tr>
                    <tr>
                        <td>변경할 비밀번호 재입력</td>
                        <td><input type="password" name="checkPwd" required></td>
                    </tr>
				</table>
				
				<br>
				
				<button type="submit" class="btn btn-secondary btn-sm" onclick="return validatePwd();">비밀번호 변경</button>
				
			</form>

            <script>
                function validatePwd() {
                    if($("input[name=updatePwd]").val() != $("input[name=checkPwd]").val()) {
                        alert("비밀번호가 일치하지 않습니다.");
                        return false;
                    }
                }
            </script>

	      </div>
	
	    </div>
	  </div>
	</div>

</body>
</html>