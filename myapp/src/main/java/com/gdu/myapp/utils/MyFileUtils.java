package com.gdu.myapp.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;

 
@Component //bean등록 new MyFileUtils를 //서비스가 가져다 씀
public class MyFileUtils {

  
  // 현재 날짜
  
  public static final LocalDate TODAY = LocalDate.now(); 
  
  
  
  
  // 업로드 경로를 반환할 메소드
  
  public String getUploadPath() {
    
    return "/upload" + DateTimeFormatter.ofPattern("/yyyy/MM/dd").format(TODAY);  
  //씨드라이브 밑에 업로드 밑에 년도 월 일   
  }
  
  
  
  
  // 원래 저장되어 있는 파일명이 아니라 저장될 파일명을 반환할 메소드
  public String getFilesystemName(String originalFilename) {
    //원래 파일이름 받아와서 새로운 이름으로 변형시킴
    // 파일명.확장자 분리되어있음 , 원래 파일명은 버리고 확장자만 살려서 쓸예정
    //
    String extName = null;
    if(originalFilename.endsWith("tar.gz")) {
      extName = ".tar.gz";
     //확장자에 . 이 있는 애들은 이렇게 예외처리 해주는게 좋다. 
    }else {
     //나머지는 . 뒤의 걸로
      extName = originalFilename.substring(originalFilename.lastIndexOf("."));
    }//charSequence는 그냥 스트링이라고 봐도 됨
    return UUID.randomUUID().toString().replace("-", "")+extName;
    //중복 없이    
    //원래 이름이ㅇ 인데 다른이름으로 . 디비에 다시 저장
    //데이터 베이스 태그를 붙이기.
    
  }
  
  
  
  
  
  
  
}
