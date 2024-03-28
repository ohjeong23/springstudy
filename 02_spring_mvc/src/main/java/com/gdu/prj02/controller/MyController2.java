package com.gdu.prj02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController2 {
  
  // value="/board/list.do"  :  http://localhost:8080/prj02/board/list.do
  // 응답 jsp                : /WEB-INF/views/board/list.jsp                              
  
  //    <a href="${contextPath}/board/list.do">board 목록</a> 컨텍스트패치 빼고 경로 쓰기
  @RequestMapping(value="/board/list.do",method=RequestMethod.GET)
  public void method() {
   //board/list.jsp 를 만들어서 경로와 이름이 같게 해줌.
    
    
    
  }
  

}
