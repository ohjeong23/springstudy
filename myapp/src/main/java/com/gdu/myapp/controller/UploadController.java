package com.gdu.myapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.myapp.service.UploadService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/upload")
@RequiredArgsConstructor
@Controller
public class UploadController {

  private final UploadService uploadService;

  @GetMapping("/list.do")
  public String list(HttpServletRequest request, Model model) {
    model.addAttribute("request", request);
    uploadService.loadUploadList(model); //서비스 호출
    return "upload/list";
    
    
    //목록을 만들어서 확인하는거 까지
  }
  
  @GetMapping("/write.page")
  public String write() {
    return "upload/write";
  }
  
  @PostMapping("/register.do")
  public String register(MultipartHttpServletRequest multipartRequest
                       , RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("inserted", uploadService.registerUpload(multipartRequest));
    return "redirect:/upload/list.do";
  } //인설티드 값을 가지고 리스트로가서 트루면 값있고 없으면 0 메시지 나오는거
  
  @GetMapping("/detail.do")
  public String detail(@RequestParam(value="uploadNo", required=false, defaultValue="0") int uploadNo
                     , Model model) {
    uploadService.loadUploadByNo(uploadNo, model);
    return "upload/detail";
  }
  
  
  @GetMapping("/download.do")
  public ResponseEntity<Resource> download(HttpServletRequest request){
    return uploadService.download(request); 
  }
  
  @GetMapping("/downloadAll.do")
  public ResponseEntity<Resource> downloadAll(HttpServletRequest request){
    return uploadService.downloadAll(request); 
  }
  
  
  
  
  
  
}