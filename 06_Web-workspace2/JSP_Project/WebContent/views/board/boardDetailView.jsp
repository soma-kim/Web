<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.*" %>
<!--
	이렇게 따로 import 해 줘도 되지만 같은 경로니까 위처럼 별 찍어 줘도 됨!
	<%@ page import="com.kh.board.model.vo.Board, com.kh.board.model.vo.Attachment" %>
  -->
<%
	// 필요한 데이터들 먼저 뽑기
	Board b = (Board)request.getAttribute("b");
	// 게시글 번호, 카테고리명, 글 제목, 글 내용, 작성자 아이디, 작성일

	Attachment at = (Attachment)request.getAttribute("at");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer {
        background-color: black;
        color: white;
        width: 1000px;
        height: 700;
        margin: auto;
        margin-top: 50px;
    }

    .outer table { border-color : white; }

</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>

    <div class="outer">

        <br>
        <h2 align="center">일반게시판 상세보기</h2>
        <br>

        <table id="detail-area" align="center" border="1">
            <!-- (tr>th+td+th+td)*4 + Enter -->
            <tr>
                <th width="70">카테고리</th>
                <td width="70"><%= b.getCategory() %></td>
                <th width="70">제목</th>
                <td width="350"><%= b.getBoardTitle() %></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%= b.getBoardWriter() %></td>
                <th>작성일</th>
                <td><%= b.getCreateDate() %></td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height:200px;">
                        <%= b.getBoardContent() %>
                    </p>
                </td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td colspan="3">
                
                	<% if(at == null) { %>
                		<!-- 첨부파일이 없는 경우: '첨부파일이 없습니다.' 출력 -->
                		첨부파일이 없습니다.
                	<% } else { %>
                		<!-- 첨부파일이 있는 경우: 첨부파일을 다운로드받을 수 있게끔 링크 걸기 (첨부파일의 원본명)-->
                    	<!--
                    		링크를 눌렀을 때 조회만 되게끔
                    		<a href="<%= contextPath %>/<%= at.getFilePath() + at.getChangeName() %>">
                    	 -->
                    	 <!-- 링크를 눌렀을 때 다운로드도 가능하게끔, download 속성에서 "원본명"으로 다운로드 가능하게끔 설정 -->
                	     <a download="<%= at.getOriginName() %>" href="<%= contextPath %>/<%= at.getFilePath() + at.getChangeName() %>">
                    		<%= at.getOriginName() %>
                    	</a>
                	<% } %>
                </td>
            </tr>

        </table>

        <br>

        <div align="center">
            <a href="<%= contextPath %>/list.bo?currentPage=1" class="btn btn-secondary btn-sm">목록가기</a>

			<% if(loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())) { %>
	            <!-- 로그인한 사용자가 게시글 작성자일 경우에만 보여지게끔 -->
	            <a href="<%= contextPath %>/updateForm.bo?bno=<%= b.getBoardNo() %>" class="btn btn-warning btn-sm">수정하기</a>
	            <a href="<%= contextPath %>/delete.bo?bno=<%= b.getBoardNo() %>" class="btn btn-danger btn-sm">삭제하기</a>
            <% } %>
        </div>

        <br>

        <!-- 우선 화면 구현만! 기능구현은 AJAX 배우고 나서 할 것! -->
        <div id="reply-area">
            <table border="1" align="center">
                <thead>
                
                	<% if(loginUser != null) { %>
                    <!-- 로그인이 되어 있을 경우 -->
                    <tr>
                        <th>댓글 작성</th>
                        <td>
                            <textarea id="replyContent" cols="50" rows="3" style="resize:none;"></textarea>
                        </td>
                        <td><button onclick="insertReply();">댓글등록</button></td>
                    </tr>
                    <% } else { %>
                    <!-- 로그인이 되어 있지 않은 경우 -->
                    <tr>
                        <th>댓글 작성</th>
                        <td>
                            <textarea id="" cols="50" rows="3" style="resize:none;" readonly>로그인 후 이용 가능한 서비스입니다.</textarea>
                        </td>
                        <td><button disabled>댓글등록</button></td>
                    </tr>
                    <% } %>
                </thead>
                <tbody>
                <!--
                    <tr>
                        <td>admin</td>
                        <td>댓글 내용이 들어갈 자리~!</td>
                        <td>2022년 10월 22일</td>
                    </tr>
                    <tr>
                        <td>admin</td>
                        <td>댓글 내용이 들어갈 자리~!</td>
                        <td>2022년 10월 22일</td>
                    </tr>
                 -->
                </tbody>
            </table>
            
            <script>            	
            	$(function() {
            		selectReplyList();
            		
            		// 댓글 실시간 조회 기능을 추가하고 싶다면? (== 단톡방처럼)
            		// 1초 간격마다 selectReplyList 함수 실행
            		setInterval(selectReplyList, 1000);
            	});
            	
            	function insertReply() {
            		$.ajax({
            			url : "rinsert.bo",
            			data : {
            					content : $("#replyContent").val(),
            					bno : <%= b.getBoardNo() %>
            			},
            			type : "post",
            			success : function(result) {
            				
            				// result에 댓글 작성 성공 시 1, 실패 시 0이 담겨 있을 것
            				if(result > 0) { // 성공
            					
            					// 갱신된 댓글 리스트를 다시 조회
            					selectReplyList();
            				
            					// textarea 초기화 => 새로고침 하지 않아도 한 것 같은 효과
            					$("#replyContent").val("");
            					
            				} else { // 실패
            					
            					alert("댓글 작성에 실패했습니다.")
            					
            				}
            			},
            			error : function() {
            				console.log("댓글 작성용 ajax 통신 실패!");
            			}
            			
            		});
            	}
            	
            	function selectReplyList() {
            		
            		$.ajax({
            			url : "rlist.bo",
            			data : {bno : <%= b.getBoardNo() %>},
            			success : function(list) {
            				
            				// console.log(list);
            				
            				var result = "";
            				
            				for(var i in list) { // i: 0, 1, 2, 3, ..., 마지막 인덱스값
            					
            					result += "<tr>"
            							+ 		"<td>" + list[i].replyWriter + "</td>"
            							+ 		"<td>" + list[i].replyContent + "</td>"
            							+ 		"<td>" + list[i].createDate + "</td>"
            							+ "</tr>";
            					
            				}
            				
            				$("#reply-area tbody").html(result);
            				
            			},
            			error : function() {
            				console.log("댓글리스트 조회용 ajax 통신 실패!");
            			}
            		});
            	}
            	
            </script>
            
            
            <br>
            <br>

        </div>

    </div>

</body>
</html>