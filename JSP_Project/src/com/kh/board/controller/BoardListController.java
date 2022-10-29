package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	    // -- 페이징 처리 방법 --
	    // 어떤 페이지를 클릭했느냐에 따라 페이징바의 숫자, 게시글 번호 등이 모두 달라짐
	    // 총 7개의 변수 필요 => 기본적으로 알아내야 하는 변수 4개, 계산해서 구해야 하는 변수 3개
	    int listCount;   // 현재 총 게시글 개수
	    int currentPage; // 현재 요청한 페이지(즉, 사용자가 요청한 페이지 수)
	    int pageLimit;   // 페이지 하단에 보여질 페이징바의 페이지 최대 개수
	    int boardLimit;  // 한 페이지에 보여질 게시글의 최대 개수(몇 개 단위씩 리스트가 보여질 건지)
	    
	    int maxPage;     // 가장 마지막 페이지가 몇 번 페이지인지(총 페이지 수)
	    int startPage;   // 페이지 하단에 보여질 페이징바의 시작 수
	    int endPage;     // 페이지 하단에 보여질 페이징바의 끝 수
	    
	    // *listCount: 총 게시글 개수
	    listCount = new BoardService().selectListCount();
	    
	    // System.out.println(listCount);
	    
	    // *currentPage: 현재 페이지 (즉, 사용자가 요청한 페이지)
	    // => 쿼리스트링으로 대놓고 넘김!!
	    currentPage = Integer.parseInt(request.getParameter("currentPage"));
	    
	    // * pageLimit: 페이지 하단에 보여질 페이징 바의 페이지 최대 개수
	    //              (페이지 목록들을 몇 개 단위씩 보여 줄 건지)
	    pageLimit = 10;
	    
	    // * boardLimit: 한 페이지에 보여질 게시글의 최대 개수
	    //               (게시글을 몇 개 단위씩 보여 줄 건지)
	    boardLimit = 10;
	    
	    // * maxPage: 가장 마지막 페이지가 몇 번 페이지인지 => 총 페이지 수를 나타냄
	    /*
	     * listCount, boardLimit에 영향을 받음
	     * 
	     * - 공식 구하기
	     *   단, boardLimit가 10이라는 가정 하에 규칙을 구해 보자!
	     *   
	     *     listCount(총 개수)     boardLimit       maxPage
	     *         100.0     /     10 => 몫 10.0     10 번 페이지 
	     *         101.0     /     10 => 몫 10.1     11 번 페이지  
	     *         105.0     /     10 => 몫 10.5     11 번 페이지
	     *         109.0     /     10 => 몫 10.9     11 번 페이지
	     *         110.0     /     10 => 몫 11.0     11 번 페이지
	     *         111.0     /     10 => 몫 11.1     12 번 페이지
	     *   
	     *   => 나눗셈 연산한 결과를 "올림" 처리한다면?
	     *   
	     *   1) listCount를 double로 강제 형변환
	     *   2) listCount / boardLimit
	     *   3) 결과값에 올림 처리 => Math.ceil();
	     *   4) 결과값을 int형으로 강제 형변환
	     */
	    maxPage = (int)Math.ceil((double)listCount / boardLimit);
	    
	    // * startPage : 페이지 하단에 보일 페이징바의 시작 수
	    /*
	     * pageLimit, currentPage에 영향을 받음
	     * 
	     * - 공식 구하기
	     *   단, pageLimit가 10이라는 가정 하에 규칙을 구해 보자!
	     *   
	     *   pageLimit가 10일 경우
	     *   startPage: 1, 11, 21, 31, 41, ... => n * 10 + 1
	     *   
	     *   pageLimit가 5일 경우
	     *   startPage: 1, 6, 11, 16, 21, 26, ... => n * 5 + 1
	     *   
	     *   => 즉, n * pageLimit + 1
	     *   
	     *    currentPage       startPage
	     *         1                1       =>     0 * pageLimit + 1 => 1
	     *         5                1       =>     0 * pageLimit + 1 => 1
	     *         10               1       =>     0 * pageLimit + 1 => 1
	     *         11               11      =>     1 * pageLimit + 1 => 11
	     *         15               11      =>     1 * pageLimit + 1 => 11
	     *         20               11      =>     1 * pageLimit + 1 => 11
	     *   
	     *   => 1 ~ 10 : n = 0    =>  0 ~  9 / pageLimit = 0 
	     *     11 ~ 20 : n = 1    => 10 ~ 19 / pageLimit = 1
	     *     21 ~ 30 : n = 2    => 20 ~ 29 / pageLimit = 2
	     *     31 ~ 40 : n = 3    => 30 ~ 39 / pageLimit = 3
	     *                ...
	     *               n = (currentPage - 1) / pageLimit
	     * startPage =              n                * pageLimit + 1
	     *           = (currentPage - 1) / pageLimit * pageLimit + 1
	     */
	    startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
	    
	    // * endPage : 페이지 하단에 보여질 페이징바의 끝 수
	    /*
	     * startPage, pageLimit에 영향을 받음 (단, maxPage도 영향을 주긴 함)
	     * 
	     * - 공식 구하기
	     *   단, pageLimit가 10이라는 가정 하에 규칙을 구해 보자!
	     *   
	     *   startPage: 1   =>   endPage: 10
	     *   startPage: 11  =>   endPage: 20
	     *   startPage: 21  =>   endPage: 30
	     *   
	     *   endPage = startPage + pageLimit - 1
	     */
	    endPage = startPage + pageLimit - 1;
	    
	    // 근데 maxPage가 고작 13까지밖에 안 된다면?
	    // => endPage를 maxPage로 변경
	    if(endPage > maxPage) {
	        endPage = maxPage;
	    }
	    
	    // 페이지 정보들 (7개의 변수)을 하나의 공간에 담아서 보내자
	    // => 페이지 정보들을 담을 VO 클래스를 하나 더 만들 것!
	    //    (공지사항이나 사진 게시판에서도 쓰일 수 있으므로 common 패키지에 만들 것!)
	    
	    // 1. PageInfo 객체로 가공 (조회할 때, 페이징바 만들 때 필요) 
	    
	    PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit
	                             , maxPage, startPage, endPage);
	    
	    // pi를 넘기면서 서비스로 요청
	    // 2. list에는 해당 페이지에서 보여져야 할 게시글들의 목록들(목록 만들 때 필요)
	    ArrayList<Board> list = new BoardService().selectList(pi);
	    
	    request.setAttribute("pi", pi);
	    request.setAttribute("list", list);
	    
	    request.getRequestDispatcher("views/board/boardListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
