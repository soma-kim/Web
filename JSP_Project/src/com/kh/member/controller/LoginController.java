package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*
         * <HttpServletRequest 객체와 HttpServletResponse 객체>
         * - request: 서버로 요청할 때의 정보들이 담겨 있음
         *            (요청 시 전달값, 요청 전송 방식, 요청자의 ip 주소 등)
         *            request.getParameter() / request.getParameterValues()로 값 뽑기
         * - response: 요청에 대해 응답할 때 필요한 객체
         *                 자바 코드로 응답 페이지를 만들 때 주로 사용
         * 
         * <GET 방식과 POST 방식>
         * - GET: 사용자가 입력한 값이 url에 노출 / 데이터의 길이 제한 / 대신 즐겨찾기가 편리
         * - POST: 사용자가 입력한 값이 url에 노출 X / 데이터의 길이 제한 X / 대신 즐겨찾기가 불편 / Timeout이 존재
         * 
         */
        
        // 1) 인코딩 처리해야 함 (post 방식일 경우)
        request.setCharacterEncoding("UTF-8");
        
        // 2) 요청 시 전달값을 꺼내기 (request의 parameter 영역으로부터)
        // 요청 시 전달값을 뽑아서 변수에 담기!
        // request.getParameter("키값"): String 타입의 밸류값 1개 리턴
        // request.getParameterValues("키값"): Strin[] 타입의 밸류값 여러 개가 묶여서 리턴
        
        // 요청 시 리턴값
        // userId: 아이디값
        String userId = request.getParameter("userId"); // "user01"
        // userPwd: 비밀번호값
        String userPwd = request.getParameter("userPwd"); // "password01"
        // saveId : 아이디 저장 여부에 대한 체크값
        String saveId = request.getParameter("saveId"); // 체크를 했다면 "y", 하지 않았다면 null 값이 넘어옴
        
        if(saveId != null && saveId.equals("y")) {
            
            // 아이디를 저장하겠다
            // => userId라는 키값으로 넘겨받았던 아이디값을 쿠키로 저장
            
            /*
             * * Cookie: 브라우저에서 사용되는 일종의 저장소 개념 (브라우저에서 키-밸류 세트로 값을 저장하고 있음)
             *           사용자가 사용하고 있는 서버에서 만들어져서 사용자의 컴퓨터 (브라우저)에 저장하는 정보
             *           주로 보안과 관련 없는 기능을 다룰 때 사용
             *           
             * * 쿠키 생성
             * - 쿠키는 객체를 생성한 다음 응답 정보에 같이 첨부해서 브라우저로 넘겨야지만 사용 가능
             * - 쿠키 생성 시 name(키값), value(밸류값) 세트로 반드시 지정해야 함
             * - name, value는 모두 문자열이어야만 함
             * - name이 중복될 경우 덮어씌워짐
             * - expires(max age - 만료기간)은 초 단위로 작성, 필수 아닌 옵션이지만 작성하는 것을 권장!
             * 
             * [ 표현법 ]
             * Cookie cookie = new Cookie("name", "value");
             * cookie.setMaxAge(초단위시간);
             */
            
            Cookie cookie = new Cookie("saveId", userId); // "saveId" - "user01"
            
            //                     1분          ==        60초
            //        1시간 ==      60분          ==      60 * 60초
            // 하루 == 24시간 == 1 * 24 * 60분 == 1 * 24 * 60 * 60초
            // 이틀 == 48시간 == 2 * 24 * 60분 == 2 * 24 * 60 * 60초
            cookie.setMaxAge(1 * 24 * 60 * 60); // 만료기간 1일 (초단위 작성)
            
            // 쿠키를 브라우저로 넘기기 => 응답 정보에 첨부함 (response 객체 사용)
            response.addCookie(cookie);
            
        } else {
            
            // 아이디를 저장하지 않겠다
            // => 아이디를 저장하고 있었던 쿠키 자체를 삭제
            // (쿠키를 삭제시키지 않으면 만료일까지 계속 살아 있기 때문)
            
            /*
             * * 쿠키 삭제
             * - 쿠키 삭제 구문은 따로 명령어 없음
             * - 그 대신 같은 키값으로 쿠키를 하나 생성하고 (덮어씌워짐), 그 쿠키의 만료 시간을 0으로 세팅 
             */
            
            Cookie cookie = new Cookie("saveId", userId);
            cookie.setMaxAge(0); // 0초
            response.addCookie(cookie);
            
        }
        
        // 주의사항!
        // 키값을 제시 시 오타가 나면 없는 키값을 찾는 꼴이기 때문에 무조건 null이 리턴됨!
        
        // 여기서 콘솔에 잘 뜨는지 테스트 한 것이기 때문에 주석 처리
        // System.out.println(userId);
        // System.out.println(userPwd);
        
        // 3) 요청 시 전달값들을 VO 객체로 가공하기
        Member m = new Member();
        m.setUserId(userId);
        m.setUserPwd(userPwd);
        
        // 4) 가공한 VO 객체를 해당 요청을 처리하는 서비스 클래스의 메소드로 넘기기
        Member loginUser = new MemberService().loginMember(m);
        
        // 5) 처리된 결과를 가지고 사용자가 보게 될 응답 뷰 지정
        // System.out.println(loginUser);

		/*
		 * * 응답 페이지에 전달할 값이 있다면 값을 어딘가에 담아야 함
		 * 	 (담아 줄 수 있는 Servlet Scope 내장객체 4종류)
		 * 1) application : application 객체에 담은 데이터는 웹 애플리케이션 전역에서 다 꺼내 쓸 수 있음
		 * 2) session : session 객체에 담은 데이터는 웹 애플리케이션 전역에서 다 꺼내 쓸 수 있음
		 * 				단, 한 번 담은 데이터는 내가 직접 지우기 전까지 쓸 수 있음
		 * 					한 번 담은 데이터는 서버가 멈추기 전까지 쓸 수 있음
		 * 					한 번 담은 데이터는 브라우저가 종료되기 전까지 쓸 수 있음
		 * 3) request : request 객체에 담은 데이터는 해당 요청에 대한 응답 페이지에서만 사용 가능함
		 * 4) page : 해당 jsp 페이지에서만 데이터를 담고 꺼내 쓸 수 있음
		 * 
		 * => session과 request를 주로 많이 씀
		 * 
		 * 공통적으로 데이터를 담고자 한다면 request.setAttribute("키", 밸류);
		 *         데이터를 꺼내고자 한다면 request.getAttribute("키"); : Object 타입의 밸류 리턴
		 *         데이터를 지우고자 한다면 request.removeAttribute("키");
		 *         
		*/
		
		if(loginUser == null) { // 로그인 실패 => 에러 문구를 담아서 에러 페이지로 응답
		    
		    // 응답 페이지에서 필요로 하는 데이터
		    request.setAttribute("errorMsg", "로그인에 실패했습니다.");
		    
		    // 응답 페이지 지정 => RequestDispatcher 객체 생성 후 포워딩
		    RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
		    view.forward(request, response);
		    
		    /*
		     * 포워딩 방식: 해당 url 경로로 선택된 뷰가 보여질 뿐
		     *          url은 절대 변경되지 않음 (요청했을 때 이 Servlet의 url이 주소창에 그대로 남아 있음)
		     */
		    
		} else { // 로그인 성공 => 응답 페이지에 loginUser 데이터 전달, 메인 페이지로 응답
		    
		    // 응답 페이지에서 필요로 하는 데이터
		    // 로그인한 회원의 정보를 로그아웃하기 전까지 계속 가져다 써야 함
		    // (글 작성, 마이페이지, 글 수정, 댓글 작성, ...)
		    // => session 객체에 담기
		    
		    // Servelt에서 JSP 내장 객체인 session에 접근하고자 한다면
		    // 우선 session 객체 (HttpSession)를 얻어와야 함
		    // => request 객체로부터 getSession() 메소드를 통해
		    HttpSession session = request.getSession();
		    session.setAttribute("loginUser", loginUser);
		    
		    // 성공 메시지도 담기
		    session.setAttribute("alertMsg", "성공적으로 로그인되었습니다.");
		    
		    /*
		     * 응답 페이지 지정 방식
		     * 1. 포워딩 방식: 해당 선택된 jsp가 보여질 뿐 url 주소는 여전히 이 서블릿 url 매핑값으로 지정되어 있음
		     *             http://localhost:8888/jsp/login.me
		     *             
		     * 2. url 재요청 방식 (sendRedirect 방식)
		     *    : 자바 코드로 url 주소를 요청하는 방식 (즉, 새로고침의 개념)
		     *      자바스크립트 때 location.href = "~~~", 와 같은 원리
		     */
		    // response.sendRedirect("/jsp"); // 내가 요청한 url 주소로 이동하겠음
		    response.sendRedirect(request.getContextPath());
		    
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
