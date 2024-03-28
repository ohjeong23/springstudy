package com.gdu.prj09.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; //요청 본문 에이작 요청 data 

import com.gdu.prj09.dto.AddressDto;
import com.gdu.prj09.service.MemberService;

import lombok.RequiredArgsConstructor;

/*
 * RESTful
 * 1. REpresentation State Transfer
 * 전송의 새로운 표현법 
 * 2. 요청 주소를 작성하는 한 방식이다.
 * 3. 요청 파라미터를 ? 뒤에 추가하는 Query String 방식을 사용하지 않는다.
 * 4. 요청 파라미터를 주소에 포함하는 Path Variable 방식을 사용하거나, 요청 본문에 포함하는 방식을 사용한다.
 * 비동기 방식으로 파일을 요청에 본문에 포함하는 방식을 씀. 요청할때 폼을 쓰고 바디가 데이터 본문에 포함한다는 뜻. 삽입과 수정을 오늘 할거
 * 데이터를 포함하는 파라미터를 본문에아니면 주소에 포함하는 방식들
 * 5. 요청의 구분을 "주소 + 메소드" 조합으로 구성한다.
 * 6. CRUD 요청 예시
 *         |             URL             |       Method
 * --------|---------------------------------------------------------    
 * 1) 목록 | / members                   |        GET
 *         | / members/page/1            |
 *         | / members/page/1/display/20 |        
 * 2) 상세 | /members/1                  |        GET
 * 3) 삽입 | /members                    |        POST // 데이터를 본문에 실어서 보냄 포스트 방식의 요청은 주소창에 노출하지 않고 
 * 4) 수정 | /members                    |        PUT 삽입은 아니지만 비슷한 풋 방식 
 * <3,4 메소드가 다르니까 구성이 다름 주소는 같지만 내부적으론 포스트랑 같지만 다름>
 * 5) 삭제 | /member/1                  |       DELETE  
 *         | /members/1,2,3              |       DELETE
 * 
 * 
 * 
 * 저번엔 폼이였는데 이번엔 제이슨데이터로 보내본다. 객체를 만들고 보냄
 * 
 * 
 * 
 * 
 */

@Controller
@RequiredArgsConstructor //스프링 컨테이너에서 가져와라 ioc 근데 못가져옴 멤버 서비스 스프링 컨테이너에 안넣어서
public class MemberController {

  private final MemberService memberService;
  
  @GetMapping("/admin/member.do")
  public void adminMember() {
    //컨트롤러에 반환타입이 없으면 주소를 jsp 로 인식한다.
    //반환타입이 void 인 경우 주소를 jsp 경로로 인식한다.
    //  /amin/member.do ====> /WEB-INF/views/admin/member.jsp
  }
  
  @PostMapping(value="/members", produces="application/json")
  public ResponseEntity<Map<String, Object>> registerMember(@RequestBody Map<String, Object> map
                                                          , HttpServletResponse response){
    
    
  //리스판스는 이메일 중복체크 안하려고 그냥 예외로 넘기려고  
    
    return memberService.registerMember(map, response); //성공 했다고 함
    
  }
  //{page} vlaue=page 여기 있는값을 인트 페이지에저장해라. 전달이 안되면 null로 인식됨
 //null 일 수도 있는 데이터를 옵셔널 경로에 포함된 값 스트링이여서 파라미터랑 
  @GetMapping(value="members/page/{p}/display/{dp}",produces="application/json") //페이지가 필수가 아닐 수 있다. 리콰이어드
  public ResponseEntity<Map<String, Object>> getMembers(@PathVariable(value="p", required=false) Optional<String> optPage
                                                       ,@PathVariable(value="dp",required=false) Optional<String> optDisplay){
    
   
    int page = Integer.parseInt(optPage.orElse("1"));
    int display = Integer.parseInt(optDisplay.orElse("20")); // 전달되면 전달된 값 전달안되면 1이나 20 
    
    
    return memberService.getMembers(page, display);
  
  
  }
  @GetMapping(value="/members/{memberNo}", produces="application/json")
  public ResponseEntity<Map<String, Object>> getMemberByNo(@PathVariable(value="memberNo", required=false) Optional<String> opt) {
    int memberNo = Integer.parseInt(opt.orElse("0"));
    return memberService.getMemberByNo(memberNo);
  }
  
  @PutMapping(value="/members", produces="application/json")
  public ResponseEntity<Map<String, Object>> modifyMember(@RequestBody Map<String, Object> map){
    
    return memberService.modifyMember(map);
  }
  
  @DeleteMapping(value="/member/{memberNo}", produces="application/json")
  public ResponseEntity<Map<String, Object>> removeMember(@PathVariable(value="memberNo", required=false) Optional<String> opt){
  int memberNo = Integer.parseInt(opt.orElse("0"));
  
  return memberService.removeMember(memberNo);
  //멤버 지울때 어드레스를 지우려고 할 필요없다.  자동으로 지워지게 테이블 짜놓음
  }

@DeleteMapping(value="/members/{memberNoList}", produces="application/json")
public ResponseEntity<Map<String, Object>> removeMembers(@PathVariable(value="memberNoList",required=false) Optional<String> opt ){
  
  return memberService.removeMembers(opt.orElse("0"));
  
  
}


}
