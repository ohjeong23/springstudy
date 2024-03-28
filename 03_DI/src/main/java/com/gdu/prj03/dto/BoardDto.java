package com.gdu.prj03.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder

public class BoardDto {
  
  private int boardNo;
  private String title;
  private String contents;

    
}
