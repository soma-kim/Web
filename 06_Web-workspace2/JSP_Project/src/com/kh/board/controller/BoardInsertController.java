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
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // 일반 게시글 추가 가능
	    // 1. Board 테이블에 insert
	    // 2. 만약 첨부파일이 있다면 Attachment 테이블도 insert
	    
	    // 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    /*
	    // 뽑아야 할 값
	    // userNo: 작성자 번호
	    String boardWriter = request.getParameter("userNo"); // "3"
	    // category: 카테고리 번호
	    String category = request.getParameter("category"); // "20"
	    // title: 제목
	    String boardTitle = request.getParameter("title");
	    // content: 내용
	    String boardContent = request.getParameter("content");
	    // upfile: 첨부파일
	    */
	    // System.out.println(boardWriter);
	    // System.out.println(category);
	    // System.out.println(boardTitle);
	    // System.out.println(boardContent);
	    
	    // 폼 전송을 일반 방식이 아닌 multipart/form-data로 전송하는 경우
	    // 일반 request 객체로부터 값 뽑기가 불가함 => null 나옴
	    // => MultipartRequest 타입으로 변환 후 뽑기 가능
	    
	    // 우선 해당 요청이 multipart/form-data 형식이지 먼저 검사
	    if(ServletFileUpload.isMultipartContent(request)) {
	        // ServletFileUpload.isMultipartContent(request)
	        // => 해당 request (요청)이 multipart/form-data 형식이라면 true 반환
	        
	        // System.out.println("이거 multipart 어쩌고 맞다");
	        
	        // HttpServletRequest 타입을 MultipartRequest 타입으로 변환하기
	        // => request 객체, 저장할 파일의 경로값, 파일의 용량 제한, 인코딩 형식, 파일명을 수정시켜 주는 객체
	        // (변환하는 구문의 역할은 타입을 변환할 뿐만 아니라 그 요청에 딸린 첨부파일까지 다 저장해 주는 역할)
	        
	        // 1. 전송되는 파일을 처리할 작업 내용
	        // 1_1. 전송 파일의 용량 제한
	        // (정수 타입 => int, byte 단위의 값을 기술해야 함)
	        // => 10Mbte로 제한
	        /*
	         * 단위 정리
	         * 1byte == 8bit
	         * byte -> kbyte -> mbte -> gbyte -> tbyte -> ...
	         * 1kbyte == 1024byte (2의 10승)
	         * 1mbyte == 1024kbyte (2의 10승)
	         *        == 1024 * 1024byte
	         */
	        int maxSize = 10 * 1024 * 1024;
	        
	        // 1_2. 전달된 파일을 저장할 서버의 실 경로 지정
	        /*
	         * application 내장 객체로부터 경로 알아내기
	         * 
	         * 참고
	         * session 내장 객체 얻어내기: request.getSession();
	         * application 내장 객체 얻어내기: request.getSession().getServletContext() 
	         * 
	         */
	        String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");
	        // => 가장 처음 /가 의미하는 것: WebContent
	        // => 가장 마지막 /가 의미하는 것 : 해당 폴더의 내부
	        
	        System.out.println(maxSize);
	        System.out.println(savePath);
	        
	        // 2. 전달된 파일명 수정 및 서버에 업로드 작업
	        /*
	         * - HtttpServletRequest 타입을 MultipartRequest 타입으로 변환
	         * 
	         * [ 표현법 ]
	         * MultipartRequest xxx
	         *      = new MultipartRequest(request객체, 저장할폴더의경로, 용량제한,
	         *                             인코딩값, 파일명을수정시켜주는객체);
	         * 
	         * => 위의 매개변수 생성자로 생성
	         * => 위 구문 한 줄만으로 넘어온 첨부파일들이 해당 폴더에 업로드됨!
	         * => MultipartRequest 타입은 (cos.jar에서 제공하는 클래스)
	         * 
	         * * cos.jar 라이브러리
	         * com.oreilly.servlet의 약자
	         * 다운로드 링크: http://www.servlets.com
	         * => 파일 업로드 기능을 구현하려면 필수적으로 필요함
	         * 
	         * * 사용자가 올린 파일은 보통 파일명을 수정해서 저장하게끔 되어 있음
	         * => 같은 파일명이 있을 경우 덮어씌워질 수도 있고, 한글/특수문자/띄어쓰기가 포함된 파일명의 경우
	         *    서버에 따라 문제가 발생할 가능성이 있음
	         * => 기본적으로 파일명 수정 작업을 해 주는 개체를 cos.jar 에서 제공해 줌
	         *    DefaultFileRenamePolicy 객체
	         *    (내부적으로 rename() 메소드가 호출되면서 파일명이 수정됨,
	         *     기본적으로 동일한 파일명이 이미 존재할 경우 카운팅된 숫자를 붙여서 업로드됨)
	         *    예) aaa.jpg, aaa1.jpg, aaa2.jpg
	         *
	         * => 하지만 우리 입맛대로 절대 파일명이 겹치지 않게끔 rename 해 볼 것임!
	         *    (DefaultFileRenamePolicy 객체 사용 x)
	         * => 내 입맛대로 파일명을 rename 해 주는 객체 생성 (com.kh.common.MyfileRenamePolicy 클래스 만들기)
	         * 
	         * 
	         */
	        MultipartRequest multiRequest = new MultipartRequest(request, savePath
	                                                    , maxSize, "UTF-8", new MyFileRenamePolicy()); 
	        
	        // 3. DB에 기록할 데이터를 뽑아서 VO 객체에 담기
	        // 뽑아야 할 값
	        // - 카테고리번호 (category), 제목(title), 내용(content), 작성자 회원번호(userNo)
	        // => Board 로 가공
	        String category = multiRequest.getParameter("category");
	        String boardTitle = multiRequest.getParameter("title");
            String boardContent = multiRequest.getParameter("content");
            String boardWriter = multiRequest.getParameter("userNo");
            
            Board b = new Board();
            b.setCategory(category);
            b.setBoardTitle(boardTitle);
            b.setBoardContent(boardContent);
            b.setBoardWriter(boardWriter);
            
            // 만약 첨부파일이 있다면 첨부파일에 대한 정보도 뽑아서 VO 객체에 담기
            // => upfile이라는 키값으로 뽑기
            // => Attachment로 가공
            Attachment at = null;
            
            // 첨부파일이 있나 검사
            // multiRequest.getOriginalFileName(): 해당 키값으로 딸려온 파일의 원본 파일명을 찾아 리턴 
            // => 없다면 null
            if(multiRequest.getOriginalFileName("upfile") != null) { // 첨부파일이 있다면
                
                at = new Attachment();
                at.setOriginName(multiRequest.getOriginalFileName("upfile")); // 원본명
                at.setChangeName(multiRequest.getFilesystemName("upfile")); // 수정명, 실제 서버에 업로드된 파일명
                at.setFilePath("resources/board_upfiles/");
            }
            
            // 이 시점 기준으로 첨부파일이 없다면 at == null
            // 4. 서비스 요청
            int result = new BoardService().insertBoard(b, at);
	        
            // 5. 결과에 따른 응답페이지 지정
            if(result > 0) { // 성공 => /jsp/list.bo?currentPage=1 요청 (가장 최신글이므로)
                
                request.getSession().setAttribute("alertMsg", "게시글 작성에 성공했습니다.");
                response.sendRedirect(request.getContextPath() + "/list.bo?currentPage=1");
                
            } else { // 실패 => 실패 문구 담아서 에러 페이지로 포워딩
                
                // 첨부파일이 있었을 경우 이미 업로드된 첨부파일을 굳이 서버에 보관할 필요가 없음
                // => 지워 줌(가지고 있어 봤자 용량만 차지함!)
                if(at != null) {
                 
                    // 삭제하고자 하는 파일 객체를 생성 후 delete() 메소드 호출
                    new File(savePath + at.getChangeName()).delete();
                }
                
                request.setAttribute("errorMsg", "게시글 작성 실패");
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
