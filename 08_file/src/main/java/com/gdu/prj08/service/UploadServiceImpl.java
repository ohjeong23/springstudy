package com.gdu.prj08.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.prj08.Dao.FileDao;
import com.gdu.prj08.Dto.HistoryDto;
import com.gdu.prj08.utils.MyFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

  private final MyFileUtils myFileUtils;
  private final FileDao fileDao;
  
  @Override
  public int upload1(MultipartHttpServletRequest multipartRequest) {
   
   String writer = multipartRequest.getParameter("wrtier");
   String ip = multipartRequest.getRemoteAddr();
   
   HistoryDto history = HistoryDto.builder()
   
   
    List<MultipartFile> files =  multipartRequest.getFiles("files");
    
    //첨부 파일 목록 순회
    for(MultipartFile multipartFile : files) {
      
      // 첨부 파일 존재 여부 확인 
      if(multipartFile != null && multipartFile.isEmpty()) {
        
        //첨부 파일 경로
        String uploadPath = myFileUtils.getUploadPath();
        
        //첨부 파일 경로 디렉터리 만들기
        File dir= new File(uploadPath);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        
        //첨부 파일의 원래 이름 
         String originalFileName = multipartFile.getOriginalFilename();
        
        //첨부 파일 저장 이름
        String filesystemName = myFileUtils.getFilesystemName(originalFileName);
        
        //첨부 파일 File 객체 만들기
        File file = new File(dir,filesystemName);
        
        // 저장
               
        try {
          multipartFile.transferTo(file);
          
        } catch (Exception e) {

        e.printStackTrace();
        
        }
        

      }
      
      
    }
//insertCount가 1 컨트롤러에 반환하는게 1 인설트카운트가 1을가지고 인덱스 제이에스피 메인점 두로 감
    return 1;
  }

  @Override
  public Map<String, Object> upload2(MultipartHttpServletRequest multipartRequest) {

 List<MultipartFile> files =  multipartRequest.getFiles("files");
    
    //첨부 파일 목록 순회
    for(MultipartFile multipartFile : files) {
      
      // 첨부 파일 존재 여부 확인 
      if(multipartFile != null && multipartFile.isEmpty()) {
        
        //첨부 파일 경로
        String uploadPath = myFileUtils.getUploadPath();
        
        //첨부 파일 경로 디렉터리 만들기
        File dir= new File(uploadPath);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        
        //첨부 파일의 원래 이름 
        String originalFileName = multipartFile.getOriginalFilename();
        
        //첨부 파일 저장 이름
        String filesystemName = myFileUtils.getFilesystemName(originalFileName);
        
        //첨부 파일 File 객체 만들기
        File file = new File(dir,filesystemName);
        
        // 저장
        //이 멀티파트파일을 위의 파일 파일 뉴파일의 폴더의 파일시스템네임에 보낸다.        
        try {
          multipartFile.transferTo(file);
          
        } catch (Exception e) {

        e.printStackTrace();
        
        }
        

      }
      
      
    }
    return Map.of("success",1);
  }
  
  
}
