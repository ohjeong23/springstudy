package com.gdu.prj08.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.prj08.service.UploadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UploadController {


  private final UploadService uploadService;
   
  @PostMapping("/upload1.do")
  public String upload1(MultipartHttpServletRequest multipartRequest
                                 ,RedirectAttributes redirectAttributes) {//리다이렉트할때 플래시 에트리부트를 써서 하면 여기서도 확인가능
    //서비스로 넘기기 멀티파트를 넘겨서 실제로는 서비스가 하도록
    
    
    int insertCount = uploadService.upload1(multipartRequest);
    redirectAttributes.addFlashAttribute("insertCount", insertCount); //요기
    return "redirect:/main.do"; // 요기
  }
  @ResponseBody //비동기 응답 데이터 자체
  @PostMapping(value ="/upload2.do", produces="application/json") // 이 맵이 제이슨이다 알려줌
  public Map<String, Object> upload2(MultipartHttpServletRequest multipartRequest){
    return uploadService.upload2(multipartRequest); 
    
    //반환값에다가 뷰리졸버를 무조건 갖다붙임 되도 안되는거 됨
    //이래야 맵을 데이터로 봐서 되는겨, 제이슨이 아님 변환 과정이 필요하다. 잭슨이라는 디펜던시가 맵을 자동으로 제이슨데이터로 바꿔줌 
    //반대도 가능 
  }
/* 이것도 가능하다 
  @PostMapping(value ="/upload2.do", produces="application/json") // 이 맵이 제이슨이다 알려줌
  public ResponseEntity<Map<String, Object>> upload2(MultipartHttpServletRequest multipartRequest){
    return new ResponseEntity<T>(Map.of("success",1),HttpStatus.OK);//200 응답코드 
  }*/
}
