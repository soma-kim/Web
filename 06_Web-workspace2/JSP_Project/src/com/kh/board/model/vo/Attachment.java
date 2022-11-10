package com.kh.board.model.vo;

import java.sql.Date;

public class Attachment {
    
    // 필드부
    private int fileNo;        // FILE_NO NUMBER PRIMARY KEY,
    private int refNo;         // REF_BNO NUMBER NOT NULL,
    private String originName; // ORIGIN_NAME VARCHAR2(255) NOT NULL,
    private String changeName; // CHANGE_NAME VARCHAR2(255) NOT NULL,
    private String filePath;   // FILE_PATH VARCHAR2(1000),
    private Date uploadDate;   // UPLOAD_DATE DATE DEFAULT SYSDATE NOT NULL,
    private int fileLevel;     // FILE_LEVEL NUMBER,
    private String status;     // STATUS VARCHAR2(1) DEFAULT 'Y' CHECK(STATUS IN('Y', 'N')),
    
    // 생성자부
    public Attachment() { }

    public Attachment(int fileNo, int refNo, String originName, String changeName, String filePath, Date uploadDate,
            int fileLevel, String status) {
        super();
        this.fileNo = fileNo;
        this.refNo = refNo;
        this.originName = originName;
        this.changeName = changeName;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
        this.fileLevel = fileLevel;
        this.status = status;
    }
    
    // 메소드부
    public int getFileNo() {
        return fileNo;
    }

    public void setFileNo(int fileNo) {
        this.fileNo = fileNo;
    }

    public int getRefNo() {
        return refNo;
    }

    public void setRefNo(int refNo) {
        this.refNo = refNo;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(int fileLevel) {
        this.fileLevel = fileLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attachment [fileNo=" + fileNo + ", refNo=" + refNo + ", originName=" + originName + ", changeName="
                + changeName + ", filePath=" + filePath + ", uploadDate=" + uploadDate + ", fileLevel=" + fileLevel
                + ", status=" + status + "]";
    }
    
}
