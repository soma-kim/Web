package com.kh.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PizzaServlet
 */
@WebServlet("/confirmPizza.do")
public class PizzaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PizzaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// System.out.println("잘 실행되나..?");
		
		// GET 방식으로 요청했었음 => 인코딩설정은 패스
		// POST 방식일경우 request.setCharacterEncoding("UTF-8"); 구문을 먼저 실행해야함
		
		// 요청시 전달값 뽑기 (request 의 parameter 영역으로부터)
		// request.getParameter("키값") : String 타입의 밸류값 하나 리턴
		// request.getParameterValues("키값") : String[] 타입의 밸류값들이 묶여서 리턴
		// => 만약 키값이 존재하지 않는다면 
		//	  (즉, radio 나 checkbox 의 경우 체크가 안되면 키값이 애초에 안넘어감)
		//    없는 키값을 제시할 경우 null 이 반환됨
		
		// 뽑아야할 값들
		// userName : 주문자명
		String userName = request.getParameter("userName"); // "홍길동"
		// phone : 휴대폰번호
		String phone = request.getParameter("phone"); // "01011112222"
		// address : 주소지
		String address = request.getParameter("address"); // "서울시 영등포구"
		// message : 요청메세지
		String message = request.getParameter("message"); // "일회용품은 빼주세요" / ""
		// pizza : 피자종류
		String pizza = request.getParameter("pizza"); // "콤비네이션피자" / "치즈피자" / ..
		// topping : 토핑선택 (여러개)
		String[] toppings = request.getParameterValues("topping"); // ["고구마무스", "콘크림무스"] / null
		// side : 사이드메뉴선택 (여러개)
		String[] sides = request.getParameterValues("side"); // ["사이다", "핫소스"] / null
		// payment : 결제방식
		String payment = request.getParameter("payment"); // "card" / "cash"
		
		// -- 원래의 흐름 --
		// VO 객체로 가공
		
		// VO 객체로 가공한 전달값을 Service 단으로 넘기기 -> DAO -> DB
		// SQL문 실행 결과를 리턴받기
		
		// -- DB 까지 요청이 들어갔다라는 가정 하에 자바 로직으로 간단하게 처리해보기 --
		int price = 0;
		
		switch(pizza) {
		case "콤비네이션피자" : price += 10000; break;
		case "치즈피자" : price += 11000; break;
		case "포테이토피자" : 
		case "고구마피자" : price += 12000; break;
		case "불고기피자" : price += 13000; break;
		}
		
		if(toppings != null) {
			
			for(int i = 0; i < toppings.length; i++) {
				
				switch(toppings[i]) {
				case "고구마무스" : 
				case "콘크림무스" : price += 1500; break;
				case "파인애플토핑" : 
				case "치즈토핑" : price += 2000; break;
				case "치즈크러스트" :
				case "치즈바이트" : price += 3000; break;
				}
			}
		}
		
		if(sides != null) {
			
			for(int i = 0; i < sides.length; i++) {
				
				switch(sides[i]) {
				case "콜라" :
				case "사이다" : price += 2000; break;
				case "갈릭소스" : 
				case "핫소스" : price += 300; break;
				case "피클" :
				case "파마산치즈가루" : price += 1000; break;
				}
			}
		}
		
		/*
		System.out.println("userName : " + userName);
		System.out.println("phone : " + phone);
		System.out.println("address : " + address);
		System.out.println("message : " + message);
		System.out.println("pizza : " + pizza);
		System.out.println("toppings : " + String.join(", ", toppings));
		System.out.println("sides : " + String.join(", ", sides));
		System.out.println("payment : " + payment);
		System.out.println("price : " + price);
		*/
		
		// 실행 결과에 따른 응답화면 지정 (jsp 한테 위임)
		
		// 응답페이지에서 필요로 하는 데이터가 있을 경우
		// request 의 attribute 영역에 키-밸류 세트로 데이터를 미리 담아두기 (수하물 붙이기!!)
		// => request.setAttribute("키값", 밸류값);
		request.setAttribute("userName", userName);
		request.setAttribute("phone", phone);
		request.setAttribute("address", address);
		request.setAttribute("message", message);
		request.setAttribute("pizza", pizza);
		request.setAttribute("toppings", toppings);
		request.setAttribute("sides", sides);
		request.setAttribute("payment", payment);
		request.setAttribute("price", price);
		
		// 1) RequestDispatcher 객체 생성 (jsp 파일의 경로 제시)
		RequestDispatcher view = request.getRequestDispatcher("views/05_PizzaPayment.jsp");
		
		// 2) 포워딩
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
