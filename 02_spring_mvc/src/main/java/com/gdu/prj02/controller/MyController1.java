package com.gdu.prj02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller //컨트롤러
public class MyController1 {
  /*
   * 메소드
   * 
   * 1. 반환타입 
   *  1) String : 응답할 jsp 의 경로와 이름을 반환한다.
   *  2) void  
   *    (1) 요청한 주소를 jsp 이름으로 인식하고 처리한다. 직관적이지 않음
   *       - 요청 주소 : /board/list.brd
   *       - jsp 경로  : /board/list.jsp
   *       - 목록보기를 하고 싶어서 /board/list.do를 요청했다 . 이 주소를 jsp 이름으로 인식하고서 board/list.jsp 로 인식하고 처리함
   *    (2) 직접 응답(HttpServletResponse)을 만든다. 대부분 JavaScript 코드를 만든다.  
   *    - 응답을 만들어서 로케이션으로 이동한다는건 메소드 반환타입을 void로 해야함
   *    로케이션 하이퍼 어쩌구를 이용해서 직접 이동시킴 반환타입을 스트링말고 보이드로 잡고해
   *    리스판스 선언을 매개변수로 선언
   * 2. 메소드명 
   *   아무 일도 안 한다.
   *   
   * 3. 매개변수
   *  1) 요청과 응답을 위한 각종 변수의 선언이 가능하다.
   *  2) 주요 매개변수
   *    (1) HttpServletRequest request   request.getSession()을 사용해서 알아냈었음 선언해서 사용하는건 지금이 처음
   *    (2) HttpServletResponse response
   *    (3) HttpSession session - 페이지가 바뀌어도 항상 유지되어있음
   *    (4) 커맨드 객체 : 요청 파라미터를 받는 객체
   *    (5) 일반 변수   : 요청 파라미터를 받는  변수
   *    (6) Model model : forward 할 때 정보를 저장할 객체 (attribute)
   *    (7) RedirectAttributes rttr : redirect 할 때 정보를 저장할 객체 (flash attribute)     
   */
  
  // value="/"  : contextPath 요청을 의미한다. http://localhost:8080/prj02
  // value="/main.do": contextPath/main.do 요청. http://localhost:8080/prj02/main.do
  @RequestMapping(value={"/", "/main.do"}, method=RequestMethod.GET)
  public String welcome() {
    // 뷰 리졸버 prefix : /WEB-INF/views
    // 뷰 리졸버 suffix : .jsp
    // 실제 리턴 :/WEB-INF/views/index.jsp
    
    
    return "index";
  }

  
  
  
  
  
}
