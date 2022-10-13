package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		 * 			  (요청 시 전달값, 요청 전송 방식, 요청자의 ip 주소 등)
		 * 			  request.getParameter() / request.getParameterValues()로 값 뽑기
		 * - response: 요청에 대해 응답할 때 필요한 객체
		 * 			       자바 코드로 응답 페이지를 만들 때 주로 사용
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
		System.out.println(loginUser);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
