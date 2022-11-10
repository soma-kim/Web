<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.notice.model.vo.Notice" %>
    
<%
	// request에 담았던 list 키값을 뽑아오기 (== 공지사항 전체 리스트 조회 결과물)
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");
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
        height : 500px;
        margin : auto;
        margin-top : 50px;
    }

    .list-area {
        border : 1px solid white;
        text-align : center;
    }

    .list-area>tbody>tr:hover {
        background-color: grey;
        cursor : pointer;
    }
</style>
</head>
<body>

<%@ include file="../common/menubar.jsp" %>

<div class="outer">
    <br>
    <h2 align="center">공지사항</h2>
    <br>

	<!-- 공지사항은 관리자만 작성 가능하므로 조건 걸어 줘야 함 -->
	<% if(loginUser != null && loginUser.getUserId().equals("admin")) { %>
	    <div style="width:850px;" align="right">
        <a class="btn btn-secondary" href="<%= contextPath %>/enrollForm.no">글작성</a>
    	</div>
	<% } %>

    <table class="list-area" align="center">
        <thead>
            <tr>
                <th>글번호</th>
                <th width="400">글제목</th>
                <th width="100">작성자</th>
                <th>조회수</th>
                <th width="100">작성일</th>
            </tr>
        </thead>

        <tbody>
            <!-- 보통 작성일 기준 내림차순, 즉 최신 글이 가장 위에 오게끔 구현함 -->
            <!--
            <tr>
                <td>3</td>
                <td>우리 서버는 안 터짐 ㅋㅋ</td>
                <td>admin</td>
                <td>120</td>
                <td>2022-10-16</td>
            </tr>
            <tr>
                <td>2</td>
                <td>공지사항일까요</td>
                <td>admin</td>
                <td>70</td>
                <td>2021-04-28</td>
            </tr>
            <tr>
                <td>1</td>
                <td>안녕하세요 공지사항 서비스 start</td>
                <td>admin</td>
                <td>256</td>
                <td>2021-03-01</td>
            </tr>
          	-->
          	<% if(list.isEmpty()) { %>
          		<!-- 리스트가 비어 있을 경우: 조회된 공지사항이 없을 경우 -->
          		<tr>
          			<td colspan="5">존재하는 공지사항이 없습니다.</td>
          		</tr>
          	<% } else { %>
          		<!--  리스트가 비어 있지 않을 경우: 조회된 공지사항이 적어도 한 건이라도 존재할 경우 -->
          		<% for(Notice n : list) { %> <!-- 향상된 for문 -->
          			<tr>
          				<td><%= n.getNoticeNo() %></td>
          				<td><%= n.getNoticeTitle() %></td>
          				<td><%= n.getNoticeWriter() %></td>
          				<td><%= n.getCount() %></td>
          				<td><%= n.getCreateDate() %></td>
          			</tr>
          		<% } %>
          	<% } %>
          	
        </tbody>

    </table>
    
            <script>
        	$(function() {
        		<!-- 게시글에 클릭 버튼 걸기 -->
        		$(".list-area>tbody>tr").click(function() {
        			
        			<!-- 선택된 td 태그의 글 번호 내용물 추출 -->
        			location.href = "<%= contextPath %>/detail.no?nno=" + $(this).children().eq(0).text();
        			
        		});
        		
        	});
        </script>
</div>

</body>
</html>