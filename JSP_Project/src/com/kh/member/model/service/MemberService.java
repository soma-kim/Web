package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;
import com.kh.member.model.dao.MemberDao;

public class MemberService {
	
	// 로그인 요청 서비스
	public Member loginMember(Member m) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) 요청 시 전달값과 만들어진 Connection 객체를 DAO의 메소드한테 전달
		Member loginUser = new MemberDao().loginMember(conn, m);
		
		// 3) INSERT, UPDATE, DELETE문이라면 commit / rollback 처리
		// 로그인은 SELECT 구문이므로 해당 과정 필요 없음!
		
		// 4) Connection 자원 반납
		JDBCTemplate.close(conn);		
		
		// 5) 결과 리턴
		return loginUser;
	}

}
