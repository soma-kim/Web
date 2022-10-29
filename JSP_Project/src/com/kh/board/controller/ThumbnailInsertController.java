package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class ThumbnailInsertController
 */
@WebServlet("/insert.th")
public class ThumbnailInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    
	    // 이 요청이 multipart/form-data 형식인지 먼저 검사
	    if(ServletFileUpload.isMultipartContent(request)) {
	        
	        // 1. 전달된 파일에 대한 정보 먼저 지정 (전송파일 용량 제한, 지정할 파일의 물리적인 경로)
	        // 1_1. 용량 제한
	        int maxSize = 10 * 1024 * 1024;
	        
	        // 1_2. 저장할 파일의 물리적인 경로
	       String savePath = request.getSession().getServletContext().getRealPath("/resources/thumbnail_upfiles/");
	       
	       // 2. 전달된 파일명 수정 작업 후 서버에 업로드 + MultipartRequest 타입으로 변환
	       MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
	       // MultipartRequest로 바꿀 request 넣어 주고, 사진 저장할 경로 savePath 넣어 주고, 최대 용량 넣어 주고, 인코딩, 파일명 수정작업
	       
	       // 3. DB에 전달할 값 뽑기
	       
	       // Board에 Insert => userNo, title, content
	       Board b = new Board();
	       b.setBoardWriter(multiRequest.getParameter("userNo"));
	       b.setBoardTitle(multiRequest.getParameter("title"));
	       b.setBoardContent(multiRequest.getParameter("content"));
	       
	       // Attachment에 Insert
	       // 단, 여러 개의 첨부파일이 있을 예정!
	       // => ArrayList<Attachment>에 담기 (최소 1개~ 최대 4개)
	       ArrayList<Attachment> list = new ArrayList<>();
	       
	       for(int i = 1; i <= 4; i++) { // i: 1, 2, 3, 4
	           
	           // 키값 먼저 세팅
	           String key = "file" + i; // key: "file1", "file2", "file3", "file4"
	           
	           // 해당 키값에 대한 첨부파일이 있다면 처리!
	           if(multiRequest.getOriginalFileName(key) != null) { // 첨부파일이 존재할 경우
	               
	               // Attachment 객체 생성
	               // 원본명, 수정명, 폴더경로, 파일레벨
	               
	               Attachment at = new Attachment();
	               at.setOriginName(multiRequest.getOriginalFileName(key));
	               at.setChangeName(multiRequest.getFilesystemName(key));
	               at.setFilePath("resources/thumbnail_upfiles/");
	               
	               if(i == 1) { // 대표 이미지일 경우 => 1
	                   at.setFileLevel(1);     
	               } else { // 상세 이미지일 경우  => 2
	                   at.setFileLevel(2);
	               }
	               
	               list.add(at);
	           }
	       }
	       
	       // 현재 list에는 첨부파일이 차곡차곡 담긴 상태임
	       // Service 단으로 요청 후 결과 받기
	       int result = new BoardService().insertThumbnailBoard(b, list);
	       
	       // 결과에 따른 응답 페이지 지정
	       if(result > 0) { // 성공 => 사진 게시판 리스트 url(list.th)로 재요청
	           
	           request.getSession().setAttribute("alertMsg", "성공적으로 사진 게시글이 업로드되었습니다");
	           response.sendRedirect(request.getContextPath() + "/list.th");
	           
	       } else { // 실패 => 에러 문구 담아서 에러페이지 포워딩
	           
	           request.setAttribute("errorMsg", "사진 게시판 업로드 실패");
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