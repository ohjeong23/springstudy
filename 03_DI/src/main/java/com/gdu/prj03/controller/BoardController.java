package com.gdu.prj03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.prj03.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

//        @Controller @Service  @Repository
// view - controller - service - dao
//빈이 세개 저장되어있다. 컨트롤러에 뒤에 꺼까지
@RequiredArgsConstructor  // 파이널 필드용 생성자이고 낫널 체크
@Controller  //Controller 에서 사용하는 @Component
public class BoardController {
/************************* DI ******************************************/
/*
 * Dependency Injection
 * 1. 의존 주입
 * 2. Spring Container 에 저장된 bean 을 특정 객체에 넣어주는 것을 말한다.
 * 3. 방법
 *     1) 필드 주입
 *     2) 생성자 주입 ***요거 씀
 *     3) setter 주입 
 * 4. 사용 가능한 annotation
 *   1) @Inject
 *   2) @Resource, @Qulifier
 *   3) @Autowired (대부분 이걸 사용한다.) 이걸로 할거임
 */
  //1. 필드 주입 
  //@Autowired
  //;  
  //boardService 임플
  
  //2.생성자 주입
  //  1)생성자의 매개변수로 주입된다.
  //  2) @Autowired 를 생략할 수 있다,. 생성자 주입에서만 오토와이어드 생략 가능
  // private BoardService boardService;
 
  // private BoardController(BoardService boardService) {
  //  super();
  //  this.boardService = boardService;
  // } //생성자의 매개변수로 자동 주입 된다.
  
  //3. setter 주입
  //   1) 메소드의 매개변수로 주입된다.
  //   2) @Autowired 를 생략할 수 없다.
  //   3) 사실 메소드명 상관이 없다.
  // 인터페이스 하나에 메소드 여러개 넣음  
  // 인터페이스에 메소드 하나 일수도 ListService DetailService 따로 만들고 기능마다 클래스를 따로 만듬 
  // 서비스에 메소드 여러개 있을 수 도  public void setBean(10...) 10 개 다 주입되서 필드로 넘어감
  // private BoardService boardService;
  
  // @Autowired
  // public void setBoardService(BoardService boardService) {
  //  this.boardService = boardService;
  // }
  
  // 앞으로 사용할 한 가지 방식
  // final 필드 + 생성자 주입(lombok의 @RequiredArgsConstructor를 이용해서 매개변수의 null 체크를 수행함) 파이널 필드에 
  // 파이널 처리를 하게 되면 파이널은 곧바로 초기화를 해야함
  private final BoardService boardService;
  
  
  //@allArgsConstructor로 한방에 해버림
  // 이걸 할때 스프링 컨스트럭에 빈이 없으면 안됨 
  //그래서 롬복에서는 널이면 안된다. 
//  public BoardController(@NonNull BoardService boardService) {
//  super();
//  this.boardService = boardService;
// } //생성자를 통해 파이널필드를 채워주는건 가능하다. 1단께는 생성자 호출 생성자의 경우에는 파이널 

  
  
  
  
  @GetMapping("/board/list.do")
  public String list(Model model) {
  //목록보기 포워딩해줘야하니까 필요한게 모델
   model.addAttribute("boardList", boardService.getBoardList());
   return "board/list";
   
    
  }
  
  @GetMapping("/board/detail.do")
  public String detail(int boardNo ,Model model) {
    model.addAttribute("board",boardService.getBoardByNo(boardNo));
    return "board/detail";
  }
  
}
