package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MemberEnrollFormController
 */
@WebServlet("/enrollForm.me")
public class MemberEnrollFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberEnrollFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 이 서블릿의 용도: 단순히 회원가입 페이지를 띄워 주는 용도
	    
	    // 내가 보여 주고자 하는 url : http://localhost:8888/jsp/enrollForm.me
	    // 실제 사용자에게 보여 주고자 하는 화면 : views/member/memberEnrollForm.jsp
	    
	    // 포워딩 방식
	    // RequestDispatcher view = request.getRequestDispatcher("views/member/memberEnrollForm.jsp");
	    // view.forward(request, response);
	    // 한 줄로 줄일 수 있음!
	    request.getRequestDispatcher("views/member/memberEnrollForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
