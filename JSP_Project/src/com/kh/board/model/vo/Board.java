package com.kh.board.model.vo;

import java.sql.Date;

public class Board {
    
    // 필드부
    private int boardNo;         // BOARD_NO NUMBER PRIMARY KEY,
    private int boardType;       // BOARD_TYPE NUMBER, -- 일반 게시판(1)이냐 사진 게시판(2)이냐
    private String category;     // CATEGORY_NO NUMBER,
                                 // 10 같은 카테고리 번호, "공통" 같은 카테고리명을 모두 담을 수 있어야 함 => String 타입으로
    private String boardTitle;   // BOARD_TITLE VARCHAR2(100) NOT NULL,
    private String boardContent; // BOARD_CONTENT VARCHAR2(4000) NOT NULL,
    private String boardWriter;  // BOARD_WRITER NUMBER NOT NULL,
    private int count;           // COUNT NUMBER DEFAULT 0,
    private Date createDate;     // CREATE_DATE DATE DEFAULT SYSDATE NOT NULL,
    private String status;       // STATUS VARCHAR2(1) DEFAULT 'Y' CHECK (STATUS IN('Y', 'N')),
    
    private String titleImg;
    
    // 생성자부
    public Board() { }

    public Board(int boardNo, int boardType, String category, String boardTitle, String boardContent,
            String boardWriter, int count, Date createDate, String status) {
        super();
        this.boardNo = boardNo;
        this.boardType = boardType;
        this.category = category;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardWriter = boardWriter;
        this.count = count;
        this.createDate = createDate;
        this.status = status;
    }
    
    public Board(int boardNo, String category, String boardTitle, String boardWriter, int count, Date createDate) {
        super();
        this.boardNo = boardNo;
        this.category = category;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.count = count;
        this.createDate = createDate;
    }
    
    // 일반게시글 상세조회용 생성자
    public Board(int boardNo, String category, String boardTitle, String boardContent, String boardWriter,
            Date createDate) {
        super();
        this.boardNo = boardNo;
        this.category = category;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardWriter = boardWriter;
        this.createDate = createDate;
    }

    // 메소드부
    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public int getBoardType() {
        return boardType;
    }

    public void setBoardType(int boardType) {
        this.boardType = boardType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public String getBoardWriter() {
        return boardWriter;
    }

    public void setBoardWriter(String boardWriter) {
        this.boardWriter = boardWriter;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    @Override
    public String toString() {
        return "Board [boardNo=" + boardNo + ", boardType=" + boardType + ", category=" + category + ", boardTitle="
                + boardTitle + ", boardContent=" + boardContent + ", boardWriter=" + boardWriter + ", count=" + count
                + ", createDate=" + createDate + ", status=" + status + "]";
    }

}
