package com.gdu.prj08.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class HistroyDto {
 
  private int historyNo;
  private String writer;
  private String ip;
  
  
}
