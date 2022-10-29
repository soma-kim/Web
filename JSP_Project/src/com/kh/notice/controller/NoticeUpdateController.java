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
 * Servlet implementation class NoticeUpdateController
 */
@WebServlet("/update.no")
public class NoticeUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    
	    // nno: 글번호 => 어느 글을 수정할 것인지
	    int noticeNo = Integer.parseInt(request.getParameter("nno"));
	    // title: 글 제목 => 바꿀 제목
	    String noticeTitle = request.getParameter("title");
	    // content: 글 내용 => 바꿀 내용
	    String noticeContent = request.getParameter("content");
	    
	    Notice n = new Notice();
	    n.setNoticeNo(noticeNo);
	    n.setNoticeTitle(noticeTitle);
	    n.setNoticeContent(noticeContent);
	    
	    int result = new NoticeService().updateNotice(n);
	    
	    if(result > 0) { // 성공 => session에 alertMsg 담기, 해당 게시글의 상세조회 페이지로 url 요청
	        
	        request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 수정되었습니다.");
	        
	        // /jsp/detail.no?nno=해당글번호
	        response.sendRedirect(request.getContextPath() + "/detail.no?nno=" + noticeNo);
	        
	    } else { // 실패
	        
	        request.setAttribute("errorMsg", "공지사항 수정 실패");
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
