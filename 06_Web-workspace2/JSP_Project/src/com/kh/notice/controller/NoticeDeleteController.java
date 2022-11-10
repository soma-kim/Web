package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeDeleteController
 */
@WebServlet("/delete.no")
public class NoticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 삭제하고자 하는 글번호
	    int noticeNo = Integer.parseInt(request.getParameter("nno"));
	    
	    // 서비스단으로 글 번호 넘기며 삭제 요청 및 결과 받기
	    int result = new NoticeService().deleteNotice(noticeNo);
	    
	    if(result > 0) { // 성공 => alertMsg 담기, 공지사항 리스트 페이지로 url 요청
	        
	        request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 삭제되었습니다.");
	        
	        // /jsp/list.no
	        response.sendRedirect(request.getContextPath() + "/list.no");
	        
	    } else { // 실패 = > 에러문구를 담아서 에러 페이지로 포워딩
	        
	        request.setAttribute("errorMsg" , "공지사항 삭제 실패");
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
