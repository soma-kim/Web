<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// request 의 attribute 영역으로부터 값을 뽑기
	// request.getAttribute("키값") : Object 타입의 밸류값이 리턴

	// 뽑아야할 값들 
	// userName : 성함
	String userName = (String)request.getAttribute("userName");
	// phone : 전화번호
	String phone = (String)request.getAttribute("phone");
	// address : 주소
	String address = (String)request.getAttribute("address");
	// message : 요청 사항
	String message = (String)request.getAttribute("message");
	// pizza : 피자종류
	String pizza = (String)request.getAttribute("pizza");
	// toppings : 토핑 (배열)
	String[] toppings = (String[])request.getAttribute("toppings");
	// sides : 사이드메뉴 (배열)
	String[] sides = (String[])request.getAttribute("sides");
	// payment : 결제방식
	String payment = (String)request.getAttribute("payment");
	// price : 결제금액
	int price = (int)request.getAttribute("price");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>KH 피자 아카데미</title>
</head>
<body>

	<h1>피자 결제 페이지</h1>

    <h2>주문 내역</h2>

    <h4>[ 주문자 정보 ]</h4>

    <ul>
        <li>성함 : <%= userName %></li>
        <li>전화번호 : <%= phone %></li>
        <li>주소 : <%= address %></li>

		<% if(message.equals("")) { %>
        	<li>요청사항 : 작성 안함</li> <!-- case 1 : 요청사항을 기술하지 않았을 경우 -->
        <% } else { %>
       		<li>요청사항 : <%= message %></li> <!-- case 2 : 요청사항을 기술했을 경우 -->
        <% } %>
    </ul>

    <br>

    <h4>[ 주문 정보 ]</h4>

    <ul>
        <li>피자 : <%= pizza %></li>

		<% if(toppings == null) { %>
        	<li>토핑 : 선택 안함</li> <!-- case 1 : 토핑을 선택하지 않았을 경우 -->
        <% } else { %>
        	<li>토핑 : <%= String.join(", ", toppings) %></li> <!-- case 2 : 토핑을 선택했을 경우 -->
        <% } %>

		<% if(sides == null) { %>
        	<li>사이드 : 선택 안함</li> <!-- case 1 : 사이드를 선택하지 않았을 경우 -->
        <% } else { %>
        	<li>사이드 : <%= String.join(", ", sides) %></li> <!-- case 2 : 사이드를 선택했을 경우 -->
        <% } %>

        <li>결제 방식 : <%= payment %></li>
    </ul>

    <br>

    <h3>위와 같이 주문하셨습니다.</h3>
    <h2>총 가격 : <%= price %> 원</h2>
    
    <br>
</body>
</html>