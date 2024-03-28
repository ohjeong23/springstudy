package com.gdu.prjex.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.gdu.prjex.dto.AddressDto;
import com.gdu.prjex.dto.MemberDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {
  
  private final SqlSessionTemplate sqlSessionTemplate;
  
  public final static String NS = "com.gdu.prjex.mybatis.mapper.member_t.";
  
  
  @Override
  public int insertMember(MemberDto member) {
    
    
    return sqlSessionTemplate.insert(NS+"insertMember",member);
  }

  @Override
  public int insertAddress(AddressDto address) {
    return 0;
  }

  @Override
  public int updateMember(MemberDto member) {
    return 0;
  }

  @Override
  public int deletemember(int memberNo) {
    return 0;

  
  }

  @Override
  public int deletemembers(List<String> memberNoList) {

    
    return 0;
  }

  @Override
  public int getTotalMemberCount() {

    
    return 0;
  }

  @Override
  public List<AddressDto> getMemberList(Map<String, Object> map) {

    
    return null;
  }

  @Override
  public MemberDto getmemberByNo(int memberNo) {

    
    
    return null;
  }

  @Override
  public int getTotalAddressCountByNo(int memberNo) {

    
    return 0;
  }

  @Override
  public List<AddressDto> getAddressListByNo(Map<String, Object> map) {

    
    return null;
  }

}
