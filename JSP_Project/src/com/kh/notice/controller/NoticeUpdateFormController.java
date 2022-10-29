package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeUpdateFormController
 */
@WebServlet("/updateForm.no")
public class NoticeUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 해당 게시글 번호 뽑기
	    int noticeNo = Integer.parseInt(request.getParameter("nno")); // "1" -> 1
	    
	    // 수정 페이지에서 기존의 제목과 기존의 내용이 보여지게끔 다시  조회
	    Notice n = new NoticeService().selectNotice(noticeNo);
	    // 글번호, 글제목, 글내용, 작성자 아이디, 작성일
	    
	    request.setAttribute("n", n);
	    
	    // 공지사항 수정 페이지 포워딩
	    request.getRequestDispatcher("views/notice/noticeUpdateForm.jsp").forward(request, response);
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
