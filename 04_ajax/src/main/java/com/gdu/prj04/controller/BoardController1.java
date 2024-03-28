package com.gdu.prj04.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.prj04.dto.BoardDto;
import com.gdu.prj04.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/ajax1") //을 다르고 온다. 에이작 1로 시작하는 모든 요청을 담당하는 컨트롤러 
@Controller //
@RequiredArgsConstructor // 
public class BoardController1 {
  

  private final BoardService boardService;

  @ResponseBody // 반환값은 jsp 의 이름이 아니고 어떤 데이터다.(비동기 작업에서 꼭 필요한 annotation)
  @GetMapping(value="/list.do",produces="application/json")//produces : 응답 데이터 타입 (context-Type) //데이터의 컨텐트 타입 //이건 제이슨을 만들어 반환한다. // 목록인데 제이슨의 목록을 가져오고 싶음 , 이메소드가 만드는 데이터의 타입을 적어줄수있다.
  public List<BoardDto> list(){ //jackson 라이브러리가 List<BoardDto> 를 JSON 데이터로 변환한다.
    return boardService.getBoardList();
    
    //jsp 가 아니니까 뷰 리졸브가 관섭하지마 내가 리턴하는건 진짜 어떤 데이터가 반환하는건 이름이 아니라 어떤 데이터다 라는걸 알려주는 게
    //@ResponseBody 응답에 실려서 보낸다.어떤 데이터를 반환하는거 
  }
  
  
  @ResponseBody
  @GetMapping(value="/detail.do",produces="application/json")
  public BoardDto detail(int boardNo) { //requestpama 생략
    return boardService.getBoardByNo(boardNo);
  }
  
 
  
  
  

}
