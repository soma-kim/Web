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
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 1) 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    
	    // 2) 요청 시 전달값을 뽑아서 변수 및 객체에 담기
	    // 뽑아야 할 값
	    // title: 글 제목
	    String noticeTitle = request.getParameter("title");
	    // content : 글 내용
	    String noticeContent = request.getParameter("content");
	    // 추가적으로 필요한 데이터: 작성자의 회원번호 (userNo)
	    String userNo = request.getParameter("userNo"); // "1" => 문자열로써의 회원번호 받아냄
	    // noticeWriter 필드는 애초에 String 타입으로 정의해 뒀기 때문에 굳이 파싱을 하지 않음!
	    
	    Notice n = new Notice();
	    n.setNoticeTitle(noticeTitle);
	    n.setNoticeContent(noticeContent);
	    n.setNoticeWriter(userNo);
	    
	    // 3) Service 단으로 넘기면서 요청 후 결과 받기
	    int result = new NoticeService().insertNotice(n);
	    
	    // 4) 결과에 따른 응답 페이지 지정
	    if(result > 0) { // 성공 => 성공 문구 alert, 공지사항 리스트 조회 페이지로 url 요청
	        request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 등록되었습니다.");
            response.sendRedirect(request.getContextPath() + "/list.no");
	    } else { // 실패 => 에러 문구를 담아 에러 페이지로 포워딩
	        request.setAttribute("errorMsg", "공지사항 등록 실패");
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
