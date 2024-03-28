package com.gdu.prj10.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.prj10.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
 private final BoardService boardService;
 
 @PostMapping(value="/summernote/imageUpload.do", produces="application/json")
 public ResponseEntity<Map<String, Object>> summernoteImageUpload(MultipartHttpServletRequest multipartRequest) {
   return boardService.summernoteImageUpload(multipartRequest);
 }
    //멀티파트 리졸버를 가져와야 사용이 가능.
    //빈 준비만 해주면 스프링이 가져다씀 멀티 파트 파일 사용할때 본문의 파일을 가져다 쓰는건 요 아이 인코딩확인 크기확인 
 
 @PostMapping("/board/register.do")
 public String register(HttpServletRequest request) {
   System.out.println(request.getParameter("contents"));
   return "redirect:/main.do";
 }
  
 
 

}
