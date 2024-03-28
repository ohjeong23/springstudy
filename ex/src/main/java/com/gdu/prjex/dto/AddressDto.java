package com.gdu.prjex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class AddressDto {
  
  private int memberNo;
  private String email;
  private String name;
  private String gender;
  

}
