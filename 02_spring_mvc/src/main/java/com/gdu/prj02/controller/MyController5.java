package com.gdu.prj02.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MyController5 {
  
  //요청이 두번이다 redirect add.do 끝나고 새요청을 만들어주면 redirect
  /*
   * redirect 방법
   * 
   * 1. return "redirect:요청주소";
   * 2. HttpServletResponse response 를 이용한 응답 만들기 
   */
  
  //Spring 4 이후 GetMapping / PostMapping / PutMapping / DeleteMapping 등 사용 가능
  
  //GetMapping PostMapping
  @GetMapping("/faq/add.do") //1 요청
  public String add(RedirectAttributes redirectAttributes) {
    
    // add 결과 1,0
    int addResult = Math.random() < 0.5 ? 1 : 0;
    
    // add 결과를 flash attribute로 저장하면 redirect 경로에서 확인이 가능하다.
    // 성공 : "/faq/list.do" 요청으로 이동하는 faq/list.jsp 에서 addResult 값을 확인할 숭 ㅣㅆ다.
    // 실패 : "/main.do" 요청으로 이동하는 index.jsp 에서 addResult 값을 확인할 수 있다.
    
    redirectAttributes.addFlashAttribute("addResult",addResult);
   
    
    
    // add 결과에 따른 이동 성공하면 목록보기 실패하면 메인으로 했었는데 
    String path = addResult == 1 ? "/faq/list.do" : "/main.do"; //2 요청이라 원래 뭐 다른 요청을 보낼 수 없음. 그치만 redirectATtri 있어서 가능
    
    
    // 이동
    return "redirect:" + path;
    //실제로 사용자가 두번의 이동을 감행 
  }
  
  @GetMapping("/faq/list.do")
  public String list() { //목록보기
    return "faq/list";
    
  }
      
   @GetMapping("/faq/modify.do")
   public void modify(HttpServletRequest request, HttpServletResponse response) {
     
     //modify 결과
     int modifyResult = Math.random() < 0.5 ? 1: 0;
     
     // 응답 만들기
     response.setContentType("text/html; charset=UTF-8");
     try {
       
       PrintWriter out = response.getWriter();
       out.println("<script>");
       if(modifyResult ==1) {
         out.println("alert('수정되었습니다.');");
         out.println("location.href='"+ request.getContextPath()  + "/faq/list.do';");
         //로케이션과 히스토리가 리다이렉트 역할 
       }else {
         out.println("alert('실패했습니다.')");
         out.println("history.back()");
       }
       out.println("</script>");
             
    } catch (Exception e) {

      e.printStackTrace();
    
    }
   }
  
  
   
   
   
   
}
