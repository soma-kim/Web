package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MyPageController
 */
@WebServlet("/myPage.me")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // 마이페이지 띄우기 => 로그인한 상태일 경우에만 보여지게끔
	    // 로그인 전 요청 시 : 알람 메시지를 담아 메인 페이지로 요청
	    // 로그인 후 요청 시 : 마이페이지를 포워딩
	    
	    // 현재 로그인한 상태인지 검사하는 방법 : session이 loginUser 키값이 존재하는지 검사!
	    HttpSession session = request.getSession();
	    
	    if(session.getAttribute("loginUser") == null) { // 로그인 전
	        session.setAttribute("alertMsg", "로그인 후 이용 가능한 서비스입니다.");
	        response.sendRedirect(request.getContextPath());
	        
	    } else {
	        // 포워딩 방식 이용
	        // 현재 경로: http://localhost:8888/jsp/myPage.me
	        // 파일 경로: WebContent/views/member/myPage.jsp
	        request.getRequestDispatcher("views/member/myPage.jsp").forward(request, response);
	    }
	 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
