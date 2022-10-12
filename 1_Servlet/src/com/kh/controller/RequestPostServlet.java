package com.kh.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestPostServlet
 */
@WebServlet("/test2.do") // 어노테이션
public class RequestPostServlet extends HttpServlet { // RequestPOSTServlet 클래스 영역 시작
	
	// 필드부
	private static final long serialVersionUID = 1L; // 상수 필드
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestPostServlet() { // 기본생성자
        super();
        // TODO Auto-generated constructor stub
    }

    // 메소드부
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // doGET 영역 메소드 시작
		// 자동완성 코드는 그냥 지워 버렸음!
		// doGet() 메소드를 호출해도 정말 POST 메소드가 잘 실행이 되는지 확인
		
		// System.out.println("잘 실행되나...?");
		
		// POST 방식으로 요청할 경우 값을 뽑기 전에 우선 UTF-8 방식으로 인코딩 설정을 해야 함
		// POST 방식의 기본 인코딩 설정이 ISO-8859-1이기 때문
		// request.setCharacterEncoding("UTF-8");
		
		request.setCharacterEncoding("UTF-8");
		
		// 요청 시 전달값들은 request의 parameter 영역에 key-value 세트로 담겨 있음 (마치 Map처럼)
		// request.getParameter("키값"): String 타입의 밸류값 리턴
		// request.getParameterValues("키값"): String[] 타입의 밸류값들이 리턴
		
		String name = request.getParameter("name"); // name이라는 변수에는 "홍길동" 같은 식으로 담겨 있을 것, 입력 안 했다면 빈 문자열!
		String gender = request.getParameter("gender"); // gender라는 변수에는 "M" 또는 "F" 같은 식으로 담김, 라디오 버튼이므로 입력 안 했다면 null 값
		int age = Integer.parseInt(request.getParameter("age")); // 20이 아닌 "20"이 받아와지므로 파싱해 줘야 함 
		// 주의할 점 "20a" 또는 빈 문자열 넘기면 => NumberFormatException 발생
		String city = request.getParameter("city"); // "서울시"
		double height = Double.parseDouble(request.getParameter("height")); // 170.5가 아닌 "170.5"가 담기므로 파싱해 줘야 함
		String[] foods = request.getParameterValues("food"); // ["햄버거", "만두", "떡볶이", ...], 체크박스이므로 입력 안 한다면 null 값
		
		// 출력
		System.out.println("name: " + name);
		System.out.println("gender: " + gender);
		System.out.println("age: " + age);
		System.out.println("city: " + city);
		System.out.println("height: "+ height);
		// System.out.println("foods: " + foods /* .toString() */); // 주소값 출력
		
		if(foods == null) { // NullPointerException 방지
			System.out.println("foods: 선택된 음식 없음");
		} else {
			System.out.println("foods: " + String.join(", ", foods));
			// 배열에 있는 모든 값들을 구분자를 통해서 하나의 문자열로 연이어 반환해 주는 메소드
		}
		
		// 원래의 흐름
		// Cotroller -> Service -> DAO -> DB
		// result > 0 : 성공
		// else : 실패
		
		// 위의 요청 처리를 다 했다라는 가정 하에 사용자가 보게 될 응답 페이지 출력
		
		// 단, 그 응답 화면 (JSP)에서 필요로 하는 데이터들을 request 객체에 담아서 보내 줘야 함
		// => request의 attribute 영역 (key-value 세트로)
		// request.setAttribute("키", 밸류);
		// 키값은 String 고정, 밸류 타입은 상관없음!
		
		// +++ 여담으로 수화물 부치는 과정과 비슷함 (위임하기 전에 변수의 데이터들 그대로 넘기기)
		request.setAttribute("name", name);
		request.setAttribute("age", age);
		request.setAttribute("gender", gender);
		request.setAttribute("city", city);
		request.setAttribute("height", height);
		request.setAttribute("foods", foods);
		
		// 응답 페이지 출력하는 방법
		// 1. 자바를 이용하는 방법: Java 코드 내에 HTML 코드를 기술
		// 2. JSP를 이용하는 방법: HTML 내에 Java 코드를 기술
		// (Java Server Page: HTML 내에 Java 코드를 기술하여 동적인 페이지 구현 가능)
		
		// 응답 페이지를 만드는 과정을 Java 코드로 직접 하는 것이 아니라
		// JSP에게 "위임"할 것(대신 응답 페이지 만들어 달라고 떠넘기기)
		
		// 응답 페이지 위임을 해서 필요한 객체: RequsetDispatcher
		
		// 1) 응답하고자 하는 JSP 뷰 파일을 선택하면서 객체 생성
		RequestDispatcher view = request.getRequestDispatcher("views/responsePage.jsp");
		
		// 2) 포워딩 작업
		view.forward(request, response); // url 주소는 변함이 없고 화면만 바꿔치기 됨
		
		
	} // doGET 메소드 끝

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // doPOST 영역 메소드 시작
		// TODO Auto-generated method stub
		doGet(request, response); // doGET 메소드 호출 구문
		
		// System.out.println("잘 실행되나...?");
		// form 태그에서 POST 방식으로 요청 시 이 doPost() 메소드가 호출됨
		// => 자동완성상 어차피 doGet() 메소드를 호출하고 있기 때문에 doGet() 메소드에 작성해도 무방
		
	} // doPOST 메소드 영역 끝

} // RequestPostServlet 클래스 영역 끝
