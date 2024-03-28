package com.gdu.prj04.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.prj04.dao.BoardDao;

@Controller
public class MvcController {

  //@Inject
  //private BoardDao boardDao;
  
  @GetMapping(value={"/","/main.do"})
  public String welcome() {
   // System.out.println(boardDao.getBoardList());  
    return "index";
    
  }
  @GetMapping ("/exercise1.do") //주소를 제이에스피 이름으로 인식시키기 반환 안해버리면 됨 보이드로 인해 경로가 엑설사이즈1로 포워딩 됨
  
  public void exerices1() {
  
    //경로를 
  }
  

  @GetMapping("/exercise2.do")
  public String exercies2() {
    return "exercise2";
  }
  
  @GetMapping("/exercise3.do")
  public String exercies3() {
    return "exercise3";
  }
   

}
