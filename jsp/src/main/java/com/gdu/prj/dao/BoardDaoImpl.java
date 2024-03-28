package com.gdu.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.gdu.prj.dto.BoardDto;

/*
 * view - controller - service - dao - db
 * 자바 스터디의 다오 
 * 메소드마다 만들고 만남 미리 선언 x 
 *다오는 디비 접근만함 디비접속할대 썼던 삼총사
 */

public class BoardDaoImpl implements BoardDao {

  // dao 는 db 를 처리한다.
  private Connection con;
  private PreparedStatement ps;
  private ResultSet rs;
  
  // Connection Pool 관리를 위한 DataSource 객체 선언
  private DataSource dataSource;
  
  // SingletonPattern
  private static BoardDao boardDao = new BoardDaoImpl();
  private BoardDaoImpl() {
    // META-INF\context.xml 파일에 명시된 Resource 를 이용해 DataSource 객체 생성하기
    try {
      Context context = new InitialContext();
      Context env = (Context)context.lookup("java:comp/env"); //파일자체를 찾기위해 
      dataSource = (DataSource)env.lookup("jdbc/myoracle");//어떤 걸 썼는지 확인  하나로 묶이면 env 뒤에 가져다 붙이면 됨
    } catch (NamingException e) {
      System.out.println("관련 자원을 찾을 수 없습니다.");
    }
  }
  public static BoardDao getInstance() {
    return boardDao;
  }
  
  @Override
  public int insertBoard(BoardDto board) {
    int insertCount = 0;
    try {
      con = dataSource.getConnection();
      String sql = "INSERT INTO BOARD_T(BOARD_NO, TITLE, CONTENTS, MODIFIED_AT, CREATED_AT) VALUES(BOARD_SEQ.NEXTVAL, ?, ?, CURRENT_DATE, CURRENT_DATE)";
      ps = con.prepareStatement(sql);
      ps.setString(1, board.getTitle());
      ps.setString(2, board.getContents());
      insertCount = ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      close();  // this.close();
    }
    return insertCount;
  }

  @Override
  public int updateBoard(BoardDto board) {
    int updateCount = 0;
    try {
      con = dataSource.getConnection();
      String sql = "UPDATE BOARD_T SET TITLE = ?, CONTENTS = ?, MODIFIED_AT = CURRENT_DATE WHERE BOARD_NO = ?";
      ps = con.prepareStatement(sql);
      ps.setString(1, board.getTitle());
      ps.setString(2, board.getContents());
      ps.setInt(3, board.getBoard_no());
      updateCount = ps.executeUpdate();
      
    } catch (Exception e) {
      e.printStackTrace();
      
      
    } finally {
      close();
    }
    return updateCount;
  }

  @Override
  public int deleteBoard(int board_no) {
    int deleteCount = 0;
    try {
      con = dataSource.getConnection();
      String sql = "DELETE FROM BOARD_T WHERE BOARD_NO = ?";
      ps = con.prepareStatement(sql);
      ps.setInt(1, board_no);
      deleteCount = ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      close();
    }
    return deleteCount;
  }
  
  @Override
  public int deleteBoards(String param) {
    int deleteCount = 0;
    try {
      con = dataSource.getConnection();
      String sql = "DELETE FROM BOARD_T WHERE BOARD_NO IN(" + param + ")";
      ps = con.prepareStatement(sql);
      deleteCount = ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      close();
    }
    return deleteCount;
  }
  

  @Override
  public List<BoardDto> selectBoardList(Map<String, Object> params) {
    List<BoardDto> boardList = new ArrayList<>();
    try {
      con = dataSource.getConnection();
      String sql = "SELECT BOARD_NO, TITLE, CONTENTS, MODIFIED_AT, CREATED_AT"
          + "  FROM (SELECT ROW_NUMBER() OVER(ORDER BY BOARD_NO "+ params.get("sort") + ") AS RN, BOARD_NO, TITLE, CONTENTS, MODIFIED_AT, CREATED_AT"
          + "          FROM BOARD_T)"
          + " WHERE RN BETWEEN ? AND ?";
      ps = con.prepareStatement(sql);
      ps.setInt(1, (int)params.get("begin"));//캐스팅 필요할지도 int
      ps.setInt(2, (int)params.get("end"));
      rs = ps.executeQuery();
      while(rs.next()) {//개수만큼 호출 ,한줄씩 처리 
        BoardDto board = BoardDto.builder()
                            .board_no(rs.getInt(1))
                            .title(rs.getString(2))
                            .contents(rs.getString(3))
                            .modified_at(rs.getDate(4))
                            .created_at(rs.getDate(5))
                          .build();
        boardList.add(board);
      }//쿼리 페이지 처리없는 전체 목록 가져오기
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      close();
    }
    return boardList;
  }

  @Override
  public int getBoardCount() {
    int boardCount = 0;
    try {
      con = dataSource.getConnection();
      String sql = "SELECT COUNT(*) FROM BOARD_T";
      ps = con.prepareStatement(sql);
      rs = ps.executeQuery();
      if(rs.next()) { //존재 안할수도 있어서 이프 해야함
        boardCount = rs.getInt(1);
      }//값이 같은건 있어봐야 하나 두개 0개
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      close();
    }
    return boardCount;
  }

  
  
  
  
  @Override
  public BoardDto selectBoardByNo(int board_no) {
    BoardDto board = null;
    try {
      con = dataSource.getConnection();
      String sql = "SELECT BOARD_NO, TITLE, CONTENTS, MODIFIED_AT, CREATED_AT FROM BOARD_T WHERE BOARD_NO = ?";
      ps = con.prepareStatement(sql);
      ps.setInt(1, board_no);
      rs = ps.executeQuery();
      if(rs.next()) {
        board = BoardDto.builder()
                    .board_no(rs.getInt(1))
                    .title(rs.getString(2))
                    .contents(rs.getString(3))
                    .modified_at(rs.getDate(4))
                    .created_at(rs.getDate(5))
                  .build();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      close();
    }
    return board;
  }

  @Override
  public void close() {
    try {
      if(rs != null)  rs.close();
      if(ps != null)  ps.close();
      if(con != null) con.close();  // Connection 반납으로 동작
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
