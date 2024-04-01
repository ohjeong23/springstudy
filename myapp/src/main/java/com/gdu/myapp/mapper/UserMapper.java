package com.gdu.myapp.mapper;

import java.util.Map;

import com.gdu.myapp.dto.LeaveUserDto;
import com.gdu.myapp.dto.UserDto;

public interface UserMapper {
  UserDto getUserByMap(Map<String, Object> map);
  int insertAccessHistory(Map<String, Object> map);
  LeaveUserDto getLeaveUserByMap(Map<String, Object> map); //탈퇴한 사람들 
  int insertUser(UserDto user);
  
} //마이 바티스의 메퍼라는 엑스엠엘을 자바측에서 호출할 수 있게 자바에 메퍼를 만든 방식 
//자바의 메퍼는 인터페이스로 만듬 인터페이스는 엑스엠엘과 연결되어있다. 
//자바측 메퍼는 인터페이스.
//엑스엠엘의 네임 페 자바의 쿼리 아이디를 동일하게 맞춰라.
//마이 바티스 네임스페이스를 인터페이스로 맞춰라.