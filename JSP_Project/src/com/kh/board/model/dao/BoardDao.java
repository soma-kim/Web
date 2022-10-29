package com.kh.board.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.board.model.vo.Reply;
import com.kh.common.model.vo.PageInfo;

public class BoardDao {
    
    private Properties prop = new Properties();
    
    public BoardDao() {
        String fileName = BoardDao.class.getResource("/sql/board/board-mapper.xml").getPath();
        try {
        prop.loadFromXML(new FileInputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int selectListCount(Connection conn) {
        
        // SELECT문 => ResultSet 객체 (그룹함수를 써서 한 행 조회)
        
        int listCount = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectListCount");
        
        try {
            pstmt = conn.prepareStatement(sql);
            rset = pstmt.executeQuery();
            
            // while(rset.next()) 로 뽑아왔지만 지금은 107이라는 1개의 행으로 조회되므로 if문 씀
            
            if(rset.next()) {
                listCount = rset.getInt("COUNT");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        
        return listCount;
        
    }
    
    public ArrayList<Board> selectList(Connection conn, PageInfo pi) {
        
        // SELECT문 => ResultSet 객체 (여러 행 조회)
        
        ArrayList<Board> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectList");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            /*
             * boardLimit가 10이라는 가정 하에
             * currentPage = 1 => 시작값   1, 끝값 10
             * currentPage = 2 => 시작값 11, 끝값 20
             * currentPage = 3 => 시작값 21, 끝값 30
             * 
             * 시작값 = (currentPage - 1) * boardLimit + 1
             * 끝값 = 시작값 + boardLimit - 1
             */
            int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
            int endRow = startRow + pi.getBoardLimit() - 1;
            
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, endRow);
            
            rset = pstmt.executeQuery();
            
            while(rset.next()) {
                
                list.add(new Board(rset.getInt("BOARD_NO")
                                 , rset.getString("CATEGORY_NAME")
                                 , rset.getString("BOARD_TITLE")
                                 , rset.getString("USER_ID")
                                 , rset.getInt("COUNT")
                                 , rset.getDate("CREATE_DATE")));
            }
                        
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return list;
    }
    
    public ArrayList<Category> selectCategoryList(Connection conn) {
        
        // SELECT문 => ResultSet 객체 (여러 행 조회)
        
        ArrayList<Category> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet  rset = null;
        
        String sql = prop.getProperty("selectCategoryList");
        
        try {
            pstmt = conn.prepareStatement(sql);
            rset = pstmt.executeQuery();
            
            while(rset.next()) {
                
                list.add(new Category(rset.getInt("CATEGORY_NO")
                                    , rset.getString("CATEGORY_NAME")));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        
        return list;
        
    }
    
    public int insertBoard(Connection conn, Board b) {
        
        // INSERT문 => int (처리된 행의 갯수)
        
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("insertBoard");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, Integer.parseInt(b.getCategory()));
            pstmt.setString(2, b.getBoardTitle());
            pstmt.setString(3, b.getBoardContent());
            pstmt.setInt(4, Integer.parseInt(b.getBoardWriter()));
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            // 여기서 conn 반납하면 절대 안 됨!!!
            // 이 트랜잭션에서 2개의 insert문을 실행하기 때문에 하나라도 실패하면 rollback 되어 버림
        }
        return result;
    }
    
    public int insertAttachment(Connection conn, Attachment at) {
        
        // INSERT문 => int (처리된 행의 개수)
        
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("insertAttachment");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, at.getOriginName());
            pstmt.setString(2, at.getChangeName());
            pstmt.setString(3, at.getFilePath());
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        
        return result;
                
    }
    
    public int increaseCount(Connection conn, int boardNo) {
        
        // UPDATE문 => int(처리된 행의 개수)
        
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("increaseCount");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, boardNo);
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        
        return result;
        
    }
    
    public Board selectBoard(Connection conn, int boardNo) {
        
        // SELECT문 => ResultSet 객체 (많아 봤자 1행 조회)
        
        Board b = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectBoard");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, boardNo);
            
            rset = pstmt.executeQuery();
            
            if(rset.next()) {
                
                b = new Board(rset.getInt("BOARD_NO")
                            , rset.getString("CATEGORY_NAME")
                            , rset.getString("BOARD_TITLE")
                            , rset.getString("BOARD_CONTENT")
                            , rset.getString("USER_ID")
                            , rset.getDate("CREATE_DATE"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            close(rset);
            close(pstmt);
        }
        
        return b;

    }
    
    public Attachment selectAttachment(Connection conn, int boardNo) {
        
        // SELECT문 => ResultSet 객체 (1행 조회)
        
        Attachment at = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectAttachment");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, boardNo);
            
            rset = pstmt.executeQuery();
            
            if(rset.next()) {
                
                at = new Attachment();
                at.setFileNo(rset.getInt("FILE_NO"));
                at.setOriginName(rset.getString("ORIGIN_NAME"));
                at.setChangeName(rset.getString("CHANGE_NAME"));
                at.setFilePath(rset.getString("FILE_PATH"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        
        return at;

    }
    
    public int updateBoard(Connection conn, Board b) {
        
        // UPDATE문 => int (처리된 행의 개수)
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("updateBoard");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, Integer.parseInt(b.getCategory()));
            pstmt.setString(2, b.getBoardTitle());
            pstmt.setString(3, b.getBoardContent());
            pstmt.setInt(4, b.getBoardNo());
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        
        return result;
        
    }
    
    public int updateAttachment(Connection conn, Attachment at) {
        
        // UPDATE문 => int (처리된 행의 개수)
        
        int result = 0;
        PreparedStatement pstmt = null;
        String sql = prop.getProperty("updateAttachment");
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, at.getOriginName());
            pstmt.setString(2, at.getChangeName());
            pstmt.setInt(3, at.getFileNo());
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        
        return result;
    }
    
    public int insertNewAttachment(Connection conn, Attachment at) {
        
        // INSERT문 => int (처리된 행의 개수)
        
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("insertNewAttachment");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, at.getRefNo());
            pstmt.setString(2, at.getOriginName());
            pstmt.setString(3, at.getChangeName());
            pstmt.setString(4, at.getFilePath());
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }
    
    public int deleteBoard(Connection conn, int boardNo) {
        
        // UPDATE문 -> int (처리된 행의 개수)
        
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("deleteBoard");
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, boardNo);
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        
        return result;
    }
    
    public int insertThumbnailBoard(Connection conn, Board b) {
        
        // INSERT문 => int (처리된 행의 개수)
        
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("insertThumbnailBoard");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, b.getBoardTitle());
            pstmt.setString(2, b.getBoardContent());
            pstmt.setInt(3, Integer.parseInt(b.getBoardWriter()));
            
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        
        return result;        
    }
    
    public int insertAttachmentList(Connection conn, ArrayList<Attachment> list) {
        
        // INSERT문 여러 번 => int (처리된 행의 개수)
        
        int result = 1;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("insertAttachmentList");
        
        try {
        // DAO 메소드 하나로 동일한 쿼리문을 여러 번 실행해야 함
        // => 쿼리문을 실행할 때마다 pstmt를 생성해야 함
        // => list에 담긴 Attachment의 개수만큼 반복 돌리기
        
        // list에서 하나씩 뽑아서 at에 담겠다
        for(Attachment at : list) {
            
            pstmt = conn.prepareStatement(sql);
            
            // 쿼리문 완성시키기
            pstmt.setString(1,  at.getOriginName());
            pstmt.setString(2, at.getChangeName());
            pstmt.setString(3, at.getFilePath());
            pstmt.setInt(4,  at.getFileLevel());
            
            // 쿼리문 실행 후 결과 받기(결과를 누적곱으로 => 하나라도 실패하면 0, 시작(초기화)값은 1이 되어야 함!)
            result *= pstmt.executeUpdate();

        }
            
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                
                close(pstmt);
            }
            return result;
    }
    
    public ArrayList<Board> selectThumbnailList(Connection conn) {
        
        // SELECT문 => ResultSet 타입의 객체로 리턴 (여러 행 조회)
        
        ArrayList<Board> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectThumbnailList");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            rset = pstmt.executeQuery();
            
            while(rset.next()) {
                
                Board b = new Board();
                b.setBoardNo(rset.getInt("BOARD_NO"));
                b.setBoardTitle(rset.getString("BOARD_TITLE"));
                b.setCount(rset.getInt("COUNT"));
                b.setTitleImg(rset.getString("TitleImg"));
                
                list.add(b);
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            close(rset);
            close(pstmt);
        }
        
        return list;

    }
    
    public ArrayList<Attachment> selectAttachmentList(Connection conn, int boardNo) {
        
        // SELECT문 => ResultSet 객체 (여러 행 조회)
        
        ArrayList<Attachment> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        // 일반 게시판 상세조회 시 썼던 쿼리문 재활용
        String sql = prop.getProperty("selectAttachment");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, boardNo);
            
            rset = pstmt.executeQuery();
            
            while(rset.next()) {
                
                Attachment at = new Attachment();
                
                at.setFileNo(rset.getInt("FILE_NO"));
                at.setOriginName(rset.getString("ORIGIN_NAME"));
                at.setChangeName(rset.getString("CHANGE_NAME"));
                at.setFilePath(rset.getString("FILE_PATH"));
                
                list.add(at);
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            close(rset);
            close(pstmt);
        }
        
        return list;
    }
    
    public ArrayList<Reply> selectReplyList(Connection conn, int boardNo) {
        
        // SELECT문 => ResultSet 객체(여러 행 조회)
        
        ArrayList<Reply> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectReplyList");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, boardNo);
            
            rset = pstmt.executeQuery();
            
            while(rset.next()) {
                list.add(new Reply(rset.getInt("REPLY_NO"),
                                   rset.getString("REPLY_CONTENT"),
                                   rset.getString("USER_ID"),
                                   rset.getString("CREATE_DATE")));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        
        return list;
        
    }
    
    public int insertReply(Connection conn, Reply r) {
        
        // INSERT문 => int (처리된 행의 개수)
        
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = prop.getProperty("insertReply");
        
        try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, r.getReplyContent());
            pstmt.setInt(2, r.getRefBoardNo());
            pstmt.setInt(3, Integer.parseInt(r.getReplyWriter()));
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }
}