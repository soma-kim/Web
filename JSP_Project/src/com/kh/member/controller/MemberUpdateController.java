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
 * Servlet implementation class MemberUpdateController
 */
@WebServlet("/update.me")
public class MemberUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // 1) 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    
	    // 2) 요청 시 전달값들을 뽑아서 변수 및 객체에 담기
	    // 뽑아야 할 값
	    // userId: 아이디값 => 변경 불가한 값 (unique 제약조건에 의해 구분 용도)
	    String userId = request.getParameter("userId");
	    // userName: 이름
	    String userName = request.getParameter("userName");
	    // phone: 전화번호
	    String phone = request.getParameter("phone");
	    // email: 이메일
	    String email = request.getParameter("email");
	    // address: 주소
	    String address = request.getParameter("address");
	    // interest: 관심분야(배열)
	    String[] interestArr = request.getParameterValues("interest");
	    
	    // 배열 형태의 관심 분야들을 하나의 문자열로 연잇는 작업 추가
	    String interest = "";
	    
	    if(interestArr != null) {
	        interest = String.join(", ", interestArr);
	    }
	    
	    // Member 타입의 객체
	    Member m = new Member(userId, userName, phone, email, address, interest);
	    
	    // 3) 서비스단으로 요청 보내고 결과 받기
	    Member updateMem = new MemberService().updateMember(m);
	    
	    // 4) 돌려받은 처리 결과를 가지고 사용자가 보게 될 응답 페이지를 지정
	    if(updateMem == null) { // 실패 => 에러 문구를 담아서 에러페이지로 포워딩
	        
	        request.setAttribute("errorMsg", "회원 정보 수정에 실패했습니다.");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);    
	    } else { // 성공 => 갱신된 회원의 정보를 session에 덮어씌우기, 알람 문구 담기, 마이페이지로 url 재요청
	        
	        HttpSession session = request.getSession();
	        session.setAttribute("loginUser", updateMem); // 맵처럼 같은 키값은 존재 불가하므로 덮어쓰여짐
	        session.setAttribute("alertMsg", "성공적으로 회원 정보를 수정했습니다.");
	        
	        // 마이페이지로 url 재요청
	        // localhost:8888/jsp/myPage.me
	        response.sendRedirect(request.getContextPath() + "/myPage.me");
	        
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
