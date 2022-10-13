package com.kh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// 자주 쓰이는 JDBC 관련한 반복적인 코드를 메소드 단위별로 묶어서 정의 => static => 싱글톤 패턴
public class JDBCTemplate {
	
	// 1. Connection 객체 생성 (DB 접속) 후 해당 Connection 객체를 반환하는 메소드
	// 동적 코딩 방식
	public static Connection getConnection() {
		
		// driver.properties 파일로부터 key에 대한 value 값들을 읽어오기
		// => Properties 객체가 필요함 (Map 계열 컬렉션)
		Properties prop = new Properties();
		
		// 읽어들이고자 하는 driver.properties 파일의 물리적인 경로
		// => 지금 내가 작업 중인 폴더는 src 폴더가 아닌 배포되는 폴더 기준 (=실행되는 폴더 기준)으로 잡아야 함!
		//    나중에 배포 시에는 src 폴더는 주지 않기 때문에 그때는 경로를 찾지 못함!
		String fileName = JDBCTemplate.class.getResource("/sql/driver/driver.properties").getPath();
		// .class까지의 의미: 이 클래스의 컴파일된 파일 / WebContent.WEB-INF.classes(슬래시가 의미하는 곳).com.kh.common.JDBCTemplate
		// .getResource()까지의 의미: 괄호 안의 클래스 파일로부터 다른 파일을 얻어내겠다
		//						  => ~~.class.getResource()이므로 class의 최상위 경로인 classes가 슬래시가 의미하는 바가 됨
		// .getPath()까지의 의미: 해당 파일의 문자열 타입의 풀 경로
		
		// C:\06_Web-workspace2\JSP_Project\WebContent\WEB-INF\classes\sql\driver\driver.properties
		// 현재 fileName에는 위 경로가 담겨 있음
		
		try {
			prop.load(new FileInputStream(fileName));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		
		try {
			// 1) JDBC Driver 등록
			Class.forName(prop.getProperty("driver"));
			
			// 2) DB와 접속된 Connection 객체를 생성
			conn = DriverManager.getConnection(prop.getProperty("url")
												, prop.getProperty("username")
												, prop.getProperty("password"));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) { // 부모 클래스 타입이 더 밑에 있어야 Unreachable Code가 되지 않음!
			e.printStackTrace();
		}
		
		return conn;
		
		/*
		// 정적 코딩 방식
		 
		Connection conn = null;
		
		try {
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) DB와 접속된 Connection 객체를 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SERVER", "SERVER");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) { // 부모 클래스 타입이 더 밑에 있어야 Unreachable Code가 되지 않음!
			e.printStackTrace();
		}
		
		return conn;
		*/

	}
	
	// 2. 전달받은 Connection 객체를 가지고 commit 해 주는 메소드
	public static void commit(Connection conn) {
		
		try {
			if(conn != null && conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 3. 전달받은 Connection 객체를 가지고 rollback 해 주는 메소드
	public static void rollback(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 4. 전달받은 Connection 객체를 반납시켜 주는 메소드
	public static void close(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 5. 전달받은 Statement 객체를 반납시켜 주는 메소드
	// => 다형성에 의해 자식 클래스인 PreparedStatement 객체도 반납 가능함
	public static void close(Statement stmt) { // 오버로딩에 의해 같은 메소드명이 허용
		
		try {
			if(stmt != null && !stmt.isClosed()) {
					stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 6. 전달받은 ResultSet 객체를 반납시켜 주는 메소드
	public static void close(ResultSet rset) {
		
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
