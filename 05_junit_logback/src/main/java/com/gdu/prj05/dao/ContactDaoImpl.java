package com.gdu.prj05.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.gdu.prj05.dto.ContactDto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

// @Repository
//@RequiredArgsConstructor
@AllArgsConstructor
public class ContactDaoImpl implements ContactDao {

  
  private SqlSessionTemplate sqlSessionTemplate;
  //모든 메소드에서 쿼리 시작할때 사용함 
  //원래는 다오에 쿼리랑 코드 둘다 있었는데
  //마이바티스에선 다오에 코드 매퍼에 쿼리를 넣음
//파이널 처리를 위한 생성자 주입  
  //무슨 메퍼에 무슨 쿼리 
  
  public final static String NS = "com.gdu.prj05.mybatis.mapper.contact_t."; 
  
  @Override
  public int registerContact(ContactDto contact) { // name스페이스가 여기에 다들어가야함 
    int insertCount = sqlSessionTemplate.insert(NS+"registerContact", contact);   
    return insertCount ;
  }

  @Override
  public int modifyContact(ContactDto contact) {
    
    int updateCount = sqlSessionTemplate.update(NS + "modifyContact", contact);
    return updateCount;
  }

  @Override
  public int removeContact(int contactNo) {
    int deleteCount = sqlSessionTemplate.delete(NS+"removeContact", contactNo);  
    return deleteCount;
  }

  @Override
  public List<ContactDto> getContactList() {
    // 목 록 을 반환하더라도 리스트를 반환하는게 아니라 컨텍트디티오를  반환하는거임
    List<ContactDto> contactList = sqlSessionTemplate.selectList(NS + "getContactList"); 
    return contactList;
  }
   

  @Override
  public ContactDto getContactByNo(int contactNo) {
    
    
   ContactDto contact = sqlSessionTemplate.selectOne(NS+"getContactByNo", contactNo);
    return contact;
  }

}
