package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // 회원 가입 기능 구현
	    
	    // 1) 인코딩 변경
	    request.setCharacterEncoding("UTF-8");
	    
	    // 2) 요청 시 전달값들을 뽑아서 변수 및 객체에 기록하기
	    // 뽑아야 할 값
	    // userId: 아이디 (필수입력)
	    String userId = request.getParameter("userId");
	    // userPwd: 비밀번호 (필수입력)
	    String userPwd = request.getParameter("userPwd");
	    // userName: 이름 (필수입력)
	    String userName = request.getParameter("userName");
	    // phone: 휴대폰번호
	    String phone = request.getParameter("phone"); // 빈 문자열이 올 수도 있음
	    // email: 이메일 주소
	    String email = request.getParameter("email"); // 빈 문자열이 올 수도 있음
	    // address: 주소
	    String address = request.getParameter("address"); // 빈 문자열이 올 수도 있음
	    // interest: 관심분야 (배열)
	    String[] interestArr = request.getParameterValues("interest");
	                                                    // ["운동", "등산] / null
	    
	    // String[] --> String
	    // ["운동", "등산"] --> "운동, 등산"
	    String interest = "";
	    
	    if(interestArr != null) {
	        interest = String.join(", ", interestArr);
	    }
	    
	    // 매개변수 생성자를 이용해서 Member 객체에 담기
	    Member m = new Member(userId, userPwd, userName, phone, email, address, interest);
	    
	    // 3) 전달값을 Service에 전달하면서 요청 처리
	    int result = new MemberService().insertMember(m);
	    
	    // 4) 처리 결과를 가지고 사용자가 보게 될 응답페이지를 지정
	    if(result > 0) { // 성공 => 성공 메시지를 담아 메인페이지로 요청
	        
           HttpSession session = request.getSession();
           session.setAttribute("alertMsg", "회원가입에 성공했습니다.");
           
           // URL 재요청 방식
           // http://localhost:8888/jsp
           // response.sendRedirect("/jsp"); // 하드코딩 방식
           response.sendRedirect(request.getContextPath()); // 동적 바인딩
	           
	    } else { // 실패 => 실패 메시지를 담아서 에러 페이지로 포워딩
	        
	        request.setAttribute("errorMsg", "회원가입에 실패했습니다.");
	        
	        // 포워딩 방식
	        // 지금 경로: http://localhost:8888/jsp/insert.me
	        // 파일 경로: WebContent/views/common/errorPage.jsp
	        RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
	        view.forward(request, response);
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
