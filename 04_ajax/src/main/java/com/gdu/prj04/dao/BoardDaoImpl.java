package com.gdu.prj04.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdu.prj04.dto.BoardDto;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor//  이거 하나로 끝남 //메소드이름이 빈의 이름 private BoardDto board1; .public BoardDto board1
public class BoardDaoImpl implements BoardDao {

  
  //@Autowired 3번 // 최악의 방법 하나하나 필드마다 오토해주는거 필드 주입방식
  
  //생성자 주입으로 집어넣기 
  
/*  public BoardDaoImpl(BoardDto board1, BoardDto board2, BoardDto board3) {
    super();
    this.board1 = board1;
    this.board2 = board2;
    this.board3 = board3;
  } */ // 근데 이게 @allargsConstrucrtor 
  private BoardDto board1;
  private BoardDto board2;
  private BoardDto board3;
  
  
  // 스프링 컨테이너에 board1~3까지 있음
  //이걸 가져와야하는데 이걸 가져오는게 @Autowired  근데 여기서 생성자를 써서 하는게 좋음
  
 //세터형식의 주입도 가능 
/*@Autowired
public void setBean(BoardDto board1,BoardDto board2, BoardDto board3) {
  this.board1 = board1;
  this.board2 = board2;
  this.board3 = board3;
  
//메소드의 매개변수를 자동주입 계산 방식으로 전달함. 메소드의 매개변수 자동주입 대상
//매개변ㅇ수에 빈을 주입하고 해당변수는 필드에 주입되어 그냥 필드에 주입되는 방식 
//매개변수에서 필드로 옮긴것
}*/
  
 

  @Override
  public List<BoardDto> getBoardList() {

    return Arrays.asList(board1,board2,board3);
  }

  @Override
  public BoardDto getBoardByNo(int boardNo) {
BoardDto board = null;
switch(boardNo) {
case 1 :board = board1;break;
case 2 :board = board2;break;
case 3 :board = board3;break;

}
    return board;
  }

}
