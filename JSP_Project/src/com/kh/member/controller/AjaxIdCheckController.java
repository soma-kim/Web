package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;

/**
 * Servlet implementation class AjaxIdCheckController
 */
@WebServlet("/idCheck.me")
public class AjaxIdCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxIdCheckController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 요청 시 전달값 뽑기
	    // checkId: 검사하고자 하는 아이디
	    String checkId = request.getParameter("checkId"); // checkId라는 변수에 든 value값 뽑기
	    
	    // 전달값을 서비스로 넘겨서 요청 처리 후 결과 받기
	    int count = new MemberService().idCheck(checkId); // 중복된 아이디가 있다면 1, 없다면 0
	    
	    // 조건에 따른 응답 데이터 넘겨 주기
	    // => 응답 데이터 1개만 넘길 것이기 때문에 굳이 JSON을 쓰지 않아도 됨!
	    response.setContentType("text/html; charset=UTF-8");
	    
	    if(count > 0) { // 이미 사용 중인 아이디가 있을 경우 => 사용 불가능 ("NNNNN")
	        response.getWriter().print("NNNNN");
	    } else { // 존재하는 아이디가 없을 경우 => 사용 가능 ("NNNNY")
	        response.getWriter().print("NNNNY");

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
