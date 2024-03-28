package com.gdu.prjex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberDto {

  private int addressNo;
  private String zonecode;
  private String address;
  private String detailAddress;
  private String extraAddress;
  private MemberDto member;
  
}
