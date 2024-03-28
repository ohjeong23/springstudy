package com.gdu.prj05.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.prj05.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value="/contact")//슬래시 컨트렉으로 시작하는 모든건 
@RequiredArgsConstructor
@Controller

public class ContactController {
  
  private final ContactService contactService;
  // private static final Logger log = LoggerFactory.getLogger(ContactController.class); // Contactcontroller 가 동작할 때 로그를 찍는 log 롬복이 대신 해줌 @slf4j
  
  //목록보기 목록을 가지고 목록보기로 넘어가야함 모델에 저장해서
 @GetMapping(value="/list.do")
 public String list(HttpServletRequest request, Model model) {
   log.info(request.getMethod() + "/" + request.getRequestURI());
   model.addAttribute("contactList",contactService.getContactList());
  return "contact/list";
 // 포워드 경로 
      }
 
 
 
 @GetMapping(value="/detail.do")
 public String detail(@RequestParam(value="contact-no",required=false, defaultValue="0") int contactNo, Model model){
  //번호 받아와서 변수에 저장하는 리퀘스트 파람 
// 번호가 오지않을 경우를 대비해서 전달이 안온경우 디폴트 0으로 반드시 와야하는거 아님
   model.addAttribute("contact",contactService.getContactByNo(contactNo));
   //                     
   return "contact/detail";
 }
 
 @GetMapping(value="/write.do")
 public String write(HttpServletRequest request) {
   log.info(request.getMethod() + "/" + request.getRequestURI());

   return"contact/write";
 }
 
 @PostMapping(value="/register.do")
 public void register(HttpServletRequest request, HttpServletResponse response) {
   log.info(request.getMethod() + "/" + request.getRequestURI());

   contactService.registerContact(request, response);
   
 }
 //자기가 직접 만드니까 반환할 필요가 없음
//중복 매핑 있으면 그냥 서버가 뻗어버림
 //매핑이 같아도 메소드가 다르면 됨
@GetMapping(value="/remove.do")
public void remove1(HttpServletRequest request, HttpServletResponse response) {
  log.info(request.getMethod() + "/" + request.getRequestURI());

  contactService.removeContact(request, response);
  
}

@PostMapping(value="/remove.do")
public void remove2(HttpServletRequest request, HttpServletResponse response) {
  log.info(request.getMethod() + "/" + request.getRequestURI());
  contactService.removeContact(request, response);
  
}


@PostMapping(value="/modify.do")
public void modify(HttpServletRequest request, HttpServletResponse response) {
  contactService.modifyContact(request, response);
}




}
