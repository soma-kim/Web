package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdatePwdController
 */
@WebServlet("/updatePwd.me")
public class MemberUpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdatePwdController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // 1) 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    
	    // 2) 요청 시 전달값을 뽑아서 변수 및 객체에 담기
	    // 뽑아야 할 값
	    // userPwd : 기존 비밀번호
	    String userPwd = request.getParameter("userPwd");
	    // updatePwd: 새로운 비밀번호
	    String updatePwd = request.getParameter("updatePwd");
	    // checkPwd는 자바스크립트로 이미 유효성 검사를 했기 때문에 굳이 넘겨받지 않음!
	    
	    // 추가적으로 필요한 값: userId
	    // 로그인한 회원의 정보를 알아내는 방법
	    // 1. input type="hidden"으로 애초에 요청 시 숨겨서 전달
	    // 2. session 영역에 담겨 있는 회원 객체로부터 뽑기
	    
	    String userId = request.getParameter("userId");
	    
	    Member m = new Member();
	    m.setUserId(userId); // 해당 회원의 아이디값
	    m.setUserPwd(userPwd); // 해당 회원의 기존 비밀번호값
	    // 새로 바꿀 비밀번호를 담을 필드가 존재하지 않기 때문에 바꿀 비밀번호는 따로 객체로 가공하지 않음
	    
	    // 3) Service단으로 요청 시 전달값을 넘기면서 요청 처리, 결과 리턴받기
	    Member updateMem =  new MemberService().updatePwdMember(m, updatePwd);
	    
	    HttpSession session = request.getSession();
	    
	    // 4) 결과에 따른 응답페이지 지정
	    if(updateMem == null) { // 실패 => 실패 문구를 alertMsg 키값으로 보내기 => 마이페이지 url 요청
	        session.setAttribute("alertMsg", "비밀번호 변경에 실패했습니다.");
	    } else { // 성공 => 갱신된 회원의 정보를 loginUser라는 키값에 덮어씌우기, 성공 문구를 alertMsg 키값으로 보내기 => 마이페이지 url 요청
	        session.setAttribute("loginUser", updateMem);
	        session.setAttribute("alertMsg", "성공적으로 비밀번호가 변경되었습니다.");
	    }
	    
	    response.sendRedirect(request.getContextPath() + "/myPage.me");
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}