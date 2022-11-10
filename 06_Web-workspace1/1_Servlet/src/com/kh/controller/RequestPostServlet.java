package com.kh.controller; // 패키지 선언부

// import 선언부
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
public class RequestPostServlet extends HttpServlet { // RequestPostServlet 클래스 영역 시작
	
	// 필드부
	private static final long serialVersionUID = 1L; // 상수필드
       
	// 생성자부
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // doGet 메소드 영역 시작

		// System.out.println("잘 실행되나..?");
		
		// POST 방식으로 요청 할 경우 값을 뽑기 전에 우선 UTF-8 방식으로 인코딩 설정을 해야한다!!
		// POST 방식의 기본 인코딩 설정이 ISO-8859-1 이기 때문
		// request.setCharacterEncoding("UTF-8"); 
		request.setCharacterEncoding("UTF-8"); // 값을 뽑기 전에 무조건 실행할 것!!
		
		// 요청 시 전달된 값들은 request 의 parameter 영역에 key-value 세트로 담겨있음 (마치 Map 처럼)
		// request.getParameter("키값") : String 타입의 밸류값 리턴
		// request.getParameterValues("키값") : String[] 타입의 밸류값들이 리턴
		
		String name = request.getParameter("name"); // "홍길동" / ""
		String gender = request.getParameter("gender"); // "M" / "F" / null
		int age = Integer.parseInt(request.getParameter("age")); // "20" -> 20
		// 주의할점  "20a" 또는 "" -> NumberFormatException 발생
		String city = request.getParameter("city"); // "서울시"
		double height = Double.parseDouble(request.getParameter("height")); // "170" -> 170.0
		String[] foods = request.getParameterValues("food"); // ["한식", "중식", "일식"] / null
		
		// 출력
		System.out.println("name : " + name);
		System.out.println("gender : " + gender);
		System.out.println("age : " + age);
		System.out.println("city : " + city);
		System.out.println("height : " + height);
		// System.out.println("foods : " + foods /* .toString() */); // 주소값 출력
		
		if(foods == null) {
			System.out.println("foods : 없음");
		}
		else {
			System.out.println("foods : " + String.join(", ", foods));
			// 배열에 있는 모든 값들을 구분자를 통해서 하나의 문자열로 연이어 반환해주는 메소드
		}
		
		// 원래의 흐름
		// Controller -> Service -> DAO -> DB
		// if(result > 0) : 성공
		// else : 실패
		
		// 위의 요청 처리를 다 했다 라는 가정하에  사용자가 보게 될 응답페이지 출력
		
		// 단, 그 응답화면 (JSP) 에서 필요로 하는 데이터들을 request 객체에 담아서 보내줘야 함
		// => request 의 attribute 영역 (key-value 세트로)
		// request.setAttribute("키", 밸류);
		request.setAttribute("name", name);
		request.setAttribute("age", age);
		request.setAttribute("gender", gender);
		request.setAttribute("city", city);
		request.setAttribute("height", height);
		request.setAttribute("foods", foods);
		
		// 응답페이지 출력하는 방법
		// 1. 자바를 이용하는 방법 : Java 코드 내에 HTML 코드를 기술
		// 2. JSP 를 이용하는 방법 : HTML 내에 Java 코드를 기술
		// (Java Server Page : HTML 내에 Java 코드를 기술하여 동적인 페이지 구현 가능)
		
		// 응답 페이지를 만드는 과정을 Java 코드로 직접하는것이 아니라
		// JSP 에게 "위임"할것 (대신 응답페이지를 만들어달라고 떠넘기기)
		
		// 응답 페이지 위임을 위해 필요한 객체 : RequestDispatcher
		
		// 1) 응답하고자하는 JSP 뷰 파일을 선택하면서 객체 생성
		RequestDispatcher view = request.getRequestDispatcher("views/responsePage.jsp");
		
		// 2) 포워딩 작업
		view.forward(request, response); // url 주소는 변함이 없고 화면만 바꿔치기 됨
		
	} // doGet 메소드 영역 끝

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // doPost 메소드 영역 시작
		// TODO Auto-generated method stub
		doGet(request, response); // doGet 메소드 호출 구문
		
		// System.out.println("잘 실행되나..?");
		// form 태그에서 POST 방식으로 요청 시 이 doPost() 메소드가 호출됨
		// => 자동완성 상 어차피 doGet() 메소드를 호출하고 있기 때문에 요청 시 처리할 코드를 doGet() 메소드에 작성해도 무방
		
	} // doPost 메소드 영역 끝

} // RequestPostServlet 클래스 영역 끝
