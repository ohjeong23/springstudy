package com.gdu.prj09.dao;

import java.util.List;
import java.util.Map;

import com.gdu.prj09.dto.AddressDto;
import com.gdu.prj09.dto.MemberDto;

public interface MemberDao {
  int insertMember(MemberDto member);
  int insertAddress(AddressDto address);
  int updateMember(Map<String, Object> map);
  int updateAddress(Map<String, Object> map); // Controller 에서 Map으로 만들어서 Service로 넘ㄱ기고 이걸 다오로 넘김 이러면 업데이트할때 멤버랑 어드레스 둘다 맵을 사용
  int deleteMember(int memberNo);
  int deleteMembers(List<String> memberNoList); 
  int getTotalMemberCount();
  List<AddressDto> getMemberList(Map<String, Object> map);// 목록 가져올때 사용 두개
  MemberDto getMemberByNo(int memberNo);
  int getTotalAddressCountByNo(int memberNo);
  List<AddressDto> getAddressListByNo(Map<String, Object> map);//map 에 begin end member_no 세개가 들어있어야함 
}


  //회원 정보 하나 주소정보는 많이 
  
  // 파라미터 타입은 
  //#{param1}, #{param2} 생략하고 써도 됨
