package com.kh.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

// import com.kh.common.JDBCTemplate;
import static com.kh.common.JDBCTemplate.*; // JDBCTemplate 클래스의 모든 메소드들을 그냥 가져다 쓰겠음!
import com.kh.notice.model.dao.NoticeDao;
import com.kh.notice.model.vo.Notice;

public class NoticeService {
    
    // 공지사항 전체 조회용 서비스
    public ArrayList<Notice> selectNoticeList() {
        
        // 1) Connection 객체 생성
        Connection conn = /* JDBCTemplate.*/ getConnection();
        
        // 2) 만들어진 Connection 객체와 전달값을 DAO로 넘기면서 요청 처리 후 결과받기
        ArrayList<Notice> list = new NoticeDao().selectNoticeList(conn);
        
        // 3) 트랜잭션 처리 => SELECT문을 실행할 것이기 때문에 패스
        
        // 4) Connection 객체 반납
        /* JDBCTemplate.*/ close(conn);
        
        // 5) 결과 반환
        return list;
    }
    
    // 공지사항 작성용 서비스
    public int insertNotice(Notice n) {
        
        // 1) Connection 객체 생성
        Connection conn = getConnection();
        
        // 2) conn, n을 넘기면서 DAO에 요청 후 결과받기
        int result = new NoticeDao().insertNotice(conn, n);
        
        // 3) 트랜잭션 처리
        if(result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        
        // 4) 반납
        close(conn);
        
        // 5) 반환
        return result;
    }
    
    // 공지사항 조회수 증가용 서비스
    public int increaseCount(int noticeNo) {
        
        // 1) Connection 생성
        Connection conn = getConnection();
        
        // 2) conn, noticeNo을 DAO로 넘기면서 요청 후 결과 받기
        int result = new NoticeDao().increaseCount(conn, noticeNo);
        
        // 3) 트랜잭션 처리
        if(result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        
        // 4) conn 반납
        close(conn);
        
        // 5) 결과 리턴
        return result;
    }
    
    // 게시글 상세조회용 서비스
    public Notice selectNotice(int noticeNo) {
        
        // 1) Connection 생성
        Connection conn = getConnection();
        
        // 2) conn, noticeNo을 넘기면서 DAO에 요청 처리 후 결과 받기
        Notice n = new NoticeDao().selectNotice(conn, noticeNo);
        
        // 3) 트랜잭션 처리 => 패스
        
        // 4) conn 반납
        close(conn);
        
        // 5) 결과 리턴
        return n;
    }
    
    // 공지사항 수정용 서비스
    public int updateNotice(Notice n) {
        Connection conn = getConnection();
        
        int result = new NoticeDao().updateNotice(conn, n);
        
        if(result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        
        close(conn);
        
        return result;
    }
    
    // 공지사항 삭제용 서비스
    public int deleteNotice(int noticeNo) {
        
        Connection conn = getConnection();
        
        int result = new NoticeDao().deleteNotice(conn, noticeNo);
        
        if(result > 0) { 
            commit(conn);
        } else {
            rollback(conn);
        }
        
        return result;
        
    }
    

}
