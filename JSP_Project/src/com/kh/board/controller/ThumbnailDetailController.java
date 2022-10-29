package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class ThumbnailDetailController
 */
@WebServlet("/detail.th")
public class ThumbnailDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    int boardNo = Integer.parseInt(request.getParameter("bno"));
	    
	    // 조회 수 증가용 서비스 요청 후 성공 시 상세 조회 요청 => increaseCount 메소드 재활용
	    int result = new BoardService().increaseCount(boardNo);
	    
	    if(result > 0) { // 성공 => 게시글 정보, 첨부파일들 정보 조회
	        
	        // Board 테이블로부터 해당 게시글 정보만 뽑아오기
	        // 일반 게시판용 selectBoard 쿼리 활용 => 기본 내부 조인에서 left outer 조인으로 변경
	        // 내부 조인인 경우 일치하는 컬럼만을 가져오는 구조였는데,
	        // 사진 게시판의 경우 카테고리가 null이기 때문에 일치하는 것이 없어 반환되는 것이 없었던 것!
	        // 카테고리 컬럼을 기준으로 일치하는 컬럼, 일치하지 않는 컬럼도 가지고 오려면 outer join(외부조인) 해 줘야 함!
	        // 어찌되었든 간에 Board 테이블의 내용물을 조회하고 싶기 때문에 Board 테이블을 기준으로 left outer 조인으로 변경
	        Board b = new BoardService().selectBoard(boardNo);
	        
	        // Attachment 테이블로부터 해당 게시글에 딸린 첨부파일들을 모두 조회
	        ArrayList<Attachment> list = new BoardService().selectAttachmentList(boardNo);
	        
	        // 수화물 싣기
	        request.setAttribute("b", b);
	        request.setAttribute("list", list);
	        
	        request.getRequestDispatcher("views/board/thumbnailDetailView.jsp").forward(request, response);
	        
	    } else { // 실패 => 에러 문구 담아서 에러 페이지로 포워딩 
	        
	        request.setAttribute("errorMsg", "사진 게시글 조회 실패");
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
