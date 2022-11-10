<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Board" %>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
%>
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
        height : 1000px;
        margin : auto;
        margin-top : 50px;
    }

    .list-area {
        width : 760px;
        margin : auto;
    }

    .thumbnail {
        border : 1px solid white;
        width : 220px;
        display : inline-block; /* 가로로 배치, 그냥 inline은 속성 안 먹힘 */
        margin : 14px;
    }

    .thumbnail:hover {
        cursor : pointer;
        opacity : 0.7; /* 투명도 조절해서 마우스 올렸을 때 살짝 흐려지게 보이도록 */
    }

</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>

    <div class="outer">


        <br>
        <h2 align="center">사진 게시판</h2>
        <br>
		
		<% if(loginUser != null) { %>
		<!--
			 로그인한 회원만 보이는 버튼
			 a href 태그 뒤에 절대경로로 <%= contextPath %>/enrollForm.th 기재해도 무방!
	  	-->
	        <div style="width:850px" align="right">
	            <a href="enrollForm.th" class="btn btn-secondary">글작성</a>
	        </div>
		<% } %>
        <br><br>


        <div class="list-area">
       		<!--
            <div class="thumbnail" align="center">
                <img src="" width="200px" height="150px">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
            <div class="thumbnail" align="center">
                <img src="" width="200px" height="150px">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
            <div class="thumbnail" align="center">
                <img src="" width="200px" height="150px">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
            <div class="thumbnail" align="center">
                <img src="" width="200px" height="150px">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
            <div class="thumbnail" align="center">
                <img src="" width="200px" height="150px">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
            <div class="thumbnail" align="center">
                <img src="" width="200px" height="150px">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
            <div class="thumbnail" align="center">
                <img src="" width="200px" height="150px">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
		-->
		
		<% if(!list.isEmpty()) { %>

			<% for(Board b : list) { %>
				
				<div class="thumbnail" align="center">
					<input type="hidden" value="<%= b.getBoardNo() %>">
					<img src="<%= contextPath %>/<%= b.getTitleImg() %>" width="200px" height="150px">
					<p>
						No.<%= b.getBoardNo() %> <%= b.getBoardTitle() %> <br>
						조회수 : <%= b.getCount() %>
					</p>
				</div>
				
			<% } %>

		<% } else { %>
			등록된 게시글이 없습니다
		<% } %>
        </div>
        
        <script>
        
        	$(function() {
        		$(".thumbnail").click(function() {
        		
        			location.href = "<%= contextPath %>/detail.th?bno=" + $(this).children().eq(0).val();
        			/*
        				p 태그 안에 있던 No. 뒤 글 번호를 span 태그로 감싼 뒤 탐색 메소드 활용하여 불러와도 되고,
        			    div에 input type="hidden"으로 글 번호를 따로 한 번 더 만들어서 숨겨 줘도 됨
        			        이번은 input type="hiddne"을 사용함!
        			    "class값 thumbnail인  div 클릭 시, 해당 영역 자식 중 첫 번째의 값의 value(== b.getBoardNo())을 가지고 와라"
        			    => 이 방법으로 글 번호를 구할 수 있음!
        			*/
        			
        		});
        	});
        	
        </script>
</div>

	

</body>
</html>