package com.kh.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JqAjaxController1
 */
@WebServlet("/jqAjax1.do")
public class JqAjaxController1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// System.out.println("잘 호출되나...?"); // 전송 버튼 눌렀을 때 콘솔에 잘 뜸!
		
		// request.getParameter("키값"): String 타입의 밸류값
		String str = request.getParameter("input");
		
		// System.out.println("요청 시 전달값 : " + str);
		
		// 요청 처리를 다 했다는 가정 하에 응답할 데이터
		String responseData = "입력된 값: " + str + ", 길이: " + str.length();
		
		// 요청을 보냈던 곳으로 응답데이터를 돌려주기
		// (기존의 동기식 요청에서는 응답 데이터를 Servlet) 내장 객체에 실어서 페이지 통째로 돌려줌
		// => 비동기식 요청에서는 응답 데이터만 넘겨 줌
		
		// 1. 혹시라도 응답 데이터에 한글이 있을 경우 응답 데이터가 깨질 수 있기 때문에
		//    응답 데이터에 대한 mimetype과 charset을 설정해야 함
		response.setContentType("text/html; charset=UTF-8");
		// 자바 코드로 HTML 코드 실어서 응답 페이지를 자바 코드로 바꿀 때 써 봤었음!
		// 이 과정 안 해 주면 한글이 모두 물음표로 처리되는 현상 발생!
		// (RequestGetServlet.java 참고! 서버 진도 초반에 써 봤음)
		
		// 2. JSP와의 통로 열어 주기
		//    => PrintWriter 형식의 통로를 열어줌
		response.getWriter().print(responseData);
		// => 이것도 자바 코드 안에 html 코드 넣을 때 사용했던 메소드
		// 서블릿으로 화면 구성할 때 써 봄!
		
		//------- 현재 상황
		//------- index.jsp에서는 $.ajax 관련 function으로 type까지만 넣어 준 상태!
		
		//------- 메인 페이지에서 텍스트 입력 후 전송 버튼을 눌렀을 때 doget 메소드가 호출되어
		//------- 인코딩, 통로 연 뒤 응답 데이터를 넘긴 상태!
		//------- (다만 index.jsp에서 응답을 받아 주지 않은 상태이기 때문에 변화가 없음)
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
