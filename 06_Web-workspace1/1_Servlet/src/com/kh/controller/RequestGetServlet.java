package com.kh.controller; // 패키지 선언부

// import 선언부
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestGetServlet
 */
@WebServlet("/test1.do") // 어노테이션
public class RequestGetServlet extends HttpServlet { // RequestGetServlet 클래스 영역 시작
	
	// 필드부
	private static final long serialVersionUID = 1L; // 상수필드
	
    // 생성자부
	// 생성자 : 클래스명과 이름이 같고 반환형이 없는 일종의 메소드
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestGetServlet() { // 매개변수가 없는 기본생성자
        super();
        // TODO Auto-generated constructor stub
    }

    // 메소드부
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // doGet() 메소드 영역 시작

		// System.out.println("잘 실행되나..?");
		
		/*
		 * form 태그에서 GET 방식으로 요청했다면 이 doGet() 메소드가 호출됨
		 * 
		 * 첫번째 매개변수인 HttpServletRequest request 매개변수에는 요청 시 전달된 내용물들이 담김
		 * (사용자가 입력한 값, 요청 전송 방식, 요청한 사용자의 ip 주소 등등)
		 * 
		 * 두번째 매개변수인 HttpServletResponse response 매개변수에는 요청을 처리 후 응답을 할때 사용하는 객체
		 * (응답시 필요한 메소드들을 호출)
		 * 
		 * => request 객체로부터 요청시 전달값들을 뽑아 처리 후에 response 객체로 응답
		 * 
		 * 우선, 요청을 처리하기 위해 요청시 전달된 값 (사용자가 입력한 값) 들을 뽑는다.
		 * request 객체의 parameter 라는 영역 안에 사용자가 입력한 값들이 key-value 세트로 담겨있음 (name-value)
		 * 
		 * 따라서 request 의 parameter 영역으로부터 전달된 데이터를 뽑는 메소드를 이용한다.
		 * - request.getParameter("키값") : String 형태의 밸류값을 리턴
		 * => 무조건 문자열 형으로 반환되기 때문에 다른 자료형으로 변경하려면 파싱해야함
		 * - request.getParameterValues("키값") : String[] 형태의 밸류값들을 리턴
		 * => 하나의 key 값으로 여러개의 value 값들을 받는 경우 (체크박스) 문자열 배열 형으로 반환 가능
		 */
		
		String name = request.getParameter("name"); // "홍길동" / "" (텍스트 상자가 빈 경우 빈 문자열이 넘어감)
		
		String gender = request.getParameter("gender"); // "M" / "F" / null (라디오 버튼의 경우 체크된 것이 하나도 없다면 빈 문자열이 아닌 null 값이 넘어감)
		
		// String age = request.getParameter("age"); // "20" (무조건 문자열로 넘어감)
		int age = Integer.parseInt(request.getParameter("age")); // Wrapper 클래스를 이용해서 파싱
																 // "20" -> 20
																 // "20a" -> NumberFormatException 발생 (주의할것)
		
		String city = request.getParameter("city"); // "서울시" / "경기도" / ..
		
		// String height = request.getParameter("height"); // "170" (무조건 문자열로 넘어감)
		double height = Double.parseDouble(request.getParameter("height")); // "170" -> 170.0
		
		// 체크박스와 같은 복수개의 정보를 받을 때는 배열로 받아야 함
		String[] foods = request.getParameterValues("food"); // ["한식", "중식", "일식"] / null (체크박스의 경우 또한 체크된 것이 하나도 없을 때 null 이 넘어옴)
		
		System.out.println("name : " + name);
		System.out.println("gender : " + gender);
		System.out.println("age : " + age);
		System.out.println("city : " + city);
		System.out.println("height : " + height);
		// System.out.println("foods : " + foods /* .toString() */); // 주소값
		
		if(foods == null) {
			System.out.println("foods : 좋아하는 음식이 없습니다.");
		}
		else {
			/*
			for(int i = 0; i < foods.length; i++) {
				System.out.println(foods[i]);
			}
			*/
			System.out.println("foods : " + String.join(", ", foods));
			// String.join("구분자", 배열명)
			// 배열에 있는 모든 값들을 구분자를 통해서 하나의 문자열로 연이어서 반환해주는 메소드
		}
		
		// 이 뽑아낸 값들을 가지고 VO 객체로 가공 후 Service 단으로 요청 처리해야함 (DB 와 상호작용)
		// 보통의 흐름 : Service 의 메소드 호출 시 뽑은 값들을 VO 로 가공하여 전달 -> DAO 호출 -> DB SQL 문 실행 -> 결과 반환
		
		// DB 가 있다는 가정하에 아래의 구문이 실행됬을 거임!!
		// int result = new MemberService().insertMember(m); // 성공 : 1, 실패 : 0
		
		// if(result > 0) : 성공시에는 성공 화면을, 실패시에는 실패 화면을 호출
		// Service 와 DAO 단은 앞으로 거의 변동이 없음 => View 와 Controller 단의 소스코드만 변경
		
		// 위와 같은 요청처리를 다 했다는 가정 하에 사용자가 보게될 응답페이지를 만들어서 전달
		
		// 요청에 대한 응답 페이지 반환하기
		// 1. 자바를 이용하는 방법 : Java 코드 내에 HTML 코드를 기술
		// 2. JSP 를 이용하는 방법 : HTML 코드 내에 Java 코드를 기술 
		
		// * 자바를 이용하는 방법
		// 장점 : Java 코드 내에 작성하기 때문에 반복문이나 조건문, 유용한 메소드들을 활용 가능하다.
		// 단점 : 복잡, 혹시라도 후에 HTML 코드를 수정할 경우 Java 코드를 건들여야함
		//		 수정된 내용을 다시 반영하고자 한다면 서버를 재실행 (restart) 해줘야함
		
		// => response 객체를 통해 사용자에게 html (응답화면) 전달
		// 1) 이제부터 내가 출력할 내용은 문서형태의 html 이고 인코딩 방식은 utf-8 이라는 것을 지정
		response.setContentType("text/html; charset=UTF-8");
		
		// 2) 응답하고자 하는 사용자 (요청했던 사용자) 와의 스트림 (클라이언트와의 통로) 생성
		PrintWriter out = response.getWriter();
		
		// 3) 생성된 스트림을 통해 응답 html 구문을 한줄씩 출력
		// print, println, printf
		out.println("<html>");
		
		out.println("<head>");
		
		out.println("<style>");
		out.println("h2 {color:red;}");
		out.println("#name {color:orange;}");
		out.println("#age {color:yellow;}");
		out.println("#city {color:green;}");
		out.println("#height {color:blue;}");
		out.println("#gender {color:navy;}");
		out.println("li {color:purple;}");
		out.println("</style>");
		
		out.println("</head>");
		
		out.println("<body>");
		
		out.println("<h2>개인정보 응답 화면</h2>");
		
		// out.println("<span id='name'>" + name + "</span> 님은 ");
		out.printf("<span id='name'>%s</span> 님은 ", name);
		out.printf("<span id='age'>%d</span>살이며, ", age);
		out.printf("<span id='city'>%s</span> 에 사는, ", city);
		out.printf("<span id='height'>%.1f</span>cm 이고, ", height);
		
		out.print("성별은 ");
		
		if(gender == null) {
			out.print("선택을 안했습니다. <br>");
		}
		else {
			if(gender.equals("M")) {
				out.print("<span id='gender'>남자</span> 입니다. <br>");
			}
			else {
				out.print("<span id='gender'>여자</span> 입니다. <br>");
			}
		}
		
		out.print("좋아하는 음식은 ");
		
		if(foods == null) {
			out.print("없습니다.");
		}
		else {
			out.print("<ul>");
			
			for(int i = 0; i < foods.length; i++) {
				out.printf("<li>%s</li>", foods[i]);
			}
			
			out.print("</ul>");
		}
		
		out.println("</body>");
		out.println("</html>");
		
	} // doGet() 메소드 영역 끝

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // doPost() 메소드 영역 시작
		// TODO Auto-generated method stub
		doGet(request, response); // doGet() 메소드 호출중
	} // doPost() 메소드 영역 끝

} // RequestGetServlet 클래스 영역 끝
