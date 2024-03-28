package com.gdu.prjex.dao;

import java.util.List;
import java.util.Map;

import com.gdu.prjex.dto.AddressDto;
import com.gdu.prjex.dto.MemberDto;

public interface MemberDao {
  
  int insertMember(MemberDto member);
  int insertAddress(AddressDto address);
  int updateMember(MemberDto member);
  int deletemember(int memberNo);
  int deletemembers(List<String> memberNoList);
  int getTotalMemberCount();
  List<AddressDto> getMemberList(Map<String, Object> map);
  MemberDto getmemberByNo(int memberNo);
  int getTotalAddressCountByNo(int memberNo);
  List<AddressDto> getAddressListByNo(Map<String, Object> map);
  
  
  
  

}
