package com.gdu.prj03.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.prj03.dao.BoardDao;
import com.gdu.prj03.dto.BoardDto;

import lombok.RequiredArgsConstructor;
//리스트 가져와서 곧바로 컨트롤러에 넘겨버림
//빈으로 해놔야 컨트롤러가 가져다 씀
//                   서비스용 컨포넌트는 서비스 
//       @Controller @Service  @Repository(이걸로 빈에 만들어서 담았다. 보드다오 타입이 같은걸 가져와서 보드다오타입은 하나만 보드다오임플)
//보드 다오 타입으로 잡아주는게 맞다.
//view - controller - service - dao
//컨트롤러가 바오를 쓸수없으니까 가져와서 바로 넘겨줌
@RequiredArgsConstructor

@Service
public class BoardServiceImpl implements BoardService {

  //똑같은 타입 두개면 스프링컨테이너가 관리할 빈이아닌데 있는거 빈은 옵젝트 구분해서 쓰기 
  private final BoardDao boardDao;
  //서비스는 다오 필요
  //생성자는 오토와이어를 생략가능
  //오토와이어를 배웠는데 오토와이어 생략함 없음
  
  
  
  @Override
  public List<BoardDto> getBoardList() {

    
    return boardDao.getBoardList();
  }

  @Override
  public BoardDto getBoardByNo(int boardNo) {

    
    return boardDao.getBoardByNo(boardNo);
  }

}
