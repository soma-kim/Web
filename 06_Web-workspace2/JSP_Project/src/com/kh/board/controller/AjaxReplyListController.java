package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Reply;

/**
 * Servlet implementation class AjaxReplyListController
 */
@WebServlet("/rlist.bo")
public class AjaxReplyListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxReplyListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    // 게시글 번호 뽑기
	    int boardNo = Integer.parseInt(request.getParameter("bno")); // request.getParameter()는 기본이 문자열이기 때문에 파싱 해 줌
	    
	    // 게시글 번호를 서비스로 전달하면서 요청 처리 후 결과 받기
	    ArrayList<Reply> list = new BoardService().selectReplyList(boardNo);
	    
	    // GSON을 이용해서 응답 => ArrayList를 자바 스크립트의 배열 형태로 변환
	    response.setContentType("application/json; charset=UTF-8");
	    new Gson().toJson(list, response.getWriter()); // 응답할 데이터, 데이터를 넘길 통로 객체를 차례로 넘김

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
