package com.gdu.prj09.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//member jsp로 전달
public class MemberDto {
  
  private int memberNo;
  private String email;
  private String name;
  private String gender;
  
//굳이 두 테이블 더해서 디티오를 만들 필요없고 그냥 맵을 만들어서 하는게 낫다. 필요한걸 꺼내서 쓰기

}
