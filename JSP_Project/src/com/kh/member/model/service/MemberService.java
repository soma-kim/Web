package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {

    // 로그인 요청 서비스
    public Member loginMember(Member m) {

        // 1) Connection 객체 생성
        Connection conn = JDBCTemplate.getConnection();

        // 2) 요청 시 전달값과 만들어진 Connection 객체를 DAO 의 메소드한테 전달 (호출)
        Member loginUser = new MemberDao().loginMember(conn, m);

        // 3) INSERT, UPDATE, DELETE 문이라면 commit / rollback 처리

        // 4) Connection 객체 반납
        JDBCTemplate.close(conn);

        // 5) 결과 반환
        return loginUser;
    }
    
    // 회원가입용 서비스
    public int insertMember(Member m) {
        
        // 1) Connection 객체 생성
        Connection conn = JDBCTemplate.getConnection();
        
        // 2) 전달값과 만들어진 Connection 객체를 DAO에 전달하면서 요청
        int result = new MemberDao().insertMember(conn, m);
        
        // 3) 트랜잭션 처리
        if(result > 0) { // 성공
            JDBCTemplate.commit(conn);
            
        } else { // 실패
            JDBCTemplate.rollback(conn);
        }
        
        // 4) Connection 객체 반납
        JDBCTemplate.close(conn);
        
        // 5) 결과 반환
        return result;
        
    }
    
    // 회원 정보 수정용 서비스
    public Member updateMember(Member m) {
        
        // 1) Connection 객체 생성
        Connection conn = JDBCTemplate.getConnection();
        
        // 2) 만들어진 Connection과 전달값을 DAO로 넘기면서 요청
        int result = new MemberDao().updateMember(conn, m);
        
        // DAO로 다녀온 result에는 1 또는 0이 담겨 있을 것
        
        Member updateMem = null;
        
        // 3) 트랜잭션 처리
        if(result > 0) { // 회원 정보 수정 성공 => commit
            JDBCTemplate.commit(conn);
            
            // 갱신된 회원 객체를 다시 조회해 오기
            updateMem = new MemberDao().selectMember(conn, m.getUserId());
            
        } else { // 회원 정보 수정 실패 => rollback
            JDBCTemplate.rollback(conn);
        }
        
        // 4) Connection 객체 반납
        JDBCTemplate.close(conn);
        
        // 5) 결과 리턴
        return updateMem; // 성공: 갱신된 회원의 정보, 실패: null
    }
    
    // 비밀번호 변경용 서비스
    public Member updatePwdMember(Member m, String updatePwd) {
        
        // 1) Connection 객체 생성
        Connection conn = JDBCTemplate.getConnection();
        
        // 2) 만들어진 Connection 객체와 전달값을 DAO로 넘겨서 요청 처리 후 결과받기
        int result = new MemberDao().updatePwdMember(conn, m, updatePwd);
        
        Member updateMem = null;
        
        // 3) 결과에 따른 트랜잭션 처리
        if(result > 0) { // 성공 => commit 
            JDBCTemplate.commit(conn);
            
            // 갱신된 회원의 정보 다시 조회하기
            updateMem = new MemberDao().selectMember(conn, m.getUserId());
            
        } else { // 실패 => rollback
            JDBCTemplate.rollback(conn);
        }
        
        // 4) Connection 객체 반납
        JDBCTemplate.close(conn);
        
        // 5) 결과 반환
        return updateMem; // 성공: 해당 갱신된 회원의 정보, 실패: null
    }
    
    // 회원 탈퇴용 서비스
    public int deleteMember(Member m) {
        
        // 1) Connection 객체 생성
        Connection conn = JDBCTemplate.getConnection();
        
        // 2) 만들어진 Connection 객체와 전달값을 DAO로 넘기고 결과받기
        int result = new MemberDao().deleteMember(conn, m);
        
        // 3) 결과에 따른 트랜잭션 처리
        if(result > 0) { // 성공 => commit
            JDBCTemplate.commit(conn);
            
        } else { // 실패 => rollback
            JDBCTemplate.rollback(conn);
        }
        
        // 4) Connection 객체 반납
        JDBCTemplate.close(conn);
        
        // 5) 결과 반환
        return result;
    }
    
    // 아이디 중복체크용 서비스
    public int idCheck(String checkId) {
        
        Connection conn = JDBCTemplate.getConnection();
        
        int count = new MemberDao().idCheck(conn, checkId);
        
        JDBCTemplate.close(conn);
        
        return count;
    }

}
