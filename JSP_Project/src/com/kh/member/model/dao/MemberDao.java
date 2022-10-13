package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		// member-mapper.xml의 쿼리문을 불러오기 위한 객체 생성
				
				// src 기준이 아니라 컴파일된 .class 기준으로 경로 잡아 줘야 배포 후에도 실행 가능
				String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();
				
				try {
					prop.loadFromXML(new FileInputStream(fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}

	}
	
	public Member loginMember(Connection conn, Member m) {
		
		// SELECT 문 => ResultSet 객체 (unique 제약조건에 의해 많아 봤자 한 건만 조회됨) => Member 객체
		
		// 1) 필요한 변수를 먼저 세팅
		Member loginUser = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("loginMember");
		
		try {
			// 2) 쿼리문 실행에 필요한 PreparedStatement 객체를 생성하기
			// 완성이든 미완성이든 쿼리문 넘겨서 대기
			pstmt = conn.prepareStatement(sql);
			
			// 3_1) 미완성된 쿼리문일 경우 완성시키기
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			
			// 3_2) 쿼리문 실행 후 결과받기
			rset = pstmt.executeQuery();
			
			// 4) rset으로부터 커서를 움직여 가며 값 뽑아내기
			if(rset.next()) {
				
				// member 클래스의 생성자 순서에 맞춰서 호출
				loginUser = new Member(rset.getInt("USER_NO"),
									   rset.getString("USER_ID"),
									   rset.getString("USER_PWD"),
									   rset.getString("USER_NAME"),
									   rset.getString("PHONE"),
									   rset.getString("EMAIL"),
									   rset.getString("ADDRESS"),
									   rset.getString("INTEREST"),
									   rset.getDate("ENROLL_DATE"),
									   rset.getDate("MODIFY_DATE"),
									   rset.getString("STATUS")
									   );
				
			}
			
			// 이 시점 기준으로
			// 만약 일치하는 회원을 찾았다면 loginUser에는 해당 회원의 정보가 다 담겨 있을 것
			// 만약 일치하는 회원을 못 찾았다면 loginUser에는 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5) 자원 반납 (생성된 순서의 역순)
			// pstmt -> rset 순서로 생성됨
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
			
		}
		
		// 6) 결과 리턴
		return loginUser;
	}

}
