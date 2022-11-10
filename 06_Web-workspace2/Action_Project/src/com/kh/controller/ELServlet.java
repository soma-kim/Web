package com.kh.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.model.vo.Person;

/**
 * Servlet implementation class ELServlet
 */
@WebServlet("/el.do")
public class ELServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ELServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*
		 * * 데이터들을 담을 수 있는 JSP 내장 객체 종류(Servlet Scope 내장 객체 - 타입 기준 정의)
		 * 1. ServletContext (applicationScope)
		 * 	  한 애플리케이션당 단 1개 존재하는 객체
		 *   이 영역에 데이터를 담으면 애플리케이션 전역에서 사용 가능
		 *   => 공유 범위가 가장 큼
		 * 2. HttpSession (sessionScope)
		 *   한 브라우저당 단 1개 존재하는 객체
		 *   이 영역에 데이터를 담으면 애플리케이션 전역에서 사용 가능
		 *   단, 값이 한 번 담기면 서버가 멈추거나, 브라우저가 종료되거나, 세션에 든 값이 직접 지워지기 "전"까지만 사용 가능
		 *   => 공유 범위가 다소 제한적임
		 * 3. HttpServletRequest (requestScope)
		 *   요청 및 응답 시 매번 생성되는 객체
		 *   이 영역에 데이터를 담으면 해당 request 객체를 포워딩받는 응답 jsp에서만 사용 가능(1회성)
		 *   => 공유 범위가 해당 요청에 대한 응답 jsp 단 하나뿐임!
		 * 4. PageContext (pageScope)
		 *   현재 그 jsp 페이지에서만 사용 가능
		 *   => 공유 범위가 가장 작음 (현재 그 페이지에서 값을 담을 수 있고 그 페이지에서만 값을 꺼낼 수 있음)
		 *   
		 * 위의 객체들의 값을 담을 때는 .setAttribute("키", 담고자하는데이터);
		 *           값을 꺼낼 때는 .getAttribute("키"); => Object 타입의 밸류값 리턴 (다형성 적용)
		 *           값을 지울 때는 .removeAttribute("키");
		 */
		
		// requestScope에 객체 담기 - 별도 정의 없이 바로 사용 가능
		request.setAttribute("classRoom", "D강의장");
		request.setAttribute("student", new Person("홍길동", 15, "남자"));
		
		// sessionScope에 데이터 담기
		HttpSession session = request.getSession(); // 세션 객체를 얻어낸 후 사용 가능
		session.setAttribute("academy", "컴터학원");
		session.setAttribute("teacher", new Person("김티처", 20, "여자"));
		
		// requestScope와 sessionScope에 동일한 키값으로 데이터를 담기
		// 원래 동일한 키값을 넣으면 덮어씌워지지만 이 경우 request, session으로써 하나씩 정의했으므로 중복 아님!
		request.setAttribute("scope", "request");
		session.setAttribute("scope", "session");
		
		// applicationScope에 데이터 담기
		// ServletContext application = request.getSession().getServletContext(); // 세션으로부터 객체를 얻어내는 첫 번째 방법
		ServletContext application = request.getServletContext(); // request 객체로부터 얻어내는 두 번째 방법
		application.setAttribute("scope", "application"); // 객체의 타입이 다르므로 이 키값 역시 중복 아님!
		
		// jsp 페이지로 포워딩
		request.getRequestDispatcher("views/1_EL/01_el.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
