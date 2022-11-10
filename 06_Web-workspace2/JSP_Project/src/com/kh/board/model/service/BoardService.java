package com.kh.board.model.service;

import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.board.model.vo.Reply;
import com.kh.common.model.vo.PageInfo;

public class BoardService {
    
    public int selectListCount() {
        
        Connection conn = getConnection();
        
        int listCount = new BoardDao().selectListCount(conn);
        
        close(conn);
        
        return listCount;

    }
    
    public ArrayList<Board> selectList(PageInfo pi) {
        
        Connection conn = getConnection();
        
         ArrayList<Board> list = new BoardDao().selectList(conn, pi);
         
         close(conn);
         
         return list;
         
    }
    
    public ArrayList<Category> selectCategoryList() {
        
            Connection conn = getConnection();
            
            ArrayList<Category> list = new BoardDao().selectCategoryList(conn);
            
            close(conn);
            
            return list;
    }
    
    public int insertBoard(Board b, Attachment at) {
        
        Connection conn = getConnection();
        
        // 주의사항 => DAO의 메소드 1개 == 쿼리문 1개
        
        // BOARD 테이블 INSERT 요청 먼저 (첨부파일이 있든 없든 간에 무조건 일어나야 함)
        int result1 = new BoardDao().insertBoard(conn, b); // 성공: 1, 실패: 0
        
        // 한 개의 트랜잭션에 테이블의 변동이 있는 DML이 두 번 실행
        // => 그 두 번의 DML 중 하나라도 실패한다면 전부 rollback 처리해야 함
        // => 그 두 번의 DML이 모두 성공해야지만 커밋!
        
        // 만약 첨부파일이 있다면 ATTACHMENT 테이블에 INSERT 요청을 보내기
        
        // 두 번째 요청에 대한 결과값을 담을 변수 먼저 세팅
        int result2 = 1;
        
        if(at != null) { // 첨부파일이 있다면
            
            result2 = new BoardDao().insertAttachment(conn, at);
        }
        
        // 트랜잭션 처리
        // result1 > 0 && result2 > 0 => 둘 다 성공일 경우
        if(result1 > 0 && result2 > 0) {
            
            // 경우의 수
            // 1. 첨부파일이 있는 경우: result1 == 1, result2 == 1
            // 2. 첨부파일이 없는 경우: result1 == 1, result2 == 1
            // => result2를 0으로 초기화해 놓는다면, 첨부파일이 없는 경우 트랜잭션이 모두 성공했을 때도 result2는 여전히 0이기 때문에 else 블럭으로 빠져나감
            // => 애초에 result2 변수 세팅을 1로 변경해 줄 것임!
            
            commit(conn);
        } else {
            rollback(conn);
        }
        
        close(conn);
        
        // 자바 문법상 메소드 한 개의 리턴값은 한 개뿐 => result1, result2를 같이 넘겨야 함
        // 경우의 수
        // result1 == 1, result2 == 1 => result1 * result2 == 1 (모두 성공인 경우)
        // result1 == 1, result2 == 0 => result1 * result2 == 0 (1만 성공인 경우)
        // result1 == 0, result2 == 1 => result1 * result2 == 0 (2만 성공인 경우)
        // result1 == 0, result2 == 0 => result1 * result2 == 0 (모두 실패한 경우)
        // => 혹시라도 하나라도 실패해서 0이 될 경우 아예 실패값을 전달하기 위해 곱셈 결과를 보냄
        
        return result1 * result2;
    }
    
    public int increaseCount(int boardNo) {
        
        Connection conn = getConnection();
        
        int result = new BoardDao().increaseCount(conn, boardNo);
        
        if(result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        close(conn);
        
        return result;
    }
    
    public Board selectBoard(int boardNo) {
        
        Connection conn = getConnection();
        
        Board b = new BoardDao().selectBoard(conn, boardNo);
        
        close(conn);
        
        return b;
        
    }
    
    public Attachment selectAttachment(int boardNo) {
        
        Connection conn = getConnection();
        
        Attachment at = new BoardDao().selectAttachment(conn, boardNo);
        
        close(conn);
        
        return at;
        
    }
    
    public int updateBoard(Board b, Attachment at) {
        Connection conn = getConnection();
        
        // 3가지 경우 모두 공통적으로 실행해야 하는 BOARD UPDATE 구문 요청
        int result1 = new BoardDao().updateBoard(conn, b);
        
        // 두 번째 요청 결과를 받을 변수 세팅
        // 0으로 세팅했을 때, 기존 첨부파일이 없었다면 넘겨 줄 곱하기 결과값은 무조건 0이 되므로 애초에 1로 세팅
        int result2 = 1;
        
        if(at != null) { // 새롭게 첨부된 파일이 있을 경우 => Attachment 테이블에 Update 또는 Insert
            
            if(at.getFileNo() != 0) { // 기존 첨부파일이 있는 경우 => Attachment 테이블에 UPDATE 요청
                
                result2 = new BoardDao().updateAttachment(conn, at);
                
            } else { // 기존 첨부파일이 없는 경우 => Attachment 테이블에 INSERT 요청
                
                // 기존에 우리가 만들어 놨던 insertAttachment 재활용 불가! (쿼리문이 다르기 때문)
                result2 = new BoardDao().insertNewAttachment(conn, at);     
            }
        }
        
        // 모든 요청이 다 성공했을 경우만 commit
        if(result1 > 0 && result2 > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        
        close(conn);
        
        return result1 * result2;
    }
    
    // 게시글 삭제용 서비스
    public int deleteBoard(int boardNo) {
        
        Connection conn = getConnection();
        
        int result = new BoardDao().deleteBoard(conn, boardNo);
        
        if(result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        
        return result;
    }
    
    public int insertThumbnailBoard(Board b, ArrayList<Attachment> list) {
        
        Connection conn = getConnection();
        
        // 각각 b와 list를 insert 할 수 있는 요청 보내기
        // list는 bno을 참조해야 하기 때문에 "b부터 진행"해야 함!!
        int result1 = new BoardDao().insertThumbnailBoard(conn, b);
        
        int result2 = new BoardDao().insertAttachmentList(conn, list);
        
        if(result1 > 0 && result2 > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        
        close(conn);
        
        return result1 * result2;
    }
    
    public ArrayList<Board> selectThumbnailList() {
        
        Connection conn = getConnection();
        
        ArrayList<Board> list = new BoardDao().selectThumbnailList(conn);
     
        close(conn);
        
        return list;
    }
    
    public ArrayList<Attachment> selectAttachmentList(int boardNo) {
        
        Connection conn = getConnection();
        
        ArrayList<Attachment> list = new BoardDao().selectAttachmentList(conn, boardNo);
        
        close(conn);
        
        return list;
    }
    
    public ArrayList<Reply> selectReplyList(int boardNo) {
        
        Connection conn = getConnection();
        
        ArrayList<Reply> list = new BoardDao().selectReplyList(conn, boardNo);
        
        close(conn);
        
        return list;
    }
    
    public int insertReply(Reply r) {
        
        Connection conn = getConnection();
        
        int result = new BoardDao().insertReply(conn, r);
        
        if(result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        
        close(conn);
        
        return result;
        
    }
     
}
