package com.kh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class JqAjaxController2
 */
@WebServlet("/jqAjax2.do")
public class JqAjaxController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// System.out.println("잘실행되나..?");
		
		// 기존의 동기식 요청에서는
		// post방식으로 요청이 들어왔을 경우 인코딩 설정을 먼저 해 주고 나서 요청 시 전달값을 뽑았어야 했음
		// => 비동기식 요청에서는 기본 인코딩 세팅이 UTF-8이기 때문에 인코딩 설정은 생략
		
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		
		// 요청 처리를 다 했다는 가정 하에 응답할 데이터 (문자열)
		/*
		String responseData = "이름: " + name + ", 나이: " + age;
		
		// System.out.println(responseData);
		
		// 데이터 응답하기
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(responseData);
		*/
		
		// 여러 개의 데이터로 응답하고 싶다면?
		// 요청 처리를 다 했다는 가정 하에 응답할 데이터 (문자열)
		// response.setContentType("text/html; charset=UTF-8");
		// response.getWriter().print(name);
		// response.getWriter().print(age);
		// '말똥27'이 콘솔에 출력됨 => 한 개의 문자열처럼 이어져서 응답됨
		// => 즉, Ajax는 기본적으로 결과를 오로지 한 개만 응답할 수 있는 구조임!
		
		// 만약 여러 개의 응답 데이터를 넘기고 싶다면 JSON이라는 개념을 활용해야 함
		
		/*
		 * * JSON(JavaScript Object Notation) : 자바 스크립트 객체 표기법
		 * - ajax 통신 시 데이터 전송에 사용되는 포맷 형식 중 하나
		 * - JSON 처리 시 사용되는 클래스 종류는 기본적으로 자바에서 제공하지 않음 (외부 라이브러리 필요)
		 *    https://code.google.com/archive/p/json-simple/downloads
		 *    json-simple-1.1.1 jar 다운로드 후 WEB-INF/lib에 붙여 넣기
		 *    
		 * - 응답 데이터가 여러 개일 경우 JSON에서 제공하는 두 가지 형태 중 선택하여 가공
		 * 1. JSONArray[value, value, value, ...] => 자바 스크립트의 배열 형식
		 * 2. JSONObject{key:value, key:value, key:value, ...} => 자바 스크립트의 객체 형식
		 */
		
		// JSONArray 타입으로 넘기기
		/*
		JSONArray jArr = new JSONArray(); // 임포트 해 주기
		jArr.add(name);
		jArr.add(age);
		// 이 코드 작성 시 내부적으로 빈 배열 생성 후 -> ["말똥"] -> ["말똥", 27]
		// 위와 같이 차곡차곡 채워짐
		// => 자바스크립트에서의 배열은 자바의 ArrayList와 유사함 (타입 제한 X, 사이즈 제한 X, 인덱스 개념 O)
		
		// jArr을 응답 데이터로 넘기기
		// 응답할 데이터의 컨텐트 타입을 제대로 지정해야 문자열 형식으로 넘어가지 않음!
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jArr);
		*/
		
		// JSONObject 타입으로 넘기기
		JSONObject jObj = new JSONObject(); // 내부적으로 맵 계열의 빈 오브젝트 타입이 하나 만들어짐 {}
		jObj.put("name", name); // {name:"김말똥"}
		jObj.put("age", age); // {name:"김말똥", age:27}
		// => 자바 스크립트에서의 객체는 자바에서의 HashMap과 유사 (키-밸류 세트, 사이즈 제한 X, 키값 중복 X, 밸류 중복 O, 인덱스 개념 X)
		
		// jObj를 응답 데이터로 넘기기
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jObj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
