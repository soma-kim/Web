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
 * Servlet implementation class NoticeDetailController
 */
@WebServlet("/detail.no")
public class NoticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 클릭했을 때의 글 번호
	    int noticeNo = Integer.parseInt(request.getParameter("nno"));
	    // System.out.println(noticeNo);
	    
	    // 조회수 증가용 서비스 먼저 호출
	    int result = new NoticeService().increaseCount(noticeNo);
	    
	    // 조회 수가 잘 증가되었다면 1 => 게시글 상세 조회 요청
	    // 아니라면 0 => 실패 처리
	    
	    if(result > 0) { // 성공
	        
	        // 게시글 상세조회 요청 후 noticeDetailView.jsp가 보여지도록 포워딩
	        Notice n = new NoticeService().selectNotice(noticeNo);
	        // selectNoticeList 아님!
	        // System.out.println(n);
	        
	        request.setAttribute("n", n);
	        
	        // 공지사항 상세보기 페이지 포워딩
	        request.getRequestDispatcher("views/notice/noticeDetailView.jsp").forward(request, response);
	        
	    } else { // 실패
	        
	        // 에러 문구를 담아서 에러 페이지로 포워딩
	        request.setAttribute("errorMsg", "공지사항 조회 실패");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	        
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
