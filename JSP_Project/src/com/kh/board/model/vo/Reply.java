package com.kh.board.model.vo;

public class Reply {
    
    private int replyNo;         // REPLY_NO NUMBER PRIMARY KEY,
    private String replyContent; // REPLY_CONTENT VARCHAR2(400) NOT NULL,
    private int refBoardNo;        // REF_BNO NUMBER NOT NULL,
    private String replyWriter;  // REPLY_WRITER NUMBER NOT NULL,
    private String createDate;   // CREATE_DATE DATE DEFAULT SYSDATE NOT NULL,
    private String status;       // STATUS VARCHAR2(1) DEFAULT 'Y' CHECK (STATUS IN ('Y', 'N')),
    
    
    public Reply() {
        super();
    }


    public Reply(int replyNo, String replyContent, int refBoardNo, String replyWriter, String createDate, String status) {
        super();
        this.replyNo = replyNo;
        this.replyContent = replyContent;
        this.refBoardNo = refBoardNo;
        this.replyWriter = replyWriter;
        this.createDate = createDate;
        this.status = status;
    }
    
    // 댓글 리스트 조회용 생성자
    public Reply(int replyNo, String replyContent, String replyWriter, String createDate) {
        super();
        this.replyNo = replyNo;
        this.replyContent = replyContent;
        this.replyWriter = replyWriter;
        this.createDate = createDate;
    }

    public int getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(int replyNo) {
        this.replyNo = replyNo;
    }


    public String getReplyContent() {
        return replyContent;
    }


    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }


    public int getRefBoardNo() {
        return refBoardNo;
    }


    public void setRefBoardNo(int refBoardNo) {
        this.refBoardNo = refBoardNo;
    }


    public String getReplyWriter() {
        return replyWriter;
    }


    public void setReplyWriter(String replyWriter) {
        this.replyWriter = replyWriter;
    }


    public String getCreateDate() {
        return createDate;
    }


    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Reply [replyNo=" + replyNo + ", replyContent=" + replyContent + ", refBoardNo=" + refBoardNo
                + ", replyWriter=" + replyWriter + ", createDate=" + createDate + ", status=" + status + "]";
    }

}