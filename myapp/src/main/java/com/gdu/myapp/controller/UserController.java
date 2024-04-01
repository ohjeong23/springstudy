package com.gdu.myapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.myapp.service.UserService;

@RequestMapping("/user") 
@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    super();
    this.userService = userService;
  }
  
  @GetMapping("/signin.page")
  public String signinPage(HttpServletRequest request
                         , Model model) { //리퀘스트의 header안에 referer 라는 값으로 들어있는거임  블로그 보다가 로그인하고서 이전 페이지의 정보를 가지고 있는거임 
    
    // Sign In 페이지 이전의 주소가 저장되어 있는 Request Header 의 referer
    String referer = request.getHeader("referer"); //로그인 이후 돌아갈 주소이기 때문에 필요
    
    //
    
    
    // referer 로 돌아가면 안 되는 예외 상황 (아이디/비밀번호 찾기 화면, 가입 화면 등)
    String[] excludeUrls = {"/findId.page","/findPw.page","/signup.page"}; //쓰면 안되는 유알엘
    //여기서 하나씩 봅아서 체크
    
    // Sign In 이후 이동할 url
    String url = referer; // 사이트 오자마자 로그인 하는 곳 
    if(referer != null) { //referer가 항상 있진 않음  들어가자마자 로그인하는 사이트는 리퍼럴 값이 없음 
      for(String excludeUrl : excludeUrls) { //리퍼럴에 가면 안되는 주소가 있는지 확인
        if(referer.contains(excludeUrl)) {
          url = request.getContextPath() + "/main.page"; //가면 안되는 곳에 포함되면 메인페이지로 보내주겠다.
          break; //기본적으로 리퍼럴이 유알엘인데 가면 안되면 바꿈 리퍼럴이 없어도 바꿔줌
        }
      }
    } else {
      url = request.getContextPath() + "/main.page";  //발견하면 바로 브레이크//mvc controller 에서 함 
    }
    
    // Sign In 페이지로 url 넘겨 주기
    model.addAttribute("url",  url); //값을 가지면 유알엘 값 으로 아니면 리퍼럴값으로// 예외 주소에 포함되어 있는지에 따라  무조건 넘겨주는건 아님 
     //모델에 저장해서 리턴했으니 포워드 한것 포워드때 전달할 데이터 모델에  
    return "user/signin";
    
  }
  
  @PostMapping("/signin.do")
  public void signin(HttpServletRequest request, HttpServletResponse response) {
    userService.signin(request, response);
  }
  
  @GetMapping("/signup.page")
  public String signupPage() {
    
    return "user/signup"; 
  }
  
  @PostMapping(value="/checkEmail.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> checkEmail(@RequestBody Map<String, Object> params) {
    return userService.checkEmail(params); //컨트롤러에 오자마자 화면으로 다시감 서비스가 다 이미 했음. 서비스로부터 받아서 서빙만 함.
  }
  
  
  @PostMapping(value="/sendCode.do", produces="application/json")
  public ResponseEntity<Map<String,Object>> sendCode(@RequestBody Map<String, Object> params){
    System.out.println(params);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  
  
  
  
}