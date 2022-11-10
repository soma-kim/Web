package com.kh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.model.vo.Member;

/**
 * Servlet implementation class JqAjaxController3
 */
@WebServlet("/jqAjax3.do")
public class JqAjaxController3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 요청 시 전달값인 회원번호 (no) 뽑기
		int memberNo = Integer.parseInt(request.getParameter("no"));
		
		// 데이터를 조회했다라는 가정 하에 간단하게 Member 객체 구성
		Member m = new Member(memberNo, "고길동", 50, "남");
		
		// 만들어진 객체를 응답 데이터로 넘기기
		// response.setContentType("text/html; charset=UTF-8");
		// 알게 모르게 내부적으로 toString() 메소드가 호출돼서 문자열로 응답이 넘어감
		// response.getWriter().print(m /* .toString() */);
		/*
		// { 속성명 : 속성값, 속성명 : 속성값, ...} => JSONObject
		JSONObject jObj = new JSONObject(); // {}
		jObj.put("memberNo", m.getMemberNo()); // {memberNo:30}
		jObj.put("memberName", m.getMemberName()); // {memberNo:30, memberName:"고길동"}
		jObj.put("age", m.getAge()); // {memberNo:30, memberName:"고길동", age:50}
		jObj.put("gender", m.getGender()); // {memberNo:30, memberName:"고길동", age:50, gender:"남"}
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jObj);
		*/
		
		// 근데 필드 많아지면 언제 한땀한땀 1줄씩 코드 만들면서 넣고 있을래요,,?
		// 필드가 많아질 때 알아서 하나하나 넣어 주는 라이브러리가 있음!
		
		// GSON: Google JSON을 뜻함
		// GSON 외부 라이브러리를 연동해야지만 사용 가능!
		// https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.2
		response.setContentType("application/json; charset=UTF-8");
		
		// Gson gson = new Gson(); // lib 경로에 다운로드 한 gson.jar 복붙 후 임포트 해 주기
		// toJson(응답할객체, 응답할스트림객체);
		// gson.toJson(m, response.getWriter());
		// => response.getWriter()라는 통로로 m 이라는 객체를 응답 데이터로 넘길 거야
		// 단, 변환 시 전달되는 키값은 VO 객체의 각 필드값으로 자동으로 잡혀 가공 후 넘겨 줌!
		
		new Gson().toJson(m, response.getWriter()); // 한 줄로도 표현 가능!
		
		// VO 객체 하나만 응답 시 JSONObject 타입으로 만들어져서 응답
		// ArrayList 응답 시 JSONArray 타입으로 만들어져서 응답
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}