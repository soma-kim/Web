package com.kh.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeListController
 */
@WebServlet("/list.no")
public class NoticeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 공지사항 전체 리스트 조회 후 조회 결과를 담아서 응답 페이지로 포워딩
	    
	    // Service 단으로 요청 보낸 후 결과 받기
	    ArrayList<Notice> list = new NoticeService().selectNoticeList();
	    
	    // 응답 페이지에 데이터를 보내기 전에 한번 출력
	    // System.out.println(list);
	    
	    // 응답 페이지에서 필요로 하는 데이터를 
	    request.setAttribute("list", list);
	    
	    // 공지사항 리스트 페이지를 포워딩
	    request.getRequestDispatcher("/views/notice/noticeListView.jsp").forward(request, response);
	    
	    
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
