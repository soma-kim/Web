package com.kh.notice.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.notice.model.vo.Notice;

public class NoticeDao {
    
    private Properties prop = new Properties();
    
    public NoticeDao() { 
        // 다른 클래스에서 NoticeDao를 호출하면 해당 기본 메소드가 먼저 실행되므로
        // notice-mapper 파일의 sql문을 읽어들일 준비를 여기서 해 줌
        
        // 어느 경로의 파일을 읽어올 것인지 제시
        String fileName = NoticeDao.class.getResource("/sql/notice/notice-mapper.xml").getPath();
        
        // 파일 읽어들이기
        try {
            prop.loadFromXML(new FileInputStream(fileName));
        } catch (IOException e) { // multi catch로 부모 타입인 IOException를 예외처리 할 수 있도록 함
            e.printStackTrace();
        }
    }
    
    public ArrayList<Notice> selectNoticeList(Connection conn) {
        
        // SELECT문 => ResultSet 객체 (공지사항 전체 조회이기 때문에 여러 건이 조회될 가능성이 훨씬 높음)
        // 즉, 여러 행 조회이므로 ArrayList<Notice>로 받아 줘야 함
        
        // 1) 필요한 변수 먼저 세팅
        ArrayList<Notice> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selctNoticeList");
        
        try {
            // 2) PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // 3_1) 미완성된 쿼리문 완성시키기
            // => 쿼리문 자체가 완성되어 있고, 채울 값 없으므로 패스
            
            // 3_2) 쿼리문 실행 후 결과 받기
            rset = pstmt.executeQuery();
            
            // 4) rset으로부터 더 이상 뽑을 값이 없을 때까지 반복 돌려가며 조회된 내용물을 가공하기
            while(rset.next()) {
                
                list.add(new Notice(rset.getInt("NOTICE_NO"),
                                    rset.getString("NOTICE_TITLE"),
                                    rset.getString("USER_ID"),
                                    rset.getInt("COUNT"),
                                    rset.getDate("CREATE_DATE")));
                // 위와 같은 매개변수 3가지의 생성자가 없기 때문에 빨간줄! 생성자 추가해 주면 없어짐
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            // 5) 자원 반납
            // JDBCTemplate import를 static으로 바꾸고 별 찍어 주면 클래스명 제시하지 않아도 쓸 수 있음
            /* JDBCTemplate.*/ close(rset);
            /* JDBCTemplate.*/ close(pstmt);
        }
        
        // 6) 결과 반환
        return list; // 성공: list의 내용물이 있음, 실패: list.isEmpty() == true

    }
    
    public int insertNotice(Connection conn, Notice n) {
        
        // INSERT문 => int (처리된 행의 개수)
        
        // 1) 필요한 변수 세팅
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("insertNotice");
        
        
        try {
            // 2) pstmt 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // 3_1) 쿼리문 완성
            pstmt.setString(1, n.getNoticeTitle());
            pstmt.setString(2, n.getNoticeContent());
            pstmt.setInt(3, Integer.parseInt(n.getNoticeWriter())); // "1" -> 1
            
            // 3_2) 실행 후 결과 받기
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            // 4) 자원 반납
            close(pstmt);
        }
        
        // 5) 반환
        return result;        
    }
    
    public int increaseCount(Connection conn, int noticeNo) {
        
        // UPDATE문 => int (처리된 행의 개수)
        
        // 1) 필요한 변수 먼저 세팅
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("increaseCount");
        
        try {
            // 2) pstmt 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // 3_1) 쿼리문 완성
            pstmt.setInt(1, noticeNo);

            // 3_2) 실행 후 결과받기
            result = pstmt.executeUpdate();
             
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            // 4) 자원 반납
            close(pstmt);
        }
        
        // 5) 결과 반환
        return result;
    }
    
    public Notice selectNotice(Connection conn, int noticeNo) {
    
    // SELECT문 => ResultSet 객체(Primary key 기준으로 조회하기 때문에 많아 봤자 1건) => Notice 객체
    
    // 1) 필요한 변수 먼저 세팅
    Notice n = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    
    String sql = prop.getProperty("selectNotice");
    
    try {
        // 2) pstmt 객체 생성
        pstmt = conn.prepareStatement(sql);
        
        // 3_1) 쿼리문 완성시키기
        pstmt.setInt(1, noticeNo);
        
        // 3_2) 실행 후 결과 받기
        rset = pstmt.executeQuery();
        
        // 4) rset으로부터 조회된 데이터 뽑기
        if(rset.next()) {
            
            n = new Notice(rset.getInt("NOTICE_NO")
                         , rset.getString("NOTICE_TITLE")
                         , rset.getString("NOTICE_CONTENT")
                         , rset.getString("USER_ID")
                         , rset.getDate("CREATE_DATE"));
            
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        
     // 5) 자원 반납
        close(rset);
        close(pstmt);
        
    }
    
    // 6) 결과 반환
    return n;
    
    }
    
    public int updateNotice(Connection conn, Notice n) {
        
        // UPDATE문 => int (처리된 행의 개수)
        
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("updateNotice");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, n.getNoticeTitle());
            pstmt.setString(2,  n.getNoticeContent());
            pstmt.setInt(3, n.getNoticeNo());
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        
        return result;

    }
    
    public int deleteNotice(Connection conn, int noticeNo) {
        
        // UPDATE문 => int (처리된 행의 갯수)
        
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("deleteNotice");
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, noticeNo);
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        
        return result;
    }

}
