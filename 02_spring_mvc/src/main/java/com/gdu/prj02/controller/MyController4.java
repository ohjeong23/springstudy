package com.gdu.prj02.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.gdu.prj02.dto.BlogDto;

@Controller
public class MyController4 {
  @RequestMapping("/blog/list.do")
  public String list(Model model) {
   //model에 데이터 저장 방식  
    // DB 에서 select 한 결과
    List<BlogDto> blogList = Arrays.asList(
        new BlogDto(1,"제목1"),
        new BlogDto(2,"제목2"),
        new BlogDto(3,"제목3")
        );
    
    // Model에 저장한 값은 forward 할 때 전달된다.
    model.addAttribute("blogList", blogList);

    
    
    // 기본 이동 방식은 forward 방식이다. 데이터 전달 
// 그동안 전달할때 리퀘스트를 써서 전달했음   public String list(HttpServletRequest request로 request.setAttribute("blogList",blogList) 

    return "blog/list"; 
    //메소드는 겟방식이 기본 그래서 위에 메소드는 생략가능,method=RequestMethod.GET
  }
  //컨트롤러가 응답하는 제이에스의 경로를  프리픽스에 뷰로 해놔서 뷰에 만들어야함

  @RequestMapping("/blog/detail.do")
  //파라미터의 이름
  public String detail(@RequestParam(value="blogNo"
                                   , required=false
                                   , defaultValue="0") int blogNo, Model model) {
   
    // DB 에서 가져온 데이터 
    BlogDto blog = BlogDto.builder()
                          .blogNo(blogNo)
                          .title("제목" + blogNo)
                          .build();
    //포워딩하려면 모델이 필요 매개변수의 모델이 선언되어 있어야 함
   
    //JSP 로 전달할 데이터
    model.addAttribute("blog",blog);
    //blog/detail.jsp 로 forward 이동하다
    return "blog/detail";
    
        
    
    
  }
  
  //@RequestMapping(value="/blog/add.do", method=RequestMethod.POST)
  public String add(BlogDto blog) { // 커맨드 객체의 모델Model 저장 방식 : 클래스 타입을 camelCase로 ㅣ변경해서 저장한다. (BlogDto->blogDto 로 변경해서 저장)
    //블로그에만 저장되는게 아니라 모델에도 저장됨
    //blog/addResult.jsp로 forward 
    //객체이름으로 되지않고 클래스타입 으로 저장된다.
    //모델없이 포워드 중인데 됨 왜냐면 자동으로 저장되기에
    return "blog/addResult";// addResult로 forward
    //카멜케이스로 바꿔서 모델에 저장 
  }
  
  
  //forward 할때 모델이 필요하다.
  @RequestMapping(value="/blog/add.do", method=RequestMethod.POST)
  public String add2( @ModelAttribute("blog") BlogDto blog) { //@ModelAttribute : 커맨드 객체가 모델Model의 저장되는 이름을 지정할때 사용 이름 바꾸고 싶을때
    
    return"blog/addResult";
  }
  
  
  
}
