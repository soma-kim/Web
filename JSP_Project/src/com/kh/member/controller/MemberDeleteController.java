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
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // 1) 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    
	    // 2) 요청 시 전달값 뽑아서 밑 변수나 객체에 담기
	    // 뽑아야 할 값
	    // userPwd: 비밀번호
	    String userPwd = request.getParameter("userPwd");
	    
	    // 로그인한 회원의 정보를 알아내는 방법
	    // 1. input type="hidden"으로 애초에 넘기기
	    // 2. session에 담겨 있는 회원의 정보 뽑기
	    // => 모두 가능하나 이 기능에서는 session 방법을 써 볼 것!
	    //  비밀번호는 유니크값이 아니므로 회원 정보를 알아낼 수 없음
	    // => 추가적으로 필요한 값: userId
	    
	    // 세션 객체 얻어내기 => loginUser 키값에 해당되는 밸류로부터 아이디값만 getter로 뽑기
	    HttpSession session = request.getSession();
	    String userId = ((Member)session.getAttribute("loginUser")).getUserId();
	    
	    // Member 객체에 담기 => 몇 개 안 되니까 setter 메소드로
	    Member m = new Member();
	    m.setUserId(userId);
	    m.setUserPwd(userPwd);
	    
	    // 3) Service단으로 요청 및 결과 받기
	    // 해당 아이디와 일치하는 값은 단 1개뿐일 것이므로 int형 변수로 받아 줌
	    int result = new MemberService().deleteMember(m);
	    
	    // 4) 결과에 따른 응답 페이지 지정
	    if(result > 0) { // 성공 => 성공 문구를 담아서 메인 페이지로 url 요청 (단, 로그아웃되도록)
	        
	        session.setAttribute("alertMsg", "성공적으로 회원 탈퇴되었습니다. 그동안 이용해 주셔서 ㅠㅠ... 고마워요...");
	        
	        // invalidate() 메소드를 이용해서 세션을 무효화시킴으로써 로그아웃 처리 가능!
	        // session.invalidate();
	        // => invalidate() 메소드를 사용 시 세션 자체가 만료되어 alert가 되지 않기 때문에 (값을 담을 그릇 자체를 없애 버린 꼴)
	        // 즉, 이 방법을 사용하면  탈퇴 성공 시에도 알림창이 뜨지 않음! => 성공 문구를 담았던 그릇 자체를 없애 버리는 꼴
	        // 이 경우에는 removeAttribute("키값")을 이용하여 현재 로그인한 사용자의 정보를 지워 주는 방식으로 로그아웃시킴
	        session.removeAttribute("loginUser");
	        
	        response.sendRedirect(request.getContextPath());
	        
	    } else { // 실패 => 에러 문구를 request에 담아서 (errorMsg 키값) 에러페이지로 포워딩
	        
	        request.setAttribute("errorMsg", "비밀번호 불일치! 회원 탈퇴에 실패했습니다.");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	        // forward(request, response): 나 대신에 응답 좀 해 줘~ 라고 위임시키는 개념
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
