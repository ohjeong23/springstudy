package com.gdu.prj04.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.prj04.dto.BoardDto;
import com.gdu.prj04.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/ajax3")
@RequiredArgsConstructor
@Controller // @ResponseBody annotation 은 ResponseEntity 클래스가 제공한다.
public class BoardController3 {
  
  //레스트 컨트롤러일 필요도 없다. 리스폰스엔티티를 쓰면 리스폰스 바디할필요 없음.
  //선택지 너무 많아.
  private final BoardService boardService;
  
  @GetMapping("/list.do")
  //뭘 생략해
  public ResponseEntity<List<BoardDto>> list(){ //제네릭 응답 데이터의 차이 
  
    
    //응답 헤더 빼고 프로듀스 써도 됨 갈아타자
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/json");
    
    // 반환
    return new ResponseEntity<List<BoardDto>>( boardService.getBoardList()   //리스판스 코드만 보냄 응답 헤더와 응답 코드를 보냄
                                             , header
                                             , HttpStatus.OK); //200 
    
        
    
  }
  
  @GetMapping("/detail.do")
  public ResponseEntity<BoardDto> detail(int boardNo){
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/json");
  return new ResponseEntity<BoardDto>(boardService.getBoardByNo(boardNo)
                                        ,header
                                        , HttpStatus.OK);
  }
  //보드 디티오를 담을 거다.
  

}
