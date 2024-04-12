package com.gdu.myapp.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myapp.dto.AttachDto;
import com.gdu.myapp.dto.UploadDto;
import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UploadMapper;
import com.gdu.myapp.utils.MyFileUtils;
import com.gdu.myapp.utils.MyPageUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

  private final UploadMapper uploadMapper;
  private final MyPageUtils myPageUtils;
  private final MyFileUtils myFileUtils;
  
  @Override
  public boolean registerUpload(MultipartHttpServletRequest multipartRequest) {

    // UPLOAD_T 테이블에 추가하기
    String title = multipartRequest.getParameter("title");
    String contents = multipartRequest.getParameter("contents");
    int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
    
    UserDto user = new UserDto();
    user.setUserNo(userNo);
    
    UploadDto upload = UploadDto.builder()
                          .title(title)
                          .contents(contents)
                          .user(user)
                        .build();
    
    System.out.println("INSERT 이전 : " + upload.getUploadNo());  // uploadNo 없음
    int insertUploadCount = uploadMapper.insertUpload(upload);
    System.out.println("INSERT 이후 : " + upload.getUploadNo());  // uploadNo 있음 (<selectKey> 동작에 의해서)
    
    // 첨부 파일 처리하기
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    // 첨부 파일이 없는 경우 : [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]]
    // 첨부 파일이 있는 경우 : [MultipartFile[field="files", filename=404.jpg, contentType=image/jpeg, size=63891]]
    // System.out.println(files);

    int insertAttachCount;
    if(files.get(0).getSize() == 0) {
      insertAttachCount = 1;  // 첨부가 없어도 files.size() 는 1 이다.
    } else {
      insertAttachCount = 0;
    }
    
    for (MultipartFile multipartFile : files) {
      
      if(multipartFile != null && !multipartFile.isEmpty()) {
        
        String uploadPath = myFileUtils.getUploadPath();
        File dir = new File(uploadPath);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        String originalFilename = multipartFile.getOriginalFilename();
        String filesystemName = myFileUtils.getFilesystemName(originalFilename);
        File file = new File(dir, filesystemName);
        
        try {

          multipartFile.transferTo(file);
          
          // 썸네일 작성
          String contentType = Files.probeContentType(file.toPath());
          int hasThumbnail = contentType != null && contentType.startsWith("image") ? 1 : 0;
          
          // 이미지의 썸네일 만들기
          if(hasThumbnail == 1) {
            File thumbnail = new File(dir, "s_" + filesystemName);
            Thumbnails.of(file)             // 원본 이미지 파일
                      .size(96, 64)         // 가로 96px, 세로 64px
                      .toFile(thumbnail);   // 썸네일 이미지 파일
          }
          
          // ATTACH_T 테이블에 추가하기
          AttachDto attach = AttachDto.builder()
                                .uploadPath(uploadPath)
                                .filesystemName(filesystemName)
                                .originalFilename(originalFilename)
                                .hasThumbnail(hasThumbnail)
                                .uploadNo(upload.getUploadNo())
                              .build();
          
          insertAttachCount += uploadMapper.insertAttach(attach);
          
        } catch (Exception e) {
          e.printStackTrace();
        }
        
      }  // if
      
    }  // for
    
    return (insertUploadCount == 1) && (insertAttachCount == files.size());
    
  }

  @Override
  public void loadUploadList(Model model) {
     
    /*
     * interface UploadService.java
     *     {
     *     서비스에서 모델에 있는거 꺼내씀 
     *     모델로만 모든걸 받아오는 경우가 있음
     *     필요한 파라미터를 가져오면 되니까
     *    
     *    모델에 리퀘스트를 넣어놓고 가져다 쓰는경우가 있을 수 있다.
     *    
     *     
     *     
     *    하나의 메소드만 가지고 있을 경우도 있다.
     *    void execute(Model model);
     *    }
     *    
     *    class UploadRegisterService implements UploadService{
     *    @Override
     *    void execute(Model model) {
     *    
     *    }
     *    }
     *    
     *    class UploadListService implements UploadService {
     *     @Override
     *     void execute(Model model){
     *     }
     *     }    
     *    
     *    
     */
    
    
    
    
    Map<String, Object> modelMap = model.asMap();
    HttpServletRequest request = (HttpServletRequest)modelMap.get("request");  //저장된 타입으로 반환되는데 오브젝트로 되어있다.
   
    //  
    int total = uploadMapper.getUploadCount();
    
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    
    //int display = 20; //옵셔널로 20
    int display = Integer.parseInt(optDisplay.orElse("20"));
    
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
    
    
    myPageUtils.setPaging(total, display, page);
    //쿼리로 넘기고 싶은 데이터 3개 소트 정렬방식으로 옵션얼 처리
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC"); // 내림차순 으로 전달이 안되면 내림차순 전달되면 전달 된 대로
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin(),
        "end", myPageUtils.getEnd(),
        "sort", sort);
    
    /*
     * 
     * 
     * total = 100, display = 20
     * 
     * page  beginNo (세팅이 되는 시작 번호)
     * 1       100
     * 2        80
     * 3        60
     * 4        40
     * 5        20
     */
    
    
    
    
    //키 벨류
    //리스트 제이에스 피로 넘겨 주고 싶은 값들 
    model.addAttribute("beginNo", total - (page - 1) * display); 
    model.addAttribute("uploadList",uploadMapper.getUploadList(map));
    model.addAttribute("paging", myPageUtils.getPaging(request.getContextPath() + "/upload/list.do", sort, display));
    //목록보기하는 주소 넣는거다.    //소트 디스플레이 리퀘스트 유알아이만 만들면 소트하고 디스플레인는자동으로 삽입된다.
    //전체 몇페이지인지 단순하게 말고 옵션을 사용해야함 페이지 전달이 안될 수 있다.
    //모델에 전달해 준것 . 이거 두개로 서비스가 되었고 컨트롤러 단에 가서 서비스 호출이미 함
    
    model.addAttribute("display", display);

    //디스플레이가 얼마인지 다시 돌려주는지
    model.addAttribute("sort",sort); // 화면에서 이엘ㄹ로 사용가능 디스 제이에스피 디스플레이랑 소트를 이엘로 사용가능
    model.addAttribute("page",page);//패이지도 받아옴
  }
  
  @Override
  public void loadUploadByNo(int uploadNo, Model model) { // 상세보기 업로드의 상세 하단에 첨부 
    model.addAttribute("upload", uploadMapper.getUploadByNo(uploadNo));
    model.addAttribute("attachList",uploadMapper.getAttachList(uploadNo));
    
    
    
    
  }
  @Override
  public ResponseEntity<Resource> download(HttpServletRequest request) {
    
    //첨부 파일의 정보를 가져오기 디비에서 
    int attachNo = Integer.parseInt(request.getParameter("attachNo"));
    AttachDto attach = uploadMapper.getAttachByNo(attachNo);
    //이 첨부 파일을 정보를 File  객체로 만든 뒤 resource  객체로 변환
    File file = new File(attach.getUploadPath(), attach.getFilesystemName());
    Resource resource = new FileSystemResource(file);
    
    // 첨부 파일이 없으면 다운로드 취소
    if(!resource.exists()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // DOWNLOAD_COUNT 증가
    uploadMapper.updateDownloadCount(attachNo);
    //사용자가 다운로드 받을 파일명 결정 (오리지널 파일명 사용 originalFilename 을 브라우저에 따라 다르게 인코딩 처리 하겠ㅏ다.
    String originalFilename = attach.getOriginalFilename();
    String userAgent = request.getHeader("User-Agent");
    try {
      // IE 
      if(userAgent.contains("Trident")) { //공백을 플러스로 플러스를 공백으로 
      originalFilename = URLEncoder.encode(originalFilename,"UTF-8").replace("+", " ");   
      }
      // Edge
      else if(userAgent.contains("Edg")) {
         originalFilename = URLEncoder.encode(originalFilename, "UTF-8"); 
      }
      // Other 
      else {
        originalFilename = new String(originalFilename.getBytes(), "ISO-8859-1");
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    
    }
    //다운로드용 응답 헤더 설정 (HTTP 참조)
    HttpHeaders responseHeader = new HttpHeaders();
    responseHeader.add("Content-Type", "application/octet-stream");
    responseHeader.add("Content-Disposition", "attachment; filename=" + originalFilename);
    responseHeader.add("Content-Length",file.length() + ""); // 
    
    //다운로드 진행 헤더가 필요 하니까 3개짜리
    return new ResponseEntity<Resource>(resource, responseHeader,HttpStatus.OK);
  }
  @Override
  public ResponseEntity<Resource> downloadAll(HttpServletRequest request) {
    
    //다운로드 할 모든 첨부 파일들의 정보를 DB 에서 가져오기
     int uploadNo = Integer.parseInt(request.getParameter("uploadNo"));
     List<AttachDto> attachList = uploadMapper.getAttachList(uploadNo);    //해당 게시물의 정보들 리스트를 다 가져옴
   
     // 첨부 파일이 없으면 종료
     if(attachList.isEmpty()) {
       return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
     }
     
     //임시 zip 파일 저장할 경로
     File tempDir = new File(myFileUtils.getTempPath());
     if(!tempDir.exists()) {
       tempDir.mkdirs();
       
     }
     //임시zip 파일 이름
     String tempFilename = myFileUtils.getTempFilename() + ".zip";
     
     //임시 zip 파일 file객체
     
     File tempFile = new File(tempDir, tempFilename);
       
     
     
     
     //첨부 파일들을 하나씩  zip 파일로 모으기
     try {
 
       // zipoOutputStream 객체 생성
       ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(tempFile));
       
       for (AttachDto attach : attachList) {
         //zip 파일에 포함할 zipEntry 객체 생성
         
         ZipEntry zipEntry = new ZipEntry(attach.getOriginalFilename());
             
         
         //zip 파일에 ZipEntry 객체 명단 추가
         zout.putNextEntry(zipEntry);   
         
         
         //실제 첨부 파일을 zip 파일에 등록 ,첨부 파일을 읽어서 zip 파일로 보냄
         BufferedInputStream in = new BufferedInputStream( new FileInputStream(new File(attach.getUploadPath(), attach.getFilesystemName())));
         zout.write(in.readAllBytes());
         
         //사용한 자원 정리
         in.close();
         zout.closeEntry();
       
        // 다운로드 횟수 DOWNLOAD_COUNT 증가
         uploadMapper.updateDownloadCount(attach.getAttachNo());
         
         
       } // for
       
       
       
       //zout 자원 반납
       zout.close(); //클로즈 자원 잘 반납하기.
       
       
       
    } catch (Exception e) {

     e.printStackTrace();    
    
      
    }
     
     //다운로드 할 zip File 객체 -> Resource 객체
     Resource resource = new FileSystemResource(tempFile);
     
     //다운로드용 응답 헤더 설정 (HTTP 참조)
     HttpHeaders responseHeader = new HttpHeaders();
     responseHeader.add("Content-Type", "application/octet-stream");
     responseHeader.add("Content-Disposition", "attachment; filename=" + tempFilename);
     responseHeader.add("Content-Length",tempFile.length() + ""); // 
     
     //다운로드 진행 헤더가 필요 하니까 3개짜리
     return new ResponseEntity<Resource>(resource, responseHeader,HttpStatus.OK);
     
    //스케쥴러 정리할거 
  }
  
  
  @Override
  public void removeTempFiles() {

    // 임시 폴더의 모든 파일 제거
    File tempDir = new File(myFileUtils.getTempPath());
    File[] tempFiles = tempDir.listFiles();
    if(tempFiles != null) {
      for(File tempFile : tempFiles) {
        tempFile.delete();
      }
    } 
  }

}