<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer {
        background-color: black;
        color : white;
        width : 1000px;
        margin : auto;
        margin-top : 50px;
    }

    #enroll-form table { margin : auto;} /* 테이블 가운데 정렬 */
    #enroll-form input { margin : 5px; } /* input 태그들 사이 간격 */
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>
    <!-- ../ : 현재 폴더로부터 한 겹 빠져나가겠다 -->

    <div class="outer">
        
        <br>
        <h2 align="center">회원가입</h2>
        
        <!--
        	현재 나의 주소: http://localhost:8888/jsp/enrollForm.me
        	내가 요청을 보내고자 하는 주소: http://localhost:8888/jsp/insert.me
        	
        	절대경로: /jsp/insert.me
        	상대경로: insert.me
         -->

        <form id="enroll-form" action="<%= contextPath %>/insert.me" method="post">

            <!-- 아이디, 비밀번호, (비밀번호 확인), 전화번호, 이메일, 주소, 취미 -->
            <table>
                <!-- (tr>td*3)*8 + Enter -->
                <tr>
                    <td>* 아이디</td>
                    <td><input type="text" name="userId" maxlength="12" required></td>
                    <td><button type="button" onclick="idCheck();">중복확인</button></td>
                    <!-- 아이디 중복 확인은 나중에 AJAX 라는 기술을 배운 뒤 할 것 -->
                </tr>
                <tr>
                    <td>* 비밀번호</td>
                    <td><input type="password" name="userPwd" maxlength="15" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>* 비밀번호 확인</td>
                    <td><input type="password" maxlength="15" required></td>
                    <!-- 프론트에서 읽고 일치하는지만 보면 되므로 서버에는 넘길 필요 없음 == name 값 필요 없음! -->
                    <td></td>
                </tr>
                <tr>
                    <td>* 이름</td>
                    <td><input type="text" name="userName" maxlength="6" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;전화번호</td>
                    <td><input type="text" name="phone" placeholder="- 포함해서 입력"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;이메일</td>
                    <td><input type="email" name="email"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;주소</td>
                    <td><input type="text" name="address"></td>
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

            <br><br>

            <div align="center">
                <button type="submit" disabled>회원가입</button>
                <button type="reset">초기화</button>
            </div>

            <br>

        </form>

    </div>
    
    <script>
    	function idCheck() {
    		
    		// 아이디를 입력하는 input 요소 객체
    		var $userId = $("#enroll-form input[name=userId]");
    		// name 속성이 userId인 요소가 menubar.jsp 에도 있기 때문에
    		// 확실하게 어디에 속해 있는 요소인지 잘 적어 둬야 함
    		
    		$.ajax({
    			url : "idCheck.me",
    			data : {checkId : $userId.val()},
    			success : function(result) {
    				
    				// result의 값은 "NNNNN" 또는 "NNNNY"가 담겨 있음
    				if(result == "NNNNN") { // 사용 불가
    					
    					alert("이미 존재하거나 탈퇴한 회원의 아이디입니다.");
    					$userId.focus(); // 커서를 깜빡거림으로써 재입력 유도
    				
    				} else {
    					
    					if(confirm("사용 가능한 아이디입니다. 사용하시겠습니까?")) { // 사용하겠다
    						
    						// 아이디 입력값 확정 => 다시 수정 못 하게 readonly 속성 추가
    						$userId.attr("readonly", true);
    						
    						// 회원 가입 버튼 활성화
    						$("#enroll-form button[type=submit]").removeAttr("disabled");
    						
    					} else { // 사용하지 않겠다
    						
    						// 재입력 유도
    						$userId.focus();
    					}
    				}
    			},
    			error : function() {
    				console.log("아이디 중복체크용 ajax 통신 실패!");
    			}
    		});
    	}
    </script>

</body>
</html>