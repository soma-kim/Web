package com.kh.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

// 인터페이스 구현 => 반드시 미완성된 메소드를 구현해서 써야 됨 (오버라이딩)
public class MyFileRenamePolicy implements FileRenamePolicy {

    // 기존의 파일을 전달받아서 파일명 수정 작업 후 수정된 파일 자체를 return 해 줄 것!
    @Override
    public File rename(File originFile) {
        
        // 원본파일명("aaa.jpg")
        String originName = originFile.getName();
        
        // 수정 파일명
        // => 파일 업로드 시간 (년월일시분초) + 5자리 랜덤값 (10000 ~ 99999)
        // => 최대한 이름이 겹치지 않게!
        
        // 확장자
        // => 원본파일의 확장자 그대로
        
        //  원본명                 ------->             수정명
        // aaa.jpg                20221020163855xxxxx.jpg
        
        // 1. 파일이 업로드된 시간 (년월일시분초)
        // *주의* java.util.Date로 import 해 줘야 함
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        
        // 2. 5자리 랜덤값
        int ranNum = (int)(Math.random() * 90000) + 10000;
        
        // 3. 원본파일 확장자
        // => 파일명 중간에 .이 들어가는 경우도 있기 때문에
        //    원본 파일명에서 가장 맨 마지막에 나오는 . 기준으로 파일명과 확장자를 나눔 
        String ext = originName.substring(originName.lastIndexOf("."));
        
        // 결합
        String changeName = currentTime + ranNum + ext;
        
        // 원본파일(originFile)을 수정된 파일명으로 적용시켜서 파일객체로 변환
        return new File(originFile.getParent(), changeName);
    }
    
}