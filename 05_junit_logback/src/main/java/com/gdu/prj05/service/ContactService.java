package com.gdu.prj05.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.prj05.dto.ContactDto;

public interface ContactService {
  
  void registerContact(HttpServletRequest request,HttpServletResponse response);
  void modifyContact(HttpServletRequest request,HttpServletResponse response);
  void removeContact(HttpServletRequest request,HttpServletResponse response);
  List<ContactDto> getContactList();
  ContactDto getContactByNo(int contactNo);
  
  
  
  //view controller service  서비스가 컨트롤러로 전달해주는걸  
  
  // view controller serivce

}
