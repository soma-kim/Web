package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class BoardDetailController
 */
@WebServlet("/detail.bo")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 해당 게시글 번호 먼저 뽑기
	    int boardNo = Integer.parseInt(request.getParameter("bno"));
	    
	    // 조회 수 증가 / 게시글 조회(Board) / 첨부파일 조회(Attachment)
	    // => BoardService로 요청을 3번 보내야 함
	    BoardService bService = new BoardService();
	    
	    // 1. 조회 수 증가 요청
	    int result = bService.increaseCount(boardNo);
	    
	    if(result > 0) { // 조회 수 증가에 성공했다면
	        
	        // 게시글 조회, 첨부파일 조회
	        Board b = bService.selectBoard(boardNo);
	        Attachment at = bService.selectAttachment(boardNo);
	        
	        // 게시글 정보 보내기
	        request.setAttribute("b", b);
	        request.setAttribute("at", at);
	        
	        // 게시글 상세조회 페이지로 포워딩
	        request.getRequestDispatcher("views/board/boardDetailView.jsp").forward(request, response);
	        
	    } else { // 조회 수 증가에 실패했다면
	        
	        // 에러 문구 담아서 에러페이지로 포워딩
	        // 키값 오타 나면 null 값 찍힘!
	        request.setAttribute("errorMsg", "게시글 상세 조회 실패");
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
