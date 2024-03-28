package com.gdu.prj09.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gdu.prj09.dto.MemberDto;

@Service
public interface MemberService {
  
  //하나의 서비스는 여러개의 다오를 부를 수 있음 
  //목록 가져올때도 두개가 필요함.
  //한 서비스에서 둘다 부를 일은 없다.deleteMember,s
  //사용자가 삭제 버튼을 눌렀는데 둘다 되면 안되니까 서비스를 두개로 분리
  //싱글페이지 반환할때 쓰라고 스프링에서 만든거  @ResponseBody 를 품고 있는 메퍼 
//Map int List 값 다 저장해서 쓸 예정
  //HttpServletRequest , @RequestParam , MyPageUtils(setter 가 아직 없어서 안됨)
  //prj09/members?page=1&display=20 파라미터 @RequestParam
  //prj09/members/page/1/display/20 으로 바꿔서  경로에 포함된 데이터 @PathVariable
  
  // /members ---> getMembers(int page, int dispaly) ---->getTotalMemberCount()
  // 목록요청        페이지랑 디스플레이 요청         호출할때 리스트 맵 getMemberList(Map map)

      
 
  ResponseEntity<Map<String, Object>> getMembers(int page, int display);//display,page 를 받아와  
  ResponseEntity<Map<String, Object>> getMemberByNo(int memberNo);
  ResponseEntity<Map<String, Object>> registerMember(Map<String, Object> map, HttpServletResponse response);  //응답은 캐치 블록에서 예외 쓰려고 만듬  
  //1과0 만 있더라도 맵으로 보내주기 
  //중복처리할거면 getMemberByEmail(String email)을 만들어야하는데 이번엔 안함
  
  ResponseEntity<Map<String,Object>> modifyMember(Map<String, Object> map); //이메일을 아이디로 씀 
  
  //이메일은 수정할 수 없다.
  
  ResponseEntity<Map<String,Object>> removeMember(int memberNo); //한명 지울때 uri method 겟 호스트 메소드가 추가되면서 상세보기와 주소가 주소 체계가 같더라도 요청을 분류할 수 있다. 
  ResponseEntity<Map<String, Object>> removeMembers(String memberNoList);  //매개변수 구분해서 처리 ,삭제를 위한 딜리트 매핑 @DeleteMapping 주소창에 정보가 전달됨 겟ㄱ방식과 비슷함
  
  
  
  
  
  
}
