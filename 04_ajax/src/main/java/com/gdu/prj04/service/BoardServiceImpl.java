package com.gdu.prj04.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.prj04.dao.BoardDao;
import com.gdu.prj04.dto.BoardDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //파이널 필드의 주입구는 이거 주요 DI방식 
@Service
public class BoardServiceImpl implements BoardService {
//이거도 가져다 쓰려고 서비스를 빈으로 만들어주는 컨퍼넌트는 서비스
  private final BoardDao boardDao; //변조막기위해 파이널
  
  
  
  @Override
  public List<BoardDto> getBoardList() {

     return boardDao.getBoardList();
  }

  @Override
  public BoardDto getBoardByNo(int boardNo) {

    return boardDao.getBoardByNo(boardNo);
  }

  
}
