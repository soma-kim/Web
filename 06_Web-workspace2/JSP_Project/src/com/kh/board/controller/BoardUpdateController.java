package com.kh.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    
	    // multipart/form-data 형식으로 요청이 들어왔는지 검사
	    if(ServletFileUpload.isMultipartContent(request)) {
	        // true라면, (== multipart/form-data가 맞다면)
	        
	        // 1. 전송된 파일에 대한 정보들 먼저 지정 (전송 파일 용량 제한, 전달된 파일을 저장할 서버의 실경로)
	        // 1_1. 전송 파일 용량 제한 (int 자료형으로 byte 단위 기준)
	        int maxSize = 10 * 1024 * 1024; // 10mbyte
	        
	        // 1_2. 전달된 파일을 저장할 서버의 실제 경로
	        String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles");
	        
	        // 2. 전달된 파일명 수정 작업 후 서버에 업로드 & MultipartRequest 타입으로 변환
	        // => 매개변수 생성자 호출
	        MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
	        
	        // 3. 본격적으로 요청 시 전달값을 뽑을 수 있음!
	        // => 변수 및 객체에 기록
	        
	        // - 공통적으로 수행: Board 테이블 Update에 필요한 데이터들 먼저 가공
	        int boardNo = Integer.parseInt(multiRequest.getParameter("bno"));
	        String category = multiRequest.getParameter("category");
	        String boardTitle = multiRequest.getParameter("title");
	        String boardContent = multiRequest.getParameter("content");
	        
	        Board b = new Board();
	        b.setBoardNo(boardNo);
	        b.setCategory(category);
	        b.setBoardTitle(boardTitle);
	        b.setBoardContent(boardContent);
	        
	        // 첨부파일에 대한 정보를 담아둘 변수
	        Attachment at = null;
	        
	        // 요청 시 전달값 중 넘어온 첨부파일이 있는지 먼저 검사
	        if(multiRequest.getOriginalFileName("reUpfile") != null) {
	            // 넘어온 첨부파일이 있을 경우
	            // => Attachment 테이블에 Update 또는 Insert 해야 함

	            // 2개의 쿼리문 중 공통적으로 필요한 항목들 먼저 세팅
	            // => ORIGIN_NAMEM, CHANGE_NAME 컬럼값
	            at = new Attachment();
	            at.setOriginName(multiRequest.getOriginalFileName("reUpfile"));
	            at.setChangeName(multiRequest.getFilesystemName("reUpfile"));
	            
	            // Insert 구문에서 추가적으로 필요로 하는 FILE_PATH 컬럼값도 세팅
	            at.setFilePath("resources/board_upfiles/");
	            
	            // 기존 파일이 있었는지 없었는지 검사
	            // => 기존 파일이 있었다면 : Attachment 테이블에 Update 구문 실행
	            // => 기존 파일이 없었다면 : Attachment 테이블에 Insert 구문 실행
	            
	            // 기존 파일이 있을 경우 originFileNo, originFileName을 넘겼음
	            if(multiRequest.getParameter("originFileNo") != null) {
	                // 기존 첨부파일이 있을 경우
	                
	                // Update 구문에서 추가적으로 필요로 하는 기존 파일의 고유번호 담기
	                at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo")));
	                
	            } else {
	                // 기존 첨부파일이 없을 경우
	                
	                // Insert 구문에서 추가적으로 필요로 하는 참조 게시글 번호를 담기
	                at.setRefNo(boardNo);
	            }
	             
            }
	        

            // 이 시점 기준으로 각 케이스별로 필요한 데이터들이 at에 담겨 있음
             
            //                                    b   at
            // CASE 1. 기존 첨부파일 X, 새로운 첨부파일 X => b, null          => BOARD 테이블 UPDATE만 실행
            // CASE 2. 기존 첨부파일 O, 새로운 첨부파일 O => b, fileNo이 담긴 at => BOARD UPDATE, ATTACHMENT UPDATE  
            // CASE 3. 기존 첨부파일 X, 새로운 첨부파일 O => b, refNo이 담긴 at  => BOARD UPDATE, ATTACHMENT INSERT 
            
            // 모두 하나의 트랜잭션으로 처리해야 함!
            int result = new BoardService().updateBoard(b, at);
            
            if(result > 0) { // 수정 성공 => 상세 페이지로 url 요청
                
                // 만약에 기존 첨부파일이 있고, 새로운 첨부파일도 있을 경우! 서버에 있던 기존 첨부파일을 삭제
                if(multiRequest.getParameter("originFileName") != null
                        && multiRequest.getOriginalFileName("reUpfile") != null) {
                    
                    new File(savePath + multiRequest.getParameter("originFileName")).delete();
                    
                }
                
                request.getSession().setAttribute("alertMsg", "게시글이 성공적으로 수정되었습니다.");
                response.sendRedirect(request.getContextPath() + "/detail.bo?bno=" + boardNo); // url 요청
                
            } else { // 수정 실패 => 에러 문구 담아서 에러 페이지로 포워딩
                
                request.setAttribute("errorMsg", "게시글 수정 실패");
                request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	            
	        }
	        
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
