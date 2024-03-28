package com.gdu.prj06.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.prj06.dto.ContactDto;

public interface ContactService {
  void registerContact(HttpServletRequest request, HttpServletResponse response);
  void modifyContact(HttpServletRequest request, HttpServletResponse response);
  void removeContact(HttpServletRequest request, HttpServletResponse response);
  List<ContactDto> getContactList();
  ContactDto getContactByNo(int contactNo);
  void txTest();
}
  //view controller service  서비스가 컨트롤러로 전달해주는걸  
  
  // view controller serivce
//서비스에 트랜잭션 동작하는지 여부를 확인하기위해서 추가
