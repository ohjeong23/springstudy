package com.gdu.myapp.service;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UserMapper;
import com.gdu.myapp.utils.MySecurityUtils;

@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  
  public UserServiceImpl(UserMapper userMapper) {
    super();
    this.userMapper = userMapper;
  }

@Override
  public ResponseEntity<Map<String, Object>> checkEmail(Map<String, Object> params) {
    boolean enableEmail= userMapper.getUserByMap(params) == null
                     && userMapper.getLeaveUserByMap(params) == null;
  return new ResponseEntity<>(Map.of("enableEmail",enableEmail), HttpStatus.OK);
  
}  
  
  
  @Override
  public void signin(HttpServletRequest request, HttpServletResponse response) {
    
    
    
    try {
      //입력한 아이디
      String email = request.getParameter("email");
      //입력한 비밀번호 + SHA-256 방식의 암호화 처리를 하고 있다.
      String pw = MySecurityUtils.getSha256(request.getParameter("pw"));
      //접속한 아이디
      //접속한 기록을 남길 때 필요한 정보
       String ip = request.getRemoteAddr();
       
       //DB로 보낼 정보(email/pw ; USER_T, email/ip : ACCESS_HISTORY_T)
      Map<String, Object> params = Map.of("email", email
          , "pw", pw
          , "ip", ip);
      
      // email/pw 가 일치하는 회원 정보 가져오기
      UserDto user = userMapper.getUserByMap(params);
      
      // 일치하는 회원 있음 (Sign In 성공)
      if(user != null) {
//이메일 패스워드 가진 사용자가 있음. 돌아가는 유알엘 레퍼럴
    //돌아가기 전에 세션에 올리는거
  //성공했을때 ACCESS_HISTORY_t에 접속 기록 남기기 
  userMapper.insertAccessHistory(params);
  //회원 정보를 세션(브라우저 닫기 전까지 정보가 유지되는 공간, 기본 30분 정보 유지)에 보관하기
        request.getSession().setAttribute("user", user);
        //Sign In 후 페이지 이동
        response.sendRedirect(request.getParameter("url"));  //이동한다. 익셉션 //페이지 이동 코드
      } else { // 일치하는 회원 없음 (else: sign in 실패)
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter(); //익셉션 이여서 오류남
        out.println("<script>");
        out.println("alert('일치하는 회원 정보가 없습니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.page'"); // 로그인 실패시
        out.println("</script>");
        out.flush(); 
        out.close(); 
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  @Override
  public void signout(HttpServletRequest request, HttpServletResponse response) {

    
    
  }

  @Override
  public void signup(HttpServletRequest request, HttpServletResponse response) {
    

  }

  @Override
  public void leave(HttpServletRequest request, HttpServletResponse response) {
   
    

  }

}