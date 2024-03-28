package com.gdu.prj.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.jasper.tagplugins.jstl.core.Param;

import com.gdu.prj.common.ActionForward;
import com.gdu.prj.dao.BoardDao;
import com.gdu.prj.dao.BoardDaoImpl;
import com.gdu.prj.dto.BoardDto;
import com.gdu.prj.utils.MyPageUtils;

import jakarta.servlet.http.HttpServletRequest;

/*
 * view - controller - service - dao - db
 * 서비스는 다오를 사용해서 정보를 완성한다.
 */

public class BoardServiceImpl implements BoardService {

  // service 는 dao 를 호출한다.
  private BoardDao boardDao = BoardDaoImpl.getInstance();
  
  //field 목록 보기는  MyPageUtils 객체가 필요하다.
  private MyPageUtils myPageUtils = new MyPageUtils();
  
  
  @Override
  public ActionForward addBoard(HttpServletRequest request) {
//타이틀하고 컨텐츠 파라미터가 있다 리퀘스트에 ,insertBoard 필요로함 BoardDto로만들어서 넘겨줘야 받아야함

    String title = request.getParameter("title");
    String contents = request.getParameter("contents");
    BoardDto board = BoardDto.builder()
                        .title(title)
                        .contents(contents)
                      .build();
    int insertCount = boardDao.insertBoard(board);
      //리다이렉트 경로는 유알엘매핑으로 작성한다.

    // redirect 경로는 URLMapping 으로 작성한다.
    String view = null;
    if(insertCount == 1) {
      view = request.getContextPath() + "/board/list.brd";
    } else if(insertCount == 0) {
      view = request.getContextPath() + "/main.brd";
    }//INSERT 이후 이동은  redirect contextPath부터 경로 적기 request로부터 꺼내서 붙임
    //service가 돌아야 함 
    // INSERT 이후 이동은 redirect 이다.
    return new ActionForward(view, true);
  }

  @Override
  public ActionForward getBoardList(HttpServletRequest request) {
    
    // 전체 게시글 개수 
    int total = boardDao.getBoardCount();
    //한 페이지에 표시할 게시글 개수
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    
    //현재 페이지 번호
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
       //정렬방식 소트 전달안하면 내림차순 오름차순으로 해도 2페이지에선 내림차순으로 바뀜
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    
    //페이징 처리에 필요한 변수 값 계산하기 setPaging 호출
    myPageUtils.setPaging(total, display, page);  //비긴 엔드값 계산됨
    
    //목록을 가져올 때 필요한 변수를 Map 에 저장함
    Map<String, Object> params = Map.of("begin", myPageUtils.getBegin(),
        "end",myPageUtils.getEnd()
        ,"sort", sort);
    
    
    //목록 가져오기
   
    List<BoardDto> boardList = boardDao.selectBoardList(params);
    
    //페이지 링크 가져오기 이걸론 부족
    String paging = myPageUtils.getPaging(request.getRequestURI(),sort,display);
    
    //jsp에 전달할 데이터들
   
   
    request.setAttribute("total",total);
    request.setAttribute("boardList", boardList);
    request.setAttribute("paging", paging);
    request.setAttribute("display", display);
    request.setAttribute("sort", sort);
    
    return new ActionForward("/board/list.jsp", false);
  }

  @Override
  public ActionForward getBoardByNo(HttpServletRequest request) {

    Optional<String> opt = Optional.ofNullable(request.getParameter("board_no"));
    int board_no = Integer.parseInt(opt.orElse("0"));   
    BoardDto board = boardDao.selectBoardByNo(board_no);
    String view = null;
    if(board != null) {
      view = "/board/detail.jsp";
      request.setAttribute("board", board);
      
    }else {
      view = "/index.jsp";
    
    }
     
    return new ActionForward(view,false);
  }

  @Override
  public ActionForward editBoard(HttpServletRequest request) {

    String param = request.getParameter("board_no");
    int board_no = 0;
    if(!param.isEmpty()) {
      board_no = Integer.parseInt(param);
    }
    BoardDto board = boardDao.selectBoardByNo(board_no);
    String view =null;
    if(board != null) {
      view ="/board/edit.jsp";
      request.setAttribute("board", board);
    }else {
      view = "/index.jsp";
    }
    return new ActionForward(view,false);
  }

  @Override
  public ActionForward modifyBoard(HttpServletRequest request) {

    int board_no=Integer.parseInt(request.getParameter("board_no"));
    String title = request.getParameter("title");
    String contents = request.getParameter("contents");
    BoardDto board = BoardDto.builder()
              .title(title)
              .contents(contents)
              .board_no(board_no)
              .build();
       
       
    int updateCount = boardDao.updateBoard(board);
    String view = null;
    if(updateCount ==0) {
      view = request.getContextPath()+"/main.brd";
      
    }else {
      view = request.getContextPath()+"/board/detail.brd?board_no="+board_no;
    }
    return new ActionForward(view,true);
  }

  @Override
  public ActionForward removeBoard(HttpServletRequest request) {

    String param = request.getParameter("board_no");
    int board_no =0;
    if(!param.isEmpty()) {
      board_no=Integer.parseInt(param);
      
    }
    int deleteCount = boardDao.deleteBoard(board_no);
    String view = null;
    if(deleteCount ==0) {
      view = request.getContextPath()+"/main.brd";
          }else {
            view= request.getContextPath()+"/board/list.brd";
          }
    
    return new ActionForward(view,true);
  }
  

  @Override
  public ActionForward removeBoards(HttpServletRequest request) {
    String param = request.getParameter("param");
    int deleteCount = boardDao.deleteBoards(param);
    String view = null;
    if(deleteCount == 0) {
      view = request.getContextPath() + "/main.brd";
    } else {
      view = request.getContextPath() + "/board/list.brd";
    }
    return new ActionForward(view, true);
  }

}